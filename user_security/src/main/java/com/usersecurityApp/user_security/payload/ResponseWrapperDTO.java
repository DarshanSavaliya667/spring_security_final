package com.usersecurityApp.user_security.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWrapperDTO {
    private Integer status;
    private String message;
    private Object data;
    private String path;

    public ResponseWrapperDTO() {
    }

    public ResponseWrapperDTO(Integer status, String message ,String path ) {
        this.status = status;

        this.message = message;
        this.path = path;
    }

    public ResponseWrapperDTO(Integer status,  String message, Object data,String path) {
        super();
        this.status = status;

        this.message = message;
        this.data = data;
        this.path = path;
    }
}
