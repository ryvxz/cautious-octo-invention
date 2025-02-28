package com.mycompany.webapplicationsv.model;

import java.sql.Timestamp;

public class HelpMessage
{

    private int messageId;
    private String userName;
    private String message;
    private Timestamp submissionDate;

    public HelpMessage()
    {
    }

    public HelpMessage(int messageId, String userName, String message, Timestamp submissionDate)
    {
        this.messageId = messageId;
        this.userName = userName;
        this.message = message;
        this.submissionDate = submissionDate;
    }

    public int getMessageId()
    {
        return messageId;
    }

    public void setMessageId(int messageId)
    {
        this.messageId = messageId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Timestamp getSubmissionDate()
    {
        return submissionDate;
    }

    public void setSubmissionDate(Timestamp submissionDate)
    {
        this.submissionDate = submissionDate;
    }
}
