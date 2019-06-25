package orangeschool.service;

import java.util.List;

import orangeschool.model.TextContent;

public interface TextContentService {
    void save(TextContent _content);

    TextContent findByContent(String _content);
    TextContent findByTextID(Integer _id);
    List<TextContent> findAll();
    void deleteById(Integer _id);
    
}