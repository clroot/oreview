package io.clroot.oreview.repository;

import io.clroot.oreview.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
