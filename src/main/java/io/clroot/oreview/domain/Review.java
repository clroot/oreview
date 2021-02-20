package io.clroot.oreview.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_id")
    private Learning learning;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

    private Boolean done = false;
    private LocalDate dueDate;
    private LocalDate finishedDate;

    @Builder
    public Review(Learning learning, LocalDate dueDate, ReviewStatus reviewStatus) {
        this.learning = learning;
        this.dueDate = dueDate;
        this.reviewStatus = reviewStatus;
    }

    public void finishReview() {
        if (done) {
            throw new RuntimeException("이미 완료된 복습입니다.");
        }
        done = true;
        finishedDate = LocalDate.now();
    }
}
