/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.proxy;

import com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue;
import com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipException;

import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This interface defines the services that are available in CMS with
 * respect to the Customer Relationship and Shareholder life cycle.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision: $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface ICustRelationshipProxy
{       
	// ******************** START Proxy Methods for Customer Relationship ****************

    /**
	     * Gets the Customer Relationship trx value by parent sub profile ID.
	     *
	     * @param ctx transaction context
	     * @param parentSubProfileID parent sub profile ID
	     * @return Customer Relationship transaction value for the parent sub profile ID
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustRelationshipTrxValue getCustRelationshipTrxValue (ITrxContext ctx,
    	long parentSubProfileID )
        throws CustRelationshipException;
    
    /**
	     * Gets the Customer Relationship trx value by transaction id.
	     *
	     * @param ctx transaction context
	     * @param trxID transaction id
	     * @return Customer Relationship transaction value
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustRelationshipTrxValue getCustRelationshipTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws CustRelationshipException;

    /**
	     * Maker updates list of Customer Relationship.
	     *
	     * @param ctx transaction context
	     * @param trxVal Customer Relationship transaction value
	     * @param value list of Customer Relationship object to use for updating.
	     * @return updated Customer Relationship transaction value
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustRelationshipTrxValue makerUpdateCustRelationship (ITrxContext ctx,
           ICustRelationshipTrxValue trxVal, ICustRelationship[] value)
    	throws CustRelationshipException;

    /**
	     * Maker close Customer Relationship rejected by a checker.
	     *
	     * @param ctx transaction context
	     * @param trxVal Customer Relationship transaction value
	     * @return the updated Customer Relationship transaction value
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustRelationshipTrxValue makerCloseCustRelationship (ITrxContext ctx,
           ICustRelationshipTrxValue trxVal)
           throws CustRelationshipException;

    /**
	     * Checker approve Customer Relationship updated by a maker.
	     *
	     * @param ctx transaction context
	     * @param trxVal Customer Relationship transaction value
	     * @return the updated Customer Relationship transaction value
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustRelationshipTrxValue checkerApproveUpdateCustRelationship (
        ITrxContext ctx, ICustRelationshipTrxValue trxVal)
    	throws CustRelationshipException;

    /**
	     * Checker reject Customer Relationship updated by a maker.
	     *
	     * @param ctx transaction context
	     * @param trxVal Customer Relationship transaction value
	     * @return updated Customer Relationship transaction value
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustRelationshipTrxValue checkerRejectUpdateCustRelationship (
        ITrxContext ctx, ICustRelationshipTrxValue trxVal)
    	throws CustRelationshipException;

	// ******************** START Proxy Methods for Customer Shareholder ****************
		
	/**
	     * Gets the Customer Shareholder trx value by parent sub profile ID.
	     *
	     * @param ctx transaction context
	     * @param parentSubProfileID parent sub profile ID
	     * @return Customer Shareholder transaction value for the parent sub profile ID
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustShareholderTrxValue getCustShareholderTrxValue (ITrxContext ctx,
    	long parentSubProfileID )
        throws CustRelationshipException;
    
    /**
	     * Gets the Customer Shareholder trx value by transaction id.
	     *
	     * @param ctx transaction context
	     * @param trxID transaction id
	     * @return Customer Shareholder transaction value
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustShareholderTrxValue getCustShareholderTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws CustRelationshipException;

    /**
	     * Maker updates list of Customer Shareholder.
	     *
	     * @param ctx transaction context
	     * @param trxVal Customer Shareholder transaction value
	     * @param value list of Customer Shareholder object to use for updating.
	     * @return updated Customer Shareholder transaction value
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustShareholderTrxValue makerUpdateCustShareholder (ITrxContext ctx,
           ICustShareholderTrxValue trxVal, ICustShareholder[] value)
    	throws CustRelationshipException;

    /**
	     * Maker close Customer Shareholder rejected by a checker.
	     *
	     * @param ctx transaction context
	     * @param trxVal Customer Shareholder transaction value
	     * @return the updated Customer Shareholder transaction value
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustShareholderTrxValue makerCloseCustShareholder (ITrxContext ctx,
           ICustShareholderTrxValue trxVal)
           throws CustRelationshipException;

    /**
	     * Checker approve Customer Shareholder updated by a maker.
	     *
	     * @param ctx transaction context
	     * @param trxVal Customer Shareholder transaction value
	     * @return the updated Customer Shareholder transaction value
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustShareholderTrxValue checkerApproveUpdateCustShareholder (
        ITrxContext ctx, ICustShareholderTrxValue trxVal)
    	throws CustRelationshipException;

    /**
	     * Checker reject Customer Shareholder updated by a maker.
	     *
	     * @param ctx transaction context
	     * @param trxVal Customer Shareholder transaction value
	     * @return updated Customer Shareholder transaction value
	     * @throws CustRelationshipException on errors encountered
	     */
    public ICustShareholderTrxValue checkerRejectUpdateCustShareholder (
        ITrxContext ctx, ICustShareholderTrxValue trxVal)
    	throws CustRelationshipException;
}
