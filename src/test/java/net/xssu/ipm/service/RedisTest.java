package net.xssu.ipm.service;

import net.xssu.ipm.service.impl.RedisServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springConfig/spring-context.xml")
public class RedisTest {
    @Autowired
    RedisServiceImpl redisService;

    @Test
    public void addPatternTest() {
        long newSize = redisService.storePatternString("# patterns begin\n" +
                "send_num = 3\n" +
                "recv_num = 6\n" +
                "\n" +
                "s0.msg = “GET / HTTP/1.0\\r\\nUser-Agent: Patterns 1.0\\r\\nAccept: */*\\r\\n\\r\\n\"\n" +
                "s0.waiting = r0\n" +
                "\n" +
                "s1.msg = \"GET /index.php HTTP/1.0\\r\\nUser-Agent: Patterns 1.0\\r\\nAccept: */*\\r\\n\\r\\n\"\n" +
                "s1.waiting = r2\n" +
                "\n" +
                "s2.msg = “GET /login HTTP/1.0\\r\\nUser-Agent: Patterns 1.0\\r\\nAccept: */*\\r\\n\\r\\n\"\n" +
                "s2.waiting = r3\n" +
                "\n" +
                "r0.is_banner = true\n" +
                "r0.patterns = {“HTTP 1.”, “200 OK\"}\n" +
                "r0.goto = r1\n" +
                "\n" +
                "r1.patterns = {“Nginx”, “Apache”}\n" +
                "r1.goto = {0, s1}, {1, s2}");
        assertNotEquals(newSize, -1);
    }

}
