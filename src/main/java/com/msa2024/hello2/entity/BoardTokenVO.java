package com.msa2024.hello2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardTokenVO {

    private String board_token;
    private int   status;
    private String make_date;

}