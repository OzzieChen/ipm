package net.xssu.ipm.service.impl;

import net.xssu.ipm.entity.ScanTask;
import net.xssu.ipm.service.IMQSendingService;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MQSendingServiceImpl implements IMQSendingService {
    private RabbitTemplate rabbit;
    private Queue queue;

    public MQSendingServiceImpl(RabbitTemplate rabbit, Queue queue) {
        this.rabbit = rabbit;
        this.queue = queue;
    }

    public boolean sendTask(ScanTask msg) {
        try {
            rabbit.convertAndSend(queue.getName(), msg);
            return true;
        } catch (AmqpException e) {
            e.printStackTrace();
            return false;
        }
    }

}
