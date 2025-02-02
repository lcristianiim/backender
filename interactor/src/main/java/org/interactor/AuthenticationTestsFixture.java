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


    public boolean newUserCreationRequest(String method, String endpoint, String payload) {
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

    public boolean cleanDatabase() {
        return true;
    }

    private static String parseHTMLLinkFromFitnesse(String endpoint) {
        return endpoint.replaceAll("<a href=\"(.*?)\">(.*?)</a>", "$1");
    }


    public void setJwtServer(String jwtServer) {
        this.jwtServer = jwtServer;
    }


}
