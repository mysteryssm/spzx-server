import com.atguigu.spzx.manager.ManagerApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: SpringbootUnitTest
 * @author: yck
 * @create: 2024-02-22
 */

@SpringBootTest(classes =  ManagerApplication.class)
@Transactional
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringbootUnitTest {

}
