package com.msa2024.hello2.board;

import java.util.List;

import com.msa2024.hello2.entity.BoardVO;
import com.msa2024.hello2.page.PageRequestVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface BoardMapper {

	List<BoardVO> getList(PageRequestVO pageRequestVO);
	int  getTotalCount(PageRequestVO pageRequestVO);
	BoardVO view(BoardVO boardVO);
	int incViewCount(BoardVO boardVO);
	int delete(BoardVO boardVO);
	int update(BoardVO boardVO);
	void allDelete();
	int insert(BoardVO boardVO);

}
