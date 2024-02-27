package org.arep;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.arep.SNSpark.get;
import static org.arep.SNSpark.post;

public class App {
    public static void main(String[] args) throws IOException, URISyntaxException {
        get("/hola", (req) -> {
            return "Hola Mundo O_O " + req;
        });


        post("/adios", (req) -> {
            return "esto es una prueba post ~_~ " + req;
        });

        // start the server
        if (!SNSpark.running) SNSpark.getInstance().start(args);


    }

}
