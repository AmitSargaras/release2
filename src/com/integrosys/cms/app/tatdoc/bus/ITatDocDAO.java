package com.integrosys.cms.app.tatdoc.bus;

import java.util.List;

import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack;
import com.integrosys.cms.app.tatduration.bus.ITatParam;

/**
 * @author Cynthia
 * @since Sep 1, 2008
 */
public interface ITatDocDAO {

	// **************** Entity Names In Hibernate File ******************
	/**
	 * entity name for OBTatDoc stored in actual table
	 */
	public static final String ACTUAL_TAT_DOC = "actualTatDoc";

	/**
	 * entity name for OBTatDoc stored in stage table
	 */
	public static final String STAGE_TAT_DOC = "stageTatDoc";

	/**
	 * entity name for OBTatDocDraft stored in actual table
	 */
	public static final String ACTUAL_TAT_DOC_DRAFT = "actualTatDocDraft";

	/**
	 * entity name for OBTatDocDraft stored in stage table
	 */
	public static final String STAGE_TAT_DOC_DRAFT = "stageTatDocDraft";

	// **************** Implementation Methods ******************
	public ITatDoc create(String entityName, ITatDoc tatDoc);

	public ITatDoc update(String entityName, ITatDoc tatDoc);

	public ITatDoc getTatDocByID(String entityName, long tatDocID);

	public ITatDoc getTatDocByID(String entityName, Long tatDocID);

	public ITatDoc getTatDocByLimitProfileID(String entityName, long limitProfileID);

	public ITatParam getTatParamByAppType(String appType);
	
	public void commitTatTrackingList(OBTatLimitTrack trackOB);
}
