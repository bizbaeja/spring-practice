package com.msa2024.hello2.board;

import com.msa2024.hello2.entity.BoardTokenVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardTokenMapper {

    int insert(String board_token);
    int updateStatusComplate(String board_token);
}
