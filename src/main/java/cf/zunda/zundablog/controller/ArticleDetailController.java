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
import java.sql.Timestamp;
import java.util.List;

@Controller
public class ArticleDetailController {
    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;


    // 記事詳細画面から記事詳細画面への遷移(コメント追記)
    @RequestMapping(value = "/article_detail/{articleId}", params = {"param=comment"})
    public ModelAndView articleDetailWithArticleIdModeComment(ModelAndView mav, @Valid @ModelAttribute("newComment") Comment newComment, BindingResult bindingResult, @PathVariable("articleId") String articleId) {

        if (!bindingResult.hasErrors()) {
            Comment insertComment = new Comment();
            insertComment.setArticleId(Integer.valueOf(articleId));
            insertComment.setComment(newComment.getComment());
            insertComment.setCreateTime(new Timestamp(System.currentTimeMillis()));
            commentService.insert(insertComment);
            mav.addObject("newComment", new Comment());
        }
        List<Comment> comments = commentService.findByArticleId(Integer.valueOf(articleId));
        mav.addObject("comments", comments);
        mav.addObject("pageTitle", "記事詳細");
        mav.setViewName("article_detail");

        Article article = articleService.findOne(Integer.valueOf(articleId));
        mav.addObject("article", article);

        return mav;
    }

    // 記事詳細画面から記事修正画面への遷移
    @RequestMapping(value = "/article_detail/{articleId}/", params = {"param=modify"})
    public ModelAndView articleDetailWithArticleIdModeModify(ModelAndView mav, @PathVariable("articleId") String articleId) {

        mav.addObject("pageTitle", "記事修正");
        mav.setViewName("article_modify");
        Article article = articleService.findOne(Integer.valueOf(articleId));
        mav.addObject("article", article);
        return mav;
    }

    // 記事詳細画面から記事削除画面への遷移
    @RequestMapping(value = "/article_detail/{articleId}/", params = {"param=delete"})
    public ModelAndView articleDetailWithArticleIdModeDelete(ModelAndView mav, @PathVariable("articleId") String articleId) {

        mav.addObject("pageTitle", "記事削除確認");
        mav.setViewName("article_delete_confirm");
        Article article = articleService.findOne(Integer.valueOf(articleId));
        mav.addObject("article", article);

        return mav;
    }

    // 記事詳細画面から記事一覧画面への遷移
    @RequestMapping(value = "/article_detail/{articleId}", params = {"param=return"})
    public ModelAndView articleDetailWithArticleIdModeReturn(ModelAndView mav, @PathVariable("articleId") String articleId) {

        List<Article> articles = articleService.findAll();
        mav.addObject("articles", articles);
        mav.addObject("pageTitle", "記事一覧");
        mav.setViewName("article_list");
        Article article = articleService.findOne(Integer.valueOf(articleId));
        mav.addObject("article", article);
        return mav;
    }
}
