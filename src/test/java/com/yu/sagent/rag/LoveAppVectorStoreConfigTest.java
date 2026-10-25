package com.yu.sagent.rag;

import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 向量库集成测试：运行时会调用配置的 embedding 服务。
 * Spring 上下文启动时也会执行配置中的 Markdown 文档入库逻辑。
 */
@SpringBootTest
class LoveAppVectorStoreConfigTest {

    @Autowired
    @Qualifier("loveAppVectorStore")
    private VectorStore vectorStore;

    @Test
    void shouldStoreAndSearchDocument() {
        assertNotNull(vectorStore, "应该成功创建向量库 Bean");

        String text = "向量库集成测试：夫妻发生争执时，应先冷静下来，再倾听对方的感受。";
        Document document = new Document(
                text,
                Map.of("source", "LoveAppVectorStoreConfigTest")
        );

        try {
            vectorStore.add(List.of(document));

            // 使用原文查询，验证存储和检索链路，避免依赖语义改写的排名。
            List<Document> results = vectorStore.similaritySearch(
                    SearchRequest.builder()
                            .query(text)
                            .topK(5)
                            .similarityThreshold(0.0)
                            .build()
            );

            assertNotNull(results, "检索结果不应为 null");
            Document matched = results.stream()
                    .filter(item -> document.getId().equals(item.getId()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("应该检索到刚写入的测试文档"));

            assertEquals(text, matched.getText(), "检索到的文档内容应该保持一致");
            assertEquals(
                    "LoveAppVectorStoreConfigTest",
                    matched.getMetadata().get("source"),
                    "检索到的文档应该保留元数据"
            );
        } finally {
            vectorStore.delete(List.of(document.getId()));
        }
    }
}
