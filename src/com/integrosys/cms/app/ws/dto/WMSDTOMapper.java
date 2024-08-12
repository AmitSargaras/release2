package com.integrosys.cms.app.ws.dto;

import java.util.List;

import org.springframework.stereotype.Service;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.ui.manualinput.limit.XRefDetailForm;


@Service
public class WMSDTOMapper {
	

	public OBCustomerSysXRef  getActualFromDTO(WMSRequestDTO dto ) {

		OBCustomerSysXRef customerSysXRef = new OBCustomerSysXRef();
		
		customerSysXRef.setFacilitySystemID(dto.getSystemId());
		customerSysXRef.setLineNo(dto.getLineNo());
		customerSysXRef.setSerialNo(dto.getSerialNo());
		customerSysXRef.setLiabBranch(dto.getLiabBranch());
		customerSysXRef.setReleasedAmount(dto.getReleasedAmount());
		customerSysXRef.setCloseFlag(dto.getCloseLineFlag());
		
		return customerSysXRef;
	}
	
	public XRefDetailForm  getFormFromDTO(WMSRequestDTO dto,ICMSCustomer cust) {

		XRefDetailForm xRefDetailForm = new XRefDetailForm();
		
		xRefDetailForm.setFacilitySystemID(dto.getSystemId());
		xRefDetailForm.setLineNo(dto.getLineNo());
		xRefDetailForm.setSerialNo(dto.getSerialNo());
		xRefDetailForm.setLiabBranch(dto.getLiabBranch());
		xRefDetailForm.setReleasedAmount(dto.getReleasedAmount());
		xRefDetailForm.setCloseFlag(dto.getCloseLineFlag());
		
		return xRefDetailForm;
	}

	public WMSRequestDTO getDTOWithActualValues(ILimitJdbc limitJdbc,String systemId, String lineNo, String serialNo, String liabBranch) {
		WMSRequestDTO wmsRequestDTO = new WMSRequestDTO();
		
		OBCustomerSysXRef obCustomerSysXRef = null;
		
		if(null != limitJdbc.getLineDetails(systemId, lineNo, serialNo, liabBranch)) {
			obCustomerSysXRef = (OBCustomerSysXRef) limitJdbc.getLineDetails(systemId, lineNo, serialNo, liabBranch).get(0);
		
			wmsRequestDTO.setSystemId(systemId);
			wmsRequestDTO.setLineNo(lineNo);
			wmsRequestDTO.setSerialNo(serialNo);
			wmsRequestDTO.setLiabBranch(liabBranch);
			wmsRequestDTO.setFacilitySystem(obCustomerSysXRef.getFacilitySystem());
			wmsRequestDTO.setReleasedAmount(obCustomerSysXRef.getReleasedAmount());
			wmsRequestDTO.setCloseLineFlag(obCustomerSysXRef.getCloseFlag());
			wmsRequestDTO.setStatus(obCustomerSysXRef.getStatus());
			wmsRequestDTO.setXrefID(obCustomerSysXRef.getXRefID()+"");
			return wmsRequestDTO;
		}		
		return null;
	}
	
	public String updateDTO(ILimitJdbc limitJdbc, WMSRequestDTO wmsRequestDTO) {
		return limitJdbc.updateLineDetails(wmsRequestDTO.getSystemId(), wmsRequestDTO.getLineNo(), wmsRequestDTO.getSerialNo(), wmsRequestDTO.getLiabBranch(), wmsRequestDTO.getReleasedAmount(), wmsRequestDTO.getCloseLineFlag(),wmsRequestDTO.getXrefID());
	}
	
	public List getFacilityDetails(ILimitJdbc limitJdbc,String xrefID) {
		return limitJdbc.getFacilityDetails(xrefID);
	}
	
	public void updateFacilityDetails(ILimitJdbc limitJdbc,String lmtId, String amount) {
		limitJdbc.updateFacilityDetails(lmtId, amount);
	}
}
