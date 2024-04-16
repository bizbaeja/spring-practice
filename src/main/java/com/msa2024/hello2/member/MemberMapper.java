package com.msa2024.hello2.member;

import com.msa2024.hello2.entity.MemberVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MemberMapper {

	MemberVO login(MemberVO boardVO);

}
