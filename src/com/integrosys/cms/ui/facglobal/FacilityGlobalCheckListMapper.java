package com.integrosys.cms.ui.facglobal;

import com.integrosys.cms.ui.docglobal.DocumentationGlobalCheckListMapper;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/05 10:33:54 $ Tag: $Name: $
 */

public class FacilityGlobalCheckListMapper extends DocumentationGlobalCheckListMapper {


    /**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {

        FacilityGlobalForm aForm = (FacilityGlobalForm) cForm;
		IDocumentItem temp = (IDocumentItem)super.mapFormToOB(cForm, map);
        if (!((aForm.getItemID() != null) && !aForm.getItemID().trim().equals(""))) {
            DefaultLogger.debug(this, "Going for Insert");
            temp.setItemType(ICMSConstant.DOC_TYPE_FACILITY);
            //temp.setLoanApplicationType(aForm.getLoanApplicationType());
        }else{
        	//temp.setLoanApplicationType(aForm.getLoanApplicationType());
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
		FacilityGlobalForm aForm = (FacilityGlobalForm) super.mapOBToForm(cForm, obj, map);
		if (obj != null) {
			IDocumentItem tempOb = (IDocumentItem) obj;
//			aForm.setLoanApplicationType(tempOb.getLoanApplicationType());
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
