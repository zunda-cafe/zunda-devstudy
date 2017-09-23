package cf.zunda.zundablog.service;


import cf.zunda.zundablog.Entity.Comment;
import cf.zunda.zundablog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public void insert(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> findByArticleId(Integer articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    public void deleteByArticleId(Integer articleId) {
        commentRepository.deleteByArticleId(articleId);
    }
}