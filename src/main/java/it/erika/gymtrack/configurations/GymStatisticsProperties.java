package it.erika.gymtrack.configurations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "statistics")
public record GymStatisticsProperties(@Valid @NotNull Certificates certificates, @Valid @NotNull Subscriptions subscriptions) {

    public record Certificates(@NotNull Duration expiringSoon) {}

    public record Subscriptions(@NotNull Duration expiringSoon) {}
}
