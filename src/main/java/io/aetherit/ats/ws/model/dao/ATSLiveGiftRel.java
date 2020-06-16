package io.aetherit.ats.ws.model.dao;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSLiveGiftRel {
    private long giftRelId;
    private long roomId;
    private int giftIdx;
    private long userId;
    private int donateQty;
    private LocalDateTime regDt;
}
