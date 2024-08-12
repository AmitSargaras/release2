/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.secmaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Arrays;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.IDynamicProperty;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.OBDynamicProperty;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/03 15:49:01 $ Tag: $Name: $
 */
public class SubmitSecurityMasterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SubmitSecurityMasterCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "secTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "mandatoryID", "java.lang.String", REQUEST_SCOPE },
				{ "mandatoryDisplayID", "java.lang.String", REQUEST_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE },
				{ "entryCodesArrayList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "idList", "java.util.ArrayList", SERVICE_SCOPE }, { "subType", "java.lang.String", REQUEST_SCOPE },
				{ "categoryList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "checkBoxID", "java.lang.String", REQUEST_SCOPE },
				
				{ "withTitle", "java.lang.String", REQUEST_SCOPE },
				{ "withoutTitle", "java.lang.String", REQUEST_SCOPE },
				{ "completedProperty", "java.lang.String", REQUEST_SCOPE },
				{ "underConstruction", "java.lang.String", REQUEST_SCOPE },
				{ "usedWithoutFBR", "java.lang.String", REQUEST_SCOPE },
				{ "newWithoutFBR", "java.lang.String", REQUEST_SCOPE },
				{ "usedWithFBR", "java.lang.String", REQUEST_SCOPE },
				{ "newWithFBR", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ITemplateTrxValue secTrxVal = (ITemplateTrxValue) map.get("secTrxVal");
			String mandatoryID = (String) map.get("mandatoryID");
			String mandatoryDisplayID = (String) map.get("mandatoryDisplayID");

			ITemplate template = (ITemplate) map.get("template");
			
			String subType = (String) map.get("subType");
			//DefaultLogger.debug(this, "template in submit --->" + template);
			DefaultLogger.debug(this, "subType===============" + subType);
			//subType="CAM";
			StringTokenizer st = new StringTokenizer(mandatoryID, ",");
			HashMap hm = new HashMap();
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				hm.put(key, key);
			}
			StringTokenizer stDisplay = new StringTokenizer(mandatoryDisplayID, ",");
			HashMap hmDisplay = new HashMap();
			while (stDisplay.hasMoreTokens()) {
				String key = stDisplay.nextToken();
				hmDisplay.put(key, key);
			}
			String withTitle = (String) map.get("withTitle");
			String withoutTitle = (String) map.get("withoutTitle");
			String completedProperty = (String) map.get("completedProperty");
			String underConstruction = (String) map.get("underConstruction");
			
			String usedWithoutFBR = (String) map.get("usedWithoutFBR");
			String newWithoutFBR = (String) map.get("newWithoutFBR");
			String usedWithFBR = (String) map.get("usedWithFBR");
			String newWithFBR = (String) map.get("newWithFBR");
			
			StringTokenizer wt = new StringTokenizer(withTitle, ",");
			StringTokenizer wot = new StringTokenizer(withoutTitle, ",");
			StringTokenizer cp = new StringTokenizer(completedProperty, ",");
			StringTokenizer uc = new StringTokenizer(underConstruction, ",");
			
			StringTokenizer nfbr = new StringTokenizer(newWithFBR, ",");
			StringTokenizer nofbr = new StringTokenizer(newWithoutFBR, ",");
			StringTokenizer ufbr = new StringTokenizer(usedWithFBR, ",");
			StringTokenizer uofbr = new StringTokenizer(usedWithoutFBR, ",");
			
			HashMap wtHM = new HashMap();
			HashMap wotHM = new HashMap();
			HashMap cpHM = new HashMap();
			HashMap ucHM = new HashMap();
			
			HashMap nfbrHM = new HashMap();
			HashMap nofbrHM = new HashMap();
			HashMap ufbrHM = new HashMap();
			HashMap uofbrHM = new HashMap();
			
			while (wt.hasMoreTokens()) {
				String key = wt.nextToken();
				wtHM.put(key, key);
			}
			
			while (wot.hasMoreTokens()) {
				String key = wot.nextToken();
				wotHM.put(key, key);
			}
			
			while (cp.hasMoreTokens()) {
				String key = cp.nextToken();
				cpHM.put(key, key);
			}
			
			while (uc.hasMoreTokens()) {
				String key = uc.nextToken();
				ucHM.put(key, key);
			}
			
			while (nfbr.hasMoreTokens()) {
				String key = nfbr.nextToken();
				nfbrHM.put(key, key);
			}
			
			while (nofbr.hasMoreTokens()) {
				String key = nofbr.nextToken();
				nofbrHM.put(key, key);
			}
			
			while (ufbr.hasMoreTokens()) {
				String key = ufbr.nextToken();
				ufbrHM.put(key, key);
			}
			
			while (uofbr.hasMoreTokens()) {
				String key = uofbr.nextToken();
				uofbrHM.put(key, key);
			}

			ITemplateItem temp[] = template.getTemplateItemList();
			if (temp != null) {
				for (int i = 0; i < temp.length; i++) {
					if (hm.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setIsMandatoryInd(true);
					}
					else {
						template.getTemplateItemList()[i].setIsMandatoryInd(false);
					}
					if (hmDisplay.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setIsMandatoryDisplayInd(true);
					}
					else {
						template.getTemplateItemList()[i].setIsMandatoryDisplayInd(false);
					}
					if (wtHM.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setWithTitle(true);
					}
					else {
						template.getTemplateItemList()[i].setWithTitle(false);
					}
				
					if (wotHM.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setWithoutTitle(true);
					}
					else {
						template.getTemplateItemList()[i].setWithoutTitle(false);
					}
				
					if (cpHM.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setPropertyCompleted(true);
					}
					else {
						template.getTemplateItemList()[i].setPropertyCompleted(false);
					}
				
					if (ucHM.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setUnderConstruction(true);
					}
					else {
						template.getTemplateItemList()[i].setUnderConstruction(false);
					}
					if (ufbrHM.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setUsedWithFBR(true);
					}					
					else {
						template.getTemplateItemList()[i].setUsedWithFBR(false);
					}
					
					if (uofbrHM.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setUsedWithoutFBR(true);
					}					
					else {
						template.getTemplateItemList()[i].setUsedWithoutFBR(false);
					}
					
					if (nofbrHM.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setNewWithoutFBR(true);
					}					
					else {
						template.getTemplateItemList()[i].setNewWithoutFBR(false);
					}
					
					if (nfbrHM.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setNewWithFBR(true);
					}					
					else {
						template.getTemplateItemList()[i].setNewWithFBR(false);
					}
				}
			}
			//DefaultLogger.debug(this, "template in after madtory flag set submit --->" + template);

			if (subType.equalsIgnoreCase(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
				String checkBoxID = (String) map.get("checkBoxID");
				ArrayList idList = (ArrayList) map.get("idList");
				ArrayList categoryList = (ArrayList) map.get("categoryList");
				StringTokenizer comlun = new StringTokenizer(checkBoxID, "%");
				ArrayList comlunList = new ArrayList();
				int a = 0;
				while (comlun.hasMoreTokens()) {
					String comlunValue = comlun.nextToken();
					comlunList.add(a, comlunValue);
					a++;
				}
				ArrayList allValueList = new ArrayList();
				for (int i = 0; i < comlunList.size(); i++) {
					ArrayList setValue = new ArrayList();
					String stringValue = (String) comlunList.get(i);
					StringTokenizer qq = new StringTokenizer(stringValue, ",");
					while (qq.hasMoreTokens()) {
						String stringID = qq.nextToken();
						setValue.add(stringID);
					}
					allValueList.add(i, setValue);
				}

                // DefaultLogger.debug(this, "checkBoxID===============" + checkBoxID);

                ArrayList entryCodesList = (ArrayList) map.get("entryCodesArrayList");

                //parse the allValueList first - dump into a hashmap
                //structure of hashmap = [itemID, hash map of dynPropEntry(key), dynamicPropObject(value)]
                HashMap dynPropIDList = new HashMap();
                for(int i=0; i<allValueList.size(); i++) {
                    String dynPropEntryCode = (String)entryCodesList.get(i);
                    String dynPropCategory = (String)categoryList.get(i);
                    long dynPropSetupID = Long.parseLong((String) idList.get(i));

                    ArrayList dynPropSelectedItemList =(ArrayList) allValueList.get(i);
                    for(int b=0; b<dynPropSelectedItemList.size(); b++) {
                        String itemID = (String)dynPropSelectedItemList.get(b);
                        HashMap dynPropEntryList = (dynPropIDList.get(itemID) == null) ? new HashMap() :
                                                        (HashMap)dynPropIDList.get(itemID);

                        IDynamicProperty dynamicObject = new OBDynamicProperty();
                        dynamicObject.setPropertyCategory(dynPropCategory);
                        dynamicObject.setPropertyValue(dynPropEntryCode);
                        dynamicObject.setPropertySetupID(dynPropSetupID);

                        dynPropEntryList.put(dynPropEntryCode, dynamicObject);
                        dynPropIDList.put(itemID, dynPropEntryList);
                    }
                }

                //DefaultLogger.debug(this, ">>>>>>>>>>>>>>> dynPropIDList \n " + dynPropIDList);
                for (int i = 0; i < temp.length; i++) {
                    mergeDynProp(temp[i], (HashMap)dynPropIDList.get(String.valueOf(i)));
                }

                // ArrayList list1 = new ArrayList();
//				if (temp != null) {
//					for (int i = 0; i < temp.length; i++) {
//						int k = 0;
//						ArrayList list2 = new ArrayList();
//						for (int m = 0; m < allValueList.size(); m++) {
//							HashMap ww = new HashMap();
//							ArrayList listToMap = (ArrayList) allValueList.get(m);
//							if (listToMap != null) {
//								for (int n = 0; n < listToMap.size(); n++) {
//									ww.put(listToMap.get(n), listToMap.get(n));
//								}
//							}
//							IDynamicProperty dynamicObject = new OBDynamicProperty();
//							if (ww.containsKey(String.valueOf(i))) {
//								dynamicObject.setPropertyCategory((String)categoryList.get(m));
//								dynamicObject.setPropertyValue((String) entryCodesList.get(m));
//								dynamicObject.setPropertySetupID(Long.parseLong((String) idList.get(m)));
//                                // propertyList[k] = dynamicObject;
//								list2.add(k, dynamicObject);
//								k++;
//							}
//						}
//						if (k != 0) {
//							IDynamicProperty[] propertyList = new IDynamicProperty[k];
//							for (int n = 0; n < list2.size(); n++) {
//								propertyList[n] = (IDynamicProperty) list2.get(n);
//							}
//							template.getTemplateItemList()[i].getItem().setPropertyList(propertyList);
//						}
//					}
//				}
			}

			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory
					.getCheckListTemplateProxyManager();
			if (secTrxVal == null) {
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>CREATING");
				secTrxVal = proxy.makerCreateTemplate(ctx, template);
			}
			else {
				ITemplateItem temp1[] = template.getTemplateItemList();
				if (temp1 != null) {
					for (int i = 0; i < temp1.length; i++) {
						if (hm.containsKey(String.valueOf(i))) {
							template.getTemplateItemList()[i].setIsMandatoryInd(true);
						}
						else {
							template.getTemplateItemList()[i].setIsMandatoryInd(false);
						}
						if (hmDisplay.containsKey(String.valueOf(i))) {
							template.getTemplateItemList()[i].setIsMandatoryDisplayInd(true);
						}
						else {
							template.getTemplateItemList()[i].setIsMandatoryDisplayInd(false);
						}
						if (wtHM.containsKey(String.valueOf(i))) {
							template.getTemplateItemList()[i].setWithTitle(true);
						}
						else {
							template.getTemplateItemList()[i].setWithTitle(false);
						}
					
						if (wotHM.containsKey(String.valueOf(i))) {
							template.getTemplateItemList()[i].setWithoutTitle(true);
						}
						else {
							template.getTemplateItemList()[i].setWithoutTitle(false);
						}
					
						if (cpHM.containsKey(String.valueOf(i))) {
							template.getTemplateItemList()[i].setPropertyCompleted(true);
						}
						else {
							template.getTemplateItemList()[i].setPropertyCompleted(false);
						}
					
						if (ucHM.containsKey(String.valueOf(i))) {
							template.getTemplateItemList()[i].setUnderConstruction(true);
						}
						else {
							template.getTemplateItemList()[i].setUnderConstruction(false);
						}
                        if (ufbrHM.containsKey(String.valueOf(i))) {
                            template.getTemplateItemList()[i].setUsedWithFBR(true);
                        }
                        else {
                            template.getTemplateItemList()[i].setUsedWithFBR(false);
                        }

                        if (uofbrHM.containsKey(String.valueOf(i))) {
                            template.getTemplateItemList()[i].setUsedWithoutFBR(true);
                        }
                        else {
                            template.getTemplateItemList()[i].setUsedWithoutFBR(false);
                        }

                        if (nofbrHM.containsKey(String.valueOf(i))) {
                            template.getTemplateItemList()[i].setNewWithoutFBR(true);
                        }
                        else {
                            template.getTemplateItemList()[i].setNewWithoutFBR(false);
                        }

                        if (nfbrHM.containsKey(String.valueOf(i))) {
                            template.getTemplateItemList()[i].setNewWithFBR(true);
                        }
                        else {
                            template.getTemplateItemList()[i].setNewWithFBR(false);
                        }
					}
				}

				for (int z = 0; z < template.getTemplateItemList().length; z++) {
					DefaultLogger.debug(this, template.getTemplateItemList()[z].getItem());
				}
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>CREATING");
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>Updating");
				secTrxVal = proxy.makerUpdateTemplate(ctx, secTrxVal, template);
			}
			resultMap.put("request.ITrxValue", secTrxVal);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}


    private void mergeDynProp(ITemplateItem templateItem, HashMap newDynPropMap) {

        //remove all
        if(newDynPropMap == null || newDynPropMap.size() == 0) {
            templateItem.getItem().setPropertyList(null);
            return;
        }

        IDynamicProperty[] currDynPropList = templateItem.getItem().getPropertyList();
        if(currDynPropList == null || currDynPropList.length == 0) {
            ArrayList newDynPropList = new ArrayList(newDynPropMap.values());
            templateItem.getItem().setPropertyList((IDynamicProperty[])newDynPropList.toArray(new IDynamicProperty[0]));
        } else {

            ArrayList mergedDynPropList = new ArrayList(Arrays.asList(currDynPropList));
            for(int i=0; i<mergedDynPropList.size(); i++) {
                IDynamicProperty dynProp = (IDynamicProperty)mergedDynPropList.get(i);
                String key = dynProp.getPropertyValue();
                if(newDynPropMap.get(key) == null) {
                    mergedDynPropList.remove(dynProp);  //existing item - to be deleted
                    i--;
                } else {
                    newDynPropMap.remove(key);      //existing update, reuse existing
                }
            }

            //new items, not in existing - to add
            mergedDynPropList.addAll(newDynPropMap.values());
            templateItem.getItem().setPropertyList((IDynamicProperty[])mergedDynPropList.toArray(new IDynamicProperty[0]));
        }
    }
    
}
