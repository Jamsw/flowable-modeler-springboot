package com.spartices;

import org.flowable.ui.common.model.UserRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class login {

    @GetMapping("flowable/rest/account")
    public UserRepresentation test(){
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail("admin@qq.com");
        userRepresentation.setFirstName("admin");
        userRepresentation.setLastName("test");
        userRepresentation.setId("admin");
        return userRepresentation;
    }
}
