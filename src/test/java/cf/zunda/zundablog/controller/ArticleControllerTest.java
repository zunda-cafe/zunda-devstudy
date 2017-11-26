package cf.zunda.zundablog.controller;

import cf.zunda.zundablog.Entity.Article;
import cf.zunda.zundablog.Entity.Comment;
import cf.zunda.zundablog.Entity.User;
import cf.zunda.zundablog.service.ArticleService;
import cf.zunda.zundablog.service.CommentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private CommentService commentService;

    private HashMap<String, Object> sessionAttr = new HashMap<>();

    @Before
    public void setUp() {
        // ==== 前準備
        // テスト用セッションデータをセット
        sessionAttr = new HashMap<String, Object>();
        User user = new User();
        user.setId(1);
        user.setUserId("1");
        sessionAttr.put("user", user);
    }

    @Test
    public void testArticleListのGet() throws Exception {
        // phase1 事前準備(Setup)
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

        // phase2 実行(Exercise)
        // phase3 検証(Verify)
        this.mvc.perform(get("/article/list")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))

                // ==== 検証
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test1")))
                .andExpect(content().string(containsString("test2")));
        // phase4 事後処理(teardown)

    }

    @Test
    public void testArticleCreateのGet() throws Exception {
        // phase1 事前準備(Setup)
        // phase2 実行(Exercise)
        this.mvc.perform(get("/article/create")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr).param("form", ""))
                .andExpect(status().isOk());
        // phase3 検証(Verify)
        // phase4 事後処理(teardown)

    }

    @Test
    public void testArticleCreateのPostのconfirm() throws Exception {
        // phase1 事前準備(Setup)
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(new Timestamp(System.currentTimeMillis()));

        // phase2 実行(Exercise)
        this.mvc.perform(post("/article/create")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr).param("confirm", "")
                .param("title", "タイトル").param("body", "本文"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("article"));
        // phase3 検証(Verify)
        // phase4 事後処理(teardown)s
    }

    @Test
    public void testArticleCreateのPostのconfirmで記事情報がない() throws Exception {
        // phase1 事前準備(Setup)
        // phase2 実行(Exercise)
        this.mvc.perform(post("/article/create")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr).param("confirm", ""))
                .andExpect(status().isOk());
        // phase3 検証(Verify)
        // phase4 事後処理(teardown)s
    }

    @Test
    public void testArticleCreateのPostのredo() throws Exception {
        // phase1 事前準備(Setup)
        // phase2 実行(Exercise)
        this.mvc.perform(post("/article/create")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr).param("redo", ""))
                .andExpect(status().isOk());
        // phase3 検証(Verify)
        // phase4 事後処理(teardown)

    }

    @Test
    public void testArticleCreateのPost() throws Exception {
        // phase1 事前準備(Setup)
        // phase2 実行(Exercise)
        this.mvc.perform(post("/article/create")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                .andExpect(status().isOk());
        // phase3 検証(Verify)
        // phase4 事後処理(teardown)

    }

    @Test
    public void testArticleArticleIdのGet() throws Exception {

        // phase1 事前準備(Setup)
        // テスト用データをセット
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(new Timestamp(System.currentTimeMillis()));

        given(this.articleService.findOne(1)).willReturn(article1);
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setArticleId(1);
        comment1.setComment("コメントのテスト");
        comment1.setCreateTime(new Timestamp(System.currentTimeMillis()));
        List comments = new ArrayList<Comment>();
        comments.add(comment1);
        given(this.commentService.findByArticleId(1)).willReturn(comments);

        // phase2 実行(Exercise)
        // phase3 検証(Verify)
        this.mvc.perform(get("/article/1")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                .andExpect(status().isOk());
        // phase4 事後処理(teardown)
    }

    @Test
    public void testArticleArticleIdのPost() throws Exception {
        // phase1 事前準備(Setup)
        // テスト用データをセット
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(new Timestamp(System.currentTimeMillis()));

        given(this.articleService.findOne(1)).willReturn(article1);
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setArticleId(1);
        comment1.setComment("コメントのテスト");
        comment1.setCreateTime(new Timestamp(System.currentTimeMillis()));
        List comments = new ArrayList<Comment>();
        comments.add(comment1);
        given(this.commentService.findByArticleId(1)).willReturn(comments);
        // phase2 実行(Exercise)
        // phase3 検証(Verify)
        this.mvc.perform(post("/article/1")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                .andExpect(status().isOk());
        // phase4 事後処理(teardown)
    }

    @Test
    public void testArticleArticleIdCreateのGet() throws Exception {
        // phase1 事前準備(Setup)
        // テスト用データをセット
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(new Timestamp(System.currentTimeMillis()));

        given(this.articleService.findOne(1)).willReturn(article1);
        // phase2 実行(Exercise)
        // phase3 検証(Verify)
        this.mvc.perform(get("/article/1/create").param("form", "")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                .andExpect(status().isOk());
        // phase4 事後処理(teardown)
    }

    @Test
    public void testArticleArticleIdCreateのPostのConfirm() throws Exception {
        // phase1 事前準備(Setup)
        // phase2 実行(Exercise)
        // phase3 検証(Verify)
        this.mvc.perform(post("/article/1/create").param("confirm", "")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr)
                .param("title", "タイトル").param("body", "本文"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("article"));
        // phase4 事後処理(teardown)
    }

    @Test
    public void testArticleArticleIdCreateのPostのConfirmで記事情報がない() throws Exception {
        // phase1 事前準備(Setup)
        // phase2 実行(Exercise)
        // phase3 検証(Verify)
        this.mvc.perform(post("/article/1/create").param("confirm", "")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                .andExpect(status().isOk());
        // phase4 事後処理(teardown)
    }

    @Test
    public void testArticleArticleIdCreateのPostのRedo() throws Exception {
        // phase1 事前準備(Setup)
        // phase2 実行(Exercise)
        // phase3 検証(Verify)
        this.mvc.perform(post("/article/1/create").param("redo", "")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                .andExpect(status().isOk());
        // phase4 事後処理(teardown)
    }

    @Test
    public void testArticleArticleIdCreateのPost() throws Exception {
        // phase1 事前準備(Setup)
        // テスト用データをセット
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(new Timestamp(System.currentTimeMillis()));

        given(this.articleService.findOne(1)).willReturn(article1);

        // テスト用データをセット
        Article article2 = new Article();
        article2.setId(2);
        article2.setTitle("test2");
        article2.setBody("MVCテストデータ2");
        article2.setUserId(2);
        article2.setCreateTime(new Timestamp(System.currentTimeMillis()));

        List<Article> list = Arrays.asList(article1, article2);
        given(this.articleService.findAll()).willReturn(list);

        // phase2 実行(Exercise)
        // phase3 検証(Verify)
        this.mvc.perform(post("/article/1/create")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                .andExpect(status().isOk());
        // phase4 事後処理(teardown)
    }

    @Test
    public void testArticleArticleIdDeleteのPost() throws Exception {
        // phase1 事前準備(Setup)
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
        // phase2 実行(Exercise)
        // phase3 検証(Verify)
        this.mvc.perform(post("/article/1/delete")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                .andExpect(status().isOk());
        // phase4 事後処理(teardown)
    }

    @Test
    public void testArticleArticleIdDeleteのPostのConfirm() throws Exception {
        // phase1 事前準備(Setup)
        // テスト用データをセット
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(new Timestamp(System.currentTimeMillis()));

        given(this.articleService.findOne(1)).willReturn(article1);

        // phase2 実行(Exercise)
        // phase3 検証(Verify)
        this.mvc.perform(post("/article/1/delete").param("confirm", "")
                .accept(MediaType.TEXT_PLAIN).sessionAttrs(sessionAttr))
                .andExpect(status().isOk());
        // phase4 事後処理(teardown)
    }
}
