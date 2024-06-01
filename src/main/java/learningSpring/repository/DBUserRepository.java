package learningSpring.repository;

import learningSpring.domain.Anime;
import learningSpring.domain.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DBUserRepository extends JpaRepository<DBUser, Long> {

    DBUser findByUsername(String username);

}
