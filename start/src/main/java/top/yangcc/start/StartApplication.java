package top.yangcc.start;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yangcc.common.result.SimpleResult;

@SpringBootApplication

@RestController("/")
@Slf4j
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }


    @RequestMapping("/start")
    public SimpleResult<String> start(Integer age) {
        log.info("[StartApplication][start][{}]",age);
        String json = """
                {
                "name":"chalmery",
                "age": "%s"
                }
                """;
        String format = String.format(json, age);
        log.info("[StartApplication][end][{}]",format);
        return SimpleResult.buildSuccess(format);
    }


}
