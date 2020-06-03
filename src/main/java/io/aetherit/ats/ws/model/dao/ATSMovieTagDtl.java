package io.aetherit.ats.ws.model.dao;

import io.aetherit.ats.ws.model.type.ATSLangCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieTagDtl {
    private int id;
    private int movId;
    private String name;
    private ATSLangCode langCd;
}
