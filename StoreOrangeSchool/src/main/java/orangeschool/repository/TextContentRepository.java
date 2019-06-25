package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import orangeschool.model.TextContent;

public interface TextContentRepository extends JpaRepository<TextContent, Integer> {
	 TextContent findByContent(String _content);
	 TextContent findByTextID(Integer _id);
	 
}
