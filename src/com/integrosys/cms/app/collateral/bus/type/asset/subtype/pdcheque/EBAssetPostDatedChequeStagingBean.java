/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/pdcheque/EBAssetPostDatedChequeStagingBean.java,v 1.1 2003/07/24 10:29:28 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque;

import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for staging asset of type post dated cheque.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/24 10:29:28 $ Tag: $Name: $
 */
public abstract class EBAssetPostDatedChequeStagingBean extends EBAssetPostDatedChequeBean {
	/**
	 * Get post dated cheque local home
	 * 
	 * @return EBPostDatedChequeLocalHome
	 */
	protected EBPostDatedChequeLocalHome getEBPostDatedChequeLocalHome() {
		EBPostDatedChequeLocalHome ejbHome = (EBPostDatedChequeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_POST_DATED_CHEQUE_STAGING_LOCAL_JNDI, EBPostDatedChequeLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBPostDatedChequeLocalHome for staging is Null!");
		}

		return ejbHome;
	}
}