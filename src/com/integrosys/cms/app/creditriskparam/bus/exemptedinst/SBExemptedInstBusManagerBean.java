/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.FinderException;
import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.rmi.RemoteException;

/**
 * This session bean acts as the facade to the Entity Beans for Exempted Institution actual data.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class SBExemptedInstBusManagerBean implements SessionBean
{
    /** SessionContext object */
    private SessionContext ctx;

    /**
     * Default Constructor
     */
    public SBExemptedInstBusManagerBean()
    {}	
/**
	* @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager#getAllExemptedInst
	*/
    public IExemptedInst[] getAllExemptedInst ()
        throws ExemptedInstException
    {
        try {
            EBExemptedInstHome ejbHome = getEBExemptedInstHome();
            Iterator i = ejbHome.findAll ().iterator();

            ArrayList arrList = new ArrayList();

            while (i.hasNext())
            {
                EBExemptedInst theEjb = (EBExemptedInst) i.next();
				IExemptedInst exemptedInst = theEjb.getValue ();
                arrList.add ( exemptedInst );
            }
			return (IExemptedInst[]) arrList.toArray (new OBExemptedInst[0]);
        }
        catch (FinderException e) {
            throw new ExemptedInstException ("FinderException caught at getAllExemptedInst " + e.toString());
        }
        catch (Exception e) {
            throw new ExemptedInstException ("Exception caught at getAllExemptedInst " + e.toString());
        }
    }
	
	/**
	* @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager#getExemptedInstByGroupID
	*/
    public IExemptedInst[] getExemptedInstByGroupID (long groupID)
        throws ExemptedInstException
    {
        try {
            EBExemptedInstHome ejbHome = getEBExemptedInstHome();
            Iterator i = ejbHome.findByGroupID ( groupID ).iterator();

            ArrayList arrList = new ArrayList();
            ArrayList subProfileIDList = new ArrayList();

            while (i.hasNext())
            {
                EBExemptedInst theEjb = (EBExemptedInst) i.next();
				IExemptedInst exemptedInst = theEjb.getValue ();
				subProfileIDList.add( new Long( exemptedInst.getCustomerID() ) );
                arrList.add ( exemptedInst );
            }
			//retreive customer main information for display at UI
			if (subProfileIDList != null && subProfileIDList.size() != 0 )
			{
				Map custMap = CustomerDAOFactory.getDAO().getCustomerMainDetails( subProfileIDList );
				if(arrList.size() > 0 ) {

					for (int j=0; j<arrList.size(); j++) {
						IExemptedInst exemptedInst = (IExemptedInst)arrList.get(j);
						ICMSCustomer custDtls = (ICMSCustomer)custMap.get( new Long( exemptedInst.getCustomerID() ) );
						ICMSLegalEntity obLegalEntity = custDtls.getCMSLegalEntity();						
						exemptedInst.setCustomerName( custDtls.getCustomerName());						
						exemptedInst.setLEReference( obLegalEntity.getLEReference() );
						exemptedInst.setCustIDSource( obLegalEntity.getSourceID() );
					}
				}
				
			}
            return (IExemptedInst[]) arrList.toArray (new OBExemptedInst[0]);
        }
        catch (FinderException e) {
            throw new ExemptedInstException ("FinderException caught at getExemptedInstByGroupID " + e.toString());
        }
        catch (Exception e) {
            throw new ExemptedInstException ("Exception caught at getExemptedInstByGroupID " + e.toString());
        }
    }

	/**
	* @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager#getExemptedInstBySubProfileID
	*/
    public IExemptedInst getExemptedInstBySubProfileID (long subProfileID)
        throws ExemptedInstException
    {
        try {
            EBExemptedInstHome ejbHome = getEBExemptedInstHome();
            EBExemptedInst theEjb = ejbHome.findBySubProfileID ( subProfileID );
           
			IExemptedInst exemptedInst = theEjb.getValue ();
			return exemptedInst;         	
           
        }
        catch (FinderException e) {
            //not found is ok
			return null;
        }
        catch (Exception e) {
            throw new ExemptedInstException ("Exception caught at getExemptedInstBySubProfileID " + e.toString());
        }
    }

	/**
	* @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager#createExemptedInst
	*/
    public IExemptedInst[] createExemptedInst (IExemptedInst[] value)
        throws ExemptedInstException
    {
        if (value == null || value.length == 0)
        	throw new ExemptedInstException ("IExemptedInst[] is null");
				
        EBExemptedInstHome ejbHome = getEBExemptedInstHome();

        try {
            ArrayList arrList = new ArrayList();
            long groupID = ICMSConstant.LONG_MIN_VALUE;

            for (int i = 0; i < value.length; i++) {
				OBExemptedInst exemptedInst = new OBExemptedInst (value[i]);
  
				exemptedInst.setGroupID (groupID);

                EBExemptedInst theEjb = ejbHome.create (exemptedInst);
                exemptedInst = (OBExemptedInst)theEjb.getValue();
                groupID = exemptedInst.getGroupID();

                arrList.add ( exemptedInst );
            }
            return (IExemptedInst[]) arrList.toArray (new OBExemptedInst[0]);
        }
        catch (CreateException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new ExemptedInstException ("CreateException caught! " + e.toString());
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new ExemptedInstException ("RemoteException caught! " + e.toString());
        }
    }

	/**
	* @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager#updateExemptedInst
	*/
    public IExemptedInst[] updateExemptedInst(IExemptedInst[] value)
        throws ExemptedInstException
    {
		
        EBExemptedInstHome ejbHome = getEBExemptedInstHome();

        try {

			ArrayList arrList = new ArrayList();
			
			for (int i = 0; i < value.length; i++)
			{
				IExemptedInst exemptedInst = value[i];
				
				DefaultLogger.debug (this, " processing exemptedInst: " + exemptedInst);
				
				if (ICMSConstant.LONG_INVALID_VALUE == exemptedInst.getExemptedInstID())
				{
					DefaultLogger.debug (this, " Create Exempted Institution for: " + exemptedInst.getExemptedInstID());

					EBExemptedInst theEjb = ejbHome.create ( exemptedInst );
					
					arrList.add ( theEjb.getValue() );
				}
				else if( exemptedInst.getStatus().equals( ICMSConstant.STATE_DELETED ) )
				{
					DefaultLogger.debug (this, " Delete Exempted Institution for: " + value[i].getExemptedInstID());
					EBExemptedInst theEjb = ejbHome.findByPrimaryKey ( new Long( value[i].getExemptedInstID() ) );
					//do soft delete
					theEjb.setStatusDeleted( exemptedInst );					
				}
				else 
				{
					DefaultLogger.debug (this, " Update Exempted Institution for: " + value[i].getExemptedInstID());
					EBExemptedInst theEjb = ejbHome.findByPrimaryKey ( new Long( value[i].getExemptedInstID() ) );
					theEjb.setValue ( value[i] );
					arrList.add ( theEjb.getValue() );
				}				
            }

            return (IExemptedInst[]) arrList.toArray (new OBExemptedInst[0]);

        }
        catch (FinderException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new ExemptedInstException ("FinderException caught! " + e.toString());
        }
        catch (VersionMismatchException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new ExemptedInstException ("VersionMismatchException caught! " + e.toString());
        }
        catch (CreateException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new ExemptedInstException ("CreateException caught! " + e.toString());
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new ExemptedInstException ("RemoteException caught! " + e.toString());
        }
    }
	
	
    /**
     * Method to rollback a transaction
     *
     * @throws ExemptedInstException on errors encountered
     */
    protected void rollback() throws ExemptedInstException
    {
        ctx.setRollbackOnly();
    }    

    /**
	     * Get home interface of EBExemptedInst.
	     *
	     * @return EBExemptedInstHome
	     * @throws ExemptedInstException on errors encountered
	     */
    protected EBExemptedInstHome getEBExemptedInstHome()
        throws ExemptedInstException
    {
        EBExemptedInstHome ejbHome = (EBExemptedInstHome) BeanController.getEJBHome (
            ICMSJNDIConstant.EB_EXEMPT_INST_JNDI, EBExemptedInstHome.class.getName());

        if (ejbHome == null)
            throw new ExemptedInstException("EBExemptedInstHome is null!");

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