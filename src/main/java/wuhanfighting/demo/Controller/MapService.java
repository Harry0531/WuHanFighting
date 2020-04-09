package wuhanfighting.demo.Controller;
import  java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    @Autowired
    MapDao mapDao;

    public List<MapEntity> getMapStatus(){
        return mapDao.getMapStatus();
    }

    public void updateStatus(MapEntity mapEntity){
        mapDao.updateStatus(mapEntity);

    }
}
