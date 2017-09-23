package cf.zunda.zundablog.repository;

import cf.zunda.zundablog.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserId(String userId);
}
