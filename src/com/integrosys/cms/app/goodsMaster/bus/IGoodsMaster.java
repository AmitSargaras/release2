package com.integrosys.cms.app.goodsMaster.bus;

import java.util.Set;
import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

public interface IGoodsMaster extends Serializable, IValueObject{
	
	/*public Set getGoodsParentCodeList(); 
	public void setGoodsParentCodeList(Set goodsParentCodeList);*/
	
	public String getGoodsParentCode(); 
	public void setGoodsParentCode(String goodsParentCode);
	
	public String getGoodsCode(); 
	public void setGoodsCode(String goodsCode);
	
	public String getGoodsName(); 
	public void setGoodsName(String goodsName);
	
	public String getRestrictionType(); 
	public void setRestrictionType(String restrictionType);
	
	public String getDeprecated();
    public void setDeprecated(String deprecated);

    public long getId();
    public void setId(long id);	

    public String getStatus();
    public void setStatus(String aStatus);	
    
    public long getMasterId();
    public void setMasterId(long masterId);	

    public long getVersionTime();
    public void setVersionTime(long versionTime);
    
    public Date getCreationDate();
    public void setCreationDate(Date creationDate);
   
    public String getCreateBy();
    public void setCreateBy(String createBy);
    
    public Date getLastUpdateDate();
    public void setLastUpdateDate(Date lastUpdateDate);
   
    public String getLastUpdateBy();
    public void setLastUpdateBy(String lastUpdateBy);
    
	public String getCpsId() ;
	public void setCpsId(String cpsId) ;

	public String getOperationName();
	public void setOperationName(String operationName) ;
}
