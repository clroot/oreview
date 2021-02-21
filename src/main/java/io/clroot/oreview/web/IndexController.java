package io.clroot.oreview.web;

import io.clroot.oreview.domain.Learning;
import io.clroot.oreview.domain.Review;
import io.clroot.oreview.service.LearningService;
import io.clroot.oreview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final LearningService learningService;
    private final ReviewService reviewService;

    @GetMapping("/")
    public String index(Model model) {
        List<Learning> learningList = learningService.findThatCreatedToday();
        List<Review> reviewList = reviewService.findThatShouldBeDone();

        model.addAttribute("learningList", learningList);
        model.addAttribute("reviewList", reviewList);

        return "index";
    }
}
