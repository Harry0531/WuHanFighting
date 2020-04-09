package wuhanfighting.demo.Controller.WebSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wuhanfighting.demo.Controller.MapEntity;
import wuhanfighting.demo.Controller.MapService;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.util.List;
/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
// 当是Springboot项目时需增加@Component注解,初始化声明WebSocket
@Component
@ServerEndpoint("/wb")
public class WebSocketTest {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    private static MapService mapService;

    @Autowired
    public void setMapService(MapService mapService) {
        WebSocketTest.mapService = mapService;
    }

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        List<MapEntity> mapEntityList = mapService.getMapStatus();
        StringBuffer tmp = new StringBuffer(JSON.toJSONString(mapEntityList));
        try {
            this.sendMessage(tmp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        MapEntity now = new MapEntity();
        JSONObject nowJson = JSON.parseObject(message);
        now.setName((String)nowJson.get("name"));
        now.setComment((String)nowJson.get("comment"));
        now.setLine((Integer) nowJson.get("line"));
        now.setCol((Integer) nowJson.get("col"));
        now.setIndex((Integer) nowJson.get("index"));
        now.setStatus((Integer) nowJson.get("status"));
        now.setSchool((String) nowJson.get("school"));

        mapService.updateStatus(now);
        System.out.println(now.getName()+"点亮了第"+now.getLine()+"行第"+now.getCol()+"列的图片\n"+now.getComment());
        List<MapEntity> mapEntityList = mapService.getMapStatus();
        StringBuffer tmp = new StringBuffer(JSON.toJSONString(mapEntityList));

        //群发消息
        for(WebSocketTest item: webSocketSet){
            try {
                item.sendMessage(tmp.toString());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
    /**
     * 向所有用户发送消息
     * @param
     * @throws IOException
     */
    public void lightAllTest() throws IOException{
        List<MapEntity> mapEntityList = mapService.getMapStatus();
        StringBuffer tmp = new StringBuffer(JSON.toJSONString(mapEntityList));
        for(WebSocketTest item: webSocketSet){
            try {
                item.session.getBasicRemote().sendText(tmp.toString());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }
}