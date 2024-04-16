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
public class BoardVO {
	
	private String bno;
	private String btitle;
	private String bcontent;
	private String member_id;
	private String bdate;
	private String view_count;
	private String bwriter;
	//게시물 토큰 변수 선언
	private String board_token;
	//첨부파일
	private MultipartFile file;


	//첨부파일
	private BoardFileVO boardFileVO;
}
