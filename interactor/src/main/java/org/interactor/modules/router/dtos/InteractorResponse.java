package org.interactor.modules.router.dtos;

public class InteractorResponse {

    private String body;
    private int code;
    private ResponseType type;
    private Principal principal;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }
}
