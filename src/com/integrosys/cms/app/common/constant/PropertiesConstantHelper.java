package com.integrosys.cms.app.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class PropertiesConstantHelper {

	/**
	 * List of the system that need to filter out for STP
	 */
	protected static List FILTER_STP_SYSTEM_LIST = getPropertyList("filter.stp.systems");

	/**
	 * List of application type that need to filter out for STP facility loading
	 */
	protected static List FILTER_STP_FAC_LOAD_APP_TYPE = getPropertyList("filter.stp.facility.loading.apptype");

	/**
	 * List of system that need to filter out for documentation module
	 */
	protected static List FILTER_DOCUMENTATION_SYSTEM = getPropertyList("filter.documentation.systems");

	/**
	 * List of application type that need to filter out for documentation module
	 */
	protected static List FILTER_DOCUMENTATION_APP_TYPE = getPropertyList("filter.documentation.apptype");

	/**
	 * List of application type that need to filter out for STP collateral
	 * loading
	 */
	protected static List FILTER_STP_COL_LOAD_APP_TYPE = getPropertyList("filter.stp.collateral.loading.apptype");

	/**
	 * List of systems that need to filter out for STP collateral loading
	 */
	protected static List FILTER_STP_COL_LOAD_SYSTEM = getPropertyList("filter.stp.collateral.loading.systems");

	/**
	 * Web URL to retrieve Letter of Instruction from RLOS
	 */
	public static String RLOS_DOCGEN_PATH = PropertyManager.getValue("rlos.letter.instruction.web.url");

	/**
	 * Indicate whether filter application module access by application type
	 */
	public static boolean FILTER_MODULE_ACCESS_BY_AA_TYPE = PropertyManager.getBoolean(
			"modules.filter.by.application.type", false);
    public static boolean FILTER_MODULE_ACCESS_BY_BZ_UNIT = PropertyManager.getBoolean(
			"modules.filter.by.biz.unit", false);


    public static List getPropertyList(String key) {
		List returnList = new ArrayList();
		String propertyList = PropertyManager.getValue(key);
		if ((propertyList != null) && (propertyList.length() > 0)) {
			StringTokenizer st = new StringTokenizer(propertyList, "|");
			while (st.hasMoreTokens()) {
				returnList.add(st.nextToken());
			}
		}
		return returnList;
	}

	private static Map getPropertyMap(String key) {
		Map map = new HashMap();
		String propertyList = PropertyManager.getValue(key);
		if ((propertyList != null) && (propertyList.length() > 0)) {
			StringTokenizer st = new StringTokenizer(propertyList, "|");
			while (st.hasMoreTokens()) {
				String actionEventStr = st.nextToken();
				if (actionEventStr.indexOf(",") < 0) {
					map.put(actionEventStr, null);
				}
				else {
					StringTokenizer st1 = new StringTokenizer(actionEventStr, ",");
					String action = st1.nextToken();
					List eventList = new ArrayList();
					while (st1.hasMoreTokens()) {
						eventList.add(st1.nextToken());
					}
					map.put(action, eventList);
				}
			}
		}
		return map;
	}

	public static boolean isResetGlobalCustomerAARequire(String action, String event) {
		if (action == null) {
			return true;
		}

		Map RESET_GLOBAL_CUSTOMER_LIMIT_PROFILE_ACTION_EVENT = getPropertyMap("reset.global.customer.limit.profile");
		if (RESET_GLOBAL_CUSTOMER_LIMIT_PROFILE_ACTION_EVENT == null) {
			return false;
		}

		if (RESET_GLOBAL_CUSTOMER_LIMIT_PROFILE_ACTION_EVENT.containsKey(action)) {
			List eventList = (ArrayList) RESET_GLOBAL_CUSTOMER_LIMIT_PROFILE_ACTION_EVENT.get(action);
			if (eventList == null) {
				return true;
			}
			if (eventList.contains(event)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isSTPRequired() {
		return PropertyManager.getBoolean("stp.required", false);
	}

	public static boolean isValidSTPSystem(String sourceSystem) {
		return (sourceSystem != null) && !FILTER_STP_SYSTEM_LIST.contains(sourceSystem);
	}

	public static boolean isValidSTPFacilityLoadingApplicationType(String appType) {
		return (appType != null) && !FILTER_STP_FAC_LOAD_APP_TYPE.contains(appType);
	}

	public static boolean isValidDocumentationSystem(String sourceSystem) {
		return (sourceSystem == null) || !FILTER_DOCUMENTATION_SYSTEM.contains(sourceSystem);
	}

	public static boolean isValidDocumentationApplicationType(String appType) {
		return (appType == null) || !FILTER_DOCUMENTATION_APP_TYPE.contains(appType);
	}

	public static boolean isValidSTPCollateralLoadingApplicationType(String appType) {
		return (appType != null) && !FILTER_STP_COL_LOAD_APP_TYPE.contains(appType);
	}

	public static boolean isValidSTPCollateralLoadingSystesm(String sourceSystem) {
		return (sourceSystem != null) && !FILTER_STP_COL_LOAD_SYSTEM.contains(sourceSystem);
	}

	public static boolean isFilterByApplicationType() {
		return FILTER_MODULE_ACCESS_BY_AA_TYPE;
	}
     public static boolean isFilterByBusinessUnit() {
		return FILTER_MODULE_ACCESS_BY_BZ_UNIT;
	}
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

	public static boolean displayExceptionLog() {
		return PropertyManager.getBoolean("display.exception.log", false);
	}

	public static String getBuildNumber() {
		return PropertyManager.getValue("build.number", "-");
	}

	public static boolean isProductDescSpecialHandling() {
		return PropertyManager.getBoolean("common.code.product.type.special.handling", false);
	}

	public static boolean isPendingFacilityFieldsEnable() {
		return PropertyManager.getBoolean("facility.pending.fields.enable", false);
	}

	public static boolean isMigrationDataFieldsEnable() {
		return PropertyManager.getBoolean("migration.data.fields.enable", false);
	}

	public static boolean isFilterPreApprovalDocuments() {
		return PropertyManager.getBoolean("checklist.filter.pre.approval.docs", true);
	}

	public static Map getTemplateCCDocApplicableApplicationTypesMap() {
		Map ccDocAppTypesMap = getPropertyMap("template.cc.doc.app.types");
		for (Iterator itr = ccDocAppTypesMap.entrySet().iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			List applicableAppTypes = (List) entry.getValue();
			entry.setValue(applicableAppTypes.toArray(new String[0]));
		}
		return ccDocAppTypesMap;
	}

    public static boolean isGenerateLIApplicable(String applicationType, long sequence) {
        Map appGenLIAutoEndDate = getPropertyMap("apps.gen.li.auto.endDate");

        ArrayList al = (ArrayList)appGenLIAutoEndDate.get(applicationType);
        if (al != null) {
            try {
                String string = al.get(0).toString();
                long seq = Long.parseLong(string);
                if (seq == sequence) {
                    return true;
                }
            } catch (ClassCastException cce) {
                DefaultLogger.debug("PropertiesConstantHelper : ", cce.getMessage());
            }
        }
        return false;
    }

    public static boolean isGenerateLIApplicable(String applicationType) {
        Map appGenLIAutoEndDate = getPropertyMap("apps.gen.li.auto.endDate");

        ArrayList al = (ArrayList)appGenLIAutoEndDate.get(applicationType);
        if (al != null) {
            return true;
        } else {
            return false;
        }
    }
}
