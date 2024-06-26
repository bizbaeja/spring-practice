package com.msa2024.hello2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
	private String uid;
	private String pwd;
	private String name;
	private String email;
}
