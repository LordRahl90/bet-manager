package com.betmanager.betmenager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BetResponse {
    private UUID id;
    private Integer oddsFor;
    private Integer oddsAgainst;
    private Date createdAt;
}
