package com.mycompany.webapplicationsv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.webapplicationsv.model.HelpMessage;
import com.mycompany.webapplicationsv.util.DBConnection;

public class HelpMessageDAO
{

    public void createMessage(String userName, String message) throws SQLException
    {
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement st = conn.prepareStatement(
                "INSERT INTO help_messages(user_name, message) VALUES (?, ?)");
        st.setString(1, userName);
        st.setString(2, message);
        st.executeUpdate();
    }

    public List<HelpMessage> getRecentMessages(int limit) throws SQLException
    {
        List<HelpMessage> messages = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM help_messages ORDER BY submission_date DESC LIMIT ?");
        st.setInt(1, limit);
        ResultSet rs = st.executeQuery();

        while (rs.next())
        {
            HelpMessage message = new HelpMessage();
            message.setMessageId(rs.getInt("message_id"));
            message.setUserName(rs.getString("user_name"));
            message.setMessage(rs.getString("message"));
            message.setSubmissionDate(rs.getTimestamp("submission_date"));
            messages.add(message);
        }

        return messages;
    }
}
