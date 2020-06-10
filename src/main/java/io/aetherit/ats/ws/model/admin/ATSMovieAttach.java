package io.aetherit.ats.ws.model.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieAttach {
    private String movId;
    private long userId;
    private String comment;
    private int sortOrder;
}
