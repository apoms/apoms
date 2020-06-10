package io.aetherit.ats.ws.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.aetherit.ats.ws.model.type.ATSUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSSimpleUser implements Serializable {
    public static final long serialVersionUID = 1L;

    private long userId;
    private String nickName;
    private String phoneNo;				// include country code
    private ATSUserType type;				// include country code
    private LocalDateTime regDt;
    private LocalDateTime modDt;
}
