package com.yu.sagent.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.SimpleVectorStore;


import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 恋爱大师向量数据库配置（初始化基于内存的向量数据库 Bean）
 */
@Configuration
public class LoveAppVectorStoreConfig {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Autowired
    private VectorStore vectorStore;

    @Resource
    private MyKeywordEnricher myKeywordEnricher;

    @Bean
    VectorStore loveAppVectorStore(EmbeddingModel embeddingModel) {
        // 避免在数据库中插入多条重复数据，先注释掉
//        List<Document> documents = loveAppDocumentLoader.loadMarkdowns();
//        List<Document> enrichedDocuments = myKeywordEnricher.enrichDocuments(documents);
//        vectorStore.add(enrichedDocuments);
        return vectorStore;
    }

}
