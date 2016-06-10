package com.rest.gallery.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/rest")
public class GalleryController {
	
	@RequestMapping(value="/gallery", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public @ResponseBody String upload(Gallery gallery, HttpServletRequest request){

		System.out.println(gallery.getTitle());
		System.out.println(gallery.getMyFile());		
		
		//원하는 경로에 저장
		String path=request.getServletContext().getRealPath("/copy");
		try {
			gallery.getMyFile().transferTo(new File(path+"/"+gallery.getMyFile().getOriginalFilename()));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "sfsdafasdf";
	} 
	
}





