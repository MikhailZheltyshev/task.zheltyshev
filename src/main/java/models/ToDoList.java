package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class ToDoList {

    @SerializedName("todoList")
    @Expose
    private List<ToDo> todoList = null;

    public ToDoList() {
    }

    public ToDoList(List<ToDo> todoList) {
        this.todoList = todoList;
    }

    public List<ToDo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<ToDo> todoList) {
        this.todoList = todoList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("todoList", todoList)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(todoList)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ToDoList)) {
            return false;
        }
        ToDoList rhs = ((ToDoList) other);
        return new EqualsBuilder()
                .append(todoList, rhs.todoList)
                .isEquals();
    }
}
