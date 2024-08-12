package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.component.bizstructure.app.bus.ITeam;


public class CustGrpIdentifierDAOFactory {

    public static final String FAM = "FAM";
    public static final String GAM = "GAM";

    /**
     * Create a default customer dao implementation
     *
     * @return ICustGrpIdentifierDAO
     */
    public static ICustGrpIdentifierDAO getDAO(ITrxContext ctx) {
        if (ctx != null) {
            ITeam team = ctx.getTeam();
            String teamCode = team.getTeamType().getBusinessCode();
            return new CustGrpIdentifierDAO();

        }
        return new CustGrpIdentifierDAO();
    }

    /**
     * Return default DAO
     *
     * @return ICustGrpIdentifierDAO
     */
    protected static ICustGrpIdentifierDAO getDAO() {
        return new CustGrpIdentifierDAO();
    }


    public static ICustGrpIdentifierDAO getStagingDAO() throws SearchDAOException {
        return new StagingCustGrpIdentifierDAO();
    }
}
