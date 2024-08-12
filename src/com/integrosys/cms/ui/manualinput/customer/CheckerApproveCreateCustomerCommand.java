package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.json.command.PrepareSendReceivePartyCommand;
import com.integrosys.cms.app.json.dao.ScmPartyDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.manualinput.party.IIfscCodeDao;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.ecbf.counterparty.ClimesToECBFHelper;
import com.integrosys.cms.ui.manualinput.customer.bankingmethod.IBankingMethodDAO;

	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 29-03-2011
	 *
	 */

public class CheckerApproveCreateCustomerCommand extends AbstractCommand{
	
	/**		Default Constructor	 */
	
	public CheckerApproveCreateCustomerCommand() {
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
				{"ICMSCustomerTrxValue", "com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		}
		);
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
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		}
		);
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
		 HashMap exceptionMap = new HashMap();
		 
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// Customer Trx value 
			String loginUser = String.valueOf(ctx.getUser().getTeamTypeMembership().getMembershipID());
			ICMSCustomerTrxValue trxValueIn = (OBCMSCustomerTrxValue) map.get("ICMSCustomerTrxValue");
			
			ICMSCustomer stageCustomer = (OBCMSCustomer) trxValueIn.getStagingCustomer();
		//	ICMSCustomer actualCustomer = (OBCMSCustomer) trxValueIn.getCustomer();
			
			ISystem[] otherSystemStage = stageCustomer.getCMSLegalEntity().getOtherSystem();
		//	ISystem[] otherSystemActual = actualCustomer.getCMSLegalEntity().getOtherSystem();
			boolean isMatchFound = false;
			List checkSystemList = new ArrayList();
			if(otherSystemStage !=null){
			for (int i = 0; i < otherSystemStage.length; i++) {
				ISystem iSystem = otherSystemStage[i];
				/*isMatchFound = false;
				for (int j = 0; j < otherSystemActual.length; j++) {
					ISystem iSystemActual = otherSystemActual[j];

					if (iSystemActual.getSystem().equals(iSystem.getSystem())
							&& iSystemActual.getSystemCustomerId().equals(
									iSystem.getSystemCustomerId())) {
						isMatchFound = true;
					}
				}
				if (!isMatchFound) {*/
					checkSystemList.add(iSystem);
			//	}
				}
			}
			
			ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
			  List cust = customerDAO.getAllSystemAndSystemId();
			for (Iterator iterator = checkSystemList.iterator(); iterator.hasNext();) {
				ISystem object = (ISystem) iterator.next();
				
				if(cust!=null && cust.size()!=0)
				{	
					DefaultLogger.debug(this, " ---------total no. of system and system id combination is-------------"+cust.size());
				for(int i = 0;i<cust.size();i++)
				{
				OBSystem sys = (OBSystem)cust.get(i);
					DefaultLogger.debug(this, " ---------system and system id combination (in actual) is-------------"+sys.getSystem()+"---"+sys.getSystemCustomerId()+"---");
					if(sys.getSystem().equals(object.getSystem()) && sys.getSystemCustomerId().equals(object.getSystemCustomerId()))
							{
						DefaultLogger.debug(this, " -------system and system id combination matched (in actual)------------"+sys.getSystem()+"---"+sys.getSystemCustomerId()+"---");
						exceptionMap.put("bankingMethodEmptyError", new ActionMessage("error.string.duplicatesystem.id"));
						ICMSCustomerTrxValue partyGroupTrxValue = null;
						resultMap.put("request.ITrxValue", partyGroupTrxValue);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
							}
				}
				}
				
			}
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			//added by bharat for duplicate system - system id combination
			//SCM interface
			
			IJsInterfaceLog log = new OBJsInterfaceLog();
			ScmPartyDao scmPartyDao = (ScmPartyDao)BeanHouse.get("scmPartyDao");	
			String custId=stageCustomer.getCifId();
			String mainScmFlag = scmPartyDao.getBorrowerScmFlag(custId);
			String stgScmFlag = scmPartyDao.getStgBorrowerScmFlag(trxValueIn.getStagingReferenceID());

			// Function  to approve updated Customer Trx
			ICMSCustomerTrxValue trxValueOut = CustomerProxyFactory.getProxy().checkerApproveCreateCustomer(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
			//IFSC Actual table insert
			String stagingReferenceID=trxValueIn.getStagingReferenceID();
			String referenceID=trxValueOut.getReferenceID();
			IIfscCodeDao ifscCodeDao = (IIfscCodeDao) BeanHouse.get("ifscCodeDao");
			
			String ifscCodeList= customerDAO.getIfscCodeList("stage",stagingReferenceID);
			if(null!=ifscCodeList && !"".equals(ifscCodeList)) {
				String[] ifscStringArry = ifscCodeList.split(",");
				ifscCodeDao.createActualIfscCode(ifscStringArry,referenceID);
			}
			
		//START INSERT RECORDS INTO CMS_BANKING_METHOD_CUST FOR BANKING_METHODS	
			String bankingMethodss = stageCustomer.getFinalBankMethodList();
			
			IBankingMethodDAO bankingMethodDAOImpl = (IBankingMethodDAO)BeanHouse.get("bankingMethodDAO");
//			String bankingMethodss = obCustomer.getBankingMethod();
//			if(bankingMethodss == null || "".equals(bankingMethodss)) {
//				bankingMethodss = stageCustomer.getFinalBankMethodList();
//			}
			if(bankingMethodss != null && !"".equals(bankingMethodss)) {
			String[] bankMethodArr = bankingMethodss.split(",");
			ArrayList bankMethList = new ArrayList();
			for(int i=0; i<bankMethodArr.length;i++) {
				String[] bankMethodArr1 =bankMethodArr[i].split("-");
				bankMethList.add(bankMethodArr1[0]);
			}
			
			for(int i=0;i<bankMethList.size();i++) {
				OBBankingMethod obj = new OBBankingMethod();
				obj.setBankType((String)bankMethList.get(i));
				obj.setLEID(Long.parseLong(trxValueOut.getReferenceID()));
				obj.setCustomerIDForBankingMethod(stageCustomer.getCifId());
				obj.setStatus("ACTIVE");
				bankingMethodDAOImpl.insertBankingMethodCustActual(obj);				
				
			}
			}
			//END 
			
			//SCM Interface 
			DefaultLogger.debug(this, "Going to call SCM webservice");
			DefaultLogger.debug(this, "get transaction Id "+trxValueIn.getTransactionID());
			DefaultLogger.debug(this, "get reference Id "+trxValueIn.getReferenceID());
			DefaultLogger.debug(this, "get reference Id "+trxValueIn.getTransactionHistoryID());
			DefaultLogger.debug(this, "get trx reference Id "+trxValueIn.getTrxReferenceID());
			DefaultLogger.debug(this, "get trx subtype "+trxValueIn.getTransactionSubType());
			DefaultLogger.debug(this, "get trx type "+trxValueIn.getTransactionType());
			DefaultLogger.debug(this, "get trx staging ref id "+trxValueIn.getStagingReferenceID());

			try {
				PrepareSendReceivePartyCommand scmWsCall = new PrepareSendReceivePartyCommand();
				log.setModuleName("CUSTOMER");
				log.setIs_udf_upload("N");
				if(stgScmFlag.equalsIgnoreCase("Yes")) {
					DefaultLogger.debug(this, "Inside if as scmFlag is Yes "+mainScmFlag+" "+stgScmFlag);
					scmWsCall.scmWebServiceCall(custId, "A", "Y",log);
				}else {
					DefaultLogger.debug(this, "Inside else as scm flag is no. Need not call the service ");
				}

				
			}catch(Exception e) {
				DefaultLogger.debug(this, "error in SCM webservice "+e);
			}
			
			try {
				ClimesToECBFHelper.sendRequest(trxValueOut.getCustomer());
			} catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.error(this, "Exception caught inside sendRequest while sending data to ecbf party webservice with error: " + e.getMessage(), e);
			}

			
		}catch (CustomerException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
