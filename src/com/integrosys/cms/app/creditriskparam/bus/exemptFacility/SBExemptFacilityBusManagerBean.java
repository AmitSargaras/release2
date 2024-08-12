/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/SBExemptFacilityBusManagerBean.java,v 1.3 2003/09/02 06:58:17 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;

/**
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 *
 */
public class SBExemptFacilityBusManagerBean implements SessionBean {


    public IExemptFacilityGroup getExemptFacilityGroup()
            throws ExemptFacilityException
    {
        try {
            EBExemptFacilityHome home = getEBExemptFacilityHome();
            Collection efg = home.findAll();
            List exemptFacilityGroup = new ArrayList();
            long groupID = ICMSConstant.LONG_INVALID_VALUE;
            if (efg != null)
            for (Iterator itrEbs = efg.iterator(); itrEbs.hasNext();) {
                EBExemptFacility ebExemptFacility = (EBExemptFacility) itrEbs.next();
                // added by Jitendra to filter out the deleted record on 13th Apr 2008
                IExemptFacility  obj =   ebExemptFacility.getValue();
                 if (!ICMSConstant.STATE_DELETED.equals(obj.getStatus()))  {
                    exemptFacilityGroup.add(obj);
                    groupID = ebExemptFacility.getValue().getGroupID();
                 }

            }
            if (exemptFacilityGroup.size() > 0){
                IExemptFacilityGroup facGroup = new OBExemptFacilityGroup();
                facGroup.setExemptFacility((IExemptFacility[])exemptFacilityGroup.toArray(new IExemptFacility[0]));
                facGroup.setExemptFacilityGroupID(groupID);
                return facGroup;
            }
            else
                return null;
        } catch (RemoteException e) {
            throw new ExemptFacilityException("Exception in getExemptFacility: " + e.toString());
        } catch (FinderException e) {
            throw new ExemptFacilityException("Exception in getExemptFacility: " + e.toString());
        }
    }

    public IExemptFacilityGroup createExemptFacilityGroup(IExemptFacilityGroup details) throws ExemptFacilityException {

        DefaultLogger.debug(this,">>>>> [SBExemptFacilityBusManagerBean]  Inside createExemptFacility");
        if (details == null)
        {
            throw new ExemptFacilityException("The paramGroup to be created is null !!!");
        }
        if (details.getExemptFacility() == null)
        {
            throw new ExemptFacilityException("The paramGroup.getEntries() to be created is invalid !!!");
        }
        List list = new ArrayList();
        long groupID = getGroupID();

        try {
            DefaultLogger.debug(this,">>>>> [SBExemptFacilityBusManagerBean] --> EBExemptFacilityHome");
            EBExemptFacilityHome home = getEBExemptFacilityHome();
            if (details != null) {
                IExemptFacility[]   aIExemptFacility = details.getExemptFacility();
                if (aIExemptFacility != null && aIExemptFacility.length > 0) {
                    for (int i = 0; i < aIExemptFacility.length; i++) {
                        IExemptFacility obj = aIExemptFacility[i];
                        obj.setGroupID(groupID);
                        DefaultLogger.debug(this,">>>>> [SBExemptFacilityBusManagerBean] --> called EBExemptFacility(ejbCreate)");
                        EBExemptFacility eb = home.create(obj);
                        obj = eb.getValue();
                        groupID = eb.getValue().getGroupID();
                        list.add(obj);
                    }
                }
                if (list.size() > 0) {
                    IExemptFacility[]  IExemptFacilityList = (IExemptFacility[]) list.toArray(new IExemptFacility[0]);
                    details.setExemptFacility(IExemptFacilityList);
                    details.setExemptFacilityGroupID(groupID);
                }
            }
            return details;

        } catch (CreateException e) {
            DefaultLogger.error(this, "", e);
            throw new ExemptFacilityException(e);
        } catch (RemoteException e) {
            _context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new ExemptFacilityException(e);
        } catch (Exception e) {
            _context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new ExemptFacilityException(e);
        }
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.bus.exemptedinst.SBExemptedInstBusManager#updateExemptedInst
    */
    public IExemptFacilityGroup updateExemptFacilityGroup(IExemptFacilityGroup group)
        throws ExemptFacilityException
    {

        EBExemptFacilityHome ejbHome = getEBExemptFacilityHome();
        IExemptFacilityGroup grp = new OBExemptFacilityGroup();
        try {

            List arrList = new ArrayList();
            IExemptFacility[] value = group.getExemptFacility();
            for (int i = 0; i < value.length; i++)
            {
                IExemptFacility exemptFac = value[i];

                DefaultLogger.debug (this, " processing exemptFac with Ref : " + exemptFac.getCmsRef());

                if (ICMSConstant.LONG_INVALID_VALUE == exemptFac.getExemptFacilityID())
                {
                    DefaultLogger.debug (this, " Create Exempted Facility for Group: " + exemptFac.getExemptFacilityID());

                    EBExemptFacility theEjb = ejbHome.create ( exemptFac );

                    arrList.add ( theEjb.getValue() );
                }
                else if( exemptFac.getStatus().equals( ICMSConstant.STATE_DELETED ) )
                {
                        // Lini commented
//                    DefaultLogger.debug (this, " Delete Exempted Facility for: " + value[i].getGroupID());
//                    EBExemptFacility theEjb = ejbHome.findByPrimaryKey ( new Long( value[i].getGroupID() ) );
                    DefaultLogger.debug (this, " Delete Exempted Facility for: " + value[i].getExemptFacilityID());
                    EBExemptFacility theEjb = ejbHome.findByPrimaryKey ( new Long( value[i].getExemptFacilityID() ) );
                    //do soft delete
                    theEjb.setStatusDeleted( exemptFac );
                }
                else
                {
                    DefaultLogger.debug (this, " Update Exempted Facility for: " + value[i].getExemptFacilityID());
                    EBExemptFacility theEjb = ejbHome.findByPrimaryKey ( new Long( value[i].getExemptFacilityID() ) );
                    theEjb.setValue ( value[i] );
                    arrList.add ( theEjb.getValue() );
                }
            }
            grp.setExemptFacility((IExemptFacility[])arrList.toArray (new OBExemptFacility[0]));

            return grp ;

        }
        catch (FinderException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new ExemptFacilityException ("FinderException caught! " + e.toString());
        }
        catch (ConcurrentUpdateException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new ExemptFacilityException ("VersionMismatchException caught! " + e.toString());
        }
        catch (CreateException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new ExemptFacilityException ("CreateException caught! " + e.toString());
        }
        catch (RemoteException e) {
            DefaultLogger.error (this, "", e);
            rollback();
            throw new ExemptFacilityException ("RemoteException caught! " + e.toString());
        }
    }

    public IExemptFacilityGroup getExemptFacilityGroupByGroupID(long groupID) throws ExemptFacilityException {
     try {
            EBExemptFacilityHome ejbHome = getEBExemptFacilityHome();
            Iterator i = ejbHome.findByGroupID ( groupID ).iterator();
            IExemptFacilityGroup grp = null;//new OBExemptFacilityGroup();

            List arrList = new ArrayList();
            if (i.hasNext()){
                arrList = new ArrayList();
                grp = new OBExemptFacilityGroup();
            }
            while (i.hasNext())
            {
                EBExemptFacility theEjb = (EBExemptFacility) i.next();
				IExemptFacility ExemptFacility = theEjb.getValue ();
                if (!ICMSConstant.STATE_DELETED.equals(ExemptFacility.getStatus()))  {
                 arrList.add ( ExemptFacility );
            }
            }
            if (grp != null)
                grp.setExemptFacility((IExemptFacility[])arrList.toArray (new OBExemptFacility[0]));
            return grp;
        }
        catch (FinderException e) {
            throw new ExemptFacilityException ("FinderException caught at getExemptFacilityByGroupID " + e.toString());
        }
        catch (Exception e) {
            throw new ExemptFacilityException ("Exception caught at getExemptFacilityByGroupID " + e.toString());
        }

    }

    protected long getGroupID() {
        long groupID = ICMSConstant.LONG_INVALID_VALUE;
        try {
            groupID = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupID;
    }

    protected EBExemptFacilityHome getEBExemptFacilityHome()
    {
        DefaultLogger.debug(this,"getEbExemptFacilityHome EB_EXEMPT_FACILITY_JNDI " + ICMSJNDIConstant.EB_EXEMPT_FACILITY_JNDI);
        EBExemptFacilityHome home = (EBExemptFacilityHome) BeanController.getEJBHome(
           ICMSJNDIConstant.EB_EXEMPT_FACILITY_JNDI, EBExemptFacilityHome.class.getName());
        return home;
    }

    /**
     * Get the name of the sequence to be used for the item id
     * @return String - the name of the sequence
     */
    protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_EXEMPT_FACILITY_SEQ;
    }

    public void ejbCreate() {
    }


    public void setSessionContext(SessionContext sessionContext)
            throws EJBException {
        _context = sessionContext;
    }


    public void ejbRemove()
            throws EJBException {
    }


    public void ejbActivate()
            throws EJBException {
    }


    public void ejbPassivate()
            throws EJBException {
    }

    protected void rollback() throws ExemptFacilityException
    {
        _context.setRollbackOnly();
    }
    private SessionContext _context;

}
