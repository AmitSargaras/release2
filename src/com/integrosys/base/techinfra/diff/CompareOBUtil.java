/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.base.techinfra.diff;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreation;

import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This is the Utility Class used to compare to objects.
 *
 * @author Ravi Vegiraju
 * @version 0.1
 * @since 2003/06/12 $Date: 2003/06/12
 */
public class CompareOBUtil {

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
     * This method is used to compare a specific member of two similar objects
     * based on the field name parameter passed. the field name is resolved to
     * the subsiquent method name. The method is invoked using the java
     * reflexion. And the return type objects will be compared for equality.
     * castor maps will be used to compare if the objects are other then java
     * objects. If return type is Arrays are Collections, the will be traversed
     * recursively to the depth to compare for the equality
     *
     * @param o1        object to be compared.
     * @param o2        object to be compared with.
     * @param fieldName
     * @return boolean value true if the compared field of the two objects is
     *         same and false otherwise.
     * @throws CompareOBException thrown when any exception occurs during the
     *                            processing
     */
    public static boolean compOB(Object o1, Object o2, String fieldName) throws CompareOBException {
        // ******* object o1 is original and o2 is staging ************ //
        if ((fieldName == null) || fieldName.equals("")) {
            throw new CompareOBException("Input Param field name should not be null..");
        }
        if ((o1 == null) && (o2 == null)) {
            // throw new
            // CompareOBException("Input Param objects should not be null..");
            return true; // since both are == null
        }
        if ((o1 == null) && (o2 != null)) {
            if (doesMethodReturnsNull(o2, fieldName) == true) {
                return true;
            } else {
                return false;
            }
        }
        if ((o1 != null) && (o2 == null)) {
            if (doesMethodReturnsNull(o1, fieldName) == true) {
                return true;
            } else {
                return false;
            }
        }
        if ((o1 != null) && (o2 != null)) {
            if (!o1.getClass().getName().equals(o2.getClass().getName())) {
                throw new CompareOBException("Input Param objects are not the same type of objects..");
            }
        }
        /*
           * if ((o1 != null && o2 == null) || (o1 == null && o2 != null)) {
           * return false; }
           */
        Class cls = o1.getClass();
        String methodName = resolveMethodName(fieldName);
        Method[] methods = cls.getMethods();
        Method method = null;
        boolean isMethodFound = false;
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                method = methods[i];
                isMethodFound = true;
                break;
            }
        }
        if (!isMethodFound) {
            throw new CompareOBException("The method corresponding to the input field name is not found..");
        }
        try {
            Object obj1 = method.invoke(o1, (Object[])null);
            Object obj2 = method.invoke(o2, (Object[])null);
            return compOB(obj1, obj2);
        }
        catch (IllegalAccessException e) {
            // e.printStackTrace();
            throw new CompareOBException(
                    "caught IllegalAccessException while invoking the object method using reflection" + e.getMessage());
        }
        catch (InvocationTargetException e) {
            // e.printStackTrace();
            throw new CompareOBException(
                    "caught InvocationTargetException while invoking the object method using reflection"
                            + e.getMessage());
        }
    }

    /**
     * This method is used to compare a specific member of two similar objects.
     * The field name is resolved to the subsiquent method name.
     * The method is invoked using the java reflexion. And the return type
     * objects will be compared for equality. castor maps will be used to compare
     * if the objects are other then java objects.
     * If return type is Arrays are Collections, the will be traversed
     * recursively to the depth to compare for the equality
     *
     * @param o1        object to be compared.
     * @param o2        object to be compared with.
     * @param excludeField        array list of the field to exclude compare.
     * @return boolean value true if the compared field of the two objects is
     *         same and false otherwise.
     * @throws CompareOBException thrown when any exception occurs during the
     *                            processing
     */
    public static boolean compOBObj(Object o1, Object o2, String[] excludeField) throws CompareOBException {
        if ((o1 == null) && (o2 == null)) {
            // throw new
            // CompareOBException("Input Param objects should not be null..");
            return true; // since both are == null
        }
        if ((o1 != null) && (o2 != null)) {
            if (!o1.getClass().getName().equals(o2.getClass().getName())) {
                throw new CompareOBException("Input Param objects are not the same type of objects..");
            }
        }
        /*
           * if ((o1 != null && o2 == null) || (o1 == null && o2 != null)) {
           * return false; }
           */
        if (o1.getClass().isArray()) {
            if (Array.getLength(o1) != Array.getLength(o2)) {
                // return false if array lengths are not equal
                return false;
            }
            boolean tmpFlg = true;
            for (int j = 0; j < Array.getLength(o1); j++) {
                if (!compOB(Array.get(o1, j), Array.get(o2, j))) {
                    tmpFlg = false;
                    return false;
                } else {
                    tmpFlg = true;
                }
            }
            return tmpFlg;
        }else{
            Class cls = o1.getClass();
            Method[] methods = cls.getMethods();
            Object obj1 = null;
            Object obj2 = null;
            boolean different = true;
            boolean exField = false;
            for (int i = 0; i < methods.length; i++) {
                String mName = methods[i].getName();
                if (mName.substring(0, 3).equals("get")) {
                    if(excludeField != null){
                        for(int j=0; j<excludeField.length; j++){
                            if(mName.equals(excludeField[j])){
                                exField = true;
                                break;
                            }else{
                                exField = false;
                            }
                        }
                    }
                    if(!exField){
                        try {
                            obj1 = methods[i].invoke(o1, (Object[])null);
                            obj2 = methods[i].invoke(o2, (Object[])null);
                            different = compOB(obj1, obj2);
                            if(!different){
                                break;
                            }

                        }
                        catch (IllegalAccessException e) {
                            // e.printStackTrace();
                            throw new CompareOBException(
                                    "caught IllegalAccessException while invoking the object method using reflection" + e.getMessage());
                        }
                        catch (InvocationTargetException e) {
                            // e.printStackTrace();
                            throw new CompareOBException(
                                    "caught InvocationTargetException while invoking the object method using reflection"
                                            + e.getMessage());
                        }
                    }
                }
            }
            return different;
        }
    }

    /**
     * This method is used to compare staging values (modified records) with the
     * original values (existing records) The two object arrays are compared for
     * changed/unchanged/added/deleted records. Every object to be compared
     * should have 2 castor map xmls, one to compare id's and another to compare
     * objects castor id maps are used to map the element in one array againest
     * the element in another array. castor maps are then used to compare the
     * objects
     *
     * @param a1 object[] is staging array, usually contains the modifiyed
     *           values that are pending approval.
     * @param a2 object[] is original array, usually contains the values before
     *           modifications
     * @return CompareResult value true if the compared field of the two objects
     *         is same and false otherwise.
     * @throws CompareOBException thrown when any exception occurs during the
     *                            processing
     */
    public static List compOBArray(Object[] a1, Object[] a2) throws CompareOBException {
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
                Object o = a2[i];
                actualRefIdMap.put(getIdHash(o), o);
            }
        }

        //Andy Wong, 2 Dec 2008: optimize objects Id comparison using Map
        for (int i = 0; i < a1.length; i++) {
            if (actualRefIdMap.containsKey(getIdHash(a1[i]))) {
                Object OB2 = actualRefIdMap.get(getIdHash(a1[i]));
                if (castorComp(a1[i], OB2)) {
                    cr = new CompareResult(a1[i], OB_UNMODIFIED);
                    cr.setUnmodified(true);
                    resultList.add(cr);
                } else {
                    cr = new CompareResult(a1[i], OB_MODIFIED);
                    cr.setModified(true);
                    cr.setOriginal(OB2);
                    resultList.add(cr);
                }
                //remove key-object map once it is processed
                actualRefIdMap.remove(getIdHash(a1[i]));
            } else {
                cr = new CompareResult(a1[i], OB_ADDED);
                cr.setAdded(true);
                resultList.add(cr);
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
   
    public static List compOBArrayCheckList(Object[] a1, Object[] a2) throws CompareOBException {
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
                Object o = a2[i];
                actualRefIdMap.put(getIdHash(o), o);
            }
        }

        //Andy Wong, 2 Dec 2008: optimize objects Id comparison using Map
        for (int i = 0; i < a1.length; i++) {
            if (actualRefIdMap.containsKey(getIdHash(a1[i]))) {
                Object OB2 = actualRefIdMap.get(getIdHash(a1[i]));
                ICheckListItem stage=(OBCheckListItem)a1[i];
                ICheckListItem actual=(OBCheckListItem)OB2;
               if( !stage.getDocumentStatus().equals(actual.getDocumentStatus())){
            	   cr = new CompareResult(a1[i], OB_MODIFIED);
                   cr.setModified(true);
                   cr.setOriginal(OB2);
                   resultList.add(cr);
               }
               else if (castorComp(a1[i], OB2)) {                	
                    cr = new CompareResult(a1[i], OB_UNMODIFIED);
                    cr.setUnmodified(true);
                    resultList.add(cr);
                } else {
                    cr = new CompareResult(a1[i], OB_MODIFIED);
                    cr.setModified(true);
                    cr.setOriginal(OB2);
                    resultList.add(cr);
                }
                //remove key-object map once it is processed
                actualRefIdMap.remove(getIdHash(a1[i]));
            } else {
                cr = new CompareResult(a1[i], OB_ADDED);
                cr.setAdded(true);
                resultList.add(cr);
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

    public static List compOBArrayFD(Object[] a1, Object[] a2) throws CompareOBException {
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
                Object o = a2[i];
                actualRefIdMap.put(getIdHash(o), o);
            }
        }

        //Andy Wong, 2 Dec 2008: optimize objects Id comparison using Map
        for (int i = 0; i < a1.length; i++) {
            if (actualRefIdMap.containsKey(getIdHash(a1[i]))) {
            	// Show the difference in colour at UI level.
            	//This will compare actual & stage object.
                Object OB2 = actualRefIdMap.get(getIdHash(a1[i]));
                ICashDeposit stage=(OBCashDeposit)a1[i];
                ICashDeposit actual=(OBCashDeposit)OB2;
                ILienMethod[] stageLean= stage.getLien();
                ILienMethod[] actualLean= actual.getLien();
                List res=new ArrayList();
                if(null!=stageLean && null!=actualLean){
                 res= compOBArrayLien(stageLean, actualLean);
                }
               String stgFinWare=stage.getFinwareId();
               String actualFinWare=actual.getFinwareId();
               String stgVerification="";
               String actualVerification="";
               SimpleDateFormat date=new SimpleDateFormat("ddMMyyyy");
               if(null!=stage.getVerificationDate()){
               stgVerification=date.format(stage.getVerificationDate());
               }if(null!=actual.getVerificationDate()){
               actualVerification=date.format(actual.getVerificationDate());
               }
               if(null==stgFinWare ||"".equals(stgFinWare)){
            	   stgFinWare="";
               }
               if(null==actualFinWare ||"".equals(actualFinWare)){
            	   actualFinWare="";
               }
               if(null==stgVerification ||"".equals(stgVerification)){
            	   stgVerification="";
               }
               if(null==actualVerification ||"".equals(actualVerification)){
            	   actualVerification="";
               }
             /*   if(!stage.toString().equals(actual.toString())){
                	 cr = new CompareResult(a1[i], OB_MODIFIED);
                     cr.setModified(true);
                     cr.setOriginal(OB2);
                     resultList.add(cr);
                }
                else if(stage.toString().equals(actual.toString())){
                	  cr = new CompareResult(a1[i], OB_UNMODIFIED);
                      cr.setUnmodified(true);
                      resultList.add(cr);
               	   }*/              
                
                
                if( !String.valueOf(stage.getDepositeInterestRate()).equals(String.valueOf(actual.getDepositeInterestRate()))){
             	   cr = new CompareResult(a1[i], OB_MODIFIED);
                    cr.setModified(true);
                    cr.setOriginal(OB2);
                    resultList.add(cr);
                }else if( !stage.getSystemName().equals(actual.getSystemName())){
              	   cr = new CompareResult(a1[i], OB_MODIFIED);
                   cr.setModified(true);
                   cr.setOriginal(OB2);
                   resultList.add(cr);
               } else if( !stage.getActive().equals(actual.getActive())){
              	   cr = new CompareResult(a1[i], OB_MODIFIED);
                   cr.setModified(true);
                   cr.setOriginal(OB2);
                   resultList.add(cr);
               }
                else if( !stage.getSystemId().equals(actual.getSystemId())){
              	   cr = new CompareResult(a1[i], OB_MODIFIED);
                   cr.setModified(true);
                   cr.setOriginal(OB2);
                   resultList.add(cr);
               }
                else if( !stgFinWare.equals(actualFinWare)){
               	   cr = new CompareResult(a1[i], OB_MODIFIED);
                    cr.setModified(true);
                    cr.setOriginal(OB2);
                    resultList.add(cr);
                }
                else if( !stgVerification.equals(actualVerification)){
                	   cr = new CompareResult(a1[i], OB_MODIFIED);
                     cr.setModified(true);
                     cr.setOriginal(OB2);
                     resultList.add(cr);
                 }
                /*else if(actual.getFinwareId()!=null && !"".equals(actual.getFinwareId())){
            	   if(stage.getFinwareId()==null ||"".equals(stage.getFinwareId())){
            		   cr = new CompareResult(a1[i], OB_MODIFIED);
                       cr.setModified(true);
                       cr.setOriginal(OB2);
                       resultList.add(cr);
            	   }
            	   else if( !stage.getFinwareId().equals(actual.getFinwareId())){               
	              	   cr = new CompareResult(a1[i], OB_MODIFIED);
	                   cr.setModified(true);
	                   cr.setOriginal(OB2);
	                   resultList.add(cr);
            	   }
            	   else //(stage.getFinwareId().equals(actual.getFinwareId()))
            	   {               
            		   cr = new CompareResult(a1[i], OB_UNMODIFIED);
                       cr.setUnmodified(true);
                       resultList.add(cr);
                	   }
               }else if((stage.getFinwareId()!=null ||!"".equals(stage.getFinwareId()))&&
            		   (actual.getFinwareId()==null || "".equals(actual.getFinwareId()))){
            	   cr = new CompareResult(a1[i], OB_MODIFIED);
                   cr.setModified(true);
                   cr.setOriginal(OB2);
                   resultList.add(cr);
            	   
               }*/
                else if(!res.isEmpty()){
                	boolean modifychk=false;
                	boolean unmodifychk=false;
               	 for(int f=0;f<res.size();f++){
               		CompareResult rel=(CompareResult)res.get(f);
               		if(rel.isAdded()||rel.isDeleted()||rel.isModified()){
               		 /*cr = new CompareResult(a1[i], OB_MODIFIED);
                     cr.setModified(true);
                     cr.setOriginal(OB2);
                     resultList.add(cr);*/
               			modifychk=true;
               		}else{               			
               		 /*cr = new CompareResult(a1[i], OB_UNMODIFIED);
                     cr.setUnmodified(true);
                     resultList.add(cr);*/
               			unmodifychk=true;
               		}
               		
               	 }
               	 if(modifychk){
               		cr = new CompareResult(a1[i], OB_MODIFIED);
                    cr.setModified(true);
                    cr.setOriginal(OB2);
                    resultList.add(cr);
               	 }else if(unmodifychk && !modifychk){
               		cr = new CompareResult(a1[i], OB_UNMODIFIED);
                    cr.setUnmodified(true);
                    resultList.add(cr);
               	 }
               }
                else if (castorComp(a1[i], OB2)) {
                    cr = new CompareResult(a1[i], OB_UNMODIFIED);
                    cr.setUnmodified(true);
                    resultList.add(cr);
                }
                else {
                    cr = new CompareResult(a1[i], OB_MODIFIED);
                    cr.setModified(true);
                    cr.setOriginal(OB2);
                    resultList.add(cr);
                }
                //remove key-object map once it is processed
                actualRefIdMap.remove(getIdHash(a1[i]));
            } else {
                cr = new CompareResult(a1[i], OB_ADDED);
                cr.setAdded(true);
                resultList.add(cr);
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
    public static List compOBArrayCase(Object[] a1, Object[] a2) throws CompareOBException {
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
                ICaseCreation o =(ICaseCreation) a2[i];
                actualRefIdMap.put(String.valueOf(o.getChecklistitemid()),o);
            }
        }

        //Andy Wong, 2 Dec 2008: optimize objects Id comparison using Map
        for (int i = 0; i < a1.length; i++) {
        	 ICaseCreation o =(ICaseCreation) a1[i];
            if (actualRefIdMap.containsKey(String.valueOf(o.getChecklistitemid()))) {
            	ICaseCreation OB2 =(ICaseCreation) actualRefIdMap.get(String.valueOf(o.getChecklistitemid()));
                if (OB2.getStatus().equalsIgnoreCase(o.getStatus())) {
                    cr = new CompareResult(a1[i], OB_UNMODIFIED);
                    cr.setUnmodified(true);
                    resultList.add(cr);
                } else {
                    cr = new CompareResult(a1[i], OB_MODIFIED);
                    cr.setModified(true);
                    cr.setOriginal(OB2);
                    resultList.add(cr);
                }
                //remove key-object map once it is processed
                actualRefIdMap.remove(String.valueOf(o.getChecklistitemid()));
            } else {
                cr = new CompareResult(a1[i], OB_ADDED);
                cr.setAdded(true);
                resultList.add(cr);
            }
        }

        

        return resultList;
    }
    /**
     * This method resolves the method name based on the field name pased using
     * the standared java getter convention
     */
    private static String resolveMethodName(String s) {
        return "get" + (s.substring(0, 1)).toUpperCase() + s.substring(1);
    }

    /**
     * This method compares two arrays
     */
    private static boolean compArrayType(Object o1, Object o2) throws CompareOBException {
        if (Array.getLength(o1) != Array.getLength(o2)) {
            // return false if array lengths are not equal
            return false;
        }
        boolean tmpFlg = false;
        for (int j = 0; j < Array.getLength(o1); j++) {
            if (!compOB(Array.get(o1, j), Array.get(o2, j))) {
                tmpFlg = false;
                return false;
            } else {
                tmpFlg = true;
            }
        }
        return tmpFlg;
    }

    /**
     * This method compares two Lists
     */
    private static boolean compListType(Object o1, Object o2) throws CompareOBException {
        if (((List) o1).size() != ((List) o2).size()) {
            // return false if size of Lists are not equal
            return false;
        }
        boolean tmpFlg = false;
        for (int j = 0; j < ((List) o1).size(); j++) {
            if (!compOB(((List) o1).get(j), ((List) o2).get(j))) {
                tmpFlg = false;
                return false;
            } else {
                tmpFlg = true;
            }
        }
        return tmpFlg;
    }

    /**
     * This method deligates to the proper private method based on the object
     * type..
     */
    private static boolean compOB(Object o1, Object o2) throws CompareOBException {
        if ((o1 == null) && (o2 == null)) {
            return true;
        }

        if (isStringAndEquals(o1, o2)) {
            return true;
        }

        if (((o1 == null) && (o2 != null)) || ((o1 != null) && (o2 == null))) {
            return false;
        }
        if (!o1.getClass().getName().equals(o2.getClass().getName())) {
            return false;
        }
        Package pkg = o1.getClass().getPackage();
        if (o1.getClass().isArray()) {
            return compArrayType(o1, o2);
        } else if (isListType(o1)) {
            return compListType(o1, o2);
        } else if ((pkg != null) && pkg.getName().startsWith("java.")) {
            return compJavaType(o1, o2);
        } else {
            return castorComp(o1, o2);
        }
    }

    /**
     * This method compares java type objects only
     */
    private static boolean compJavaType(Object o1, Object o2) {
        if (!o1.equals(o2)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * This Method checks whether the given object is extended form
     * java.util.AbstractList.
     */
    private static boolean isListType(Object obj) throws CompareOBException {
        try {
            Class c = Class.forName("java.util.AbstractList");
            if (c.isInstance(obj)) {
                return true;
            } else {
                return false;
            }
        }
        catch (ClassNotFoundException e) {
            // e.printStackTrace();
            throw new CompareOBException("caught ClassNotFoundException while lookimg for java.util.AbstractList"
                    + e.getMessage());
        }
    }

    /**
     * This method compares two given objects of same type using castor maps..
     */
    private static boolean castorComp(Object o1, Object o2) throws CompareOBException {
        if (!StringUtils.equals(getHash(o1), getHash(o2))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method compares two object id's based on the spacified castor id xml
     * maps...
     */
//	private static boolean castorIdComp(Object o1, Object o2) throws CompareOBException {
//		if (!getIdHash(o1).equals(getIdHash(o2))) {
//			return false;
//		}
//		else {
//			return true;
//		}
//	}

    /**
     * This method gets the xml string of the given object using castor..
     */
    private static String getHash(Object o) throws CompareOBException {
        return MapBasedXMLGenerator.getInstance().getXMLString(o);
    }

    /**
     * This method gets the id xml string of the given object using castor..
     */
    private static String getIdHash(Object o) throws CompareOBException {
        return MapBasedXMLGenerator.getInstance().getIdXMLString(o);
    }

    /**
     * Helper method
     */
    private static boolean doesMethodReturnsNull(Object o1, String fieldName) throws CompareOBException {
        Class cls = o1.getClass();
        String methodName = resolveMethodName(fieldName);
        Method[] methods = cls.getMethods();
        Method method = null;
        boolean isMethodFound = false;
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                method = methods[i];
                isMethodFound = true;
                break;
            }
        }
        if (!isMethodFound) {
            throw new CompareOBException("The method corresponding to the input field name is not found..");
        }
        try {
            Object obj1 = method.invoke(o1, (Object[])null);
            if (null == obj1) {
                return true;
            } else {
                if (obj1 instanceof java.lang.Boolean) {
                    if (((Boolean) obj1).booleanValue() == false) {
                        return true;
                    }
                } else if (obj1 instanceof java.lang.Character) {
                    if (((Character) obj1).charValue() == 'z') {// todo wont
                        // work for char
                        // z
                        return true;
                    }
                } else if (obj1 instanceof java.lang.Byte) {
                    if (((Byte) obj1).byteValue() == 0) {
                        return true;
                    }
                } else if (obj1 instanceof java.lang.Short) {
                    if (((Short) obj1).shortValue() == 0) {
                        return true;
                    }
                } else if (obj1 instanceof java.lang.Integer) {
                    if (((Integer) obj1).intValue() == 0) {
                        return true;
                    }
                } else if (obj1 instanceof java.lang.Long) {
                    if (((Long) obj1).longValue() == 0) {
                        return true;
                    }
                } else if (obj1 instanceof java.lang.Float) {
                    if (((Float) obj1).floatValue() == 0.0) {
                        return true;
                    }
                } else if (obj1 instanceof java.lang.Double) {
                    if (((Double) obj1).doubleValue() == 0.0) {
                        return true;
                    }
                } else if (obj1 instanceof java.lang.String) {
                    if (((String) obj1).trim().equals("")) {
                        return true;
                    }
                    if (((String) obj1).trim().equalsIgnoreCase("null")) {
                        return true;
                    }
                }
                return false;
            }
        }
        catch (IllegalAccessException e) {
            // e.printStackTrace();
            throw new CompareOBException(
                    "caught IllegalAccessException while invoking the object method using reflection" + e.getMessage());
        }
        catch (InvocationTargetException e) {
            // e.printStackTrace();
            throw new CompareOBException(
                    "caught InvocationTargetException while invoking the object method using reflection"
                            + e.getMessage());
        }
    }

    private static boolean isStringAndEquals(Object o1, Object o2) {
        if (o1 == null) {
            if (o2 == null) {
                return true;
            } else if (o2 instanceof String) {
                if (((String) o2).equalsIgnoreCase("null")) {
                    return true;
                }
                if (((String) o2).trim().equalsIgnoreCase("")) {
                    return true;
                }
            }
        } else {
            if (o1 instanceof String) {
                if (o2 == null) {
                    if (((String) o1).equalsIgnoreCase("null")) {
                        return true;
                    }
                    if (((String) o1).trim().equalsIgnoreCase("")) {
                        return true;
                    }
                } else if (o2 instanceof String) {
                    if (((String) o1).trim().equalsIgnoreCase(((String) o2).trim())) {
						return true;
					}
				}
			}
		}
		return false;
	}
    
    public static List compOBArrayLien(Object[] a1, Object[] a2) throws CompareOBException {
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
                ILienMethod o =(ILienMethod) a2[i];
                actualRefIdMap.put(String.valueOf(o.getActualLienId()),o);
            }
        }

        //Andy Wong, 2 Dec 2008: optimize objects Id comparison using Map
        boolean actualArray=false;
        for (int i = 0; i < a1.length; i++) {
        	
        	 ILienMethod o =(ILienMethod) a1[i];
        	 if(o.getActualLienId()!=0){
            if (actualRefIdMap.containsKey(String.valueOf(o.getActualLienId()))) {
            	ILienMethod OB2 =(ILienMethod) actualRefIdMap.get(String.valueOf(o.getActualLienId()));
                boolean unModified= true;
                if(null!=OB2.getLienNumber() && null!=o.getLienNumber()){
	                if(!OB2.getLienNumber().equalsIgnoreCase(o.getLienNumber())){
	                	unModified = false ;
	                }
                }
          
	                if(!String.valueOf(OB2.getLienAmount()).equalsIgnoreCase(String.valueOf(o.getLienAmount()))){
	                	unModified = false ;
	                }
            
                if(null!=OB2.getSerialNo() && null!=o.getSerialNo()){
	                if(!OB2.getSerialNo().equalsIgnoreCase(o.getSerialNo())){
	                	unModified = false ;
	                }
                }
                if((o.getRemark()!=null && !"".equals(o.getRemark()))&& (OB2.getRemark()!=null && !"".equals(OB2.getRemark()))){
	                if(!OB2.getRemark().equalsIgnoreCase(o.getRemark())){
	                	unModified = false ;
	                }
                }
                             
            	
            	if (unModified) {
                    cr = new CompareResult(a1[i], OB_UNMODIFIED);
                    cr.setUnmodified(true);
                    resultList.add(cr);
                } else {
                    cr = new CompareResult(a1[i], OB_MODIFIED);
                    cr.setModified(true);
                    cr.setOriginal(OB2);
                    resultList.add(cr);
                }
                //remove key-object map once it is processed
                actualRefIdMap.remove(String.valueOf(o.getActualLienId()));
                actualArray=true;
            } else {
                cr = new CompareResult(a1[i], OB_ADDED);
                cr.setAdded(true);
                resultList.add(cr);
            }
        }else{
        	 cr = new CompareResult(a1[i], OB_UNMODIFIED);
             cr.setUnmodified(true);
             resultList.add(cr);
        	
        }
            
            
        }
        if(actualArray){
        if(!actualRefIdMap.isEmpty()){
        	
        	Iterator iterator= actualRefIdMap.values().iterator();
        	while(iterator.hasNext()){
        		cr = new CompareResult(iterator.next(), OB_DELETED);
                cr.setDeleted(true);
                resultList.add(cr);	
        	}
        }
        }
        

        return resultList;
    }
}
