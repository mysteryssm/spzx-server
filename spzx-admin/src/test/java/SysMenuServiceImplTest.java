import com.atguigu.spzx.manager.ManagerApplication;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @description: SysMenuService
 * @author: yck
 * @create: 2024-02-22
 */

public class SysMenuServiceImplTest extends SpringbootUnitTest{
    @Autowired
    private SysMenuService sysMenuService;

    @Test
    public void findAllNodesTest() {
        List<SysMenu> allNodes = sysMenuService.findAllNodes();
    }
}
