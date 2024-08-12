/**
 * Generate footers.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

import java.util.HashMap;

class cCreateFooter extends cCreateElement {

  private static cCreateFooter rscInstance = null;

  
  public String toString() {
    return this.xml;
  }

  static public cCreateFooter getInstance() {
    if (cCreateFooter.rscInstance == null) {
      cCreateFooter.rscInstance = new cCreateFooter();
    }
    return cCreateFooter.rscInstance;
  }

  protected void fGenerateFTR() {
    this.xml = "__GENERATEFTR__";
  }

  
  protected void fGenerateP() {
    this.xml = this.xml.replace("__GENERATEFTR__", "<" + cCreateElement.NAMESPACE + ":p>__GENERATEP__</" + cCreateElement.NAMESPACE + ":p>");
  }

  
  protected void fGenerateR() {
    if (!"".equals(String.valueOf(this.xml.equals("")))) {
      if (this.xml.indexOf("__GENERATEP__") != -1) {
        this.xml = this.xml.replace("__GENERATEP__", "<" + cCreateElement.NAMESPACE + ":r>__GENERATER__</" + cCreateElement.NAMESPACE + ":r>__GENERATERSUB__");
      } else if (this.xml.indexOf("__GENERATER__") != -1) {
        this.xml = this.xml.replace("__GENERATER__", "<" + cCreateElement.NAMESPACE + ":r>__GENERATER__</" + cCreateElement.NAMESPACE + ":r>__GENERATERSUB__");
      } else if (this.xml.indexOf("__GENERATERSUB__") != -1) {
        this.xml = this.xml.replace("__GENERATERSUB__", "<" + cCreateElement.NAMESPACE + ":r>__GENERATER__</" + cCreateElement.NAMESPACE + ":r>__GENERATERSUB__");
      }
    } else {
      this.xml = "<" + cCreateElement.NAMESPACE + ":r>__GENERATER__</" + cCreateElement.NAMESPACE + ":r>__GENERATERSUB__";
    }
  }

  protected void fGeneratePTAB(String strRelativeTo, String strAlignment, String strLeader) {
    if ("".equals(strRelativeTo)) {
      strRelativeTo = "margin";
    }
    if ("".equals(strAlignment)) {
      strAlignment = "right";
    }
    if ("".equals(strLeader)) {
      strLeader = "none";
    }
    this.xml = this.xml.replace("__GENERATER__", "<" + cCreateElement.NAMESPACE + ":ptab " + cCreateElement.NAMESPACE + ":relativeTo='" + strRelativeTo + "' " + cCreateElement.NAMESPACE + ":alignment='" + strAlignment + "' " + cCreateElement.NAMESPACE + ":leader='" + strLeader + "'></" + cCreateElement.NAMESPACE + ":ptab>");
  }

  protected void fGenerateFLDSIMPLE() {
    this.xml = this.xml.replace("__GENERATERSUB__", "<" + cCreateElement.NAMESPACE + ":fldSimple " + cCreateElement.NAMESPACE + ":instr=' PAGE   \\* MERGEFORMAT '></" + cCreateElement.NAMESPACE + ":fldSimple>");
  }

  public void fCreateFooter(String text, HashMap arrArgs) {
    this.xml = "";
    this.fGenerateFTR();
    arrArgs.put("text", text);
    cCreateText objText = cCreateText.getInstance();
    objText.fCreateText(arrArgs);
    this.xml = this.xml.replace("__GENERATEFTR__", objText.toString() + "__GENERATEFTR__");
    if (arrArgs.containsKey("pager") && "true".equals(arrArgs.get("pager").toString())) {
      this.fGenerateP();
      this.fGenerateR();
      String pagerAlignment = "center";
      if (arrArgs.containsKey("pagerAlignment")) {
        pagerAlignment = arrArgs.get("pagerAlignment").toString();
      }
      this.fGeneratePTAB("margin", pagerAlignment, "");
      this.fGenerateFLDSIMPLE();
    }
  }
}
