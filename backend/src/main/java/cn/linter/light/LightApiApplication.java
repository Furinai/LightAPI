package cn.linter.light;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author wangxiaoyang
 */
@SpringBootApplication
@EnableCaching
public class LightApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightApiApplication.class, args);
    }

}