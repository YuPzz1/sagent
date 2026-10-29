package com.yu.sagent.rag;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;

/**
 * 创建上下文查询增强器的工厂
 * 属于一个错误处理机制，在查询不到相关文档的时候进行的回复
 */
public class LoveAppContextualQueryAugmenterFactory {

    public static ContextualQueryAugmenter createInstance() {
        PromptTemplate emptyContextPromptTemplate = new PromptTemplate("""
                你应该输出下面的内容：
                我是曼波，咕咕嘎嘎😎😎😎
                """);
        return ContextualQueryAugmenter.builder()
                .allowEmptyContext(false) // 检索不到也不抛异常
                .emptyContextPromptTemplate(emptyContextPromptTemplate)
                .build();
    }
}
