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

import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * This class facades the ICustRelationshipProxy implementation by delegating
 * the handling of requests to an ejb session bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class CustRelationshipProxyImpl implements ICustRelationshipProxy
{

   // ******************** Proxy methods for Customer Relationship ****************
	

    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustRelationshipTrxValue
	    */
    public ICustRelationshipTrxValue getCustRelationshipTrxValue (ITrxContext ctx,
    	long parentSubProfileID )
        throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.getCustRelationshipTrxValue (ctx, parentSubProfileID);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at getCustRelationshipTrxValue: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at getCustRelationshipTrxValue: " + e.toString());
        }
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustRelationshipTrxValueByTrxID
	    */
    public ICustRelationshipTrxValue getCustRelationshipTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.getCustRelationshipTrxValueByTrxID (ctx, trxID);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at getCustRelationshipTrxValueByTrxID: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at getCustRelationshipTrxValueByTrxID: " + e.toString());
        }
    }
    
    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerUpdateCustRelationship
	    */
    public ICustRelationshipTrxValue makerUpdateCustRelationship (ITrxContext ctx,
           ICustRelationshipTrxValue trxVal, ICustRelationship[] value)
    	throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.makerUpdateCustRelationship (ctx, trxVal, value);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at makerUpdateCustRelationship: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at makerUpdateCustRelationship: " + e.toString());
        }
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerCloseUpdateCustRelationship
	    */
    public ICustRelationshipTrxValue makerCloseCustRelationship (ITrxContext ctx,
           ICustRelationshipTrxValue trxVal) throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.makerCloseCustRelationship (ctx, trxVal);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at makerCloseCustRelationship: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at makerCloseCustRelationship: " + e.toString());
        }
    }    

    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerApproveUpdateCustRelationship
	    */
    public ICustRelationshipTrxValue checkerApproveUpdateCustRelationship (
        ITrxContext ctx, ICustRelationshipTrxValue trxVal)
    	throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.checkerApproveUpdateCustRelationship (ctx, trxVal);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at checkerApproveUpdateCustRelationship: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at checkerApproveUpdateCustRelationship: " + e.toString());
        }
    }
	
    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerRejectUpdateCustRelationship
	    */
    public ICustRelationshipTrxValue checkerRejectUpdateCustRelationship (
        ITrxContext ctx, ICustRelationshipTrxValue trxVal)
    	throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.checkerRejectUpdateCustRelationship (ctx, trxVal);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at checkerRejectUpdateCustRelationship: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at checkerRejectUpdateCustRelationship: " + e.toString());
        }
    }	 

	// ******************** Proxy methods for Customer Shareholder ****************	

    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustShareholderTrxValue
	    */
    public ICustShareholderTrxValue getCustShareholderTrxValue (ITrxContext ctx,
    	long parentSubProfileID )
        throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.getCustShareholderTrxValue (ctx, parentSubProfileID);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at getCustShareholderTrxValue: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at getCustShareholderTrxValue: " + e.toString());
        }
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustShareholderTrxValueByTrxID
	    */
    public ICustShareholderTrxValue getCustShareholderTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.getCustShareholderTrxValueByTrxID (ctx, trxID);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at getCustShareholderTrxValueByTrxID: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at getCustShareholderTrxValueByTrxID: " + e.toString());
        }
    }
    
    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerUpdateCustShareholder
	    */
    public ICustShareholderTrxValue makerUpdateCustShareholder (ITrxContext ctx,
           ICustShareholderTrxValue trxVal, ICustShareholder[] value)
    	throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.makerUpdateCustShareholder (ctx, trxVal, value);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at makerUpdateCustShareholder: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at makerUpdateCustShareholder: " + e.toString());
        }
    }

    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerCloseUpdateCustRelationship
	    */
    public ICustShareholderTrxValue makerCloseCustShareholder (ITrxContext ctx,
           ICustShareholderTrxValue trxVal) throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.makerCloseCustShareholder (ctx, trxVal);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at makerCloseCustShareholder: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at makerCloseCustShareholder: " + e.toString());
        }
    }    

    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerApproveUpdateCustShareholder
	    */
    public ICustShareholderTrxValue checkerApproveUpdateCustShareholder (
        ITrxContext ctx, ICustShareholderTrxValue trxVal)
    	throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.checkerApproveUpdateCustShareholder (ctx, trxVal);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at checkerApproveUpdateCustShareholder: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at checkerApproveUpdateCustShareholder: " + e.toString());
        }
    }
	
    /**
	    * @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerRejectUpdateCustShareholder
	    */
    public ICustShareholderTrxValue checkerRejectUpdateCustShareholder (
        ITrxContext ctx, ICustShareholderTrxValue trxVal)
    	throws CustRelationshipException
    {
        try {
            SBCustRelationshipProxy proxy = getProxy();
            return proxy.checkerRejectUpdateCustShareholder (ctx, trxVal);
        }
        catch (CustRelationshipException e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at checkerRejectUpdateCustShareholder: " + e.toString());
        }
        catch (Exception e) {
            DefaultLogger.error (this, "", e);
            throw new CustRelationshipException ("Exception caught at checkerRejectUpdateCustShareholder: " + e.toString());
        }
    }	 

    /**
     * Method to rollback a transaction. Not implemented at online proxy level.
     *
     * @throws CustRelationshipException for any errors encountered
     */
    protected void rollback() throws CustRelationshipException
    {}

    /**
     * Helper method to get ejb object of custrelationship proxy session bean.
     *
     * @return custrelationship proxy ejb object
     */
    private SBCustRelationshipProxy getProxy() throws CustRelationshipException
    {
        SBCustRelationshipProxy proxy = (SBCustRelationshipProxy) BeanController.getEJB (
            ICMSJNDIConstant.SB_CUST_RELNSHIP_PROXY_JNDI, SBCustRelationshipProxyHome.class.getName());

        if (proxy == null) {
            throw new CustRelationshipException ("SBCustRelationshipProxy is null!");
        }
        return proxy;
    }
}