package cn.edu.sziit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.edu.sziit.mapper")
public class SolrApp {
    public static void main(String[] args) {
        SpringApplication.run(SolrApp.class, args);
    }
}
