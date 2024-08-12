/**
 * Generate links.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

import java.util.HashMap;

public class cCreateLink extends cCreateElement {

  private static cCreateLink rscInstance = null;
  private String strLink;
  private String strTitle;

   
  public String toString() {
    return this.xml;
  }

  public static cCreateLink getInstance() {
    if (cCreateLink.rscInstance == null) {
      cCreateLink.rscInstance = new cCreateLink();
    }
    return cCreateLink.rscInstance;
  }

  public cCreateLink() {
  }

  public String fGetStrLink() {
    return this.strLink;
  }

  public String fGetStrTitle() {
    return this.strTitle;
  }

  protected void fGenerateFLDCHAR(String strFldCharType) {
    this.xml = this.xml.replace("__GENERATER__", "<" + cCreateElement.NAMESPACE + ":fldChar " + cCreateElement.NAMESPACE + ":fldCharType='" + strFldCharType + "'></" + cCreateElement.NAMESPACE + ":fldChar>");
  }

  protected void fGenerateHYPERLINK(String strType) {
    this.xml = "<" + cCreateElement.NAMESPACE + ":hyperlink r:id='rId5' w:history='1'>__GENERATEP__</" + cCreateElement.NAMESPACE + ":hyperlink>";
  }

  protected void fGenerateINSTRTEXT(String strLink) {
    if ("".equals(strLink)) {
      strLink = "http://localhost";
    }
    this.xml = this.xml.replace("__GENERATER__", "<" + cCreateElement.NAMESPACE + ":instrText xml:space='preserve'> HYPERLINK \"" + strLink + "\"</" + cCreateElement.NAMESPACE + ":instrText>");
  }

  protected void fGeneratePROOFERR(String strType) {
    this.xml = this.xml.replace("__GENERATERSUB__", "<" + cCreateElement.NAMESPACE + ":proofErr " + cCreateElement.NAMESPACE + ":type='" + strType + "'></" + cCreateElement.NAMESPACE + ":proofErr>__GENERATERSUB__");
  }

   
  protected void fGenerateR() {
    if (this.xml.indexOf("__GENERATEP__") != -1) {
      this.xml = this.xml.replace("__GENERATEP__", "<" + cCreateElement.NAMESPACE + ":r>__GENERATER__</" + cCreateElement.NAMESPACE + ":r>__GENERATERSUB__");
    } else if (this.xml.indexOf("__GENERATERSUB__") != -1) {
      this.xml = this.xml.replace("__GENERATERSUB__", "<" + cCreateElement.NAMESPACE + ":r>__GENERATER__</" + cCreateElement.NAMESPACE + ":r>__GENERATERSUB__");
    } else {
      this.xml = "<" + cCreateElement.NAMESPACE + ":r>__GENERATER__</" + cCreateElement.NAMESPACE + ":r>__GENERATERSUB__";
    }
  }

   
  protected void fGenerateRSTYLE(String strVal) {
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":rStyle " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":rStyle>");
  }

  public void fCreateEmbeddedLink() {
    this.xml = "";
    this.fGenerateR();
    this.fGenerateFLDCHAR("begin");
    this.fGenerateR();
    this.fGenerateINSTRTEXT(this.strTitle);
    this.fGenerateR();
    this.fGenerateFLDCHAR("separate");
    this.fGeneratePROOFERR("gramStart");
    this.fGenerateR();
    this.fGenerateRPR();
    this.fGenerateRSTYLE("Hipervnculo");
    this.fGenerateT(this.strLink);
    this.fGeneratePROOFERR("gramEnd");
    this.fGenerateR();
    this.fGenerateFLDCHAR("end");
  }

  public void fCreateLink(String strTexto, String strEnlace) {
    this.xml = "";
    this.fGenerateP();
    this.fGenerateR();
    this.fGenerateFLDCHAR("begin");
    this.fGenerateR();
    this.fGenerateINSTRTEXT(strEnlace);
    this.fGenerateR();
    this.fGenerateFLDCHAR("separate");
    this.fGeneratePROOFERR("gramStart");
    this.fGenerateR();
    this.fGenerateRPR();
    this.fGenerateRSTYLE("Hipervnculo");
    this.fGenerateT(strTexto);
    this.fGeneratePROOFERR("gramEnd");
    this.fGenerateR();
    this.fGenerateFLDCHAR("end");
    this.fCleanTemplate();
  }

  public void fInitLink(HashMap arrArgs) {
    this.strLink = arrArgs.get("title").toString();
    this.strTitle = arrArgs.get("link").toString();
  }
}
