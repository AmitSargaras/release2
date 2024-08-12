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

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * This is the remote interface to the SBCustRelationshipProxyBean
 * session bean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface SBCustRelationshipProxy extends EJBObject
{   
	// ******************** START Proxy Methods for Customer Relationship ****************

    /**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustRelationshipTrxValue
	*/
    public ICustRelationshipTrxValue getCustRelationshipTrxValue (ITrxContext ctx,
    	long parentSubProfileID )
        throws CustRelationshipException, RemoteException;
    
   /**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustRelationshipTrxValueByTrxID
	*/
    public ICustRelationshipTrxValue getCustRelationshipTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws CustRelationshipException, RemoteException;

    /**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerUpdateCustRelationship
	*/
    public ICustRelationshipTrxValue makerUpdateCustRelationship (ITrxContext ctx,
           ICustRelationshipTrxValue trxVal, ICustRelationship[] value)
    	throws CustRelationshipException, RemoteException;

    /**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerCloseCreateCustRelationship
	*/
    public ICustRelationshipTrxValue makerCloseCustRelationship (ITrxContext ctx,
           ICustRelationshipTrxValue trxVal)
           throws CustRelationshipException, RemoteException;
   
    /**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerApproveUpdateCustRelationship
	*/
    public ICustRelationshipTrxValue checkerApproveUpdateCustRelationship (
        ITrxContext ctx, ICustRelationshipTrxValue trxVal)
    	throws CustRelationshipException, RemoteException;

    /**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerRejectUpdateCustRelationship
	*/
    public ICustRelationshipTrxValue checkerRejectUpdateCustRelationship (
        ITrxContext ctx, ICustRelationshipTrxValue trxVal)
    	throws CustRelationshipException, RemoteException;

	// ******************** START Proxy Methods for Customer Shareholder ****************
		
	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustShareholderTrxValue
	*/
    public ICustShareholderTrxValue getCustShareholderTrxValue (ITrxContext ctx,
    	long parentSubProfileID )
        throws CustRelationshipException, RemoteException;
    
   /**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustShareholderTrxValueByTrxID
	*/
    public ICustShareholderTrxValue getCustShareholderTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws CustRelationshipException, RemoteException;

    /**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerUpdateCustShareholder
	*/
    public ICustShareholderTrxValue makerUpdateCustShareholder (ITrxContext ctx,
           ICustShareholderTrxValue trxVal, ICustShareholder[] value)
    	throws CustRelationshipException, RemoteException;

    /**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerCloseCreateCustRelationship
	*/
    public ICustShareholderTrxValue makerCloseCustShareholder (ITrxContext ctx,
           ICustShareholderTrxValue trxVal)
           throws CustRelationshipException, RemoteException;
   
    /**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerApproveUpdateCustShareholder
	*/
    public ICustShareholderTrxValue checkerApproveUpdateCustShareholder (
        ITrxContext ctx, ICustShareholderTrxValue trxVal)
    	throws CustRelationshipException, RemoteException;

    /**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerRejectUpdateCustShareholder
	*/
    public ICustShareholderTrxValue checkerRejectUpdateCustShareholder (
        ITrxContext ctx, ICustShareholderTrxValue trxVal)
    	throws CustRelationshipException, RemoteException;
}