package wuhanfighting.demo.Controller;
import java.io.IOException;
import java.nio.file.WatchEvent;
import java.util.*;

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

    List<MapEntity> entities = new ArrayList<>();

    public List<MapEntity> getMapStatus(){
        return mapDao.getMapStatus();
    }

    public void updateStatus(MapEntity mapEntity){
        mapEntity.setTime(new Date());
        mapDao.updateStatus(mapEntity);
    }
    public  void reset() throws IOException {
        mapDao.reset();
        webSocketTest.lightAllTest();
    }
    public  void lightAll(){
        List<MapEntity> mapEntities = mapDao.getMapStatus();
        Collections.shuffle(mapEntities);
        MapEntity temp;
        for (MapEntity map:mapEntities) {
            if(map.getStatus() == 0){
                temp=generatePerson(map.getIndex());
                temp.setLine(map.getLine());
                temp.setCol(map.getCol());
                temp.setIndex(map.getIndex());
                mapDao.updateStatus(temp);
            }
            try {
                webSocketTest.lightAllTest();
                    Thread.sleep(1000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public MapEntity generatePerson(int index){
       if(entities.size() == 0) {
           entities.add(new MapEntity("吴桐", "岁月静好是因为有医护人员替我们负重前行!武汉，加油!", "北京理工大学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("史继筠", "希望早日度过难关!武汉加油，全国人民加油", "北京理工大学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("奚英伦", "中华民族五千年，秉承中华精神， 延续至今，多少大风大浪都闯过来了，这次同样会有惊无险，战胜新冠状病毒，勇往直前!", "北京理工大学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("袁武", "我相信，不久的将来，我们很快重新恢复热闹与美好。", "北京理工大学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("宋雪", "同舟共济，共渡难关，武汉加油，医护人员平安归来!", "北京理工大学", 1, 1, 1, 1, new Date()));

           entities.add(new MapEntity("张佳明", "希望疫情早日过去，我们能走上街头繁花与共。", "北京理工大学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("李子彧", "武汉加油！", "北京理工大学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("任翔渝", "同舟共济武汉加油!丹巴加油!中国加油!共抗疫情!", "北京理工大学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("张航", "为武汉人民加油，让我们齐心协力，战胜疫情!", "北京理工大学", 1, 1, 1, 1, new Date()));

           entities.add(new MapEntity("朱长昊", "武汉今天也是有阳光作伴的一天，我们不孤单，要乐观!", "北京理工大学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("熊慧志", "千言万语汇成一句：中国加油，湖北加油，武汉加油。", "华中师范大学第一附属中学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("刘娟", "聚沙成塔，众志成城，佑我中华，武汉加油!", "华中师范大学第一附属中学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("屈哲", "抗击新冠肺炎病毒，胜利终将属于我们!", "华中师范大学第一附属中学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("王雪冰", "等你们胜利凯旋!加油、祝福、平安!", "华中师范大学第一附属中学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("安婧怡", "向抗击新型肺炎第一线的医护人员们致敬!", "华中师范大学第一附属中学", 1, 1, 1, 1, new Date()));

           entities.add(new MapEntity("郑晓姝", "团结一心，攻克疫情。减少流动，不添麻烦。自我保护，不耗资源。武汉加油，湖北加油。", "华中师范大学第一附属中学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("余行健", "希望武汉的疫情快点过去，让天空恢复蓝色，武汉加油！中国加油！", "华中师范大学第一附属中学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("左维", "一份爱心，一份力量，共抗疫情，武汉，加油！中国，加油！一份爱心一份力量，共抗疫情！", "华中师范大学第一附属中学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("黄诗绮", "等到来年春暖花开冰雪融化，我们就去武汉看樱花。武汉加油！", "华中师范大学第一附属中学", 1, 1, 1, 1, new Date()));
           entities.add(new MapEntity("司原杰", "好心态，很重要，疫情面前，不恐慌，一起加油，无往不胜！", "华中师范大学第一附属中学", 1, 1, 1, 1, new Date()));
       }
       return entities.get(index-1);
    }
}
