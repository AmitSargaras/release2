package com.integrosys.cms.host.stp.mapper;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.stp.bus.OBStpField;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.StpCommonException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: KLYong
 * @version: ${VERSION}
 * @since: Aug 28, 2008 12:03:53 PM
 */

/**
 * @author Andy Wong
 * @since 22 Jan 2009
 *        <p/>
 *        use reflection to map Stp and OB fields
 */
public class STPMapper implements ISTPMapper, IStpConstants, ICMSConstant {

    private final Logger logger = LoggerFactory.getLogger(STPMapper.class);
    private DomParser stpDomParser;

    public DomParser getStpDomParser() {
        return stpDomParser;
    }

    public void setStpDomParser(DomParser stpDomParser) {
        this.stpDomParser = stpDomParser;
    }

    /**
     * Get the xml list
     *
     * @param id       - request or response id
     * @param filePath - xml file path (not used, inject from Spring xml)
     * @return xml list
     */
    public List getList(String id, String filePath) throws Exception {
        //domparser in singleton
        return stpDomParser.getStpFieldList(id);
    }

    /**
     * Map to field OB (STP Object)
     *
     * @param object - business object, hashmap, hashset, collection
     * @param list   - list containing stp objects retrieved from xml attributes
     * @return list containing stp objects with values
     */
    public List mapToFieldOB(Object object, List list) throws Exception {
        OBStpField obStpField;
        Map methodMap = getMethodMap(object);
        List returnList = new ArrayList();

        if (object instanceof ArrayList) {
            ArrayList arlist = (ArrayList) object;
            if (arlist != null && arlist.size() > 0) {
                methodMap = getMethodMap(arlist.get(0));
            }

            for (int i = 0; i < list.size(); i++) {
                obStpField = new OBStpField(list, i);
                int stpSeq = Integer.parseInt(obStpField.getSequence()); //Retrieve the sequence in the xml definition
                String className = obStpField.getClassName();
                String classField = obStpField.getClassField();
                Object[] bizOB = arlist.toArray(); //FIFO

                for (int obSeq = 0; obSeq < bizOB.length; obSeq++) { //Sequence size
                    if (stpSeq == (obSeq + 1)) {
                        Object curBizOB = bizOB[obSeq];
                        if (curBizOB != null
                                && StringUtils.isNotEmpty(className)
                                && Class.forName(className).isAssignableFrom(curBizOB.getClass())
                                && methodMap.containsKey(resolveGetterMethodName(classField))) { //Compare class name
                            convertToString(obStpField, curBizOB); //Set value into stp object
                        }
                    }
                }
                returnList.add(obStpField); //Set stp object to be contain inside a list
            }

        } else if (object instanceof HashMap) {
            Map map = (HashMap) object;

            for (int i = 0; i < list.size(); i++) {
                obStpField = new OBStpField(list, i);
                String classFieldID = obStpField.getFieldID(); //Get stp field id for hashmap key

                for (Iterator iterator = (map.keySet()).iterator(); iterator.hasNext();) {
                    String fieldID = iterator.next().toString();
                    String value = (String) map.get(fieldID);

                    if (StringUtils.equalsIgnoreCase(classFieldID, fieldID)) {
                        obStpField.setValue(value); //Set value into stp object
                    }
                }
                returnList.add(obStpField); //Set stp object to be contain inside a list
            }
        } else {
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                obStpField = (OBStpField) iterator.next();
                String className = obStpField.getClassName();
                String classField = obStpField.getClassField();

                if (object != null
                        && StringUtils.isNotEmpty(className)
                        && Class.forName(className).isAssignableFrom(object.getClass())
                        && methodMap.containsKey(resolveGetterMethodName(classField))) { //Compare class name
                    convertToString(obStpField, object); //Set value into stp object
                }
                returnList.add(obStpField);
            }
        }
        return returnList;
    }

    /**
     * Map to biz OB (CMS Model Object)
     *
     * @param object - CMS Model Object/ Object bean
     * @param list   - list containing the stp objects
     * @return as object bean
     */
    public Object mapToBizOB(Object object, List list) throws Exception {
        Map methodMap = getMethodMap(object);

        for (int index = 0; index < list.size(); index++) {
            OBStpField obStpField = (OBStpField) list.get(index);
            String className = obStpField.getClassName();
            String classField = obStpField.getClassField();

            if (object != null
                    && StringUtils.isNotEmpty(className)
                    && Class.forName(className).isAssignableFrom(object.getClass())
                    && methodMap.containsKey(resolveSetterMethodName(classField))) { //Compare class name
                object = convertToObject(object, methodMap, obStpField);
            }
        }
        return object;
    }

    private Object convertToObject(Object object, Map methodMap, OBStpField obStpField) throws Exception {
        Method getMethod = (Method) methodMap.get(resolveGetterMethodName(obStpField.getClassField()));
        Method setMethod = (Method) methodMap.get(resolveSetterMethodName(obStpField.getClassField()));

        if (getMethod == null) {
            getMethod = (Method) methodMap.get(resolvePrimitiveGetterMethodName(obStpField.getClassField()));

            if (getMethod == null)
                throw new StpCommonException("The getter method not found. Class Name = " + obStpField.getClassName() + "| Class Field = " + obStpField.getClassField() + "| Field Id = " + obStpField.getFieldID(), null);
        }

        if (setMethod == null) {
            throw new StpCommonException("The setter method not found. Class Name = " + obStpField.getClassName() + "| Class Field = " + obStpField.getClassField() + "| Field Id = " + obStpField.getFieldID(), null);
        }

        if (StringUtils.isNotBlank(obStpField.getValue())) {
            Class aClass = getMethod.getReturnType();
            if (aClass.equals(Class.forName("java.lang.Boolean"))
                    || StringUtils.equals(aClass.getName(), "boolean")) {
                setMethod.invoke(object, new Object[]{new Boolean(StringUtils.equals(obStpField.getValue(), TRUE_VALUE) ? true : false)});

            } else if (aClass.equals(Class.forName("java.lang.Character"))
                    || StringUtils.equals(aClass.getName(), "char")) {
                setMethod.invoke(object, new Object[]{new Character(obStpField.getValue().charAt(0))});

            } else if (aClass.equals(Class.forName("java.lang.Byte"))) {
                setMethod.invoke(object, new Object[]{new Byte(obStpField.getValue())});

            } else if (aClass.equals(Class.forName("java.lang.Short"))) {
                setMethod.invoke(object, new Object[]{new Short(obStpField.getValue())});

            } else if (aClass.equals(Class.forName("java.lang.Integer"))
                    || StringUtils.equals(aClass.getName(), "int")) {
                NumberFormat formatter = new DecimalFormat("#"); //Format to Integer/Numerical only
                String formatedValue = formatter.format(new Double(obStpField.getValue()));
                setMethod.invoke(object, new Object[]{new Integer(formatedValue)});

            } else if (aClass.equals(Class.forName("java.lang.Long"))
                    || StringUtils.equals(aClass.getName(), "long")) {
                setMethod.invoke(object, new Object[]{new Long(obStpField.getValue())});

            } else if (aClass.equals(Class.forName("java.lang.Float"))
                    || StringUtils.equals(aClass.getName(), "float")) {
                setMethod.invoke(object, new Object[]{new Float(obStpField.getValue())});

            } else if (aClass.equals(Class.forName("java.lang.Double"))
                    || StringUtils.equals(aClass.getName(), "double")) {
                setMethod.invoke(object, new Object[]{new Double(obStpField.getValue())});

            } else if (aClass.equals(Class.forName("java.util.Date"))) {
                int checkDate = Integer.parseInt(obStpField.getValue());
                if (checkDate > 0) {
                    String strDate = obStpField.getValue().substring(1, 9);
                    DateFormat dateFormat = new SimpleDateFormat(PropertyManager.getValue(STP_DATE_PATTERN));
                    Date date = dateFormat.parse(strDate);
                    setMethod.invoke(object, new Object[]{date});
                }

            } else if (aClass.equals(Class.forName("java.math.BigDecimal"))) {
                int decPoint = 0; //Defaulted
                if (StringUtils.isNotBlank(obStpField.getDecimalPoint()))
                    decPoint = Integer.parseInt(obStpField.getDecimalPoint());

                BigDecimal bigDecimal = new BigDecimal(obStpField.getValue());
                bigDecimal.setScale(decPoint);
                setMethod.invoke(object, new Object[]{bigDecimal});

            } else if (aClass.isInstance(Class.forName("com.integrosys.base.businfra.currency.Amount").newInstance())) {
                setMethod.invoke(object, new Object[]{new Amount((new Double(obStpField.getValue())).doubleValue(), "MYR")});

            } else if (aClass.isInstance(Class.forName("com.integrosys.cms.app.common.bus.OBBookingLocation").newInstance())) {
                NumberFormat formatter = new DecimalFormat("#"); //Format to Integer/Numerical only
                String truncateZeroPad = formatter.format(new Double(obStpField.getValue()));
                setMethod.invoke(object, new Object[]{new OBBookingLocation("MY", truncateZeroPad)});

            } else if (aClass.equals(Class.forName("java.lang.String"))) {
                //Andy Wong: truncate front zero if it is packed value
                if (StringUtils.equals(obStpField.getFormat(), "P")) {
                    NumberFormat formatter = new DecimalFormat("#"); //Format to Integer/Numerical only
                    String truncateZeroPad = formatter.format(new Double(obStpField.getValue()));
                    setMethod.invoke(object, new Object[]{truncateZeroPad});
                } else {
                    setMethod.invoke(object, new Object[]{obStpField.getValue()});
                }
            } else {
                throw new StpCommonException("Conversion to OB failed. Class = " + aClass + " Field Id = " + obStpField.getFieldID(), null);
            }
        }
        return object;
    }

    private void convertToString(OBStpField obStpField, Object object) throws Exception {
        String strValue = "";
        int decPoint = 0; //Default to 0
        if (StringUtils.isNotBlank(obStpField.getDecimalPoint()))
            decPoint = Integer.parseInt(obStpField.getDecimalPoint());

        Map methodMap = getMethodMap(object);
        Method getMethod = (Method) methodMap.get(resolveGetterMethodName(obStpField.getClassField()));

        if (getMethod == null) {
            getMethod = (Method) methodMap.get(resolvePrimitiveGetterMethodName(obStpField.getClassField()));

            if (getMethod == null)
                throw new StpCommonException("The getter method not found. Class Name = " + obStpField.getClassName() + "| Class Field = " + obStpField.getClassField() + "| Field Id = " + obStpField.getFieldID(), null);
        }

        if (object != null) {
            Object obj1 = getMethod.invoke(object, (Object[])null);
            if (obj1 != null) {
                if (obj1 instanceof java.lang.Boolean) {
                    strValue = ((Boolean) obj1).booleanValue() ? TRUE_VALUE : FALSE_VALUE;

                } else if (obj1 instanceof java.lang.Character) {
                    strValue = ((Character) obj1).toString();

//                } else if (obj1 instanceof java.lang.Byte) {

                } else if (obj1 instanceof java.lang.Short) {
                    BigDecimal bigDecimal = new BigDecimal(((Short) obj1).doubleValue()).setScale(decPoint, BigDecimal.ROUND_HALF_UP);
                    strValue = bigDecimal.toString();

                } else if (obj1 instanceof java.lang.Integer) {
                    BigDecimal bigDecimal = new BigDecimal(((Integer) obj1).doubleValue()).setScale(decPoint, BigDecimal.ROUND_HALF_UP);
                    strValue = bigDecimal.toString();

                } else if (obj1 instanceof java.lang.Long) {
                    BigDecimal bigDecimal = new BigDecimal(((Long) obj1).doubleValue()).setScale(decPoint, BigDecimal.ROUND_HALF_UP);
                    strValue = bigDecimal.toString();

                } else if (obj1 instanceof java.lang.Float) {
                    BigDecimal bigDecimal = new BigDecimal(((Float) obj1).doubleValue()).setScale(decPoint, BigDecimal.ROUND_HALF_UP);
                    strValue = bigDecimal.toString();

                } else if (obj1 instanceof java.lang.Double) {
                    BigDecimal bigDecimal = new BigDecimal(((Double) obj1).doubleValue()).setScale(decPoint, BigDecimal.ROUND_HALF_UP);
                    strValue = bigDecimal.toString();

                } else if (obj1 instanceof Amount) {
                    BigDecimal bigDecimal = new BigDecimal(((Amount) obj1).getAmount()).setScale(decPoint, BigDecimal.ROUND_HALF_UP);
                    strValue = bigDecimal.toString();

                } else if (obj1 instanceof Date) {
                    DateFormat dateFormat = new SimpleDateFormat(PropertyManager.getValue(STP_DATE_PATTERN));
                    strValue = dateFormat.format((Date) obj1);

                } else if (obj1 instanceof BigDecimal) {
                    strValue = ((BigDecimal) obj1).setScale(decPoint, BigDecimal.ROUND_HALF_UP).toString();

                } else if (obj1 instanceof OBBookingLocation) {
                    strValue = ((OBBookingLocation) obj1).getOrganisationCode();

                } else if (obj1 instanceof String) {
                    strValue = (String) obj1;

                } else {
                    throw new StpCommonException("Data type not handled. Field Id = " + obStpField.getFieldID(), null);
                }

            }
        }

        obStpField.setValue(StringUtils.trimToEmpty(strValue));
    }

    /**
     * Method to return a list of Biz OB from Field OB list
     *
     * @param list stp field list
     * @return list of biz object
     * @throws Exception exception
     */
    public List mapToBizOBList(List list) throws Exception {
        HashMap fieldListMap = new HashMap();
        List returnList = new ArrayList();
        int noOfRecordSet = 0;

        //group biz obj attribute as whole into map
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            OBStpField obStpField = (OBStpField) iterator.next();
            String seq = obStpField.getSequence();

            // Andy Wong: retrieve number of record set returned in response
            if (StringUtils.equals(obStpField.getFieldID(), RES_RECORD_RETURN)) {
                noOfRecordSet = Integer.parseInt(obStpField.getValue());
                continue;
            }

            if (StringUtils.isNotEmpty(seq)) {
                List tmp = (List) fieldListMap.get(seq);
                if (tmp != null) {
                    tmp.add(obStpField);
                } else {
                    tmp = new ArrayList();
                    tmp.add(obStpField);
                }
                fieldListMap.put(seq, tmp);
            }
        }

        //instantiate biz obj based on sequence
        for (int i = 1; i <= noOfRecordSet; i++) {
            Object bizOB = null;
            Map methodMap = null;
            ArrayList seqList = (ArrayList) fieldListMap.get(Integer.toString(i));
            for (Iterator iterator1 = seqList.iterator(); iterator1.hasNext();) {
                OBStpField obStpField = (OBStpField) iterator1.next();

                if (StringUtils.isNotEmpty(obStpField.getClassName())) {
                    Class aClass = Class.forName(obStpField.getClassName());
                    if (bizOB == null) {
                        bizOB = aClass.newInstance();
                        methodMap = getMethodMap(bizOB);
                    }
                    bizOB = convertToObject(bizOB, methodMap, obStpField);
                }
            }
            returnList.add(bizOB);
        }

        return returnList;
    }

    /**
     * This method resolves the getter method name based on the field name pased using
     * the standared java getter convention
     */
    private static String resolveGetterMethodName(String s) {
        return "get" + StringUtils.lowerCase(s);
    }

    private static String resolvePrimitiveGetterMethodName(String s) {
        return "is" + StringUtils.lowerCase(s);
    }

    /**
     * This method resolves the setter method name based on the field name pased using
     * the standared java getter convention
     */
    private static String resolveSetterMethodName(String s) {
        return "set" + StringUtils.lowerCase(s);
    }

    private Map getMethodMap(Object object) {
        Map methodMap = new HashMap();

        if (object != null) {
            Method[] methods = object.getClass().getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                methodMap.put(method.getName().toLowerCase(), method);
            }
        }

        return methodMap;
    }
}
