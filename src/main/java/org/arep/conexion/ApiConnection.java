package org.arep.conexion;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is responsible for connecting to the OMDB API and obtaining information about movies.
 * It uses a cache to prevent multiple requests to the API for the same movie.
 * @author Santiago Naranjo
 * @author Daniel Benavides
 */
public class ApiConnection {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://www.omdbapi.com/?apikey=6395a690&t=";
    private static final Map<String, String> CACHE = new HashMap<>();
    /**
     * Makes a request to the OMDB API using the movie title as a parameter.
     * If the movie has been searched before, the cached response is returned.
     *
     * @param searchFilm Movie title to search.
     * @return API response in JSON format.
     * @throws IOException If there is an error in the connection or reading the response.
     */
    public static String httpClientAPI(String searchFilm) throws IOException {
        searchFilm = searchFilm.toLowerCase();
        if (CACHE.containsKey(searchFilm)) {
            System.out.println("------------CACHE------------------");
            return CACHE.get(searchFilm);
        }
        URL obj = new URL(GET_URL + searchFilm.toLowerCase());
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream in = con.getInputStream();
            String responseBody = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);
            CACHE.put(searchFilm, jsonResponse.toString());
            System.out.println(jsonResponse.toString());
            return jsonResponse.toString();
        } else {
            System.out.println("GET request not worked");
            return "";
        }
    }

}

