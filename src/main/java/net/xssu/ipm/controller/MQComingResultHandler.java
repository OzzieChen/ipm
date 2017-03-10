package net.xssu.ipm.controller;

import net.xssu.ipm.entity.ScanResult;
import net.xssu.ipm.service.IMQReceivingService;
import net.xssu.ipm.service.IRedisService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class MQComingResultHandler {
    private RabbitTemplate rabbit;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    IMQReceivingService receivingService;

    public MQComingResultHandler(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void handleComingResultMsg(ScanResult scanResult) {
        receivingService.storeOneResult(scanResult);

        /* How many clients finished this task */
        long finishedCount = stringRedisTemplate.opsForHash().size("task" + scanResult.getTaskId());

        /* How many clients did this task */
        long totalClients = stringRedisTemplate.opsForHash().size("clients");

        if (finishedCount == totalClients) {
            receivingService.organiseResult(scanResult.getTaskId());
        }
    }
}
