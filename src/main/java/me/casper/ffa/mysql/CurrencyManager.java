package me.casper.ffa.mysql;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class CurrencyManager {
    private final MySQL mysql;

    public CompletableFuture<Void> setCurrency(String uniqueId, int currency) {
        String query = "UPDATE `userdata` SET `exp` = ? WHERE `uniqueId` = ?";
        return mysql.update(query, currency, uniqueId).thenAccept(result -> {});
    }

    public CompletableFuture<Void> addCurrency(String uniqueId, int amount) {
        String query = "UPDATE `userdata` SET `exp` = `exp` + ? WHERE `uniqueId` = ?";
        return mysql.update(query, amount, uniqueId).thenAccept(result -> {});
    }

    public CompletableFuture<Boolean> isExisting(String uniqueId) {
        String query = "SELECT * FROM `userdata` WHERE `uniqueId` = ? LIMIT 1";
        return mysql.getResult(query, uniqueId)
                .thenApply(result -> result.size() > 0);
    }

    public CompletableFuture<Void> createUser(String uniqueId) {
        return isExisting(uniqueId).thenCompose(existing -> {
            if (existing) {
                return CompletableFuture.completedFuture(null);
            }
            String query = "INSERT INTO `userdata` (uniqueId, money, exp) VALUES (?, ?, ?)";
            return mysql.update(query, uniqueId, 0, 0).thenAccept(result -> {});
        });
    }

    public CompletableFuture<Integer> getCurrency(String uniqueId) {
        String query = "SELECT `exp` FROM `userdata` WHERE `uniqueId` = ?";
        return mysql.getResult(query, uniqueId).thenApply(result -> {
            if (result.size() > 0) {
                return result.get(0).get("exp").getAsInt();
            }
            return null;
        });
    }
}

