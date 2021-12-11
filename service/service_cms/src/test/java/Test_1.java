import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author 曹广福
 * @date 2021/11/17 0:29
 */
public class Test_1 {

    @Resource
    RedisUtils redisUtils;


    @Test
    public void set() {

        redisUtils.set("cao", "123");
        System.out.println(redisUtils.get("cao"));
    }
}
