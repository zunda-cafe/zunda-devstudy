package cf.zunda.zundablog.controller;

import cf.zunda.zundablog.Entity.Article;
import cf.zunda.zundablog.Entity.User;
import cf.zunda.zundablog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;


@Controller
public class ArticleContributionController {

    @Autowired
    ArticleService articleService;

    @Autowired
    HttpSession session;

    // 記事投稿画面から記事投稿確認画面への遷移
    @RequestMapping(value = "/article_contribution/", params = {"param=contribution"})
    public ModelAndView articleContributionModeContribution(ModelAndView mav, @Valid @ModelAttribute("article") Article article, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            mav.addObject("pageTitle", "記事投稿");
            mav.setViewName("article_contribution");
            return mav;
        }
        mav.addObject("article", article);
        mav.addObject("pageTitle", "記事投稿確認");
        mav.setViewName("article_contribution_confirm");

        return mav;
    }

    // 記事投稿画面から記事一覧画面への遷移
    @RequestMapping(value = "/article_contribution/", params = {"param=return"})
    public ModelAndView articleContributionModeReturn(ModelAndView mav) {

        List<Article> articles = articleService.findAll();
        mav.addObject("articles", articles);
        mav.addObject("pageTitle", "記事一覧");
        mav.setViewName("article_list");

        return mav;
    }

    // 記事投稿確認画面から記事一覧画面への遷移
    @RequestMapping(value = "/article_contribution_confirm/", params = {"param=contribution"})
    public ModelAndView articleContributionConfirmModeContribution(ModelAndView mav, @ModelAttribute("article") Article article) {

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
        mav.setViewName("article_list");

        return mav;
    }
    //記事投稿確認画面から記事一覧画面への遷移
    @RequestMapping(value = "/article_contribution_confirm/", params = {"param=return"})
    public ModelAndView articleContributionConfirmModeReturn(ModelAndView mav, @ModelAttribute("article") Article article) {

        mav.addObject("article", article);
        mav.addObject("pageTitle", "記事投稿");
        mav.setViewName("article_contribution");
        return mav;
    }
}
