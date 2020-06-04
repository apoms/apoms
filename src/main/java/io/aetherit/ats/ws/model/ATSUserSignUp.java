package io.aetherit.ats.ws.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSUserSignUp {
    private ATSUser user;
    private String emailVerificationCode;
}
