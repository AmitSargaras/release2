package com.integrosys.cms.app.cci.bus;


import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;


public abstract class EBCCICounterpartyDetailsStagingBean extends EBCCICounterpartyDetailsBean {


    protected EBCCICounterpartyDetailsLocalHome getEBStockIndexFeedEntryLocalHome() throws CCICounterpartyDetailsException {

        EBCCICounterpartyDetailsLocalHome home = null;
        String ebHomeName = EBCCICounterpartyDetailsLocalHome.class.getName();
        String JNDIName = ICMSJNDIConstant.EB_CCI_COUNTERPARTY_DETAILS_STAGING_JNDI;
        home = (EBCCICounterpartyDetailsLocalHome) BeanController.getEJBLocalHome(JNDIName, ebHomeName);

        if (home != null) {
            return home;
        }
        throw new CCICounterpartyDetailsException("EBCCICounterpartyDetailsBeanItemLocal is null!");
    }


   public  ICCICounterpartyDetails ejbHomeGetCCICounterpartyByGroupCCINo(long groupCCINo)  throws SearchDAOException{


        try {
            
           return  CCICustomerDAOFactory.getDAO().getCCICounterpartyByGroupCCINo(groupCCINo);
         }catch(SearchDAOException  ex) {
            ex.printStackTrace();
        }
         return null;
     }



}
