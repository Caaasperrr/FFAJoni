package me.casper.ffa.mysql;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class DeathsManager {

    private final MySQL mysql;

    public CompletableFuture<Integer> getDeaths(String uniqueId) {
        String query = "SELECT `deaths` FROM `userdata` WHERE `uniqueId` = ?";
        return mysql.getResult(query, uniqueId).thenApply(result -> {
            if (result.size() > 0) {
                return result.get(0).get("deaths").getAsInt();
            }
            return null;
        });
    }

    public CompletableFuture<Void> addDeaths(String uniqueId, int amount) {
        String query = "UPDATE `userdata` SET `deaths` = `deaths` + ? WHERE `uniqueId` = ?";
        return mysql.update(query, amount, uniqueId).thenAccept(result -> {
        });
    }

}
