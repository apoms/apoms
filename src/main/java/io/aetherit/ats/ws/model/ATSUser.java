package io.aetherit.ats.ws.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.aetherit.ats.ws.model.type.ATSUserStatus;
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
    @NotNull
    private String nickName;
    
    @NotNull
    @NotBlank(message = "Please enter your phone number.")
    private String cntryCode;
    private String phoneNo;
    
    @NotNull
    private ATSUserType type;
    private ATSUserStatus statusCode;
    private BigDecimal pointAmt;
    
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    
}
