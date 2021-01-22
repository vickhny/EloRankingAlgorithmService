package com.elo.ranking.service;

import com.elo.ranking.dto.Matches;
import com.elo.ranking.dto.PlayerDTO;
import com.elo.ranking.entity.Player;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EloRankingService {

    List<PlayerDTO> fileReadAndUploadToDB(MultipartFile names, MultipartFile matches);

    List<PlayerDTO> getAll(String sortBy);

    Player getPlayerInfo(Long playerId);

    List<Matches> getSuggestedNextMatches();


}
