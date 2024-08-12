/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBCollateral.java,v 1.35 2006/09/15 08:30:10 hshii Exp $
 */
package com.aurionpro.clims.rest.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralLimitMapComparator;
import com.integrosys.cms.app.collateral.bus.IAddtionalDocumentFacilityDetails;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.collateral.bus.IInstrument;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.sharesecurity.bus.IShareSecurity;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
/**
 * This class represents a Collateral entity.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.35 $
 * @since $Date: 2006/09/15 08:30:10 $ Tag: $Name: $
 */
public class CollateralRestRequestDTO {
	
	private List<RestApiHeaderRequestDTO> headerDetails;		
	
	private List<CollateralDetailslRestRequestDTO> bodyDetails;
	
	public List<RestApiHeaderRequestDTO> getHeaderDetails() {
		return headerDetails;
	}
	public void setHeaderDetails(List<RestApiHeaderRequestDTO> headerDetails) {
		this.headerDetails = headerDetails;
	}
	public List<CollateralDetailslRestRequestDTO> getBodyDetails() {
		return bodyDetails;
	}
	public void setBodyDetails(List<CollateralDetailslRestRequestDTO> bodyDetails) {
		this.bodyDetails = bodyDetails;
	}
}