package com.luokeke.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.luokeke.pojo.Transaction;

@Mapper
public interface TransactionMapper {

	public int insertTransaction(Transaction transaction);

	public int notifySuccess(@Param("orderNo") String orderNo, @Param("notifySuccessTime") String notifySuccessTime);
	
	public int notifyFail(@Param("orderNo") String orderNo, @Param("notifySuccessTime") String notifySuccessTime);
	

}
