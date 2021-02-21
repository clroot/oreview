package io.clroot.oreview.repository;

import io.clroot.oreview.domain.Learning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LearningRepository extends JpaRepository<Learning, Long> {
    List<Learning> findLearningByCreateAtBetween(LocalDateTime from, LocalDateTime to);
}
