import com.atguigu.spzx.common.log.annotation.EnableLogAspect;
import properties.MinioProperties;
import properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ljl
 * @create 2023-10-22-16:34
 */

@SpringBootApplication
@EnableScheduling
@EnableLogAspect
@ComponentScan(basePackages = {"com.atguigu.spzx"})
@EnableConfigurationProperties(value = {UserAuthProperties.class, MinioProperties.class})
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class , args) ;
    }

}