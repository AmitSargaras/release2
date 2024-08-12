package com.integrosys.cms.batch.sibs.parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Created by IntelliJ IDEA.
 * User: USER
 * Date: Aug 20, 2008
 * Time: 2:55:10 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DomParser {

    private static final Logger logger = LoggerFactory.getLogger(DomParser.class);
    private static HashMap commonCodeTagMap;
    private static HashMap tableTagMap;
    private static HashMap dependencyTagMap;

    /**** key in the hashmap  ****/
    private static final String KEY_TYPE = "TYPE";
    private static final String KEY_PARENT = "PARENT_TAG";
    private static final String KEY_ENTRY = "ENTRY_TAG";
    private static final String KEY_REMOTE_NAME = "ATTRIBUTE_REMOTE_NAME";
    private static final String KEY_LOCAL_NAME = "ATTRIBUTE_LOCAL_NAME";
    private static final String KEY_IS_DEPENDENCY = "IS_DEPENDENCY";
    private static final String KEY_SPECIAL_HANDLE = "SPECIAL_HANDLE_TAG";
    private static final String KEY_SPECIAL_HANDLE_ATTRIBUTE = "ATTRIBUTE_SPECIAL_HANDLE";

    /**** fields in the parameter property file ****/
    private static final String TAG_TYPE_COMMON_CODE = "common-code";
    private static final String TAG_TYPE_TABLE = "table";
    private static final String TAG_TYPE_DEPENDENCY = "dependency";
    private static final String TAG_ENTRY = "entry";
    private static final String ENTRY_TAG_ATTRIBUTE_REMOTE_NAME = "remoteEntityName";
    private static final String ENTRY_TAG_ATTRIBUTE_LOCAL_CATEGORY_NAME = "categoryCode";
    private static final String ENTRY_TAG_ATTRIBUTE_LOCAL_ENTITY_NAME = "localEntityName";
    private static final String TAG_SPECIAL_HANDLE = "specialHandle";
    private static final String SPECIAL_HANDLE_TAG_ATTRIBUTE_ID = "id";


    // **************** Setup ******************
    static {
        commonCodeTagMap =  new HashMap();
        commonCodeTagMap.put(KEY_TYPE, IParameterProperty.TYPE_COMMON_CODE);
        commonCodeTagMap.put(KEY_PARENT, TAG_TYPE_COMMON_CODE);
        commonCodeTagMap.put(KEY_ENTRY, TAG_ENTRY);
        commonCodeTagMap.put(KEY_REMOTE_NAME, ENTRY_TAG_ATTRIBUTE_REMOTE_NAME);
        commonCodeTagMap.put(KEY_LOCAL_NAME, ENTRY_TAG_ATTRIBUTE_LOCAL_CATEGORY_NAME);
        commonCodeTagMap.put(KEY_IS_DEPENDENCY, Boolean.FALSE);
        commonCodeTagMap.put(KEY_SPECIAL_HANDLE, TAG_SPECIAL_HANDLE);
        commonCodeTagMap.put(KEY_SPECIAL_HANDLE_ATTRIBUTE, SPECIAL_HANDLE_TAG_ATTRIBUTE_ID);

        tableTagMap =  new HashMap();
        tableTagMap.put(KEY_TYPE, IParameterProperty.TYPE_TABLE);
        tableTagMap.put(KEY_PARENT, TAG_TYPE_TABLE);
        tableTagMap.put(KEY_ENTRY, TAG_ENTRY);
        tableTagMap.put(KEY_REMOTE_NAME, ENTRY_TAG_ATTRIBUTE_REMOTE_NAME);
        tableTagMap.put(KEY_LOCAL_NAME, ENTRY_TAG_ATTRIBUTE_LOCAL_ENTITY_NAME);
        tableTagMap.put(KEY_IS_DEPENDENCY, Boolean.FALSE);
        tableTagMap.put(KEY_SPECIAL_HANDLE, TAG_SPECIAL_HANDLE);
        tableTagMap.put(KEY_SPECIAL_HANDLE_ATTRIBUTE, SPECIAL_HANDLE_TAG_ATTRIBUTE_ID);

        dependencyTagMap = new HashMap();
        dependencyTagMap.put(KEY_TYPE, IParameterProperty.TYPE_TABLE);
        dependencyTagMap.put(KEY_PARENT, TAG_TYPE_DEPENDENCY);
        dependencyTagMap.put(KEY_ENTRY, TAG_ENTRY);
        dependencyTagMap.put(KEY_REMOTE_NAME, ENTRY_TAG_ATTRIBUTE_REMOTE_NAME);
        dependencyTagMap.put(KEY_LOCAL_NAME, ENTRY_TAG_ATTRIBUTE_LOCAL_ENTITY_NAME);
        dependencyTagMap.put(KEY_IS_DEPENDENCY, Boolean.TRUE);

    }



    // **************** Property File Related Methods ******************
    public static Map parseParameterPropertyFile(InputStream inputStream) {
    	
    	try {
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();      //get factory
        
          dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
          dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
          
          dbf.setNamespaceAware(true);

            //Get document builder
            DocumentBuilder db = dbf.newDocumentBuilder();      //Using factory get an instance of document builder
            Document dom = db.parse(inputStream);       //Parse using builder to get DOM representation of the XML file
            Map paramPropertyMap = new HashMap();
            parseDocument(dom, commonCodeTagMap, paramPropertyMap);
            parseDocument(dom, tableTagMap, paramPropertyMap);
            parseDocument(dom, dependencyTagMap, paramPropertyMap);

            return paramPropertyMap;

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            throw new IllegalStateException("ParserConfigurationException");
        } catch (SAXException se) {
            se.printStackTrace();
            throw new IllegalStateException("SAXException");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new IllegalStateException("IOException");
        }
    }

    public static List parseParameterFileForRemoteEntityName(InputStream inputStream) {      //Get document builder
    	try {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();      //get factory
        
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbf.setNamespaceAware(true);

        
            DocumentBuilder db = dbf.newDocumentBuilder();      //Using factory get an instance of document builder
            Document dom = db.parse(inputStream);       //Parse using builder to get DOM representation of the XML file
            List remoteEntityNameList = new ArrayList();
            parseDocument(dom, commonCodeTagMap, remoteEntityNameList);
            parseDocument(dom, tableTagMap, remoteEntityNameList);
            parseDocument(dom, dependencyTagMap, remoteEntityNameList);

            return remoteEntityNameList;

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            throw new IllegalStateException("ParserConfigurationException");
        } catch (SAXException se) {
            se.printStackTrace();
            throw new IllegalStateException("SAXException");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new IllegalStateException("IOException");
        }
    }


    private static void parseDocument(Document dom, HashMap tagMap, Map paramPropertyMap) {
        try {
            Element docEle = dom.getDocumentElement();
            NodeList parentList = docEle.getElementsByTagName((String)tagMap.get(KEY_PARENT));
            Element parentElement = (Element) parentList.item(0);
            NodeList entryList = parentElement.getElementsByTagName((String)tagMap.get(KEY_ENTRY));

            if (entryList != null && entryList.getLength() > 0) {
                for (int i = 0; i < entryList.getLength(); i++) {
                    Element entry = (Element) entryList.item(i);
                    OBParameterProperty ob = new OBParameterProperty();
                    ob.setType((String)tagMap.get(KEY_TYPE));
                    ob.setRemoteEntityName(entry.getAttribute((String)tagMap.get(KEY_REMOTE_NAME)));
                    ob.setLocalName(entry.getAttribute((String)tagMap.get(KEY_LOCAL_NAME)));
                    ob.setIsDependencyUpdate(((Boolean)tagMap.get(KEY_IS_DEPENDENCY)).booleanValue());
                    DefaultLogger.debug("DomParser", "remoteName: " + ob.getRemoteEntityName());
                    NodeList specialHandleList = entry.getElementsByTagName((String)tagMap.get(KEY_SPECIAL_HANDLE));
                    if(specialHandleList != null && specialHandleList.getLength() > 0) {
                        HashMap map = new HashMap();
                        DefaultLogger.debug("DomParser", "special handle list [length]: " + specialHandleList.getLength());
                        for(int j=0; j<specialHandleList.getLength(); j++) {                            
                            Element specialHandle = (Element) specialHandleList.item(j);
                            DefaultLogger.debug("DomParser", "Element.getAttribute (id): " + specialHandle.getAttribute("id"));
                            map.put(specialHandle.getAttribute((String)tagMap.get(KEY_SPECIAL_HANDLE_ATTRIBUTE)), null);
                        }
                        ob.setSpecialHandlingMap(map);
                    }
                    paramPropertyMap.put(ob.getRemoteEntityName(), ob);
                }
            }
        }
        catch (NullPointerException ex) {
            logger.error("parseDocument - Request/Response id might be incorrect. ", ex);
            IllegalStateException exception = new IllegalStateException();
            exception.initCause(ex);
            throw exception;
        }
    }

    private static void parseDocument(Document dom, HashMap tagMap, List remoteNameList) {
        try {
            Element docEle = dom.getDocumentElement();
            NodeList parentList = docEle.getElementsByTagName((String)tagMap.get(KEY_PARENT));
            Element parentElement = (Element) parentList.item(0);
            NodeList entryList = parentElement.getElementsByTagName((String)tagMap.get(KEY_ENTRY));

            if (entryList != null && entryList.getLength() > 0) {
                for (int i = 0; i < entryList.getLength(); i++) {
                    Element entry = (Element) entryList.item(i);
                    remoteNameList.add(entry.getAttribute((String)tagMap.get(KEY_REMOTE_NAME)));
                }
            }
        }
        catch (NullPointerException ex) {
            logger.error("parseDocument - Request/Response id might be incorrect. ", ex);
            IllegalStateException exception = new IllegalStateException();
            exception.initCause(ex);
            throw exception;
        }
    }

}
