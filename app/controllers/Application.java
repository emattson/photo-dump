package controllers;

import models.Album;
import models.Photo;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;

import java.io.File;
import java.util.List;

public class Application extends Controller {
  
    public static Result index(String album) {
        return ok(
                index.render(form(Photo.class), album)
        );
    }

    public static Result savePicture(){
        Form form = form().bindFromRequest();
        if (form.hasErrors()){
            Logger.error("bad request");
            return badRequest(index.render(form, "1"));
        }
        String albumName = form.field("album").value();
        Album album;
        if(Album.find.where().eq("name", albumName).findList().size() == 0) {
            album = new Album(albumName);
        } else {
            album = Album.find.byId(albumName);
        }
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
                    photo.setAlbum(album);
                    photo.save();
                    Logger.debug(photo.getPicture());
                }


            }
            return redirect(routes.Application.index(album.getName()));
        } else {
            flash("error", "Missing file");
            return redirect(routes.Application.index("Default"));
        }
    }
}