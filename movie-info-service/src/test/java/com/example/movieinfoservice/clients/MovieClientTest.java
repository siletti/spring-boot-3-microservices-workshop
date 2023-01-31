package com.example.movieinfoservice.clients;

import com.example.movieinfoservice.exceptions.MovieNotFoundException;
import com.example.movieinfoservice.exceptions.UnauthorizedException;
import com.example.movieinfoservice.models.MovieSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {"eureka.client.enabled=false", "api.url=http://localhost:8088/movie"})
class MovieClientTest {

    public static WireMockServer wireMockServer = new WireMockServer(8088);

    @BeforeAll
    static void setupClass() {
        configureFor("http://localhost:8088/movie", 8088);
        wireMockServer.start();
    }

    @AfterEach
    void after() {
        wireMockServer.resetAll();
    }

    @AfterAll
    static void clean() {
        wireMockServer.shutdown();
    }

    @Autowired
    MovieClient movieClient;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${api.key}")
    private String apiKey;

    private static final String movieId = "550";

    @Test
    void getMovie401() {
        wireMockServer.stubFor(get(urlPathEqualTo("/movie/" + movieId))
                .willReturn(aResponse()
                        .withStatus(401)));

        assertThrows(UnauthorizedException.class, () -> movieClient.getMovie(movieId, apiKey));
    }

    @Test
    void getMovie404() {
        wireMockServer.stubFor(get(urlPathEqualTo("/movie/" + movieId))
                .willReturn(aResponse()
                        .withStatus(404)));

        assertThrows(MovieNotFoundException.class, () -> movieClient.getMovie(movieId, apiKey));
    }

    @Test
    void getMovie200() throws Exception {
        final String expectedBody = """
                { "id": 550, "title": "Fight Club",  "overview": "A ticking-time-bomb insomniac.."}
                """;

        final MovieSummary expected = objectMapper.readValue(expectedBody, MovieSummary.class);

        wireMockServer.stubFor(get(urlPathEqualTo("/movie/" + movieId))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(expectedBody)
                        .withStatus(200)));


        final MovieSummary actual = movieClient.getMovie(movieId, apiKey);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }
}