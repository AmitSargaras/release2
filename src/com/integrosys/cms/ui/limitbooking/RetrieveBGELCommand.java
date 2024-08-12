/**
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.ui.limitbooking;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custgrpi.bus.OBGroupSearchResult;
import com.integrosys.cms.app.limitbooking.proxy.ILimitBookingProxy;
import com.integrosys.cms.app.limitbooking.proxy.LimitBookingProxyFactory;
import com.integrosys.cms.app.limitbooking.bus.IBankGroupDetail;
import com.integrosys.cms.app.limitbooking.bus.OBBankGroupDetail;
import com.integrosys.cms.app.limitbooking.bus.OBLimitBooking;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author priya
 *
 */

public class RetrieveBGELCommand extends AbstractCommand implements ICommonEventConstant {
     /**
     * Default Constructor
     */
    public RetrieveBGELCommand() {
        DefaultLogger.debug(this,"Inside RetrieveBGELCommand >>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"bkgIDNo", "java.lang.String", REQUEST_SCOPE},
            {LimitBookingAction.LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", FORM_SCOPE},
            {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking", SERVICE_SCOPE},
            {LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, "java.lang.String", REQUEST_SCOPE}
        });
    }


    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
            {LimitBookingAction.SESSION_LIMIT_BOOKING, "com.integrosys.cms.app.limitbooking.bus.OBLimitBooking" , SERVICE_SCOPE},
            {LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, "java.lang.String", REQUEST_SCOPE},
            {LimitBookingAction.LIMIT_BOOKING_GROUP_RETRIEVED_FLAG, "java.lang.String", REQUEST_SCOPE}
        });
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
      
        DefaultLogger.debug(this, "Inside doExecute()");
        try {
            // Get Collection and set to OB then put in session
            OBLimitBooking sessLimitBooking = (OBLimitBooking)map.get(LimitBookingAction.SESSION_LIMIT_BOOKING);
            OBLimitBooking limitBooking = (OBLimitBooking)map.get(LimitBookingAction.LIMIT_BOOKING);
            limitBooking.setAllBkgs(sessLimitBooking.getAllBkgs());
            limitBooking.setLoanSectorList(sessLimitBooking.getLoanSectorList());
            
            String bkgIDNo = (String)map.get("bkgIDNo");
            if (bkgIDNo != null)
                bkgIDNo = bkgIDNo.toUpperCase();

            ILimitBookingProxy proxy = LimitBookingProxyFactory.getProxy();
            Long subProfileId = proxy.getSubProfileIDByIDNumber(bkgIDNo);
            
            List tempBgelBookings = sessLimitBooking.getBankGroupList(); 
            
            List bgelBookings = new ArrayList();
            
            if (tempBgelBookings != null) {
            	for (int i=0; i<tempBgelBookings.size(); i++) {
            		if (((IBankGroupDetail)tempBgelBookings.get(i)).getGrpIsRetrieved() == false) {
            			bgelBookings.add(tempBgelBookings.get(i));
            		}
            	}
            }
            
            boolean isGroupRetrieved = false;
            
            if (subProfileId != null) {
            	
            	List col = proxy.retrieveBGELGroup(subProfileId);  
            	
            	if (col != null) {
            		for (int i=0; i<col.size(); i++) {
            			
            			isGroupRetrieved = true;	
            			
                		IBankGroupDetail groupDetail = createObjectFromResult((OBGroupSearchResult)col.get(i));
                		
                			if (bgelBookings.size() == 0) {
                				bgelBookings.add(groupDetail);
                			}
                			else {
                				
                				boolean bgelFound = false;
                				
                				for (int j=0; j<bgelBookings.size(); j++) {
                    				if (((IBankGroupDetail)bgelBookings.get(j)).getBkgTypeCode().equals(((IBankGroupDetail)groupDetail).getBkgTypeCode())) {
                    					bgelFound = true;
                    					break;
                    				}
                    			}
                				if (!bgelFound) {
                					bgelBookings.add(groupDetail);
                				}
                			}
                	}
            	}
            }
            
            if (isGroupRetrieved == false) {
            	resultMap.put(LimitBookingAction.LIMIT_BOOKING_GROUP_RETRIEVED_FLAG, "false");
            }
            else {
            	resultMap.put(LimitBookingAction.LIMIT_BOOKING_GROUP_RETRIEVED_FLAG, "true");
            }
     
            limitBooking.setBankGroupList(bgelBookings);
            resultMap.put(LimitBookingAction.SESSION_LIMIT_BOOKING, limitBooking);
            resultMap.put(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT, (String)map.get(LimitBookingAction.LIMIT_BOOKING_FROM_EVENT));

        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
  
        return returnMap;
    }
    
    private IBankGroupDetail createObjectFromResult(OBGroupSearchResult col) {
		IBankGroupDetail obj = null;
		if (col == null) {
			return obj;
		} else {
			obj = new OBBankGroupDetail();

			// Group Records
			if (StringUtils.isNotEmpty(new Long(col.getGrpID()).toString())&&!new Long(col.getGrpID()).toString().equals(String.valueOf(ICMSConstant.LONG_INVALID_VALUE))){
				obj.setBkgTypeCode(new Long(col.getGrpID()).toString());
				obj.setBkgTypeDesc(col.getGroupName());
				obj.setLimitConvAmount(col.getConvLmt());
				obj.setLimitInvAmount(col.getInvLmt());
				obj.setLimitIslamAmount(col.getIslamLmt());
				obj.setLimitAmount(col.getGroupLmt());
                obj.setBkgType(ICMSConstant.BKG_TYPE_BGEL);
                obj.setGrpIsRetrieved(true);
                if (col.getGrpNo()==null ) {
                  Debug("getGrpNo  = null is null");
                }
            }
		}
		return obj;

	}
    
    private void Debug(String msg) {
    	DefaultLogger.debug(this,"RetrieveBGELCommand = " + msg);
	}
}