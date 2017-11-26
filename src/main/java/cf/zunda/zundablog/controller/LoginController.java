package cf.zunda.zundablog.controller;

import cf.zunda.zundablog.Entity.Article;
import cf.zunda.zundablog.Entity.User;
import cf.zunda.zundablog.service.ArticleService;
import cf.zunda.zundablog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;

    // ログイン画面への遷移
    @RequestMapping(value = "/login", params = "form", method= RequestMethod.GET)
    public ModelAndView loginForm(ModelAndView mav) {

        // 初期画面表示時は、ゲストユーザとする
        User user = new User();
        user.setId(0);
        user.setUserId("ゲスト");
        session.setAttribute("user", user);

        mav.addObject("user", userService.findByUserId("user01"));
        mav.addObject("pageTitle", "ログイン");
        mav.setViewName("login");
        return mav;
    }

    // ログイン画面から記事一覧画面への遷移
    @RequestMapping(value = "/login", method= RequestMethod.POST)
    public ModelAndView login(ModelAndView mav, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            mav.addObject("pageTitle", "ログイン");
            mav.setViewName("login");
            return mav;
        }
        User dbUser = userService.findByUserId(user.getUserId());
        if (dbUser == null || !user.getPassword().equals(dbUser.getPassword())) {
            mav.addObject("errorMessage", "ユーザIDまたはパスワードが一致しません");
            mav.addObject("pageTitle", "ログイン");
            mav.setViewName("login");
            return mav;

        }
        // すべての情報を持っているDBのユーザを入れる
        session.setAttribute("user", dbUser);
        List<Article> articles = articleService.findAll();
        mav.addObject("articles", articles);
        mav.addObject("pageTitle", "記事一覧");
        mav.setViewName("article/list");
        return mav;
    }
}
