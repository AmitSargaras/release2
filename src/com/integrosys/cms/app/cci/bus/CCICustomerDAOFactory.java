package com.integrosys.cms.app.cci.bus;

import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.base.businfra.search.SearchDAOException;

/**
* This factory class will load ICustomerDAO implementations
*
* @author  $Author: hshii $
* @version $Revision: 1.4 $
* @since   $Date: 2005/02/03 06:23:17 $
* Tag:     $Name:  $
*/
public class CCICustomerDAOFactory {

    public static final String FAM  = "FAM";
    public static final String GAM  = "GAM";
    /**
    * Create a default customer dao implementation
    *
    * @return ICustomerDAO
    */
    public static ICCICustomerDAO getDAO(ITrxContext ctx) {
        if ( ctx != null ) {
           ITeam team = ctx.getTeam();
            String teamCode = team.getTeamType().getBusinessCode();
            //DefaultLogger.debug(CustomerDAOFactory.class,"Team Code is:"+team);
          return new CCICustomerDAO();

        }
        // todo:throw exception. Currently return default DAO, till integration is done
         return new CCICustomerDAO();
    }
    /**
    * Return default DAO
    *
    * @return ICCICustomerDAO
    */
    public static ICCICustomerDAO getDAO() {
        return new CCICustomerDAO();
    }



    public static ICCICustomerDAO getStagingDAO() throws SearchDAOException
    {
        return new StagingCCICustomerDAO();
    }
}
