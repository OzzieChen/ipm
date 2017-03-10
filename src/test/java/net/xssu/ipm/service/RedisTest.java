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
        //long oriSize = stringRedisTemplate.opsForHash().size("patterns");
        long newSize = redisService.storePatternString("testValue123");
        assertNotEquals(newSize, -1);
    }

}
