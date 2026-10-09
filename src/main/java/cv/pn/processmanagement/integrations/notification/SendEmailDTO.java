package cv.pn.processmanagement.integrations.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendEmailDTO {

    public String from;
    public String msg;
    public String origin;
    public String subject;
    @JsonProperty("to")
    public String myto;



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMyto() {
        return myto;
    }

    public void setMyto(String myto) {
        this.myto = myto;
    }
}
