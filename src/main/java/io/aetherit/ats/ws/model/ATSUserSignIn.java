package io.aetherit.ats.ws.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSUserSignIn {
    @NotBlank(message = "user.login.message.mustid")
    private String userId;
    @NotNull
    private String password;
}
