package io.aetherit.ats.ws.model;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    private String userId;
    
    @NotNull
    private String password;
    
    @NotNull
    @NotBlank(message = "Please enter your phone number.")
    private String phoneNo;
    
    @NotNull
    private String userName;
    
    private ATSUserType type;
    
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    
}
