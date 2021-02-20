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
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewRepository reviewRepository;

    @SuppressWarnings("NonAsciiCharacters")
    @Test
    public void 리뷰_완료_테스트() {
        //given
        Review review = Review.builder()
                .learning(null)
                .reviewStatus(ReviewStatus.FIRST)
                .dueDate(LocalDate.now().plusDays(ReviewStatus.FIRST.getNextDays()))
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
        LocalDate learningDate = LocalDate.now();
        Review first = Review.builder()
                .learning(null)
                .reviewStatus(ReviewStatus.FIRST)
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
}