package io.aetherit.ats.ws.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    private String userId;
    private String phoneNo;
    private String userName;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
}
