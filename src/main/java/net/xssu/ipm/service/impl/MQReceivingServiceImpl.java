package net.xssu.ipm.service.impl;

import net.xssu.ipm.entity.ScanResult;
import net.xssu.ipm.service.IMQReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Iterator;
import java.util.List;

public class MQReceivingServiceImpl implements IMQReceivingService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Override
    public void storeOneResult(ScanResult scanResult) {
        /* Deal with output bytes[] */
        String outputStr = new String(scanResult.getResultFileBytes());
        stringRedisTemplate.opsForHash().put("task" + scanResult.getTaskId(), scanResult.getClientId(), outputStr);
    }

    @Override
    public String organiseResult(int taskId) {
        List<Object> outputList = stringRedisTemplate.opsForHash().values("task" + taskId);
        Iterator<Object> iterator = outputList.iterator();
        StringBuilder result = new StringBuilder();
        while (iterator.hasNext()) {
            String outputResult = (String) iterator.next();
            result.append(outputResult);
        }
        // TODO: Write result to DB and notify users goes here...
        return result.toString();
    }

}
