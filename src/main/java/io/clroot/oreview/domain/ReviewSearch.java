package io.clroot.oreview.domain;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ReviewSearch {
    public static Specification<Review> shouldBeDone(LocalDate dueDate) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.lessThanOrEqualTo(root.get("dueDate"), dueDate),
                criteriaBuilder.isFalse(root.get("done"))
        ));
    }
}
