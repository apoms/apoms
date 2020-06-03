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
	private String add;
	private String id;
    private String channelType;
    private boolean hasSelection;
    private String name;
    private String parentModule;
    private String showType;
}
