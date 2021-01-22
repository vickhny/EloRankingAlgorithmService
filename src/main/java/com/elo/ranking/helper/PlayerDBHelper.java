package com.elo.ranking.helper;

import com.elo.ranking.dto.Matches;
import com.elo.ranking.entity.Player;
import com.elo.ranking.exceptionHandler.FileFormatException;
import com.elo.ranking.repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class PlayerDBHelper {

    @Autowired
    private PlayerRepository playerRepository;

    public void savePlayerData(MultipartFile names) throws IOException {

        InputStream nameStream = names.getInputStream();

        Scanner sc = new Scanner(nameStream);

        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (!line.trim().equalsIgnoreCase("")) {
                String player[] = line.trim().replaceAll("\\s+", " ").split(" ");
                if (player.length == 2 && !player[1].matches("[0-9]+")) {
                    playerRepository.save(new Player(Long.parseLong(player[0]), player[1]));
                } else {
                    throw new FileFormatException("Invalid input file!!");
                }
            }
        }
    }

    public List<Player> updatePlayerData(MultipartFile matches) throws IOException {

        Map<Long, Player> playerMap = playerRepository.findAll().stream().collect(Collectors.toMap(Player::getPlayerId, player -> player));

        InputStream nameStream = matches.getInputStream();
        Scanner sc = new Scanner(nameStream);
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (!line.trim().equalsIgnoreCase("")) {
                String participants[] = line.trim().replaceAll("\\s+", " ").split(" ");
                if (participants.length == 2) {

                    Player winner = playerMap.get(Long.parseLong(participants[0].trim()));
                    Player lost = playerMap.get(Long.parseLong(participants[1].trim()));
                    if (!ObjectUtils.isEmpty(winner) && !ObjectUtils.isEmpty(lost)) {
                        winner.playerWon(lost.getScore());
                        winner.wonAgainst(lost.getPlayerId());
                        lost.playerLost(winner.getScore());
                        lost.lostAgainst(winner.getPlayerId());
                    }
                }
            }
        }

        List<Player> playerList = playerMap.values().stream().sorted(Comparator.comparing(Player::getScore).reversed()).collect(Collectors.toCollection(LinkedList::new));
        playerList.forEach(player -> player.setRank(playerList.indexOf(player) + 1));
        return playerList.stream().sorted(Comparator.comparing(Player::getPlayerId)).collect(Collectors.toList());
    }

    public List<Player> getAll(String sortBy) {
        if (StringUtils.hasLength(sortBy)) {
            switch (sortBy) {
                case EloConstant.ID:
                    return playerRepository.findAll().stream().sorted(Comparator.comparing(Player::getPlayerId)).collect(Collectors.toList());
                case EloConstant.NAME:
                    return playerRepository.findAll().stream().sorted(Comparator.comparing(Player::getName)).collect(Collectors.toList());
                case EloConstant.WINS:
                    return playerRepository.findAll().stream().sorted(Comparator.comparing(Player::getNoOfWins).reversed().thenComparing(Player::getNoOfLost).thenComparing(Player::getScore)).collect(Collectors.toList());
                case EloConstant.LOSES:
                    return playerRepository.findAll().stream().sorted(Comparator.comparing(Player::getNoOfLost).reversed()).collect(Collectors.toList());
                case EloConstant.RANK:
                    return playerRepository.findAll().stream().sorted(Comparator.comparing(Player::getRank)).collect(Collectors.toList());
                case EloConstant.SCORE:
                    return playerRepository.findAll().stream().sorted(Comparator.comparing(Player::getScore).reversed()).collect(Collectors.toList());
            }
        }
        return playerRepository.findAll().stream().sorted(Comparator.comparing(Player::getPlayerId)).collect(Collectors.toList());
    }

    public Player getPlayerInfo(Long playerId) {
        return playerRepository.findById(playerId).orElseThrow();
    }

    public List<Matches> getSuggestedMatches() {

        List<Matches> matchesList = new ArrayList<>();

        List<Player> playerList = playerRepository.findAll();
        playerList.forEach(player -> {
            List<Long> playedWith = new LinkedList<>();
            playedWith.addAll(player.getWonAgainst());
            playedWith.addAll(player.getLostAgainst());
            playerList.forEach(players -> {
                        if (players != player && !playedWith.contains(players.getPlayerId())) {
                            matchesList.add(new Matches(players.getName() + "(" + players.getPlayerId() + ", " + players.getScore() + ") vs. " + player.getName() + "(" + player.getPlayerId() + ", " + player.getScore() + ")"));
                        }
                    }
            );
        });

        return matchesList;
    }
}
