package cf.zunda.zundablog.service;

import cf.zunda.zundablog.Entity.Comment;
import cf.zunda.zundablog.repository.CommentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    CommentRepository commentRepositoryMock = mock(CommentRepository.class);

    @InjectMocks
    CommentService target;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsert() {
        // phase1 事前準備(Setup)
        // 戻り値がvoidなのでMock化しない
        // 構造もシンプルなので、Spyをして内部状態の確認もしない
        // テストデータをセット
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setArticleId(1);
        comment1.setComment("テストデータ");
        comment1.setCreateTime(now);

        // phase2 実行(Exercise)
        target.insert(comment1);

        // phase3 検証(Verify)
        verify(commentRepositoryMock, times(1)).save(any(Comment.class));

        // phase4 事後処理(teardown)
        // なし
    }

    @Test
    public void testFindByArticleId() {
        // phase1 事前準備(Setup)
        // テストデータをセット
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Comment comment1 = new Comment();
        comment1.setId(1);
        comment1.setArticleId(1);
        comment1.setComment("テストデータ");
        comment1.setCreateTime(now);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);

        // Mockにテストデータを設定
        when(commentRepositoryMock.findByArticleId(anyInt())).thenReturn(comments);

        // phase2 実行(Exercise)
        List<Comment> actual = target.findByArticleId(1);

        // phase3 検証(Verify)
        verify(commentRepositoryMock, times(1)).findByArticleId(anyInt());
        Assert.assertThat(actual.get(0).getId(), is(1));
        Assert.assertThat(actual.get(0).getArticleId(), is(1));
        Assert.assertThat(actual.get(0).getComment(), is("テストデータ"));
        Assert.assertThat(actual.get(0).getCreateTime(), is(now));

        // phase4 事後処理(teardown)
        // なし
    }

    @Test
    public void testDeleteByArticleId() {
        // phase1 事前準備(Setup)
        // 戻り値がvoidなのでMock化しない
        // 構造もシンプルなので、Spyをして内部状態の確認もしない

        // phase2 実行(Exercise)
        target.deleteByArticleId(1);

        // phase3 検証(Verify)
        verify(commentRepositoryMock, times(1)).deleteByArticleId(anyInt());

        // phase4 事後処理(teardown)
        // なし
    }

}
