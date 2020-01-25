package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ToDo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("personId")
    @Expose
    private Integer personId;
    @SerializedName("description")
    @Expose
    private String description;

    public ToDo() {
    }

    public ToDo(Integer id, Integer personId, String description) {
        this.id = id;
        this.personId = personId;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("personId", personId)
                .append("description", description)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(description)
                .append(personId)
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ToDo)) {
            return false;
        }
        ToDo rhs = ((ToDo) other);
        return new EqualsBuilder()
                .append(description, rhs.description)
                .append(personId, rhs.personId)
                .append(id, rhs.id)
                .isEquals();
    }
}
