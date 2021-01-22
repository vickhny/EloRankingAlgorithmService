package com.elo.ranking.service;

import com.elo.ranking.dto.Matches;
import com.elo.ranking.dto.PlayerDTO;
import com.elo.ranking.entity.Player;
import com.elo.ranking.exceptionHandler.FileFormatException;
import com.elo.ranking.exceptionHandler.NoSuchElementException;
import com.elo.ranking.helper.PlayerDBHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class EloRankingServiceImpl implements EloRankingService {


    @Autowired
    private PlayerDBHelper playerDBHelper;

    @Transactional
    public List<PlayerDTO> fileReadAndUploadToDB(MultipartFile names, MultipartFile matches) {
        try {
            playerDBHelper.savePlayerData(names);
            return playerDBHelper.updatePlayerData(matches).stream().map(player -> new PlayerDTO(player.getPlayerId(), player.getName(), player.getNoOfWins(), player.getNoOfLost(), player.getScore(), player.getRank())).collect(Collectors.toCollection(LinkedList::new));
        } catch (Exception ex) {
            throw new FileFormatException("Invalid file format!! ");
        }
    }

    public List<PlayerDTO> getAll(String sortBy) {

        return playerDBHelper.getAll(sortBy).stream().map(player -> new PlayerDTO(player.getPlayerId(), player.getName(), player.getNoOfWins(), player.getNoOfLost(), player.getScore(), player.getRank())).collect(Collectors.toCollection(LinkedList::new));
    }


    public Player getPlayerInfo(Long playerId) {
        try {
            return playerDBHelper.getPlayerInfo(playerId);
        } catch (Exception ex) {
            throw new NoSuchElementException(playerId);
        }
    }

    public List<Matches> getSuggestedNextMatches() {
        return playerDBHelper.getSuggestedMatches();
    }
}
