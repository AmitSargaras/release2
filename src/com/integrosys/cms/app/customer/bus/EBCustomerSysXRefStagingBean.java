/*
 * Created on Mar 8, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class EBCustomerSysXRefStagingBean extends EBCustomerSysXRefBean {	
    public Date getLastAllocationDate() {
    	return null; 
    }
    
    public void setLastAllocationDate(Date lastAllocationDate) {}
    
    public String getColAllocationAmtCcy() {
    	return null;
    }

    public void setColAllocationAmtCcy (String currency){}
    
    public String getOutstandingAmtCcy() {
    	return null;
    }
    
    public void setOutstandingAmtCcy (String currency) {}

    public Double getEBCollateralAllocationAmt() {
    	return null;
    }
    
    public void setEBCollateralAllocationAmt (Double amt) {}
    
    public Double getEBOutstandingAmt() {
    	return null;
    }
    
    public void setEBOutstandingAmt (Double amt) {}	
    
  //added by santosh UBS LIMIT upload
    protected EBLimitXRefUdfLocalHome getEBLocalHomeXRefUdfInfo() throws CustomerException {
		EBLimitXRefUdfLocalHome home = (EBLimitXRefUdfLocalHome) BeanController	.getEJBLocalHome(ICMSJNDIConstant.EB_XREF_UDF_LOCAL_JNDI_STAGING, EBLimitXRefUdfLocalHome.class.getName());
		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBLimitXRefUdfLocalHome is null!");
		}
	}
  	//end santosh
    
    protected EBLineCovenantLocalHome getEBLocalHomeLineCovenant() throws CustomerException {
		EBLineCovenantLocalHome home = (EBLineCovenantLocalHome) BeanController	.getEJBLocalHome(ICMSJNDIConstant.EB_LINE_COVENANT_LOCAL_JNDI_STAGING, EBLineCovenantLocalHome.class.getName());
		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBLineCovenantLocalHome is null!");
		}
	}
    
    protected EBLimitXRefUdfLocalHome2 getEBLocalHomeXRefUdfInfo2() throws CustomerException {
  		EBLimitXRefUdfLocalHome2 home = (EBLimitXRefUdfLocalHome2) BeanController	.getEJBLocalHome(ICMSJNDIConstant.EB_XREF_UDF_LOCAL_JNDI_STAGING2, EBLimitXRefUdfLocalHome2.class.getName());
  		if (null != home) {
  			return home;
  		} else {
  			throw new CustomerException("EBLimitXRefUdfLocalHome2 is null!");
  		}
  	}
    
    protected EBLimitXRefCoBorrowerLocalHome getEBLocalHomeXRefCoBorrower() throws CustomerException {
    	EBLimitXRefCoBorrowerLocalHome home = (EBLimitXRefCoBorrowerLocalHome) BeanController	.getEJBLocalHome(ICMSJNDIConstant.EB_XREF_COBORROWER_LOCAL_JNDI_STAGING, EBLimitXRefCoBorrowerLocalHome.class.getName());
  		if (null != home) {
  			return home;
  		} else {
  			throw new CustomerException("EBLimitXRefCoBorrowerLocalHome is null!");
  		}
  	}
}
