package com.integrosys.cms.app.commoncode.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface ICommonCodeType extends Serializable, IValueObject {

	long getCommonCategoryId();

	String getCommonCategoryCode();

	String getCommonCategoryName();

	int getCommonCategoryType();

	String getActiveStatus();

	void setCommonCategoryId(long categoryId);

	void setCommonCategoryCode(String categoryCode);

	void setCommonCategoryName(String categoryName);

	void setCommonCategoryType(int categoryType);

	void setActiveStatus(String activeStatus);

	public String getRefCategoryCode();

	public void setRefCategoryCode(String refCategoryCode);
}
