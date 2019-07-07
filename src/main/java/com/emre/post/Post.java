package com.emre.post;

import com.emre.user.User;

import java.util.Date;

public class Post {

    private Integer id;
    private Integer userId;
    private Date createdDate;
    private String post;

    public Post() {

    }

    public Post(Integer id, Integer userId, Date createdDate, String post) {
        this.id = id;
        this.userId = userId;
        this.createdDate = createdDate;
        this.post = post;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", createdDate=" + createdDate +
                ", post='" + post + '\'' +
                '}';
    }
}
