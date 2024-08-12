/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.bus;

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
 * This session bean acts as the facade to the Entity Beans for Limit Booking actual data.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class SBLimitBookingBusManagerBean implements SessionBean
{
    /** SessionContext object */
    private SessionContext ctx;

    /**
     * Default Constructor
     */
    public SBLimitBookingBusManagerBean()
    {}	
	
	/**
	    * @see com.integrosys.cms.app.limitbooking.bus.SBLimitBookingBusManager#getLimitBooking
	    */
    public ILimitBooking getLimitBooking (long limitBookingID)
        throws LimitBookingException
    {

		EBLimitBookingHome ejbHome = getEBLimitBookingHome();
		try {

			EBLimitBooking theEjb = ejbHome.findByPrimaryKey ( new Long( limitBookingID ) );

			return theEjb.getValue ();
		}
		catch (FinderException e) {

			DefaultLogger.error (this, "", e);
			throw new LimitBookingException ("FinderException caught! " + e.toString());
		}
		catch (RemoteException e) {
			DefaultLogger.error (this, "", e);
			throw new LimitBookingException ("RemoteException caught! " + e.toString());
		}
    }    

    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.SBLimitBookingBusManager#createLimitBooking
	    */
    public ILimitBooking createLimitBooking (ILimitBooking value)
        throws LimitBookingException
    {
        if (value == null )
        	throw new LimitBookingException ("ILimitBooking is null");		

        EBLimitBookingHome ejbHome = getEBLimitBookingHome();

        try {
			EBLimitBooking theEjb = ejbHome.create (value);
			
			long verTime = theEjb.getVersionTime();
			theEjb.createDependants( value, verTime, theEjb.getLimitBookingID() );	
			
			return theEjb.getValue();	            
        }
        catch (CreateException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException ("CreateException caught! " + e.toString());
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException ("RemoteException caught! " + e.toString());
        }
		catch(Exception e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException("Caught Exception!", e);
        }
    }

    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.SBLimitBookingBusManager#updateLimitBooking
	    */
    public ILimitBooking updateLimitBooking (ILimitBooking value)
        throws LimitBookingException
    {
        if (value == null )
        	throw new LimitBookingException ("ILimitBooking is null");

        EBLimitBookingHome ejbHome = getEBLimitBookingHome();
        try {

			EBLimitBooking theEjb = ejbHome.findByPrimaryKey (new Long( value.getLimitBookingID() ) );
			theEjb.setValue ( value );

            return theEjb.getValue ();
        }
        catch (FinderException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException ("FinderException caught! " + e.toString());
        }
        catch (VersionMismatchException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException ("VersionMismatchException caught! " + e.toString(), new ConcurrentUpdateException(e.getMessage()));
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException ("RemoteException caught! " + e.toString());
        }

    }

    /**
	    * @see com.integrosys.cms.app.limitbooking.bus.SBLimitBookingBusManager#deleteLimitBooking
	    */
    public ILimitBooking deleteLimitBooking (ILimitBooking value)
        throws LimitBookingException
    {
        if (value == null )
        	throw new LimitBookingException ("ILimitBooking is null");

        EBLimitBookingHome ejbHome = getEBLimitBookingHome();

        try {
			EBLimitBooking theEjb = ejbHome.findByPrimaryKey (new Long( value.getLimitBookingID() ) );
			ILimitBooking template = theEjb.getValue();

			//do soft delete
			theEjb.delete(value);			

            return template;

        }
        catch (FinderException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException ("FinderException caught! " + e.toString());
        }
        catch (VersionMismatchException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException ("VersionMismatchException caught! " + e.toString());
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException ("RemoteException caught! " + e.toString());
        }
    }
	
	 /**
	    * @see com.integrosys.cms.app.limitbooking.bus.SBLimitBookingBusManager#successLimitBooking
	    */
    public ILimitBooking successLimitBooking (ILimitBooking value)
        throws LimitBookingException
    {
        if (value == null )
        	throw new LimitBookingException ("ILimitBooking is null");

        EBLimitBookingHome ejbHome = getEBLimitBookingHome();

        try {
			EBLimitBooking theEjb = ejbHome.findByPrimaryKey (new Long( value.getLimitBookingID() ) );
			ILimitBooking template = theEjb.getValue();

			//do soft delete
			theEjb.success(value);			

            return template;

        }
        catch (FinderException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException ("FinderException caught! " + e.toString());
        }
        catch (VersionMismatchException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException ("VersionMismatchException caught! " + e.toString());
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new LimitBookingException ("RemoteException caught! " + e.toString());
        }
    }
	
    /**
	     * Method to rollback a transaction
	     *
	     * @throws LimitBookingException on errors encountered
	     */
    protected void rollback() throws LimitBookingException
    {
        ctx.setRollbackOnly();
    }    

    /**
	     * Get home interface of EBLimitBooking.
	     *
	     * @return EBLimitBookingHome
	     * @throws LimitBookingException on errors encountered
	     */
    protected EBLimitBookingHome getEBLimitBookingHome()
        throws LimitBookingException
    {
        EBLimitBookingHome ejbHome = (EBLimitBookingHome) BeanController.getEJBHome (
            ICMSJNDIConstant.EB_LIMIT_BOOKING_JNDI, EBLimitBookingHome.class.getName());

        if (ejbHome == null)
            throw new LimitBookingException("EBLimitBookingHome is null!");

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