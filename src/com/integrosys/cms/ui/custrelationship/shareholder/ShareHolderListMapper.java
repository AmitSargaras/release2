package com.integrosys.cms.ui.custrelationship.shareholder;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.bus.OBCustShareholder;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;



public class ShareHolderListMapper extends AbstractCommonMapper {

	/**
	 * Convert form values to backend OB objects
	 * @param form Struts form
	 * @param hashMap
	 */
	public Object mapFormToOB(CommonForm aForm, HashMap hashMap)
			throws MapperException {
		
        DefaultLogger.debug(this, "entering ShareHolder mapFormToOB(...).");
        String event = aForm.getEvent();		
        
        DefaultLogger.debug(this, "event in ShareHolder is " + event);
        
        if (ShareHolderListAction.EVENT_ADD.equals(event) ||
				ShareHolderListAction.EVENT_ADD_FRAME.equals(event) ||
        		ShareHolderListAction.EVENT_REMOVE.equals(event) ||
        		ShareHolderListAction.EVENT_SUBMIT.equals(event) || 
        		ShareHolderListAction.EVENT_PAGINATE.equals(event)) {
            // Will return a feedGroup OB,
        	
        	ShareHolderListForm form = (ShareHolderListForm) aForm;
        	
            String[] percentageOwnArr = form.getPercentages();
            String[] checkSelectsArr = form.getCheckSelects();
            
            ICustShareholder[] shareHolderArr = null;

            if (percentageOwnArr != null) {
            	shareHolderArr =
                        new ICustShareholder[percentageOwnArr.length];
                for (int i = 0; i < percentageOwnArr.length; i++) {
                	shareHolderArr[i] = new OBCustShareholder();
                	if (percentageOwnArr[i] != null)
                		try {
                			shareHolderArr[i].setPercentageOwn(new Double(percentageOwnArr[i]));
                		} catch(NumberFormatException nfe) {
                			DefaultLogger.debug(this, nfe);
                		}
                	else
                		shareHolderArr[i].setPercentageOwn(new Double(0));
                }
            } else {
            	shareHolderArr = new ICustShareholder[0];
            }
            
            HashMap shareHolderMap = new HashMap();
            shareHolderMap.put("shareHolder", shareHolderArr);
            shareHolderMap.put("targetOffset", form.getTargetOffset());
            
            if (ShareHolderListAction.EVENT_REMOVE.equals(event))
            	shareHolderMap.put("shareHolderDeletes", checkSelectsArr);
            
            return shareHolderMap;
            
        } else if (ShareHolderListAction.EVENT_LIST_VIEW.equals(event) ||
        		ShareHolderListAction.EVENT_LIST_READ.equals(event) ||
        		ShareHolderListAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event) ||
        		ShareHolderListAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {
        	
        	ShareHolderListForm form = (ShareHolderListForm) aForm;
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

        ShareHolderListForm form = (ShareHolderListForm) aForm;
        String event = form.getEvent();

        int offset = ((Integer) hashMap.get("offset")).intValue();
        int length = ((Integer) hashMap.get("length")).intValue();
        Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        DefaultLogger.debug(this, "event is " + event);
		
        // Need to readjust the form value for proper display.
        if (ShareHolderListAction.EVENT_READ.equals(event) ||
        		ShareHolderListAction.EVENT_REMOVE.equals(event) ||
        		ShareHolderListAction.EVENT_PAGINATE.equals(event) ||
        		// ShareHolderListAction.EVENT_LIST_STAGING.equals(event) ||
        		// ShareHolderListAction.EVENT_READ_MAKER_EDIT.equals(event) ||
                ShareHolderListAction.EVENT_PREPARE.equals(event)) {

            ICustShareholderTrxValue value = (ICustShareholderTrxValue) object;
            ICustShareholder[] list = value.getStagingCustShareholder();

            extractForDisplay(offset, length, form, list, locale);
        }

        if (ShareHolderListAction.EVENT_REMOVE.equals(event)) {
            form.setCheckSelects(new String[0]);
        }

        return form;
	}
	
	/**
	 * Extracting the necessary range of Customer Relationship for ui display
	 * @param offset
	 * @param length
	 * @param form
	 * @param ShareHolderList
	 * @param locale
	 */
    private void extractForDisplay(int offset, int length,
            ShareHolderListForm form, ICustShareholder[] shareHolder, Locale locale) {

		if (shareHolder == null) {
			// Do nothing when there is no group.
			return;
		}
		
		DefaultLogger.debug(this, "number of cust shareHolder = " + shareHolder.length);
		
		// setting the limit position where the ui range should end
		// offset is the begin position of the ui range 
		int limit = offset + length;
		if (limit > shareHolder.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " +
					length + " > entries.length " + shareHolder.length);
	
			limit = shareHolder.length;
		}
		
		String[] percentages = new String[limit - offset];


		for (int i = offset; i < limit; i++) {
			percentages[i - offset] =
				String.valueOf(shareHolder[i].getPercentageOwn());
			
		}
		
		form.setPercentages(percentages);
	}	
	
    public static int adjustOffset(int offset, int length, int maxSize) {

        int adjustedOffset = offset;

        if (offset >= maxSize) {
            DefaultLogger.debug(ShareHolderListMapper.class.getName(), "offset " +
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
            DefaultLogger.debug(ShareHolderListMapper.class.getName(),
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
        		              {"shareHolderMap", "java.util.HashMap", FORM_SCOPE},
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
