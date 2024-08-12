/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/user/bus/SBUserTeamManagerBean.java,v 1.1 2005/08/08 08:23:58 dli Exp $
 */
package com.integrosys.cms.app.user.bus;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.EBTeam;
import com.integrosys.component.bizstructure.app.bus.EBTeamHome;
import com.integrosys.component.bizstructure.app.bus.EBTeamMembership;
import com.integrosys.component.bizstructure.app.bus.EBTeamMembershipHome;
import com.integrosys.component.bizstructure.app.bus.EBTeamTypeHome;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.bizstructure.app.bus.OBTeam;
import com.integrosys.component.bizstructure.app.bus.OBTeamMembership;

/**
 * This session bean provides the support for multiple-role per user (CPC &
 * CPC_Custodian), according to CR33. -- 2005/08/04 10:29:00 davidli
 * 
 * @author $Author: dli $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/08 08:23:58 $ Tag: $Name: $
 */

public class SBUserTeamManagerBean implements SessionBean {

	public SBUserTeamManagerBean() {
		sc = null;
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbActivate() throws EJBException {
	}

	public void ejbPassivate() throws EJBException {
	}

	public void ejbRemove() throws EJBException {
	}

	public void setSessionContext(SessionContext sessionContext) throws EJBException {
		sc = sessionContext;
	}

	/**
	 * Get the member team list of a given user by its UserID.
	 * 
	 * @return ITeam[]
	 */
	public ITeam[] getTeamsByUserID(long userID) throws EntityNotFoundException {
		try {
			Collection col = getTeamHome().findByUserID(new Long(userID));

			if ((col == null) || (col.size() == 0)) {
				throw new EntityNotFoundException("Entity not found");
			}

			ITeam team[] = new OBTeam[col.size()];
			int i = 0;
			for (Iterator it = col.iterator(); it.hasNext();) {
				team[i] = ((EBTeam) it.next()).getOBTeam();
				i++;
			}

			return team;
		}
		catch (FinderException fe) {
			throw new EntityNotFoundException("Entity not found", fe);
		}
		catch (RemoteException ee) {
			throw new EJBException(ee);
		}
		catch (BizStructureException e) {
			e.printStackTrace();
			throw new EJBException(e);
		}
	}

	/**
	 * Get the team membership list of a given user by its UserID.
	 * 
	 * @return ITeamMembership[]
	 */
	public ITeamMembership[] getTeamMembershipListByUserID(long userID) throws EntityNotFoundException {

		try {
			Collection col = getTeamMembershipHome().findByUserID(new Long(userID));

			if ((col == null) || (col.size() == 0)) {
				throw new EntityNotFoundException("Entity not found");
			}

			ITeamMembership teammship[] = new OBTeamMembership[col.size()];
			int i = 0;
			for (Iterator it = col.iterator(); it.hasNext();) {
				teammship[i] = ((EBTeamMembership) it.next()).getOBTeamMembership();
				i++;
			}

			return teammship;
		}
		catch (FinderException fe) {
			throw new EntityNotFoundException("Entity not found", fe);
		}
		catch (RemoteException ee) {
			throw new EJBException(ee);
		}
	}

	/**
	 * Get the remote interface home of EBTeam entity bean.
	 * 
	 * @return EBTeamHome
	 */
	protected EBTeamHome getTeamHome() throws RemoteException {
		EBTeamHome home = (EBTeamHome) BeanController.getEJBHome("EBTeamHome",
				"com.integrosys.component.bizstructure.app.bus.EBTeamHome");
		return home;
	}

	/**
	 * Get the remote interface home of EBTeamMembership entity bean.
	 * 
	 * @return EBTeamMembershipHome
	 */
	protected EBTeamMembershipHome getTeamMembershipHome() throws RemoteException {
		EBTeamMembershipHome home = (EBTeamMembershipHome) BeanController.getEJBHome("EBTeamMembershipHome",
				"com.integrosys.component.bizstructure.app.bus.EBTeamMembershipHome");
		return home;
	}

	/**
	 * Get the remote interface home of EBTeamType entity bean.
	 * 
	 * @return EBTeamTypeHome
	 */
	protected EBTeamTypeHome getTeamTypeHome() throws RemoteException {
		EBTeamTypeHome home = (EBTeamTypeHome) BeanController.getEJBHome("EBTeamTypeHome",
				"com.integrosys.component.bizstructure.app.bus.EBTeamTypeHome");
		return home;
	}

	SessionContext sc;

}
