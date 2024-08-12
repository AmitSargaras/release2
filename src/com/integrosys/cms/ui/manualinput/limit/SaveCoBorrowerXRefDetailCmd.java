/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.OBLimitXRefCoBorrower;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveCoBorrowerXRefDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
		   //  	{ "xrefDetailForm", "java.lang.Object", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE }, 
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "restCoBorrowerList", "java.util.List", SERVICE_SCOPE },
				{"coBorrowerId", "java.lang.String", REQUEST_SCOPE },
				{ "coBorrowerName", "java.lang.String", REQUEST_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "lmtId", "java.lang.String", SERVICE_SCOPE },
				{ "restCoBorrowerIds", "java.lang.String", SERVICE_SCOPE },
				{ "restCoBorrowerIds", "java.lang.String", REQUEST_SCOPE },
				{ "facCoBorrowerLiabIds", "java.lang.String", REQUEST_SCOPE },
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
			//{"xrefDetailForm", "java.lang.Object", FORM_SCOPE },
			{ "restCoBorrowerList", "java.util.List", SERVICE_SCOPE },
			{ "restCoBorrowerIds", "java.lang.String", SERVICE_SCOPE },
			{ "facCoBorrowerLiabIds", "java.lang.String", SERVICE_SCOPE },
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
	
		HashMap temp = new HashMap();
		try {
			
			String event = (String) map.get("event");
			String fromevent = (String) map.get("fromevent");
			String index = (String) map.get("indexID");
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			System.out.println("inside SaveCoBorrowerXRefDetailCmd.java event====="+event+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ fromevent"+fromevent);
			
		/*	if	( EventConstant.EVENT_READ.equals(event) 
					|| EventConstant.EVENT_READ_UBS.equals(event)|| EventConstant.EVENT_READ_TS.equals(event)|| 
							//EventConstant.EVENT_READ_UBS_REJECTED.equals(event)|| 
							// EventConstant.EVENT_READ_UDF.equals(event)||
							EventConstant.EVENT_READ_RELEASED_LINE_DETAILS.equals(event)
							//||EventConstant.EVENT_READ_UDF_REJECTED.equals(event)
							|| EventConstant.EVENT_READ_RELEASED_LINE_DETAILS_REJECTED.equals(event)
							||EventConstant.EVENT_EDIT_UDF.equals(event) 	
							||EventConstant.EVENT_PREPARE_UPDATE_UBS.equals(event) 	
							 || EventConstant.EVENT_UPDATE_SUB_ACNT_UBS.equals(event)
						
					
					) {
				List list = new ArrayList();
				
				ILimitDAO lmtDao = LimitDAOFactory.getDAO();
				List vNames = new ArrayList();
				if(lmtTrxObj.getReferenceID()!=null) {
					System.out.println("lmtTrxObj.getReferenceID()==========="+lmtTrxObj.getReferenceID());
					vNames= lmtDao.getRestrictedCoBorrowerForLine(lmtTrxObj.getReferenceID());
				
					result.put("restCoBorrowerList",vNames);
				}
				
			}
			*/
			//Add CoBorrower
		//	if(!EventConstant.EVENT_DELETE_CO_BORROWER.equals(event)) {
				
		if	(EventConstant.EVENT_ADD_CO_BORROWER.equals(event) 
			|| 	EventConstant.EVENT_ADD_CO_BORROWER_1.equals(event) 
				/*|| EventConstant.EVENT_READ.equals(event) 
				|| EventConstant.EVENT_READ_UBS.equals(event)|| EventConstant.EVENT_READ_TS.equals(event)|| 
						//EventConstant.EVENT_READ_UBS_REJECTED.equals(event)|| 
						// EventConstant.EVENT_READ_UDF.equals(event)||
						EventConstant.EVENT_READ_RELEASED_LINE_DETAILS.equals(event)
						//||EventConstant.EVENT_READ_UDF_REJECTED.equals(event)
						|| EventConstant.EVENT_READ_RELEASED_LINE_DETAILS_REJECTED.equals(event)
						||EventConstant.EVENT_EDIT_UDF.equals(event) 		
				
				*/
				) {
			List list = (List)map.get("restCoBorrowerList");
			//System.out.println("list======================="+list);
			String coBorrowerId = (String)map.get("coBorrowerId");
			String coBorrowerName = (String)map.get("coBorrowerName");
					
			System.out.println("@@@@@@@@@@@coBorrowerId+++++++++++++"+coBorrowerId + "& coBorrowerName+++++++++++++++++++"+coBorrowerName);

			//DefaultLogger.debug(this, "Size of list for event edit "+list.size());
			DefaultLogger.debug(this, "in PrepareXrefDetailCmd.java ==720==>> lmtTrxObj.getReferenceID();"+ lmtTrxObj.getReferenceID()); 
			if(list==null)
			{
			list = new ArrayList();
			}
			boolean flag = false;
			ILimitDAO lmtDao = LimitDAOFactory.getDAO();
			List vNames = new ArrayList();
			if(lmtTrxObj.getReferenceID()!=null) {
				System.out.println("lmtTrxObj.getReferenceID()==========="+lmtTrxObj.getCustomerID());
				vNames= lmtDao.getRestrictedCoBorrowerForLine(lmtTrxObj.getCustomerID());
				
			}
			if(list!=null && list.size()!=0)
			{	
				System.out.println("list.size() ==================="+list.size() );
				if( list.size() >= 5)

				{
					System.out.println("list.size() is greater than 5############# ===================");
				//	HashMap exceptionMap = new HashMap();
				//	exceptionMap.put("restCoBorrowerListError", new ActionMessage("error.string.duplicate.coBorrower.size"));
					ICMSCustomerTrxValue partyGroupTrxValue = null;
					result.put("request.ITrxValue", partyGroupTrxValue);
					temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
				//	temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return temp;
				}
				
				for(int i = 0;i<list.size();i++)
				{
					OBLimitXRefCoBorrower ven = (OBLimitXRefCoBorrower)list.get(i);
					if((null != ven.getCoBorrowerId()  && ven.getCoBorrowerId().equals(coBorrowerId))){
					flag = false;
					}
				}
			}
			
			if(!flag)
			{
				System.out.println("flag::: "+flag);
				if(null !=vNames && vNames.size()!=0)
				{	
					for(int i = 0;i<vNames.size();i++)
					{
						OBLimitXRefCoBorrower ven = (OBLimitXRefCoBorrower)vNames.get(i);
						//	if( null != ven.getCoBorrowerName() &&ven.getCoBorrowerName().equals( coBorrowerName))
						if( null != ven.getCoBorrowerId() &&ven.getCoBorrowerId().equals( coBorrowerId))

							{
							//	HashMap exceptionMap = new HashMap();
							//	exceptionMap.put("restCoBorrowerListError", new ActionMessage("error.string.duplicate.coBorrower.id"));
								ICMSCustomerTrxValue partyGroupTrxValue = null;
								result.put("request.ITrxValue", partyGroupTrxValue);
								temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
							//	temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
								return temp;
							}
					}
				}
			
				for(int i = 0;i<list.size();i++)
				{
					OBLimitXRefCoBorrower ven = (OBLimitXRefCoBorrower)list.get(i);
				//	if(null != ven.getCoBorrowerName() && ven.getCoBorrowerName().equals(coBorrowerName))
					if( null != ven.getCoBorrowerId() &&ven.getCoBorrowerId().equals( coBorrowerId))

							{
					//	HashMap exceptionMap = new HashMap();
					//	exceptionMap.put("restCoBorrowerListError", new ActionMessage("error.string.duplicate.coBorrower.id"));
						ICMSCustomerTrxValue partyGroupTrxValue = null;
						result.put("request.ITrxValue", partyGroupTrxValue);
						temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
						//temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return temp;
							}
				}
				
			}
			
			OBLimitXRefCoBorrower value = new OBLimitXRefCoBorrower();
			if(null != coBorrowerName && !"".equals(coBorrowerName)) {
			value.setCoBorrowerId(coBorrowerId);
			value.setCoBorrowerName(coBorrowerName);
			
			list.add(value);
			}
			List	idlist = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				OBLimitXRefCoBorrower coborrObj = (OBLimitXRefCoBorrower) list.get(i);
				idlist.add(coborrObj.getCoBorrowerId());
				
			}
			
			String lineCoBorrowerIds = UIUtil.getDelimitedStringFromList(idlist, ",");
			   lineCoBorrowerIds = lineCoBorrowerIds==null ? "" : lineCoBorrowerIds ;
			   System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% lineCoBorrowerIds"+lineCoBorrowerIds);
			result.put("restCoBorrowerList",list);
			result.put("restCoBorrowerIds",lineCoBorrowerIds);
		}
			//CoBorrower added
			
		//Delete CoBorrower	
		/*if(EventConstant.EVENT_DELETE_CO_BORROWER.equals(event) || EventConstant.EVENT_DELETE_CO_BORROWER_1.equals(event)) {

				System.out.println("Inside Delete command"+event);
				
				List list = (List)map.get("restCoBorrowerList");
				
				String coBorrowerId = (String)map.get("coBorrowerId");
				String coBorrowerName = (String)map.get("coBorrowerName");
				
				list.remove((Integer.parseInt(index))-1);
				
				result.put("restCoBorrowerList", list);
			}
			*/
			/*result.put("index",index);
			result.put("event",event);*/
			
		
			result.put("lmtTrxObj", lmtTrxObj);
			String facCoBorrowerLiabIds =  (String) map.get("facCoBorrowerLiabIds");
			System.out.println("facCoBorrowerLiabIds in JAVA CMD:::::"+facCoBorrowerLiabIds);
			result.put("facCoBorrowerLiabIds", facCoBorrowerLiabIds);
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			return temp;		
			
			
			
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
	}
}
