/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/EBProfileStagingBean.java,v 1.2 2004/06/04 04:53:01 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

/**
 * Profile bean (staging) implementation class.
 *
 * @author  $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since   $Date: 2004/06/04 04:53:01 $
 */
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.commodity.main.CommodityException;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Mar 25, 2004 Time:
 * 10:31:30 AM To change this template use File | Settings | File Templates.
 */
public abstract class EBProfileStagingBean extends EBProfileBean {

	public EBProfileStagingBean() {
		isStaging = true;
	}

	protected EBSupplierLocalHome _getSupplierLocalHome() {
		return (EBSupplierLocalHome) BeanController.getEJBLocalHome(JNDIConstants.EB_SUPPLIER_STAGING_LOCAL_BEAN,
				EBSupplierLocalHome.class.getName());
	}

	protected EBBuyerLocalHome _getBuyerLocalHome() {
		return (EBBuyerLocalHome) BeanController.getEJBLocalHome(JNDIConstants.EB_BUYER_STAGING_LOCAL_BEAN,
				EBBuyerLocalHome.class.getName());
	}

	protected long _generateSupplierPK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_PROFILE_SUPPLIER_STAGING_SEQ);
	}

	protected long _generateBuyerPK() throws CommodityException {
		return generatePK(CommodityConstant.SEQUENCE_PROFILE_BUYER_STAGING_SEQ);
	}

}
