package io.clroot.oreview.domain;

import lombok.Getter;

@Getter
public enum ReviewStatus {
    FIRST(2),
    SECOND(3),
    THIRD(7),
    FOURTH(16),
    LAST(null);

    private final Integer nextDays;

    ReviewStatus(Integer nextDays) {
        this.nextDays = nextDays;
    }
}
