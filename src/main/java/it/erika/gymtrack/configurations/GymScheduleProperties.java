package it.erika.gymtrack.configurations;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated // ci permette di inserire le annotation che usiamo nei dto
@ConfigurationProperties(prefix = "gym") // prefisso di gym properties
public record GymScheduleProperties(@Valid @NotNull Schedule schedule) {

    public record Schedule(@NotNull LocalTime opening, @NotNull LocalTime closing) {}
}
