package io.aetherit.ats.ws.model.dao;

import java.time.LocalDateTime;

import io.aetherit.ats.ws.model.type.ATSLangCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieCoverImage {
	private int coverIdx;
	private int movId;
    private String coverDomain;
    private String horizontalLarge;
    private String horizontalSmall;
    private String verticalLarge;
    private String verticalSmall;
    private ATSLangCode langCd;
    private String modId;
    private LocalDateTime modDt;
}
