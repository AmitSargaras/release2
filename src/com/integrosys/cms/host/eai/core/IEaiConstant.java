package com.integrosys.cms.host.eai.core;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Various constants used in EAI context, such as Transaction keys, Hibernate
 * entity names, Validation used allowed values
 * @author marvin
 * @author Chong Jun Yong
 */
public interface IEaiConstant {

	public static final String DELIMITER = "|";

	public static final String AA_LAW_TYPE_ISL = ICMSConstant.AA_LAW_TYPE_ISLAM;

	public static final String AA_LAW_TYPE_CON = ICMSConstant.AA_LAW_TYPE_CONVENTIONAL;

	public static final String REPUBLISH_INDICATOR = "REPUBLISH";

	public static final String NORMAL_INDICATOR = "NORMAL";

	public static final char CREATEINDICATOR = ICMSConstant.HOST_STATUS_INSERT.charAt(0);

	public static final char UPDATEINDICATOR = ICMSConstant.HOST_STATUS_UDPATE.charAt(0);

	public static final char DELETEINDICATOR = ICMSConstant.HOST_STATUS_DELETE.charAt(0);

	public static final char CHANGEINDICATOR = ICMSConstant.TRUE_VALUE.charAt(0);

	public static final String UPDATE_STATUS_IND_INSERT = ICMSConstant.HOST_STATUS_INSERT;

	public static final String UPDATE_STATUS_IND_UPDATE = ICMSConstant.HOST_STATUS_UDPATE;

	public static final String UPDATE_STATUS_IND_DELETE = ICMSConstant.HOST_STATUS_DELETE;

	public static final String CHANGE_INDICATOR_YES = ICMSConstant.TRUE_VALUE;

	public static final String CHANGE_INDICATOR_NO = ICMSConstant.FALSE_VALUE;

	public static final char DUMMYINDICATOR = 'M';

	public static final String TENURE_TYPE_FREEHOLDER = "F";

	public static final String TENURE_TYPE_LEASEHOLD = "L";

	public static final String ACTIVE_STATUS = ICMSConstant.STATE_ACTIVE;

	public static final String CMSID = "CmsId";

	public static final String CIFID = "CIFId";

	public static final String LIMITPROFILEID = "LimitProfileId";

	public static final String LIMIT_KEY = "LimitTrxKey";

	public static final String CHECKLIST_KEY = "ChecklistTrxKey";

	public static final String CO_LIMIT_KEY = "CoLimitTrxKey";

	public static final String CUSTOMER_KEY = "SubProfileTrxKey";

	public static final String DOCUMENT_KEY = "DocumentTrxKey";

	public static final String LIMITPROFILE_KEY = "LimitProfileTrxKey";

	public static final String SECURITY_KEY = "SecurityTrxKey";

	public static final String SECURITY_REPUBLISH_KEY = "RepublishSecurityTrxKey";

	public static final String COVENANT_KEY = "CovenantTrxKey";

	public final static String MSG_OBJ = "msgobj";

	public final static String FLATMSG_OBJ = "flatmsgobj";

	public final static String STAG_MSG_OBJ = "stagmsgobj";

	public static final String SUBPROFILE_ID = "1";

	public static final String GCIF_COUNTRY_PREFIX = "gcif.country.id.";

	public final static String CUST_SINGLE = "CUST_SINGLE";

	public final static String CUST_MULTILE = "CUST_MULTIPLE";

	public final static String STAT_PROCESSING = "P";

	public final static String STAT_SUCCESS = "S";

	public final static String STAT_EXCEPTION = "E";

	public final static String ACTUAL_LIMIT_GENERAL = "actualLimitGeneral";

	public final static String STAGE_LIMIT_GENERAL = "stageLimitGeneral";

	public final static String ACTUAL_FACILITY_MASTER = "actualFacMaster";

	public final static String ACTUAL_FACILITY_OFFICER = "actualFacOfficer";

	public final static String ACTUAL_FACILITY_RELATIONSHIP = "actualFacRelationship";

	public final static String ACTUAL_FACILITY_MULTI_FINANCE = "actualFacilityMultiTierFinancing";

	public final static String ACTUAL_DOC_CHECKLIST_INPUT = "actualDocChecklistInput";

	public final static String ACTUAL_DOC_CHECKLIST_ITEM = "actualDocChecklistItem";

	public final static String STAGE_FACILITY_MASTER = "stageFacMaster";

	public final static String STAGE_FACILITY_OFFICER = "stageFacOfficer";

	public final static String STAGE_FACILITY_RELATIONSHIP = "stageFacRelationship";

	public final static String STAGE_FACILITY_MULTI_FINANCE = "stageFacilityMultiTierFinancing";

	public final static String STAGE_DOC_CHECKLIST_INPUT = "stageDocChecklistInput";

	public final static String STAGE_DOC_CHECKLIST_ITEM = "stageDocChecklistItem";

	public final static String ENTITY_ACTUAL_FACILITY_MESSAGE = "actualFacMessage";

	public final static String ENTITY_ACTUAL_FACILITY_ISL_SECURITY_DEPOSIT = "actualFacIslamicSecurityDeposit";

	public final static String ENTITY_ACTUAL_FACILITY_ISL_RENTAL_RENEWAL = "actualFacIslamicRentalRenewal";

	public final static String ENTITY_STAGE_FACILITY_MESSAGE = "stageFacMessage";

	public final static String ENTITY_STAGE_FACILITY_ISL_SECURITY_DEPOSIT = "stageFacIslamicSecurityDeposit";

	public final static String ENTITY_STAGE_FACILITY_ISL_RENTAL_RENEWAL = "stageFacIslamicRentalRenewal";

	public final static String ENTITY_ACTUAL_FACILITY_INCREMENTAL = "actualFacIncremental";

	public final static String ENTITY_ACTUAL_FACILITY_REDUCTION = "actualFacReduction";

	public final static String ENTITY_STAGE_FACILITY_INCREMENTAL = "stageFacIncremental";

	public final static String ENTITY_STAGE_FACILITY_REDUCTION = "stageFacReduction";

	public final static String CHECKLIST_TEMPLATE_INQUIRY = "TEMPLATE.INQUIRY";

	public final static String CHECKLIST_DOCUMENT_INQUIRY = "DOCUMENT.INQUIRY";

	/** Allowed values for Yes and No, ie, "Y" and "N" */
/*	public final static String[] ALLOWED_VALUES_YES_NO = new String[] { ICMSConstant.TRUE_VALUE,
			ICMSConstant.FALSE_VALUE };

	*//** Allowed values for Yes, No and NA, ie, "Y", "N", "O" *//*
	public final static String[] ALLOWED_VALUES_YES_NO_NA = new String[] { ICMSConstant.TRUE_VALUE,
			ICMSConstant.FALSE_VALUE, ICMSConstant.NOT_AVAILABLE_VALUE };

	*//** Allowed values for Frequency, ie, "D", "W", "M", "Y" *//*
	public final static String[] ALLOWED_VALUES_FREQUENCY_UNIT = new String[] { ICMSConstant.FREQ_UNIT_DAYS,
			ICMSConstant.FREQ_UNIT_WEEKS, ICMSConstant.FREQ_UNIT_MONTHS, ICMSConstant.FREQ_UNIT_YEARS };

	*//** Allowed values for Update Status Indicators, ie, "I", "U", "D" *//*
	public final static String[] ALLOWED_VALUES_UPDATE_STATUS_INDICATORS = new String[] {
			ICMSConstant.HOST_STATUS_INSERT, ICMSConstant.HOST_STATUS_DELETE, ICMSConstant.HOST_STATUS_UDPATE };

	*//** Allowed values for Custodian Types, ie, "I", "E" *//*
	public final static String[] ALLOWED_VALUES_CUSTODIAN_TYPES = new String[] { ICMSConstant.INTERNAL_COL_CUSTODIAN,
			ICMSConstant.EXTERNAL_COL_CUSTODIAN };*/

	/** Allowed values for long value (19) */
	public final static long ALLOWED_LONG_VALUE_LENGTH_19 = Long.MAX_VALUE;

	/** Allowed values for long value (10) */
	public final static long ALLOWED_LONG_VALUE_LENGTH_10 = Long.parseLong(StringUtils.repeat("9", 10));

	public final static long ALLOWED_LONG_VALUE_YEAR_YYYY = 9999;

}
