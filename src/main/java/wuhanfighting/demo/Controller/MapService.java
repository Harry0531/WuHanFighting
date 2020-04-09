package wuhanfighting.demo.Controller;
import java.io.IOException;
import java.nio.file.WatchEvent;
import java.util.Collections;
import  java.util.List;
import java.util.Map;

import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wuhanfighting.demo.Controller.WebSocket.WebSocketTest;

@Service
public class MapService {

    @Autowired
    MapDao mapDao;

    @Autowired
    WebSocketTest webSocketTest;

    public List<MapEntity> getMapStatus(){
        return mapDao.getMapStatus();
    }

    public void updateStatus(MapEntity mapEntity){
        mapDao.updateStatus(mapEntity);
    }
    public  void reset(){
        mapDao.reset();
    }
    public  void lightAll(){
        List<MapEntity> mapEntities = mapDao.getMapStatus();
        Collections.shuffle(mapEntities);
        for (MapEntity map:mapEntities) {
            if(map.getStatus() == 0){
                map.setStatus(1);
                map.setName("测试人"+mapEntities.indexOf(map));
                map.setSchool("测试学校"+mapEntities.indexOf(map));
                map.setComment("测试评论"+mapEntities.indexOf(map));
                mapDao.updateStatus(map);
            }
            try {
                webSocketTest.lightAllTest();
                Thread.sleep(3000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
