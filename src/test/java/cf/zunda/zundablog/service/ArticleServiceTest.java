package cf.zunda.zundablog.service;

import cf.zunda.zundablog.Entity.Article;
import cf.zunda.zundablog.repository.ArticleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

public class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepositoryMock = mock(ArticleRepository.class);

    @InjectMocks
    ArticleService target;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        // phase1 事前準備(Setup)
        // テストデータをセット
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(now);

        Article article2 = new Article();
        article2.setId(2);
        article2.setTitle("test2");
        article2.setBody("MVCテストデータ2");
        article2.setUserId(2);
        article2.setCreateTime(now);

        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);

        // Mockにテストデータを設定
        when(articleRepositoryMock.findAll((any(Sort.class)))).thenReturn(articles);

        // phase2 実行(Exercise)
        List<Article> actual = target.findAll();

        // phase3 検証(Verify)
        verify(articleRepositoryMock, times(1)).findAll(any(Sort.class));
        Assert.assertThat(actual.size(), is(2));

        Assert.assertThat(actual.get(0).getId(), is(1));
        Assert.assertThat(actual.get(0).getTitle(), is("test1"));
        Assert.assertThat(actual.get(0).getBody(), is("MVCテストデータ1"));
        Assert.assertThat(actual.get(0).getUserId(), is(1));
        Assert.assertThat(actual.get(0).getCreateTime(), is(now));

        Assert.assertThat(actual.get(1).getId(), is(2));
        Assert.assertThat(actual.get(1).getTitle(), is("test2"));
        Assert.assertThat(actual.get(1).getBody(), is("MVCテストデータ2"));
        Assert.assertThat(actual.get(1).getUserId(), is(2));
        Assert.assertThat(actual.get(1).getCreateTime(), is(now));

        // phase4 事後処理(teardown)
        // なし
    }

    @Test
    public void testFineOne() {

        // phase1 事前準備(Setup)
        // テストデータをセット
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(now);

        // Mockにテストデータを設定
        when(articleRepositoryMock.findOne(anyInt())).thenReturn(article1);

        // phase2 実行(Exercise)
        Article actual = target.findOne(1);

        // phase3 検証(Verify)
        verify(articleRepositoryMock, times(1)).findOne(anyInt());
        Assert.assertThat(actual.getId(), is(1));
        Assert.assertThat(actual.getTitle(), is("test1"));
        Assert.assertThat(actual.getBody(), is("MVCテストデータ1"));
        Assert.assertThat(actual.getUserId(), is(1));
        Assert.assertThat(actual.getCreateTime(), is(now));

        // phase4 事後処理(teardown)
        // なし

    }

    @Test
    public void testdelete() throws Exception {
        // phase1 事前準備(Setup)
        // 戻り値がvoidなのでMock化しない
        // 構造もシンプルなので、Spyをして内部状態の確認もしない

        // phase2 実行(Exercise)
        target.delete(1);

        // phase3 検証(Verify)
        verify(articleRepositoryMock, times(1)).delete(anyInt());

        // phase4 事後処理(teardown)
        // なし
    }

    @Test
    public void testInsert() {
        // phase1 事前準備(Setup)
        // 戻り値がvoidなのでMock化しない
        // 構造もシンプルなので、Spyをして内部状態の確認もしない
        // テストデータをセット
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(now);

        // phase2 実行(Exercise)
        target.insert(article1);

        // phase3 検証(Verify)
        verify(articleRepositoryMock, times(1)).save(any(Article.class));

        // phase4 事後処理(teardown)
        // なし
    }

    @Test
    public void testUpdate() {
        // phase1 事前準備(Setup)
        // 戻り値がvoidなのでMock化しない
        // 構造もシンプルなので、Spyをして内部状態の確認もしない
        // テストデータをセット
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(now);

        // phase2 実行(Exercise)
        target.update(article1);

        // phase3 検証(Verify)
        verify(articleRepositoryMock, times(1)).save(any(Article.class));

        // phase4 事後処理(teardown)
        // なし
    }
}