package com.integrosys.cms.app.rbicategory.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author  Govind.Sahu 
 */
public interface IIndustryNameStage extends Serializable, IValueObject {

	/**
	 * @return the id
	 */
	public long getId();
	/**
	 * @param id the id to set
	 */
	public void setId(long id) ;
	/**
	 * @return the rbiCodeCategoryId
	 */
	public String getRbiCodeCategoryId() ;
	/**
	 * @param rbiCodeCategoryId the rbiCodeCategoryId to set
	 */
	public void setRbiCodeCategoryId(String rbiCodeCategoryId) ;
	/**
	 * @return the oBRbiCategory
	 */
	public OBRbiCategory getOBRbiCategory();
	/**
	 * @param rbiCategory the oBRbiCategory to set
	 */
	public void setOBRbiCategory(OBRbiCategory rbiCategory);
	/**
	 * @return the rbiCategoryId
	 */
	public long getRbiCategoryId();
	/**
	 * @param rbiCategoryId the rbiCategoryId to set
	 */
	public void setRbiCategoryId(long rbiCategoryId);
}
