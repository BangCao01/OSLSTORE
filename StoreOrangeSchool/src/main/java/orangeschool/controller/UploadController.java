package orangeschool.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/upload") // This
public class UploadController{

	 @RequestMapping(value = "/{f1}/{f2}/{filename}")
	 @ResponseBody
	 public byte[] getImage(
			 @PathVariable(value = "f1") String f1,
			 @PathVariable(value = "f2") String f2,
			 @PathVariable(value = "filename") String filename,
			 HttpServletRequest request) throws IOException {
	        
		    String uploadRootPath = request.getServletContext().getRealPath("upload");
	        File serverFile = new File(uploadRootPath
	        		+File.separator + f1 
	        		+File.separator + f2
	        		+File.separator + filename
	        		);

	        return Files.readAllBytes(serverFile.toPath());
	    }
	 
	
}
