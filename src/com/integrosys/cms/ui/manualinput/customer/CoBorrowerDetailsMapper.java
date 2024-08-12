package com.integrosys.cms.ui.manualinput.customer;

import static com.integrosys.cms.app.common.util.MapperUtil.emptyIfNull;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.CO_BORROWER_LIAB_ID_LIST;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCoBorrowerDetails;

public class CoBorrowerDetailsMapper extends AbstractCommonMapper {

	@Override
	public Object mapFormToOB(CommonForm form, HashMap map) throws MapperException {
		CoBorrowerDetailsForm coBorrowerForm = (CoBorrowerDetailsForm) form;
		OBCoBorrowerDetails coBorrower = new OBCoBorrowerDetails();
		
		coBorrower.setCoBorrowerLiabId(coBorrowerForm.getCoBorrowerLiabId());
		coBorrower.setCoBorrowerName(coBorrowerForm.getCoBorrowerName());
		coBorrower.setIsInterfaced("N");
		return coBorrower;
	}

	@Override
	public CommonForm mapOBToForm(CommonForm form, Object obj, HashMap map) throws MapperException {
		OBCoBorrowerDetails coBorrower = (OBCoBorrowerDetails) obj;
		CoBorrowerDetailsForm coBorrowerForm = (CoBorrowerDetailsForm) form;
		
		coBorrowerForm.setCoBorrowerLiabId(emptyIfNull(coBorrower.getCoBorrowerLiabId()));
		coBorrowerForm.setCoBorrowerName(emptyIfNull(coBorrower.getCoBorrowerName()));
		coBorrowerForm.setIsInterfaced("N");
		coBorrowerForm.setCoBorrowerLiabIdList((String)map.get(CO_BORROWER_LIAB_ID_LIST));
		
		return coBorrowerForm;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ "OBCMSCustomer", ICMSCustomer.class.getName(), SERVICE_SCOPE },
			{ CO_BORROWER_LIAB_ID_LIST, String.class.getName(), SERVICE_SCOPE },
		};
	}
}
