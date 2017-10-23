package cf.zunda.zundablog.controller;

import cf.zunda.zundablog.Entity.Article;
import cf.zunda.zundablog.Entity.User;
import cf.zunda.zundablog.service.ArticleService;
import cf.zunda.zundablog.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private CommentService commentService;

    @Test
    public void testList() throws Exception{

        // ==== 前準備
        // テスト用セッションデータをセット
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        User user = new User();
        user.setId(1);
        user.setUserId("1");
        sessionAttr.put("user", user);

        // テスト用データをセット
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(new Timestamp(System.currentTimeMillis()));

        // テスト用データをセット
        Article article2 = new Article();
        article2.setId(2);
        article2.setTitle("test2");
        article2.setBody("MVCテストデータ2");
        article2.setUserId(2);
        article2.setCreateTime(new Timestamp(System.currentTimeMillis()));

        List<Article> list = Arrays.asList(article1, article2);
        given(this.articleService.findAll()).willReturn(list);

        // ==== 実行
        this.mvc.perform(get("/article/list")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))

                // ==== 検証
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test1")))
                .andExpect(content().string(containsString("test2")));
    }
}
