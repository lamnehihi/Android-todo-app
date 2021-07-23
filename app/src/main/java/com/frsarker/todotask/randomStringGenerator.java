package com.frsarker.todotask;
import java.util.UUID;

public class randomStringGenerator {

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return "uuid = " + uuid;
    }
}