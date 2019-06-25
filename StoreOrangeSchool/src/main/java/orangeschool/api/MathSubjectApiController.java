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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import orangeschool.WebUtil;
import orangeschool.model.Category;
import orangeschool.model.Customer;
import orangeschool.model.Englishtest;
import orangeschool.model.MathSubject;
import orangeschool.model.Result;
import orangeschool.model.Topic;
import orangeschool.service.CategoryService;
import orangeschool.service.EnglishtestService;
import orangeschool.service.MathSubjectService;
import orangeschool.service.ResultService;
import orangeschool.service.TopicService;
import orangeschool.service.UserService;


@RestController
public class MathSubjectApiController {
	private Integer type = 2;

	@Autowired
	private MathSubjectService mathSubjectService;

	@Autowired
	private EnglishtestService elaService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TopicService topicService;
	
	
	enum SubjectType{
		Math,
				
	}
	
	@RequestMapping("/math/categories")
    public String getCategory() {
        
		List<Category> categories = categoryService.findWithOrderInRange(49, 62);
		
		String _ret ="{\"data\":{";
		_ret +="\"type\":\"math_categories\",";
		
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
	
	
	@RequestMapping("/math")
    public String getTopicBy(
    		@RequestParam(value="categoryid", defaultValue="0") int _categoryid
    		
    		) {
        
		//Customer customer= this.userService.findById(_customerid);
		
		Category category = categoryService.findById(_categoryid);
		List<Topic> Maths = topicService.findByTypeParent(this.type, null);
		List<Topic> SubTopics = null;
		if(Maths.size() >0)
		SubTopics = topicService.findByTypeCategoryParent(this.type, category, Maths.get(0));
		
		List<Topic> ret = new ArrayList<Topic>();
		
		System.out.println(" SubTopics.size() :" + SubTopics.size());

		
		String _ret ="{\"data\":{";
		_ret +="\"type\":\"math_topic_list\",";
		
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
	
	@RequestMapping("/math/topic")
    public String getMathsubjectBy(
    		@RequestParam(value="topicid", defaultValue="0") int _topicid
    		
    		) {
		
		List<MathSubject> tests = this.mathSubjectService.findByTopicID(_topicid);
		String _ret ="{\"data\":{";
		_ret +="\"type\":\"math_subject_list\",";
		
		_ret +="\"subjects\":[";
		for(int i =0; i < tests.size(); i++)
		{
			MathSubject test = tests.get(i);
			_ret += test.toJsonString();
			if(i < (tests.size() - 1))
			_ret +=",";
			
		}
		_ret +="]}}";
		
		return _ret;
		
	}
	
	
	
	@RequestMapping("/math/detail")
    public String getMathsubjectDetail(
    		@RequestParam(value="subjectid", defaultValue="0") int _subjectid
    		
    		) {
		
		System.out.println("subjectid :" + _subjectid);
		MathSubject ret = this.mathSubjectService.findById(_subjectid);
		//ObjectMapper mapper = new ObjectMapper();
		String _ret = "acscs";

	    _ret = ret.toJsonString();
		
		return _ret;
	}
	
	
	@RequestMapping("/math/add")
    public String addMathsubject(
    		@RequestParam(value="topicid", defaultValue="0") Integer _topicid,
    		@RequestParam(value="categoryid", defaultValue="0") Integer _categoryid,
    		@RequestParam(value="subject", defaultValue="0") String _subject,
    		@RequestParam(value="description", defaultValue="0") String _description
    		
    		) {
		
		
		//MathSubject ret = this.mathSubjectService.findById(_subjectid);
		//ObjectMapper mapper = new ObjectMapper();
		String _ret = "acscs";

	    //_ret = ret.toJsonString();
		
		return _ret;
	}
	
	
	
	
	

}
