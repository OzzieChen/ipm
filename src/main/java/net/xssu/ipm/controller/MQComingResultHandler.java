package net.xssu.ipm.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MQComingResultHandler {
    private RabbitTemplate rabbit;

    public MQComingResultHandler(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }
    public void handleComingResultMsg(String jsonStr) {
        System.out.println(jsonStr);
    }
}
