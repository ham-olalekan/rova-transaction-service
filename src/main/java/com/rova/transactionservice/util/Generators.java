package com.rova.transactionservice.util;

import java.util.UUID;

public class Generators {

    public static String generateUniqueAccountNumbers(){
        UUID uuid = UUID.randomUUID();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        long accountNumber = Math.abs(leastSignificantBits) % 1_000_000_000L;

        // Pad the account number with leading zeros if necessary
        return String.format("%010d", accountNumber);
    }
}
