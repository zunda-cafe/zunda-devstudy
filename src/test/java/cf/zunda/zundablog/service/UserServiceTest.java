package cf.zunda.zundablog.service;

import cf.zunda.zundablog.Entity.User;
import cf.zunda.zundablog.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    UserRepository userRepositoryMock = mock(UserRepository.class);

    @InjectMocks
    UserService target;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByUserId() {
        // phase1 事前準備(Setup)
        // テストデータをセット
        Timestamp now = new Timestamp(System.currentTimeMillis());
        User user1 = new User();
        user1.setId(1);
        user1.setUserId("user1");
        user1.setPassword("password");
        user1.setCreateTime(now);

        // Mockにテストデータを設定
        when(userRepositoryMock.findByUserId(anyString())).thenReturn(user1);

        // phase2 実行(Exercise)
        User actual = target.findByUserId("user1");

        // phase3 検証(Verify)
        verify(userRepositoryMock, times(1)).findByUserId(anyString());
        Assert.assertThat(actual.getId(), is(1));
        Assert.assertThat(actual.getUserId(), is("user1"));
        Assert.assertThat(actual.getPassword(), is("password"));
        Assert.assertThat(actual.getCreateTime(), is(now));

        // phase4 事後処理(teardown)
        // なし
    }

}
