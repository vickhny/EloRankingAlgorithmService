package com.elo.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class PlayerDTO {

    private Long playerId;

    private String name;

    private int noOfWins;

    private int noOfLost;

    private long score;

    private int rank;

}
