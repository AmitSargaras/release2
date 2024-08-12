package com.integrosys.cms.ui.profile;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.component.login.app.LoginConstant;

public class PasswordChangeMapper extends AbstractCommonMapper {

	/**
	 * Default Construtor
	 */
	public PasswordChangeMapper() {
		DefaultLogger.debug(this, "Inside constructor");
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the mapFormToOB method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request or service. The
	 * scope cannot be of form type
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE } });
	}

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside PasswordChangeMapper mapFormToOB");
		PasswordChangeForm aForm = (PasswordChangeForm) cForm;
		OBPasswordChange ob = new OBPasswordChange();
		ob.setOldPasswd(aForm.getOldPasswd());
		ob.setNewPasswd(aForm.getNewPasswd());
		ob.setConfirmPasswd(aForm.getConfirmPasswd());
		ob.setLoginId(aForm.getLoginId());// revalidate in application
		ob.setRole(aForm.getRole());// revalidate in application
		ob.setPinType(LoginConstant.SME_PIN_TYPE1);
		ob.setRealm(aForm.getRealm());// revalidate in application
		return ob;
	}

	private boolean isNull(Object aValue) {
		return (aValue == null) ? true : false;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @throws MapperException on errors
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap aMap) throws MapperException {
		return cForm;
	}

}
