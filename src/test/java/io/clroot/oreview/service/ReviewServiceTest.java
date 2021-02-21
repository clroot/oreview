package io.clroot.oreview.service;

import io.clroot.oreview.domain.Review;
import io.clroot.oreview.domain.ReviewStatus;
import io.clroot.oreview.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewRepository reviewRepository;

    LocalDateTime now = LocalDateTime.of(2021, 2, 21, 12, 0, 0);

    @SuppressWarnings("NonAsciiCharacters")
    @Test
    public void 리뷰_저장_테스트() {
        //given
        LocalDate dueDate = now.toLocalDate().plusDays(ReviewStatus.FIRST.getNextDays());
        reviewService.save(Review.builder()
                .learning(null)
                .status(ReviewStatus.FIRST)
                .dueDate(dueDate)
                .build());

        //when
        List<Review> reviewList = reviewRepository.findAll();

        //then
        Review review = reviewList.get(0);
        assertThat(review.getDone()).isFalse();
        assertThat(review.getDueDate()).isEqualTo(dueDate);
        assertThat(review.getLearning()).isNull();
        assertThat(review.getStatus()).isEqualTo(ReviewStatus.FIRST);
    }

    @SuppressWarnings("NonAsciiCharacters")
    @Test
    public void 리뷰_완료_테스트() {
        //given
        Review review = Review.builder()
                .learning(null)
                .status(ReviewStatus.FIRST)
                .dueDate(now.toLocalDate().plusDays(ReviewStatus.FIRST.getNextDays()))
                .build();

        //when
        reviewService.finishReview(review);

        //then
        assertThat(review.getDone()).isTrue();
        assertThat(review.getFinishedDate()).isNotNull();
    }

    @SuppressWarnings("NonAsciiCharacters")
    @Test
    public void 다음_리뷰_생성_테스트() {
        //given
        LocalDate learningDate = now.toLocalDate();
        Review first = Review.builder()
                .learning(null)
                .status(ReviewStatus.FIRST)
                .dueDate(learningDate.plusDays(1))
                .build();

        //when
        Review second = reviewService.createNextReview(first);
        Review third = reviewService.createNextReview(second);
        Review fourth = reviewService.createNextReview(third);
        Review last = reviewService.createNextReview(fourth);

        //then
        assertThat(ChronoUnit.DAYS.between(learningDate, first.getDueDate())).isEqualTo(1);
        assertThat(ChronoUnit.DAYS.between(learningDate, second.getDueDate())).isEqualTo(3);
        assertThat(ChronoUnit.DAYS.between(learningDate, third.getDueDate())).isEqualTo(6);
        assertThat(ChronoUnit.DAYS.between(learningDate, fourth.getDueDate())).isEqualTo(13);
        assertThat(ChronoUnit.DAYS.between(learningDate, last.getDueDate())).isEqualTo(29);
    }

    @SuppressWarnings("NonAsciiCharacters")
    @Test
    public void 이행해야할_리뷰_조회_테스트() {
        //given
        reviewService.save(Review.builder()
                .learning(null)
                .status(ReviewStatus.FIRST)
                .dueDate(now.toLocalDate().minusDays(1)).build());
        reviewService.save(Review.builder()
                .learning(null)
                .status(ReviewStatus.FIRST)
                .dueDate(now.toLocalDate()).build());
        reviewService.save(Review.builder()
                .learning(null)
                .status(ReviewStatus.FIRST)
                .dueDate(now.toLocalDate().plusDays((1))).build());

        //when
        List<Review> reviewList = reviewService.findThatShouldBeDone();

        //then
        for (Review review : reviewList) {
            assertThat(review.getDueDate()).isBeforeOrEqualTo(now.toLocalDate());
            assertThat(review.getDone()).isFalse();
        }
    }
}