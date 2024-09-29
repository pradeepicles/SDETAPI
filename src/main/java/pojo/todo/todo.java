package pojo.todo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class todo {
    private int userId;
    private int id;
    private String title;
    private boolean completed;
}
