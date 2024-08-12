package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBProductTypeLimitParameter implements IProductTypeLimitParameter {
    
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long productProgramId;
	private String productTypeDesc;
	private String referenceCode;
    private String status;
    private long cmsRefId = ICMSConstant.LONG_INVALID_VALUE;

	public OBProductTypeLimitParameter() {
        super();
    }

    public OBProductTypeLimitParameter(IProductTypeLimitParameter productTypeLimitParameter) {
        this();
        AccessorUtil.copyValue(productTypeLimitParameter, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProductProgramId() {
        return productProgramId;
    }

    public void setProductProgramId(Long productProgramId) {
        this.productProgramId = productProgramId;
    }

    public String getProductTypeDesc() {
        return productTypeDesc;
    }

    public void setProductTypeDesc(String productTypeDesc) {
        this.productTypeDesc = productTypeDesc;
    }
    
    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCmsRefId() {
		return cmsRefId;
	}

	public void setCmsRefId(long cmsRefId) {
		this.cmsRefId = cmsRefId;
	}
	
    public boolean equals (Object obj)
    {
        if (obj == null)
            return false;
        else if (!(obj instanceof OBProductTypeLimitParameter))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }

}
