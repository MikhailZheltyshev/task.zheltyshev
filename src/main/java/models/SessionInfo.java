package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SessionInfo {

    @SerializedName("sessionId")
    @Expose
    private Integer sessionId;
    @SerializedName("status")
    @Expose
    private String status;

    public SessionInfo() {
    }

    public SessionInfo(Integer sessionId, String status) {
        super();
        this.sessionId = sessionId;
        this.status = status;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sessionId", sessionId)
                .append("status", status)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(sessionId)
                .append(status)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SessionInfo)) {
            return false;
        }
        SessionInfo rhs = ((SessionInfo) other);
        return new EqualsBuilder()
                .append(sessionId, rhs.sessionId)
                .append(status, rhs.status)
                .isEquals();
    }
}
