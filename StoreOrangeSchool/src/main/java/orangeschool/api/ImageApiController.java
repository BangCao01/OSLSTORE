package orangeschool.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import orangeschool.model.ImageContent;
import orangeschool.service.ImageContentService;



@RestController
public class ImageApiController {
	@Autowired
	private ImageContentService imageContentService;
	
	@RequestMapping("/imgs")
    public String getImageBy(
    		@RequestParam(value="name", defaultValue="") String _name
    		
    		) {
        
		//Customer customer= this.userService.findById(_customerid);
		
		List<ImageContent>  images = imageContentService.findByName(_name);
		int cnt = images.size();
		Random rand = new Random();
		int n = rand.nextInt(cnt);
		ImageContent image = images.get(n);
		String _ret ="{\"data\":{";
		_ret +="\"type\":\"image\",";
		
		_ret +="\"imagecontent\":{";
		_ret += image.toJsonString();
		_ret +="}}}";
		
		return _ret;
    }
	

}
