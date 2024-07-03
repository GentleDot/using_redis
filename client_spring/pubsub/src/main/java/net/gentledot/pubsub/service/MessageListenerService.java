package net.gentledot.pubsub.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageListenerService implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // byte array -> new String
        log.info("Received channel: {}", new String(message.getChannel()));
        log.info("Received message: {}", new String(message.getBody()));
    }
}
