package com.takehome.stayease.dto;

import com.takehome.stayease.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupDto {

    @NotBlank(message = "Invalid email")
    @Email(message = "Invalid email")
    String email;

    @NotBlank
    String password;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    RoleEnum role = RoleEnum.USER;
}
