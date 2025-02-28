package com.mycompany.webapplicationsv.model;

import java.sql.Timestamp;

public class Post
{

    private int postId;
    private String userName;
    private String postContent;
    private Timestamp postDate;
    private int postOrder;

    public Post()
    {
    }

    public Post(int postId, String userName, String postContent, Timestamp postDate, int postOrder)
    {
        this.postId = postId;
        this.userName = userName;
        this.postContent = postContent;
        this.postDate = postDate;
        this.postOrder = postOrder;
    }

    public int getPostId()
    {
        return postId;
    }

    public void setPostId(int postId)
    {
        this.postId = postId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPostContent()
    {
        return postContent;
    }

    public void setPostContent(String postContent)
    {
        this.postContent = postContent;
    }

    public Timestamp getPostDate()
    {
        return postDate;
    }

    public void setPostDate(Timestamp postDate)
    {
        this.postDate = postDate;
    }

    public int getPostOrder()
    {
        return postOrder;
    }

    public void setPostOrder(int postOrder)
    {
        this.postOrder = postOrder;
    }
}
