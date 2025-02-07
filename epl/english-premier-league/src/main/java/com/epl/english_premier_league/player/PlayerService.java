package com.epl.english_premier_league.player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public List<Player> getPlayersFromTeam(String teamName) {
        return playerRepository.findAll().stream()
                .filter(player -> teamName.equals(player.getTeamName()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByName(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayerByPosition(String searchText) {
        return playerRepository.findAll().stream()
                .filter(player -> player.getPosition().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPosition(String teamName, String position) {
        return playerRepository.findAll().stream()
                .filter(player -> teamName.equals(player.getTeamName()) && position.equals(player.getPosition()))
                .collect(Collectors.toList());
    }

    public Player addPlayer(Player player) {
        playerRepository.save(player);
        return player;
    }

    public Player updatePlayer(Player updatedPlayer) {
        Optional<Player> optionalPlayer = playerRepository.findByName(updatedPlayer.getName());
        if (optionalPlayer.isPresent()) {
            Player playerToUpdate = optionalPlayer.get();
            playerToUpdate.setName(updatedPlayer.getName());
            playerToUpdate.setTeamName(updatedPlayer.getTeamName());
            playerToUpdate.setPosition(updatedPlayer.getPosition());
            playerToUpdate.setTeamName(updatedPlayer.getTeamName());
            playerToUpdate.setGoals(updatedPlayer.getGoals());
            playerToUpdate.setMinutesPlayed(updatedPlayer.getMinutesPlayed());
            playerToUpdate.setInterceptions(updatedPlayer.getInterceptions());
            //save player
            playerRepository.save(updatedPlayer);
            return updatedPlayer;
        }
        return null;
    }
    @Transactional
    public void removePlayer(String playerName) {
        playerRepository.deleteByName(playerName);
    }
}
