package com.integrosys.cms.ui.collateral;

import static com.integrosys.cms.app.common.util.MapperUtil.bigDecimalToString;
import static com.integrosys.cms.app.common.util.MapperUtil.doubleToString;
import static com.integrosys.cms.app.common.util.MapperUtil.stringToBigDecimal;
import static com.integrosys.cms.app.common.util.MapperUtil.stringToDouble;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.integrosys.cms.app.collateral.bus.ISecurityCoverage;
import com.integrosys.cms.app.collateral.bus.OBSecurityCoverage;

public class CollateralMapperHelper {

	public static SecurityCoverageForm mapSecurityCoverageOBToForm(List<ISecurityCoverage> obj){
		SecurityCoverageForm form = new SecurityCoverageForm();
		if(obj != null && !CollectionUtils.isEmpty(obj)) {
			ISecurityCoverage securityCoverage = obj.get(0);
			form.setCoverageAmount(bigDecimalToString(securityCoverage.getCoverageAmount()));
			form.setAdHocCoverageAmount(bigDecimalToString(securityCoverage.getAdHocCoverageAmount()));
			form.setCoveragePercentage(doubleToString(securityCoverage.getCoveragePercentage()));
		}
		return form;
	}
	
	public static List<ISecurityCoverage> mapSecurityCoverageFormToOB(CollateralForm form){
		ISecurityCoverage obj = new OBSecurityCoverage();
		if(form != null) {
			obj.setCoverageAmount(stringToBigDecimal(form.getCoverageAmount()));
			obj.setAdHocCoverageAmount(stringToBigDecimal(form.getAdHocCoverageAmount()));
			obj.setCoveragePercentage(stringToDouble(form.getCoveragePercentage()));
		}
		List<ISecurityCoverage> securityCoverageList = new ArrayList<ISecurityCoverage>();
		securityCoverageList.add(obj);

		return securityCoverageList;
	}
}
