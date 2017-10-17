package com.css.msgserver.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.css.msgserver.jpa.pojo.BkOpenCard;

public interface BkOpenCardDao extends CrudRepository<BkOpenCard, String>,PagingAndSortingRepository<BkOpenCard, String> {
	
	
	public Page<BkOpenCard> findBySendStateAndZzlb(String sendState,String zzlb,Pageable pageable);
	
	
	public int countBySendState(String sendState);
	/**
	 * @param oldSendState 条件 ：状态
	 * @param filename     条件：文件名称
	 * @param newSendState 修改值：状态
	 * @return
	 */
	@Modifying 
	@Query("update BkOpenCard  set sendState= ?3 where sendState = ?1 and fileName = ?2")
	public int updateSendState(String oldSendState,String filename,String newSendState);
}
