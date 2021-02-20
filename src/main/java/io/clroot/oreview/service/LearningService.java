package io.clroot.oreview.service;

import io.clroot.oreview.domain.Learning;
import io.clroot.oreview.repository.LearningRepository;
import io.clroot.oreview.web.dto.LearningSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LearningService {
    private final LearningRepository learningRepository;

    @Transactional
    public Learning save(LearningSaveRequestDto requestDto) {
        Learning learning = requestDto.toEntity();

        learningRepository.save(learning);
        return learning;
    }
}
