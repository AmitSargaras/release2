package com.integrosys.cms.ui.creditriskparam.exemptedinst.list;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;



public class ExemptedInstListMapper extends AbstractCommonMapper {

	/**
	 * Convert form values to backend OB objects
	 * @param form Struts form
	 * @param hashMap
	 */
	public Object mapFormToOB(CommonForm aForm, HashMap hashMap)
			throws MapperException {
		
        DefaultLogger.debug(this, "entering ExemptedInstListMapper mapFormToOB(...).");
        String event = aForm.getEvent();		
        
        DefaultLogger.debug(this, "event in CustRelationship is " + event);
        
        if (ExemptedInstListAction.EVENT_ADD.equals(event) ||
        		ExemptedInstListAction.EVENT_REMOVE.equals(event) ||
        		ExemptedInstListAction.EVENT_SUBMIT.equals(event)) {
            // Will return a feedGroup OB,
        	
        	ExemptedInstListForm form = (ExemptedInstListForm) aForm;
        	
            String[] checkSelectsArr = form.getCheckSelects();
            
            ICustRelationship[] custRelArr = null;
            
            HashMap custRelationshipMap = new HashMap();
            custRelationshipMap.put("exemptedInst", custRelArr);
            
            if (ExemptedInstListAction.EVENT_REMOVE.equals(event))
            	custRelationshipMap.put("exemptedInstDeletes", checkSelectsArr);
            
            return custRelationshipMap;
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

        ExemptedInstListForm form = (ExemptedInstListForm) aForm;
        String event = form.getEvent();

        int offset = ((Integer) hashMap.get("offset")).intValue();
        int length = ((Integer) hashMap.get("length")).intValue();
        Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        DefaultLogger.debug(this, "event is " + event);
		
        // Need to readjust the form value for proper display.
        if (ExemptedInstListAction.EVENT_READ.equals(event) ||
        		ExemptedInstListAction.EVENT_REMOVE.equals(event) ||
                ExemptedInstListAction.EVENT_PREPARE.equals(event)) {

            IExemptedInstTrxValue value = (IExemptedInstTrxValue) object;
            IExemptedInst[] list = value.getExemptedInst();

            extractForDisplay(offset, length, form, list, locale);
        }

        if (ExemptedInstListAction.EVENT_REMOVE.equals(event)) {
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
            ExemptedInstListForm form, IExemptedInst[] exemptedInst, Locale locale) {

		if (exemptedInst == null) {
			// Do nothing when there is no group.
			return;
		}
		
		DefaultLogger.debug(this, "number of exempted Inst = " + exemptedInst.length);
		
		// setting the limit position where the ui range should end
		// offset is the begin position of the ui range 
		int limit = offset + length;
		if (limit > exemptedInst.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " +
					length + " > entries.length " + exemptedInst.length);
	
			limit = exemptedInst.length;
		}
		
		// TODO Not needed at the moment
		String[] nameArr = new String[limit - offset];
		String[] dobArr = new String[limit - offset];
		String[] ageArr = new String[limit - offset];
		String[] entityRelArr = new String[limit - offset];
		String[] countryArr = new String[limit - offset];
		String[] remarksArr = new String[limit - offset];

		for (int i = offset; i < limit; i++) {
			//nameArr[i - offset] =
			
		}
		
		//form.setNames(nameArr);
	}	
	
    public static int adjustOffset(int offset, int length, int maxSize) {

        int adjustedOffset = offset;

        if (offset >= maxSize) {
            DefaultLogger.debug(ExemptedInstListMapper.class.getName(), "offset " +
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
            DefaultLogger.debug(ExemptedInstListMapper.class.getName(),
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
        		              {"exemptedInstMap", "java.util.HashMap", FORM_SCOPE},
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
