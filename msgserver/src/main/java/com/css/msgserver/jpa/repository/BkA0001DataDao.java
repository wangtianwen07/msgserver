package com.css.msgserver.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.css.msgserver.jpa.pojo.BkA0001Data;

public interface BkA0001DataDao extends  CrudRepository<BkA0001Data, String>{
	public List<BkA0001Data> findBySendStateAndCzlxAndSender(String sendState,String czlx,String sender);
	public List<BkA0001Data> findBySendStateAndCzlxAndSenderAndWjsLessThan(String sendState,String czlx,String sender,Integer wjs);
	public Page<BkA0001Data> findBySendStateAndCzlxAndSender(String sendState,String czlx,String sender,Pageable pageable);
	public Integer countByCreateTimeBetween(Date min,Date max);
	public Integer countBySendStateAndCzlxAndSender(String sendState,String czlx,String sender);
}
