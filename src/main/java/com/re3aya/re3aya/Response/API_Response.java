package com.re3aya.re3aya.Response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class API_Response {
    private String message;
    private Object data;

    public API_Response() {

    }

    public API_Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
