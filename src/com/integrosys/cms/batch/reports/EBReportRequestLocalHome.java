package com.integrosys.cms.batch.reports;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description: This is the Local Home interface for the EBReportRequest Entity
 * Bean.
 * 
 * @author $Author: ritika $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/27 03:40:41 $ Tag: $Name: $
 */

public interface EBReportRequestLocalHome extends EJBLocalHome {

	public EBReportRequestLocal create(IReportRequest value) throws CreateException;

	public EBReportRequestLocal findByPrimaryKey(Long pk) throws FinderException;

	public Collection findByStatus(String status) throws FinderException;
}
