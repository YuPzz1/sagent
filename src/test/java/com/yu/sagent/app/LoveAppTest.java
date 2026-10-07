package com.yu.sagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void doChat() {
        loveApp.doChat("你好，我是yu", "1");
        loveApp.doChat("记忆测试，我叫什么", "1");
    }
}