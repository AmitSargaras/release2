package com.integrosys.cms.ui.secglobal;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.docglobal.DocumentationGlobalCheckListMapper;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/05 10:33:54 $ Tag: $Name: $
 */

public class SecurityGlobalCheckListMapper extends DocumentationGlobalCheckListMapper {


    /**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {

        SecurityGlobalForm aForm = (SecurityGlobalForm) cForm;
		IDocumentItem temp = (IDocumentItem)super.mapFormToOB(cForm, map);
        if (!((aForm.getItemID() != null) && !aForm.getItemID().trim().equals(""))) {
            DefaultLogger.debug(this, "Going for Insert");
            if(temp.getItemType()==null || temp.getItemType().trim().equals("")){
            	 temp.setItemType(ICMSConstant.DOC_TYPE_SECURITY); 	
            }
           
            //temp.setLoanApplicationType(aForm.getLoanApplicationType());
        }else{
        	//temp.setLoanApplicationType(aForm.getLoanApplicationType());
        }
        
        if(StringUtils.isNotBlank(aForm.getIsApplicableForCersaiInd())) {
        	temp.setIsApplicableForCersaiInd(aForm.getIsApplicableForCersaiInd());
        }
        
      //Constructing the loan applications types into one single long string
//		String appendApp = "";
//		
//		for (int i = 0; i < aForm.getLoanApplicationList().length;i++)
//		{
//			appendApp = appendApp.concat(aForm.getLoanApplicationList()[i]).concat("-");
//			
//		}
//		
//		System.out.println("Checking the applications selected in string : " + appendApp);
//		
//		
//		temp.setLoanApplicationType(appendApp);
       
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
		SecurityGlobalForm aForm = (SecurityGlobalForm) super.mapOBToForm(cForm, obj, map);
		if (obj != null) {
			IDocumentItem tempOb = (IDocumentItem) obj;
//			aForm.setLoanApplicationType(tempOb.getLoanApplicationType());
			if(StringUtils.isNotBlank(tempOb.getIsApplicableForCersaiInd())) {
				aForm.setIsApplicableForCersaiInd(tempOb.getIsApplicableForCersaiInd());
	        }
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
