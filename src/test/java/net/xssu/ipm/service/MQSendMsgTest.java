package net.xssu.ipm.service;

import net.xssu.ipm.entity.ScanTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springConfig/spring-context.xml")
public class MQSendMsgTest {
    @Autowired
    @Qualifier("jsonRabbitTemplate")
    private RabbitTemplate jsonRabbit;

    @Autowired
    @Qualifier("plainRabbitTemplate")
    private RabbitTemplate plainRabbit;

    @Autowired
    private IMQSendingService sendingService;

    @Test
    public void rabbitShouldNotBeNull() {
        assertNotNull(jsonRabbit);
        assertNotNull(plainRabbit);
    }

    @Test
    public void sendMsg() {
        assertNotNull(sendingService);
        long seed = System.currentTimeMillis();
        ScanTask msg1 = new ScanTask(3, "60.205.8.0/28", "80", "1/2", seed, true, true, 1);
        ScanTask msg2 = new ScanTask(4, "60.205.8.0/28", "80", "2/2", seed, true, true, 1);
        boolean result = sendingService.sendTask(msg1);
        boolean result2 = sendingService.sendTask(msg2);
        assertTrue(result);
        assertTrue(result2);
    }

    public void setJsonRabbit(RabbitTemplate jsonRabbit) {
        this.jsonRabbit = jsonRabbit;
    }

    public void setPlainRabbit(RabbitTemplate plainRabbit) {
        this.plainRabbit = plainRabbit;
    }
}
