package com.yu.sagent.app;

import com.yu.sagent.advisor.MyLoggerAdvisor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoveApp {
    private final ChatClient chatClient;

    private final String SYSTEM_PROMPT = "你是一个恋爱大师";

    public LoveApp (ChatModel chatModel) {
        ChatMemoryRepository repository =
                new InMemoryChatMemoryRepository();

        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(10)
                .build();

        chatClient = ChatClient.builder(chatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .build();
    }

    public String doChat(String message, String chatId){
        String content = chatClient
                .prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec
                        .param(ChatMemory.CONVERSATION_ID, chatId)
                        )
                .call()
                .content();

        log.info("content: {}", content);
        return content;
    }

    // AI恋爱知识库问答系统
    @Resource
    private VectorStore loveAppVectorStore;

    /**
     * 和RAG知识库进行对话
     * @param message
     * @param chatId
     * @return
     */
    public String doChatWithRag(String message, String chatId){
        String content = chatClient
                .prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec
                        .param(ChatMemory.CONVERSATION_ID, chatId)
                )
                .advisors(new MyLoggerAdvisor())
                .advisors(QuestionAnswerAdvisor.builder(loveAppVectorStore).build())
                .call()
                .content();

        log.info("content: {}", content);
        return content;
    }
}
