/**
 * 
 */
package com.integrosys.cms.ui.custrelationship.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSLegalEntity;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.bus.OBCustShareholder;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.custrelationship.CustRelConstants;
import com.integrosys.cms.ui.custrelationship.CustRelUtils;
import com.integrosys.cms.ui.custrelationship.shareholder.ShareHolderListForm;

/**
 * @author siew kheat
 * 
 */
public class AddNewShareHolderCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{
						"CustShareHolderTrxValue",
						"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Consume
																	// the
																	// current
																	// feed
																	// entries
																	// to be
																	// saved as
																	// a whole.
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "sub_profile_id", "java.lang.String", SERVICE_SCOPE },
				{ "customerType", "java.lang.String", SERVICE_SCOPE },
				{ "session.customerlist",
						"com.integrosys.base.businfra.search.SearchResult",
						SERVICE_SCOPE },
				{ "selectedCustList", "java.util.List", FORM_SCOPE },
				{ "leIDType", "java.lang.String", REQUEST_SCOPE },
				{ CRCustSearchForm.MAPPER, "java.util.List", FORM_SCOPE },
				{"from_event", "java.lang.String", SERVICE_SCOPE},

		};
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {// Produce all the feed entries.
				{
						"CustShareHolderTrxValue",
						"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue",
						SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, // Produce
																	// the
																	// length.
				{ "length", "java.util.Integer", SERVICE_SCOPE }, // To
																	// populate
																	// the form.
				{ "sub_profile_id", "java.lang.String", SERVICE_SCOPE },
				{ "customerType", "java.lang.String", SERVICE_SCOPE },
				{
						ShareHolderListForm.MAPPER,
						"com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue",
						FORM_SCOPE },
				{ "entRelLabels", "java.util.Collection",
						ICommonEventConstant.REQUEST_SCOPE },
				{ "entRelValues", "java.util.Collection",
						ICommonEventConstant.REQUEST_SCOPE }, 
				{"from_event", "java.lang.String", SERVICE_SCOPE},
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *             on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			ICustShareholderTrxValue value = (ICustShareholderTrxValue) map
					.get("CustShareHolderTrxValue");

			ICustShareholder[] custShareHolderArr = value
					.getStagingCustShareholder();

			// Validate that the (buy currency, sell currency) pair cannot be
			// the same as one of the existing pairs.
			if (custShareHolderArr == null) {
				custShareHolderArr = new ICustShareholder[0];
			}

			String parentSubProfileID = (String) map.get("sub_profile_id");

			if (exceptionMap.isEmpty()) {
				// Only proceed if there are no errors.

				// HashMap selectedCustMap = new HashMap();

				List selectedCustList = (List) map.get(CRCustSearchForm.MAPPER);

				String[] custCifId = (String[]) selectedCustList.get(0);

				DefaultLogger.debug(this,
						"**************> custSubProfileIDArr length : "
								+ custCifId.length);

				SearchResult sr = (SearchResult) map
						.get("session.customerlist");
				Collection c = sr.getResultList();

				Iterator iter = c.iterator();

				List selectedCustomerList = new ArrayList();

				while (iter.hasNext()) {
					OBCustomerSearchResult obsr = (OBCustomerSearchResult) iter.next();
					String cifId = obsr.getLegalReference();

					if (custCifId != null) {
						for (int i = 0; custCifId.length > i; i++) {
							String tempCifId = custCifId[i];

							if (StringUtils.equals(cifId, tempCifId)) {
								// found the selected customer
								ICustShareholder entry = new OBCustShareholder();

								entry.setParentSubProfileID(Long
										.parseLong(parentSubProfileID));

								entry
										.setCustRelationshipID(ICMSConstant.LONG_INVALID_VALUE);
								entry.setRefID(ICMSConstant.LONG_INVALID_VALUE);
								entry
										.setGroupID(ICMSConstant.LONG_INVALID_VALUE);

								//fillCustShareHolder(entry, obsr);

								ICMSCustomer customer = new OBCMSCustomer();
								ICMSLegalEntity legalEntity = new OBCMSLegalEntity();

								legalEntity.setLegalRegCountry(obsr.getOrigLocCntry());
								legalEntity.setSourceID(obsr.getSourceID());
								legalEntity.setLEReference(obsr.getLegalReference());
								legalEntity.setLegalRegNumber(obsr.getIdNo());

								customer.setCustomerName(obsr.getLegalName());
								customer.setCMSLegalEntity(legalEntity);
				
								entry.setSubProfileID(obsr.getSubProfileID());
								entry.setCustomerDetails(customer);

								selectedCustomerList.add(entry);
							}
						}
					}
				}

				// get customer information for the customer chosen
				
				List list = new ArrayList();
				boolean exist = false;
				
				ICustShareholder[] selectedCustomerArray = (ICustShareholder[]) selectedCustomerList.toArray(new ICustShareholder[0]);
				
				if (custShareHolderArr.length == 0) {
                    list = getNewList(null, selectedCustomerArray);
				} else {                  
                    exist = validateDuplicate(custShareHolderArr, selectedCustomerArray, exceptionMap);

                    if (exist) {
                        list = getNewList(selectedCustomerArray, null);
                    }
                    else {
                        list = getNewList(selectedCustomerArray, custShareHolderArr);
                        
                    }

                }
				
				ICustShareholder[] newEntriesArr = (ICustShareholder[]) list.toArray(new ICustShareholder[0]);
				
				if (!exist) {
					value.setStagingCustShareholder(newEntriesArr);
				}	

				// Add list into as the last item of the array.
				/*ICustShareholder[] newEntriesArr = new ICustShareholder[custCustShareHolderArr.length
						+ selectedCustomerList.size()];

				System.arraycopy(custCustShareHolderArr, 0, newEntriesArr, 0,
						custCustShareHolderArr.length);

				DefaultLogger.debug(this,
						"**************> selectedCustomerList size : "
								+ selectedCustomerList.size());

				for (int i = 0; i < selectedCustomerList.size(); i++) {
					ICustShareholder entry = (ICustShareholder) selectedCustomerList
							.get(i);
					newEntriesArr[custCustShareHolderArr.length + i] = entry;
				}
*/

				resultMap.put("CustShareHolderTrxValue", value);

				CommonCodeList commonCode = CommonCodeList.getInstance(null,
						null, ICMSUIConstant.ENTITY_REL, null);
				Collection entRelValues = commonCode.getCommonCodeValues();
				Collection entRelLabels = commonCode.getCommonCodeLabels();
				if (entRelValues == null) {
					entRelValues = new ArrayList();
				}
				if (entRelLabels == null) {
					entRelLabels = new ArrayList();
				}

				entRelValues = CustRelUtils.removeIntFromCollection(
						CustRelConstants.SHAREHOLDER_ID, entRelValues);
				entRelLabels = CustRelUtils.removeStringFromCollection(
						CustRelConstants.SHAREHOLDER_LABEL, entRelLabels);

				resultMap.put("entRelValues", entRelValues);
				resultMap.put("entRelLabels", entRelLabels);
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	private List getNewList(ICustShareholder[] oldList, ICustShareholder[] newList) {
		List list = new ArrayList();
		if (oldList != null) {
			for (int i = 0; i < oldList.length; i++) {
				list.add(oldList[i]);
			}
		}
		if (newList != null) {
			for (int i = 0; i < newList.length; i++) {
				list.add(newList[i]);
			}
		}
		return list;
	
	}
	
	 private boolean validateDuplicate(ICustShareholder[] jspIDoc, ICustShareholder[] dbIDoc, HashMap exceptionMap) {
	       
	        boolean valid = false;

	        if (jspIDoc == null || jspIDoc.length == 0){
	            return false;
	        }

	        if (dbIDoc == null || dbIDoc.length == 0){
	            return false;
	        }
	        String errorKey = "";
	        for (int i = 0; i < jspIDoc.length; i++) {

	            for (int jj= 0; jj < dbIDoc.length; jj++) {
	                   long jspKey = jspIDoc[i].getSubProfileID();
	                   long dbKey = dbIDoc[jj].getSubProfileID();
	                    
	                   if (jspKey == dbKey){
	                        errorKey = "errEntityID" + jspIDoc[i].getSubProfileID();
	                        valid = true;
	                        exceptionMap.put(errorKey, new ActionMessage("error.cust.relationship.shareholder.duplicate"));
	                }
	            }
	        }
	        return valid;
	    }
}
