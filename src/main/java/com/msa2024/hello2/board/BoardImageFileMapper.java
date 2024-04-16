package com.msa2024.hello2.board;

import com.msa2024.hello2.entity.BoardImageFileVO;
import com.msa2024.hello2.entity.BoardVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardImageFileMapper {

    int insert(BoardImageFileVO boardImageFileVO);
    BoardImageFileVO findById(String board_image_file_id);
    int updateBoardNo(BoardVO board);
}
