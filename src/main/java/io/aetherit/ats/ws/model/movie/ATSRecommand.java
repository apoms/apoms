package io.aetherit.ats.ws.model.movie;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSRecommand {
	private int actionNum;
	private HashMap<String,String> address;
	private ATSCover allCovers;
    private ATSCover allJCovers;
    private int faceNum;
    private long id;
    private int movId;
    private String movName;
    private LocalDate recommendDate;			// 2020-05-04
    private String recommendDesc;
    private List<ATSMovieTag> relTagName;
    private int storyNum;
}
