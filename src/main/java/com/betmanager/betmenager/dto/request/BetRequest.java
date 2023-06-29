package com.betmanager.betmenager.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BetRequest {
    private Integer oddsFor;
    private Integer oddsAgainst;
}
