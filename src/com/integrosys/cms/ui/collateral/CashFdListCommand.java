/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/ReturnCollateralCommand.java,v 1.4 2003/09/19 08:49:33 hshii Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionMessage;

import com.iflex.fcr.xface.td.inq.FixedDepositInquiryRequestDTO;
import com.iflex.fcr.xface.td.inq.FixedDepositInquiryResponseDTO;
import com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapper;
import com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapperPortBindingStub;
import com.iflex.fcr.xface.td.inq.FixedDepositInquiryWrapperServiceLocator;
import com.iflex.fcr.xface.td.inq.SessionContext;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/19 08:49:33 $ Tag: $Name: $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 12:13:00 PM
 * To change this template use Options | File Templates.
 */
public class CashFdListCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
				 { "receipt_no", "java.lang.String", REQUEST_SCOPE },
				 { "ownval", "java.lang.String", REQUEST_SCOPE },
				 { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				 { IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
        });
    }
    
    public String[][] getResultDescriptor() {
        return (new String[][]{
            	{ "fdList", "java.util.List", REQUEST_SCOPE },
            	{"fdWebServiceFlag", "java.lang.String",REQUEST_SCOPE},
            	{"fdMap","java.util.HashMap",SERVICE_SCOPE},
          });
    }
    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        String receipt_no=(String)map.get("receipt_no");
        String ownval=(String)map.get("ownval");
        String sql ="";
        String partyId ="";
        ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
        ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
        String fdWebServiceFlag ="";
        if(null == receipt_no || "".equals(receipt_no))
        {
        	exceptionMap.put("rec",  new ActionMessage("error.string.not.zero","Please Input Recipt No."));
        }
        else
 {
			fdWebServiceFlag = CollateralDAOFactory.getDAO().getFdEnableFlag();
			if (fdWebServiceFlag != null
					&& "Y".equalsIgnoreCase(fdWebServiceFlag)) {
				Map<String, String> fdMap = new HashMap<String, String>();
				
				//Code added by Ankit : For fd receipt no length =14 char then only validated through FC flex web service else data will be fetched from database
				if(receipt_no.trim().length()==14){
				//end
				
				// Web services call in case of FD security
				String issueDate = "";
				String depositeMaturityDate = "";
				String depositorName ="";
				String depositeAmt="";
				String depositeInterestRate="";
				String errorMessage="";
				try {
	
					SimpleDateFormat format1=new SimpleDateFormat("dd/MMM/yyyy");
					SimpleDateFormat format2=new SimpleDateFormat("dd-MMM-yy");
					

					int bankCode=Integer.parseInt(PropertyManager.getValue("fdflexcube.sessioncontext.bankcode"));
					String channel=PropertyManager.getValue("fdflexcube.sessioncontext.channel");
					String serviceCode=PropertyManager.getValue("fdflexcube.sessioncontext.servicecode");
					String userId=PropertyManager.getValue("fdflexcube.sessioncontext.userid");
					
					DefaultLogger.debug(this, "bankCode:"+bankCode);
					DefaultLogger.debug(this, "channel:"+channel);
					DefaultLogger.debug(this, "serviceCode:"+serviceCode);
					DefaultLogger.debug(this, "userId:"+userId);
					SessionContext sessionContext=new SessionContext();
					sessionContext.setBankCode(bankCode);
					sessionContext.setChannel(channel);
					sessionContext.setServiceCode(serviceCode);
					sessionContext.setUserId(userId);
				
					
					DefaultLogger.debug(this, "fdFlexEndPoint::::"+PropertyManager.getValue("fdflexcube.webservice.url"));
					String fdFlexEndPoint = PropertyManager.getValue("fdflexcube.webservice.url");
					if(fdFlexEndPoint!=null){
						fdFlexEndPoint = fdFlexEndPoint.trim();
						DefaultLogger.debug(this, "fdFlexEndPoint.trim()::::"+fdFlexEndPoint);
						
					}
					
					FixedDepositInquiryWrapperServiceLocator fdlocator=new FixedDepositInquiryWrapperServiceLocator();
					
					DefaultLogger.debug(this,"fdlocator:"+fdlocator);
					DefaultLogger.debug(this,"setting fdFlexEndPoint address");
					fdlocator.setFixedDepositInquiryWrapperPortEndpointAddress(fdFlexEndPoint);
					
					DefaultLogger.debug(this,"completed setting of  fdFlexEndPoint address");
					FixedDepositInquiryWrapper fixedDepositInquiryWrapperPort = fdlocator.getFixedDepositInquiryWrapperPort();
					DefaultLogger.debug(this,"fixedDepositInquiryWrapperPort:"+fixedDepositInquiryWrapperPort);
					

					FixedDepositInquiryRequestDTO fixedDepositInquiryRequestDTO=new FixedDepositInquiryRequestDTO();
		
					fixedDepositInquiryRequestDTO.setFdAccountNo(receipt_no);
					DefaultLogger.debug(this, "calling getFixedDepositDetails");
					FixedDepositInquiryResponseDTO fixedDepositDetails = fixedDepositInquiryWrapperPort.getFixedDepositDetails(sessionContext, fixedDepositInquiryRequestDTO);
					DefaultLogger.debug(this, "call getFixedDepositDetails completed sucessfully.");
				
					
					FixedDepositInquiryWrapperPortBindingStub fdStub=(FixedDepositInquiryWrapperPortBindingStub)fixedDepositInquiryWrapperPort;
					DefaultLogger.debug(this, "fdStub:"+fdStub);
					
					 String request = fdStub._getCall().getMessageContext().getRequestMessage().getSOAPPartAsString();
					 DefaultLogger.debug(this,"Fd request:"+request);
					
						
					String response = fdStub._getCall().getMessageContext().getResponseMessage().getSOAPPartAsString();
					DefaultLogger.debug(this,"Fd response:"+response);
					
					if(null!=fixedDepositDetails){
					String errorCode = fixedDepositDetails.getTransactionStatus().getErrorCode();
					
					String replyCode =String.valueOf(fixedDepositDetails.getTransactionStatus().getReplyCode());
					String replyText = fixedDepositDetails.getTransactionStatus().getReplyText();
					
					
					
					if("0".equals(replyCode)){
						
						fdMap.put("errorMessage", errorMessage);
						
					}else{
						errorMessage=replyCode+":"+replyText;
					fdMap.put("errorMessage", errorMessage);
					}

					 depositorName = fixedDepositDetails.getDepositorName();
					 depositeAmt =String.valueOf(fixedDepositDetails.getDepositAmount());
					if (null != fixedDepositDetails.getDepositDate() && !"".equals(fixedDepositDetails.getDepositDate())) {

						
						issueDate=format1.format(format2.parse(fixedDepositDetails.getDepositDate()));
						
					}
					if (null != fixedDepositDetails.getMaturityDate() && !"".equals(fixedDepositDetails.getMaturityDate())) {
						depositeMaturityDate = format1.format(format2.parse(fixedDepositDetails.getMaturityDate()));
					}
					 depositeInterestRate = String.valueOf(fixedDepositDetails
							.getInterestRate());
		
					}
						fdMap.put("depositorName", depositorName);
						fdMap.put("depositeAmt", depositeAmt);
						fdMap.put("issueDate", issueDate);
						fdMap.put("depositeMaturityDate", depositeMaturityDate);
						fdMap.put("depositeInterestRate", depositeInterestRate);
						fdMap.put("depositorNo", receipt_no);
				
					DefaultLogger.debug(this, "depositeReceiptNo:" + receipt_no
							+ ",depositeAmt:" + depositeAmt
							+ ",depositeInterestRate:" + depositeInterestRate
							+ ",issueDate:" + issueDate
							+ ",depositeMaturityDate:" + depositeMaturityDate
							+ ",depositeAmt:" + depositeAmt + ",depositorName:"
							+ depositorName+"errorMessage:"+errorMessage);
			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					fdMap.put("errorMessage", "Failed to connect to webservice.");

				}

				/*
				 * fdMap.put("depositorName", "Sam,sung");
				 * fdMap.put("depositeAmt", "2,000"); fdMap.put("issueDate",
				 * "02/Jan/2015"); fdMap.put("depositeMaturityDate",
				 * "31/Jan/2016"); fdMap.put("depositeInterestRate", "9.5");
				 * fdMap.put("depositorNo", receipt_no);
				 */
				result.put("fdMap", fdMap);
			}

			else {
				if (null != ownval) {
					// for own party issuer
					if (ownval.equals("true")) {
						// partyId = " and lsp_le_id = "+itrxValue.getLegalID();
						partyId = " and lsp_le_id = '" + custOB.getCifId()
								+ "'";
					}
					// for other party issuer
					else {
						// partyId =
						// " and lsp_le_id <> "+itrxValue.getLegalID();
						partyId = " and lsp_le_id <> '" + custOB.getCifId()
								+ "'";
					}

					sql = "select cd.CASH_DEPOSIT_ID,( CASE  WHEN cd.is_own_bank = 'Y' THEN sp.LSP_SHORT_NAME WHEN cd.is_own_bank = 'N' THEN cd.DEPOSITOR_NAME END ) AS DEPOSITOR_NAME ,cd.CMS_COLLATERAL_ID,cd.DEPOSIT_AMOUNT,COALESCE((select sum(LIEN_AMOUNT) from cms_lien cl where cd.cash_deposit_id = cl.cash_deposit_id) ,0)as LIEN_AMOUNT,cd.ISSUE_DATE,cd.DEPOSIT_MATURITY_DATE,cd.DEPOSIT_INTEREST_RATE , sp.LSP_SHORT_NAME "
							+ " FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "
							+ " where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "
							+ " AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "
							+ " AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "
							+ " and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "
							+ " AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "
							+ " AND lsm.UPDATE_STATUS_IND = 'I'	AND cd.ACTIVE ='active' and cd.deposit_reference_number = '"
							+ receipt_no + "'" + partyId;

					// sql="SELECT CASH_DEPOSIT_ID,DEPOSITOR_NAME,CMS_COLLATERAL_ID,DEPOSIT_AMOUNT,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE FROM CMS_CASH_DEPOSIT WHERE deposit_reference_number = '"+receipt_no+"' AND STATUS='ACTIVE' AND ACTIVE='active'";
					if (null != receipt_no || !"".equals(receipt_no)) {
						List fdList = CollateralDAOFactory.getDAO()
								.selectCashFdList(sql, receipt_no);
						result.put("fdList", fdList);
					}
				} else {
					exceptionMap.put("rec", new ActionMessage(
							"error.string.not.zero", "Lien Number"));
				}
				
				result.put("fdMap", fdMap);
			}
		}else {
			if (null != ownval) {
				// for own party issuer
				if (ownval.equals("true")) {
					// partyId = " and lsp_le_id = "+itrxValue.getLegalID();
					partyId = " and lsp_le_id = '" + custOB.getCifId()
							+ "'";
				}
				// for other party issuer
				else {
					// partyId =
					// " and lsp_le_id <> "+itrxValue.getLegalID();
					partyId = " and lsp_le_id <> '" + custOB.getCifId()
							+ "'";
				}

				sql = "select cd.CASH_DEPOSIT_ID,( CASE  WHEN cd.is_own_bank = 'Y' THEN sp.LSP_SHORT_NAME WHEN cd.is_own_bank = 'N' THEN cd.DEPOSITOR_NAME END ) AS DEPOSITOR_NAME ,cd.CMS_COLLATERAL_ID,cd.DEPOSIT_AMOUNT,COALESCE((select sum(LIEN_AMOUNT) from cms_lien cl where cd.cash_deposit_id = cl.cash_deposit_id) ,0)as LIEN_AMOUNT,cd.ISSUE_DATE,cd.DEPOSIT_MATURITY_DATE,cd.DEPOSIT_INTEREST_RATE , sp.LSP_SHORT_NAME "
						+ " FROM CMS_LIMIT_SECURITY_MAP lsm,SCI_LSP_APPR_LMTS lmts,SCI_LSP_LMT_PROFILE pf,SCI_LE_SUB_PROFILE sp,CMS_CASH_DEPOSIT cd,cms_security sec "
						+ " where sp.CMS_LE_SUB_PROFILE_ID = pf.CMS_CUSTOMER_ID AND pf.CMS_LSP_LMT_PROFILE_ID  = lmts.CMS_LIMIT_PROFILE_ID "
						+ " AND lmts.CMS_LSP_APPR_LMTS_ID  = lsm.CMS_LSP_APPR_LMTS_ID	AND cd.CMS_COLLATERAL_ID (+)   = lsm.CMS_COLLATERAL_ID "
						+ " AND (lsm.UPDATE_STATUS_IND != 'D' OR lsm.UPDATE_STATUS_IND IS NULL)	AND (CD.STATUS = 'ACTIVE')	AND sp.status != 'INACTIVE' "
						+ " and lmts.cms_limit_status <> 'DELETED'and sec.cms_collateral_id = cd.cms_collateral_id "
						+ " AND lsm.CHARGE_ID            = (SELECT MAX(CHARGE_ID) FROM CMS_LIMIT_SECURITY_MAP MAPS1 WHERE MAPS1.CMS_COLLATERAL_ID=CD.CMS_COLLATERAL_ID and MAPS1.CMS_LSP_APPR_LMTS_ID = lsm.CMS_LSP_APPR_LMTS_ID) "
						+ " AND lsm.UPDATE_STATUS_IND = 'I'	AND cd.ACTIVE ='active' and cd.deposit_reference_number = '"
						+ receipt_no + "'" + partyId;

				// sql="SELECT CASH_DEPOSIT_ID,DEPOSITOR_NAME,CMS_COLLATERAL_ID,DEPOSIT_AMOUNT,ISSUE_DATE,DEPOSIT_MATURITY_DATE,DEPOSIT_INTEREST_RATE FROM CMS_CASH_DEPOSIT WHERE deposit_reference_number = '"+receipt_no+"' AND STATUS='ACTIVE' AND ACTIVE='active'";
				if (null != receipt_no || !"".equals(receipt_no)) {
					List fdList = CollateralDAOFactory.getDAO()
							.selectCashFdList(sql, receipt_no);
					result.put("fdList", fdList);
				}
			} else {
				exceptionMap.put("rec", new ActionMessage(
						"error.string.not.zero", "Lien Number"));
			}
		}
	}
        
        result.put("fdWebServiceFlag", fdWebServiceFlag);
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
    
