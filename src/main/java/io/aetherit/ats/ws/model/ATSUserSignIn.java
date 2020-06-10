package io.aetherit.ats.ws.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSUserSignIn {
	@NotNull
	private String cntryCode;
	@NotNull
	private String name;
	@NotNull
    private String password;
}
