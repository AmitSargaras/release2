package com.integrosys.cms.ui.udf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.app.udf.bus.UDFConstants;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class UdfMapper extends AbstractCommonMapper {
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	public Object mapFormToOB(CommonForm form, HashMap map) throws MapperException {
		UdfForm udfForm = (UdfForm) form;
		IUdf obUdf=null;
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		try {
		obUdf = new OBUdf();
		
		
		long fieldId = Long.parseLong(udfForm.getFieldTypeId());
		obUdf.setFieldTypeId(fieldId);
		obUdf.setModuleId(Long.parseLong(udfForm.getModuleId()));
		obUdf.setOptions(udfForm.getOptions());
		obUdf.setFieldName(udfForm.getFieldName());
		if(udfForm.getMandatory()!=null)
		obUdf.setMandatory(udfForm.getMandatory());
		
		if(udfForm.getNumericLength()!=null)
			obUdf.setNumericLength(udfForm.getNumericLength());
		// Set Field Name
		if (Long.parseLong(udfForm.getFieldTypeId()) == UDFConstants.FIELDTYPEID_TEXT) {
			obUdf.setFieldType(UDFConstants.FIELDTYPEDESC_TEXT);
			obUdf.setOptions("");
		}else if (Long.parseLong(udfForm.getFieldTypeId()) == UDFConstants.FIELDTYPEID_DATE) {
			obUdf.setFieldType(UDFConstants.FIELDTYPEDESC_DATE);
			obUdf.setOptions("");
		}else if (Long.parseLong(udfForm.getFieldTypeId()) == UDFConstants.FIELDTYPEID_NUMERIC_TEXT) {
			obUdf.setFieldType(UDFConstants.FIELDTYPEDESC_NUMERIC_TEXT);
			obUdf.setOptions("");
		}
		else if (Long.parseLong(udfForm.getFieldTypeId()) == UDFConstants.FIELDTYPEID_RADIOBUTTON) {
			obUdf.setFieldType(UDFConstants.FIELDTYPEDESC_RADIOBUTTON);
		}
		else if (Long.parseLong(udfForm.getFieldTypeId()) == UDFConstants.FIELDTYPEID_SELECT) {
			obUdf.setFieldType(UDFConstants.FIELDTYPEDESC_SELECT);
		}
		else if (Long.parseLong(udfForm.getFieldTypeId()) == UDFConstants.FIELDTYPEID_TEXTAREA) {
			obUdf.setFieldType(UDFConstants.FIELDTYPEDESC_TEXTAREA);
			obUdf.setOptions("");
		}
		else if (Long.parseLong(udfForm.getFieldTypeId()) == UDFConstants.FIELDTYPEID_CHECKBOX) {
			obUdf.setFieldType(UDFConstants.FIELDTYPEDESC_CHECKBOX);
		}
		// Set Module Name
		if (Long.parseLong(udfForm.getModuleId()) == UDFConstants.MODULEID_CAM) {
			obUdf.setModuleName(UDFConstants.MODULEDESC_CAM);
		}
		else if (Long.parseLong(udfForm.getModuleId()) == UDFConstants.MODULEID_PARTY) {
			obUdf.setModuleName(UDFConstants.MODULEDESC_PARTY);
		}else if (Long.parseLong(udfForm.getModuleId()) == UDFConstants.MODULEID_LIMIT) {
			obUdf.setModuleName(UDFConstants.MODULEDESC_LIMIT);
		}
		else if (Long.parseLong(udfForm.getModuleId()) == UDFConstants.MODULEID_LIMIT_2) {
			obUdf.setModuleName(UDFConstants.MODULEDESC_LIMIT_2);
		}
	//	obUdf.setStatus(UDFConstants.STATUS_CREATED);
		
		if( udfForm.getStatus() != null && ! udfForm.getStatus().equals(""))
			obUdf.setStatus(udfForm.getStatus());
		else
			obUdf.setStatus("ACTIVE");
		
		if(udfForm.getOperationName()!=null && (!udfForm.getOperationName().trim().equals(""))){
			obUdf.setOperationName(udfForm.getOperationName());
		}
		obUdf.setSequence(Integer.parseInt(udfForm.getSequence()));
		
		if(udfForm.getCreateBy()!=null && udfForm.getCreateBy().trim()!=""){
			obUdf.setCreateBy(udfForm.getCreateBy());
		}else{
			obUdf.setCreateBy(user.getLoginID());
		}	
		
		if(udfForm.getLastUpdateBy()!=null && udfForm.getLastUpdateBy().trim()!=""){
			obUdf.setLastUpdateBy(udfForm.getLastUpdateBy());
		}else{
			obUdf.setLastUpdateBy(user.getLoginID());
		}
		
		
		if(udfForm.getLastUpdateDate()!=null && (!udfForm.getLastUpdateDate().equals("")))
        {
		obUdf.setLastUpdateDate(df.parse(udfForm.getLastUpdateDate()));
        }else{
        	obUdf.setLastUpdateDate(new Date());
        }
		if(udfForm.getCreationDate()!=null && (!udfForm.getCreationDate().equals("")))
        {
		obUdf.setCreationDate(df.parse(udfForm.getCreationDate()));
        }else{
        	obUdf.setCreationDate(new Date());
        }
		
		obUdf.setDeprecated(udfForm.getDeprecated());
		
			if(udfForm.getId()!=null && (!udfForm.getId().trim().equals("")))
            {
				obUdf.setId(Long.parseLong(udfForm.getId()));
            }
			
		return obUdf;
		}catch(Exception e) {
			throw new MapperException("failed to map udfForm to ob of UDF ", e);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap arg2)
			throws MapperException {
		// TODO Auto-generated method stub
//		return null;

		
		UdfForm form = (UdfForm) cForm;
		IUdf item = (IUdf) obj;

		form.setModuleId(Long.toString(item.getModuleId()));
		form.setModuleName(item.getModuleName());
		form.setFieldName(item.getFieldName());
		form.setFieldTypeId(Long.toString(item.getFieldTypeId()));
		form.setFieldType(item.getFieldType());
		form.setOptions(item.getOptions());
		form.setSequence(String.valueOf(item.getSequence()));
		form.setMandatory(item.getMandatory());
		form.setNumericLength(item.getNumericLength());
		form.setCreateBy(item.getCreateBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
		form.setCreationDate(df.format(item.getCreationDate()));
		//form.setVersion(Long.toString(item.getVersionTime()));
		//form.setDeprecated(item.getDeprecated());
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());
		form.setDeprecated(item.getDeprecated());
		
		return form;
	
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "udfObj", "com.integrosys.cms.app.udf.bus.OBUdf", SERVICE_SCOPE }, });
	}
}
