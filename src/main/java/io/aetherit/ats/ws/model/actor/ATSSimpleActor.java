package io.aetherit.ats.ws.model.actor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSSimpleActor {
    private int id;
    private String nameCn;
    private String nameEn;
    private String nameJpn;
    private String photoUrl;
}

