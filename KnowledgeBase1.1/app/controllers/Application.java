package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Question;
import models.User;
import play.Play;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

public class Application extends Controller {

	public static void index() {

		ArrayList<Question> questions= new ArrayList<Question>();
		
			for(Question question:User.questions){
				questions.add(question);
			}
				
		
		render(questions);
	}

}

