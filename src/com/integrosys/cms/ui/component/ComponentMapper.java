package com.integrosys.cms.ui.component;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.app.component.bus.OBComponent;

public class ComponentMapper extends AbstractCommonMapper {

	
	public Object mapFormToOB(CommonForm cForm, HashMap inputs)throws MapperException {
		
		MaintainComponentForm form = (MaintainComponentForm) cForm;

		IComponent obItem = null;
		try {
			
				obItem = new OBComponent();		
				
				obItem.setDeprecated(form.getDeprecated());
				
				if(form.getId()!=null && (!form.getId().equals("")))
	            {
					obItem.setId(Long.parseLong(form.getId()));
	            }
				if(form.getVersionTime()!=null && (!form.getVersionTime().equals("")))
	            {
					obItem.setVersionTime(Long.parseLong(form.getVersionTime()));
	            }
				
				
				obItem.setStatus(form.getStatus());
				obItem.setComponentType(form.getComponentType());
				obItem.setComponentCode(form.getComponentCode());
				obItem.setComponentName(form.getComponentName());
				obItem.setHasInsurance(form.getHasInsurance());
				//Start:-------->Abhishek Naik
				obItem.setDebtors(form.getDebtors());
				obItem.setAge(form.getAge());
				// End:-------->Abhishek Naik 
				//Start Santosh
				obItem.setComponentCategory(form.getComponentCategory());
				obItem.setApplicableForDp(form.getApplicableForDp());
				//End Santosh
			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of Component Branch item", ex);
		}
	
		
	}

	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)throws MapperException {
		MaintainComponentForm form = (MaintainComponentForm) cForm;
		IComponent item = (IComponent) obj;

		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setDeprecated(item.getDeprecated());
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());
		form.setComponentType(item.getComponentType());
		form.setComponentName(item.getComponentName());
		form.setComponentCode(item.getComponentCode());
		form.setHasInsurance(item.getHasInsurance());
		//Start:-------->Abhishek Naik
		form.setAge(item.getAge());
		form.setDebtors(item.getDebtors());
		// End:-------->Abhishek Naik 
		//Start Santosh
		form.setComponentCategory(item.getComponentCategory());
		form.setApplicableForDp(item.getApplicableForDp());
		//End Santosh
		return form;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "componentObj", "com.integrosys.cms.app.component.bus.OBComponent", SERVICE_SCOPE }, });
	}
}
