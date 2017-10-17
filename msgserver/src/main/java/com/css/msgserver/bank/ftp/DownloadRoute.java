package com.css.msgserver.bank.ftp;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * 
 *自动下载用于从指定目录自动下载 
 * 实际业务场景不适用,只能手动下载
 */
//@Component
public class DownloadRoute extends RouteBuilder {
    
    private static Logger logger = LoggerFactory.getLogger( DownloadRoute.class );

    @Value("${ftp.server.receive.info}")
    private String sftpServer;
    @Value("${ftp.local.receive.info}")
    private String downloadLocation;
    @Override
    public void configure() throws Exception {
        from( sftpServer ).to(  downloadLocation ).log(LoggingLevel.INFO, logger, "Downloaded file ${file:name} complete.");
    }

}
