package cv.pn.processmanagement.utilities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.micrometer.core.instrument.binder.http.HttpRequestTags;

import java.time.LocalDateTime;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse{

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();
    private boolean status;
    private String statusText;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Object> details;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pagination pagination;

    private String processNumber;

    public String getProcessNumber() {
        return processNumber;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void getStatusText(String statusText) {
        this.statusText = statusText;
    }


    public List<Object> getDetails() {
        return details;
    }

    public void setDetails(List<Object> details) {
        this.details = details;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }



    public Pagination getPagination() {
        if (pagination != null && pagination.getTotal() > 0) {
            return pagination;
        } else {
            return null;
        }
    }



    public APIResponse(buildAPIResponse build) {
        this.status = build.status;
        this.statusText = build.statusText;
        this.details = build.details;
        this.pagination = build.pagination;
        this.processNumber = build.processNumber;
    }



    public static class buildAPIResponse {
        boolean status;
        String statusText;
        List<Object> details;
        Pagination pagination;
        String processNumber;

        public buildAPIResponse setStatus(boolean status) {
            this.status = status;
            return this;
        }

        public buildAPIResponse setStatusText(String statusText) {
            this.statusText = statusText;
            return this;
        }

        public buildAPIResponse setDetails(List<Object> details) {
            this.details = details;
            return this;
        }

        public buildAPIResponse setPagination(Pagination pagination) {
            this.pagination = pagination;
            return this;
        }

        public buildAPIResponse setProcessNumber(String processNumber) {
            this.processNumber = processNumber;
            return this;
        }

        public APIResponse builder() {
            return new APIResponse(this);
        }

        public boolean isStatus() {
            return status;
        }

        public String getStatusText() {
            return statusText;
        }

        public List<Object> getDetails() {
            return details;
        }

        public Pagination getPagination() {
            return pagination;
        }

        public String getProcessNumber() {
            return processNumber;
        }


    }
}
