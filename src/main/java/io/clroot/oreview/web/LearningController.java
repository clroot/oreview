package io.clroot.oreview.web;

import io.clroot.oreview.service.LearningService;
import io.clroot.oreview.web.dto.LearningSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LearningController {
    private final LearningService learningService;

    @PostMapping(value = "/learning")
    public String processLearning(LearningSaveRequestDto requestDto, Errors errors) {
        log.debug("Processing learning: " + requestDto.getTitle());
        if (errors.hasErrors()) {
            return "redirect:/error";
        }
        learningService.save(requestDto);
        return "redirect:/";
    }
}
