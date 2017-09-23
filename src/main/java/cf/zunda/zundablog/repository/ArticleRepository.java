package cf.zunda.zundablog.repository;

import cf.zunda.zundablog.Entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
