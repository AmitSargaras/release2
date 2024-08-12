package com.integrosys.cms.ui.manualinput.partygroup;

/**
 * Purpose : Used for Validating party group
 *
 * @author $Author: Bharat waghela $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-13 15:13:16 +0800 
 * Tag : $Name$
 */

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;

public class PartyGroupValidator   {
	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		String errorCode = null;

		MIPartyGroupForm form = (MIPartyGroupForm) commonform;
			
		if(form.getPartyName()==null || "".equals(form.getPartyName().trim()))
		{
			errors.add("partyNameError", new ActionMessage("error.string.mandatory"));
			DefaultLogger.debug(PartyGroupValidator.class, "partyNameError");
		}
		else{
			if(form.getPartyName().length()>100)
			{
				errors.add("partyNameError", new ActionMessage("error.partyname.length.exceeded"));
				DefaultLogger.debug(PartyGroupValidator.class, "error.partyname.length.exceeded");
			}
			else{
				boolean nameFlag = ASSTValidator.isValidGenericASST(form.getPartyName());
				if( nameFlag == true)
					errors.add("partyNameError", new ActionMessage("error.string.invalidCharacter"));
				}
		}
		
		if (form.getGroupExpLimit() != null && !("".equals(form.getGroupExpLimit().trim())))
		{
		 if (!(errorCode = Validator.checkNumber(form.getGroupExpLimit(),false,0,
				99999999999999999999.99,3,locale)).equals(Validator.ERROR_NONE)) {
				errors.add("grpExpLmtError",
				new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
				errorCode), "0", "99999999999999999999.99"));
			}
		}

		return errors;
		
    }

}
