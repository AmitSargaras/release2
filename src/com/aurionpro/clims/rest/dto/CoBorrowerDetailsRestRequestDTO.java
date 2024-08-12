package com.aurionpro.clims.rest.dto;
import java.io.Serializable;

public class CoBorrowerDetailsRestRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String coBorrowerLiabId;
	
	private String coBorrowerName;

	public String getCoBorrowerLiabId() {
		return coBorrowerLiabId;
	}

	public void setCoBorrowerLiabId(String coBorrowerLiabId) {
		this.coBorrowerLiabId = coBorrowerLiabId;
	}

	public String getCoBorrowerName() {
		return coBorrowerName;
	}

	public void setCoBorrowerName(String coBorrowerName) {
		this.coBorrowerName = coBorrowerName;
	}
}
