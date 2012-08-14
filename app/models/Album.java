package models;

import play.Logger;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created on 8/10/12 @ 2:02 PM.
 *
 * @author eli-mattson
 */
@Entity
public class Album extends Model{
    @Id
    @Constraints.Required
    private String name;

    @OneToMany
    private List<Photo> photos;

    //Finder
    public static Model.Finder<String, Album> find = new Finder<String, Album>(String.class, Album.class);

    /**
     * constructor for making an album from a list of photos
     * @param name
     * @param photos
     */
    public Album(String name, List<Photo> photos) {
        this.name = name;
        this.photos = photos;
    }

    /**
     * constructor for making empty album
     * @param name
     */
    public Album (String name){
        this.name = name;
    }

    public static String getAlbumNames(){
        List<Object> names = find.findIds();
        String result = "[";
        for (Object name : names){
            result = result + " \"" + name + "\",";
        }
        if (!names.isEmpty())
            result = result.substring(0, result.length()-1);
        result = result + "]";
        Logger.debug(result);
        return result;
    }

    public void setName(String name){
        this.name = name;
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
        save();
    }

    public void addPhotos(List<Photo> photoList){
        photos.addAll(photoList);
        save();
    }

    public void deletePhoto(){
        //TODO
    }

    public List<Photo> getPhotos(){
        return photos;
    }


    public String getName() {
        return name;
    }

    public String toString(){
        String out = "[ ";
        for (Photo p : photos){
            out = out + " " + p.getId() + " " + p.getTitle() + ",";
        }
        out = out + " ]";
        return out;
    }
}
