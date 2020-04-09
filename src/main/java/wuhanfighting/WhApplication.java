package wuhanfighting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("wuhanfighting.demo.Controller")
@ComponentScan({"wuhanfighting.demo.Controller"})
public class WhApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhApplication.class, args);
    }

}
