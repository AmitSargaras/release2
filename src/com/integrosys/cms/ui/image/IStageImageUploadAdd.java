package com.integrosys.cms.ui.image;

import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * This class defines the operations that are provided by a image upload
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/07 11:32:23 $ Tag: $Name: $
 */
public interface IStageImageUploadAdd extends Serializable, IValueObject {

	public long getImgId();
	
	public void setImgId(long imgId);

	public String getImgFileName();

	public void setImgFileName(String imgFileName);

	public long getImgSize();

	public void setImgSize(long imgSize);
	
	public String getImgDepricated() ;

	public void setImgDepricated(String imgDepricated);
	
	public long getVersionTime();
	
	public void setVersionTime(long versionTime);

	public String getCustId();

	public void setCustId(String custId);

	public String getCustName();

	public void setCustName(String custName);
	
	public String getImageFilePath();

	public void setImageFilePath(String imageFilePath);
	
	public String getSendForAppFlag();
	
	public void setSendForAppFlag(String sendForAppFlag);
	
	public String getImageApprId();

	public void setImageApprId(String imageApprId);
	
	


}
