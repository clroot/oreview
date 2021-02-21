package io.clroot.oreview.service;

import io.clroot.oreview.domain.Learning;
import io.clroot.oreview.repository.LearningRepository;
import io.clroot.oreview.web.dto.LearningSaveRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LearningServiceTest {

    @Autowired
    LearningService learningService;

    @Autowired
    LearningRepository learningRepository;

    @MockBean
    DateTimeProvider dateTimeProvider;

    @SpyBean
    AuditingHandler handler;

    LocalDateTime now = LocalDateTime.of(2021, 2, 21, 12, 0, 0);

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        handler.setDateTimeProvider(dateTimeProvider);
        when(dateTimeProvider.getNow()).thenReturn(Optional.of(now));
    }

    @SuppressWarnings("NonAsciiCharacters")
    @Test
    public void 러닝_저장_테스트() {
        //given
        String title = "테스트_러닝";
        learningService.save(LearningSaveRequestDto.builder().title(title).build());

        //when
        List<Learning> learningList = learningRepository.findAll();

        //then
        Learning learning = learningList.get(0);
        assertThat(learning.getTitle()).isEqualTo(title);
        assertThat(learning.getReviewList().size()).isGreaterThanOrEqualTo(1);
        assertThat(learning.getReviewList().get(0).getDueDate()).isAfter(now.toLocalDate());
    }

    @SuppressWarnings("NonAsciiCharacters")
    @Test
    public void 오늘_생성한_러닝_조회() {
        //given
        learningService.save(LearningSaveRequestDto.builder().title("테스트1").build());
        learningService.save(LearningSaveRequestDto.builder().title("테스트2").build());

        when(dateTimeProvider.getNow()).thenReturn(Optional.of(now.minusMonths(1)));
        learningService.save(LearningSaveRequestDto.builder().title("테스트3").build());

        //when
        List<Learning> learningList = learningService.findThatCreatedToday();

        //then
        assertThat(learningList.size()).isLessThan(3);
        for (Learning learning :
                learningList) {
            assertThat(learning.getCreatedAt()).isBetween(now.minusDays(1), now.plusDays(1));
        }
    }
}