/*
 * Copyright Integro Technologies Pte Ltd
 * $header$
 */
package com.integrosys.cms.ui.feed.mutualfunds.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.diff.CompareOBException;
import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.mutualfunds.MutualFundsCommand;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class CompareMutualFundsListCommand extends MutualFundsCommand {
	
	public static final String OB_MODIFIED;

    public static final String OB_UNMODIFIED;

    public static final String OB_ADDED;

    public static final String OB_DELETED;
    
    /**
     * Constants that is specified in ofa.properties.
     */
    public static final String CSS_NAME_MODIFIED = "cms.css.modified";

    public static final String CSS_NAME_UNMODIFIED = "cms.css.unmodified";

    public static final String CSS_NAME_ADDED = "cms.css.added";

    public static final String CSS_NAME_DELETED = "cms.css.deleted";
    
    
    /**
     * static block to initialize final variables by getting values from
     * ofa.properties using Property manager.
     */

    static {
        OB_MODIFIED = PropertyManager.getValue(CSS_NAME_MODIFIED);
        OB_UNMODIFIED = PropertyManager.getValue(CSS_NAME_UNMODIFIED);
        OB_ADDED = PropertyManager.getValue(CSS_NAME_ADDED);
        OB_DELETED = PropertyManager.getValue(CSS_NAME_DELETED);
    }
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IMutualFundsFeedGroupTrxValue value = (IMutualFundsFeedGroupTrxValue) map.get("mutualFundsFeedGroupTrxValue");

		int offset = ((Integer) map.get("offset")).intValue();
		int length = ((Integer) map.get("length")).intValue();

		IMutualFundsFeedGroup actualFeedGroup = value.getMutualFundsFeedGroup();
		IMutualFundsFeedGroup stagingFeedGroup = value.getStagingMutualFundsFeedGroup();

		if ((actualFeedGroup != null) && (actualFeedGroup.getFeedEntries() != null)) {
			DefaultLogger.debug(this, "actual:");
			for (int i = 0; i < actualFeedGroup.getFeedEntries().length; i++) {
				DefaultLogger.debug(this, "buy rate " + i + " = "
						+ String.valueOf(actualFeedGroup.getFeedEntries()[i].getCurrentNAV()));
				DefaultLogger.debug(this, "ref " + i + " = "
						+ String.valueOf(actualFeedGroup.getFeedEntries()[i].getMutualFundsFeedEntryRef()));
			}
		}
		if ((stagingFeedGroup != null) && (stagingFeedGroup.getFeedEntries() != null)) {
			DefaultLogger.debug(this, "staging:");
			for (int i = 0; i < stagingFeedGroup.getFeedEntries().length; i++) {
				DefaultLogger.debug(this, "buy rate " + i + " = "
						+ String.valueOf(stagingFeedGroup.getFeedEntries()[i].getCurrentNAV()));
				DefaultLogger.debug(this, "ref " + i + " = "
						+ String.valueOf(stagingFeedGroup.getFeedEntries()[i].getMutualFundsFeedEntryRef()));
			}
		}

		try {
			List compareResultsList = CompareOBUtil.compOBArray(stagingFeedGroup.getFeedEntries(), actualFeedGroup
					.getFeedEntries());

			offset = MutualFundsListMapper.adjustOffset(offset, length, compareResultsList.size());
			resultMap.put("compareResultsList", compareResultsList);
			resultMap.put("mutualFundsFeedGroupTrxValue", value);
			resultMap.put("offset", new Integer(offset));

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] {// Produce the comparision results list.
				{ "compareResultsList", "java.util.List", SERVICE_SCOPE }, // Produce
																			// the
																			// trx
																			// value
																			// nevertheless
																			// .
				{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}
	
	public static List compOBArrayMF(IMutualFundsFeedEntry a1[], IMutualFundsFeedEntry[] a2) throws CompareOBException {
        // a1 staging and a2 is original
        ArrayList resultList = new ArrayList();
        CompareResult cr;
        //Andy Wong, 2 Dec 2008: Map for actual ref ID and object
        Map actualRefIdMap = new HashMap();

        if ((a1 == null) && (a2 == null)) {
            throw new CompareOBException("Both input params cannot be null..");
        }
        if ((a1 == null) && (a2 != null)) {
            for (int i = 0; i < a2.length; i++) {
                if (a2[i] == null) {
                    throw new CompareOBException("Input Params (Obj Array) should not contain null elemnets..");
                }
                cr = new CompareResult(a2[i], OB_DELETED);
                cr.setDeleted(true);
                resultList.add(cr);
            }
            return resultList;
        }
        if (a2 == null) {
            for (int i = 0; i < a1.length; i++) {
                if (a1[i] == null) {
                    throw new CompareOBException("Input Params (Obj Array) should not contain null elemnets..");
                }
                cr = new CompareResult(a1[i], OB_ADDED);
                cr.setAdded(true);
                resultList.add(cr);
            }
            return resultList;
        } else {
            //Andy Wong, 2 Dec 2008: set actual ref in Map instead of looping n*n times
            for (int i = 0; i < a2.length; i++) {
                /*Object o = a2[i];
                Object obj = a2[i].getMutualFundsFeedEntryRef();
                actualRefIdMap.put(obj, o);
            }
        }*/

        //Andy Wong, 2 Dec 2008: optimize objects Id comparison using Map
        for (int j = 0; j < a1.length; j++) {
            if (Long.toString(a2[i].getMutualFundsFeedEntryRef()).equals(Long.toString(a1[j].getMutualFundsFeedEntryRef()))) {
              //  IMutualFundsFeedEntry OB2 = a1[i].getMutualFundsFeedEntryRef();
                //if (castorComp(a1[i], OB2)) {
                if(StringUtils.equals(Long.toString(a1[j].getMutualFundsFeedEntryRef()), Long.toString(a2[i].getMutualFundsFeedEntryRef()))){
                    cr = new CompareResult(a1[j], OB_UNMODIFIED);
                    cr.setUnmodified(true);
                    resultList.add(cr);
                } else {
                    cr = new CompareResult(a1[j], OB_MODIFIED);
                    cr.setModified(true);
                    cr.setOriginal(a1[j]);
                    resultList.add(cr);
                }
                //remove key-object map once it is processed
              //  actualRefIdMap.remove(a1[j].getMutualFundsFeedEntryRef());
            } else {
                cr = new CompareResult(a1[j], OB_ADDED);
                cr.setAdded(true);
                resultList.add(cr);
            }
        }
            }
        } 

        for (Iterator iterator = actualRefIdMap.values().iterator(); iterator.hasNext();) {
            Object delOB = iterator.next();
            cr = new CompareResult(delOB, OB_DELETED);
            cr.setDeleted(true);
            resultList.add(cr);
        }

        return resultList;
    }
	
	/**
     * This method compares two given objects of same type using castor maps..
     */
    private static boolean castorComp(IMutualFundsFeedEntry a1, IMutualFundsFeedEntry a2) throws CompareOBException {
        if (!StringUtils.equals(Long.toString(a1.getMutualFundsFeedEntryRef()), Long.toString(a1.getMutualFundsFeedEntryRef()))) {
            return false;
        } else {
            return true;
        }
    }
}
