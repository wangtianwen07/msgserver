package com.css.msgserver.bank.ftp;

import java.io.File;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.css.msgserver.utils.URLUtil;

@Component
public class UploadRoute extends RouteBuilder {
    
    private static Logger logger = LoggerFactory.getLogger( UploadRoute.class );

    @Value("${ftp.server.send.info}")
    private String sftpServer;
    @Value("${ftp.local.send.info}")
    private String uploadLocation;
    @Override
    public void configure() throws Exception {
        from( uploadLocation ).to(  sftpServer ).log(LoggingLevel.INFO, logger, "Uploaded file  "+URLUtil.getUrl(uploadLocation)+File.separator+"${file:name} to ftpserver:"+URLUtil.getUrl(sftpServer)+" complete.");
    }
}
