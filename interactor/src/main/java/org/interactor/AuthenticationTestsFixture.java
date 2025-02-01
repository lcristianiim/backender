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

    public boolean login(String method, String endpoint, String requestBody) {

        String payload = requestBody;
        endpoint = endpoint.replaceAll("<a href=\"(.*?)\">(.*?)</a>", "$1");

        HttpResponse<String> response = Unirest.post(endpoint)
                .header("content-type", "application/json")
                .body(payload)
                .asString();

        responseStatus = response.getStatus();
        responseBody = response.getBody();

        return responseStatus == 200;
    }


    public void setJwtServer(String jwtServer) {
        this.jwtServer = jwtServer;
    }
}
