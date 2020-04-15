package application;

import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class MainApp implements ApplicationRunner {

    public static void main(String[] args) {

        SpringApplication.run(MainApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("Hello World from Application Runner");

    }

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }




 /*@Bean
public BCryptPasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }*/


}