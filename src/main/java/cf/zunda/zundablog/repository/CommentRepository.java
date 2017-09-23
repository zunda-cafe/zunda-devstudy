package cf.zunda.zundablog.repository;

import cf.zunda.zundablog.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByArticleId(Integer articleId);

    @Transactional
    void deleteByArticleId(Integer articleId);

}
