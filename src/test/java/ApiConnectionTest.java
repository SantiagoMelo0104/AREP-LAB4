import org.arep.conexion.ApiConnection;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class ApiConnectionTest {

    // Test class for the ApiConnection's httpClientAPI method
    @Test
    public void testHttpClientAPI_cachedResponse() throws IOException {
        // Given a search for "The Matrix" movie
        String searchFilm = "The Matrix";

        // When we make the first API call with the given search term
        String response1 = ApiConnection.httpClientAPI(searchFilm);

        // And we make the second API call with the same search term
        String response2 = ApiConnection.httpClientAPI(searchFilm);

        // Then the first and second responses should be the same
        assertTrue(response1.contains("The Matrix")); // Check if the response1 contains "The Matrix"
        assertEquals(response1, response2); // Check if response1 and response2 are equal
    }

    // Test class for the ApiConnection's httpClientAPI method
    @Test
    public void testHttpClientAPI_differentMovie() throws IOException {
        // Given a search for "The Matrix" movie and "Inception" movie
        String searchFilm1 = "The Matrix";
        String searchFilm2 = "Inception";

        // When we make the first API call with the first search term
        String response1 = ApiConnection.httpClientAPI(searchFilm1);

        // And we make the second API call with the second search term
        String response2 = ApiConnection.httpClientAPI(searchFilm2);

        // Then the first response should contain "The Matrix"
        assertTrue(response1.contains("The Matrix"));

        // And the second response should contain "Inception"
        assertTrue(response2.contains("Inception"));
    }

    // Test class for the ApiConnection's httpClientAPI method
    @Test
    public void testHttpClientAPI_emptyRequest() throws IOException {
        // Given an empty search term
        String response = ApiConnection.httpClientAPI("");

        // Then the API call should return an empty response
        assertEquals("", response);
    }
}