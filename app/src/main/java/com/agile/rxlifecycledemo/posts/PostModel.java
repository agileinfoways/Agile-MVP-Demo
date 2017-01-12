package com.agile.rxlifecycledemo.posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by agile-01 on 8/17/2016.
 */
public class PostModel implements PostsMVP.Model {

    @SerializedName("userId")
    @Expose
    private long userId;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;

    /**
     * @return The userId
     */
    @Override
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId The userId
     */
    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return The id
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The body
     */
    @Override
    public String getBody() {
        return body;
    }

    /**
     * @param body The body
     */
    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
