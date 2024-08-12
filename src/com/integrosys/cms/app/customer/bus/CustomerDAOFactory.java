/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/CustomerDAOFactory.java,v 1.4 2005/02/03 06:23:17 hshii Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This factory class will load ICustomerDAO implementations
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/02/03 06:23:17 $ Tag: $Name: $
 */
public class CustomerDAOFactory {

	public static final String FAM = "FAM";

	public static final String GAM = "GAM";

	/**
	 * Create a default customer dao implementation
	 * 
	 * @return ICustomerDAO
	 */
	public static ICustomerDAO getDAO(ITrxContext ctx) {
		if (ctx != null) {
			ITeam team = ctx.getTeam();
			String teamCode = team.getTeamType().getBusinessCode();
			//DefaultLogger.debug(CustomerDAOFactory.class,"Team Code is:"+team)
			// ;
			if (FAM.equals(teamCode)) {
				DefaultLogger.debug(CustomerDAOFactory.class, "Returning Fam DAO");
				return new FAMCustomerDAO();
			}
			else if (GAM.equals(teamCode)) {
				DefaultLogger.debug(CustomerDAOFactory.class, "Returning Gam DAO");
				return new GAMCustomerDAO();
			}
			else {
				return new CustomerDAO();
			}

		}
		// todo:throw exception. Currently return default DAO, till integration
		// is done
		return new CustomerDAO();
	}

	/**
	 * Return default DAO
	 * 
	 * @return ICustomerDAO
	 */
	public static ICustomerDAO getDAO() {
		return new CustomerDAO();
	}
}