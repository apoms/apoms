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
public class ATSMovieSearchTxn {
    private int srchIdx;
    private int srchWord;
    private int srchType;
    private int srchCnt;
    private ATSLangCode langCd;
}
