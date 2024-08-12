/*
 * Created on Apr 4, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

/**
 * @author Administrator
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class EBCollateralPledgorStagingBean extends EBCollateralPledgorBean {
    protected EBPledgorLocalHome getEBPledgorLocalHome() {
        EBPledgorLocalHome ejbHome = (EBPledgorLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_PLEDGOR_STAGING_LOCAL_JNDI, EBPledgorLocalHome.class.getName());
        if (ejbHome == null) {
            throw new EJBException("EBPledgorLocalHome is Null!");
        }
        return ejbHome;
    }

    /**
     * Set pledgor.
     *
     * @param pledgor of type IPledgor
     * @throws javax.ejb.FinderException on error finding the pledgor information
     */
    protected void setPledgorRef(IPledgor pledgor) throws CreateException {
        EBPledgorLocalHome ejbHome = getEBPledgorLocalHome();
        //Andy Wong, 14 July 2009: overwrite actual bean method, always insert staging pledgor detail table
        EBPledgorLocal pledgorLocal = ejbHome.create(pledgor);
        setPledgorCMR(pledgorLocal);
    }
}
