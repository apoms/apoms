package io.aetherit.ats.ws.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSChannel {
    private String add;
    private int channelType;
    private boolean hasSelection;
    private int id;
    private String name;
    private int parentModule;
    private int showType;
}
