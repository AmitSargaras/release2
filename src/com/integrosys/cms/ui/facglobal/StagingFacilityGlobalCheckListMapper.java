package com.integrosys.cms.ui.facglobal;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.ui.docglobal.StagingDocumentationGlobalCheckListMapper;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/05 12:11:06 $ Tag: $Name: $
 */

public class StagingFacilityGlobalCheckListMapper extends StagingDocumentationGlobalCheckListMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		FacilityGlobalForm aForm = (FacilityGlobalForm) cForm;
		IDocumentItem temp = (IDocumentItem) super.mapFormToOB(cForm, map);
		//temp.setLoanApplicationType(aForm.getLoanApplicationType());
		
		 //Constructing the loan applications types into one single long string
	//	String appendApp = "";
		
	//	for (int i = 0; i < aForm.getLoanApplicationList().length;i++)
		//{
		//	appendApp = appendApp.concat(aForm.getLoanApplicationList()[i]).concat("-");
			
	//	}
		
	//	System.out.println("Checking the applications selected in string : " + appendApp);
		
		
	//	temp.setLoanApplicationType(appendApp);

		return temp;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		FacilityGlobalForm aForm = (FacilityGlobalForm) super.mapOBToForm(cForm, obj, map);
//		if (obj != null) {
//			IDocumentItem tempOb = (IDocumentItem) obj;
//			//aForm.setLoanApplicationType(tempOb.getLoanApplicationType());
//		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
