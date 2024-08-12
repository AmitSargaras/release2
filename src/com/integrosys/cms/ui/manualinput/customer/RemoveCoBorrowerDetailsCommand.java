package com.integrosys.cms.ui.manualinput.customer;

import static com.integrosys.cms.ui.common.constant.IGlobalConstant.INDEX;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CUSTOMER;

public class RemoveCoBorrowerDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE },
			{ INDEX, String.class.getName(), REQUEST_SCOPE },
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE }, };
	}
	
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> exceptionMap = new HashMap<String, Object>();
		String index = (String) inputMap.get(INDEX);
		ICMSCustomer customer = (ICMSCustomer) inputMap.get(SESSION_CUSTOMER);
		ICMSLegalEntity sessionLegalEntity = (ICMSLegalEntity) customer.getCMSLegalEntity();		
		List<ICoBorrowerDetails> coBorrowerDetailsList = sessionLegalEntity.getCoBorrowerDetails();
		
		if(!CollectionUtils.isEmpty(coBorrowerDetailsList)) {
			DefaultLogger.info(this, "Remove Co-Borrower Details of index " + index + " from list of size "
					+ coBorrowerDetailsList.size());
			if(StringUtils.isNotBlank(index)) {
				int indexInt = Integer.parseInt(index)-1;
				ICoBorrowerDetails coBorrower = coBorrowerDetailsList.get(indexInt);
				
				boolean iscoBorrowerAdded = isCoBorrowerAddedInFacility(coBorrower.getCoBorrowerLiabId(),
						coBorrower.getMainProfileId());
				if (iscoBorrowerAdded) {
					exceptionMap.put("coBorrowerDetailsDeleteError",
							new ActionMessage("error.facility.coborrower.wip", coBorrower.getCoBorrowerLiabId()));
				} else {
					coBorrowerDetailsList.remove(indexInt);
					sessionLegalEntity.setCoBorrowerDetails(coBorrowerDetailsList);
				}
			}
		}
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		resultMap.put(SESSION_CUSTOMER, customer);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}
	
	private static boolean isCoBorrowerAddedInFacility(String coBorrowerId, long profileId) {
		ILimitDAO limit = LimitDAOFactory.getDAO();
		
		if(StringUtils.isBlank(coBorrowerId) && profileId <=0)
			return true;
		
		return limit.isCoBorrowerAddedInFacility(coBorrowerId, profileId);
	}
	
}
