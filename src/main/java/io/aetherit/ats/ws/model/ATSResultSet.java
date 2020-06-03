package io.aetherit.ats.ws.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSResultSet {
    private int code;
    private String enumCode;
    private String msg;
    private boolean success;
    private List<?> data;
}
