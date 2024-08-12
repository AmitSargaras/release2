package com.integrosys.cms.app.valuationAgency.bus;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;

public interface IValuationAgency extends Serializable, IValueObject {

	public long getId();

	public void setId(long id);

	public String getValuationAgencyCode();

	public void setValuationAgencyCode(String valuationAgencyCode);

	public String getValuationAgencyName();

	public void setValuationAgencyName(String valuationAgencyName);

	public String getAddress();

	public void setAddress(String address);

/*	public String getCityTown();

	public void setCityTown(String cityTown);

	public String getState();

	public void setState(String state);

	public String getCountry();

	public void setCountry(String country);

	public String getRegion();

	public void setRegion(String region);
*/
	public ICity getCityTown();

	public void setCityTown(ICity cityTown);
	public IState getState() ;
	public void setState(IState state);

	public ICountry getCountry() ;

	public void setCountry(ICountry country) ;

	public IRegion getRegion();

	public void setRegion(IRegion region);

	public long getVersionTime();

	public void setVersionTime(long versionTime);

	public String getStatus();

	public void setStatus(String status);

	public long getMasterId();

	public void setMasterId(long masterId);

	public String getDeprecated();

	public void setDeprecated(String deprecated);

	public Date getCreationDate();

	public void setCreationDate(Date creationDate);

	public String getCreateBy();

	public void setCreateBy(String createBy);

	public Date getLastUpdateDate();

	public void setLastUpdateDate(Date lastUpdateDate);

	public String getLastUpdateBy();

	public void setLastUpdateBy(String lastUpdateBy);
	
	public FormFile getFileUpload();

	public void setFileUpload(FormFile fileUpload);
}
