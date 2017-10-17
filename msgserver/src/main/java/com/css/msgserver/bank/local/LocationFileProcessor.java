package com.css.msgserver.bank.local;

import java.io.File;
import java.io.RandomAccessFile;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.css.msgserver.bank.service.IDealServcie;
import com.css.msgserver.bank.service.IOpenCardResultService;

/**
 * @author pc
 *本地文件处理器处理银行返馈的数据
 */
@Component
public class LocationFileProcessor implements Processor {

    private static Logger logger = LoggerFactory.getLogger( LocationFileProcessor.class );
    
    
    @Autowired
    IDealServcie dealService;//业务逻辑处理组件
    @Autowired
    IOpenCardResultService openCardResultService;//业务逻辑处理组件
    @Override
    public void process(Exchange exchange) throws Exception{
        GenericFileMessage<RandomAccessFile> inFileMessage = (GenericFileMessage<RandomAccessFile>) exchange.getIn();
        //
        GenericFile<RandomAccessFile> ra= inFileMessage.getGenericFile();
        String filePath = ra.getAbsoluteFilePath();//文件路径
        String fileName=ra.getFileName();
        //
        logger.info(filePath);//文件的绝对路径
        if(fileName.startsWith(FileType.PREFIX_ERR)){
        	//错误文档,什么也不做
        	logger.info("错误文档:"+filePath);
        }else if(fileName.startsWith(FileType.PREFIX_OPENCARD)){
        	openCardResultService.saveOpenCardResult(new File(filePath));
        }else{
        	//未定义不操作
        	logger.info("未定义操作:"+filePath);
        }
        //
    }

}
