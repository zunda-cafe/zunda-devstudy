package cf.zunda.zundablog.controller;

import cf.zunda.zundablog.Entity.Article;
import cf.zunda.zundablog.Entity.Comment;
import cf.zunda.zundablog.service.ArticleService;
import cf.zunda.zundablog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ArticleModifyController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    // 記事修正画面から記事修正確認画面への遷移
    @RequestMapping(value = "/article_modify/{articleId}", params = {"param=modify"})
    public ModelAndView articleModifyWithArticleIdModeModify(ModelAndView mav, @Valid @ModelAttribute("article") Article article, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            mav.addObject("pageTitle", "記事修正");
            mav.setViewName("article_modify");
            return mav;
        }
        mav.addObject("article", article);
        mav.addObject("pageTitle", "記事修正確認");
        mav.setViewName("article_modify_confirm");
        return mav;
    }

    // 記事修正画面から記事詳細画面への遷移
    @RequestMapping(value = "/article_modify/{articleId}/", params = {"param=return"})
    public ModelAndView articleModifyWithArticleIdModeReturn(ModelAndView mav, @PathVariable("articleId") String articleId) {

        //画面で変更されたものではなく、DBにある古い記事を復元
        Article oldArticle = articleService.findOne(Integer.valueOf(articleId));
        List<Comment> comments = commentService.findByArticleId(Integer.valueOf(articleId));
        mav.addObject("newComment", new Comment());
        mav.addObject("comments", comments);
        mav.addObject("article", oldArticle);
        mav.addObject("pageTitle", "記事詳細");
        mav.setViewName("article_detail");
        return mav;
    }

    // 記事修正確認画面から記事一覧画面への遷移
    @RequestMapping(value = "/article_modify_confirm/{articleId}", params = {"param=modify"})
    public ModelAndView articleModifyConrirmWithArticleIdModeModify(ModelAndView mav, @ModelAttribute("article") Article article, @PathVariable("articleId") String articleId) {

        Article oldArticle = articleService.findOne(Integer.valueOf(articleId));
        oldArticle.setTitle(article.getTitle());
        oldArticle.setBody(article.getBody());
        articleService.update(oldArticle);
        List<Article> articles = articleService.findAll();
        mav.addObject("articles", articles);
        mav.addObject("pageTitle", "記事一覧");
        mav.setViewName("article_list");
        return mav;
    }

    // 記事修正確認画面から記事修正画面への遷移
    @RequestMapping(value = "/article_modify_confirm/{articleId}", params = {"param=return"})
    public ModelAndView articleModifyConfirmWithArticleIdModeReturn(ModelAndView mav, @ModelAttribute("article") Article article) {

        mav.addObject("article", article);
        mav.addObject("pageTitle", "記事修正");
        mav.setViewName("article_modify");
        return mav;
    }
}
