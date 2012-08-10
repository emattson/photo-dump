package models;


import play.db.ebean.Model;
import play.Logger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.List;


/**
 * Created on 8/8/12 @ 12:08 PM.
 *
 * @author eli-mattson
 */

@Entity
public class Photo extends Model{

    @Id
    private Long id;

    private String title;
    private String description;
    private String picture;

    public static Model.Finder<Long, Photo> find = new Finder<Long, Photo>(Long.class, Photo.class);

    public static Photo getFirst(){
        return find.all().get(0);
    }

    public static List<Photo> getRemaining(){
        List<Photo> list = find.all();
        list.remove(0);
        return list;
    }

    public Photo(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        Logger.debug(picture);
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
