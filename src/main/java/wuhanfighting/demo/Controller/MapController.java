package wuhanfighting.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import wuhanfighting.demo.Controller.WebSocket.WebSocketTest;

import java.io.IOException;

@Controller
@RequestMapping("test")
public class MapController {

    @Autowired
    MapService mapService;

    @ResponseBody
    @RequestMapping(value = "/lightAll",method = RequestMethod.GET)
    public Object lightAll(){
        mapService.lightAll();
        return "Success";
    }
    @ResponseBody
    @RequestMapping(value = "/reset",method = RequestMethod.GET)
    public Object reset(){
        try {
            mapService.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Success";
    }
}
