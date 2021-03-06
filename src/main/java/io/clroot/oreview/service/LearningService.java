package io.clroot.oreview.service;

import io.clroot.oreview.domain.Learning;
import io.clroot.oreview.domain.Review;
import io.clroot.oreview.domain.ReviewStatus;
import io.clroot.oreview.repository.LearningRepository;
import io.clroot.oreview.web.dto.LearningSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LearningService {
    private final LearningRepository learningRepository;

    public List<Learning> findThatCreatedToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime from = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(today.plusDays(1), LocalTime.MIN);

        return learningRepository.findLearningByCreatedAtBetween(from, to);
    }

    @Transactional
    public Learning save(LearningSaveRequestDto requestDto) {
        Learning learning = learningRepository.save(requestDto.toEntity());

        Review review = Review.builder()
                .learning(learning)
                .status(ReviewStatus.FIRST)
                .dueDate(learning.getCreatedAt().toLocalDate().plusDays(1))
                .build();

        learning.addReview(review);

        return learning;
    }
}
