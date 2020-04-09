package wuhanfighting.demo.Controller;

import lombok.Data;

import java.util.Date;

@Data
public class MapEntity {
    String name;
    String comment;
    String school;
    Integer line;
    Integer col;
    Integer status;
    Integer index;
    Date time;
}
