package com.hyunyong.myapplication.data;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "step")
public class Step implements Serializable {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "short_description")
    private String shortDescription;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "video_url")
    private String videoURL;

    @ColumnInfo(name = "thumbnail_url")
    private String thumbnailURL;

    public Step(int id,
                String shortDescription,
                String description,
                String videoURL,
                String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
