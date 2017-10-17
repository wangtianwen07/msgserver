package com.css.msgserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 */
@EnableTransactionManagement
@EnableJpaRepositories(basePackages={"com.css.msgserver.jpa.repository"})
@EnableScheduling
@SpringBootApplication
public class App 
{
	private static Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
        logger.info("debug:"+logger.isDebugEnabled());
        logger.info( "<<<中国软件接口服务-消息服务器启动完成>>>");
    }
}
