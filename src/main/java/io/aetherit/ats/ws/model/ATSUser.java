package io.aetherit.ats.ws.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import io.aetherit.ats.ws.model.type.ATSUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSUser {
    private long userId;
    
    @NotNull
    private String password;
    
//    @Email
//    @NotNull
//    @NotBlank(message = "Please enter your email.")
    private String email;
    
//    @NotNull
//    @NotBlank(message = "Please enter your phone number.")
    private String phoneNo;
    
    @NotNull
    private ATSUserType type;
    
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    
}
