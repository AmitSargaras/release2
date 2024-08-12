package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SBCCICounterpartyDetailsBusManagerStagingBean extends SBCCICounterpartyDetailsBusManagerBean {

    protected long groupCCINo = ICMSConstant.LONG_INVALID_VALUE;

    /**
     * @param details
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */

    public ICCICounterpartyDetails createCCICounterpartyDetails(ICCICounterpartyDetails details) throws CCICounterpartyDetailsException {

        List list = new ArrayList();

        try {

            long groupCCINo1 = ICMSConstant.LONG_INVALID_VALUE;
            long groupCCINoRef = getGroupCCIRefID();

            EBCCICounterpartyDetailsHome home = getEBCCICounterpartyDetailsHome();
            if (details != null) {
                ICCICounterparty[]   aICCICounterparty = details.getICCICounterparty();
                if (aICCICounterparty != null && aICCICounterparty.length > 0) {
                     setGroupCCINo(aICCICounterparty);
                    for (int i = 0; i < aICCICounterparty.length; i++) {
                        ICCICounterparty obj = aICCICounterparty[i];
                        obj.setGroupCCINoRef(groupCCINoRef);
                        EBCCICounterpartyDetails eb = home.create(obj);
                        obj = eb.getValue();
                        groupCCINo1 = eb.getGroupCCINo();
                        groupCCINoRef = eb.getGroupCCINoRef();
                        list.add(obj);
                    }

                }

                if (list.size() > 0) {
                    ICCICounterparty[]  iCCICounterpartyList = (ICCICounterparty[]) list.toArray(new ICCICounterparty[0]);
                    details.setICCICounterparty(iCCICounterpartyList);
                    details.setGroupCCINo(groupCCINo1);
                    //details.setGroupCCINoRef(groupCCINoRef);
                    details.setStagingGroupCCINoRef(groupCCINoRef);
                }
            }


            return details;

        } catch (CreateException e) {
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("CreateException", e);
        } catch (RemoteException e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("RemoteException", e);
        } catch (Exception e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("Other Exception", e);
        }
    }



    /**
     * @param groupCCINo
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */

    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINo(long groupCCINo) throws CCICounterpartyDetailsException {
        long groupCCINo1 = ICMSConstant.LONG_INVALID_VALUE;
        try {
            ICCICounterpartyDetails counterPartyDetails = new OBCCICounterpartyDetails();
            ICCICounterparty[]  iCCICounterparty = null;
            EBCCICounterpartyDetailsHome home = getEBCCICounterpartyDetailsHome();
            Collection list = home.findByGroupCCINo(groupCCINo);

            List iCCICounterpartyList = new ArrayList();
            if (list != null) {
                Iterator itrExp = list.iterator();
                while (itrExp.hasNext()) {
                    EBCCICounterpartyDetails theEjb = (EBCCICounterpartyDetails) itrExp.next();
                    ICCICounterparty aICCICounterparty = theEjb.getValue();
                    groupCCINo1=aICCICounterparty.getGroupCCINo();
                    iCCICounterpartyList.add(aICCICounterparty);
                }
                if (iCCICounterpartyList.size() > 0) {
                    iCCICounterparty = (ICCICounterparty[]) iCCICounterpartyList.toArray(new ICCICounterparty[0]);
                    counterPartyDetails = new OBCCICounterpartyDetails();
                    counterPartyDetails.setICCICounterparty(iCCICounterparty);
                    counterPartyDetails.setGroupCCINo(groupCCINo1);
                }
            }

            return counterPartyDetails;

        } catch (Exception e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("Other Exception", e);
        }

    }


    /**
     * @param groupCCINoRef
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */


    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINoRef(long groupCCINoRef) throws CCICounterpartyDetailsException {
        long groupCCINo1 = ICMSConstant.LONG_INVALID_VALUE;
        try {
            ICCICounterpartyDetails counterPartyDetails = new OBCCICounterpartyDetails();
            ICCICounterparty[]  iCCICounterparty = null;
            EBCCICounterpartyDetailsHome home = getEBCCICounterpartyDetailsHome();
            Collection list = home.findByByGroupCCINoRef(groupCCINoRef);

            List iCCICounterpartyList = new ArrayList();
            if (list != null) {
                Iterator itrExp = list.iterator();
                while (itrExp.hasNext()) {
                    EBCCICounterpartyDetails theEjb = (EBCCICounterpartyDetails) itrExp.next();
                    ICCICounterparty aICCICounterparty = theEjb.getValue();
                    groupCCINo1=aICCICounterparty.getGroupCCINo();
                    iCCICounterpartyList.add(aICCICounterparty);
                }
                if (iCCICounterpartyList.size() > 0) {
                    iCCICounterparty = (ICCICounterparty[]) iCCICounterpartyList.toArray(new ICCICounterparty[0]);
                    counterPartyDetails = new OBCCICounterpartyDetails();
                    counterPartyDetails.setICCICounterparty(iCCICounterparty);
                    //counterPartyDetails.setGroupCCINoRef(groupCCINoRef);
                    counterPartyDetails.setStagingGroupCCINoRef(groupCCINoRef);
                    counterPartyDetails.setGroupCCINo(groupCCINo1);
                }
            }
            //  return CCICustomerDAOFactory.getStagingDAO().getCCICounterpartyByGroupCCINoRef(groupCCINoRef);

            return counterPartyDetails;

        } catch (Exception e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("Other Exception", e);
        }

    }

    private void setGroupCCINo(ICCICounterparty[] aICCICounterparty) {
        long grpCCNo = ICMSConstant.LONG_INVALID_VALUE;
        try {
            for (int i = 0; i < aICCICounterparty.length; i++) {
                ICCICounterparty obj = aICCICounterparty[i];
                if (obj.getGroupCCINo() != ICMSConstant.LONG_INVALID_VALUE) {
                    grpCCNo = obj.getGroupCCINo();
                }
            }
            if (grpCCNo == ICMSConstant.LONG_INVALID_VALUE) {
                grpCCNo = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
            }

            for (int i = 0; i < aICCICounterparty.length; i++) {
                ICCICounterparty obj = aICCICounterparty[i];
                obj.setGroupCCINo(grpCCNo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @return long
     */

    protected long getGroupCCINo() {
        try {
            if (groupCCINo == ICMSConstant.LONG_INVALID_VALUE) {
                groupCCINo = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupCCINo;
    }

    /**
     * @return String
     */
    protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_CCI_NO;
    }

    /**
     * @return long
     */

    protected long getGroupCCIRefID() {
        long groupCCIRefID = ICMSConstant.LONG_INVALID_VALUE;
        try {
            groupCCIRefID = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CCI_REF_ID_SEQ_STAGING, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groupCCIRefID;
    }

    /**
     * @return EBCCICounterpartyDetailsHome
     */


    protected EBCCICounterpartyDetailsHome getEBCCICounterpartyDetailsHome() {
        return (EBCCICounterpartyDetailsHome) BeanController.getEJBHome(
                ICMSJNDIConstant.EB_CCI_COUNTERPARTY_DETAILS_STAGING_JNDI,
                EBCCICounterpartyDetailsHome.class.getName());
    }


    public void ejbCreate() {
    }


    public void setSessionContext(SessionContext sessionContext)
            throws EJBException {
        context = sessionContext;
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


    private SessionContext context;
}
