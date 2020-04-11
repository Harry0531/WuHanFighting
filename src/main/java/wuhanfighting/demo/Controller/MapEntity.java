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

    public MapEntity(String name,String comment,String school,Integer line,Integer col,Integer status,Integer index,Date time){
        this.name = name;
        this.comment = comment;
        this.school = school;
        this.line=line;
        this.col=col;
        this.status=status;
        this.index=index;
        this.time =time;
    }

    public MapEntity(){

    }
}
