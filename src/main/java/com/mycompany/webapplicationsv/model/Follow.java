package com.mycompany.webapplicationsv.model;
public class Follow
{

    private String userName;
    private String follow1;
    private String follow2;
    private String follow3;

    public Follow()
    {
    }

    public Follow(String userName, String follow1, String follow2, String follow3)
    {
        this.userName = userName;
        this.follow1 = follow1;
        this.follow2 = follow2;
        this.follow3 = follow3;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getFollow1()
    {
        return follow1;
    }

    public void setFollow1(String follow1)
    {
        this.follow1 = follow1;
    }

    public String getFollow2()
    {
        return follow2;
    }

    public void setFollow2(String follow2)
    {
        this.follow2 = follow2;
    }

    public String getFollow3()
    {
        return follow3;
    }

    public void setFollow3(String follow3)
    {
        this.follow3 = follow3;
    }
}
