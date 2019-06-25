package orangeschool.service;

import java.util.List;

import orangeschool.model.Category;
import orangeschool.model.Topic;

public interface TopicService {
    void save(Topic _topic);

    Topic findByName(String _name);
    Topic findById(Integer _id);
    List<Topic> findAll();
    void deleteById(Integer _id);
    List<Topic> findByType(Integer _type);
    List<Topic> findByTypeParent(Integer _type, Topic _parent);
    List<Topic> findByTypeCategory(Integer _type, Category _category);
    List<Topic> findByTypeCategoryParent(Integer _type, Category _category, Topic _parent);
    
}