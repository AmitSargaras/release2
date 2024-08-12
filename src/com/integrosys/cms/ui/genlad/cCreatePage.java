/**
 * Generate pages.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

import java.util.HashMap;
import java.util.ArrayList;

public class cCreatePage extends cCreateElement {

  private static cCreatePage rscInstance = null;

   
  public String toString() {
    return this.xml;
  }

  public static cCreatePage getInstance() {
    if (cCreatePage.rscInstance == null) {
      cCreatePage.rscInstance = new cCreatePage();
    }
    return cCreatePage.rscInstance;
  }

  protected void fGenerateSECTPR(String rId) {
    if ("".equals(rId)) {
      rId = "12240";
    }
    this.xml = "<" + cCreateElement.NAMESPACE + ":sectPr " + cCreateElement.NAMESPACE + ":rsidR='" + rId + "' " + cCreateElement.NAMESPACE + ":rsidRPr='" + rId + "' " + cCreateElement.NAMESPACE + ":rsidSect='" + rId + "'>__GENERATEHEADERREFERENCE____GENERATEFOOTERREFERENCE____GENERATESECTPR__</" + cCreateElement.NAMESPACE + ":sectPr>";
  }

  protected void fGeneratePGSZ(String w, String h, String orient) {
    String xmlAux = "";

    if ("".equals(w)) {
      w = "11906";
    }
    if ("".equals(h)) {
      h = "16838";
    }
    if ("".equals(orient)) {
      orient = "";
    }
    xmlAux = "<" + cCreateElement.NAMESPACE + ":pgSz " + cCreateElement.NAMESPACE + ":w='" + w + "' " + cCreateElement.NAMESPACE + ":h='" + h + "'";
    if (!"".equals(orient)) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":orient='" + orient + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":pgSz>__GENERATEPGSZ__";
    this.xml = this.xml.replace("__GENERATESECTPR__", xmlAux);
  }

  protected void fGeneratePGMAR(HashMap arrArgs) {
    String top = "1440";
    String right = "1800";
    String bottom = "1440";
    String left = "1800";
    String header = "720";
    String footer = "720";
    String gutter = "0";
    if (arrArgs.containsKey("top")) {
      top = arrArgs.get("top").toString();
    }
    if (arrArgs.containsKey("bottom")) {
      bottom = arrArgs.get("bottom").toString();
    }
    if (arrArgs.containsKey("right")) {
      right = arrArgs.get("right").toString();
    }
    if (arrArgs.containsKey("left")) {
      left = arrArgs.get("left").toString();
    }
    this.xml = this.xml.replace("__GENERATEPGSZ__", "<" + cCreateElement.NAMESPACE + ":pgMar " + cCreateElement.NAMESPACE + ":top='" + top + "' " + cCreateElement.NAMESPACE + ":right='" + right + "' " + cCreateElement.NAMESPACE + ":bottom='" + bottom + "' " + cCreateElement.NAMESPACE + ":left='" + left + "' " + cCreateElement.NAMESPACE + ":header='" + header + "' " + cCreateElement.NAMESPACE + ":footer='" + footer + "' " + cCreateElement.NAMESPACE + ":gutter='" + gutter + "'></" + cCreateElement.NAMESPACE + ":pgMar>__GENERATEPGMAR__");
  }

  protected void fGenerateCOLS(String num, String sep, String space, String equalWidth) {
    if ("".equals(num)) {
      num = "";
    }
    if ("".equals(sep)) {
      sep = "";
    }
    if ("".equals(space)) {
      space = "708";
    }
    if ("".equals(equalWidth)) {
      equalWidth = "";
    }
    this.xml = this.xml.replace("__GENERATEPGMAR__", "<" + cCreateElement.NAMESPACE + ":cols " + cCreateElement.NAMESPACE + ":space='" + space + "'></" + cCreateElement.NAMESPACE + ":cols>__GENERATECOLS__");
  }

  protected void fGenerateCOL(String w, String space) {
    if ("".equals(space)) {
      space = "708";
    }
  }

  protected void fGenerateDOCGRID(String linepitch) {
    if ("".equals(linepitch)) {
      linepitch = "360";
    }
    this.xml = this.xml.replace("__GENERATECOLS__", "<" + cCreateElement.NAMESPACE + ":docGrid " + cCreateElement.NAMESPACE + ":linePitch='" + linepitch + "'></" + cCreateElement.NAMESPACE + ":docGrid>");
  }

  protected void fGenerateBR(String type) {
    if ("".equals(type)) {
      type = "";
    }
    this.xml = this.xml.replace("__GENERATER__", "<" + cCreateElement.NAMESPACE + ":br " + cCreateElement.NAMESPACE + ":type='" + type + "'></" + cCreateElement.NAMESPACE + ":br>");
  }

  protected void fGenerateSECTIONSECTPR(String rId) {
    if ("".equals(rId)) {
      rId = "12240";
    }
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":sectPr " + cCreateElement.NAMESPACE + ":rsidR='" + rId + "' " + cCreateElement.NAMESPACE + ":rsidRPr='" + rId + "' " + cCreateElement.NAMESPACE + ":rsidSect='" + rId + "'>__GENERATEHEADERREFERENCE____GENERATEFOOTERREFERENCE____GENERATESECTPR__</" + cCreateElement.NAMESPACE + ":sectPr>__GENERATEPPR__");
  }

  protected void fGenerateTITLEPG() {
    this.xml = this.xml.replace("__GENERATECOLS__", "<" + cCreateElement.NAMESPACE + ":titlePg></" + cCreateElement.NAMESPACE + ":titlePg>__GENERATECOLS__");
  }

  public void fCreatePage(ArrayList arrDat, String strStyle) {
    this.xml = "";
  }

  public void fCreateSECTPR(HashMap arrArgs) {
    this.xml = "";
    this.fGenerateSECTPR("");
    if (arrArgs.containsKey("orient") && !"".equals(arrArgs.get("orient").toString()) && "landscape".equals(arrArgs.get("orient").toString())) {
      this.fGeneratePGSZ("16838", "11906", "landscape");
    } else {
      this.fGeneratePGSZ("11906", "16838", "");
    }
    this.fGeneratePGMAR(arrArgs);
    this.fGenerateCOLS("", "", "", "");
    if (arrArgs.containsKey("titlePage") && !"".equals(arrArgs.get("titlePage").toString())) {
      this.fGenerateTITLEPG();
    }
    this.fGenerateDOCGRID("");
  }

  public void fGeneratePageBreak(String strType) {
    this.xml = "";
    this.fGenerateP();
    if (!"line".equals(strType) && !"".equals(strType)) {
      this.fGenerateR();
      this.fGenerateBR(strType);
    }
    this.fCleanTemplate();
  }
}
