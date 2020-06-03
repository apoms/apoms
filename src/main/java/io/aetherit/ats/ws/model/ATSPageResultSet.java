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
public class ATSPageResultSet {
    private int code;
    private String enumCode;
    private String msg;
    private boolean success;
    private int count;
    private int pageCount;
    private int pageNo;
    private int pageSize;
    private List<?> data;
}
