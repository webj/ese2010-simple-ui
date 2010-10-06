package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Question;
import models.User;
import play.Play;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;

public class CreateUser extends Controller {

	public static void create() {
		render();
	}
	
	public static void createUser(@Required String name,@Required String email,@Required String password,@Required String password2){
		
	   String failure;
	   
	  
	   
	   if(!password.equals(password2)){
		   failure = "passwords are not the same";
	   }
	   
	   if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
		   
		   failure = "you forgot one or more thing";
		   
	   }
	   
	   if(User.userExists(name,email)){
		   failure = "user alredy exists";
	   }
	   
	   else{
		   failure = "user created";
		   User.createUser(name, email, password);
	   }	
		
	   render(failure);
	}

}

