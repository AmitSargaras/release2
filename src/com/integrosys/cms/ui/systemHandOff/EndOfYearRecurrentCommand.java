package com.integrosys.cms.ui.systemHandOff;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.IDocumentItemTrxValue;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;
import com.integrosys.cms.batch.eod.IEndOfDayBatchService;

public class EndOfYearRecurrentCommand extends AbstractCommand implements ICommonEventConstant {
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException {		
		
		
		//StringBuffer log = getEndOfDayBatchService().performEndOfYearActivities();
		try{
		
		OBTrxContext ctx= new OBTrxContext();
		ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
		
		
				ITemplate itemTemplate =  proxy.getCAMTemplate("REC", "REC", "IN");
				if(itemTemplate !=null){
				ICMSTrxValue icmsTrxValue= getTrxManager().findTrxByRefIDAndTrxType(String.valueOf(itemTemplate.getTemplateID()), ICMSConstant.INSTANCE_TEMPLATE_LIST);
				ITemplateTrxValue trxValue = proxy.getTemplateByTrxID(icmsTrxValue.getTransactionID());
				proxy.checkerApproveTemplate(ctx, trxValue);
							}
						 
		
	}
		catch (Exception exception) {
			//log.append("Fail");
			DefaultLogger.error(this,"End of Day BatchJob Exception (System Date : "+Calendar.getInstance().getTime()+") (Method : generateHolidayListForNextYear())" );
			exception.printStackTrace();
		}
		HashMap resultMap = new HashMap();
		//resultMap.put("log", log.toString());
		HashMap returnMap = new HashMap();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	final static String DOCUMENT_CODE_SEQ = "DOCUMENT_CODE_SEQ";
	private String genrateUserSegmentSeq() throws RemoteException {
		SequenceManager seqmgr = new SequenceManager();
		String seq = null;
		try {
			seq = seqmgr.getSeqNum(DOCUMENT_CODE_SEQ, true);
			DefaultLogger.debug(this, "Generated sequence " + seq);
			NumberFormat numberFormat = new DecimalFormat("0000000");
			String docCode = numberFormat.format(Long.parseLong(seq));
			docCode = "DOC" + docCode;	
			return docCode;
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "Exception occured while generating sequence   " + e.getMessage());
			throw new RemoteException("Exception in creating the user id", e);
		}
	}
	
	private SBCMSTrxManager getTrxManager() throws TrxOperationException {
		SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME,
				SBCMSTrxManagerHome.class.getName());

		if (null == mgr) {
			throw new TrxOperationException("failed to find cms trx manager remote interface using jndi name ["
					+ ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME + "]");
		}
		else {
			return mgr;
		}
	}
	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
			//{ "log", "java.lang.String", REQUEST_SCOPE }
			}
	);}
}
