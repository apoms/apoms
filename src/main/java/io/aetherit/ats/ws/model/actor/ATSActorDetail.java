package io.aetherit.ats.ws.model.actor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSActorDetail {
	private int id;
	private String actorName;
	private String nameCn;
	private String nameEn;
	private String nameJpn;
    private String briefIntroduction;
    private String photoUrl;
    private int videosCount;
    private int height;
    private int bust;
    private int hips;
    private String cup;
    private int waist;
}
