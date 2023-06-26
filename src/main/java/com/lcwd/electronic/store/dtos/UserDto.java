package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.Validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userId;
    @Size(min=3, max=15, message = "Invalid name")
    private String name;
    //@Email(message = "Invalid user name")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid email")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password required")
    private String password;
    @Size(min=4, max=6, message = "Invalid gender")
    private String gender;
    @NotBlank(message="write something about yourself")
    private String about;
    @ImageNameValid
    private String imageName;
}
