package com.integrosys.cms.ui.creditriskparam.internalcreditrating;


import java.util.*;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.bankentitybranch.IBankEntityBranchParam;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRatingParam;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating;
import com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.OBInternalCreditRating;
import com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.apache.struts.action.ActionMessage;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import org.apache.commons.lang.StringUtils;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//public class SaveInternalCreditRatingItemCommand extends AbstractCommand {
public class SaveInternalCreditRatingItemCommand extends InternalCreditRatingCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"InternalCreditRatingItemForm", "java.lang.Object", FORM_SCOPE},	
			{"event", "java.lang.String", REQUEST_SCOPE},
			//{"errorEvent", "java.lang.String", REQUEST_SCOPE},
			{"indexID", "java.lang.String", REQUEST_SCOPE},
			{"fromEvent", "java.lang.String", REQUEST_SCOPE},
			{"internalCreditRatingTrxObj", "com.integrosys.cms.app.creditriskparam.trx.internalcreditrating.IInternalCreditRatingTrxValue", SERVICE_SCOPE},
			{"origItem", "com.integrosys.cms.app.creditriskparam.bus.internalcreditrating.IInternalCreditRating", SERVICE_SCOPE},			

		});
	}	
	
	public String[][] getResultDescriptor() {
		return (new String[][]{
			//{"errorEvent", "java.lang.String", REQUEST_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"curCreditRatingList","java.util.List", REQUEST_SCOPE }
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException 
	{
		HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try
		{
        	String event = (String)(map.get("event"));
        	String from_event = (String) map.get("fromEvent");
        	String indexID = (String)(map.get("indexID"));
        	IInternalCreditRatingTrxValue internalCreditRatingTrxObj = (IInternalCreditRatingTrxValue)map.get("internalCreditRatingTrxObj");
        	IInternalCreditRating item = (IInternalCreditRating)(map.get("InternalCreditRatingItemForm"));
        	IInternalCreditRating editParam = null;
        	List curCreditRatingList = null;
        	
        	if(internalCreditRatingTrxObj.getStagingICRList() == null) //item list would be null for initial submission
                curCreditRatingList = new ArrayList();
            else
                curCreditRatingList = new ArrayList(internalCreditRatingTrxObj.getStagingICRList());
        	
        	if (curCreditRatingList == null)  //for intial setup
                curCreditRatingList = new ArrayList();
        	
           if(StringUtils.isNotEmpty(indexID))
                editParam = (IInternalCreditRating) curCreditRatingList.get(Integer.parseInt(indexID));
              
        	if (curCreditRatingList.size() > 0) {
                if(editParam==null || !editParam.getIntCredRatCode().equals(item.getIntCredRatCode()))
                {
                    //validate duplicate internal rating grade, only when there is more than 1 item
                    for (Iterator iterator = curCreditRatingList.iterator(); iterator.hasNext();) {
                        IInternalCreditRating rating = (IInternalCreditRating) iterator.next();

                        if (rating.getIntCredRatCode().equals(item.getIntCredRatCode())) {
                            exceptionMap.put("duplicateEntryError", new ActionMessage("error.duplicate.item"));
                            break;
                        }
                    }
                }
          }
              
          if (exceptionMap.size() == 0) {  	
	        	 if (("maker_submit").equals(event)) 
	        	 {
	                    int index = Integer.parseInt(indexID);
	                    // IInternalCreditRating editItem = (IInternalCreditRating) curCreditRatingList.get(index);
	                    // maintain reference id for ejb update
	                    //item.setRefId(editItem.getRefId());
	                    //set the existing OB with amended fields to retain reference id for ejb update
	                    editParam.setIntCredRatCode(item.getIntCredRatCode());
                      editParam.setIntCredRatLmtAmt(item.getIntCredRatLmtAmt());
	                    curCreditRatingList.remove(index);
	                    curCreditRatingList.add(index, editParam);
	            } else {
	                    curCreditRatingList.add(item);
	            }
	            internalCreditRatingTrxObj.setStagingICRList(curCreditRatingList);           
          } 	
        	
				   result.put("curCreditRatingList",curCreditRatingList);
				   result.put("event", event);
			    
				
		}
        catch(Exception ex)
		{
			ex.printStackTrace();
            throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
	    temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	    return temp;
	}	
	
}


 
