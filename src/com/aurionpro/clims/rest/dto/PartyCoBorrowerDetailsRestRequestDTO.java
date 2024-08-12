package com.aurionpro.clims.rest.dto;

import java.util.List;

import com.integrosys.cms.app.ws.dto.CoBorrowerDetailsRequestDTO;

public class PartyCoBorrowerDetailsRestRequestDTO {
	
	private List<CoBorrowerDetailsRestRequestDTO> coBorrowerDetails;

	public List<CoBorrowerDetailsRestRequestDTO> getCoBorrowerDetails() {
		return coBorrowerDetails;
	}

	public void setCoBorrowerDetails(List<CoBorrowerDetailsRestRequestDTO> coBorrowerDetails) {
		this.coBorrowerDetails = coBorrowerDetails;
	}

	

}
