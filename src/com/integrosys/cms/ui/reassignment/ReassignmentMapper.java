/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/reassignment/ReassignmentMapper.java,v 1.2 2004/10/08 10:22:24 hshii Exp $
 */
package com.integrosys.cms.ui.reassignment;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchCriteria;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/10/08 10:22:24 $ Tag: $Name: $
 */
public class ReassignmentMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		ReassignmentForm aForm = (ReassignmentForm) cForm;
		Object returnObj = new Object();

		if (ReassignmentConstant.TASK_TYPE_DEAL.equals(aForm.getReassignmentType())) {
			CommodityDealSearchCriteria cSearch = new CommodityDealSearchCriteria();
			cSearch.setPendingOfficerApproval(true);
			if (ReassignmentConstant.TASK_SEARCH_KEY.equals(aForm.getSearchType())) {
				cSearch.setDealNo(aForm.getDealNo().trim());
			}
			else {
				cSearch.setTrxID(aForm.getDealTrxID().trim());
			}
			return cSearch;
		}

		if (ReassignmentConstant.TASK_TYPE_CCC.equals(aForm.getReassignmentType())) {
			CheckListSearchCriteria criteria = new CheckListSearchCriteria();
			criteria.setCheckListType(ICMSConstant.DOC_TYPE_CC);
			criteria.setPendingOfficerApproval(true);
			if (ReassignmentConstant.TASK_SEARCH_KEY.equals(aForm.getSearchType())) {
				criteria.setCheckListID(Long.parseLong(aForm.getCcChecklistID().trim()));
			}
			else {
				criteria.setTrxID(aForm.getCccTrxID().trim());
			}
			return criteria;
		}

		if (ReassignmentConstant.TASK_TYPE_SCC.equals(aForm.getReassignmentType())) {
			CheckListSearchCriteria criteria = new CheckListSearchCriteria();
			criteria.setCheckListType(ICMSConstant.DOC_TYPE_SECURITY);
			criteria.setPendingOfficerApproval(true);
			if (ReassignmentConstant.TASK_SEARCH_KEY.equals(aForm.getSearchType())) {
				criteria.setCheckListID(Long.parseLong(aForm.getScChecklistID().trim()));
			}
			else {
				criteria.setTrxID(aForm.getSccTrxID().trim());
			}
			return criteria;
		}

		return returnObj;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @throws com.integrosys.base.uiinfra.exception.MapperException on errors
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		ReassignmentForm aForm = (ReassignmentForm) cForm;

		return aForm;
	}
}
