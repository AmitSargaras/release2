package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerDAO;

public class CheckerUpdateCibilDetailsCommand extends AbstractCommand{
//Added for processing Duplicate PAN no Check
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{"cifId","java.lang.String", REQUEST_SCOPE},
				{"pan","java.lang.String", REQUEST_SCOPE}
		});
		
	}
	
	public String[][] getResultDescriptor() {
		
		return (new String[][]{
				{"duplicatePanPartyDetails","java.lang.String", SERVICE_SCOPE}
		});
	}
	
	 @Override
	public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {
		// TODO Auto-generated method stub
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		//For getting Pan details
		String cifId=(String) map.get("cifId");
		String pan=(String) map.get("pan");
		//For getting Pan details
		//For getting Pan details
		CustomerDAO customerDao = new CustomerDAO();
		List<String> duplicatePartyList=new ArrayList<String>();
		if(null!=cifId && !"".equals(cifId) && null!=pan && !"".equals(pan))
		  duplicatePartyList=customerDao.getPanDetails(cifId, pan);
		String duplicatePanPartyDetails="";
		if(null!=duplicatePartyList && duplicatePartyList.size() >0){
		for(int i=0; i<duplicatePartyList.size() ; i++){
			if(i != (duplicatePartyList.size()-1 )){
				duplicatePanPartyDetails=duplicatePanPartyDetails+duplicatePartyList.get(i)+ ",";
			}
			else{
				duplicatePanPartyDetails=duplicatePanPartyDetails+duplicatePartyList.get(i);	
			}
			}
		}
		//DefaultLogger.debug(this,"duplicatePanPartyDetails:"+ duplicatePanPartyDetails);
		resultMap.put("duplicatePanPartyDetails", duplicatePanPartyDetails);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
		
	}
}
