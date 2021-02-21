package io.clroot.oreview.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Learning {
    @Id
    @GeneratedValue
    @Column(name = "learning_id")
    private Long id;

    private String title;

    @OneToMany(mappedBy = "learning", cascade = CascadeType.ALL)
    private final List<Review> reviewList = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createAt;

    @Builder
    public Learning(String title) {
        this.title = title;
    }

    public void addReview(Review review) {
        review.changeLearning(this);
        this.reviewList.add(review);
    }
}
