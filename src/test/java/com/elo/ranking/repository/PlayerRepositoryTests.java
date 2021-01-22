package com.elo.ranking.repository;

import com.elo.ranking.entity.Player;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerRepositoryTests {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void test_create_player_data() {
        Player player = new Player(1L, "Navin");
        playerRepository.save(player);
        List<Player> players = findAll();
        Assertions.assertEquals(1, players.size());
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }
}
