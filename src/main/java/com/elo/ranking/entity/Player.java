package com.elo.ranking.entity;

import com.elo.ranking.helper.EloConstant;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@NoArgsConstructor
public class Player {

    @Id
    private Long playerId;

    private String name;

    private int noOfWins;

    private int noOfLost;

    private long score;

    private int rank;

    @ElementCollection
    private List<Long> wonAgainst;

    @ElementCollection
    private List<Long> lostAgainst;

    public Player(Long playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.score = EloConstant.DEFAULT_SCORE;
        wonAgainst = new LinkedList<>();
        lostAgainst = new LinkedList<>();
    }

    private float expectedPlayerScore(long opponent_score) {
        return 1.0f * 1.0f / (1 + 1.0f *
                (float) (Math.pow(10, 1.0f *
                        (opponent_score - this.score) / 400)));
    }

    private void updateScore(long opponent_score, int score) {
        this.score += EloConstant.K_FACTOR * (score - expectedPlayerScore(opponent_score));
    }

    public void playerWon(long opponent_score) {
        this.noOfWins += 1;
        updateScore(opponent_score, 1);
    }

    public void playerLost(long opponent_score) {
        this.noOfLost += 1;
        updateScore(opponent_score, 0);
    }

    public void wonAgainst(long playerId) {
        wonAgainst.add(playerId);
    }

    public void lostAgainst(long playerId) {
        lostAgainst.add(playerId);
    }

}
