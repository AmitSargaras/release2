package com.integrosys.cms.ui.docglobal;

import java.util.HashMap;
import java.util.Locale;

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
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/05 12:11:06 $ Tag: $Name: $
 */

public class StagingDocumentationGlobalCheckListMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public StagingDocumentationGlobalCheckListMapper() {
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
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		DocumentationGlobalForm aForm = (DocumentationGlobalForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		OBDocumentItemTrxValue docTrxObj = (OBDocumentItemTrxValue) map.get("docTrxObj");
		IDocumentItem temp;
		temp = docTrxObj.getStagingDocumentItem();
		if (temp == null) {
			throw new MapperException("The Staging ob is null in mapper");
		}

		temp.setExpiryDate(DateUtil.convertDate(locale, aForm.getExpDate()));
		temp.setItemCode(aForm.getItemCode());
		temp.setItemDesc(aForm.getItemDesc());
		temp.setItemID(Long.parseLong(aForm.getItemID()));
		temp.setDocumentVersion(aForm.getDocVersion());
		temp.setStatementType(aForm.getStatementType());
		temp.setTenureCount(Integer.parseInt(aForm.getTenureCount()));
		temp.setSkipImgTag(aForm.getSkipImgTag());
		
		
		if(aForm.getTenureType()!=null || !(aForm.getTenureType().trim().equals(""))){
			temp.setTenureType(aForm.getTenureType());
			}
			if(!(aForm.getDeprecated().trim().equals(""))){
				temp.setDeprecated(aForm.getDeprecated());
				}
			if(!(aForm.getStatus().trim().equals(""))){
				temp.setStatus(aForm.getStatus());
				}
			/*if(aForm.getSkipImgTag()!=null || !(aForm.getSkipImgTag().trim().equals(""))){
				temp.setSkipImgTag(aForm.getSkipImgTag());
				}*/
		//temp.setLoanApplicationType(aForm.getAppendLoanList());
		OBDocumentItem tempObj = (OBDocumentItem)temp;
//		tempObj.loadLoanAppTypes();
		temp = tempObj;
		
		temp.setIsPreApprove(ICMSConstant.TRUE_VALUE.equals(aForm.getIsPreApprove()));
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
		DefaultLogger.debug(this, "inside mapOb to form ");
		DocumentationGlobalForm aForm = (DocumentationGlobalForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (obj != null) {
			IDocumentItem tempOb = (IDocumentItem) obj;
			aForm.setItemID(String.valueOf(tempOb.getItemID()));
			aForm.setItemCode(tempOb.getItemCode());
			aForm.setItemDesc(tempOb.getItemDesc());
			aForm.setExpDate(DateUtil.formatDate(locale, tempOb.getExpiryDate()));
			aForm.setDocVersion(tempOb.getDocumentVersion());
			if(tempOb.getTenureCount()!=0){
				aForm.setTenureCount(String.valueOf(tempOb.getTenureCount()));	
			}
			if(!(tempOb.getTenureType().trim().equals(""))){
				aForm.setTenureType(tempOb.getTenureType());	
			}
			if(!(tempOb.getStatus().trim().equals(""))){
				aForm.setStatus(tempOb.getStatus());	
			}
			if(!(tempOb.getSkipImgTag().trim().equals(""))){
				aForm.setSkipImgTag(tempOb.getSkipImgTag());	
			}
			if(!(tempOb.getDeprecated().trim().equals(""))){
				aForm.setDeprecated(tempOb.getDeprecated());	
			}
			
			aForm.setIsPreApprove(tempOb.getIsPreApprove() ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
		}
		return aForm;
	}
}
