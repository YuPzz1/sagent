package com.yu.sagent;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeepSeekConnectionTest {

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Test
    void testDeepSeekConnection() {
        ChatClient chatClient = chatClientBuilder.build();

        String response = chatClient
                .prompt()
                .user("你好，给我发点有趣的表情")
                .call()
                .content();

        System.out.println("DeepSeek 返回：");
        System.out.println(response);
    }
}
