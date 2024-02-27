package org.arep;


import org.arep.conexion.Function;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Class to start the server
 * @author Santiago Naranjo
 * @author Daniel Benavides
 */
public class SNSpark {

    private static HashMap<String, Function> service = new HashMap<>();
    public static boolean running = false;
    private static SNSpark instance = null;


    /**
     * Returns the singleton instance of the SNSpark class.
     *
     * @return the SNSpark singleton instance
     */
    public static SNSpark getInstance() {
        if (instance == null) {
            instance = new SNSpark();
        }
        return instance;
    }

    /**
     * Starts the SNSpark server and listens for incoming connections on port 35000.
     *
     * @param args command-line arguments (ignored)
     * @throws IOException if there is an error starting the server
     * @throws URISyntaxException if there is an error parsing a URI
     */
    public void start(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        SNSpark.running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean firstLine = true;
            String uriStr = "";

            while ((inputLine = in.readLine()) != null) {
                if (firstLine) {
                    uriStr = inputLine.split(" ")[1];
                    firstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            URI requestUri = new URI(uriStr);
            try {
                System.out.println("----------------"+ requestUri.getPath());
                if (requestUri.getPath().startsWith("/action")) {
                    if(service.containsKey(requestUri.getPath().replace("/action",""))){
                        outputLine = callService(requestUri);
                        out.println(outputLine);
                    } else if(requestUri.getPath().contains(".")){
                        httpResponse(requestUri.getPath().replace("/action", ""), clientSocket.getOutputStream());
                    }else{
                        httpError();
                    }
                } else {
                    httpResponse(requestUri.getPath(), clientSocket.getOutputStream());
                }
            } catch (Exception e) {
                e.printStackTrace();
                httpError();
            }

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /**
     * Calls the service associated with the specified URI and returns the HTTP response.
     *
     * @param requestUri the URI of the service to call
     * @return an HTTP response string
     */
    private String callService(URI requestUri) {
        String calledServiceUri = requestUri.getPath().substring(7);
        String output = "HTTP/1.1 200 OK\r\n"
                + "Content-Type:text/html\r\n"
                + "\r\n";

        if (service.containsKey(calledServiceUri)) {
            if(requestUri.getQuery() != null){
                output += service.get(calledServiceUri).handle(requestUri.getQuery().split("=")[1]);
            }
            else{
                output += service.get(calledServiceUri).handle("");
            }
        }
        return output;
    }

    /**
     * Registers a GET request handler for the given path.
     *
     * @param path the path for which to register the handler
     * @param s the handler function to execute when the path is requested via a GET request
     * @throws IOException if there is an error registering the handler
     * @throws URISyntaxException if the path is not a valid URI
     */
    public static void get(String path, Function s) throws IOException, URISyntaxException {
        service.put(path, s);
    }

    /**
     * Registers a POST request handler for the given path.
     *
     * @param path the path for which to register the handler
     * @param s the handler function to execute when the path is requested via a POST request
     * @throws IOException if there is an error registering the handler
     * @throws URISyntaxException if the path is not a valid URI
     */
    public static void post(String path, Function s) throws IOException, URISyntaxException {
        service.put(path, s);
    }

    /**
     * Generates an HTTP error response with a 404 status code.
     *
     * @return the HTTP error response as a string
     */
    public static String httpError(){
        String outputLine ="HTTP/1.1 404 Not Found \r\n"
                + "Content-Type:text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Form Example</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "     <h1>Error</h1>\n"
                + "    <body>"
                + "</html>";

        return outputLine;

    }

    /**
     * This method handles the HTTP response for a given requested URI. It checks if the requested file exists in the "public" directory.
     * If the file exists, it sends an HTTP 200 OK response with the appropriate content type.
     * If the file does not exist, it sends an HTTP 404 Not Found error response.
     *
     * @param requestedUri The requested URI.
     * @param outputStream The output stream to write the HTTP response to.
     * @throws IOException If an I/O error occurs while reading the file or writing the HTTP response.
     */
    public static void httpResponse(String requestedUri, OutputStream outputStream) throws IOException {
        Path file = Paths.get("target/classes/public" + requestedUri);
        if (Files.exists(file)) {
            String contentType = Files.probeContentType(file);
            if (contentType != null) {
                outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                outputStream.write(("Content-Type: " + contentType + "\r\n").getBytes());
                outputStream.write("\r\n".getBytes());
                if (Files.isRegularFile(file) && contentType.startsWith("image/")) {
                    byte[] bytes = Files.readAllBytes(file);
                    outputStream.write(bytes);
                } else {
                    String content = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
                    outputStream.write(content.getBytes());
                }
            } else {
                String outputLine = httpError();
                outputStream.write(outputLine.getBytes());
            }
        } else {
            String outputLine = httpError();
            outputStream.write(outputLine.getBytes());
        }
    }

}
