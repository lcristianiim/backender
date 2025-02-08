package org.interactor;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;


public class AuthenticationTestsFixture {
    private String method;
    private String endpoint;
    private String requestBody;
    private String jwtServer;

    private int responseStatus;
    private String responseBody;


    public boolean registerUser(String method, String endpoint, String payload) {
        endpoint = parseHTMLLinkFromFitnesse(endpoint);

        HttpResponse<String> response = Unirest.post(endpoint)
                .header("content-type", "application/json")
                .body(payload)
                .asString();

        responseStatus = response.getStatus();
        responseBody = response.getBody();

        return responseStatus == 200;
    }

    public boolean login(String method, String endpoint, String payload) {

        endpoint = parseHTMLLinkFromFitnesse(endpoint);

        HttpResponse<String> response = Unirest.post(endpoint)
                .header("content-type", "application/json")
                .body(payload)
                .asString();

        responseStatus = response.getStatus();
        responseBody = response.getBody();

        return responseStatus == 200;
    }

    public String getActivationLink(String method, String endpoint) {

        endpoint = parseHTMLLinkFromFitnesse(endpoint);

        HttpResponse<String> response = Unirest.get(endpoint)
                .header("content-type", "application/json")
                .asString();

        responseStatus = response.getStatus();
        responseBody = response.getBody();

        return responseBody;
    }

    public boolean confirmUser(String method, String endpoint) {
        endpoint = parseHTMLLinkFromFitnesse(endpoint);

        HttpResponse<String> response = Unirest.get(endpoint)
                .header("content-type", "application/json")
                .asString();

        responseStatus = response.getStatus();
        responseBody = response.getBody();

        return response.getStatus() == 200;
    }

    public boolean purgeUser(String method, String endpoint) {
        endpoint = parseHTMLLinkFromFitnesse(endpoint);

        HttpResponse<String> response = Unirest.delete(endpoint)
                .header("content-type", "application/json")
                .asString();

        responseStatus = response.getStatus();
        responseBody = response.getBody();

        return response.getStatus() == 200;
    }

    private static String parseHTMLLinkFromFitnesse(String endpoint) {
        return endpoint.replaceAll("<a href=\"(.*?)\">(.*?)</a>", "$1");
    }

    public void setJwtServer(String jwtServer) {
        this.jwtServer = jwtServer;
    }

}
