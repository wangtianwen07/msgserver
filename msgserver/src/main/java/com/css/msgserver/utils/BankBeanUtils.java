package com.css.msgserver.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.css.msgserver.bank.model.BkBocType;
import com.css.msgserver.bank.model.IYwsjj;
import com.css.msgserver.conf.BankConf;
import com.css.msgserver.jpa.pojo.Pack;
import com.css.msgserver.jpa.pojo.PackType;



public class BankBeanUtils implements IYwsjj{
	 // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) throws IllegalAccessException, InvocationTargetException {
        if (map == null || obj == null) {
            return;
        }
        BeanUtils.populate(obj, map);
    }
    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if(obj == null){
            return null;
        }        
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            // 过滤class属性
            if (!key.equals("class")) {
                // 得到property对应的getter方法
                Method getter = property.getReadMethod();
                Object value = getter.invoke(obj);
                if(value==null){
                	map.put(key, "");                    	
                }else{
                	
                	map.put(key, value);
                }
            }

        }

        return map;

    }
    public static String toStr(Object o){
    	if(o==null){
    		return "";
    	}
    	return o.toString();
    }
    /**
     * 得到打包好的数据以 "|" 隔开，需要 pack标签
     * @param bean
     * @return
     * @throws Exception
     */
    public static String enPackData(Object bean,PackType type)throws Exception {
    	StringBuffer re=new StringBuffer("");
    	Field[] fs=bean.getClass().getDeclaredFields();
    	String[] fild=new String[fs.length];
		for(Field f:fs){
			buildField(fild,f,type);
		}
		Map<String, Object> vals=transBean2Map(bean);
		for(String k:fild){
			if(k==null)continue;
			re.append(toStr(vals.get(k))+VALUE_STEP);
		}
    	return re.substring(0,re.length()-1);
    }
    public static String enPackData(Object bean)throws Exception {
    	return enPackData(bean,PackType.inout);
    }
    /**
     * @throws  
     * @throws Exception 
     * 
     * 
     */
    public static <T> T dePackData(Class<T> t,String packData) throws PackDataException{
    	return dePackData(t,packData,PackType.inout);
    }
    public static <T> T dePackData(Class<T> t,String[] packData) throws PackDataException{
    	return dePackData(t,packData,PackType.inout);
    }
    public static <T> T dePackData(Class<T> t,String packData,PackType type) throws PackDataException{
    	//值
    	String[] val=packData.split("\\"+VALUE_STEP);
		return dePackData(t,val,type);
    }
    public static <T> T dePackData(Class<T> t,String[] val,PackType type) throws PackDataException{
    	//值
    	T obj=null;
		try {
			obj = t.newInstance();
			Field[] fs=t.getDeclaredFields();
			List<Field> res=new ArrayList<Field>();
			for(Field f:fs){
				if(f.isAnnotationPresent(Pack.class)){
					res.add(f);
				}
			}
			//属性名称
			String[] key=new String[res.size()];
			for(Field f:res){
				buildField(key,f,type);
			}
			Map<String,Object> pa=toMap(key,val);
			transMap2Bean(pa,obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PackDataException("数据包解包异常!");
		} 
    	return obj;
    }
    
    private static Map<String,Object> toMap(String[] keys,String[] vals){
    	Map<String,Object> pa=new HashMap<String,Object>();
    	if(keys.length<vals.length){
    		for(int i=0;i<keys.length;i++){
    			pa.put(keys[i],vals[i]);
    		}
    	}else{
    		for(int i=0;i<vals.length;i++){
    			pa.put(keys[i],vals[i]);
    		}
    	}
    	return pa;
    }
    /**
     * 设置需要打包的列
     * @param field
     * @param f
     */
    private static void buildField(String[] field,Field f,PackType type){
    	if(f.isAnnotationPresent(Pack.class)){
    		Pack pack=f.getAnnotation(Pack.class);
    		if(type!= PackType.inout && pack.type()!=PackType.inout && pack.type()!=type){
    			//不为输入输出且类型不相等
    			return;
    		}
	    	int i=pack.order();
	    	field[i]=f.getName();
    	}
    }
    public static List<String> getNoPackTypeFields(Class t){
    	List<String> arr=new ArrayList<String>();
    	Field[] fs=t.getDeclaredFields();
    	for(Field f:fs){
    		if(!f.isAnnotationPresent(Pack.class)){
    			arr.add(f.getName());
    		}
    	}
    	return arr;
    }
    public static String buildFHBZ(boolean fhbz,String fhsbxx){
    	String re="";
    	if(fhbz){
    		//成功
    		re=BkBocType.FHBZ_OK+VALUE_STEP+fhsbxx;
    	}else{
    		//失败
    		re=BkBocType.FHBZ_ERROR+VALUE_STEP+fhsbxx;
    	}
    	return re;
    }
    /**
     * 打包给中行的数据并加密
     * @param xml
     * @return
     * @throws Exception 
     */
    public static String enBocXml(StringBuffer xml) throws DecryptException{
    	return enBocXml(xml,BkBocType.JYDW_MY);
    }
    public static String enBocXml(StringBuffer xml,String xym) throws DecryptException{
    	String re=xml.toString();
    	//加密
    	boolean sec=BankConf.getBoolean("bank.boc.sec");
    	if(sec){
    		String key=BankConf.get("bank.boc.despwd");
    		re= DesUtil.encrypt(re, key);
    	}
    	StringBuffer sb=new StringBuffer(xym);
    	sb.append(re);
    	//
    	return sb.toString();
    }
    /**
     * 解包给中行的数据并解密
     * @param xml
     * @return
     * @throws Exception 
     */
    public static String deBocXml(String xml) throws DecryptException{
    	//
    	return deBocXml(xml,BkBocType.JYDW_MY.length());
    }
    public static String deBocXml(String xml,int len) throws DecryptException{
    	StringBuffer sb=new StringBuffer(xml);
    	String re=sb.substring(len);
    	//解密
    	boolean sec=BankConf.getBoolean("bank.boc.sec");
    	if(sec){
    		String key=BankConf.get("bank.boc.despwd");
    		return DesUtil.decrypt(re, key);
    	}
    	//
    	return re;
    }
    /**
     * 得到中国银行通道响应码
     * @param xml
     * @return
     */
    public static String getBocTdxy(String xml)  {
    	//
    	StringBuffer sb=new StringBuffer(xml);
    	String re=sb.substring(0,2);
    	//
    	return re;
    }
}
