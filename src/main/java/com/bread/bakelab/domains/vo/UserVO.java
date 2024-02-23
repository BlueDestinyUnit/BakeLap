package com.bread.bakelab.domains.vo;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter@Setter
@ToString
public class UserVO {
    @NotBlank
    @Length(max = 80)
    private String id;
    @NotBlank
    private String pw;
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String tel;

    private String address;
    @NotBlank
    @Pattern(regexp = "SELLER|USER")
    private String role;
    private String state;
}
