package com.elo.ranking.service;

import com.elo.ranking.dto.Matches;
import com.elo.ranking.entity.Player;
import com.elo.ranking.exceptionHandler.FileFormatException;
import com.elo.ranking.helper.EloConstant;
import com.elo.ranking.helper.PlayerDBHelper;
import com.elo.ranking.repository.PlayerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest
class EloRankingServiceTest {

    @InjectMocks
    private PlayerDBHelper playerDBHelper;

    @Mock
    private PlayerRepository playerRepository;


    @BeforeEach
    void setup() {
        openMocks(this);
    }


    @Test
    void test_update_player_data() throws Exception {

        List<Player> playerList = mockUpdatePlayerData();

        assertNotNull(playerList);
        assertEquals(4, playerList.size());
        assertEquals(0L, playerList.get(0).getPlayerId());
        assertEquals(1L, playerList.get(1).getPlayerId());

    }

    @Test
    void test_get_all_player_data_sortBy_RANK() throws Exception {

        List<Player> playerList = mockUpdatePlayerData();
        Mockito.when(playerRepository.findAll()).thenReturn(playerList);
        List<Player> sortedPlayerList = playerDBHelper.getAll(EloConstant.RANK);

        assertNotNull(sortedPlayerList);
        assertEquals(1, sortedPlayerList.get(0).getRank());

    }

    @Test
    void test_get_all_player_data_sortBy_WINS() throws Exception {

        List<Player> playerList = mockUpdatePlayerData();
        Mockito.when(playerRepository.findAll()).thenReturn(playerList);
        List<Player> sortedPlayerList = playerDBHelper.getAll(EloConstant.WINS);

        assertNotNull(sortedPlayerList);
        assertEquals(2, sortedPlayerList.get(0).getNoOfWins());

    }

    @Test
    void test_save_player_data_throw_exception() throws Exception {

        File matches = new File("src/test/resources/matches.txt");
        FileInputStream matchesInputStream = new FileInputStream(matches);
        MultipartFile matchesMultipartFile = new MockMultipartFile(matches.getName(), matchesInputStream);

        List<Player> players = new ArrayList<>();
        players.add(new Player(0L, "Wesley"));
        players.add(new Player(1L, "Melodie"));
        players.add(new Player(2L, "Solange"));
        players.add(new Player(3L, "Johanne"));

        Mockito.when(playerRepository.findAll()).thenReturn(players);

        assertThrows(FileFormatException.class, () -> {
            playerDBHelper.savePlayerData(matchesMultipartFile);
        });


    }

    @Test
    void test_get_suggested_matches() throws Exception {

        List<Player> playerList = mockUpdatePlayerData();
        Mockito.when(playerRepository.findAll()).thenReturn(playerList);
        List<Matches> suggestedMatches = playerDBHelper.getSuggestedMatches();

        assertNotNull(suggestedMatches);
        assertEquals(4, suggestedMatches.size());

    }

    List<Player> mockUpdatePlayerData() throws Exception {

        File matches = new File("src/test/resources/matches.txt");
        FileInputStream matchesInputStream = new FileInputStream(matches);
        MultipartFile matchesMultipartFile = new MockMultipartFile(matches.getName(), matchesInputStream);

        List<Player> players = new ArrayList<>();
        players.add(new Player(0L, "Wesley"));
        players.add(new Player(1L, "Melodie"));
        players.add(new Player(2L, "Solange"));
        players.add(new Player(3L, "Johanne"));

        Mockito.when(playerRepository.findAll()).thenReturn(players);
        return playerDBHelper.updatePlayerData(matchesMultipartFile);
    }

}