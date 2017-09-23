package cf.zunda.zundablog.service;

import cf.zunda.zundablog.Entity.Article;
import cf.zunda.zundablog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public List<Article> findAll() {
        return articleRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    public Article findOne(Integer id) {
        return articleRepository.findOne(id);
    }

    public void delete(Integer id) {
        articleRepository.delete(id);
    }

    public void insert(Article article) {
        articleRepository.save(article);
    }

    public void update(Article article) {
        articleRepository.save(article);
    }

}
