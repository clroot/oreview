package io.clroot.oreview.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Learning {

  @Id
  @GeneratedValue
  @Column(name = "learning_id")
  private Long id;

  private String title;

  private LocalDate createAt;

  @OneToMany(mappedBy = "learning", cascade = CascadeType.ALL)
  private List<Review> reviewList = new ArrayList<>();

  @Builder
  public Learning(String title) {
    this.title = title;
    this.createAt = LocalDate.now();
  }

  public Review createFirstReview() {
    Review firstReview = Review.builder()
        .learning(this)
        .dueDate(LocalDate.now().plusDays(ReviewStatus.FIRST.getNextDays()))
        .reviewStatus(ReviewStatus.FIRST)
        .build();

    reviewList.add(firstReview);

    return firstReview;
  }
}
