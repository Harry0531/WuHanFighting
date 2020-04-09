package wuhanfighting.demo.Controller;


import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;
import  java.util.List;


public interface MapDao {
    public List<MapEntity> getMapStatus();
    public void updateStatus(MapEntity mapEntity);
    public void reset();
}
