package controllers;

import models.Photo;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;

import java.io.File;
import java.util.List;

public class Application extends Controller {
  
    public static Result index() {
    return ok(index.render(form(Photo.class)));
    }

    public static Result savePicture(){
        Http.MultipartFormData body = request().body().asMultipartFormData();
        List<Http.MultipartFormData.FilePart> files = body.getFiles();
        if (files != null){
            for(Http.MultipartFormData.FilePart picture: files){
                String filename = picture.getFilename();
                String contentType = picture.getContentType();
                String myUploadPath = "public/images/uploaded/";
                File file = picture.getFile();
                if (file.renameTo(new File(myUploadPath, filename))){
                    Photo photo = new Photo("images/uploaded/" + filename);
                    photo.save();
                    Logger.debug(photo.getPicture());
                }


            }
            return redirect(routes.Application.index());
        } else {
            flash("error", "Missing file");
            return redirect(routes.Application.index());
        }
    }
}