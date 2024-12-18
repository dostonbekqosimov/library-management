package doston.code.repository;

import doston.code.entity.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

    Optional<Profile> findByUsername(String username);

   List<Profile> findAllBy();

    Boolean existsByUsername(String userName);
}
