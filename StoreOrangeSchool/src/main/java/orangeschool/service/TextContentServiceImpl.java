package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import orangeschool.repository.TextContentRepository;
import orangeschool.model.TextContent;

@Service
public class TextContentServiceImpl implements TextContentService {
    @Autowired
    private TextContentRepository textRepository;

    @Override
    public void save(TextContent _content) {
        
        
    	textRepository.save(_content);
    }

    @Override
    public TextContent findByContent(String _content) {
        TextContent content =  textRepository.findByContent(_content);
        return content;
    }
    
    @Override
    public List<TextContent> findAll() {
        return textRepository.findAll();
    }
    
    @Override
    public TextContent findByTextID(Integer _id)
    {
    	return textRepository.findByTextID(_id);
    }

    @Override
    public  void deleteById(Integer _id)
    {
    	this.textRepository.deleteById(_id);
    }

}