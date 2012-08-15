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
  
    public static Result index(String album, int n) {
        return ok(
                index.render(form(Photo.class), album, n)
        );
    }

    public static Result savePicture(){
        Form form = form().bindFromRequest("addPhoto");
        if (form.hasErrors()){
            Logger.error("bad request");
            return badRequest(index.render(form, "Default", 0));
        }
        String albumName = form.field("album").value();

        //hard code so we don't get null album names
        if(albumName.isEmpty())
            albumName = "Default";

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
                }


            }
            return index(album.getName(), 0);
        } else {
            flash("error", "Missing file");
            return index("Default", 0);
        }
    }

    public static Result editPhoto(Long id){
        Form<Photo> form = form(Photo.class).bindFromRequest("editPhoto");
        Photo photo = Photo.find.ref(id);
        photo.setTitle(form.field("title").value());
        photo.setDescription(form.field("description").value());
        photo.save();
        //updated photo
        return index(photo.getAlbum().getName(), 0);
    }

    public static Result deletePhoto(Long id){
        Photo photo = Photo.find.ref(id);
        String album = photo.getAlbum().getName();
        boolean deleted = new File("public/" + photo.getPicture()).delete();
        if (deleted){
            photo.setAlbum(null); //necessary to avoid foreign key constraints
            photo.delete();
            return index(album, 0);
        }
        Logger.error("Didn't delete photo: " + photo.getPicture());
        return index(album, 0);

    }

    public static Form<Photo> getFilledPhotoForm(Photo photo){
        return form(Photo.class).fill(photo);
    }
}