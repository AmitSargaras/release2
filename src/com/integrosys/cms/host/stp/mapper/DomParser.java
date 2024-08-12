package com.integrosys.cms.host.stp.mapper;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.host.stp.bus.OBStpField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: USER
 * Date: Aug 20, 2008
 * Time: 2:55:10 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Modified by IntelliJ IDEA.
 *
 * @author Andy Wong
 * @since June 2, 2009
 *        modify domParser to instantiate during startup, singleton
 */
public class DomParser {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String FIELD_TAGNAME = "field";
    private Map messageTagMessageFields;

    public DomParser(List fieldMappingFiles) {
        //instantiate map
        messageTagMessageFields = new HashMap();
        try {
        //Get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbf.setNamespaceAware(true);

        
            for (int i = 0; i < fieldMappingFiles.size(); i++) {
                String fileName = (String) fieldMappingFiles.get(i);
                //Using factory get an instance of document builder
                DocumentBuilder db = dbf.newDocumentBuilder();
                //Parse using builder to get DOM representation of the XML file
                Document dom = db.parse(getClass().getResource(fileName).toString());
                //Get the root element
                Element docEle = dom.getDocumentElement();

                // Get a list of all elements in the document
                NodeList nl = docEle.getElementsByTagName("*");
                for (int j = 0; j < nl.getLength(); j++) {
                    List stpMsgList = new ArrayList();
                    // Get element
                    Element element = (Element) nl.item(j);
                    //Get all the sub nodelist
                    NodeList subnl = element.getElementsByTagName(FIELD_TAGNAME);

                    if (subnl != null) {
                        for (int k = 0; k < subnl.getLength(); k++) {
                            //Get the collateral stp element
                            Element el = (Element) subnl.item(k);
                            stpMsgList.add(getOBStpField(el));
                        }
                    }
                    messageTagMessageFields.put(element.getTagName(), stpMsgList);
                }
            }
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List getStpFieldList(String tagname) throws Exception {
        //Andy Wong, 3 July 2009: deep clone message template list to avoid original being overwritten
        return (List) AccessorUtil.deepClone(messageTagMessageFields.get(tagname));
    }

    private OBStpField getOBStpField(Element stpEl) {
        String id = stpEl.getAttribute("id");
        String sequence = stpEl.getAttribute("sequence");
        String className = stpEl.getAttribute("class-name");
        String classField = stpEl.getAttribute("class-field");
        String classFieldType = stpEl.getAttribute("class-field-type");
        String position = stpEl.getAttribute("position");
        String length = stpEl.getAttribute("length");
        String value = stpEl.getAttribute("value");
        String format = stpEl.getAttribute("format");
        String mandatory = stpEl.getAttribute("mandatory");
        String decimalPoint = stpEl.getAttribute("decimal-point");

        //Create a new collateral with the value read from the xml nodes
        return new OBStpField(id, sequence, className, classField, classFieldType, position, length, value, format, mandatory, decimalPoint);
    }
}
