package it.erika.gymtrack;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {
        Instant startDate = LocalDateTime.of(2025, 1, 10, 12, 0).toInstant(ZoneOffset.UTC);
        Instant endDate = LocalDateTime.of(2025, 1, 10, 18, 0).toInstant(ZoneOffset.UTC);
        System.out.println(Duration.between(startDate, endDate));
        System.out.println(Instant.now());
    }
}