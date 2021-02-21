package io.clroot.oreview.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Learning extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "learning_id")
    private Long id;

    private String title;

    @OneToMany(mappedBy = "learning", cascade = CascadeType.ALL)
    private final List<Review> reviewList = new ArrayList<>();

    @Builder
    public Learning(String title) {
        this.title = title;
    }

    public void addReview(Review review) {
        review.changeLearning(this);
        this.reviewList.add(review);
    }
}
