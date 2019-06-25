package orangeschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

import orangeschool.model.Category;
import orangeschool.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
	
	Topic findByName(String _name);
	Topic findByTopicID(Integer _id);
	List<Topic> findByType(Integer _type);
	List<Topic> findByTypeAndCategory(Integer _type, Category _category);
	List<Topic> findByTypeAndParent(Integer _type, Topic _parent);
	List<Topic> findByTypeAndCategoryAndParent(Integer _type, 
			Category _category, 
			Topic _parent);

	
}
