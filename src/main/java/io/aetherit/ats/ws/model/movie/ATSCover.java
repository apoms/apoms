package io.aetherit.ats.ws.model.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSCover {
    private String horizontal_large;
    private String horizontal_small;
    private String vertical_large;
    private String vertical_small;
}
