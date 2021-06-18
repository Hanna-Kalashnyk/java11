//package com.exadel.discount.dto.user;
//
//import com.exadel.discount.dto.validation.Create;
//import com.exadel.discount.entity.Role;
//import lombok.Data;
//
//import javax.validation.constraints.Null;
//import java.util.UUID;
//
//@Data
//public class BaseUserDto {
//    @Null(groups = Create.class, message = "User id should be null")
//    private UUID id;
//    private String firstName;
//    private String lastName;
//    private String phone;
//    private String email;
//    private String login;
//    private String password;
//    private Role role;
//}
