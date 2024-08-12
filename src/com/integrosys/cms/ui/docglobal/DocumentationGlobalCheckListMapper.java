package com.integrosys.cms.ui.docglobal;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import org.hibernate.Query;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.chktemplate.trx.OBDocumentItemTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/05 10:33:54 $ Tag: $Name: $
 */

public class DocumentationGlobalCheckListMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	
	final static String DOCUMENT_CODE_SEQ = "DOCUMENT_CODE_SEQ";
	public DocumentationGlobalCheckListMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "docTrxObj", "com.integrosys.cms.app.chktemplate.trx.OBDocumentItemTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DocumentationGlobalForm aForm = (DocumentationGlobalForm) cForm;
		OBDocumentItemTrxValue docTrxObj = (OBDocumentItemTrxValue) map.get("docTrxObj");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		IDocumentItem temp;
		if (docTrxObj != null) {
			temp = docTrxObj.getDocumentItem();
		}
		else {
			temp = new OBDocumentItem();
		}

		if (!((aForm.getItemID() != null) && !aForm.getItemID().trim().equals(""))) {
			temp = new OBDocumentItem();
		}

		//temp.setLoanApplicationType(aForm.getAppendLoanList());

		temp.setExpiryDate(DateUtil.convertDate(locale, aForm.getExpDate()));
		temp.setItemDesc(aForm.getItemDesc());
		try {
			if( aForm.getItemCode() == null || aForm.getItemCode().equals("") ) 
				temp.setItemCode(genrateUserSegmentSeq());
			else
				temp.setItemCode(aForm.getItemCode());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		temp.setDocumentVersion(aForm.getDocVersion());
		temp.setIsPreApprove(ICMSConstant.TRUE_VALUE.equals(aForm.getIsPreApprove()));
		if(docTrxObj!=null){
			if(docTrxObj.getTransactionSubType()!=null&&!(docTrxObj.getTransactionSubType().trim().equals(""))){
				if(docTrxObj.getTransactionSubType().trim().equalsIgnoreCase(ICMSConstant.TRX_TYPE_CAM_GLOBAL_TEMPLATE)){
					temp.setItemType(ICMSConstant.DOC_TYPE_CAM);
				}
				
				if(docTrxObj.getTransactionSubType().trim().equalsIgnoreCase(ICMSConstant.TRX_TYPE_OTHER_GLOBAL_TEMPLATE)){
					temp.setItemType(ICMSConstant.DOC_TYPE_OTHER);
				}
			
			
				if(docTrxObj.getTransactionSubType().trim().equalsIgnoreCase(ICMSConstant.TRX_TYPE_FAC_GLOBAL_TEMPLATE)){
					temp.setItemType(ICMSConstant.DOC_TYPE_FACILITY);
				}
				if(docTrxObj.getTransactionSubType().trim().equalsIgnoreCase(ICMSConstant.TRX_TYPE_COL_GLOBAL_TEMPLATE)){
					temp.setItemType(ICMSConstant.DOC_TYPE_SECURITY);
				}
			}
		}
		
		temp.setTenureCount(Integer.parseInt((aForm.getTenureCount().trim()==null)?"0":aForm.getTenureCount().trim()));
		if(aForm.getTenureType()!=null || !(aForm.getTenureType().trim().equals(""))){
		temp.setTenureType(aForm.getTenureType());
		}
		

		
		if(!(aForm.getSkipImgTag().isEmpty()) || !(aForm.getSkipImgTag().trim().equals(""))){
			 temp.setSkipImgTag(aForm.getSkipImgTag());
			}else{
				temp.setSkipImgTag("DISABLE");
			}
			
 
		if(!(aForm.getStatementType().trim().equals(""))){
			temp.setStatementType(aForm.getStatementType());
			}
		temp.setIsRecurrent(aForm.getIsRecurrent());
		temp.setRating(aForm.getAppendRating());
		temp.setSegment(aForm.getAppendSegment());
		temp.setTotalSancAmt(aForm.getTotalSancAmt());
		temp.setClassification(aForm.getAppendClassification());
		temp.setGuarantor(aForm.getGuarantor());
		
		/*if(!(aForm.getIsRecurrent().trim().equals(""))){
			temp.setIsRecurrent(aForm.getIsRecurrent());
			}
		
		if(!(aForm.getAppendRating().trim().equals(""))){
			temp.setRating(aForm.getAppendRating());
			}else{
				if(!(aForm.getRating().trim().equals(""))){
					temp.setRating(aForm.getRating());
					}
			}
		
		if(!(aForm.getAppendSegment().trim().equals(""))){
			temp.setSegment(aForm.getAppendSegment());
			}else{
				if(!(aForm.getSegment().trim().equals(""))){
					temp.setSegment(aForm.getSegment());
					}
			}
		
		
		if(!(aForm.getTotalSancAmt().trim().equals(""))){
			temp.setTotalSancAmt(aForm.getTotalSancAmt());
			}
		
		
		if(!(aForm.getAppendClassification().trim().equals(""))){
			temp.setClassification(aForm.getAppendClassification());
			}else{
				if(!(aForm.getClassification().trim().equals(""))){
					temp.setClassification(aForm.getClassification());
					}
			}
		
		if(!(aForm.getGuarantor().trim().equals(""))){
			temp.setGuarantor(aForm.getGuarantor());
			}
		*/
		
		
		if(!(aForm.getDeprecated().trim().equals(""))){
			temp.setDeprecated(aForm.getDeprecated());
			}
		if(!(aForm.getStatus().trim().equals(""))){
			temp.setStatus(aForm.getStatus());
			}
		else
			temp.setStatus("ENABLE");
		//temp.setLoanApplicationType(aForm.getAppendLoanList());
		OBDocumentItem tempObj = (OBDocumentItem)temp;
//		tempObj.loadLoanAppTypes();
		temp = tempObj;
		

		temp.setOldItemCode(aForm.getOldItemCode());
		
		return temp;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DocumentationGlobalForm aForm = (DocumentationGlobalForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (obj != null) {
			IDocumentItem tempOb = (IDocumentItem) obj;
			aForm.setItemID(String.valueOf(tempOb.getItemID()));
			aForm.setItemCode(tempOb.getItemCode());
			
			aForm.setOldItemCode(tempOb.getOldItemCode());
			
			aForm.setItemDesc(tempOb.getItemDesc());
			if(tempOb.getTenureCount()!=0){
				aForm.setTenureCount(String.valueOf(tempOb.getTenureCount()));	
			}
			if( tempOb.getTenureType() != null && ! tempOb.getTenureType().equals("")  ){
				aForm.setTenureType(tempOb.getTenureType());	
			}
			if( tempOb.getSkipImgTag() != null ){
				aForm.setSkipImgTag(tempOb.getSkipImgTag());	
			}
			if(!(tempOb.getDeprecated().trim().equals(""))){
				aForm.setDeprecated(tempOb.getDeprecated());	
			}
			if( tempOb.getStatementType() != null && ! tempOb.getStatementType().equals("")  ){
				aForm.setStatementType(tempOb.getStatementType());	
			}
			
			
			
			if( tempOb.getIsRecurrent() != null && ! tempOb.getIsRecurrent().equals("")  ){
				aForm.setIsRecurrent(tempOb.getIsRecurrent());	
			}
			if( tempOb.getRating() != null && ! tempOb.getRating().equals("")  ){
				aForm.setRating(tempOb.getRating());	
			}
			if( tempOb.getSegment()!= null && ! tempOb.getSegment().equals("")  ){
				aForm.setSegment(tempOb.getSegment());	
			}
			if( tempOb.getTotalSancAmt() != null && ! tempOb.getTotalSancAmt().equals("")  ){
				aForm.setTotalSancAmt(tempOb.getTotalSancAmt());	
			}
			if( tempOb.getClassification()!= null && ! tempOb.getClassification().equals("")  ){
				aForm.setClassification(tempOb.getClassification());	
			}
			if( tempOb.getGuarantor() != null && ! tempOb.getGuarantor().equals("")  ){
				aForm.setGuarantor(tempOb.getGuarantor());	
			}
			if(!(tempOb.getStatus().trim().equals(""))){
				aForm.setStatus(tempOb.getStatus());	
			}
			
			aForm.setExpDate(DateUtil.formatDate(locale, tempOb.getExpiryDate()));
			aForm.setDocVersion(tempOb.getDocumentVersion());
			aForm.setIsPreApprove(tempOb.getIsPreApprove() ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
			//aForm.setLoanApplicationType(tempOb.getLoanApplicationType());
			
			String[] loanList = new String[10];
			aForm.setLoanApplicationList(loanList);
		}

		return aForm;
	}

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
}
