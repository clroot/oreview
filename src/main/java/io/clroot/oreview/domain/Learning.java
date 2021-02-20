package io.clroot.oreview.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  private LocalDateTime createAt;

  @OneToMany(mappedBy = "learning", cascade = CascadeType.ALL)
  private List<Review> reviewList = new ArrayList<>();

  @Builder
  public Learning(String title) {
    this.title = title;
    this.createAt = LocalDateTime.now();
  }

  public void addReview(Review review) {
    review.changeLearning(this);
    this.reviewList.add(review);
  }
}
