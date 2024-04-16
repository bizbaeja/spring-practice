package com.msa2024.hello2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardImageFileVO {

    private String board_image_file_id;
    private String board_token;
    private String bno;
    private String original_filename;
    private String real_filename;
    private String content_type;
    private long size;
    private String make_date;

    //업로드 파일
    private MultipartFile upload;


}