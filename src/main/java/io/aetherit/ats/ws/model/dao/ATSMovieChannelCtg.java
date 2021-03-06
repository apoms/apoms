package io.aetherit.ats.ws.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieChannelCtg {
	private int id;
    private int channelType;
    private boolean hasSelection;
    private String name;
    private int parentModule;
    private int showType;
    private int showOrder;
    private boolean userYn;
}
