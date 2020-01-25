package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UserCredentials {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    public UserCredentials() {
    }

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("[username=%s, password=%s]", username, password);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(password)
                .append(username)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserCredentials)) {
            return false;
        }
        UserCredentials rhs = ((UserCredentials) other);
        return new EqualsBuilder()
                .append(password, rhs.password)
                .append(username, rhs.username)
                .isEquals();
    }
}
