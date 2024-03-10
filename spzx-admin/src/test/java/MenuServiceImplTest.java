import com.spzx.admin.service.MenuService;
import com.atguigu.spzx.model.entity.admin.SysMenu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description: SysMenuService
 * @author: yck
 * @create: 2024-02-22
 */

public class MenuServiceImplTest extends SpringbootUnitTest{
    @Autowired
    private MenuService menuService;

    @Test
    public void findAllNodesTest() {
        List<SysMenu> allNodes = menuService.selectAll();
    }
}
