package com.css.msgserver.jpa.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.css.msgserver.jpa.pojo.BkA0001Ywsjj;

public interface BkA0001YwsjjDao extends CrudRepository<BkA0001Ywsjj, String> {
	public List<BkA0001Ywsjj> findByBkA0001DataUuid(String bkA0001DataUuid);
	public int countByWjm(String wjm);
	public Integer countByCreateTimeBetween(Date min,Date max);
}
