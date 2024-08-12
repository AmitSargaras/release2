package com.integrosys.cms.app.tatduration.bus;

import java.util.List;

import org.hibernate.HibernateException;

/**
 * @author Cynthia
 * @since Sep 1, 2008
 */
public interface ITatParamDAO {

	// **************** Entity Names In Hibernate File ******************
	/**
	 * entity name for OBTatDoc stored in actual table
	 */
	public static final String ACTUAL_TAT_PARAM = "actualTatParam";

	/**
	 * entity name for OBTatDoc stored in stage table
	 */
	public static final String STAGE_TAT_PARAM = "stageTatParam";
	
	public static final String ACTUAL_TAT_PARAM_ITEM = "actualTatParamItem";

	/**
	 * entity name for OBTatDoc stored in stage table
	 */
	public static final String STAGE_TAT_PARAM_ITEM = "stageTatParamItem";


	// **************** Implementation Methods ******************
	public ITatParam create(String entityName, ITatParam tatParamStage);

	public ITatParam update(String entityName, ITatParam tatParamStage);

	public ITatParam getTatParamByApplicationType(String appType) throws HibernateException;
	
	public ITatParamItem getStageTatParamItem(Long tatParamItemId) throws HibernateException;
	
	public ITatParamItem getTatParamItem(Long tatParamItemId) throws HibernateException;
	
	public ITatParam getTatParam(String entityName, Long key) throws HibernateException;
	
	public ITatParam getTatParam(Long tatParamId) throws HibernateException;

}
