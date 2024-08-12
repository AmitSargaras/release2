package com.integrosys.cms.ui.rbicategory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.OBIndustryCodeCategory;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *@author $Govind.Sahu$
 *Mapper for Rbi Category
 */

public class RbiCategoryMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "Entering method mapFormToOB");
		RbiCategoryForm form = (RbiCategoryForm) cForm;

		IRbiCategory obItem = null;
		try {
			
				obItem = new OBRbiCategory();
				obItem.setIndustryNameId(form.getIndustryNameId());
				obItem.setDeprecated(form.getDeprecated());

				if(form.getId()!=null && (!form.getId().equals("")))
	            {
					obItem.setId(Long.parseLong(form.getId()));
	            }
				obItem.setCreateBy(form.getCreateBy());
				obItem.setLastUpdateBy(form.getLastUpdateBy());
				if(form.getLastUpdateDate()!=null && (!form.getLastUpdateDate().equals("")))
	            {
				obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
	            }else{
	            	obItem.setLastUpdateDate(new Date());
	            }
				if(form.getCreationDate()!=null && (!form.getCreationDate().equals("")))
	            {
				obItem.setCreationDate(df.parse(form.getCreationDate()));
	            }else{
	            	obItem.setCreationDate(new Date());
	            }

				obItem.setStatus(form.getStatus());
				
				Set oBRbiCodeCatSet = new HashSet();
				OBIndustryCodeCategory oBRbiCodeCat = null;
				String[] selectedRbiCodeCatIdArr = form.getAppendRbiCodeCategoryList().split("-");
				 for (int i=0; i<selectedRbiCodeCatIdArr.length; i++)
			     {	 oBRbiCodeCat = new OBIndustryCodeCategory();	
		
					 oBRbiCodeCat.setRbiCodeCategoryId(selectedRbiCodeCatIdArr[i]);
					 oBRbiCodeCatSet.add(oBRbiCodeCat);
			     }
				 
				 obItem.setStageIndustryNameSet(oBRbiCodeCatSet);
				 

			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of rbi Category item", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		RbiCategoryForm form = (RbiCategoryForm) cForm;
		IRbiCategory item = (IRbiCategory) obj;
		
		form.setIndustryNameId(item.getIndustryNameId());
		
		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setDeprecated(item.getDeprecated());
		form.setStatus(item.getStatus());
		form.setId(Long.toString(item.getId()));


		return form;
	}

	/**
	 * declares the key-value pair upfront for objects that needs to be accessed
	 * in scope
	 * @return 2D-array key value pair
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "rbiCategoryObj", "com.integrosys.cms.app.rbicategory.bus", SERVICE_SCOPE }, });
	}
}
