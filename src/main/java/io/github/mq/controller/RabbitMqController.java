package io.github.mq.controller;

import io.github.mq.exchange.MqSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * RabbitMqController
 *
 * @author Wilson
 * @date 2019/2/13
 */
@RestController
@RequestMapping("/")
public class RabbitMqController {
    @Resource
    private MqSender helloSender;

    @GetMapping("/hello")
    public String hello() {
        helloSender.sendHelloQueue("121 msg");
        return "success";
    }

    @GetMapping("/fanout")
    public String fanout() {
        helloSender.sendFanout("fanout msg");
        return "success";
    }
}
