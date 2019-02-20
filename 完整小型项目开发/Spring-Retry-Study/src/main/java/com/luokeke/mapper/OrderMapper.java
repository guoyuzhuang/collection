package com.luokeke.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.luokeke.pojo.Order;

@Mapper
public interface OrderMapper {

	public int insertOrder(Order order);
	
	public int paySuccess(@Param("orderNo") String orderNo, @Param("paySuccessTime") String paySuccessTime);
}
