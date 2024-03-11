import com.spzx.model.entity.admin.Menu;
import com.spzx.admin.service.MenuService;
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
        List<Menu> allNodes = menuService.selectAll();
    }
}
