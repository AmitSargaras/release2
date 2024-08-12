package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


public class SBCustGrpIdentifierBusManagerBean implements ICustGrpIdentifierBusManager, SessionBean {




    public ICustGrpIdentifier deleteCustGrpIdentifier(ICustGrpIdentifier grpObj) throws CustGrpIdentifierException {

             EBCustGrpIdentifierHome ejbHome = getEBCustGrpIdentifierHome();
             Long grpId = new Long(grpObj.getGrpID()) ;

             try {
                 EBCustGrpIdentifier remote = ejbHome.create(grpObj);

                 ICustGrpIdentifier latestObj = remote.getValue();

                 Long str = new Long(latestObj.getGrpID());
                 remote = ejbHome.findByPrimaryKey(str);

                 latestObj = remote.getValue();
                 return latestObj;
             } catch (FinderException e) {
                 DefaultLogger.error(this, "", e);
                 throw new CustGrpIdentifierException("CreateException", e);
             } catch (CreateException e) {
                 DefaultLogger.error(this, "", e);
                 throw new CustGrpIdentifierException("CreateException", e);
             } catch (RemoteException e) {
                 context.setRollbackOnly();
                 DefaultLogger.error(this, "", e);
                 throw new CustGrpIdentifierException("RemoteException", e);
             }
         }


    public ICustGrpIdentifier updateCustGrpIdentifier(ICustGrpIdentifier grpObj) throws CustGrpIdentifierException {

          EBCustGrpIdentifierHome ejbHome = getEBCustGrpIdentifierHome();
          Long grpId = new Long(grpObj.getGrpID()) ;

          try {
              EBCustGrpIdentifier remote = ejbHome.findByPrimaryKey(grpId);
              remote.setValue(grpObj);
              ICustGrpIdentifier latestObj = remote.getValue();

              //remote.createDependants (grpObj, remote.getVersionTime ());
              Debug("-->After  createDependants");
              latestObj = remote.getValue();
              return latestObj;
          } catch (FinderException e) {
              DefaultLogger.error(this, "", e);
              throw new CustGrpIdentifierException("CreateException", e);
          } catch (ConcurrentUpdateException e) {
              DefaultLogger.error(this, "", e);
              throw new CustGrpIdentifierException("ConcurrentUpdateException", e);
/*
          }catch (VersionMismatchException e) {
              DefaultLogger.error (this, "", e);
              rollback ();
              throw new CustGrpIdentifierException ("VersionMismatchException caught! " + e.toString (), new ConcurrentUpdateException (e.getMessage ()));
*/

          } catch (RemoteException e) {
              context.setRollbackOnly();
              DefaultLogger.error(this, "", e);
              throw new CustGrpIdentifierException("RemoteException", e);
          }
      }
           
	  
	/**
	* @see com.integrosys.cms.app.custgrpi.bus.SBCustGrpIdentifierBusManager#getCustGrpByInternalLimitType
	*/ 
	public ICustGrpIdentifier[] getCustGrpByInternalLimitType(String internalLimitType) throws CustGrpIdentifierException {

        EBCustGrpIdentifierHome ejbHome = getEBCustGrpIdentifierHome();

        try {
			  
			Iterator i = ejbHome.findByInternalLimitType ( internalLimitType ).iterator();
			ArrayList arrList = new ArrayList();
			while (i.hasNext())
			{
				EBCustGrpIdentifier theEjb = (EBCustGrpIdentifier) i.next();
				ICustGrpIdentifier value = theEjb.getValue ();
				arrList.add ( value );
			}
			return (ICustGrpIdentifier[]) arrList.toArray (new OBCustGrpIdentifier[0]);
			  			  
              			  
          } catch (FinderException e) {
              DefaultLogger.error(this, "", e);
              //ok if not found
			  return null;
          
          } catch (RemoteException e) {
              context.setRollbackOnly();
              DefaultLogger.error(this, "", e);
              throw new CustGrpIdentifierException("RemoteException", e);
          }
      }
	  
	/**
	* @see com.integrosys.cms.app.custgrpi.bus.SBCustGrpIdentifierBusManager#updateCustGrpLimitAmount
	*/ 
	public void updateCustGrpLimitAmount(ICustGrpIdentifier[] grpObjList) throws CustGrpIdentifierException {

		EBCustGrpIdentifierHome ejbHome = getEBCustGrpIdentifierHome();

		try {

			for (int i = 0; i < grpObjList.length; i++) {
				ICustGrpIdentifier grpObj = grpObjList[i];
				Long grpId = new Long(grpObj.getGrpID()) ;

				EBCustGrpIdentifier remote = ejbHome.findByPrimaryKey(grpId);
				remote.updateGroupLimitAmount(grpObj);              
			}

		} catch (FinderException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new CustGrpIdentifierException("FinderException", e);
		} catch (ConcurrentUpdateException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new CustGrpIdentifierException("ConcurrentUpdateException", e);
		} catch (RemoteException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new CustGrpIdentifierException("RemoteException", e);
		}
	}
	
	/**
	* @see com.integrosys.cms.app.custgrpi.bus.SBCustGrpIdentifierBusManager#updateCustGrpLimitAmount
	*/ 
	public void updateCustGrpLimitAmount(List grpIDList, Amount lmtAmt) throws CustGrpIdentifierException {

		EBCustGrpIdentifierHome ejbHome = getEBCustGrpIdentifierHome();
		DefaultLogger.debug (this,"************updateCustGrpLimitAmount, lmtAmt "+lmtAmt);
		DefaultLogger.debug (this,"************updateCustGrpLimitAmount, grpIDList "+grpIDList);
		try {

			for (int i = 0; i < grpIDList.size(); i++) {
				Long grpId = new Long( (String)grpIDList.get(i) ) ;							

				EBCustGrpIdentifier remote = ejbHome.findByPrimaryKey(grpId);
				ICustGrpIdentifier grpObj = remote.getValue ();
				grpObj.setGroupLmt( lmtAmt );
				remote.updateGroupLimitAmount(grpObj);              
			}

		} catch (FinderException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new CustGrpIdentifierException("FinderException", e);
		} catch (ConcurrentUpdateException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new CustGrpIdentifierException("ConcurrentUpdateException", e);
		} catch (RemoteException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new CustGrpIdentifierException("RemoteException", e);
		}
    }	  	  
	
	/**
	* @see com.integrosys.cms.app.custgrpi.bus.SBCustGrpIdentifierBusManager#getCustGrpIdentifierByGrpID
	*/ 
	public ICustGrpIdentifier getCustGrpIdentifierByGrpID( Long grpID ) throws CustGrpIdentifierException {
		Debug(" getCustGrpIdentifierByGrpID grpID " + grpID);

		try {
			EBCustGrpIdentifierHome home = getEBCustGrpIdentifierHome();
			
			EBCustGrpIdentifier remote = home.findByPrimaryKey(grpID);
			return remote.getValue();
			
		} catch (FinderException e) {
			DefaultLogger.error(this, "", e);
			throw new CustGrpIdentifierException("FinderException", e);

		} catch (Exception e) {
			
			DefaultLogger.error(this, "", e);
			throw new CustGrpIdentifierException("Other Exception", e);
		}		
    }
	
     /**
     *
     * @param obj
     * @return
     * @throws CustGrpIdentifierException
     */
     public ICustGrpIdentifier getCustGrpIdentifierByGrpID(ICustGrpIdentifier obj) throws CustGrpIdentifierException {
        Debug(" getCustGrpIdentifierByGrpID grpRefID " + obj.getGrpID());
        ICustGrpIdentifier obj1 = null;
        try {
            EBCustGrpIdentifierHome home = getEBCustGrpIdentifierHome();
            Long str = new Long(obj.getGrpID());
            EBCustGrpIdentifier remote = home.findByPrimaryKey(str);
            obj1 = remote.getValue();
        } catch (Exception e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("Other Exception", e);
        }
        return obj1;
    }

    /**
     * 
     * @param grpRefID
     * @return
     * @throws CustGrpIdentifierException
     */
     public ICustGrpIdentifier getCustGrpIdentifierByTrxIDRef(long grpRefID) throws CustGrpIdentifierException {
        Debug(" getCustGrpIdentifierByTrxIDRef grpRefID " + grpRefID);
        ICustGrpIdentifier obj = null;
        try {
            EBCustGrpIdentifierHome home = getEBCustGrpIdentifierHome();
            Long str = new Long(grpRefID);
            EBCustGrpIdentifier remote = home.findByPrimaryKey(str);
            obj = remote.getValue();
        } catch (Exception e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("Other Exception", e);
        }
        return obj;
    }


    /**
     *
     * @param grpObj
     * @return
     * @throws CustGrpIdentifierException
     */
    public ICustGrpIdentifier createCustGrpIdentifier(ICustGrpIdentifier grpObj) throws CustGrpIdentifierException {

            EBCustGrpIdentifierHome ejbHome = getEBCustGrpIdentifierHome();
            IGroupCreditGrade[] list = grpObj.getGroupCreditGrade();
             Debug("-->Before createDependants getStatus" + grpObj.getStatus() );
        try {
            EBCustGrpIdentifier remote = ejbHome.create(grpObj);
            //ICustGrpIdentifier latestObj = remote.getValue();
            ICustGrpIdentifier latestObj = remote.getValue();
            Debug("-->Before createDependants");
            remote.createDependants (grpObj, remote.getVersionTime ());
           // createGroupCreditGrades(latestObj.getGrpID(),grpObj);

            Debug("-->After  createDependants");
            latestObj = remote.getValue();
            return latestObj;
        } catch (CreateException e) {
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("CreateException", e);

        }catch (VersionMismatchException e) {
            DefaultLogger.error (this, "", e);
            rollback ();
            throw new CustGrpIdentifierException ("VersionMismatchException caught! " + e.toString (), new ConcurrentUpdateException (e.getMessage ()));

        } catch (RemoteException e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }


    /**
     * For testing Purpose
     * @param grpID
     * @param aobj
     * @throws CustGrpIdentifierException
     */

    public  void createGroupCreditGrades(long grpID, ICustGrpIdentifier aobj ) throws CustGrpIdentifierException {
       DefaultLogger.debug(this," [SBCustGrpIdentifierBusManagerBean] -->Creating  createGroupCreditGrades");
        try {
              EBGroupCreditGradeLocalHome home = getEBGroupCreditGradeLocalHome();
              IGroupCreditGrade[] list = aobj.getGroupCreditGrade();
             if (list !=null && list.length >0){
                 for (int i=0;i<list.length ;i++){
                     IGroupCreditGrade obj = list[i];
                     //obj.setGrpID(grpID);
                     EBGroupCreditGradeLocal theEJB = home.create(list[i]);
                 }
             }
        } catch (Exception e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CustGrpIdentifierException("RemoteException", e);
        }
    }

    /**
     * Used in ListCounterpartyCommand  for
     *  search customer or Group
     * @param criteria
     * @return
     * @throws CustGrpIdentifierException
     */
    public SearchResult searchEntryDetails(GroupMemberSearchCriteria criteria) throws CustGrpIdentifierException {
        try {
            EBCustGrpIdentifierHome ejbHome = getEBCustGrpIdentifierHome();
            return ejbHome.searchEntryDetails(criteria);
         } catch (RemoteException e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }
    }

    public List setEntityDetails(List list)  throws CustGrpIdentifierException {
        try {
            EBCustGrpIdentifierHome ejbHome = getEBCustGrpIdentifierHome();
            return ejbHome.setEntityDetails(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }
    }

    /**
     * user to search Group
     * @param criteria
     * @return
     * @throws CustGrpIdentifierException
     */

     public SearchResult searchGroup(CustGrpIdentifierSearchCriteria criteria) throws CustGrpIdentifierException {
        try {
            EBCustGrpIdentifierHome ejbHome = getEBCustGrpIdentifierHome();
            return ejbHome.searchGroup(criteria);
         } catch (RemoteException e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }
    }

    /**
        * Used in PrepareCustGrpIdentifierCommand  for
        *  search customer or Group
        * @param inputMap
        * @return
        * @throws CustGrpIdentifierException
        */
       public Map getGroupAccountMgrCodes(Map inputMap) throws CustGrpIdentifierException {
           try {
               EBCustGrpIdentifierHome ejbHome = getEBCustGrpIdentifierHome();
               return ejbHome.getGroupAccountMgrCodes(inputMap);
            } catch (RemoteException e) {
               e.printStackTrace();
               throw new CustGrpIdentifierException("Caught Exception!", e);
           } catch (Exception e) {
               e.printStackTrace();
               throw new CustGrpIdentifierException("Caught Exception!", e);
           }
       }


    /**
        * Used in displaying credit rating amount  for
        *  group structure creation
        * @param inputMap
        * @return
        * @throws CustGrpIdentifierException
        */
       public Amount getGroupLimit(String intLmt, String rating) throws CustGrpIdentifierException {
           try {
               EBCustGrpIdentifierHome ejbHome = getEBCustGrpIdentifierHome();
               return ejbHome.getGroupLimit(intLmt, rating);
            } catch (RemoteException e) {
               e.printStackTrace();
               throw new CustGrpIdentifierException("Caught Exception!", e);
           } catch (Exception e) {
               e.printStackTrace();
               throw new CustGrpIdentifierException("Caught Exception!", e);
           }
       }

   protected EBGroupCreditGradeLocalHome getEBGroupCreditGradeLocalHome() {
        EBGroupCreditGradeLocalHome ejbHome = (EBGroupCreditGradeLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_CREDIT_GRADE_LOCAL_JNDI, EBGroupCreditGradeLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupCreditGradeLocalHome is Null!");
        }

        return ejbHome;
    }

    // end for testing




     protected EBGroupSubLimitLocalHome getEBGroupSubLimitLocalHome() {
        EBGroupSubLimitLocalHome ejbHome = (EBGroupSubLimitLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_SUBLIMIT_LOCAL_JNDI, EBGroupSubLimitLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupSubLimitLocalHome is Null!");
        }

        return ejbHome;
    }
     protected EBGroupSubLimitLocalHome getActualEBGroupSubLimitLocalHome() {
        EBGroupSubLimitLocalHome ejbHome = (EBGroupSubLimitLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_GROUP_SUBLIMIT_LOCAL_JNDI, EBGroupSubLimitLocalHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupSubLimitLocalHome is Null!");
        }

        return ejbHome;
    }
     protected EBGroupCreditGradeHome getEBGroupCreditGradeHome() {
        EBGroupCreditGradeHome ejbHome = (EBGroupCreditGradeHome) BeanController.getEJBHome(
                ICMSJNDIConstant.EB_GROUP_CREDIT_GRADE_JNDI, EBGroupCreditGradeHome.class.getName());

        if (ejbHome == null) {
            throw new EJBException("EBGroupCreditGradeHome is Null!");
        }

        return ejbHome;
    }
     protected EBCustGrpIdentifierHome getEBCustGrpIdentifierHome() {
        return (EBCustGrpIdentifierHome) BeanController.getEJBHome(
                ICMSJNDIConstant.EB_CUST_GRP_IDENTIFIER_JNDI,
                EBCustGrpIdentifierHome.class.getName());
    }


    /**
     * SessionContext object
     */
    private SessionContext ctx;

    /**
     * Method to rollback a transaction
     *
     * @throws CustGrpIdentifierException on errors encountered
     */
    protected void rollback() throws CustGrpIdentifierException {
        ctx.setRollbackOnly();
    }


    public void ejbCreate() {
    }


    public void setSessionContext(SessionContext sessionContext) throws EJBException {
        context = sessionContext;
    }


    public void ejbRemove() throws EJBException {
    }


    public void ejbActivate() throws EJBException {
    }


    public void ejbPassivate() throws EJBException {
    }


    private SessionContext context;

      private void Debug(String msg) {
    	  DefaultLogger.debug(this,"SBCustGrpIdentifierBusManagerBean = " + msg);
    }

}
