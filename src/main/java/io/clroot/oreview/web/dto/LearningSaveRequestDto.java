package io.clroot.oreview.web.dto;

import io.clroot.oreview.domain.Learning;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LearningSaveRequestDto {

    private final String title;

    public Learning toEntity() {
        return Learning.builder()
                .title(title)
                .build();
    }
}
