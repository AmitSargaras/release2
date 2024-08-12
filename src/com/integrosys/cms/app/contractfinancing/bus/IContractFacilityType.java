package com.integrosys.cms.app.contractfinancing.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IContractFacilityType extends Serializable {

	public long getFacilityTypeID();

	public void setFacilityTypeID(long facilityTypeID);

	public String getFacilityType();

	public void setFacilityType(String facilityType);

	public String getFacilityTypeOthers();

	public void setFacilityTypeOthers(String facilityTypeOthers);

	public Date getFacilityDate();

	public void setFacilityDate(Date facilityDate);

	public float getMoa();

	public void setMoa(float moa);

	public Amount getMaxCap();

	public void setMaxCap(Amount maxCap);

	public String getRemarks();

	public void setRemarks(String remarks);

	public long getCommonRef();

	public void setCommonRef(long commonRef);

	public boolean getHasChild();

	public void setHasChild(boolean hasChild);

	public boolean getIsDeleted();

	public void setIsDeleted(boolean isDeleted);
}
