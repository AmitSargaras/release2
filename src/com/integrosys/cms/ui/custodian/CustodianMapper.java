/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.custodian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria;
import com.integrosys.cms.app.custodian.bus.ICustodianDocItem;
import com.integrosys.cms.app.custodian.bus.OBCustAuthorize;
import com.integrosys.cms.app.custodian.bus.OBCustodianDoc;
import com.integrosys.cms.app.custodian.trx.OBCustodianTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: wltan $<br>
 * @version $Revision: 1.18 $
 * @since $Date: 2005/08/27 07:14:04 $ Tag: $Name: $
 */
public class CustodianMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CustodianMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "securityid", "java.lang.String", REQUEST_SCOPE },
				{ "checklistid", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				// CR-34
				{ "CustodianSearchCriteria", "com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria",
						SERVICE_SCOPE }, { "checkListItemRef", "java.lang.String", REQUEST_SCOPE },
		         // end CR-34
		});
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		CustodianForm aForm = (CustodianForm) cForm;
		ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
        aForm.setLimitProfile(String.valueOf(limitProfile.getLimitProfileID()));

		// long bid = cust.getCustomerID();
		if ((event != null) && event.equals("cc_doc_list")) {
			CustodianSearchCriteria sc = new CustodianSearchCriteria();
			// sc.setSubProfileID(bid);//no need of customer id..
			sc.setDocType(ICMSConstant.DOC_TYPE_CC);
			// CR-34 modification
			//sc.setCheckListID(Long.parseLong((String)map.get("checklistid")));
			long lchecklistid = 0;
			try {
				lchecklistid = Long.parseLong((String) map.get("checklistid"));
			}
			catch (Exception e) {
				return (CustodianSearchCriteria) map.get("CustodianSearchCriteria");
			}
			sc.setCheckListID(lchecklistid);
			// end CR-34 modification
			return sc;
			// CR-34 modification
		}
		else if ((event != null) && event.equals("security_doc_list")) {
			CustodianSearchCriteria sc = new CustodianSearchCriteria();
			// sc.setSubProfileID(bid);// no need of customer id..
			sc.setCheckListID(Long.parseLong((String) map.get("checklistid")));
			sc.setDocType(ICMSConstant.DOC_TYPE_SECURITY);
			sc.setCollateralID(Long.parseLong((String) map.get("securityid")));
			return sc;
		}
		else if ((event != null)
				&& (event.equals("lodge_custodian_maker") || event.equals("relodge_custodian_maker")
                        || event.equals("lodgereversal_custodian_maker")
						|| event.equals("tempuplift_custodian_maker") || event.equals("permuplift_custodian_maker")
						|| event.equals(CustodianAction.EVENT_REVERSE_CUST_MAKER) || event
						.equals(CustodianAction.EVENT_REVERSE_CUST_MAKER_TODO))) {
			OBCustodianTrxValue obTrxVal = (OBCustodianTrxValue) map.get("custodianTrxVal");
			OBCustodianDoc stagingDoc = (OBCustodianDoc) obTrxVal.getStagingCustodianDoc();
			// CR-34
			// stagingDoc.setReason(aForm.getDocReasons());
			String checkListItemRef = (String) map.get("checkListItemRef");
			DefaultLogger.debug(this, "checkListItemRef = " + checkListItemRef);
			ICustodianDocItem stagingItem = null;
			long lcheckListItemRef = 0;
			try {
				lcheckListItemRef = Long.parseLong(checkListItemRef);
			}
			catch (Exception e) {
			}
			if (stagingDoc.getCustodianDocItems() != null) {
				ICustodianDocItem item = null;
				for (Iterator iterator = stagingDoc.getCustodianDocItems().iterator(); iterator.hasNext();) {
					item = (ICustodianDocItem) iterator.next();
					if ((item != null) && (item.getCheckListItemRefID() == lcheckListItemRef)) {
						stagingItem = item;
					}
				}
			}
			stagingItem.setReason(aForm.getDocReasons());
            stagingItem.setCustodianDocItemBarcode(aForm.getCustDocItemBarcode());
            stagingItem.setSecEnvelopeBarcode(aForm.getSecEnvelopeBarcode());
			stagingItem.setStatus(aForm.getItemStatus());
			stagingItem.setLastUpdateDate(DateUtil.getDate());
			// end CR-34
			return stagingDoc;
		}
		else if ((event != null) && event.equals("edit_reject_custodian_maker")) {
			OBCustodianDoc doc = new OBCustodianDoc();
			OBCustodianTrxValue obTrxVal = (OBCustodianTrxValue) map.get("custodianTrxVal");
			AccessorUtil.copyValue(obTrxVal.getStagingCustodianDoc(), doc);
			// doc.setReason(aForm.getDocReasons());
			return doc;
		}
		else if ((event != null) && (event.equals("print_lodgement_memo") || event.equals("print_withdrawl_memo")) || event.equals("print_reversal_memo")) {
			String[] itemRef = aForm.getCheckListItemRef();
			// DefaultLogger.debug(this,"-------------"+itemRef);

			// CR-34
			// Values are passed in as checklistItemID@checklistID and stored in
			// HashMap
			String[] chkListItemRef = aForm.getCheckListItemRef();
			HashMap chkListIDItemIDMap = new HashMap();
			String separator = "@";
			for (int ind = 0; ind < itemRef.length; ind++) {
				String value = itemRef[ind];
				if (value.indexOf(separator) != -1) {
					StringTokenizer valueTokens = new StringTokenizer(value, separator);
					while (valueTokens.hasMoreTokens()) {
						chkListItemRef[ind] = valueTokens.nextToken();
						String chkListID = valueTokens.nextToken();
						// Putting into a HashMap with keys as ChecklistID and
						// value as List of CHecklistItemIDs for each
						// CheckListID
						if (chkListIDItemIDMap.containsKey(chkListID)) {
							ArrayList itemList = (ArrayList) chkListIDItemIDMap.get(chkListID);
							itemList.add(chkListItemRef[ind]);
						}
						else {
							ArrayList itemList = new ArrayList();
							itemList.add(chkListItemRef[ind]);
							chkListIDItemIDMap.put(chkListID, itemList);
						}
					}
				}
			}
			// end CR-34

			int custAuthzCount = ((aForm.getAuthzName1() != null) && (aForm.getAuthzName1().trim().length() > 0) ? 1
					: 0)
					+ ((aForm.getAuthzName2() != null) && (aForm.getAuthzName2().trim().length() > 0) ? 1 : 0);
			OBCustAuthorize[] custAuthzArr = new OBCustAuthorize[chkListItemRef.length * custAuthzCount];
			OBCustAuthorize custAuthz;
			int arrIndex = 0;
			for (int x = 0; x < chkListItemRef.length; x++) {
				custAuthz = new OBCustAuthorize();
				// custAuthz.setCustodianId(Long.parseLong(custodianId[x]));
				// DefaultLogger.debug(this,"CHEckListItem-------------"+
				// chkListItemRef[x]);
				custAuthz.setCheckListItemRef(Long.parseLong(chkListItemRef[x]));
				// DefaultLogger.debug(this,
				// "custAuthzArr-CHEckListItem1-------------" +
				// Long.parseLong(chkListItemRef[x]));
				// DefaultLogger.debug(this,
				// "custAuthzArr-CHEckListItem2-------------"+
				// custAuthz.getCheckListItemRef());
				if ((aForm.getAuthzName1() != null) && (aForm.getAuthzName1().trim().length() > 0)) {
					custAuthz.setAuthorizeName(aForm.getAuthzName1());
					custAuthz.setSignNum(aForm.getSignNum1());
					custAuthz.setAuthorizeRole(ICMSConstant.MEMO_AUTHZ_ROLE_1);
					if (event.equals("print_lodgement_memo")) {
						custAuthz.setOperation(ICMSConstant.MEMO_OPERATION_TYPE_LODGEMENT);
					}
                     else if (event.equals("print_reversal_memo")) {
						custAuthz.setOperation(ICMSConstant.MEMO_OPERATION_TYPE_REVERSAL);
					}
                    else {
						custAuthz.setOperation(ICMSConstant.MEMO_OPERATION_TYPE_WITHDRAWAL);
					}
					custAuthzArr[arrIndex] = custAuthz;
					// DefaultLogger.debug(this,
					// "custAuthzArr-CHEckListItem3-------------"
					// +custAuthzArr[arrIndex].getCheckListItemRef());
					arrIndex++;
				}
				if ((aForm.getAuthzName2() != null) && (aForm.getAuthzName2().trim().length() > 0)) {
					custAuthz.setAuthorizeName(aForm.getAuthzName2());
					custAuthz.setSignNum(aForm.getSignNum2());
					custAuthz.setAuthorizeRole(ICMSConstant.MEMO_AUTHZ_ROLE_2);
					if (event.equals("print_lodgement_memo")) {
						custAuthz.setOperation(ICMSConstant.MEMO_OPERATION_TYPE_LODGEMENT);
					}

                     else if (event.equals("print_reversal_memo")) {
						custAuthz.setOperation(ICMSConstant.MEMO_OPERATION_TYPE_REVERSAL);
					}
                    else {
						custAuthz.setOperation(ICMSConstant.MEMO_OPERATION_TYPE_WITHDRAWAL);
					}
					custAuthzArr[arrIndex] = custAuthz;
					// DefaultLogger.debug(this,
					// "custAuthzArr-CHEckListItem4-------------"
					// +custAuthzArr[arrIndex].getCheckListItemRef());
					arrIndex++;
				}
			}

			// for (int ii=0; ii<custAuthzArr.length; ii++)
			// {
			//DefaultLogger.debug(this,"custAuthzArr-CHEckListItem5-------------"
			// +custAuthzArr[ii].getCheckListItemRef());
			// }

			ArrayList returnList = new ArrayList();
			returnList.add(custAuthzArr);
			returnList.add(chkListItemRef);
			returnList.add(chkListIDItemIDMap);
			return returnList;
		}
		else {
			return null;
		}
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		CustodianForm aForm = (CustodianForm) cForm;
        ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
        aForm.setLimitProfile(String.valueOf(limitProfile.getLimitProfileID()));
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}