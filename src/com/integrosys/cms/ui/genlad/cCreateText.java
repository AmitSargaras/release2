/**
 * Generate texts.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class cCreateText extends cCreateElement {

  public static final int IDTITLE = 229998237;
  private static cCreateText rscInstance = null;
  private static int intIdTitle = 0;

   
  public String toString() {
    return this.xml;
  }

  public static cCreateText getInstance() {
    if (cCreateText.rscInstance == null) {
      try {
        cCreateText.rscInstance = new cCreateText();
      } catch (Exception e) {
    	  DefaultLogger.debug("cCreteText","There is error when generate a embeded element.");
      }

    }
    return cCreateText.rscInstance;
  }

  protected void fGenerateCOLOR(String str) {
    String strVal;
    if ("".equals(str)) {
      strVal = "000000";
    } else {
      strVal = str;
    }
    this.xml = this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":color " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":color>__GENERATERPR__");
  }

   
  protected void fGenerateJC(String strVal) {
    this.xml = this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":jc " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":jc>__GENERATESZ__");
  }

  protected void fGenerateI(String str) {
    String strVal;
    if ("".equals(str)) {
      strVal = "single";
    } else {
      strVal = str;
    }
    this.xml = this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":i " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":i>__GENERATERPR__");
  }

  protected void fGenerateSZ(int val) {
    if (val == 0) {
      val = 20;
    } else {
      val = val * 2;
    }
    this.xml = this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":sz " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":sz>__GENERATERPR__");
  }

  protected void fGenerateU(String str) {
    String strVal;
    if ("".equals(str)) {
      strVal = "single";
    } else {
      strVal = str;
    }
    this.xml = this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":u " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":u>__GENERATERPR__");
  }

   
  protected void fGeneratePSTYLE(String str) {
    String strVal;
    if ("".equals(str)) {
      strVal = "Ttulo";
    } else {
      strVal = str;
    }
    this.xml = this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":pStyle " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":pStyle>__GENERATEPPR__");
  }

  protected void fGeneratePAGEBREAKBEFORE(String str) {
    String strVal;
    if ("".equals(str)) {
      strVal = "on";
    } else {
      strVal = str;
    }
    this.xml = this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":pageBreakBefore val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":pageBreakBefore>__GENERATEPPR__");
  }

  protected void fGenerateWIDOWCONTROL(String str) {
    String strVal;
    if ("".equals(str)) {
      strVal = "on";
    } else {
      strVal = str;
    }
    this.xml = this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":widowControl val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":widowControl>__GENERATEPPR__");
  }

  protected void fGenerateWORDWRAP(String str) {
    String strVal;
    if ("".equals(str)) {
      strVal = "on";
    } else {
      strVal = str;
    }
    this.xml = this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":wordWrap val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":wordWrap>__GENERATEPPR__");
  }

  protected void fGenerateBOOKMARKSTART(int intId, String name) {
    this.xml = this.xml = this.xml.replace("__GENERATER__", "<" + cCreateElement.NAMESPACE + ":bookmarkStart " + cCreateElement.NAMESPACE + ":id='" + intId + "' " + cCreateElement.NAMESPACE + ":name='" + name + "'></" + cCreateElement.NAMESPACE + ":bookmarkStart>__GENERATER____GENERATEBOOKMARKEND__");
  }

  protected void fGenerateBOOKMARKEND(int intId) {
    this.xml = this.xml = this.xml.replace("__GENERATEBOOKMARKEND__", "<" + cCreateElement.NAMESPACE + ":bookmarkEnd " + cCreateElement.NAMESPACE + ":id='" + intId + "'></" + cCreateElement.NAMESPACE + ":bookmarkEnd>");
  }

  public boolean fCreateTitle(HashMap arrArgs) {
    this.xml = "";
    if (arrArgs.containsKey("val")) {
      this.fGenerateP();
      this.fGeneratePPR();
      if (arrArgs.containsKey("type") && "subtitle".equals(arrArgs.get("type").toString())) {
        this.fGeneratePSTYLE("Subttulo" + arrArgs.get("val").toString());
      } else {
        this.fGeneratePSTYLE("Ttulo" + arrArgs.get("val").toString());
      }
      if (arrArgs.containsKey("pageBreakBefore")) {
        this.fGeneratePAGEBREAKBEFORE(arrArgs.get("pageBreakBefore").toString());
      }
      if (arrArgs.containsKey("widowControl")) {
        this.fGenerateWIDOWCONTROL(arrArgs.get("widowControl").toString());
      }
      if (arrArgs.containsKey("wordWrap")) {
        this.fGenerateWORDWRAP(arrArgs.get("wordWrap").toString());
      }
      this.intIdTitle++;
      this.fGenerateBOOKMARKSTART(this.intIdTitle, "_Toc" + (this.intIdTitle + this.IDTITLE));
      this.fGenerateR();
      if ((arrArgs.containsKey("b") && !"".equals(arrArgs.get("b").toString())) || (arrArgs.containsKey("i") && !"".equals(arrArgs.get("i").toString())) || (arrArgs.containsKey("u") && !"".equals(arrArgs.get("u").toString())) || (arrArgs.containsKey("sz") && !"".equals(arrArgs.get("sz").toString())) || (arrArgs.containsKey("color") && !"".equals(arrArgs.get("color").toString()))) {
        this.fGenerateRPR();
        if (arrArgs.containsKey("b") && !"".equals(arrArgs.get("b").toString())) {
          this.fGenerateB(arrArgs.get("b").toString());
        }
        if (arrArgs.containsKey("i") && !"".equals(arrArgs.get("i").toString())) {
          this.fGenerateI(arrArgs.get("i").toString());
        }
        if (arrArgs.containsKey("u") && !"".equals(arrArgs.get("u").toString())) {
          this.fGenerateU(arrArgs.get("u").toString());
        }
        if (arrArgs.containsKey("sz") && !"".equals(arrArgs.get("sz").toString())) {
          this.fGenerateSZ(Integer.parseInt(arrArgs.get("sz").toString()));
        }
        if (arrArgs.containsKey("color") && !"".equals(arrArgs.get("color").toString())) {
          this.fGenerateCOLOR(arrArgs.get("color").toString());
        }
      }
      this.fGenerateT(arrArgs.get("text").toString());
      this.fGenerateBOOKMARKEND(this.intIdTitle);
      this.fCleanTemplate();
      return true;
    } else {
    	DefaultLogger.debug(this,"You have not introduce the text or the type of the title.");
      return false;
    }
  }

  public void fCreateText(HashMap arrArgs) {
    if (arrArgs.containsKey("text")) {
      this.xml = "";
      this.fGenerateP();
      if (arrArgs.containsKey("jc") || arrArgs.containsKey("pageBreakBefore") || arrArgs.containsKey("widowControl") || arrArgs.containsKey("wordWrap")) {
        this.fGeneratePPR();
        if (arrArgs.containsKey("jc")) {
          this.fGenerateJC(arrArgs.get("jc").toString());
        }
        if (arrArgs.containsKey("pageBreakBefore")) {
          this.fGeneratePAGEBREAKBEFORE(arrArgs.get("pageBreakBefore").toString());
        }
        if (arrArgs.containsKey("widowControl")) {
          this.fGenerateWIDOWCONTROL(arrArgs.get("widowControl").toString());
        }
        if (arrArgs.containsKey("wordWrap")) {
          this.fGenerateWORDWRAP(arrArgs.get("wordWrap").toString());
        }
      }
      this.fGenerateR();
      if ((arrArgs.containsKey("b") && !"".equals(arrArgs.get("b"))) || (arrArgs.containsKey("i") && !"".equals(arrArgs.get("i"))) || (arrArgs.containsKey("u") && !"".equals(arrArgs.get("u"))) || (arrArgs.containsKey("sz") && !"".equals(arrArgs.get("sz"))) || (arrArgs.containsKey("color") && !"".equals(arrArgs.get("color")))) {
        this.fGenerateRPR();
        if (arrArgs.containsKey("b") && !"".equals(arrArgs.get("b"))) {
          this.fGenerateB(arrArgs.get("b").toString());
        }
        if (arrArgs.containsKey("i") && !"".equals(arrArgs.get("i"))) {
          this.fGenerateI(arrArgs.get("i").toString());
        }
        if (arrArgs.containsKey("u") && !"".equals(arrArgs.get("u"))) {
          this.fGenerateU(arrArgs.get("u").toString());
        }
        if (arrArgs.containsKey("sz") && !"".equals(arrArgs.get("sz"))) {
          this.fGenerateSZ(Integer.parseInt(arrArgs.get("sz").toString()));
        }
        if (arrArgs.containsKey("color") && !"".equals(arrArgs.get("color"))) {
          this.fGenerateCOLOR(arrArgs.get("color").toString());
        }
      }
      this.fGenerateT(arrArgs.get("text").toString());
    }
  }
}
