package orangeschool.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import orangeschool.WebUtil;
import orangeschool.model.Category;
import orangeschool.model.Customer;
import orangeschool.model.Englishtest;
import orangeschool.model.Result;
import orangeschool.model.Topic;
import orangeschool.service.CategoryService;
import orangeschool.service.EnglishtestService;
import orangeschool.service.MathSubjectService;
import orangeschool.service.ResultService;
import orangeschool.service.TopicService;
import orangeschool.service.UserService;


@RestController
public class ElaApiController {
	private Integer type = 1;
	@Autowired
	private EnglishtestService elaService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TopicService topicService;
	
	
	enum SubjectType{
		Math,
				
	}
	
	@RequestMapping("/ela/categories")
    public String getCategory() {
        
		List<Category> categories = categoryService.findWithOrderInRange(100, 113);
		
		String _ret ="{\"data\":{";
		_ret +="\"type\":\"ela_categories\",";
		
		_ret +="\"categories\":[";
		for(int i =0; i < categories.size(); i++)
		{
			Category category = categories.get(i);
			_ret += category.toJsonString();
			if(i < (categories.size() - 1))
			_ret +=",";
			
		}
		_ret +="]}}";
		return _ret;
	}
	
	
	@RequestMapping("/ela")
    public String getTopicBy(
    		@RequestParam(value="categoryid", defaultValue="0") int _categoryid
    		
    		) {
        
		//Customer customer= this.userService.findById(_customerid);
		
		Category category = categoryService.findById(_categoryid);
		List<Topic> Elas = topicService.findByTypeParent(this.type, null);
		
		System.out.println(Elas.get(0).getCategoryName());
		
		System.out.println(category.getName());
		
		List<Topic> SubTopics = null;
		if(Elas.size() >0)
		SubTopics = topicService.findByTypeCategoryParent(this.type, category, Elas.get(0));
		
		List<Topic> ret = new ArrayList<Topic>();
		
		System.out.println(" SubTopics.size() :" + SubTopics.size());

		
		String _ret ="{\"data\":{";
		_ret +="\"type\":\"ela_topic_list\",";
		
		_ret +="\"topics\":[";
		for(int i =0; i < SubTopics.size(); i++)
		{
			Topic topic = SubTopics.get(i);
			_ret += topic.toJsonString();
			if(i < (SubTopics.size() - 1))
			_ret +=",";
			
		}
		_ret +="]}}";
		
		
		return _ret;
    }
	
//	@PostMapping("/ela")
//    public String getTopicByPost(
//    		//@RequestParam(value="categoryid", defaultValue="0") int _categoryid
//    		@RequestBody Category category
//    		) {
//        
//		System.out.println("ela POST");
//		//Customer customer= this.userService.findById(_customerid);
//		
//		//Category category = categoryService.findById(_categoryid);
//		List<Topic> Elas = topicService.findByTypeParent(this.type, null);
//		
//		System.out.println(Elas.get(0).getCategoryName());
//		
//		System.out.println(category.getName());
//		
//		List<Topic> SubTopics = null;
//		if(Elas.size() >0)
//		SubTopics = topicService.findByTypeCategoryParent(this.type, category, Elas.get(0));
//		
//		List<Topic> ret = new ArrayList<Topic>();
//		
//		System.out.println(" SubTopics.size() :" + SubTopics.size());
//
//		
//		String _ret ="{\"data\":{";
//		_ret +="\"type\":\"ela_topic_list\",";
//		
//		_ret +="\"topics\":[";
//		for(int i =0; i < SubTopics.size(); i++)
//		{
//			Topic topic = SubTopics.get(i);
//			_ret += topic.toJsonString();
//			if(i < (SubTopics.size() - 1))
//			_ret +=",";
//			
//		}
//		_ret +="]}}";
//		
//		
//		return _ret;
//    }
	
	
	
	@RequestMapping("/ela/topic")
    public String getElaBy(
    		@RequestParam(value="topicid", defaultValue="0") int _topicid
    		
    		) {
		
		List<Englishtest> tests = this.elaService.findByTopicID(_topicid);
		String _ret ="{\"data\":{";
		_ret +="\"type\":\"ela_subject_list\",";
		
		_ret +="\"subjects\":[";
		for(int i =0; i < tests.size(); i++)
		{
			Englishtest test = tests.get(i);
			_ret += test.toJsonString();
			if(i < (tests.size() - 1))
			_ret +=",";
			
		}
		_ret +="]}}";
		
		
		return _ret;
		
	}
	
	
	
	@RequestMapping("/ela/detail")
    public String getMathsubjectDetail(
    		@RequestParam(value="subjectid", defaultValue="0") int _subjectid
    		
    		) {
		
		System.out.println("subjectid :" + _subjectid);
		Englishtest ret = this.elaService.findWithId(_subjectid);
		//ObjectMapper mapper = new ObjectMapper();
		String _ret = "acscs";

	    _ret = ret.toJsonString();
		
		return _ret;
	}
	
	
	
	

}
