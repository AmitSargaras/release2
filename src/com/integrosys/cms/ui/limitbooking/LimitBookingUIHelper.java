package com.integrosys.cms.ui.limitbooking;

import java.rmi.RemoteException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.user.proxy.CMSStdUserProxyFactory;
import com.integrosys.cms.app.user.proxy.SBStdUserProxy;
import com.integrosys.component.user.app.bus.UserException;

/**
 * @author priya
 *
 */

public class LimitBookingUIHelper {

    public static String getBookingDescription(String code) {
        String desc = null;
        
        if (code == null){
            return desc;
        } else if (ICMSConstant.STATUS_LIMIT_BOOKING_BOOKED.equals(code)){
            return "Booked";
        } else if (ICMSConstant.STATUS_LIMIT_BOOKING_SUCCESSFUL.equals(code)){
            return "Successful";
        } else if (ICMSConstant.STATUS_LIMIT_BOOKING_EXPIRED.equals(code)){
            return "Expired";
        } else if (ICMSConstant.STATUS_LIMIT_BOOKING_DELETED.equals(code)){
            return "Deleted";
        }

        return desc;
    }
    
    public static String getUserNameByUserID (String userID) throws CommandProcessingException {
    	
    	String userName = null;
    	
    	try {
    		
    		SBStdUserProxy proxy = (SBStdUserProxy)CMSStdUserProxyFactory.getUserProxy();
            userName = proxy.getUserNameByUserID(new Long(userID).longValue());
 
    	} catch (UserException e) {
    		DefaultLogger.debug("LimitBookingUIHelper", "got user exception in limit booking UI" + e);
    		e.printStackTrace();
    		throw (new CommandProcessingException(e.getMessage()));
    	}
    	  catch (RemoteException e) {
    		DefaultLogger.debug("LimitBookingUIHelper", "got remote exception in limit booking UI" + e);
    		e.printStackTrace();
    		throw (new CommandProcessingException(e.getMessage()));
    	}
    	  
    	return userName;
    	 
    }
    
    
    public static String getBookingTypeDescription(String code, String desc) {
    	
    	if (code == null){
            return desc;
        } else if (ICMSConstant.BKG_SUB_TYPE_CONV.equals(code)){
            return "Conventional Bank BGEL";
        } else if (ICMSConstant.BKG_SUB_TYPE_ISLM.equals(code)){
            return "Islamic BANK BGEL";
        } else if (ICMSConstant.BKG_SUB_TYPE_BANK.equals(code)){
            return "Banking Group BGEL";
        } else if (ICMSConstant.BKG_SUB_TYPE_INV.equals(code)){
            return "Investment Bank BGEL";
        } else if (ICMSConstant.BKG_SUB_TYPE_BANK_NONFI.equals(code)){
            return "Banking Group BGEL (Non Fi)";
        } else if (ICMSConstant.BKG_SUB_TYPE_EXEMPT.equals(code)){
            return "Exempt Bank BGEL";
        } else if (ICMSConstant.BKG_SUB_TYPE_NON_EXEMPT.equals(code)){
            return "Non Exempt Bank BGEL";
        } else if (ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_BG.equals(code)){
            return "Banking Group GP5 Limit";
        } else if (ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_CONV.equals(code)){
            return "Conventional GP5 Limit";
        }else if (ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_ISLM.equals(code)){
            return "Islamic GP5 Limit";
        }else if (ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_GP5_INV.equals(code)){
            return "Investment GP5 Limit";
        }else if (ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_BG.equals(code)){
            return "Banking Group Internal Percentage Limit";
        }else if (ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_CONV.equals(code)){
            return "Conventional Internal Percentage Limit";
        }else if (ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_ISLM.equals(code)){
            return "Islamic Internal Percentage Limit";
        }else if (ICMSConstant.BKG_SUB_TYPE_BANK_WIDE_ILP_INV.equals(code)){
            return "Investment Internal Percentage Limit";
        }else if (ICMSConstant.BANKING_GROUP_ENTRY_CODE.equals(code)){
            return "Banking Group";
        }else if (ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE.equals(code)){
            return "Conventional Bank";
        }else if (ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE.equals(code)){
            return "Islamic Bank";
        }else if (ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE.equals(code)){
            return "Investment Bank";
        }

        return desc;
    }

}
