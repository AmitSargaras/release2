package com.integrosys.cms.app.propertyindex.bus;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA. User: Andy Wong Date: Sep 11, 2008 Time: 3:11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPropertyIdxBusManager implements IPropertyIdxBusManager {

	private IPropertyIdxDao propertyIdxDao;

	private IPropertyIdxJdbc propertyIdxJdbc;

	public IPropertyIdxDao getPropertyIdxDao() {
		return propertyIdxDao;
	}

	public void setPropertyIdxDao(IPropertyIdxDao propertyIdxDao) {
		this.propertyIdxDao = propertyIdxDao;
	}

	public IPropertyIdxJdbc getPropertyIdxJdbc() {
		return propertyIdxJdbc;
	}

	public void setPropertyIdxJdbc(IPropertyIdxJdbc propertyIdxJdbc) {
		this.propertyIdxJdbc = propertyIdxJdbc;
	}

	public IPropertyIdx createPropertyIdx(IPropertyIdx propertyIdx) {
		return getPropertyIdxDao().createPropertyIdx(getPropertyIdxEntityName(), propertyIdx);
	}

	public IPropertyIdx getPropertyIdx(long key) {
		return getPropertyIdxDao().getPropertyIdx(getPropertyIdxEntityName(), key);
	}

	public IPropertyIdx updatePropertyIdx(IPropertyIdx propertyIdx) {
		return getPropertyIdxDao().updatePropertyIdx(getPropertyIdxEntityName(), propertyIdx);
	}

	public List getAllPropertyIdx() {
		return getPropertyIdxJdbc().getAllPropertyIdx();
	}

	public boolean isSecSubTypeValTypeExist(long propertyIndexID, Set secSubTypeList, String valDesc) {
		return getPropertyIdxJdbc().isSecSubTypeValTypeExist(propertyIndexID, secSubTypeList, valDesc);
	}

	public abstract String getPropertyIdxEntityName();

}