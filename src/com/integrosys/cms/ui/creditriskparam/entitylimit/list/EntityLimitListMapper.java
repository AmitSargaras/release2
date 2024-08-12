/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.list;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.OBEntityLimit;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.ui.common.UIUtil;


/**
 * Entity Limit Mapper class
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class EntityLimitListMapper extends AbstractCommonMapper {
    
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
        		              {"entityLimitMap", "java.util.HashMap", FORM_SCOPE},
        		              {"selectedCustMap", "java.util.HashMap", FORM_SCOPE} };
    }
    
	/**
	 * Convert form values to backend OB objects
	 * @param form Struts form
	 * @param hashMap
	 */
	public Object mapFormToOB(CommonForm aForm, HashMap hashMap)
			throws MapperException {
		
        DefaultLogger.debug(this, "entering EntityLimitListMapper mapFormToOB(...).");
        String event = aForm.getEvent();		
        
        Locale locale = (Locale)hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        
        DefaultLogger.debug(this, "event in EntityLimit is " + event);
        
        EntityLimitListForm form = (EntityLimitListForm) aForm;
        
        if (EntityLimitListAction.EVENT_ADD.equals(event) ||
        		EntityLimitListAction.EVENT_REMOVE.equals(event) ||
        		EntityLimitListAction.EVENT_UPDATE.equals(event) ||
        		EntityLimitListAction.EVENT_SUBMIT.equals(event) ||
        		EntityLimitListAction.EVENT_PAGINATE.equals(event)) {
        	
            String[] checkSelectsArr = form.getCheckSelects();
            String editedPosStr = form.getEditedPos();
            
            String[] currencyArr = form.getLimitCurrencys();
            String[] limitAmountArr = form.getLimitAmounts();
            String[] limitLastReviewDateArr = form.getLimitLastReviewDates();
            
            IEntityLimit[] entityLimitArr = null;
            if (currencyArr != null) {
	            entityLimitArr = new IEntityLimit[currencyArr.length];
	            
	            for (int i = 0; i < currencyArr.length; i++) {
	            	entityLimitArr[i] = new OBEntityLimit();
	                try {
	                	
	                	entityLimitArr[i].setLimitAmount(
	                	UIUtil.convertToAmount(locale, currencyArr[i], limitAmountArr[i]));
	                	entityLimitArr[i].setLimitLastReviewDate(DateUtil.convertDate(locale, limitLastReviewDateArr[i]));
	                } catch (Exception e) {
	                    DefaultLogger.warn(this, "value is not double-parseable.");
	                    e.printStackTrace();
	                    entityLimitArr[i].setLimitAmount(null);
	                }
	            }
            }
            
            
            HashMap entityLimitMap = new HashMap();
            entityLimitMap.put("entityLimitMap", entityLimitArr);
            entityLimitMap.put("targetOffset", form.getTargetOffset());
            entityLimitMap.put("editedRemarks", form.getRemarks());
            
            if (EntityLimitListAction.EVENT_REMOVE.equals(event))
            	entityLimitMap.put("entityLimitDeletes", checkSelectsArr);
            
            if (EntityLimitListAction.EVENT_UPDATE.equals(event))
            	entityLimitMap.put("editedPos", editedPosStr);
            
            return entityLimitMap;
        } 
        
        if (EntityLimitListAction.EVENT_EDIT.equals(event) || 
        		EntityLimitListAction.EVENT_EDIT_NOOP.equals(event))  {
        	String editedPosStr = form.getEditedPos();
        	
            HashMap entityLimitMap = new HashMap();
            entityLimitMap.put("entityLimitMap", new IEntityLimit[0]);
            entityLimitMap.put("editedPos", editedPosStr);
            entityLimitMap.put("targetOffset", form.getTargetOffset());
            entityLimitMap.put("editedRemarks", form.getRemarks());
            
            return entityLimitMap;
            
        }  else if (EntityLimitListAction.EVENT_LIST_VIEW.equals(event) ||
        		EntityLimitListAction.EVENT_LIST_READ.equals(event) ||
        		EntityLimitListAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event) ||
        		EntityLimitListAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {
        	
        	HashMap entityLimitMap = new HashMap();
            entityLimitMap.put("targetOffset", form.getTargetOffset());
            entityLimitMap.put("editedRemarks", form.getRemarks());
            
            return entityLimitMap;
            
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

        EntityLimitListForm form = (EntityLimitListForm) aForm;
        String event = form.getEvent();

        int offset = ((Integer) hashMap.get("offset")).intValue();
        int length = ((Integer) hashMap.get("length")).intValue();
        Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        DefaultLogger.debug(this, "event is " + event);
		
        // Need to readjust the form value for proper display.
        if (EntityLimitListAction.EVENT_READ.equals(event) ||
        		EntityLimitListAction.EVENT_REMOVE.equals(event) ||
                EntityLimitListAction.EVENT_PREPARE.equals(event)) {

            IEntityLimitTrxValue value = (IEntityLimitTrxValue) object;
            IEntityLimit[] list = value.getEntityLimit();

            extractForDisplay(offset, length, form, list, locale);
        }

        if (EntityLimitListAction.EVENT_REMOVE.equals(event)) {
            form.setCheckSelects(new String[0]);
        }
        
        if (EntityLimitListAction.EVENT_EDIT_NOOP.equals(event)) {
        	String editedPos = (hashMap.get("editedPos") != null) ?
        			(String) hashMap.get("editedPos") : "";
        	
        	DefaultLogger.debug(this, "editedPos in EntityLimitListAction.mapOBToForm " + editedPos);
        	// form.setEditedPos(editedPos);
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
            EntityLimitListForm form, IEntityLimit[] entityLimit, Locale locale) {

		if (entityLimit == null) {
			// Do nothing when there is no group.
			return;
		}
		
		DefaultLogger.debug(this, "number of exempted Inst = " + entityLimit.length);
		
		// setting the limit position where the ui range should end
		// offset is the begin position of the ui range 
		int limit = offset + length;
		if (limit > entityLimit.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " +
					length + " > entries.length " + entityLimit.length);
	
			limit = entityLimit.length;
		}
		
		// TODO Not needed at the moment
		String[] nameArr = new String[limit - offset];
		String[] cifSourceArr = new String[limit - offset];
		String[] cifNoArr = new String[limit - offset];
		String[] limitCurrencyArr = new String[limit - offset];
		String[] limitAmountArr = new String[limit - offset];
		String[] limitArr = new String[limit - offset];
		String[] limitLastReviewDateArr = new String[limit - offset];

		for (int i = offset; i < limit; i++) {
			nameArr[i - offset] = entityLimit[i].getCustomerName();
			cifSourceArr[i - offset] = entityLimit[i].getCustIDSource();
			cifNoArr[i - offset] =  convertLongtoString(entityLimit[i].getCustomerID());
			limitCurrencyArr[i - offset] =   entityLimit[i].getLimitAmount().getCurrencyCode();
			limitAmountArr[i - offset] = String.valueOf(entityLimit[i].getLimitAmount().getAmount());
			limitLastReviewDateArr [i - offset] = UIUtil.formatDate(entityLimit[i].getLimitLastReviewDate());
			try {
			limitArr[i - offset] = UIUtil.formatAmount(entityLimit[i].getLimitAmount(), 2, locale) ;
			} catch (Exception e) {e.printStackTrace(); }
			new UIUtil();
		}
		
		form.setNames(nameArr);
		form.setCifs(cifNoArr);
		form.setCifSources(cifSourceArr);
		form.setLimitAmounts(limitAmountArr);
		form.setLimitCurrencys(limitCurrencyArr);
		form.setLimits(limitArr);
		form.setLimitLastReviewDates(limitLastReviewDateArr);
	}	
	
    public static int adjustOffset(int offset, int length, int maxSize) {

        int adjustedOffset = offset;

        if (offset >= maxSize) {
            DefaultLogger.debug(EntityLimitListMapper.class.getName(), "offset " +
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
            DefaultLogger.debug(EntityLimitListMapper.class.getName(),
                    "adjusted offset = " + adjustedOffset);
        }

        return adjustedOffset;
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
    
    private String convertLongtoString(long value) {
    	if (value == 0 || value == ICMSConstant.LONG_INVALID_VALUE)
    		return "";
    	else
    		return Long.toString(value);
    }
}
