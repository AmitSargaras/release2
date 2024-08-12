package com.integrosys.cms.app.creditriskparam.bus.productlimit;


import java.math.BigDecimal;
import java.util.*;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.businfra.currency.Amount;

public class OBProductProgramLimitParameter  implements IProductProgramLimitParameter{

	private static final long serialVersionUID = 1L;
	private static final String CURRENCY_CODE = "MYR";

	private Long id;
    private String productProgramDesc;
	private String referenceCode;
    private Amount limitAmount;
    private String status;
    private long versionTime = ICMSConstant.LONG_INVALID_VALUE;
    private long cmsRefId = ICMSConstant.LONG_INVALID_VALUE;
    private Collection productTypeList;
    private Set productTypeSet;

    
    public OBProductProgramLimitParameter() {
        super();
    }
    
    public OBProductProgramLimitParameter(IProductProgramLimitParameter productProgramLimitParameter) {
        this();
        AccessorUtil.copyValue(productProgramLimitParameter, this);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Set getProductTypeSet() {
        return productTypeSet;
    }

    public void setProductTypeSet(Set productTypeSet) {
        this.productTypeSet = productTypeSet;
    }

    public String getProductProgramDesc() {
        return productProgramDesc;
    }

    public void setProductProgramDesc(String productProgramDesc) {
        this.productProgramDesc = productProgramDesc;
    }
	
    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public Amount getLimitAmount() {
        BigDecimal amt = limitAmount.getAmountAsBigDecimal();
        if (amt != null) {
        	return new Amount(amt.doubleValue(), CURRENCY_CODE);
        }
        else {
        	return null;
        }
       // return limitAmount;
    }

    public void setLimitAmount(Amount limitAmount) {
        this.limitAmount = limitAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }
    
    public long getCmsRefId() {
		return cmsRefId;
	}

	public void setCmsRefId(long cmsRefId) {
		this.cmsRefId = cmsRefId;
	}

	public Collection getProductTypeList() {
		return productTypeList;
	}

	public void setProductTypeList(Collection productTypeList) {
		this.productTypeList = productTypeList;
        this.productTypeSet = (productTypeList == null) ? null: new HashSet (productTypeList);
	}

    public boolean equals (Object obj)
    {
        if (obj == null)
            return false;
        else if (!(obj instanceof OBProductProgramLimitParameter))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }

    class AlphabeticComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            IProductTypeLimitParameter s1 = (IProductTypeLimitParameter) o1;
            IProductTypeLimitParameter s2 = (IProductTypeLimitParameter) o2;
            return s1.getProductTypeDesc().compareTo(s2.getProductTypeDesc());
        }
    }
}
