package io.aetherit.ats.ws.model.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieSearchHistory {
    private int srchWord;
    private int srchType;
    private int tagId;
}
