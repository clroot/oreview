package io.clroot.oreview.web;

import io.clroot.oreview.domain.Learning;
import io.clroot.oreview.domain.Review;
import io.clroot.oreview.repository.LearningRepository;
import io.clroot.oreview.repository.ReviewRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LearningControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired
    private LearningRepository learningRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @After
    public void tearDown() {
        learningRepository.deleteAll();
    }

    @SuppressWarnings("NonAsciiCharacters")
    @Test
    @Transactional
    public void 러닝_저장_테스트() throws Exception {
        //given
        String title = "테스트1";
        String url = "http://localhost:" + port + "/learning";

        //when
        mvc.perform(
                post(url)
                        .param("title", title))
                .andExpect(status().is3xxRedirection());

        //then
        List<Learning> allLearning = learningRepository.findAll();
        Learning learning = allLearning.get(0);
        assertThat(learning.getTitle()).isEqualTo(title);
        assertThat(learning.getCreateAt()).isBefore(LocalDateTime.now());

        List<Review> reviewList = reviewRepository.findAll();
        Review review = reviewList.get(0);
        assertThat(review.getLearning()).isEqualTo(learning);
    }
}