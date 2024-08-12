/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/CommodityConstant.java,v 1.9 2006/05/22 07:20:22 pratheepa Exp $
 */

package com.integrosys.cms.app.commodity;

/**
 * CommodityConstant
 * @author $Author: pratheepa $
 * @version $
 * @since Mar 30, 2004 2:51:07 PM$
 */
public class CommodityConstant {

	public static final String ENTITY_STATUS_ACTIVE = "A";

	public static final String ENTITY_STATUS_DELETED = "D";

	public static final String GROUP_ID_PREFIX = "G";

	public static final String SEQUENCE_TITLE_DOCUMENT_SEQ = "TitleDocument_Seq";

	public static final String SEQUENCE_TITLE_DOCUMENT_STAGING_SEQ = "TitleDocument_Staging_Seq";

	public static final String SEQUENCE_TITLE_DOCUMENT_GROUP_SEQ = "TitleDocument_Group_Seq";

	public static final String SEQUENCE_WAREHOUSE_SEQ = "Warehouse_Seq";

	public static final String SEQUENCE_WAREHOUSE_STAGING_SEQ = "Warehouse_Staging_Seq";

	public static final String SEQUENCE_WAREHOUSE_GROUP_SEQ = "Warehouse_Group_Seq";

	public static final String SEQUENCE_SLT_SEQ = "SubLimitType_Seq";

	public static final String SEQUENCE_SLT_STAGING_SEQ = "SubLimitType_Staging_Seq";

	public static final String SEQUENCE_SLT_GROUP_SEQ = "SubLimitType_Group_Seq";

	public static final String SEQUENCE_PROFILE_SEQ = "Profile_Seq";

	public static final String SEQUENCE_PROFILE_STAGING_SEQ = "Profile_Staging_Seq";

	public static final String SEQUENCE_PROFILE_GROUP_SEQ = "Profile_Group_Seq";

	public static final String SEQUENCE_NON_RIC_SEQ = "Profile_NonRIC_Seq";

	public static final String SEQUENCE_PROFILE_SUPPLIER_SEQ = "ProfileSupplier_Seq";

	public static final String SEQUENCE_PROFILE_SUPPLIER_STAGING_SEQ = "ProfileSupplier_Staging_Seq";

	public static final String SEQUENCE_PROFILE_BUYER_SEQ = "ProfileBuyer_Seq";

	public static final String SEQUENCE_PROFILE_BUYER_STAGING_SEQ = "ProfileBuyer_Staging_Seq";

	public static final String SEQUENCE_UOM = "COMMODITY_UOM_SEQ";

	public static final String SEQUENCE_UOM_GROUP_SEQ = "COMMODITY_UOM_GROUP_SEQ";

	protected static final String[] TITLEDOCUMNETS_FROZEN = { "Warehouse Receipt", "Bill of Lading", "Trust Receipt",
			"Warehouse Receipt (Negotiable)", "Bill of Lading (Negotiable)", "Trust Receipt (Non-Negotiable)" };

	public static final String COMMODITY_CATEGORY_LIST_NAME = "CMDTCAT_NM";

	public static final String COMMODITY_TYPE_LIST_NAME = "COMMODITY_TYPE_NAMES";

	public static final String SIGN_PLUS = "pl";

	public static final String SIGN_MINUS = "mi";

	public static final String SIGN_PLUS_OR_MINUS = "pm";

	public static final int DEAL_AMT_SCALE = 2;
}