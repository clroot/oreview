package io.clroot.oreview.web.dto;

import io.clroot.oreview.domain.Learning;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LearningSaveRequestDto {

    private String title;

    @Builder
    public LearningSaveRequestDto(String title) {
        this.title = title;
    }

    public Learning toEntity() {
        return Learning.builder()
                .title(title)
                .build();
    }
}
