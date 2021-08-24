package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import recipes.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
