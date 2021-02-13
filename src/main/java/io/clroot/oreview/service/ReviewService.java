package io.clroot.oreview.service;

import io.clroot.oreview.domain.Review;
import io.clroot.oreview.domain.ReviewStatus;
import io.clroot.oreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Review createNextReview(Review review) {
        ReviewStatus currentReviewStatus = review.getReviewStatus();

        if (currentReviewStatus == ReviewStatus.LAST) {
            throw new RuntimeException("이미 복습을 모두 진행하였습니다.");
        }

        LocalDate nextDueDate = review.getDueDate().plusDays(currentReviewStatus.getNextDays());
        ReviewStatus nextReviewStatus = ReviewStatus.values()[currentReviewStatus.ordinal() + 1];

        Review next = Review.builder()
                .dueDate(nextDueDate)
                .reviewStatus(nextReviewStatus)
                .build();
        reviewRepository.save(next);

        return next;
    }
}
