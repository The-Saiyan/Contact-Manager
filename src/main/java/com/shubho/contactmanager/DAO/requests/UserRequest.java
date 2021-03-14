package com.shubho.contactmanager.DAO.requests;

import com.shubho.contactmanager.model.Contact;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Name can not be blank")
    @Size(min = 3, max = 20, message = "Minimum 3 and maximum 20 characters are allowed")
    private String name;

    @NotBlank(message = "Email can not be blank")
    private String email;

    @NotBlank(message = "Password can not be blank")
    @Size(min = 4, message = "Password should contain minimum of 4 characters")
    private String password;
    private String role;
    private Boolean enabled;
    private String imageUrl;
    private String about;
    private List<Contact> contacts;

    @Override
    public String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", imageUrl='" + imageUrl + '\'' +
                ", about='" + about + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
