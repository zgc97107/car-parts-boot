package com.car.service.impl;

import com.car.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class KafkaProducerServiceImpl implements ProducerService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void sendSyncMessage(String topicId,String message) {
        log.debug("发送消息");
        try {
            kafkaTemplate.send(topicId,message).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("消费成功"+System.currentTimeMillis());
    }

    @Override
    public void sendAsyncMessage(String topicId,String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicId, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.debug("消费失败"+System.currentTimeMillis());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.debug("消费成功"+System.currentTimeMillis());
            }
        });
    }
}
