package com.msa2024.hello2.code;

import java.util.List;

import com.msa2024.hello2.entity.CodeVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CodeMapper {

	List<CodeVO> getList();
}
