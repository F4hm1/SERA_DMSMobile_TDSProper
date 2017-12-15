package com.serasiautoraya.tdsproper.NotificatonList;

import com.serasiautoraya.tdsproper.BaseModel.Model;

/**
 * Created by Randi Dwi Nandra on 11/04/2017.
 */

public class NotificationResponseModel extends Model {

    private String NotificationId;
    private String Title;
    private String Date;
    private String Message;

    public NotificationResponseModel(String notificationId, String title, String date, String message) {
        NotificationId = notificationId;
        Title = title;
        Date = date;
        Message = message;
    }

    public String getNotificationId() {
        return NotificationId;
    }

    public String getTitle() {
        return Title;
    }

    public String getDate() {
        return Date;
    }

    public String getMessage() {
        return Message;
    }
}
