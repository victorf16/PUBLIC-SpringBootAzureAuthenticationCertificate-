package com.example.apresentacao.services;


import com.example.apresentacao.dto.SumResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CalculateService {

    private String accessToken = null;

    @Value("${azure.auth.url}")
    private String azuerAuthURL;

    @Autowired
    private JWTCertificateService jwt;

    public void printBearerToken() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String bearerToken = jwt.getToken().block();
        System.out.println("bearerToken: " + bearerToken);

        if (bearerToken != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(bearerToken);

            JsonNode accessTokenNode = jsonNode.get("access_token");
            if (accessTokenNode != null) {
                accessToken = accessTokenNode.asText();
                System.out.println("Access Token: " + accessToken);
            } else {
                System.out.println("Access Token not found in the JSON response.");
            }
        } else {
            System.out.println("Bearer Token not available.");
        }
    }


    public SumResponse sum(int a, int b) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        WebClient client = WebClient.create();
        String parametros = "{\n" +  " \"a\": " + a + ",\n" + "\"b\": " + b + "\n" + "}\n";
        printBearerToken();
        System.out.println("Assertion" + jwt.getAssertion());
        System.out.println("Token" + jwt.getToken().block());
        System.out.println("o token e = " + accessToken);

        Mono<SumResponse> sumResponseMono = client.post()
                .uri("https://apim-dev-apimwebappterraform.azure-api.net/sum/calculate") // deve se colocar a url do apim
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // define cabeçalhos
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken) //puxa da classe JWTCertificateService
                //.header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6Ii1LSTNROW5OUjdiUm9meG1lWm9YcWJIWkdldyJ9.eyJhdWQiOiI2MDcwNGE0Zi0wMzBjLTRiYzctYTk4Ny1iMTYzOGZjNjBiZDEiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vMDlkMWQ5ZGItOTljZC00OWVlLTg5YjQtNjMyMTIxNGRlYTNhL3YyLjAiLCJpYXQiOjE2OTMxOTI3MzUsIm5iZiI6MTY5MzE5MjczNSwiZXhwIjoxNjkzMTk2NjM1LCJhaW8iOiJFMkZnWU9pdjJlcDZhKzV6MGRScEFTeFRnazljWGJmeG5ubEpwcDJrK3NGb3N5T1BHbmdBIiwiYXpwIjoiMjVhNjRhZGMtMDE0MC00ODhkLTk5NzgtYmM3ZGJiNzQ3MGIzIiwiYXpwYWNyIjoiMiIsIm9pZCI6IjlhMmMwNWEyLTY5NzctNGIzMy04YTUzLTRhOTM5YjExMDg1MyIsInJoIjoiMC5BVmdBMjluUkNjMlo3a21KdEdNaElVM3FPazlLY0dBTUE4ZExxWWV4WTRfR0M5RllBQUEuIiwic3ViIjoiOWEyYzA1YTItNjk3Ny00YjMzLThhNTMtNGE5MzliMTEwODUzIiwidGlkIjoiMDlkMWQ5ZGItOTljZC00OWVlLTg5YjQtNjMyMTIxNGRlYTNhIiwidXRpIjoiOElrRXBicmV1RWlHbi1ldFh3WTdBQSIsInZlciI6IjIuMCJ9.EnopjT_QGwXSjmJ7kzPxHnICU8LvdtaJQudql-NYWvLFI7e8MVUrdBiPPXWiSLWrKoZcNMRcKtkKxkwFfJV3KYjhV6_LraPQzSUkdqXS7BHvSZHG_QZnGj9GRTN68gP3qRKTTWKh7oSXHha688KkDqEx2nlW5JVtaukrygJBkNNzy0BkDZtTBlzsfKB3etZAaOvvLkwbf1-bEOea4VI8C1r0F80FbDSs-Gvyjqjwb-Y4TZuAlDvUlMc7qYlVeDRDmC3mSUJ6fjsCXz9yKquf_-f1orqiMPlhL54t2E7fjeqwzgJpDAqbPYwg8TxHjz_lM0xoKcmiE1c6whf6QY7jpw" ) //Quado coloco o bearer manualmente ele consegue retornar, aparentemente ele nao ta gerando o bearer corretamente
                .header("Accept-Encoding", "gzip,deflate")
                .body(BodyInserters
                        .fromValue(parametros)

                )
                .retrieve()
                .bodyToMono(SumResponse.class);
        return sumResponseMono.block();


    }
}
