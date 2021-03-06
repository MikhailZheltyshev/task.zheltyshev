package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ResponseStatus {

    @SerializedName("status")
    @Expose
    protected String status;

    public ResponseStatus() {
    }

    public ResponseStatus(Integer sessionId, String status) {
        this.status = status;
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
                .append("status", status)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(status)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ResponseStatus)) {
            return false;
        }
        ResponseStatus rhs = ((ResponseStatus) other);
        return new EqualsBuilder()
                .append(status, rhs.status)
                .isEquals();
    }
}
