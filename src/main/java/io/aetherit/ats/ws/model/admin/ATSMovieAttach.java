package io.aetherit.ats.ws.model.admin;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSMovieCoverImage;
import io.aetherit.ats.ws.model.dao.ATSMovieTagDtl;
import io.aetherit.ats.ws.model.movie.ATSMovieActor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieAttach {
	private int movId;
	private String movName;
	private String movNameEng;
	private String movNameJpn;
	private LocalTime mins;
	private int playCnt;
	private String playCntStr;
	private int downCnt;
	private boolean hasDown;

	private int loveCnt;
	private boolean hasLove;
	private int upCnt;
	private boolean hasUp;
	private int dissCnt;
	private boolean hasDiss;
	private String movSn;
	private String movSnOri;
	private String movType;
	private boolean movProvider;
	private String domain;
	private String f240p;
	private String f360p;
	private String f480p;
	private String f720p;
	private String f1080p;
	private String movDesc;
	private boolean isMosaic;
	private int movScore;
	private int giftTotal;
	private BigDecimal movSaving;
	private boolean showYn;
	private int showOrder;
	private String srchMovName;
	private List<ATSMovieActor> actorList;
	private List<ATSMovieTagDtl> tagListChn;
	private List<ATSMovieTagDtl> tagListEng;
	private List<ATSMovieTagDtl> tagListJpn;
	private ATSMovieCoverImage coverChn;
	private ATSMovieCoverImage coverEng;
	private ATSMovieCoverImage coverJpn;
}