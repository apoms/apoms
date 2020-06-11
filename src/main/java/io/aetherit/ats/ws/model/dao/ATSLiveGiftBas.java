package io.aetherit.ats.ws.model.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSLiveGiftBas {
    private int giftIdx;
    private String pointName;
    private String pointType;
    private BigDecimal pointPrice;
    private String pointPriceUnit;
    private String iconDomain;
    private String iconUrl;
    private int saleQty;
    private long regId;
    private LocalDateTime regDt;
}
