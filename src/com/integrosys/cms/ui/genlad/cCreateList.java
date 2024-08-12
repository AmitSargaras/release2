/**
 * Generate lists.
 * @author 2mdc
 */

package com.integrosys.cms.ui.genlad;

import java.lang.Integer;
import java.util.*;

public class cCreateList extends cCreateElement {

  private static cCreateList rscInstance = null;
  private static int intNumericList = -1;
  private String arrList;
  private String val;
  private ArrayList data;

   
  public String toString() {
    this.fCleanTemplate();
    return this.xml;
  }

  public cCreateList() {
    this.data = new ArrayList();
  }

  public static cCreateList getInstance() {
    if (cCreateList.rscInstance == null) {
      cCreateList.rscInstance = new cCreateList();
    }
    return cCreateList.rscInstance;
  }

  protected void fAddList(String strList) {
    this.xml = this.xml.replace("__GENERATER__", strList);
  }

  protected void fGenerateILFO(String strVal) {
    if ("".equals(strVal)) {
      strVal = "0";
    }
    this.xml = this.xml.replace("__GENERATEILFO__", "<" + cCreateElement.NAMESPACE + ":ilfo " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":ilfo>");
  }

  protected void fGenerateILVL(String strVal) {
    if ("".equals(strVal)) {
      strVal = "0";
    }
    this.xml = this.xml.replace("__GENERATEPSTYLE__", "<" + cCreateElement.NAMESPACE + ":ilvl " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":ilvl>__GENERATEPSTYLE__");
  }

  protected void fGenerateLISTPR() {
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":listPr>__GENERATEILVL____GENERATEILFO__</" + cCreateElement.NAMESPACE + ":listPr>");
  }

  protected void fGenerateNUMID(String strVal) {
    this.xml = this.xml.replace("__GENERATEPSTYLE__", "<" + cCreateElement.NAMESPACE + ":numId " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":numId>");
  }

  protected void fGenerateNUMPR() {
    this.xml = this.xml.replace("__GENERATEPSTYLE__", "<" + cCreateElement.NAMESPACE + ":numPr>__GENERATEPSTYLE__</" + cCreateElement.NAMESPACE + ":numPr>");
  }

   
  protected void fGeneratePSTYLE(String strVal) {
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":pStyle " + cCreateElement.NAMESPACE + ":val='" + strVal + "'>__GENERATEPSTYLE__</" + cCreateElement.NAMESPACE + ":pStyle>");
  }

  protected void fRunArray(ArrayList arrDat, int intVal, int intDepth) {
    Iterator unkDat = arrDat.iterator();
    int intNewDepth;
    while (unkDat.hasNext()) {
      Object unkDatValue = unkDat.next();
        this.fGenerateP();
        this.fGeneratePPR();
        this.fGeneratePSTYLE("Prrafodelista");
        this.fGenerateNUMPR();
        this.fGenerateILVL("" + intDepth);
        this.fGenerateNUMID("" + intVal);
        this.fGenerateR();
        this.fGenerateT(unkDatValue.toString());
        this.arrList += this.xml;
    }
  }

  public void fCreateList(HashMap arrArgs) {
    this.xml = "";
    this.arrList = "";
    if (arrArgs.get("val").toString().equals("2")) {
      this.intNumericList++;
      this.fRunArray((ArrayList) arrArgs.get("t"), Integer.parseInt(arrArgs.get("val").toString()) + this.intNumericList, 1);
    } else {
      this.fRunArray((ArrayList) arrArgs.get("t"), Integer.parseInt(arrArgs.get("val").toString()), 1);
    }

    this.xml = this.arrList;
  }
}
