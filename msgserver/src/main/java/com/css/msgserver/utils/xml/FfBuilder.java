package com.css.msgserver.utils.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FfBuilder {
	private Template template;
	private Map<String,Object> bean;
	/**
	 * @param xmlpath  classpath:之后的路径
	 * @param obj		填充bean
	 * @throws Exception
	 */
	public FfBuilder(String xmlpath,Map<String,Object> obj)throws Exception{
		Configuration config =  new Configuration(Configuration.getVersion());
		config.setClassForTemplateLoading(this.getClass(), "/");  
		template = config.getTemplate(xmlpath, "UTF-8");// 报文模板
		bean=obj;
	}
	public String builderString()throws IOException, TemplateException{
		return builderStringBuffer().toString();
	}
	public StringBuffer builderStringBuffer()throws IOException, TemplateException{
		StringWriter out =new StringWriter();
		builderWriter(out);
		return out.getBuffer();
	}
	public void builderWriter(Writer out)throws IOException, TemplateException{
		template.process(bean, out);
	}
}
