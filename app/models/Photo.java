package models;


import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Created on 8/8/12 @ 12:08 PM.
 *
 * @author eli-mattson
 */

@Entity
public class Photo extends Model{

    @Id
    @GeneratedValue
    Long id;

    private String title;
    private String description;

    @Lob
    private byte[] picture;

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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
