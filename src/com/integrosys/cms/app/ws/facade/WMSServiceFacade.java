package com.integrosys.cms.app.ws.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.springframework.beans.factory.annotation.Autowired;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.ws.aop.CLIMSWebServiceMethod;
import com.integrosys.cms.app.ws.common.CLIMSWebService;
import com.integrosys.cms.app.ws.common.ValidationUtility;
import com.integrosys.cms.app.ws.dto.SecurityDetailsDTOMapper;
import com.integrosys.cms.app.ws.dto.WMSDTOMapper;
import com.integrosys.cms.app.ws.dto.WMSRequestDTO;
import com.integrosys.cms.app.ws.dto.WMSResponseDTO;
import com.integrosys.cms.app.ws.jax.common.CMSFault;
import com.integrosys.cms.app.ws.jax.common.CMSValidationFault;
import com.integrosys.cms.app.ws.jax.common.WMSException;
import com.integrosys.cms.batch.eod.EndOfDaySyncMastersServiceImpl;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

public class WMSServiceFacade {

	@Autowired
	private WMSDTOMapper wmsDTOMapper;

	@Autowired
	private SecurityDetailsDTOMapper securityDetailsDTOMapper;

	private ILimitJdbc limitJdbc;

	
	public ILimitJdbc getLimitJdbc() {
		return limitJdbc;
	}

	public void setLimitJdbc(ILimitJdbc limitJdbc) {
		this.limitJdbc = limitJdbc;
	}
	
	@CLIMSWebServiceMethod
	public WMSResponseDTO  updateWMSDetails(WMSRequestDTO  wmsRequestDTO) throws CMSValidationFault, CMSFault{
		DefaultLogger.debug(this, "inside updateWMSDetails");
		
		WMSResponseDTO wmsResponseDTO = new WMSResponseDTO();
		
		String systemId = "";
		String lineNo = "";
		String serialNo = "";
		String liabBranch = "";
		String releasedAmount = "";
		String closeLineFlag = "";
		String mandatoryIssue = "";
		
		String updateStatus = "";
		String remarks = "";
		
		if(wmsRequestDTO.getSystemId() == null || wmsRequestDTO.getSystemId().trim().isEmpty()) {
			mandatoryIssue = "SystemId ";
		}else {
			systemId = wmsRequestDTO.getSystemId();
		}
		
		if(wmsRequestDTO.getLineNo() == null || wmsRequestDTO.getLineNo().trim().isEmpty()) {
			mandatoryIssue = mandatoryIssue +  " Line No. ";
		}else {
			lineNo = wmsRequestDTO.getLineNo();
		}
		
		if(wmsRequestDTO.getSerialNo() == null || wmsRequestDTO.getSerialNo().trim().isEmpty()) {
			mandatoryIssue = mandatoryIssue +  "Serial No. ";
		}else {
			serialNo = wmsRequestDTO.getSerialNo();
		}
		
		if(wmsRequestDTO.getLiabBranch() == null || wmsRequestDTO.getLiabBranch().trim().isEmpty()) {
			mandatoryIssue = mandatoryIssue + "Liab Branch ";
		}else {
			liabBranch = wmsRequestDTO.getLiabBranch();
		}
		
		remarks = "Facility System Id : "+systemId+", Line no. : "+lineNo+", Serial no. : "+serialNo+", Liab Branch : "+liabBranch+", ";
		
		if(mandatoryIssue.length() > 0) {
			updateStatus = "FAIL";
			remarks = remarks + mandatoryIssue +" Mandatory";
			wmsResponseDTO.setResponseStatus(updateStatus);
			wmsResponseDTO.setRemarks(remarks);				
			return wmsResponseDTO;
		}
		
		if(wmsRequestDTO.getReleasedAmount() == null  || wmsRequestDTO.getReleasedAmount().trim().isEmpty()) {
			releasedAmount = "0";			
		}else {
			releasedAmount = wmsRequestDTO.getReleasedAmount();			
		}
		
		if(wmsRequestDTO.getCloseLineFlag() == null || wmsRequestDTO.getCloseLineFlag().trim().isEmpty()) {
			closeLineFlag = "N";
		}else {
			closeLineFlag = wmsRequestDTO.getCloseLineFlag();
		}
		
		WMSRequestDTO wmsDTOFromSystem = wmsDTOMapper.getDTOWithActualValues(getLimitJdbc(),systemId, lineNo, serialNo, liabBranch);
		
		if(null == wmsDTOFromSystem) {
			updateStatus = "FAIL";
			remarks = remarks +" Invalid Line";
			wmsResponseDTO.setResponseStatus(updateStatus);
			wmsResponseDTO.setRemarks(remarks);				
			return wmsResponseDTO;
		}
		
		String facilitySystem = wmsDTOFromSystem.getFacilitySystem();
		String lineStatus = wmsDTOFromSystem.getStatus();	

//		if(null == lineStatus || !lineStatus.equals("SUCCESS")) {
//			updateStatus = "FAIL";
//			remarks = remarks +" Unable to update due to invalid Line Status :"+lineStatus;
//			wmsResponseDTO.setResponseStatus(updateStatus);
//			wmsResponseDTO.setRemarks(remarks);				
//			return wmsResponseDTO;
//		}	
		
		if(facilitySystem.equals("BAHRAIN") || facilitySystem.equals("HONGKONG") || facilitySystem.equals("GIFTCITY")) {
			if((Float.parseFloat(wmsDTOFromSystem.getReleasedAmount()) > Float.parseFloat(releasedAmount)) && (Float.parseFloat(releasedAmount) > 0)) {
				wmsRequestDTO.setXrefID(wmsDTOFromSystem.getXrefID());
				updateStatus = wmsDTOMapper.updateDTO(getLimitJdbc(), wmsRequestDTO);
				remarks = remarks + " Line updated "+updateStatus;
			}else if((Float.parseFloat(wmsDTOFromSystem.getReleasedAmount()) == Float.parseFloat(releasedAmount))){
				updateStatus = "FAIL";
				remarks = remarks +" Released Amount is same";
				wmsResponseDTO.setResponseStatus(updateStatus);
				wmsResponseDTO.setRemarks(remarks);				
				return wmsResponseDTO;
			}else {
				updateStatus = "FAIL";
				remarks = remarks +" Invalid Released Amount";
				wmsResponseDTO.setResponseStatus(updateStatus);
				wmsResponseDTO.setRemarks(remarks);				
				return wmsResponseDTO;
			}
		}else {
			updateStatus = "FAIL";
			remarks = remarks +" Invalid Facility System";
			wmsResponseDTO.setResponseStatus(updateStatus);
			wmsResponseDTO.setRemarks(remarks);				
			return wmsResponseDTO;
		}	
		
		if(updateStatus=="SUCCESS") {
			List facDetails = wmsDTOMapper.getFacilityDetails(limitJdbc, wmsDTOFromSystem.getXrefID());
			String lmtId =(String) facDetails.get(1);
			String totalreleasedAmount = (String) facDetails.get(0);
			float amount = 0; 
			if("Y" == closeLineFlag || "Y".equals(closeLineFlag)) {
				amount = Float.parseFloat(totalreleasedAmount) - Float.parseFloat(wmsDTOFromSystem.getReleasedAmount());
			}else {
				amount = Float.parseFloat(totalreleasedAmount) - Float.parseFloat(wmsDTOFromSystem.getReleasedAmount()) + Float.parseFloat(releasedAmount);
			}
			
			
			wmsDTOMapper.updateFacilityDetails(limitJdbc, lmtId, amount+"");
		}
		wmsResponseDTO.setResponseStatus(updateStatus);
		wmsResponseDTO.setRemarks(remarks);
			
		return wmsResponseDTO;
		
	}
}
