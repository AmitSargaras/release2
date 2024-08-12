package com.integrosys.cms.ui.custrelationship.list;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.bus.OBCustRelationship;
import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;



public class CustRelationshipListMapper extends AbstractCommonMapper {

	/**
	 * Convert form values to backend OB objects
	 * @param form Struts form
	 * @param hashMap
	 */
	public Object mapFormToOB(CommonForm aForm, HashMap hashMap)
			throws MapperException {
		
        DefaultLogger.debug(this, "entering CustRelationship mapFormToOB(...).");
        String event = aForm.getEvent();		
        
        DefaultLogger.debug(this, "event in CustRelationship is " + event);
        
        if (CustRelationshipListAction.EVENT_ADD.equals(event) ||
				CustRelationshipListAction.EVENT_ADD_FRAME.equals(event) ||
        		CustRelationshipListAction.EVENT_REMOVE.equals(event) ||
        		CustRelationshipListAction.EVENT_SUBMIT.equals(event)|| 
        		CustRelationshipListAction.EVENT_PAGINATE.equals(event)) {
            // Will return a feedGroup OB,
        	
        	CustRelationshipListForm form = (CustRelationshipListForm) aForm;
        	
            String[] entityRelArr = form.getEntityRels();
            String[] remarksArr = form.getCustRemarks();
            String[] checkSelectsArr = form.getCheckSelects();
            
            ICustRelationship[] custRelArr = null;

            if (entityRelArr != null) {
            	custRelArr =
                        new ICustRelationship[entityRelArr.length];
                for (int i = 0; i < entityRelArr.length; i++) {
                	custRelArr[i] = new OBCustRelationship();
                	custRelArr[i].setRelationshipValue(entityRelArr[i]);
                	custRelArr[i].setRemarks(remarksArr[i]);
                }
            } else {
            	custRelArr = new ICustRelationship[0];
            }
            
            HashMap custRelationshipMap = new HashMap();
            custRelationshipMap.put("custRel", custRelArr);
            custRelationshipMap.put("targetOffset", form.getTargetOffset());
            
            if (CustRelationshipListAction.EVENT_REMOVE.equals(event))
            	custRelationshipMap.put("custRelDeletes", checkSelectsArr);
            
            return custRelationshipMap;
            
        } else if (CustRelationshipListAction.EVENT_LIST_VIEW.equals(event) ||
        		CustRelationshipListAction.EVENT_LIST_READ.equals(event) ||
        		CustRelationshipListAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event) ||
        		CustRelationshipListAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {
        	
        	CustRelationshipListForm form = (CustRelationshipListForm) aForm;
            return new Integer(Integer.parseInt(form.getTargetOffset()));
            
        } 
        
		return null;
		
	}

	/**
	 * Convert backend object to form
	 * @param aForm form object which should be submitted
	 * @param object respective transaction value object
	 * @param hashMap submiited request parameters stored
	 */
	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap)
			throws MapperException {
		
        DefaultLogger.debug(this, "entering mapOBToForm(...).");

        CustRelationshipListForm form = (CustRelationshipListForm) aForm;
        String event = form.getEvent();

        int offset = ((Integer) hashMap.get("offset")).intValue();
        int length = ((Integer) hashMap.get("length")).intValue();
        Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        DefaultLogger.debug(this, "event is " + event);
		
        // Need to readjust the form value for proper display.
        if (CustRelationshipListAction.EVENT_READ.equals(event) ||
        		CustRelationshipListAction.EVENT_REMOVE.equals(event) ||
        		CustRelationshipListAction.EVENT_PAGINATE.equals(event) ||
        		// CustRelationshipListAction.EVENT_LIST_STAGING.equals(event) ||
        		// CustRelationshipListAction.EVENT_READ_MAKER_EDIT.equals(event) ||
                CustRelationshipListAction.EVENT_PREPARE.equals(event)) {

            ICustRelationshipTrxValue value = (ICustRelationshipTrxValue) object;
            ICustRelationship[] list = value.getStagingCustRelationship();

            extractForDisplay(offset, length, form, list, locale);
        }

        if (CustRelationshipListAction.EVENT_REMOVE.equals(event)) {
            form.setCheckSelects(new String[0]);
        }

        return form;
	}
	
	/**
	 * Extracting the necessary range of Customer Relationship for ui display
	 * @param offset
	 * @param length
	 * @param form
	 * @param custRelationshipList
	 * @param locale
	 */
    private void extractForDisplay(int offset, int length,
            CustRelationshipListForm form, ICustRelationship[] custRelationship, Locale locale) {

		if (custRelationship == null) {
			// Do nothing when there is no group.
			return;
		}
		
		DefaultLogger.debug(this, "number of cust relationship = " + custRelationship.length);
		
		// setting the limit position where the ui range should end
		// offset is the begin position of the ui range 
		int limit = offset + length;
		if (limit > custRelationship.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " +
					length + " > entries.length " + custRelationship.length);
	
			limit = custRelationship.length;
		}
		
		String[] nameArr = new String[limit - offset];
		String[] dobArr = new String[limit - offset];
		String[] ageArr = new String[limit - offset];
		String[] entityRelArr = new String[limit - offset];
		String[] countryArr = new String[limit - offset];
		String[] remarksArr = new String[limit - offset];

		for (int i = offset; i < limit; i++) {
			nameArr[i - offset] =
				custRelationship[i].getCustomerDetails().getCustomerName();
			
			dobArr [i - offset] = DateUtil.formatDate(locale,
				custRelationship[i].getCustomerDetails()
				.getCMSLegalEntity().getRelationshipStartDate());
			
			// ToDo array of age
			ageArr[i - offset] = "12";
			
			entityRelArr[i - offset] = String.valueOf(custRelationship[i].getCustRelationshipID());
			
			countryArr[i - offset] = (custRelationship[i]
				.getCustomerDetails().getCMSLegalEntity().getLegalRegCountry());
		
			remarksArr[i - offset] = custRelationship[i].getRemarks();
			
		}
		
		form.setNames(nameArr);
	}	
	
    public static int adjustOffset(int offset, int length, int maxSize) {

        int adjustedOffset = offset;

        if (offset >= maxSize) {
            DefaultLogger.debug(CustRelationshipListMapper.class.getName(), "offset " +
                    offset + " + length " + length + " >= maxSize " + maxSize);
            if (maxSize == 0) {
                // Do not reduce offset further.
                adjustedOffset = 0;
            } else if (offset == maxSize) {
                // Reduce.
                adjustedOffset = offset - length;
            } else {
                // Rely on "/" = Integer division.
                // Go to the start of the last strip.
                adjustedOffset = maxSize / length * length;
            }
            DefaultLogger.debug(CustRelationshipListMapper.class.getName(),
                    "adjusted offset = " + adjustedOffset);
        }

        return adjustedOffset;
    }

    /**
     * Defines an two dimensional array with
     * the parameter list to be passed to the mapFormToOB method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     * 
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return new String[][]{{"offset", "java.lang.Integer", SERVICE_SCOPE}, 
        		              {"length", "java.lang.Integer", SERVICE_SCOPE}, 
        		              {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
        		            	  		 "java.util.Locale", GLOBAL_SCOPE},
        		              {"custRelationshipMap", "java.util.HashMap", FORM_SCOPE},
        		              {"selectedCustMap", "java.util.HashMap", FORM_SCOPE} };
    }
    
    /**
     * Helper method to return true if integer is one of the array elements in
     * the integer array.
     * @param target
     * @param arr
     * @return
     */
    public static boolean inArray(int target, int[] arr) {

        if (arr == null) {
            return false;
        }

        for (int i = 0; i < arr.length; i++) {
            if (target == arr[i]) {
                return true;
            }
        }

        return false;
    }
}
