package cf.zunda.zundablog.controller;

import cf.zunda.zundablog.Entity.Article;
import cf.zunda.zundablog.Entity.Comment;
import cf.zunda.zundablog.service.ArticleService;
import cf.zunda.zundablog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ArticleDeleteController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    // 記事削除確認画面から記事一覧画面への遷移
    @RequestMapping(value = "/article_delete_confirm/{articleId}/", params = {"param=delete"})
    public ModelAndView articleDeleteConfirmWithArticleIdModeDelete(ModelAndView mav, @PathVariable("articleId") String articleId) {

        articleService.delete(Integer.valueOf(articleId));
        commentService.deleteByArticleId(Integer.valueOf(articleId));
        List<Article> articles = articleService.findAll();
        mav.addObject("articles", articles);
        mav.addObject("pageTitle", "記事一覧");
        mav.setViewName("article_list");
        return mav;
    }

    // 記事削除確認画面から記事詳細画面への遷移
    @RequestMapping(value = "/article_delete_confirm/{articleId}/", params = {"param=return"})
    public ModelAndView articleDeleteConfirmWithArticleIdModeReturn(ModelAndView mav, @PathVariable("articleId") String articleId) {


        Article article = articleService.findOne(Integer.valueOf(articleId));
        mav.addObject("article", article);
        List<Comment> comments = commentService.findByArticleId(Integer.valueOf(articleId));
        mav.addObject("newComment", new Comment());
        mav.addObject("comments", comments);
        mav.addObject("pageTitle", "記事詳細");
        mav.setViewName("article_detail");
        return mav;
    }
}
