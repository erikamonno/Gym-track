package it.erika.gymtrack.dto;

import lombok.Data;

@Data
public class SubscriptionStatisticsDto {

    private Long active;
    private Long expiringSoon;
    private Long expired;
}
