package net.xssu.ipm.service.impl;

import net.xssu.ipm.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by ozziechen on 2017/3/9.
 */
public class RedisServiceImpl implements IRedisService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public long storePatternString(String value) {
        long size = stringRedisTemplate.opsForHash().size("patterns");
        stringRedisTemplate.opsForHash().put("patterns", String.valueOf(size+1), value);
        long newSize = stringRedisTemplate.opsForHash().size("patterns");
        return size == newSize ? -1 : newSize;
    }
}
