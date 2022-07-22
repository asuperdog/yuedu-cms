package cn.edu.sziit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("cn.edu.sziit.mapper") // 这里还需要加上一个注解实现增删查改
public class ManagerServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(ManagerServiceApp.class, args);
    }
}
