/**
 * Copyright AurionPro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2011-05-03 15:13:16 +0800 jtan Exp $
 */


package com.integrosys.cms.ui.directorMaster;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.asst.validator.ASSTValidator;


/**
 * Purpose : This command validate the  Director Master 
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public class DirectorMasterValidator {
	

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		
		MaintainDirectorMasterForm form = (MaintainDirectorMasterForm) commonform;
		
		if(form.getDinNo()==null || form.getDinNo().trim().equals("") ){
			errors.add("directorMasterdinNoError", new ActionMessage("error.string.mandatory"));
		
		}else{
			if(form.getDinNo().trim().length() > 9 )
				errors.add("directorMasterdinNoError", new ActionMessage("error.length.string","Din No","9"));
			else{
				boolean dinNoFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getDinNo());
				if( dinNoFlag == true)
					errors.add("spaceError", new ActionMessage("error.string.noSpace","Director Number"));
				}
		}
		
		if(form.getName()==null || form.getName().trim().equals("")){
			errors.add("directorMasterNameError", new ActionMessage("error.string.mandatory"));
		}else{
			if(form.getName().trim().length() > 50 )
				errors.add("directorMasterNameError", new ActionMessage("error.string.length","Name","50"));

			else{
				boolean nameFlag = ASSTValidator.isValidDirectorName(form.getName());
				if( nameFlag == true)
					errors.add("directorMasterNameError", new ActionMessage("error.string.invalidCharacter"));
				}
		}
		
		return errors;
    }

}
    
	
