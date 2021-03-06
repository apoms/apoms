package io.aetherit.ats.ws.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSUserToken {
    private String token;
    private String chatToken;
    private ATSSimpleUser user;
}
