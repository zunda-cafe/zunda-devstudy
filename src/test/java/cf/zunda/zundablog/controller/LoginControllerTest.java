package cf.zunda.zundablog.controller;

import cf.zunda.zundablog.Entity.User;
import cf.zunda.zundablog.service.ArticleService;
import cf.zunda.zundablog.service.CommentService;
import cf.zunda.zundablog.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    //@Autowired
    //private WebApplicationContext wac;
    @Autowired
    private LoginController target;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private UserService userService;

    @MockBean
    private CommentService commentService;

    @Test
    public void testLoginのGet() throws Exception {
        // phase1 事前準備(Setup)
        // 事前データはなし
        // テスト用セッションデータをセット
        User user = new User();
        user.setId(1);
        user.setUserId("1");

        given(this.userService.findByUserId("user01")).willReturn(user);

        // phase2 実行(Exercise)
        // phase3 検証(Verify)
        this.mvc.perform(get("/login").param("form", "").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());

        // phase4 事後処理(teardown)
        // なし
    }

    @Test
    public void testLoginPost() throws Exception {
        // phase1 事前準備(Setup)
        // 事前データはなし
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        User guestUser = new User();
        guestUser.setId(0);
        guestUser.setUserId("ゲスト");
        sessionAttr.put("user", guestUser);

        User user = new User();
        user.setId(1);
        user.setUserId("user01");
        user.setPassword("password");

        given(this.userService.findByUserId("user01")).willReturn(user);

        // phase2 実行(Exercise)
        this.mvc.perform(post("/login").param("userId", "user01").param("password", "password")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                // phase3 検証(Verify)
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginPostのログイン情報なし() throws Exception {
        // phase1 事前準備(Setup)
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        User guestUser = new User();
        guestUser.setId(0);
        guestUser.setUserId("ゲスト");
        sessionAttr.put("user", guestUser);

        // phase2 実行(Exercise)
        this.mvc.perform(post("/login").accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                // phase3 検証(Verify)
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
        // phase4 事後処理(teardown)
        // なし
    }

    @Test
    public void testLoginPostのログイン情報誤り() throws Exception {
        // phase1 事前準備(Setup)
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        User guestUser = new User();
        guestUser.setId(0);
        guestUser.setUserId("ゲスト");
        sessionAttr.put("user", guestUser);

        User user = new User();
        user.setId(1);
        user.setUserId("user01");
        user.setPassword("password");

        given(this.userService.findByUserId("user01")).willReturn(user);

        // phase2 実行(Exercise)
        this.mvc.perform(post("/login").param("userId", "userxx").param("password", "password")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                // phase3 検証(Verify)
                .andDo(print())
                .andExpect(status().isOk());


        // phase4 事後処理(teardown)
        // なし
    }
}
