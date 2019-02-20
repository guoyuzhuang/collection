package com.luokeke.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.luokeke.pojo.Log;

@Mapper
public interface LogMapper {

	public int insertLog(Log log);
}
