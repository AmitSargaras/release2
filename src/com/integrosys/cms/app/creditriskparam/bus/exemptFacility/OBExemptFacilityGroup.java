/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/

package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.creditriskparam.exemptFacility.ExemptFacilityUIHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * OBExemptFacilityGroup
 * Purpose:
 * Description:
 *
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 *        Tag: $Name$
 */
public class OBExemptFacilityGroup implements IExemptFacilityGroup{

    private long exemptFacilityGroupID;
    private IExemptFacility[] exemptFacility;
    private long versionTime;
    public static final long serialVersionUID=1L;

    public long getExemptFacilityGroupID() {
        return exemptFacilityGroupID;
    }

    public void setExemptFacilityGroupID(long exemptFacilityGroupID) {
        this.exemptFacilityGroupID = exemptFacilityGroupID;
    }

    public IExemptFacility[] getExemptFacility() {
        return exemptFacility;
    }

    public void setExemptFacility(IExemptFacility[] exemptFacility) {
        this.exemptFacility = exemptFacility;
    }

    public long getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }

    public void addItem(IExemptFacility anItem)
    {
        int numOfItems = 0;
        IExemptFacility[] itemList = getExemptFacility();

        if (itemList != null)
        {
            numOfItems = itemList.length;
        }
        IExemptFacility[] newList = new OBExemptFacility[numOfItems + 1];
        if (itemList != null)
        {
            System.arraycopy(itemList, 0, newList, 0, itemList.length);
        }
        newList[numOfItems] = anItem;
        setExemptFacility(newList);
    }

    /*private void removeItems_back(int[] anItemIndexList)
    {
        IExemptFacility[] itemList = getExemptFacility();
        IExemptFacility[] newList = new OBExemptFacility[itemList.length - anItemIndexList.length];
        int ctr = 0;
        boolean removeFlag = false;
        if (itemList != null)
        for (int ii=0; ii<itemList.length; ii++)
        {
            for (int jj=0; jj<anItemIndexList.length; jj++)
            {
                if (ii == anItemIndexList[jj])
                {
                    removeFlag = true;
                    IExemptFacility ob = itemList[ii];
                    ob.setStatus(ICMSConstant.STATE_DELETED);
                    itemList[ii]=ob;
                    break;
                }
            }
            if (!removeFlag)
            {
                newList[ctr] = itemList[ii];
                ctr++;
            }
            removeFlag = false;
        }
//        setExemptFacility(newList);
        setExemptFacility(itemList);
    }*/

    /**
     * Added  by Jitu to  delete new  record which is deleted by user in UI
     * @param anItemIndexList
     */
     public void removeItems(int[] anItemIndexList) {
        List listJitu = new ArrayList();
        
        IExemptFacility[] itemList = getExemptFacility();
        
        ExemptFacilityUIHelper helper = new ExemptFacilityUIHelper();
        
        List sortList = helper.getSortedExemptFacilityMap(Arrays.asList(itemList));
        
        itemList = (IExemptFacility[])sortList.toArray(new IExemptFacility[] {});
        
        boolean removeFlag = false;
        if (itemList != null)
            for (int ii = 0; ii < itemList.length; ii++) {
                removeFlag = false;
                IExemptFacility existingOBJ = itemList[ii];
                for (int jj = 0; jj < anItemIndexList.length; jj++) {
                    if (ii == anItemIndexList[jj]){
                        removeFlag = true;
                        break;
                    }
                }
                if (removeFlag){
                    if (existingOBJ.getExemptFacilityID() == ICMSConstant.LONG_INVALID_VALUE){
                        //Debug(" Not Adding new records which is deleted by users " + existingOBJ.getLosSystem());
                    } else {
                        //Debug(" Existing Record is deleted by users " + existingOBJ.getLosSystem());
                        existingOBJ.setStatus(ICMSConstant.STATE_DELETED);
                        //listJitu.add(existingOBJ);
                    }
                } else {
                    //Debug(" Existing Record is added " + existingOBJ.getLosSystem());
                    listJitu.add(existingOBJ);
                }

            }
        IExemptFacility[] returnList = (IExemptFacility[]) listJitu.toArray(new IExemptFacility[0]);
        setExemptFacility(returnList);
    }

    /**
     * Helper Method to print
     * @param msg
     */

      /*private void Debug(String msg) {
        System.out.println("OBExemptFacilityGroup, [ removeItems ] = " + msg);
    }*/
}
