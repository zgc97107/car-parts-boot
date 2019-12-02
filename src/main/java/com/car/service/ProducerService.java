package com.car.service;

public interface ProducerService {
    void sendSyncMessage(String topicId, String message);
    void sendAsyncMessage(String topicId, String message);
}
