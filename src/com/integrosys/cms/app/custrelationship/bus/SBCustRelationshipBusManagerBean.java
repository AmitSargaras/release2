/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.FinderException;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.math.BigDecimal;

import java.rmi.RemoteException;

/**
 * This session bean acts as the facade to the Entity Beans for Customer Relationship actual data.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class SBCustRelationshipBusManagerBean implements SessionBean
{
    /** SessionContext object */
    private SessionContext ctx;

    /**
     * Default Constructor
     */
    public SBCustRelationshipBusManagerBean()
    {}	

	/**
	* @see com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager#getCustRelationshipByGroupID
	*/
    public ICustRelationship[] getCustRelationshipByGroupID (long groupID)
        throws CustRelationshipException
    {
        try {
            EBCustRelationshipHome ejbHome = getEBCustRelationshipHome();
            Iterator i = ejbHome.findByGroupID ( groupID ).iterator();

            ArrayList arrList = new ArrayList();
            ArrayList subProfileIDList = new ArrayList();

            while (i.hasNext())
            {
                EBCustRelationship theEjb = (EBCustRelationship) i.next();
				ICustRelationship custReln = theEjb.getValue ();
				subProfileIDList.add( new Long( custReln.getSubProfileID() ) );
                arrList.add ( custReln );
            }
			//retreive customer main information for display at UI
			if (subProfileIDList != null && subProfileIDList.size() != 0 )
			{
				Map custMap = CustomerDAOFactory.getDAO().getCustomerMainDetails( subProfileIDList );
				if(arrList.size() > 0 ) {

					for (int j=0; j<arrList.size(); j++) {
						ICustRelationship custReln = (ICustRelationship)arrList.get(j);
						custReln.setCustomerDetails( (ICMSCustomer)custMap.get( new Long( custReln.getSubProfileID() ) ) );
					}
				}
				
			}
            return (ICustRelationship[]) arrList.toArray (new OBCustRelationship[0]);
        }
        catch (FinderException e) {
            throw new CustRelationshipException ("FinderException caught at getCustRelationshipByGroupID " + e.toString());
        }
        catch (Exception e) {
            throw new CustRelationshipException ("Exception caught at getCustRelationshipByGroupID " + e.toString());
        }
    }


	/**
	* @see com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager#createCustRelationship
	*/
    public ICustRelationship[] createCustRelationship (long parentSubProfileID, ICustRelationship[] value)
        throws CustRelationshipException
    {
        if (value == null || value.length == 0)
        	throw new CustRelationshipException ("ICustRelationship[] is null");
		
		if (parentSubProfileID == ICMSConstant.LONG_INVALID_VALUE )
		{
			throw new CustRelationshipException ("ParentSubProfileID is invalid ! " );
		}
        EBCustRelationshipHome ejbHome = getEBCustRelationshipHome();

        try {
            ArrayList arrList = new ArrayList();
            long groupID = ICMSConstant.LONG_MIN_VALUE;

            for (int i = 0; i < value.length; i++) {
                OBCustRelationship custRel = new OBCustRelationship (value[i]);
                DefaultLogger.debug (this, " Create Customer Relationship for group ID: " + custRel.getGroupID() );

                custRel.setGroupID (groupID);

                EBCustRelationship theEjb = ejbHome.create (parentSubProfileID, custRel);
                custRel = (OBCustRelationship)theEjb.getValue();
                groupID = custRel.getGroupID();

                arrList.add ( custRel );
            }
            return (ICustRelationship[]) arrList.toArray (new OBCustRelationship[0]);
        }
        catch (CreateException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("CreateException caught! " + e.toString());
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("RemoteException caught! " + e.toString());
        }

    }

	/**
	* @see com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager#updateCustRelationship
	*/
    public ICustRelationship[] updateCustRelationship(long parentSubProfileID, ICustRelationship[] value)
        throws CustRelationshipException
    {
		if ( parentSubProfileID == ICMSConstant.LONG_INVALID_VALUE )
		{
			throw new CustRelationshipException ("ParentSubProfileID is invalid ! " );
		}
        EBCustRelationshipHome ejbHome = getEBCustRelationshipHome();

        try {

			ArrayList arrList = new ArrayList();
			
			for (int i = 0; i < value.length; i++)
			{
				ICustRelationship custRel = value[i];
				
				DefaultLogger.debug (this, " processing custRel: " + custRel);
				
				if (ICMSConstant.LONG_INVALID_VALUE == custRel.getCustRelationshipID())
				{
					DefaultLogger.debug (this, " Create Customer Relationship for: " + custRel.getCustRelationshipID());

					EBCustRelationship theEjb = ejbHome.create ( parentSubProfileID, custRel );
					
					arrList.add ( theEjb.getValue() );
				}
				else if( custRel.getStatus().equals( ICMSConstant.STATE_DELETED ) )
				{
					DefaultLogger.debug (this, " Delete Customer Relationship for: " + value[i].getCustRelationshipID());
					EBCustRelationship theEjb = ejbHome.findByPrimaryKey ( new Long( value[i].getCustRelationshipID() ) );
					//do soft delete
					theEjb.setStatusDeleted( custRel );					
				}
				else 
				{
					DefaultLogger.debug (this, " Update Customer Relationship for: " + value[i].getCustRelationshipID());
					EBCustRelationship theEjb = ejbHome.findByPrimaryKey ( new Long( value[i].getCustRelationshipID() ) );
					theEjb.setValue ( value[i] );
					arrList.add ( theEjb.getValue() );
				}				
            }

            return (ICustRelationship[]) arrList.toArray (new OBCustRelationship[0]);

        }
        catch (FinderException e) {

            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("FinderException caught! " + e.toString());
        }
        catch (VersionMismatchException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("VersionMismatchException caught! " + e.toString());
        }
        catch (CreateException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("CreateException caught! " + e.toString());
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("RemoteException caught! " + e.toString());
        }
    }

	/**
	* @see com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager#getCustShareholderByGroupID
	*/
    public ICustShareholder[] getCustShareholderByGroupID (long groupID)
        throws CustRelationshipException
    {
        try {
            EBCustShareholderHome ejbHome = getEBCustShareholderHome();
            Iterator i = ejbHome.findByGroupID ( groupID ).iterator();

            ArrayList arrList = new ArrayList();
            ArrayList subProfileIDList = new ArrayList();

            while (i.hasNext())
            {
                EBCustShareholder theEjb = (EBCustShareholder) i.next();
				ICustShareholder custReln = theEjb.getValue ();
				subProfileIDList.add( new Long( custReln.getSubProfileID() ) );
                arrList.add ( custReln );
            }
			//retreive customer main information for display at UI
			if (subProfileIDList != null && subProfileIDList.size() != 0 )
			{
				Map custMap = CustomerDAOFactory.getDAO().getCustomerMainDetails( subProfileIDList );
				if(arrList.size() > 0 ) {

					for (int j=0; j<arrList.size(); j++) {
						ICustShareholder custReln = (ICustShareholder)arrList.get(j);
						custReln.setCustomerDetails( (ICMSCustomer)custMap.get( new Long( custReln.getSubProfileID() ) ) );
					}
				}
				
			}
            return (ICustShareholder[]) arrList.toArray (new OBCustShareholder[0]);
        }
        catch (FinderException e) {
            throw new CustRelationshipException ("FinderException caught at getCustShareholderByGroupID " + e.toString());
        }
        catch (Exception e) {
            throw new CustRelationshipException ("Exception caught at getCustShareholderByGroupID " + e.toString());
        }
    }


	/**
	* @see com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager#createCustShareholder
	*/
    public ICustShareholder[] createCustShareholder (long parentSubProfileID, ICustShareholder[] value)
        throws CustRelationshipException
    {
        if (value == null || value.length == 0)
        	throw new CustRelationshipException ("ICustShareholder[] is null");
		
		if (parentSubProfileID == ICMSConstant.LONG_INVALID_VALUE )
		{
			throw new CustRelationshipException ("ParentSubProfileID is invalid ! " );
		}
        EBCustShareholderHome ejbHome = getEBCustShareholderHome();

        try {
            ArrayList arrList = new ArrayList();
            long groupID = ICMSConstant.LONG_MIN_VALUE;

            for (int i = 0; i < value.length; i++) {
                OBCustShareholder custRel = new OBCustShareholder (value[i]);
                DefaultLogger.debug (this, " Create Customer Shareholder for group ID: " + custRel.getGroupID() );

                custRel.setGroupID (groupID);

                EBCustShareholder theEjb = ejbHome.create (parentSubProfileID, custRel);
                custRel = (OBCustShareholder)theEjb.getValue();
                groupID = custRel.getGroupID();

                arrList.add ( custRel );
            }
            return (ICustShareholder[]) arrList.toArray (new OBCustShareholder[0]);
        }
        catch (CreateException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("CreateException caught! " + e.toString());
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("RemoteException caught! " + e.toString());
        }

    }

	/**
	* @see com.integrosys.cms.app.custrelationship.bus.SBCustRelationshipBusManager#updateCustShareholder
	*/
    public ICustShareholder[] updateCustShareholder(long parentSubProfileID, ICustShareholder[] value)
        throws CustRelationshipException
    {
		if ( parentSubProfileID == ICMSConstant.LONG_INVALID_VALUE )
		{
			throw new CustRelationshipException ("ParentSubProfileID is invalid ! " );
		}
        EBCustShareholderHome ejbHome = getEBCustShareholderHome();

        try {

			ArrayList arrList = new ArrayList();
			
			for (int i = 0; i < value.length; i++)
			{
				ICustShareholder custRel = value[i];
				
				DefaultLogger.debug (this, " processing custRel: " + custRel);
				
				if (ICMSConstant.LONG_INVALID_VALUE == custRel.getCustRelationshipID())
				{
					DefaultLogger.debug (this, " Create Customer Shareholder for: " + custRel.getCustRelationshipID());

					EBCustShareholder theEjb = ejbHome.create ( parentSubProfileID, custRel );
					
					arrList.add ( theEjb.getValue() );
				}
				else if( custRel.getStatus().equals( ICMSConstant.STATE_DELETED ) )
				{
					DefaultLogger.debug (this, " Delete Customer Shareholder for: " + value[i].getCustRelationshipID());
					EBCustShareholder theEjb = ejbHome.findByPrimaryKey ( new Long( value[i].getCustRelationshipID() ) );
					//do soft delete
					theEjb.setStatusDeleted( custRel );					
				}
				else 
				{
					DefaultLogger.debug (this, " Update Customer Shareholder for: " + value[i].getCustRelationshipID());
					EBCustShareholder theEjb = ejbHome.findByPrimaryKey ( new Long( value[i].getCustRelationshipID() ) );
					theEjb.setValue ( value[i] );
					arrList.add ( theEjb.getValue() );
				}				
            }

            return (ICustShareholder[]) arrList.toArray (new OBCustShareholder[0]);

        }
        catch (FinderException e) {

            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("FinderException caught! " + e.toString());
        }
        catch (VersionMismatchException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("VersionMismatchException caught! " + e.toString());
        }
        catch (CreateException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("CreateException caught! " + e.toString());
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new CustRelationshipException ("RemoteException caught! " + e.toString());
        }
    }
	
    /**
     * Method to rollback a transaction
     *
     * @throws CustRelationshipException on errors encountered
     */
    protected void rollback() throws CustRelationshipException
    {
        ctx.setRollbackOnly();
    }    

    /**
	     * Get home interface of EBCustRelationship.
	     *
	     * @return EBCustRelationshipHome
	     * @throws CustRelationshipException on errors encountered
	     */
    protected EBCustRelationshipHome getEBCustRelationshipHome()
        throws CustRelationshipException
    {
        EBCustRelationshipHome ejbHome = (EBCustRelationshipHome) BeanController.getEJBHome (
            ICMSJNDIConstant.EB_CUST_RELNSHIP_JNDI, EBCustRelationshipHome.class.getName());

        if (ejbHome == null)
            throw new CustRelationshipException("EBCustRelationshipHome is null!");

        return ejbHome;
    }

    /**
	     * Get home interface of EBCustShareholder.
	     *
	     * @return EBCustShareholderHome
	     * @throws CustRelationshipException on errors encountered
	     */
    protected EBCustShareholderHome getEBCustShareholderHome()
        throws CustRelationshipException
    {
        EBCustShareholderHome ejbHome = (EBCustShareholderHome) BeanController.getEJBHome (
            ICMSJNDIConstant.EB_SHAREHOLDER_JNDI, EBCustShareholderHome.class.getName());

        if (ejbHome == null)
            throw new CustRelationshipException("EBCustShareholderHome is null!");

        return ejbHome;
    }

    /**
     * Called by the container to create a session bean instance. Its parameters typically
     * contain the information the client uses to customize the bean instance for its use.
     * It requires a matching pair in the bean class and its home interface. No implementation
     * is required for this bean.
     */
    public void ejbCreate()
    {}

    /**
     * Set the associated session context. The container calls this method after
     * the instance creation. The enterprise bean instance should store the
     * reference to the context object in an instance variable. This method is
     * called with no transaction context.
     */
     public void setSessionContext (SessionContext ctx) {
         this.ctx = ctx;
     }

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
     public void ejbPassivate()
     {}

    /**
     * A container invokes this method before it ends the life of the session
     * object. This happens as a result of a client's invoking a remove operation,
     * or when a container decides to terminate the session object after a
     * timeout. This method is called with no transaction context.
     */
     public void ejbRemove()
     {}
}