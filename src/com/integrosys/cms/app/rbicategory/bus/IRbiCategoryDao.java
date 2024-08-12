package com.integrosys.cms.app.rbicategory.bus;

import java.io.Serializable;
import java.util.List;
/**
 * @author  Govind.Sahu
 */
public interface IRbiCategoryDao {

	static final String ACTUAL_RBI_CATEGORY_NAME = "actualRbiCategory";
	static final String STAGE_RBI_CATEGORY_NAME = "stageRbiCategory";
	
	List searchRbiCategory(String srAlph ,String entityName)throws RbiCategoryException;
	List getAllRbiCategoryList(String entityName )throws RbiCategoryException;
	List getRbiIndCodeByNameList(String industry,String entityName)throws RbiCategoryException;
	IRbiCategory getRbiCategory(String entityName, Serializable key)throws RbiCategoryException;
	IRbiCategory updateRbiCategory(String entityName, IRbiCategory rbiCategory)throws RbiCategoryException;
	IRbiCategory deleteRbiCategory(String entityName, IRbiCategory rbiCategory);
	IRbiCategory load(String entityName,long id)throws RbiCategoryException;
	IRbiCategory createRbiCategory(String entityName, IRbiCategory rbiCategory)throws RbiCategoryException;
	boolean getActualRbiCategory(String entityName, IRbiCategory rbiCategory)throws RbiCategoryException;
	public boolean isIndustryNameApprove(String entityName,String industryNameId)throws RbiCategoryException;
	public List isRbiCodeCategoryApprove(String entityName,OBRbiCategory stgObRbiCategory, boolean isEdit, OBRbiCategory actObRbiCategory)throws RbiCategoryException;
}
