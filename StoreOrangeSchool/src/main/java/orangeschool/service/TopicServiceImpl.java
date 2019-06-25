package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import orangeschool.repository.TopicRepository;
import orangeschool.model.Category;
import orangeschool.model.Topic;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void save(Topic _topic) {
        
        
    	topicRepository.save(_topic);
    }

    @Override
    public Topic findByName(String _name) {
    	Topic topic =  topicRepository.findByName(_name);
        return topic;
    }
    
    @Override
    public Topic findById(Integer _id) {
    	Topic topic = topicRepository.findByTopicID(_id);
        return topic;
    }
    
    @Override
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Override
    public  List<Topic> findByType(Integer _type)
    {

    	return this.topicRepository.findByType(_type);
    }
    
    @Override
    public  List<Topic> findByTypeCategory(Integer _type, Category _category)
    {

    	return this.topicRepository.findByTypeAndCategory(_type, _category);
    }
    
    @Override
    public List<Topic> findByTypeCategoryParent(
    		Integer _type, 
    		Category _category, 
    		Topic _parent)
    {
    	return this.topicRepository.findByTypeAndCategoryAndParent(_type, _category, _parent);
    }
    
    @Override
    public List<Topic> findByTypeParent(Integer _type, Topic _parent)
    {
    	return this.topicRepository.findByTypeAndParent(_type, _parent);
    }
    
    @Override
    public void deleteById(Integer _id)
    {
    	topicRepository.deleteById(_id);
    }

}