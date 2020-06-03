package io.aetherit.ats.ws.model.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieActorBas {
	private int actorIdx;
	private String actorName;
	private String actorNameCn;
	private String actorNameEn;
	private String actorNameJp;
    private String briefIntroduction;
    private String photoDomain;
    private String photoUrl;
    private int videosCount;
    private int height;
    private int bust;
    private int hips;
    private int waist;
    private String cup;
    private BigDecimal starLevel;
    
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
}
