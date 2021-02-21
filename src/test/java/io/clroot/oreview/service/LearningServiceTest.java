package io.clroot.oreview.service;

import io.clroot.oreview.domain.Learning;
import io.clroot.oreview.repository.LearningRepository;
import io.clroot.oreview.web.dto.LearningSaveRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LearningServiceTest {

    @Autowired
    LearningService learningService;

    @Autowired
    LearningRepository learningRepository;

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
    }

    @SuppressWarnings("NonAsciiCharacters")
    @Test
    public void 오늘_생성한_러닝_조회() {
        //given
        learningService.save(LearningSaveRequestDto.builder().title("테스트1").build());
        learningService.save(LearningSaveRequestDto.builder().title("테스트2").build());
        learningService.save(LearningSaveRequestDto.builder().title("테스트3").build());

        //when
        List<Learning> learningList = learningService.findThatCreatedToday();

        //then
        for (Learning learning :
                learningList) {
            assertThat(learning.getCreatedAt()).isBetween(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        }
    }
}