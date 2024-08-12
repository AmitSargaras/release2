package com.integrosys.cms.ui.manualinput.customer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICriInfo;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.email.notification.bus.ICustomerNotificationDetail;
import com.integrosys.cms.app.email.notification.bus.IEmailNotification;
import com.integrosys.cms.app.email.notification.bus.IEmailNotificationService;
import com.integrosys.cms.app.email.notification.bus.OBCustomerNotificationDetail;
import com.integrosys.cms.app.json.command.PrepareSendReceivePartyCommand;
import com.integrosys.cms.app.json.dao.ScmPartyDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.limitsOfAuthorityMaster.trxlog.bus.ILimitsOfAuthorityMasterTrxLog;
import com.integrosys.cms.app.manualinput.party.IIfscCodeDao;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.manualinput.customer.bankingmethod.IBankingMethodDAO;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limitsOfAuthorityMaster.LimitsOfAuthorityMasterHelper;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.cms.ui.ecbf.counterparty.ClimesToECBFHelper;

	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 30-03-2011
	 *
	 */

public class CheckerApproveUpdateCustomerCommand extends AbstractCommand{
	

	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateCustomerCommand() {
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
				{ IGlobalConstant.USER, ICommonUser.class.getName() , GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, String.class.getName(), GLOBAL_SCOPE }, 
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
			ICMSCustomerTrxValue trxValueIn = (OBCMSCustomerTrxValue) map.get("ICMSCustomerTrxValue");

			ICMSCustomer stageCustomer = (OBCMSCustomer) trxValueIn.getStagingCustomer();
			ICMSCustomer actualCustomer = (OBCMSCustomer) trxValueIn.getCustomer();
			
			

			ILimitsOfAuthorityMasterTrxLog obLimitsOfAuthorityMasterTrxLog = null;
			String customerSegment = stageCustomer.getCustomerSegment();
			ICommonUser user = (ICommonUser) (map.get(IGlobalConstant.USER));
			String teamTypeMembershipID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
			boolean isLoaAuthorizer = String.valueOf(ICMSConstant.TEAM_TYPE_SSC_CHECKER).equals(teamTypeMembershipID) || 
					String.valueOf(ICMSConstant.CPU_MAKER_CHECKER).equals(teamTypeMembershipID);
			
			String sanctionedAmtUpdateFlag = CustomerDAOFactory.getDAO().getSanctionedAmtUpdatedFlag(actualCustomer.getCustomerID());
			if(ICMSConstant.YES.equals(sanctionedAmtUpdateFlag) || isLoaAuthorizer) {
				Map loaValidationMap = LimitsOfAuthorityMasterHelper.validateLOAMasterFieldsCustomer(trxValueIn, user, customerSegment, exceptionMap, sanctionedAmtUpdateFlag);
				exceptionMap = (HashMap) loaValidationMap.get("exceptionMap");
				obLimitsOfAuthorityMasterTrxLog = (ILimitsOfAuthorityMasterTrxLog) loaValidationMap.get("obLimitsOfAuthorityMasterTrxLog");
			}
			
			if(!exceptionMap.isEmpty()) {
				resultMap.put("request.ITrxResult", null);
				resultMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				resultMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return resultMap;
			}
			
			
			ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
			String printLogger = bundle1.getString("print.logger.enable");
			if(null!= printLogger && printLogger.equalsIgnoreCase("Y")){
				System.out.println("stageCustomer.getTotalFundedLimit():::"+stageCustomer.getTotalFundedLimit());
			}
			
			ISystem[] otherSystemStage = stageCustomer.getCMSLegalEntity().getOtherSystem();
			ISystem[] otherSystemActual = actualCustomer.getCMSLegalEntity().getOtherSystem();
			boolean isMatchFound = false;
			List checkSystemList = new ArrayList();
			if(otherSystemStage!=null){
			for (int i = 0; i < otherSystemStage.length; i++) {
				ISystem iSystem = otherSystemStage[i];
				isMatchFound = false;
				if(otherSystemActual!=null){
					for (int j = 0; j < otherSystemActual.length; j++) {
						ISystem iSystemActual = otherSystemActual[j];
	
						if (iSystemActual.getSystem().equals(iSystem.getSystem())
								&& iSystemActual.getSystemCustomerId().equals(
										iSystem.getSystemCustomerId())) {
							isMatchFound = true;
						}
					}
				}
				if (!isMatchFound) {
					checkSystemList.add(iSystem);
				}
			}
			}
			ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
			  List cust = customerDAO.getAllSystemAndSystemId();
			for (Iterator iterator = checkSystemList.iterator(); iterator.hasNext();) {
				ISystem object = (ISystem) iterator.next();
				
				if(cust!=null && cust.size()!=0)
				{	
					//Comment below lines to remove log info. from console
					//DefaultLogger.debug(this, " ---------total no. of system and system id combination is-------------"+cust.size());
				for(int i = 0;i<cust.size();i++)
				{
				OBSystem sys = (OBSystem)cust.get(i);
				//Comment below lines to remove log info. from console
					//DefaultLogger.debug(this, " ---------system and system id combination (in actual) is-------------"+sys.getSystem()+"---"+sys.getSystemCustomerId()+"---");
					if(sys.getSystem().equals(object.getSystem()) && sys.getSystemCustomerId().equals(object.getSystemCustomerId()))
							{
						//Comment below lines to remove log info. from console
					//	DefaultLogger.debug(this, " -------system and system id combination matched (in actual)------------"+sys.getSystem()+"---"+sys.getSystemCustomerId()+"---");
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
			
			//SCM Interface
			IJsInterfaceLog log = new OBJsInterfaceLog();
			ScmPartyDao scmPartyDao = (ScmPartyDao)BeanHouse.get("scmPartyDao");	
			String custId=stageCustomer.getCifId();
			String mainScmFlag = scmPartyDao.getBorrowerScmFlag(custId);
			String stgScmFlag = scmPartyDao.getStgBorrowerScmFlag(trxValueIn.getStagingReferenceID());
			System.out.println("*****Inside CheckerApproveUpdateCustomerCommand***215****caling getLatestOperationStatus() for party SCM Interface***** party id :  "+custId);
			String latestOperationStatus = scmPartyDao.getLatestOperationStatus(custId,"CUSTOMER");
			System.out.println("*****Inside CheckerApproveUpdateCustomerCommand***217****operation is"+latestOperationStatus);
			
			DefaultLogger.debug(this, "Updating Sanctioned Amount Flag from Checker Approve for partyId :"+stageCustomer.getCifId());
			actualCustomer.setSanctionedAmtUpdatedFlag(ICMSConstant.NO);
			stageCustomer.setSanctionedAmtUpdatedFlag(ICMSConstant.NO);
			
			// Function  to approve updated Customer Trx
			ICMSCustomerTrxValue trxValueOut = CustomerProxyFactory.getProxy().checkerApproveUpdateCustomer(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
			

			if(obLimitsOfAuthorityMasterTrxLog != null) {
				obLimitsOfAuthorityMasterTrxLog = LimitsOfAuthorityMasterHelper.prepareObLimitsOfAuthorityMasterTrxLogCustomer(trxValueOut, obLimitsOfAuthorityMasterTrxLog);
				LimitsOfAuthorityMasterHelper.logLimitsOfAuthorityTrxData(obLimitsOfAuthorityMasterTrxLog);
			}
			
			sanctionedAmtUpdateFlag = customerDAO.getSanctionedAmtUpdatedFlag(actualCustomer.getCustomerID());
			if(ICMSConstant.YES.equals(sanctionedAmtUpdateFlag)) {
				customerDAO.updateSanctionedAmountUpdatedFlag(actualCustomer.getCifId(), ICMSConstant.NO);
			}
			

			//for RAM Rating CR
			ICriInfo stageCriInfo[]=stageCustomer.getCMSLegalEntity().getCriList();
			String stageCustomerFyClosure=stageCriInfo[0].getCustomerFyClouser();
			
			ICriInfo actualCriInfo[]=actualCustomer.getCMSLegalEntity().getCriList();
			String actualCustomerFyClosure=actualCriInfo[0].getCustomerFyClouser();
			
			if(null!=stageCustomerFyClosure && !stageCustomerFyClosure.equals(actualCustomerFyClosure)) {
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				String lspLeId=stageCustomer.getCifId();
				String ramYear=customerDAO.getRamYear(lspLeId);
				if(null!=ramYear && !"".equals(ramYear)) {
					int year=Integer.parseInt(ramYear);
					String limitProfileID=customerDAO.getLimitProfileID(lspLeId);
					String checkListId=customerDAO.getChecklistId(Long.parseLong(limitProfileID));
					if("December Ending".equals(stageCustomerFyClosure)) {
						year=year+1;
						String decDate = "01-Aug-"+year;
						customerDAO.updateRamDueDate(checkListId,decDate,"AWAITING");
					}
					if("March Ending".equals(stageCustomerFyClosure)) {
						String marchDate = "01-Nov-"+year;
						customerDAO.updateRamDueDate(checkListId,marchDate,"AWAITING");
					}
				}
			}
			
			//IFSC Actual table insert
			String stagingReferenceID=trxValueIn.getStagingReferenceID();
			String referenceID=trxValueOut.getReferenceID();
			IIfscCodeDao ifscCodeDao = (IIfscCodeDao) BeanHouse.get("ifscCodeDao");
			
			String ifscCodeList= customerDAO.getIfscCodeList("stage",stagingReferenceID);
			if(null!=ifscCodeList && !"".equals(ifscCodeList)) {
				String[] ifscStringArry = ifscCodeList.split(",");
				ifscCodeDao.disableActualIfscCode(referenceID);
				ifscCodeDao.createActualIfscCode(ifscStringArry,referenceID);
			}
			
			try {
				ClimesToECBFHelper.sendRequest(trxValueOut.getCustomer());
			} catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.error(this, "Exception caught inside sendRequest while sending data to ecbf party webservice with error: " + e.getMessage(), e);
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
			
			
			bankingMethodDAOImpl.disableActualBankingMethod(trxValueOut.getReferenceID());
			
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
			
			
			
			// For Notification Party Closed
			IEmailNotificationService service=(IEmailNotificationService)BeanHouse.get("emailNotificationService");
			ICMSCustomer customer = trxValueOut.getCustomer();
			if("INACTIVE".equals(customer.getStatus())){
				ICustomerNotificationDetail noticationDetail= new OBCustomerNotificationDetail();
				noticationDetail.setPartyId(String.valueOf(customer.getCustomerID()));
				noticationDetail.setPartyName(customer.getCustomerName());
				DefaultLogger.debug(this, "Creating notification");
				IEmailNotification createNotification = service.createNotification("NOT0001", noticationDetail);
				DefaultLogger.debug(this, "Notification Created"+createNotification.getNotifcationId());
			}
			
			//SCM Interface 
			DefaultLogger.debug(this, "Going to call SCM webservice");
			DefaultLogger.debug(this, "get transaction Id "+trxValueIn.getTransactionID());
			DefaultLogger.debug(this, "get reference Id "+trxValueIn.getReferenceID());
			DefaultLogger.debug(this, "get reference Id "+trxValueIn.getTransactionHistoryID());
			DefaultLogger.debug(this, "get trx type "+trxValueIn.getTransactionType());
			DefaultLogger.debug(this, "get trx staging ref id "+trxValueIn.getStagingReferenceID());
			DefaultLogger.debug(this, "get Stage scm flag "+stgScmFlag);
			DefaultLogger.debug(this, "get scm Flag "+mainScmFlag);
			DefaultLogger.debug(this, "get status Flag "+latestOperationStatus);

			try {
				PrepareSendReceivePartyCommand scmWsCall = new PrepareSendReceivePartyCommand();
				log.setModuleName("CUSTOMER");
				log.setIs_udf_upload("N");
				DefaultLogger.debug(this, "New Object created "+scmWsCall);
				if(latestOperationStatus!=null && latestOperationStatus.equalsIgnoreCase("A")) {
					DefaultLogger.debug(this, "Sending again operation as A because earlier record was rejected ");
					if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("Yes")) {
						scmWsCall.scmWebServiceCall(custId, "A", "Y",log);
					}else if(mainScmFlag.equalsIgnoreCase("No")&&stgScmFlag.equalsIgnoreCase("Yes")) {
						scmWsCall.scmWebServiceCall(custId, "A","Y", log);
					}else {
						DefaultLogger.debug(this, "Inside else as both are no. Need not call the service "+mainScmFlag+" "+stgScmFlag);
					}
				}else {
					DefaultLogger.debug(this, "Sending operation as U because the party is presnt in SCM "+latestOperationStatus);
					if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("Yes")) {
						scmWsCall.scmWebServiceCall(custId, "U", "Y",log);
					}else if(mainScmFlag.equalsIgnoreCase("No")&&stgScmFlag.equalsIgnoreCase("Yes")) {
						scmWsCall.scmWebServiceCall(custId, "U","Y", log);
					}else if(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("No")) {
						scmWsCall.scmWebServiceCall(custId, "U","N", log);
					}else {
						DefaultLogger.debug(this, "Inside else as both are no. Need not call the service "+mainScmFlag+" "+stgScmFlag);
					}
				}
			}catch(Exception e) {
				DefaultLogger.debug(this, "error in SCM webservice ");
				e.printStackTrace();

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
