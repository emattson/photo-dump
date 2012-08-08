package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.addPicForm;
import views.html.index;

public class Application extends Controller {
  
  public static Result index() {
    return ok(index.render("Marriage"));
  }

    public static Result addPictures(){
        return ok(
                addPicForm.render()
        );
    }
  
}