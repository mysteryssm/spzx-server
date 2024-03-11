import com.spzx.admin.AdminApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: SpringbootUnitTest
 * @author: yck
 * @create: 2024-02-22
 */

@SpringBootTest(classes =  AdminApplication.class)
@Transactional
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringbootUnitTest {

}
