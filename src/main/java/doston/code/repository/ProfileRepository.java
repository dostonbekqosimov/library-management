package doston.code.repository;

import doston.code.entity.Profile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

    Optional<Profile> findByUsername(String username);

   List<Profile> findAllBy();

    Boolean existsByUsername(String userName);

    @Modifying
    @Transactional
    @Query("update Profile p set p.visible = false where p.id = :profileId")
    void changeVisibility(@Param("profileId") Long profileId);
}
