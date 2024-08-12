package com.integrosys.cms.ui.limitsOfAuthorityMaster;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.OBLimitsOfAuthorityMaster;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class LimitsOfAuthorityMasterMapper extends AbstractCommonMapper {
	
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		LimitsOfAuthorityMasterForm form = (LimitsOfAuthorityMasterForm) cForm;
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		ILimitsOfAuthorityMaster obItem = new OBLimitsOfAuthorityMaster();

		try {
			if(StringUtils.isNotBlank(form.getEmployeeGrade()))
				obItem.setEmployeeGrade(form.getEmployeeGrade());
			
			if(StringUtils.isNotBlank(form.getRankingOfSequence()))
				obItem.setRankingOfSequence(Integer.valueOf(form.getRankingOfSequence()));
			
			if(StringUtils.isNotBlank(form.getSegment()))
				obItem.setSegment(form.getSegment());
			
			if(StringUtils.isNotBlank(form.getLimitReleaseAmt()))
				obItem.setLimitReleaseAmt(UIUtil.mapStringToBigDecimal(form.getLimitReleaseAmt()));
			
			if(StringUtils.isNotBlank(form.getTotalSanctionedLimit()))
				obItem.setTotalSanctionedLimit(UIUtil.mapStringToBigDecimal(form.getTotalSanctionedLimit()));
			
			if(StringUtils.isNotBlank(form.getPropertyValuation()))
				obItem.setPropertyValuation(UIUtil.mapStringToBigDecimal(form.getPropertyValuation()));
			
			if(StringUtils.isNotBlank(form.getFdAmount()))
				obItem.setFdAmount(UIUtil.mapStringToBigDecimal(form.getFdAmount()));
			
			if(StringUtils.isNotBlank(form.getDrawingPower()))
				obItem.setDrawingPower(UIUtil.mapStringToBigDecimal(form.getDrawingPower()));
			
			if(StringUtils.isNotBlank(form.getSblcSecurityOmv()))
				obItem.setSblcSecurityOmv(UIUtil.mapStringToBigDecimal(form.getSblcSecurityOmv()));
			
			if(StringUtils.isNotBlank(form.getFacilityCamCovenant()))
				obItem.setFacilityCamCovenant(form.getFacilityCamCovenant());

			if(StringUtils.isNotBlank(form.getDeprecated()))
				obItem.setDeprecated(form.getDeprecated());
			else
				obItem.setDeprecated("N");

			if(StringUtils.isNotBlank(form.getId()))
				obItem.setId(Long.parseLong(form.getId()));

			if(StringUtils.isNotBlank(form.getCreatedBy()))
				obItem.setCreatedBy(form.getCreatedBy());
			else
				obItem.setCreatedBy(user.getLoginID());

			if(StringUtils.isNotBlank(form.getLastUpdateBy()))
				obItem.setLastUpdateBy(form.getLastUpdateBy());
			else
				obItem.setLastUpdateBy(user.getLoginID());

			
			
			try {
				
				if(StringUtils.isNotBlank(form.getLastUpdateDate()))
					obItem.setLastUpdateDate(df.parse(form.getLastUpdateDate()));
				else
					obItem.setLastUpdateDate(new Date());
				
				if(StringUtils.isNotBlank(form.getCreationDate()))
					obItem.setCreationDate(df.parse(form.getCreationDate()));
				else
					obItem.setCreationDate(new Date());
			}
			catch (Exception e) {
			}

			

			if(StringUtils.isNotBlank(form.getVersionTime())) 
				obItem.setVersionTime(Long.parseLong(form.getVersionTime()));
			else
				obItem.setVersionTime(0l);
			
			if(StringUtils.isNotBlank(form.getStatus()))
				obItem.setStatus(form.getStatus());
			else
				obItem.setStatus("ACTIVE");

			return obItem;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MapperException("Failed to map form to ob of LimitsOfAuthorityMaster ", ex);
		}
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object cObj, HashMap map) throws MapperException {
		LimitsOfAuthorityMasterForm form = (LimitsOfAuthorityMasterForm) cForm;
		ILimitsOfAuthorityMaster item = (ILimitsOfAuthorityMaster) cObj;
		
		if(StringUtils.isNotBlank(item.getEmployeeGrade()))
			form.setEmployeeGrade(String.valueOf(item.getEmployeeGrade()));
		
		if(item.getRankingOfSequence()>0)
			form.setRankingOfSequence(String.valueOf(item.getRankingOfSequence()));
		
		if(StringUtils.isNotBlank(item.getSegment()))
			form.setSegment(String.valueOf(item.getSegment()));
		
		if(item.getLimitReleaseAmt()!= null)
			form.setLimitReleaseAmt(UIUtil.formatWithCommaAndDecimalNew(item.getLimitReleaseAmt().toPlainString()));
		
		if(item.getTotalSanctionedLimit()!= null)
			form.setTotalSanctionedLimit(UIUtil.formatWithCommaAndDecimalNew(item.getTotalSanctionedLimit().toPlainString()));
		
		if(item.getPropertyValuation()!= null)
			form.setPropertyValuation(UIUtil.formatWithCommaAndDecimalNew(item.getPropertyValuation().toPlainString()));
		
		if(item.getFdAmount()!= null)
			form.setFdAmount(UIUtil.formatWithCommaAndDecimalNew(item.getFdAmount().toPlainString()));
		
		if(item.getDrawingPower()!= null)
			form.setDrawingPower(UIUtil.formatWithCommaAndDecimalNew(item.getDrawingPower().toPlainString()));
		
		if(item.getSblcSecurityOmv()!= null)
			form.setSblcSecurityOmv(UIUtil.formatWithCommaAndDecimalNew(item.getSblcSecurityOmv().toPlainString()));

		if(StringUtils.isNotBlank(item.getFacilityCamCovenant()))
			form.setFacilityCamCovenant(item.getFacilityCamCovenant());
		
		form.setCreatedBy(item.getCreatedBy());
		form.setLastUpdateBy(item.getLastUpdateBy());
		
		try {
			form.setLastUpdateDate(df.format(item.getLastUpdateDate()));
			form.setCreationDate(df.format(item.getCreationDate()));
		}catch (Exception e) {
		}
		
		
		form.setVersionTime(Long.toString(item.getVersionTime()));
		form.setDeprecated(item.getDeprecated());
		form.setId(Long.toString(item.getId()));
		form.setStatus(item.getStatus());

		return cForm;
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER, ICommonUser.class.getName(), GLOBAL_SCOPE }
			});
	}

}