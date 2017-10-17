package com.css.msgserver.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.css.msgserver.jpa.pojo.BkOpenCardResult;

public interface BkOpenCardResultDao extends CrudRepository<BkOpenCardResult, String> {
	public Page<BkOpenCardResult> findBySendState(String sendState,Pageable pageable);
	public Integer countBySendState(String sendState);
}
