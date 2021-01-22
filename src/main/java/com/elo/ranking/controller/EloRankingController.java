package com.elo.ranking.controller;

import com.elo.ranking.entity.ResponseMessage;
import com.elo.ranking.service.EloRankingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/elo")
@Api(value = "Elo Ranking Algorithm", description = "Implemented elo ranking API program using the Elo ranking algorithm.")
public class EloRankingController {

    @Autowired
    private EloRankingService eloRankingService;


    @ApiOperation(value = "Upload player and match data in text file format")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("names") MultipartFile names, @RequestParam("matches") MultipartFile matches) {
        String message = "";
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eloRankingService.fileReadAndUploadToDB(names, matches));
        } catch (Exception e) {
            message = "Could not upload the file: " + names.getOriginalFilename() + " and " + matches.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @ApiOperation(value = "Get ALL Player Info")
    @GetMapping("/getAllPlayerInfo")
    public ResponseEntity<?> getPlayerInfo(@RequestParam(value = "sortBy", required = false) String sortBy) {
        String message = "";
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eloRankingService.getAll(sortBy));
        } catch (Exception e) {
            message = "Something wrong happened!! ";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @ApiOperation(value = "Get Individual Player Info")
    @GetMapping("/playerInfo")
    public ResponseEntity<?> getPlayerInfo(@RequestParam(value = "playerId", required = true) Long playerId) {
        String message = "";
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eloRankingService.getPlayerInfo(playerId));
        } catch (Exception e) {
            message = "Player not found!! ";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @ApiOperation(value = "Suggested Next Matches")
    @GetMapping("/suggestedMatches")
    public ResponseEntity<?> getSuggestedMatches() {
        String message = "";
        try {
            return ResponseEntity.status(HttpStatus.OK).body(eloRankingService.getSuggestedNextMatches());
        } catch (Exception e) {
            message = "Something went wrong!! ";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

}
