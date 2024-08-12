package com.integrosys.cms.ui.userrole;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class BizStructureHelper {

	public static boolean requireCMSSegment() {
		return PropertyManager.getBoolean(ICMSConstant.REQUIRE_CMS_SEGMENT, false);
	}

	public static boolean requireOrgCodeFilter() {
		return PropertyManager.getBoolean(ICMSConstant.REQUIRE_ORG_CODE_FILTER, false);
	}
	
	public static boolean requireBizSegment() {
		return PropertyManager.getBoolean(ICMSConstant.REQUIRE_BIZ_SEGMENT, false);
	}
	
	public static boolean allowMakerCheckerSameUser() {
		return PropertyManager.getBoolean(ICMSConstant.MAKER_CHECKER_SAME_USER, false);
	}
}