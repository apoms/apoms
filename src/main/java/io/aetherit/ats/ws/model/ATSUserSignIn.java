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
	@Email
    @NotBlank(message = "user.login.message.mustemail")
    private String email;
    @NotNull
    private String password;
}
