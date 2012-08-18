package models;


import com.avaje.ebean.FetchConfig;
import play.Logger;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

    @Constraints.Required
    private String picture;

    @ManyToOne(cascade = CascadeType.ALL)
    private Album album;

    public static Model.Finder<Long, Photo> find = new Finder<Long, Photo>(Long.class, Photo.class);

    public Photo(String picture) {
        this.picture = picture;
    }

    public static boolean albumEmpty(String album){
        return find
                .fetch("album", "name", new FetchConfig().query())
                .where()
                .eq("album.name", album)
                .findList()
                .isEmpty();
    }

    public static Photo getFirst(String album){
        return find.fetch("album", "name", new FetchConfig().query()).where().eq("album.name", album).findList().get(0);
    }

    public static List<Photo> getRemaining(String album){
        List<Photo> list = find.fetch("album", "name", new FetchConfig().query()).where().eq("album.name", album).findList();
        list.remove(0);
        return list;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Long getId() {
        return id;
    }
}
