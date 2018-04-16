package edu.ap.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ap.spring.model.InhaalExamen;
import edu.ap.spring.redis.RedisService;

@Controller
@RequestMapping("/")
public class RedisController {

   private RedisService service;
	
   @Autowired
	public void setRedisService(RedisService service) {
		this.service = service;
   }
   
   @RequestMapping("/list")
   @ResponseBody
   public String list() {

	   String html = "<HTML>";
	   // get the bitcount of our counter
	   html += "<BODY><h1>" + service.bitCount("aanvraagcount") + " Aanvragen</h1><br/><br/><ul>";
	   
	   Set<String> InhaalExamen = service.keys("InhaalExamen:*");
	   for(String m : InhaalExamen) {
		   // make a key from the byte array
		   String key = new String(m);
		   // get hash with student
		   Map<Object, Object> student = service.hgetAll(key);
		   String[] parts = key.split(":");
		   
		   html += "<li>" + parts[2] + " (" + parts[1] + ")<br/>";
		   html += "student : ";
		   // iterate over student
		   for(Entry<Object, Object> entry : student.entrySet()) {
			   html += entry.getValue() + ", ";
		   }
		   // strip off last ', '
		   html = html.substring(0, html.length() - 2);
	   }
	   html += "</BODY></HTML>";
	   
	   return html;
   }
   
   @RequestMapping(value = "/new", method = RequestMethod.POST)
   public ResponseEntity<String> add(
       @PathVariable String name,
       @PathVariable String exam,
       @PathVariable String reason) {
       
       InhaalExamen examen = new InhaalExamen(name, exam, reason);
       
       service.hset("InhaalExamen", (Map<String, String>) examen);
       return new ResponseEntity<>(HttpStatus.OK);
   }
   
}
