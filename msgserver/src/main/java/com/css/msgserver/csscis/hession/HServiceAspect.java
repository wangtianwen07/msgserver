package com.css.msgserver.csscis.hession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.css.msgserver.csscis.hession.token.service.ITokenService;

@Component  
@Aspect  
public class HServiceAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	@Pointcut("execution(* com.css.msgserver.*.hession.service.impl.*.*(..))")  
	public void executeService(){}  
	
	@Around("executeService()")  
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
	    if(pjp.getTarget() instanceof ITokenService){
	    	ITokenService tsi=(ITokenService)pjp.getTarget() ;
	    	if(tsi.validate()){
	    		return pjp.proceed();
	    	}else{
	    		//System.out.println("token错误!");
	    		logger.info("token错误!");
	    	}
	    }else{
	    	//System.out.println("非hessian接口!");
	    	logger.info("非hessian接口!");
	    }
	    return null;
	}
}
