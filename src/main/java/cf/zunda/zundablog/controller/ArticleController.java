package cf.zunda.zundablog.controller;

import cf.zunda.zundablog.Entity.Article;
import cf.zunda.zundablog.Entity.Comment;
import cf.zunda.zundablog.Entity.User;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @Autowired
    HttpSession session;

    // ログイン画面から記事一覧画面への遷移
    @RequestMapping(value = "/article/list", method= RequestMethod.GET)
    public ModelAndView list(ModelAndView mav) {
        List<Article> articles = articleService.findAll();
        mav.addObject("articles", articles);
        mav.addObject("pageTitle", "記事投稿");
        mav.setViewName("article/list");
        return mav;
    }

    // 記事一覧画面から記事投稿画面への遷移
    @RequestMapping(value = "/article/create", params = "form", method= RequestMethod.GET)
    public ModelAndView createForm(ModelAndView mav) {
        mav.addObject("article", new Article());
        mav.addObject("pageTitle", "記事投稿");
        mav.setViewName("article/create");
        return mav;
    }

    // 記事投稿画面から記事投稿確認画面への遷移
    @RequestMapping(value = "/article/create", params = "confirm", method= RequestMethod.POST)
    public ModelAndView createConfim(ModelAndView mav, @Valid @ModelAttribute("article") Article article, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            mav.addObject("pageTitle", "記事投稿");
            mav.setViewName("article/create");
            return mav;
        }
        mav.addObject("article", article);
        mav.addObject("pageTitle", "記事投稿確認");
        mav.setViewName("article/create_confirm");
        return mav;
    }

        //記事投稿確認画面から記事一覧画面への遷移
    @RequestMapping(value = "/article/create", params = "redo", method= RequestMethod.POST)
    public ModelAndView createRedo(ModelAndView mav, @ModelAttribute("article") Article article) {
        mav.addObject("article", article);
        mav.addObject("pageTitle", "記事投稿");
        mav.setViewName("article/create");
        return mav;
    }

    // 記事投稿確認画面から記事一覧画面への遷移
    @RequestMapping(value = "/article/create", method= RequestMethod.POST)
    public ModelAndView create(ModelAndView mav, @ModelAttribute("article") Article article) {

        Article newArticle = new Article();
        newArticle.setTitle(article.getTitle());
        newArticle.setBody(article.getBody());

        User loginUser = (User) session.getAttribute("user");
        newArticle.setUserId(Integer.valueOf(loginUser.getId()));
        newArticle.setCreateTime(new Timestamp(System.currentTimeMillis()));

        articleService.insert(newArticle);

        List<Article> articles = articleService.findAll();
        mav.addObject("articles", articles);
        mav.addObject("pageTitle", "記事一覧");
        mav.setViewName("article/list");

        return mav;
    }

    // 記事一覧画面から記事詳細画面への遷移
    @RequestMapping(value = "/article/{articleId}", method= RequestMethod.GET)
    public ModelAndView read(ModelAndView mav, @PathVariable("articleId") String articleId) {

        Article article = articleService.findOne(Integer.valueOf(articleId));
        mav.addObject("article", article);
        List<Comment> comments = commentService.findByArticleId(Integer.valueOf(articleId));
        mav.addObject("comments", comments);
        mav.addObject("newComment", new Comment());
        mav.addObject("pageTitle", "記事詳細");
        mav.setViewName("article/detail");
        return mav;
    }

    // 記事詳細画面から記事詳細画面への遷移(コメント追記)
    @RequestMapping(value = "/article/{articleId}", method= RequestMethod.POST)
    public ModelAndView comment(ModelAndView mav, @Valid @ModelAttribute("newComment") Comment newComment, BindingResult bindingResult, @PathVariable("articleId") String articleId) {
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
        Article article = articleService.findOne(Integer.valueOf(articleId));
        mav.addObject("article", article);
        mav.setViewName("article/detail");
        return mav;
    }

    // 記事詳細画面から記事修正画面への遷移
    @RequestMapping(value = "/article/{articleId}/create", params = "form", method= RequestMethod.GET)
    public ModelAndView updateForm(ModelAndView mav, @PathVariable("articleId") String articleId) {
        Article article = articleService.findOne(Integer.valueOf(articleId));
        mav.addObject("pageTitle", "記事修正");
        mav.addObject("article", article);
        mav.setViewName("article/modify");
        return mav;
    }

    // 記事修正画面から記事修正確認画面への遷移
    @RequestMapping(value = "/article/{articleId}/create", params = "confirm", method= RequestMethod.POST)
    public ModelAndView updateConfirm(ModelAndView mav, @Valid @ModelAttribute("article") Article article, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            mav.addObject("pageTitle", "記事修正");
            mav.setViewName("article/modify");
            return mav;
        }
        mav.addObject("article", article);
        mav.addObject("pageTitle", "記事修正確認");
        mav.setViewName("article/modify_confirm");
        return mav;
    }

    // 記事修正確認画面から記事修正画面への遷移
    @RequestMapping(value = "/article/{articleId}/create", params = "redo", method= RequestMethod.POST)
    public ModelAndView updateRedo(ModelAndView mav, @ModelAttribute("article") Article article) {

        mav.addObject("article", article);
        mav.addObject("pageTitle", "記事修正");
        mav.setViewName("article/modify");
        return mav;
    }

    // 記事修正確認画面から記事一覧画面への遷移
    @RequestMapping(value="/article/{articleId}/create", method= RequestMethod.POST)
    public ModelAndView update(ModelAndView mav, @ModelAttribute("article") Article article, @PathVariable("articleId") String articleId) {

        Article oldArticle = articleService.findOne(Integer.valueOf(articleId));
        oldArticle.setTitle(article.getTitle());
        oldArticle.setBody(article.getBody());
        articleService.update(oldArticle);
        List<Article> articles = articleService.findAll();
        mav.addObject("articles", articles);
        mav.addObject("pageTitle", "記事一覧");
        mav.setViewName("article/list");
        return mav;
    }

    // 記事詳細画面から記事削除画面への遷移
    @RequestMapping(value = "/article/{articleId}/delete", params = "confirm", method = RequestMethod.POST)
    public ModelAndView deleteConfirm(ModelAndView mav, @PathVariable("articleId") String articleId) {
        Article article = articleService.findOne(Integer.valueOf(articleId));
        mav.addObject("article", article);
        mav.addObject("pageTitle", "記事削除確認");
        mav.setViewName("article/delete_confirm");
        return mav;
    }

    // 記事削除確認画面から記事一覧画面への遷移
    @RequestMapping(value = "/article/{articleId}/delete", method = RequestMethod.POST)
    public ModelAndView delete(ModelAndView mav, @PathVariable("articleId") String articleId) {

        articleService.delete(Integer.valueOf(articleId));
        commentService.deleteByArticleId(Integer.valueOf(articleId));
        List<Article> articles = articleService.findAll();
        mav.addObject("articles", articles);
        mav.addObject("pageTitle", "記事一覧");
        mav.setViewName("article/list");
        return mav;
    }
}
