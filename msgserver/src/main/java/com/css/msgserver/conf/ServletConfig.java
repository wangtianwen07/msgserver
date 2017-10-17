package com.css.msgserver.conf;

import org.apache.axis2.transport.http.AxisServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.css.msgserver.utils.FileUtils;

@Configuration
public class ServletConfig{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 
	 */
	@Bean 
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new AxisServlet(), "/services/*");
        bean.setLoadOnStartup(3);
        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
        String urlpath=FileUtils.getClassPath("axis2");
        logger.info("axis2.repositorygetResource.path="+this.getClass().getResource("").getPath());
        if(urlpath==null){
        	logger.info("axis2.repositorygetClassLoader.path="+this.getClass().getClassLoader().getResource("").getPath());
        	throw new RuntimeException("未找到/axis2路径!将打包好的jar/resources/下所有目录解压与jar包同目录下即可。");
        }
        String realWebInfPath = urlpath;
        bean.addInitParameter("axis2.repository.path",realWebInfPath);
        logger.info("axis2.repository.path="+realWebInfPath);
        bean.addInitParameter("axis2.xml.path",realWebInfPath+"/conf/axis2.xml");
        return bean;
    }
}
