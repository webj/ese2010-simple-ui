package controllers;
 
import play.*;
import play.mvc.*;

import play.mvc.With;

@With(Secure.class)
public class Users extends CRUD {

}
