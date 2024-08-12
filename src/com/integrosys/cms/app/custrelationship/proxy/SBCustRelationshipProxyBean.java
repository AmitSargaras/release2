/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.proxy;

import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.custrelationship.trx.*;
import com.integrosys.cms.app.custrelationship.trx.shareholder.*;
import com.integrosys.cms.app.custrelationship.bus.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.*;
import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import java.util.ArrayList;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * This session bean provides the implementation of Customer Relationship and Shareholder module.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class SBCustRelationshipProxyBean implements ICustRelationshipProxy, SessionBean
{
    /** SessionContext object */
    private SessionContext _context = null;

    /**
     * Default Constructor
     */
    public SBCustRelationshipProxyBean() {}

    /**
     * Method to rollback a transaction
     *
     * @throws CustRelationshipException on errors rolling back
     */
    protected void rollback() throws CustRelationshipException
    {
        try {
            _context.setRollbackOnly();
        }
        catch (Exception e) {
            throw new CustRelationshipException (e.toString());
        }
    }

    // ********** EJB Methods ****************
    /**
     * Called by the container to create a session bean instance. Its parameters
     * typically contain the information the client uses to customize the bean
     * instance for its use. It requires a matching pair in the bean class and
     * its home interface.
     */
    public void ejbCreate()
    {}

    /**
     * A container invokes this method before it ends the life of the session
     * object. This happens as a result of a client's invoking a remove
     * operation, or when a container decides to terminate the session object
     * after a timeout. This method is called with no transaction context.
     */
    public void ejbRemove()
    {}

    /**
     * The activate method is called when the instance is activated from its
     * 'passive' state. The instance should acquire any resource that it has
     * released earlier in the ejbPassivate() method. This method is called
     * with no transaction context.
     */
    public void ejbActivate()
    {}

    /**
     * The passivate method is called before the instance enters the 'passive'
     * state. The instance should release any resources that it can re-acquire
     * later in the ejbActivate() method. After the passivate method completes,
     * the instance must be in a state that allows the container to use the
     * Java Serialization protocol to externalize and store away the instance's
     * state. This method is called with no transaction context.
     */
    public void ejbPassivate    ()
    {}

    /**
     * Set the associated session context. The container calls this method
     * after the instance creation. The enterprise Bean instance should store
     * the reference to the context object in an instance variable.
     * This method is called with no transaction context.
     */
    public void setSessionContext (SessionContext sc) {
        _context = sc;
    }

    private ITrxValue constructTrxValue(ITrxContext ctx, ITrxValue cmsTrxValue)
    {
        if (cmsTrxValue instanceof ICustRelationshipTrxValue)
        {
            ICustRelationshipTrxValue trxValue = (ICustRelationshipTrxValue) cmsTrxValue;
			trxValue.setTransactionType(ICMSConstant.INSTANCE_CUST_RELNSHIP);
            trxValue.setTrxContext (ctx);
        } 
		else if (cmsTrxValue instanceof ICustShareholderTrxValue)
        {
            ICustShareholderTrxValue trxValue = (ICustShareholderTrxValue) cmsTrxValue;
			trxValue.setTransactionType(ICMSConstant.INSTANCE_SHAREHOLDER);
            trxValue.setTrxContext (ctx);
        }   		
        else {
            ((ICMSTrxValue)cmsTrxValue).setTrxContext(ctx);
        }
        return cmsTrxValue;
    }

	/**
	 * Helper method to operate transactions.
	 *
	 * @param trxVal is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws CustRelationshipException on errors encountered
	 */
	private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws CustRelationshipException
	{
		if (trxVal == null) {
			throw new CustRelationshipException("ITrxValue is null!");
		}

		try {
			ITrxController controller = null;

			if (trxVal instanceof ICustRelationshipTrxValue) {
				
				controller = (new CustRelationshipTrxControllerFactory()).getController(trxVal, param);			
			}
			else if (trxVal instanceof ICustShareholderTrxValue) {
				
				controller = (new CustShareholderTrxControllerFactory()).getController(trxVal, param);			
			}

			if (controller == null) {
				throw new CustRelationshipException("ITrxController is null!");
			}

			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return obj;
		}
		catch (CustRelationshipException e) {
			e.printStackTrace();
			rollback();
			throw e;
		}
		catch (TransactionException e) {
			e.printStackTrace();
			rollback();
			throw new CustRelationshipException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new CustRelationshipException("Exception caught! " + e.toString(), e);
		}
	}


    // ******************** START Proxy Methods for Customer Relationship ****************
		
	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustRelationshipTrxValue
	*/
	public ICustRelationshipTrxValue getCustRelationshipTrxValue (ITrxContext ctx,
			long parentSubProfileID )
		throws CustRelationshipException
	{		
		try {
			//get the customer transaction value which is the parent/reference of this transaction 
		    ICMSCustomerTrxValue custTrxVal =  CustomerProxyFactory.getProxy().getTrxCustomer( parentSubProfileID );
			if( custTrxVal == null )
			{
				return null;
			}			
			
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction (ICMSConstant.ACTION_READ_CUST_RELNSHIP);
			OBCustRelationshipTrxValue trxValue = new OBCustRelationshipTrxValue();
			trxValue.setTrxReferenceID ( String.valueOf( custTrxVal.getTransactionID() ) );
			return (ICustRelationshipTrxValue) operate (constructTrxValue (ctx, trxValue), param);
			
		}
		catch( CustomerException ce) 
		{
			DefaultLogger.error (this, "", ce);
            throw new CustRelationshipException ("Exception caught at getCustRelationshipTrxValue: " + ce.toString());
		}
	}

	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustRelationshipTrxValueByTrxID
	*/
	public ICustRelationshipTrxValue getCustRelationshipTrxValueByTrxID (ITrxContext ctx, String trxID)
		throws CustRelationshipException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_CUST_RELNSHIP_BY_TRXID);
		OBCustRelationshipTrxValue trxValue = new OBCustRelationshipTrxValue();
		trxValue.setTransactionID (trxID);
		return (ICustRelationshipTrxValue) operate (constructTrxValue (ctx, trxValue), param);
	}
	
	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerUpdateCustRelationship
	*/
	public ICustRelationshipTrxValue makerUpdateCustRelationship (ITrxContext ctx,
		ICustRelationshipTrxValue trxVal, ICustRelationship[] value)
		throws CustRelationshipException
	{
		try {
			if( value == null )
			{
				throw new CustRelationshipException("Customer Relationship is null");
			}
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
					
			if ( trxVal.getStatus().equals(ICMSConstant.STATE_ND) ||
				 trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {		
			
				param.setAction (ICMSConstant.ACTION_MAKER_CREATE_CUST_RELNSHIP);	
				
				//get the customer transaction value which is the parent/reference of this transaction 
				ICMSCustomerTrxValue custTrxVal = CustomerProxyFactory.getProxy().getTrxCustomer ( trxVal.getParentSubProfileID() );
				trxVal.setTrxReferenceID ( String.valueOf( custTrxVal.getTransactionID() ) );				
			}
			else
			{				
				param.setAction (ICMSConstant.ACTION_MAKER_UPDATE_CUST_RELNSHIP);
			}
			trxVal.setStagingCustRelationship (value);
			return (ICustRelationshipTrxValue) operate (constructTrxValue (ctx, trxVal), param);
			
		}
		catch( CustomerException ce) 
		{
			DefaultLogger.error (this, "", ce);
            throw new CustRelationshipException ("Exception caught at makerUpdateCustRelationship: " + ce.toString());
		}
	}
			
	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerCloseCreateCustRelationship
	*/
	public ICustRelationshipTrxValue makerCloseCustRelationship (ITrxContext ctx,
		ICustRelationshipTrxValue trxVal)
		throws CustRelationshipException
	{
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		if (trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_CUST_RELNSHIP);
		}
		else {				
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_CUST_RELNSHIP);
		}
		
		return (ICustRelationshipTrxValue) operate (constructTrxValue (ctx, trxVal), param);
	}	
	
	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerApproveUpdateCustRelationship
	*/
	public ICustRelationshipTrxValue checkerApproveUpdateCustRelationship (
		ITrxContext ctx, ICustRelationshipTrxValue trxVal)
		throws CustRelationshipException
	{
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_APPROVE_CUST_RELNSHIP);
		return (ICustRelationshipTrxValue) operate (constructTrxValue (ctx, trxVal), param);
	}
	
	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerRejectUpdateCustRelationship
	*/
	public ICustRelationshipTrxValue checkerRejectUpdateCustRelationship (
		ITrxContext ctx, ICustRelationshipTrxValue trxVal)
		throws CustRelationshipException
	{
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_REJECT_CUST_RELNSHIP);
		return (ICustRelationshipTrxValue) operate (constructTrxValue(ctx, trxVal), param);
	}		 
		
	// ******************** START Proxy Methods for Customer Shareholder ****************
		
	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustShareholderTrxValue
	*/
	public ICustShareholderTrxValue getCustShareholderTrxValue (ITrxContext ctx,
			long parentSubProfileID )
		throws CustRelationshipException
	{		
		try {
			//get the customer transaction value which is the parent/reference of this transaction 
		    ICMSCustomerTrxValue custTrxVal =  CustomerProxyFactory.getProxy().getTrxCustomer( parentSubProfileID );
			if( custTrxVal == null )
			{
				return null;
			}			
			
			OBCMSTrxParameter param = new OBCMSTrxParameter();
			param.setAction (ICMSConstant.ACTION_READ_SHAREHOLDER);
			OBCustShareholderTrxValue trxValue = new OBCustShareholderTrxValue();
			trxValue.setTrxReferenceID ( String.valueOf( custTrxVal.getTransactionID() ) );
			return (ICustShareholderTrxValue) operate (constructTrxValue (ctx, trxValue), param);
			
		}
		catch( CustomerException ce) 
		{
			DefaultLogger.error (this, "", ce);
            throw new CustRelationshipException ("Exception caught at getCustShareholderTrxValue: " + ce.toString());
		}
	}

	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#getCustShareholderTrxValueByTrxID
	*/
	public ICustShareholderTrxValue getCustShareholderTrxValueByTrxID (ITrxContext ctx, String trxID)
		throws CustRelationshipException
	{
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_READ_SHAREHOLDER_BY_TRXID);
		OBCustShareholderTrxValue trxValue = new OBCustShareholderTrxValue();
		trxValue.setTransactionID (trxID);
		return (ICustShareholderTrxValue) operate (constructTrxValue (ctx, trxValue), param);
	}
	
	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerUpdateCustShareholder
	*/
	public ICustShareholderTrxValue makerUpdateCustShareholder (ITrxContext ctx,
		ICustShareholderTrxValue trxVal, ICustShareholder[] value)
		throws CustRelationshipException
	{
		try {
			if( value == null )
			{
				throw new CustRelationshipException("Customer Shareholder is null");
			}
	        OBCMSTrxParameter param = new OBCMSTrxParameter();
					
			if ( trxVal.getStatus().equals(ICMSConstant.STATE_ND) ||
				 trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {		
			
				param.setAction (ICMSConstant.ACTION_MAKER_CREATE_SHAREHOLDER);	
				
				//get the customer transaction value which is the parent/reference of this transaction 
				ICMSCustomerTrxValue custTrxVal = CustomerProxyFactory.getProxy().getTrxCustomer ( trxVal.getParentSubProfileID() );
				trxVal.setTrxReferenceID ( String.valueOf( custTrxVal.getTransactionID() ) );				
			}
			else
			{				
				param.setAction (ICMSConstant.ACTION_MAKER_UPDATE_SHAREHOLDER);
			}
			trxVal.setStagingCustShareholder(value);
			return (ICustShareholderTrxValue) operate (constructTrxValue (ctx, trxVal), param);
			
		}
		catch( CustomerException ce) 
		{
			DefaultLogger.error (this, "", ce);
            throw new CustRelationshipException ("Exception caught at makerUpdateCustShareholder: " + ce.toString());
		}
	}
			
	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#makerCloseCreateCustRelationship
	*/
	public ICustShareholderTrxValue makerCloseCustShareholder (ITrxContext ctx,
		ICustShareholderTrxValue trxVal)
		throws CustRelationshipException
	{
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		if (trxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_SHAREHOLDER);
		}
		else {				
			param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_SHAREHOLDER);
		}
		
		return (ICustShareholderTrxValue) operate (constructTrxValue (ctx, trxVal), param);
	}	
	
	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerApproveUpdateCustShareholder
	*/
	public ICustShareholderTrxValue checkerApproveUpdateCustShareholder (
		ITrxContext ctx, ICustShareholderTrxValue trxVal)
		throws CustRelationshipException
	{
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_APPROVE_SHAREHOLDER);
		return (ICustShareholderTrxValue) operate (constructTrxValue (ctx, trxVal), param);
	}
	
	/**
	* @see com.integrosys.cms.app.custrelationship.proxy.ICustRelationshipProxy#checkerRejectUpdateCustShareholder
	*/
	public ICustShareholderTrxValue checkerRejectUpdateCustShareholder (
		ITrxContext ctx, ICustShareholderTrxValue trxVal)
		throws CustRelationshipException
	{
        OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction (ICMSConstant.ACTION_CHECKER_REJECT_SHAREHOLDER);
		return (ICustShareholderTrxValue) operate (constructTrxValue(ctx, trxVal), param);
	}	
}