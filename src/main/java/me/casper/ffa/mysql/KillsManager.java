package me.casper.ffa.mysql;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class KillsManager {

    private final MySQL mysql;

    public CompletableFuture<Integer> getKills(String uniqueId) {
        String query = "SELECT `kills` FROM `userdata` WHERE `uniqueId` = ?";
        return mysql.getResult(query, uniqueId).thenApply(result -> {
            if (result.size() > 0) {
                return result.get(0).get("kills").getAsInt();
            }
            return null;
        });
    }

    public CompletableFuture<Void> addKills(String uniqueId, int amount) {
        String query = "UPDATE `userdata` SET `kills` = `kills` + ? WHERE `uniqueId` = ?";
        return mysql.update(query, amount, uniqueId).thenAccept(result -> {
        });
    }

}
