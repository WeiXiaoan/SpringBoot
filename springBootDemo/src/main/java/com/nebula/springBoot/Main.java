package com.nebula.springBoot;


import com.nebula.springBoot.interceptor.LoginInterceptor;
import com.nebula.springBoot.support.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by SmallAnn on 2018/4/20
 */
@SpringBootApplication
@EnableScheduling
public class Main extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Configuration
    static class WebMvcConfigurer extends WebMvcConfigurerAdapter {
        public void addInterceptors(InterceptorRegistry registry) {
            Log.login.info("加载拦截器……");
            registry.addInterceptor(new LoginInterceptor());
//                    .addPathPatterns("/serverList/**")
//                    .excludePathPatterns("/serverList/login");
        }
    }
}