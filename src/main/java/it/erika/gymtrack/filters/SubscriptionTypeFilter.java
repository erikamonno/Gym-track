package it.erika.gymtrack.filters;

import lombok.Data;

@Data
public class SubscriptionTypeFilter {

    private String name;

    private Integer durationInDaysFrom;

    private Integer durationInDaysTo;
}
