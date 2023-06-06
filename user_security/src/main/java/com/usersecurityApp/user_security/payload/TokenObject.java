package com.usersecurityApp.user_security.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class TokenObject {

    private Long userId;

    private Boolean isAdmin;


    private Boolean isSuperAdmin;

    //for isSemiAdmin and isAdmin
    private List<String> urls;
}
