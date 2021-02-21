package io.clroot.oreview.service;

import io.clroot.oreview.domain.Review;
import io.clroot.oreview.domain.ReviewSearch;
import io.clroot.oreview.domain.ReviewStatus;
import io.clroot.oreview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> findThatShouldBeDone() {
        return reviewRepository.findAll(ReviewSearch.shouldBeDone(LocalDate.now()));
    }

    @Transactional
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public void finishReview(Review review) {
        review.finishReview();
    }

    @Transactional
    public Review createNextReview(Review review) {
        ReviewStatus currentReviewStatus = review.getStatus();

        if (currentReviewStatus == ReviewStatus.LAST) {
            throw new RuntimeException("이미 복습을 모두 진행하였습니다.");
        }

        LocalDate nextDueDate = review.getDueDate().plusDays(currentReviewStatus.getNextDays());
        ReviewStatus nextReviewStatus = ReviewStatus.values()[currentReviewStatus.ordinal() + 1];

        Review next = Review.builder()
                .dueDate(nextDueDate)
                .status(nextReviewStatus)
                .build();
        reviewRepository.save(next);

        return next;
    }

}
