package io.aetherit.ats.ws.model.main;

import java.util.List;

import io.aetherit.ats.ws.model.movie.ATSNewMovie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSSelection {
    private String clsId;
    private int id;
    private List<ATSNewMovie> movList;
    private String name;
    private int navId;
    private int selectionType;
}
