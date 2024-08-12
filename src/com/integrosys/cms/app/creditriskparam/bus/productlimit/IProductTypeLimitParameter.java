package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import java.io.Serializable;


public interface IProductTypeLimitParameter  extends Serializable {
    
   public Long getId();
   public void setId(Long id);
	 
   public Long getProductProgramId();
   public void setProductProgramId(Long productProgramId);
   
   public String getProductTypeDesc();
   public void setProductTypeDesc(String productTypeDesc);
   
   public String getReferenceCode();
   public void setReferenceCode(String referenceCode);
    
   public String getStatus();
   public void setStatus (String status);
	
   public long getCmsRefId();
   public void setCmsRefId(long cmsRefId);
   	
}
