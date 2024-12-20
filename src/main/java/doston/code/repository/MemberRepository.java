package doston.code.repository;

import doston.code.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {


    boolean existsByEmailAndVisibleTrue(String email);

    boolean existsByPhoneAndVisibleTrue(String phone);

    List<Member> findAllByVisibleTrue();

    Optional<Member> findByIdAndVisibleTrue(Long memberId);

    @Modifying
    @Transactional
    @Query("update Member m set m.visible = false where m.id = :memberId")
    void changeVisibility(@Param("memberId") Long memberId);
}
