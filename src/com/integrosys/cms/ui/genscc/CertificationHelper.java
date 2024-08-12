package com.integrosys.cms.ui.genscc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateSummary;
import com.integrosys.cms.app.cccertificate.proxy.CCCertificateProxyManagerFactory;
import com.integrosys.cms.app.cccertificate.proxy.ICCCertificateProxyManager;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/09/14 07:38:40 $ Tag: $Name: $
 */
public class CertificationHelper {

	public static boolean verifyCCCertificate(HashMap map, String errorCode) throws SCCertificateException { // R1
																												// .5
																												// CR146
		DefaultLogger.debug(CertificationHelper.class, "Inside verifyCCCertificate()");

		try {

			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ICCCertificateProxyManager proxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();

			// how about "if (customer.getNonBorrowerInd())"
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			CCCertificateSummary[] certSummary = proxy.getCCCertificateSummaryList(theOBTrxContext, limit);

            //since partial scc and scc using the same function to do the verification, so we need a indicator to tell us is this from partial call or scc call?
            boolean partial = errorCode.equals(ICMSErrorCodes.PSCC_CCC_NOT_PERFECTED) ? true : false;

			// DefaultLogger.debug(CertificationHelper.class,
			// ">>>>>>>>>>>>>>>>>>> No of CCC:" + certSummary.length);

			// int noToGenerate = 0;
			boolean isAllow = true;
			for (int i = 0; i < certSummary.length; i++) {
                //Ignore the deleted check list
                if (certSummary[i].getCheckListStatus().equals("DELETED")) continue;

				//if (!certSummary[i].getCheckListStatus().equals("DELETED")) {
					// if(certSummary[i].getSameCountryInd()) {
                    if (partial) {//partial checking way
                        /*****not going to do any checking on CC checklist level for PSCC. The condition checking mainly depend on CC checklist document (items)*****/
					//if (!certSummary[i].allowGenerateCCC()) {
						// DefaultLogger.debug(CertificationHelper.class,
						// ">>>>>>>>>>>>>>>>>>> CCC: " +
						// certSummary[i].toString());
						// noToGenerate++;
                        /*
						SCCertificateException ex = new SCCertificateException("Error with CCC: CCC Not Perfected");
						ex.setErrorCode(errorCode);
						throw ex;
						*/
                    //    isAllow = false;
                    //    break;
					//}
                    } else {
                        //scc checking way
                        String chkListStatus = certSummary[i].getCheckListStatus();

                        if (!chkListStatus.equals(ICMSConstant.STATE_CHECKLIST_COMPLETED)) {
                            isAllow = false;
                            break;
                        }
                    }

					// }
				//}
			}

            if (isAllow) {
                //verify all the documents (checklist item) for CC
                ICheckListProxyManager _proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
                HashMap checkListItem = _proxy.getCheckListItemListbyCategory(limit.getLimitProfileID(), "CC");

                HashMap allowCheckListItemStatus = new HashMap();

                if (partial) {
                    allowCheckListItemStatus.put(ICMSConstant.STATE_ITEM_COMPLETED, null);
                    allowCheckListItemStatus.put(ICMSConstant.STATE_ITEM_WAIVED, null);

                    Iterator itr = checkListItem.values().iterator();
                    for (; itr.hasNext();) {
                        OBCheckListItem OB = (OBCheckListItem) itr.next();

                        if (!OB.getItemStatus().equals(ICMSConstant.STATE_ITEM_DEFERRED)) {
                            //if this is not DEFERRED case, is this COMPLETED | WAIVED case?

                            if (!allowCheckListItemStatus.containsKey(OB.getItemStatus())) {
                                //found a checklist item status not belong to either COMPLETED | WAIVED
                                isAllow = false;
                                break;
                            }
                        }
                    }
                } else {
                    allowCheckListItemStatus.put(ICMSConstant.STATE_ITEM_COMPLETED, null);
                    allowCheckListItemStatus.put(ICMSConstant.STATE_ITEM_WAIVED, null);
                    allowCheckListItemStatus.put(ICMSConstant.STATE_ITEM_RENEWED, null);

                    for (Iterator itr = checkListItem.values().iterator(); itr.hasNext();) {
                        OBCheckListItem item = (OBCheckListItem) itr.next();
                        if (!allowCheckListItemStatus.containsKey(item.getItemStatus())) {
                            isAllow = false;
                            break;
                        }
                    }
                }

                if (!isAllow) {
                    if (partial) {
                        SCCertificateException ex = new SCCertificateException("Error Generating PSCC as because it is not meet the condition [(COMPLETED | WAIVED) & DEFERRED]");
                        ex.setErrorCode(ICMSErrorCodes.PSCC_CONDITION_NOT_MEET);
                        throw ex;
                    } else {
                        SCCertificateException ex = new SCCertificateException("Error with CCC: CCC Not Perfected");
                        ex.setErrorCode(errorCode);
                        throw ex;
                    }
                }
            } else {
                SCCertificateException ex = new SCCertificateException("Error with CCC: CCC Not Perfected");
                ex.setErrorCode(errorCode);
                throw ex;
            }

			DefaultLogger.debug(CertificationHelper.class, ">>>>>>>>>>>>>>>>>>> CCC isAllow flag:" + isAllow);
			return isAllow;

		}
		catch (CCCertificateException e) {
			e.printStackTrace();
			SCCertificateException ex = new SCCertificateException("Error with CCC: " + e.getMessage());
			// ex.setErrorCode(ICMSErrorCodes.SCC_CCC_NOT_PERFECTED);
			ex.setErrorCode(errorCode);
			throw ex;
		}
        catch (CheckListException cle) {
            cle.printStackTrace();
            SCCertificateException ex = new SCCertificateException("Error with CCC: " + cle.getMessage());
            ex.setErrorCode(errorCode);
            throw ex;
        }

	}

}
