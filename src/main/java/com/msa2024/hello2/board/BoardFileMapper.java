package com.msa2024.hello2.board;

import com.msa2024.hello2.entity.BoardFileVO;
import com.msa2024.hello2.entity.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardFileMapper {

    List<BoardFileVO> getList(BoardVO boardVO);
    BoardFileVO getBoardFile(int board_file_no);
    BoardFileVO getBoardFileVO(BoardVO boardVO);
    int delete(BoardFileVO boardFileVO);
    int insert(BoardFileVO boardFileVO);
}
