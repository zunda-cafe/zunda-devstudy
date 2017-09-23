package cf.zunda.zundablog.controller;

import cf.zunda.zundablog.Entity.Article;
import cf.zunda.zundablog.Entity.Comment;
import cf.zunda.zundablog.service.ArticleService;
import cf.zunda.zundablog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ArticleListController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    // 記事一覧画面から記事投稿画面への遷移
    @RequestMapping(value = "/article_list/")
    public ModelAndView articleList(ModelAndView mav) {
        mav.addObject("article", new Article());
        mav.addObject("pageTitle", "記事投稿");
        mav.setViewName("article_contribution");
        return mav;
    }

    // 記事一覧画面から記事詳細画面への遷移
    @RequestMapping(value = "/article_list/{articleId}/")
    public ModelAndView articleListWithArticleId(ModelAndView mav, @PathVariable("articleId") String articleId) {

        Article article = articleService.findOne(Integer.valueOf(articleId));
        mav.addObject("article", article);
        List<Comment> comments = commentService.findByArticleId(Integer.valueOf(articleId));
        mav.addObject("comments", comments);
        mav.addObject("newComment", new Comment());
        mav.addObject("pageTitle", "記事詳細");
        mav.setViewName("article_detail");
        return mav;
    }
}