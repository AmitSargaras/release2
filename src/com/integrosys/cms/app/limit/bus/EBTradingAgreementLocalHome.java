/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBTradingAgreement Entity Bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBTradingAgreementLocalHome extends EJBLocalHome {

	/**
	 * Create a local trading agreement entity bean
	 * 
	 * @param limitProfileID is the limit profile ID in long value
	 * @param value is the ITradingAgreement object
	 * @return EBTradingAgreementLocal
	 * @throws CreateException on error
	 */
	public EBTradingAgreementLocal create(long limitProfileID, ITradingAgreement value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the primary key
	 * @return EBTradingAgreementLocal
	 * @throws FinderException on error
	 */
	public EBTradingAgreementLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by limit profile ID
	 * 
	 * @param profileID is the Long value of the limit profile ID
	 * @param status the status to be excluded in this find
	 * @return Collection of EBTradingAgreementLocal
	 * @throws FinderException on error
	 */
	public Collection findByLimitProfile(Long profileID, String status) throws FinderException;
}