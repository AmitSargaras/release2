/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.aurionpro.clims.rest.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */


public class StockDetailRestResponseDTO {
	
	
	private String stockSecUniqueId;
	
	private String ClimsItemId;
	
	private List<StockLineDetailResponseDTO> lineDetails;

	public String getStockSecUniqueId() {
		return stockSecUniqueId;
	}

	public void setStockSecUniqueId(String stockSecUniqueId) {
		this.stockSecUniqueId = stockSecUniqueId;
	}

	public String getClimsItemId() {
		return ClimsItemId;
	}

	public void setClimsItemId(String climsItemId) {
		ClimsItemId = climsItemId;
	}

	public List<StockLineDetailResponseDTO> getLineDetails() {
		return lineDetails;
	}

	public void setLineDetails(List<StockLineDetailResponseDTO> lineDetails) {
		this.lineDetails = lineDetails;
	}
	
	

	
}