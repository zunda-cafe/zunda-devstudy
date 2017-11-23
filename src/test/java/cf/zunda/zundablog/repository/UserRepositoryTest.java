package cf.zunda.zundablog.repository;

import cf.zunda.zundablog.Entity.User;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.CsvDataFileLoader;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import static org.junit.Assert.*;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;

@SpringBootTest
@Rollback
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    DataSource dataSource;

    @Before
    public void setup() throws Exception {
        DataSourceDatabaseTester dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSource);
        dataSourceDatabaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);

        CsvDataFileLoader csvDataFileLoader = new CsvDataFileLoader();
        dataSourceDatabaseTester.setDataSet(csvDataFileLoader.loadDataSet(ResourceUtils.getURL("classpath:dbunit/")));

        dataSourceDatabaseTester.onSetup();

    }

    @Test
    public void teatFindByUserId() {

        // 実行
        User user01 = userRepository.findByUserId("user01");
        assertEquals(new Integer(1),user01.getId());

    }
}
