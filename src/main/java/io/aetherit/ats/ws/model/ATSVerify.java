package io.aetherit.ats.ws.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ATSVerify {
	private String authenticationKey;
	private String email;
	private String oldPassword;
	private String password; 
}
