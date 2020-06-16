package io.aetherit.ats.ws.model.movie;

import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSMovieChannelCtg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieChannel {
	private int id;
    private int channelType;
    private boolean hasSelection;
    private String name;
    private int parentModule;
    private int showType;
    private int showOrder;
    private boolean userYn;
    private List<ATSMovieChannelCtg> subChannel;
}
