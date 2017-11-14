package cf.zunda.zundablog.service;

import cf.zunda.zundablog.Entity.Article;
import cf.zunda.zundablog.repository.ArticleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import static org.mockito.Mockito.*;

public class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepositoryMock = mock(ArticleRepository.class);

    @InjectMocks
    ArticleService target;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindOne() throws Exception {

        // ==== 前準備
        // テストデータをセット
        Article article1 = new Article();
        article1.setId(1);
        article1.setTitle("test1");
        article1.setBody("MVCテストデータ1");
        article1.setUserId(1);
        article1.setCreateTime(new Timestamp(System.currentTimeMillis()));

        // Mockにテストデータを設定
        when(articleRepositoryMock.findOne(anyInt())).thenReturn(article1);

        // ==== 実行
        Article targetOne = target.findOne(1);

        // ==== 検証
        Assert.assertEquals(targetOne.getTitle(), "test1");
    }

}