/**
 * Generate styles.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;

class cCreateStyle extends cCreateElement {

  private static cCreateStyle rscInstance = null;

  public static cCreateStyle getInstance() {
    if (cCreateStyle.rscInstance == null) {
      cCreateStyle.rscInstance = new cCreateStyle();
    }
    return cCreateStyle.rscInstance;
  }

   
  public String toString() {
    return this.xml;
  }

  protected void fGenerateSTYLE(String type, String styleId, String def, String customStyle) {
    this.xml = "<" + cCreateElement.NAMESPACE + ":style " + cCreateElement.NAMESPACE + ":type='" + type + "'";
    if (!def.equals("")) {
      this.xml += " " + cCreateElement.NAMESPACE + ":default='" + def + "'";
    }
    this.xml += " " + cCreateElement.NAMESPACE + ":styleId='" + styleId + "'";
    if (!customStyle.equals("")) {
      this.xml += " " + cCreateElement.NAMESPACE + ":customStyle='" + customStyle + "'";
    }
    this.xml += ">__GENERATESTYLE__</" + cCreateElement.NAMESPACE + ":style>";
  }

  protected void fGenerateNAME(String val) {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":name " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":name>__GENERATESTYLE__");
  }

  protected void fGenerateBASEDON(String val) {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":basedOn " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":basedOn>__GENERATESTYLE__");
  }

  protected void fGenerateNEXT(String val) {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":next " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":next>__GENERATESTYLE__");
  }

  protected void fGenerateLINK(String val) {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":link " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":link>__GENERATESTYLE__");
  }

  protected void fGenerateUIPRIORITY(String val) {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":uiPriority " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":uiPriority>__GENERATESTYLE__");
  }

  protected void fGenerateUNHIDEWHENUSED() {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":unhideWhenUsed></" + cCreateElement.NAMESPACE + ":unhideWhenUsed>__GENERATESTYLE__");
  }

  protected void fGenerateAUTOREDEFINE() {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":autoRedefine></" + cCreateElement.NAMESPACE + ":autoRedefine>__GENERATESTYLE__");
  }

  protected void fGenerateQFORMAT() {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":qFormat></" + cCreateElement.NAMESPACE + ":qFormat>__GENERATESTYLE__");
  }

  protected void fGenerateRSID(String val) {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":rsid " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":rsid>__GENERATESTYLE__");
  }

  protected void fGenerateSEMIHIDDEN() {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":semiHidden></" + cCreateElement.NAMESPACE + ":semiHidden>__GENERATESTYLE__");
  }

   
  protected void fGeneratePPR() {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":pPr>__GENERATEPPR__</" + cCreateElement.NAMESPACE + ":pPr>__GENERATESTYLE__");
  }

  protected void fGenerateKEEPNEXT() {
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":keepNext></" + cCreateElement.NAMESPACE + ":keepNext>__GENERATEPPR__");
  }

  protected void fGenerateTABS() {
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":tabs>__GENERATETABS__</" + cCreateElement.NAMESPACE + ":tabs>__GENERATEPPR__");
  }

  protected void fGenerateTABCENTER(String pos) {
    this.xml = this.xml.replace("__GENERATETABS__", "<" + cCreateElement.NAMESPACE + ":tab " + cCreateElement.NAMESPACE + ":val='center' " + cCreateElement.NAMESPACE + ":pos='" + pos + "'></" + cCreateElement.NAMESPACE + ":tab>__GENERATETABS__");
  }

  protected void fGenerateTABRIGHT(String pos) {
    this.xml = this.xml.replace("__GENERATETABS__", "<" + cCreateElement.NAMESPACE + ":tab " + cCreateElement.NAMESPACE + ":val='right' " + cCreateElement.NAMESPACE + ":pos='" + pos + "'></" + cCreateElement.NAMESPACE + ":tab>__GENERATETABS__");
  }

  protected void fGenerateNUMPR() {
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":numPr>__GENERATENUMPR__</" + cCreateElement.NAMESPACE + ":numPr>__GENERATEPPR__");
  }

  protected void fGenerateILVL(String val) {
    this.xml = this.xml.replace("__GENERATENUMPR__", "<" + cCreateElement.NAMESPACE + ":ilvl " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":ilvl>__GENERATENUMPR__");
  }

  protected void fGenerateKEEPLINES() {
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":keepLines></" + cCreateElement.NAMESPACE + ":keepLines>__GENERATEPPR__");
  }

  protected void fGeneratePBDR() {
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":pBdr>__GENERATEPBDR__</" + cCreateElement.NAMESPACE + ":pBdr>__GENERATEPPR__");
  }

  protected void fGeneratePBDR_BOTTOM(String val, String sz, String space, String color, String themeColor) {
    this.xml = this.xml.replace("__GENERATEPBDR__", "<" + cCreateElement.NAMESPACE + ":pBdr " + cCreateElement.NAMESPACE + ":val='" + val + "' " + cCreateElement.NAMESPACE + ":sz='" + sz + "' " + cCreateElement.NAMESPACE + ":space='" + space + "' " + cCreateElement.NAMESPACE + ":color='" + color + "' " + cCreateElement.NAMESPACE + ":themeColor='" + themeColor + "'></" + cCreateElement.NAMESPACE + ":pBdr>__GENERATEPBDR__");
  }

  protected void fGenerateSPACING(String before, String after, String line, String lineRule) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":spacing";
    if (!before.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":before='" + before + "'";
    }
    if (!after.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":after='" + after + "'";
    }
    if (!line.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":line='" + line + "'";
    }
    if (!lineRule.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":lineRule='" + lineRule + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":spacing>__GENERATEPPR__";
    this.xml = this.xml.replace("__GENERATEPPR__", xmlAux);
  }

  protected void fGenerateRPR_SPACING(String val) {
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":spacing " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":spacing>__GENERATERPR__");
  }

  protected void fGenerateKERN(String val) {
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":kern " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":kern>__GENERATERPR__");
  }

  protected void fGenerateCONTEXTUALSPACING() {
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":contextualSpacing></" + cCreateElement.NAMESPACE + ":contextualSpacing>__GENERATEPPR__");
  }

  protected void fGenerateOUTLINELVL(String val) {
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":outlineLvl " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":outlineLvl>__GENERATEPPR__");
  }

  protected void fGenerateIND(String left) {
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":ind " + cCreateElement.NAMESPACE + ":left='" + left + "'></" + cCreateElement.NAMESPACE + ":ind>__GENERATEPPR__");
  }

  protected void fGenerateTCPR() {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":tcPr>__GENERATETCPR__</" + cCreateElement.NAMESPACE + ":tcPr>__GENERATESTYLE__");
  }

  protected void fGenerateSHD(String val, String color, String fill, String themeFill, String themeFillTint) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":shd " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    if (!color.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":color='" + color + "'";
    }
    if (!fill.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":fill='" + fill + "'";
    }
    if (!themeFill.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeFill='" + themeFill + "'";
    }
    if (!themeFillTint.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeFillTint='" + themeFillTint + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":shd>__GENERATETCPR__";
    this.xml = this.xml.replace("__GENERATETCPR__", xmlAux);
  }

  protected void fGenerateTBLPR() {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":tblPr>__GENERATETBLPR__</" + cCreateElement.NAMESPACE + ":tblPr>__GENERATESTYLE__");
  }

  protected void fGenerateTBLIND(String w, String type) {
    this.xml = this.xml.replace("__GENERATETBLPR__", "<" + cCreateElement.NAMESPACE + ":tblInd " + cCreateElement.NAMESPACE + ":w='" + w + "' " + cCreateElement.NAMESPACE + ":type='" + type + "'></" + cCreateElement.NAMESPACE + ":tblInd>__GENERATETBLPR__");
  }

  protected void fGenerateTBLSTYLEROWBANDSIZE(String val) {
    this.xml = this.xml.replace("__GENERATETBLPR__", "<" + cCreateElement.NAMESPACE + ":tblStyleRowBandSize " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":tblStyleRowBandSize>__GENERATETBLPR__");
  }

  protected void fGenerateTBLSTYLECOLBANDSIZE(String val) {
    this.xml = this.xml.replace("__GENERATETBLPR__", "<" + cCreateElement.NAMESPACE + ":tblStyleColBandSize " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":tblStyleColBandSize>__GENERATETBLPR__");
  }

  protected void fGenerateTBLBORDERS() {
    this.xml = this.xml.replace("__GENERATETBLPR__", "<" + cCreateElement.NAMESPACE + ":tblBorders>__GENERATETBLBORDERS__</" + cCreateElement.NAMESPACE + ":tblBorders>__GENERATETBLPR__");
  }

  protected void fGenerateTBLBORDERS_TOP(String val, String sz, String space, String color, String themeColor, String themeTint) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":top " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    if (!sz.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":sz='" + sz + "'";
    }
    if (!space.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":space='" + space + "'";
    }
    if (!color.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":color='" + color + "'";
    }
    if (!themeColor.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeColor='" + themeColor + "'";
    }
    if (!themeTint.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeTint='" + themeTint + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":top>__GENERATETBLBORDERS__";
    this.xml = this.xml.replace("__GENERATETBLBORDERS__", xmlAux);
  }

  protected void fGenerateTBLBORDERS_LEFT(String val, String sz, String space, String color, String themeColor, String themeTint) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":left " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    if (!sz.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":sz='" + sz + "'";
    }
    if (!space.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":space='" + space + "'";
    }
    if (!color.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":color='" + color + "'";
    }
    if (!themeColor.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeColor='" + themeColor + "'";
    }
    if (!themeTint.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeTint='" + themeTint + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":left>__GENERATETBLBORDERS__";
    this.xml = this.xml.replace("__GENERATETBLBORDERS__", xmlAux);
  }

  protected void fGenerateTBLBORDERS_BOTTOM(String val, String sz, String space, String color, String themeColor, String themeTint) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":bottom " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    if (!sz.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":sz='" + sz + "'";
    }
    if (!space.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":space='" + space + "'";
    }
    if (!color.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":color='" + color + "'";
    }
    if (!themeColor.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeColor='" + themeColor + "'";
    }
    if (!themeTint.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeTint='" + themeTint + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":bottom>__GENERATETBLBORDERS__";
    this.xml = this.xml.replace("__GENERATETBLBORDERS__", xmlAux);
  }

  protected void fGenerateTBLBORDERS_RIGHT(String val, String sz, String space, String color, String themeColor, String themeTint) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":right " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    if (!sz.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":sz='" + sz + "'";
    }
    if (!space.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":space='" + space + "'";
    }
    if (!color.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":color='" + color + "'";
    }
    if (!themeColor.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeColor='" + themeColor + "'";
    }
    if (!themeTint.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeTint='" + themeTint + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":right>__GENERATETBLBORDERS__";
    this.xml = this.xml.replace("__GENERATETBLBORDERS__", xmlAux);
  }

  protected void fGenerateTBLBORDERS_INSIDEH(String val, String sz, String space, String color, String themeColor, String themeTint) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":insideH " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    if (!sz.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":sz='" + sz + "'";
    }
    if (!space.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":space='" + space + "'";
    }
    if (!color.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":color='" + color + "'";
    }
    if (!themeColor.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeColor='" + themeColor + "'";
    }
    if (!themeTint.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeTint='" + themeTint + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":insideH>__GENERATETBLBORDERS__";
    this.xml = this.xml.replace("__GENERATETBLBORDERS__", xmlAux);
  }

  protected void fGenerateTBLBORDERS_INSIDEV(String val, String sz, String space, String color, String themeColor, String themeTint) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":insideV " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    if (!sz.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":sz='" + sz + "'";
    }
    if (!space.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":space='" + space + "'";
    }
    if (!color.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":color='" + color + "'";
    }
    if (!themeColor.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeColor='" + themeColor + "'";
    }
    if (!themeTint.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeTint='" + themeTint + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":insideV>__GENERATETBLBORDERS__";
    this.xml = this.xml.replace("__GENERATETBLBORDERS__", xmlAux);
  }

  protected void fGenerateTBLCELLMAR() {
    this.xml = this.xml.replace("__GENERATETBLPR__", "<" + cCreateElement.NAMESPACE + ":tblCellMar>__GENERATETBLCELLMAR__</" + cCreateElement.NAMESPACE + ":tblCellMar>__GENERATETBLPR__");
  }

  protected void fGenerateTOP(String w, String type) {
    this.xml = this.xml.replace("__GENERATETBLCELLMAR__", "<" + cCreateElement.NAMESPACE + ":top " + cCreateElement.NAMESPACE + ":w='" + w + "' " + cCreateElement.NAMESPACE + ":type='" + type + "'></" + cCreateElement.NAMESPACE + ":top>__GENERATETBLCELLMAR__");
  }

  protected void fGenerateLEFT(String w, String type) {
    this.xml = this.xml.replace("__GENERATETBLCELLMAR__", "<" + cCreateElement.NAMESPACE + ":left " + cCreateElement.NAMESPACE + ":w='" + w + "' " + cCreateElement.NAMESPACE + ":type='" + type + "'></" + cCreateElement.NAMESPACE + ":left>__GENERATETBLCELLMAR__");
  }

  protected void fGenerateBOTTOM(String w, String type) {
    this.xml = this.xml.replace("__GENERATETBLCELLMAR__", "<" + cCreateElement.NAMESPACE + ":bottom " + cCreateElement.NAMESPACE + ":w='" + w + "' " + cCreateElement.NAMESPACE + ":type='" + type + "'></" + cCreateElement.NAMESPACE + ":bottom>__GENERATETBLCELLMAR__");
  }

  protected void fGenerateRIGHT(String w, String type) {
    this.xml = this.xml.replace("__GENERATETBLCELLMAR__", "<" + cCreateElement.NAMESPACE + ":right " + cCreateElement.NAMESPACE + ":w='" + w + "' " + cCreateElement.NAMESPACE + ":type='" + type + "'></" + cCreateElement.NAMESPACE + ":right>__GENERATETBLCELLMAR__");
  }

   
  protected void fGenerateRPR() {
    try {
      this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":rPr>__GENERATERPR__</" + cCreateElement.NAMESPACE + ":rPr>__GENERATESTYLE__");
    } catch (Exception e) {
    	DefaultLogger.debug(this,"There a exception when generate the style file:" + e.toString() + "\n" + e.getMessage() + "\n" + e.getStackTrace());
    }
  }

  protected void fGenerateRFONTS(String asciiTheme, String eastAsiaTheme, String hAnsiTheme, String cstheme) {
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":rFonts " + cCreateElement.NAMESPACE + ":asciiTheme='" + asciiTheme + "' " + cCreateElement.NAMESPACE + ":eastAsiaTheme='" + eastAsiaTheme + "' " + cCreateElement.NAMESPACE + ":hAnsiTheme='" + hAnsiTheme + "' " + cCreateElement.NAMESPACE + ":cstheme='" + cstheme + "'></" + cCreateElement.NAMESPACE + ":rFonts>__GENERATERPR__");
  }

  protected void fGenerateRFONTS2(String ascii, String hAnsi, String cs) {
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":rFonts " + cCreateElement.NAMESPACE + ":ascii='" + ascii + "' " + cCreateElement.NAMESPACE + ":hAnsi='" + hAnsi + "' " + cCreateElement.NAMESPACE + ":cs='" + cs + "'></" + cCreateElement.NAMESPACE + ":rFonts>__GENERATERPR__");
  }

  protected void fGenerateB() {
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":b></" + cCreateElement.NAMESPACE + ":b>__GENERATERPR__");
  }

   
  protected void fGenerateBCS() {
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":bCs></" + cCreateElement.NAMESPACE + ":bCs>__GENERATERPR__");
  }

  protected void fGenerateI(String val) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":i";
    if (!val.equals("1") && !val.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":i>__GENERATERPR__";
    this.xml = this.xml.replace("__GENERATERPR__", xmlAux);
  }

  protected void fGenerateICS(String val) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":iCs";
    if (!val.equals("1") && !val.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":iCs>__GENERATERPR__";
    this.xml = this.xml.replace("__GENERATERPR__", xmlAux);
  }

  protected void fGenerateU(String val) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":u";
    if (!val.equals("1") && !val.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":u>__GENERATERPR__";

    this.xml = this.xml.replace("__GENERATERPR__", xmlAux);
  }

  protected void fGenerateCOLOR(String val, String themeColor, String themeShade) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":color " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    if (!themeColor.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeColor='" + themeColor + "'";
    }
    if (!themeShade.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeShade='" + themeShade + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":color>__GENERATERPR__";
    this.xml = this.xml.replace("__GENERATERPR__", xmlAux);
  }

  protected void fGenerateSZ(String val) {
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":sz " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":sz>__GENERATERPR__");
  }

  protected void fGenerateSZCS(String val) {
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":szCs " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":szCs>__GENERATERPR__");
  }

  protected void fGenerateVERTALIGN(String val) {
    if (val.equals("")) {
      val = "superscript";
    }
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":vertAlign " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":vertAlign>__GENERATERPR__");
  }

  public void fAddStyle(ArrayList arrArgs) {
    HashMap arrParam = (HashMap) arrArgs.get(0);
    if (arrParam.containsKey("type") && arrParam.containsKey("styleId")) {
      String def = "";
      if (arrParam.containsKey("def")) {
        def = arrParam.get("def").toString();
      }
      String customStyle = "";
      if (arrParam.containsKey("customStyle")) {
        customStyle = arrParam.get("customStyle").toString();
      }
      this.fGenerateSTYLE(arrParam.get("type").toString(), arrParam.get("styleId").toString(), def, customStyle);
      if (arrParam.containsKey("name")) {
        this.fGenerateNAME(arrParam.get("name").toString());
      }
      if (arrParam.containsKey("basedOn")) {
        this.fGenerateBASEDON(arrParam.get("basedOn").toString());
      }
      if (arrParam.containsKey("next")) {
        this.fGenerateNEXT(arrParam.get("next").toString());
      }
      if (arrParam.containsKey("link")) {
        this.fGenerateLINK(arrParam.get("link").toString());
      }
      if (arrParam.containsKey("autoRedefine") && arrParam.get("autoRedefine").toString() == "1") {
        this.fGenerateAUTOREDEFINE();
      }
      if (arrParam.containsKey("uiPriority")) {
        this.fGenerateUIPRIORITY(arrParam.get("uiPriority").toString());
      }
      if (arrParam.containsKey("semiHidden") && arrParam.get("semiHidden").toString() == "1") {
        this.fGenerateSEMIHIDDEN();
      }
      if (arrParam.containsKey("unhideWhenUsed") && arrParam.get("unhideWhenUsed").toString() == "1") {
        this.fGenerateUNHIDEWHENUSED();
      }
      if (arrParam.containsKey("qFormat") && arrParam.get("qFormat").toString() == "1") {
        this.fGenerateQFORMAT();
      }
      if (arrParam.containsKey("rsid")) {
        this.fGenerateRSID(arrParam.get("rsid").toString());
      }
      if ((arrParam.containsKey("keepNext") && arrParam.get("keepNext").toString() == "1") || arrParam.containsKey("keepLines") || arrParam.containsKey("spacing_before") || arrParam.containsKey("spacing_after") || arrParam.containsKey("spacing_line")
              || arrParam.containsKey("spacing_lineRule") || arrParam.containsKey("outlineLvl") || (arrParam.containsKey("contextualSpacing") && arrParam.get("contextualSpacing").toString() == "1") || arrParam.containsKey("ilvl") || arrParam.containsKey("ind_left")) {
        this.fGeneratePPR();
        if (arrParam.containsKey("keepNext") && arrParam.get("keepNext").toString() == "1") {
          this.fGenerateKEEPNEXT();
        }
        if (arrParam.containsKey("keepLines") && arrParam.get("keepLines").toString() == "1") {
          this.fGenerateKEEPLINES();
        }
        if (arrParam.containsKey("pBdr_bottom_val") && arrParam.containsKey("pBdr_bottom_sz") && arrParam.containsKey("pBdr_bottom_space") && arrParam.containsKey("pBdr_bottom_color") && arrParam.containsKey("pBdr_bottom_themeColor")) {
          this.fGeneratePBDR();
          this.fGeneratePBDR_BOTTOM(arrParam.get("pBdr_bottom_val").toString(), arrParam.get("pBdr_bottom_sz").toString(), arrParam.get("pBdr_bottom_space").toString(), arrParam.get("pBdr_bottom_color").toString(), arrParam.get("pBdr_bottom_themeColor").toString());
        }
        if (arrParam.containsKey("tab_center") || arrParam.containsKey("tab_right")) {
          this.fGenerateTABS();
          if (arrParam.containsKey("tab_center")) {
            this.fGenerateTABCENTER(arrParam.get("tab_center").toString());
          }
          if (arrParam.containsKey("tab_right")) {
            this.fGenerateTABRIGHT(arrParam.get("tab_right").toString());
          }
        }
        if (arrParam.containsKey("spacing_before") || arrParam.containsKey("spacing_after") || arrParam.containsKey("spacing_line") || arrParam.containsKey("spacing_lineRule")) {
          String spacing_before = "";
          if (arrParam.containsKey("spacing_before")) {
            spacing_before = arrParam.get("spacing_before").toString();
          }
          String spacing_after = "";
          if (arrParam.containsKey("spacing_after")) {
            spacing_after = arrParam.get("spacing_after").toString();
          }
          String spacing_line = "";
          if (arrParam.containsKey("spacing_line")) {
            spacing_line = arrParam.get("spacing_line").toString();
          }
          String spacing_lineRule = "";
          if (arrParam.containsKey("spacing_lineRule")) {
            spacing_lineRule = arrParam.get("spacing_lineRule").toString();
          }
          this.fGenerateSPACING(spacing_before, spacing_after, spacing_line, spacing_lineRule);
        }

        if (arrParam.containsKey("ind_left")) {
          this.fGenerateIND(arrParam.get("ind_left").toString());
        }
        if (arrParam.containsKey("contextualSpacing") && arrParam.get("contextualSpacing").toString() == "1") {
          this.fGenerateCONTEXTUALSPACING();
        }
        if (arrParam.containsKey("outlineLvl")) {
          this.fGenerateOUTLINELVL(arrParam.get("outlineLvl").toString());
        }
        if (arrParam.containsKey("ilvl")) {
          this.fGenerateNUMPR();
          this.fGenerateILVL(arrParam.get("ilvl").toString());
        }
      }
      if ((arrParam.containsKey("rFonts_asciiTheme") && arrParam.containsKey("rFonts_eastAsiaTheme") && arrParam.containsKey("rFonts_hAnsiTheme") && arrParam.containsKey("rFonts_cstheme")) || (arrParam.containsKey("b") && arrParam.get("b").toString() == "1")
              || (arrParam.containsKey("bCs") && arrParam.get("bCs").toString() == "1") || (arrParam.containsKey("i") && arrParam.get("i").toString() == "1") || (arrParam.containsKey("iCs") && arrParam.get("iCs").toString() == "1") || (arrParam.containsKey("u") && arrParam.get("u").toString() == "1") || arrParam.containsKey("color_val") || arrParam.containsKey("sz") || arrParam.containsKey("szCs") || arrParam.containsKey("kern") || arrParam.containsKey("rPr_spacing") || (arrParam.containsKey("u") && arrParam.get("u").toString() == "1") || arrParam.containsKey("vertAlign")) {
        this.fGenerateRPR();
        if (arrParam.containsKey("rFonts_asciiTheme") && arrParam.containsKey("rFonts_eastAsiaTheme") && arrParam.containsKey("rFonts_hAnsiTheme") && arrParam.containsKey("rFonts_cstheme")) {
          this.fGenerateRFONTS(arrParam.get("rFonts_asciiTheme").toString(), arrParam.get("rFonts_eastAsiaTheme").toString(), arrParam.get("rFonts_hAnsiTheme").toString(), arrParam.get("rFonts_cstheme").toString());
        }
        if (arrParam.containsKey("rFonts_ascii") && arrParam.containsKey("rFonts_hAnsi") && arrParam.containsKey("rFonts_cs")) {
          this.fGenerateRFONTS2(arrParam.get("rFonts_ascii").toString(), arrParam.get("rFonts_hAnsi").toString(), arrParam.get("rFonts_cs").toString());
        }
        if (arrParam.containsKey("b") && arrParam.get("b").toString() == "1") {
          this.fGenerateB();
        }
        if (arrParam.containsKey("bCs") && arrParam.get("bCs").toString() == "1") {
          this.fGenerateBCS();
        }
        if (arrParam.containsKey("i") && arrParam.get("i").toString() == "1") {
          this.fGenerateI(arrParam.get("i").toString());
        }
        if (arrParam.containsKey("iCs") && arrParam.get("iCs").toString() == "1") {
          this.fGenerateICS(arrParam.get("iCs").toString());
        }
        if (arrParam.containsKey("u") && arrParam.get("u").toString() == "1") {
          this.fGenerateU("");
        }
        if (arrParam.containsKey("color_val")) {
          String color_themeColor = "";
          if (arrParam.containsKey("color_themeColor")) {
            color_themeColor = arrParam.get("color_themeColor").toString();
          }
          String color_themeShade = "";
          if (arrParam.containsKey("color_themeShade")) {
            color_themeShade = arrParam.get("color_themeShade").toString();
          }
          this.fGenerateCOLOR(arrParam.get("color_val").toString(), color_themeColor, color_themeShade);
        }
        if (arrParam.containsKey("u") && arrParam.get("u").toString() == "1") {
          this.fGenerateU(arrParam.get("u").toString());
        }
        if (arrParam.containsKey("rPr_spacing")) {
          this.fGenerateRPR_SPACING(arrParam.get("rPr_spacing").toString());
        }
        if (arrParam.containsKey("kern")) {
          this.fGenerateKERN(arrParam.get("kern").toString());
        }
        if (arrParam.containsKey("sz")) {
          this.fGenerateSZ(arrParam.get("sz").toString());
        }
        if (arrParam.containsKey("szCs")) {
          this.fGenerateSZCS(arrParam.get("szCs").toString());
        }
        if (arrParam.containsKey("vertAlign")) {
          this.fGenerateVERTALIGN(arrParam.get("vertAlign").toString());
        }
      }
      if ((arrParam.containsKey("top_w") && arrParam.containsKey("top_type")) || (arrParam.containsKey("left_w") && arrParam.containsKey("left_type")) || (arrParam.containsKey("bottom_w")
              && arrParam.containsKey("bottom_type")) || (arrParam.containsKey("right_w") && arrParam.containsKey("right_type")) || (arrParam.containsKey("tblInd_w") && arrParam.containsKey("tblInd_type"))
              && (arrParam.containsKey("tblborder_top_val")) || (arrParam.containsKey("tblborder_left_val")) || (arrParam.containsKey("tblborder_bottom_val")) || (arrParam.containsKey("tblborder_right_val"))
              || (arrParam.containsKey("tblborder_insideH_val")) || (arrParam.containsKey("tblborder_insideV_val"))) {
        this.fGenerateTBLPR();
        if (arrParam.containsKey("tbl_style_row")) {
          this.fGenerateTBLSTYLEROWBANDSIZE(arrParam.get("tbl_style_row").toString());
        }
        if (arrParam.containsKey("tbl_style_col")) {
          this.fGenerateTBLSTYLECOLBANDSIZE(arrParam.get("tbl_style_col").toString());
        }
        if (arrParam.containsKey("tblInd_w") && arrParam.containsKey("tblInd_type")) {
          this.fGenerateTBLIND(arrParam.get("tblInd_w").toString(), arrParam.get("tblInd_type").toString());
        }
        if ((arrParam.containsKey("tblborder_top_val")) || (arrParam.containsKey("tblborder_left_val")) || (arrParam.containsKey("tblborder_bottom_val")) || (arrParam.containsKey("tblborder_right_val"))
                || (arrParam.containsKey("tblborder_insideH_val")) || (arrParam.containsKey("tblborder_insideV_val"))) {
          this.fGenerateTBLBORDERS();
          if (arrParam.containsKey("tblborder_top_val")) {
            String tblborder_top_sz = "";
            if (arrParam.containsKey("tblborder_top_sz")) {
              tblborder_top_sz = arrParam.get("tblborder_top_sz").toString();
            }
            String tblborder_top_space = "";
            if (arrParam.containsKey("tblborder_top_space")) {
              tblborder_top_space = arrParam.get("tblborder_top_space").toString();
            }
            String tblborder_top_color = "";
            if (arrParam.containsKey("tblborder_top_color")) {
              tblborder_top_color = arrParam.get("tblborder_top_color").toString();
            }
            String tblborder_top_themeColor = "";
            if (arrParam.containsKey("tblborder_top_themeColor")) {
              tblborder_top_themeColor = arrParam.get("tblborder_top_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrParam.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrParam.get("tblborder_insideV_themeTint").toString();
            }

            this.fGenerateTBLBORDERS_TOP(arrParam.get("tblborder_top_val").toString(), tblborder_top_sz, tblborder_top_space, tblborder_top_color, tblborder_top_themeColor, tblborder_insideV_themeTint);
          }
          if (arrParam.containsKey("tblborder_left_val")) {
            String tblborder_left_sz = "";
            if (arrParam.containsKey("tblborder_left_sz")) {
              tblborder_left_sz = arrParam.get("tblborder_left_sz").toString();
            }
            String tblborder_left_space = "";
            if (arrParam.containsKey("tblborder_left_space")) {
              tblborder_left_space = arrParam.get("tblborder_left_space").toString();
            }
            String tblborder_left_color = "";
            if (arrParam.containsKey("tblborder_left_color")) {
              tblborder_left_color = arrParam.get("tblborder_left_color").toString();
            }
            String tblborder_left_themeColor = "";
            if (arrParam.containsKey("tblborder_left_themeColor")) {
              tblborder_left_themeColor = arrParam.get("tblborder_left_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrParam.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrParam.get("tblborder_insideV_themeTint").toString();
            }
            this.fGenerateTBLBORDERS_LEFT(arrParam.get("tblborder_left_val").toString(), tblborder_left_sz, tblborder_left_space, tblborder_left_color, tblborder_left_themeColor, tblborder_insideV_themeTint);
          }
          if (arrParam.containsKey("tblborder_bottom_val")) {
            String tblborder_bottom_sz = "";
            if (arrParam.containsKey("tblborder_bottom_sz")) {
              tblborder_bottom_sz = arrParam.get("tblborder_bottom_sz").toString();
            }
            String tblborder_bottom_space = "";
            if (arrParam.containsKey("tblborder_bottom_space")) {
              tblborder_bottom_space = arrParam.get("tblborder_bottom_space").toString();
            }
            String tblborder_bottom_color = "";
            if (arrParam.containsKey("tblborder_bottom_color")) {
              tblborder_bottom_color = arrParam.get("tblborder_bottom_color").toString();
            }
            String tblborder_bottom_themeColor = "";
            if (arrParam.containsKey("tblborder_bottom_themeColor")) {
              tblborder_bottom_themeColor = arrParam.get("tblborder_bottom_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrParam.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrParam.get("tblborder_insideV_themeTint").toString();
            }
            this.fGenerateTBLBORDERS_BOTTOM(arrParam.get("tblborder_bottom_val").toString(), tblborder_bottom_sz, tblborder_bottom_space, tblborder_bottom_color, tblborder_bottom_themeColor, tblborder_insideV_themeTint);
          }
          if (arrParam.containsKey("tblborder_right_val")) {
            String tblborder_right_sz = "";
            if (arrParam.containsKey("tblborder_right_sz")) {
              tblborder_right_sz = arrParam.get("tblborder_right_sz").toString();
            }
            String tblborder_right_space = "";
            if (arrParam.containsKey("tblborder_right_space")) {
              tblborder_right_space = arrParam.get("tblborder_right_space").toString();
            }
            String tblborder_right_color = "";
            if (arrParam.containsKey("tblborder_right_color")) {
              tblborder_right_color = arrParam.get("tblborder_right_color").toString();
            }
            String tblborder_right_themeColor = "";
            if (arrParam.containsKey("tblborder_right_themeColor")) {
              tblborder_right_themeColor = arrParam.get("tblborder_right_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrParam.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrParam.get("tblborder_insideV_themeTint").toString();
            }
            this.fGenerateTBLBORDERS_RIGHT(arrParam.get("tblborder_right_val").toString(), tblborder_right_sz, tblborder_right_space, tblborder_right_color, tblborder_right_themeColor, tblborder_insideV_themeTint);
          }
          if (arrParam.containsKey("tblborder_insideH_val")) {
            String tblborder_insideH_sz = "";
            if (arrParam.containsKey("tblborder_insideH_sz")) {
              tblborder_insideH_sz = arrParam.get("tblborder_insideH_sz").toString();
            }
            String tblborder_insideH_space = "";
            if (arrParam.containsKey("tblborder_insideH_space")) {
              tblborder_insideH_space = arrParam.get("tblborder_insideH_space").toString();
            }
            String tblborder_insideH_color = "";
            if (arrParam.containsKey("tblborder_insideH_color")) {
              tblborder_insideH_color = arrParam.get("tblborder_insideH_color").toString();
            }
            String tblborder_insideH_themeColor = "";
            if (arrParam.containsKey("tblborder_insideH_themeColor")) {
              tblborder_insideH_themeColor = arrParam.get("tblborder_insideH_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrParam.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrParam.get("tblborder_insideV_themeTint").toString();
            }
            this.fGenerateTBLBORDERS_INSIDEH(arrParam.get("tblborder_insideH_val").toString(), tblborder_insideH_sz, tblborder_insideH_space, tblborder_insideH_color, tblborder_insideH_themeColor, tblborder_insideV_themeTint);
          }
          if (arrParam.containsKey("tblborder_insideV_val")) {
            String tblborder_insideV_sz = "";
            if (arrParam.containsKey("tblborder_insideV_sz")) {
              tblborder_insideV_sz = arrParam.get("tblborder_insideV_sz").toString();
            }
            String tblborder_insideV_space = "";
            if (arrParam.containsKey("tblborder_insideV_space")) {
              tblborder_insideV_space = arrParam.get("tblborder_insideV_space").toString();
            }
            String tblborder_insideV_color = "";
            if (arrParam.containsKey("tblborder_insideV_color")) {
              tblborder_insideV_color = arrParam.get("tblborder_insideV_color").toString();
            }
            String tblborder_insideV_themeColor = "";
            if (arrParam.containsKey("tblborder_insideV_themeColor")) {
              tblborder_insideV_themeColor = arrParam.get("tblborder_insideV_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrParam.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrParam.get("tblborder_insideV_themeTint").toString();
            }
            this.fGenerateTBLBORDERS_INSIDEV(arrParam.get("tblborder_insideV_val").toString(), tblborder_insideV_sz, tblborder_insideV_space, tblborder_insideV_color, tblborder_insideV_themeColor, tblborder_insideV_themeTint);
          }
          //}
        }
        if ((arrParam.containsKey("top_w") && arrParam.containsKey("top_type")) || (arrParam.containsKey("left_w") && arrParam.containsKey("left_type")) || (arrParam.containsKey("bottom_w") && arrParam.containsKey("bottom_type")) || (arrParam.containsKey("right_w") && arrParam.containsKey("right_type"))) {
          this.fGenerateTBLCELLMAR();
          if (arrParam.containsKey("top_w") && arrParam.containsKey("top_type")) {
            this.fGenerateTOP(arrParam.get("top_w").toString(), arrParam.get("top_type").toString());
          }
          if (arrParam.containsKey("left_w") && arrParam.containsKey("left_type")) {
            this.fGenerateLEFT(arrParam.get("left_w").toString(), arrParam.get("left_type").toString());
          }
          if (arrParam.containsKey("bottom_w") && arrParam.containsKey("bottom_type")) {
            this.fGenerateBOTTOM(arrParam.get("bottom_w").toString(), arrParam.get("bottom_type").toString());
          }
          if (arrParam.containsKey("right_w") && arrParam.containsKey("right_type")) {
            this.fGenerateRIGHT(arrParam.get("right_w").toString(), arrParam.get("right_type").toString());
          }
        }
      }
      if (arrParam.containsKey("shd_val") && arrParam.containsKey("shd_color") && arrParam.containsKey("shd_fill") && arrParam.containsKey("shd_themeFill")) {
        this.fGenerateTCPR();
        this.fGenerateSHD(arrParam.get("shd_val").toString(), arrParam.get("shd_color").toString(), arrParam.get("shd_fill").toString(), arrParam.get("shd_themeFill").toString(), arrParam.get("shd_themeFillTint").toString());
      }
      if (arrArgs.size() > 1) {
        for (int i = 1; i < arrArgs.size(); i++) {
          this.fAddStyleTabla((HashMap) arrArgs.get(i));
        }
      }
      this.fCleanTemplate();
      //this.xml = this.xml.replaceAll("__[A-Z]*__", "");
    }
  }

  public void fCreateStyle(String language) {
    if (language.equals("")) {
      language = "es-ES";
    }
    this.xml += "<w:docDefaults><w:rPrDefault><w:rPr><w:rFonts w:asciiTheme='minorHAnsi' w:eastAsiaTheme='minorHAnsi' w:hAnsiTheme='minorHAnsi' w:cstheme='minorBidi'/><w:sz w:val='22'/><w:szCs w:val='22'/>"
            + "<w:lang w:val='" + language + "' w:eastAsia='" + language + "' w:bidi='ar-SA'/></w:rPr></w:rPrDefault><w:pPrDefault><w:pPr><w:spacing w:after='200' w:line='276' w:lineRule='auto'/></w:pPr></w:pPrDefault></w:docDefaults>"
            + "<w:latentStyles w:defLockedState='0' w:defUIPriority='99' w:defSemiHidden='1' w:defUnhideWhenUsed='1' w:defQFormat='0' w:count='267'>"
            + "<w:lsdException w:name='Normal' w:semiHidden='0' w:uiPriority='0' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='heading 1' w:semiHidden='0' w:uiPriority='9' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='heading 2' w:uiPriority='9' w:qFormat='1'/><w:lsdException w:name='heading 3' w:uiPriority='9' w:qFormat='1'/><w:lsdException w:name='heading 4' w:uiPriority='9' w:qFormat='1'/><w:lsdException w:name='heading 5' w:uiPriority='9' w:qFormat='1'/><w:lsdException w:name='heading 6' w:uiPriority='9' w:qFormat='1'/><w:lsdException w:name='heading 7' w:uiPriority='9' w:qFormat='1'/><w:lsdException w:name='heading 8' w:uiPriority='9' w:qFormat='1'/><w:lsdException w:name='heading 9' w:uiPriority='9' w:qFormat='1'/><w:lsdException w:name='toc 1' w:uiPriority='39'/><w:lsdException w:name='toc 2' w:uiPriority='39'/><w:lsdException w:name='toc 3' w:uiPriority='39'/><w:lsdException w:name='toc 4' w:uiPriority='39'/><w:lsdException w:name='toc 5' w:uiPriority='39'/><w:lsdException w:name='toc 6' w:uiPriority='39'/><w:lsdException w:name='toc 7' w:uiPriority='39'/><w:lsdException w:name='toc 8' w:uiPriority='39'/><w:lsdException w:name='toc 9' w:uiPriority='39'/><w:lsdException w:name='caption' w:uiPriority='35' w:qFormat='1'/><w:lsdException w:name='Title' w:semiHidden='0' w:uiPriority='10' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Default Paragraph Font' w:uiPriority='1'/><w:lsdException w:name='Subtitle' w:semiHidden='0' w:uiPriority='11' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Strong' w:semiHidden='0' w:uiPriority='22' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Emphasis' w:semiHidden='0' w:uiPriority='20' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Table Grid' w:semiHidden='0' w:uiPriority='59' w:unhideWhenUsed='0'/><w:lsdException w:name='Placeholder Text' w:unhideWhenUsed='0'/><w:lsdException w:name='No Spacing' w:semiHidden='0' w:uiPriority='1' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Light Shading' w:semiHidden='0' w:uiPriority='60' w:unhideWhenUsed='0'/><w:lsdException w:name='Light List' w:semiHidden='0' w:uiPriority='61' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Grid' w:semiHidden='0' w:uiPriority='62' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 1' w:semiHidden='0' w:uiPriority='63' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 2' w:semiHidden='0' w:uiPriority='64' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 1' w:semiHidden='0' w:uiPriority='65' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 2' w:semiHidden='0' w:uiPriority='66' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 1' w:semiHidden='0' w:uiPriority='67' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 2' w:semiHidden='0' w:uiPriority='68' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 3' w:semiHidden='0' w:uiPriority='69' w:unhideWhenUsed='0'/><w:lsdException w:name='Dark List' w:semiHidden='0' w:uiPriority='70' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Shading' w:semiHidden='0' w:uiPriority='71' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful List' w:semiHidden='0' w:uiPriority='72' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Grid' w:semiHidden='0' w:uiPriority='73' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Shading Accent 1' w:semiHidden='0' w:uiPriority='60' w:unhideWhenUsed='0'/><w:lsdException w:name='Light List Accent 1' w:semiHidden='0' w:uiPriority='61' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Grid Accent 1' w:semiHidden='0' w:uiPriority='62' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 1 Accent 1' w:semiHidden='0' w:uiPriority='63' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 2 Accent 1' w:semiHidden='0' w:uiPriority='64' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 1 Accent 1' w:semiHidden='0' w:uiPriority='65' w:unhideWhenUsed='0'/><w:lsdException w:name='Revision' w:unhideWhenUsed='0'/><w:lsdException w:name='List Paragraph' w:semiHidden='0' w:uiPriority='34' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Quote' w:semiHidden='0' w:uiPriority='29' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Intense Quote' w:semiHidden='0' w:uiPriority='30' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Medium List 2 Accent 1' w:semiHidden='0' w:uiPriority='66' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 1 Accent 1' w:semiHidden='0' w:uiPriority='67' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 2 Accent 1' w:semiHidden='0' w:uiPriority='68' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 3 Accent 1' w:semiHidden='0' w:uiPriority='69' w:unhideWhenUsed='0'/><w:lsdException w:name='Dark List Accent 1' w:semiHidden='0' w:uiPriority='70' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Shading Accent 1' w:semiHidden='0' w:uiPriority='71' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful List Accent 1' w:semiHidden='0' w:uiPriority='72' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Grid Accent 1' w:semiHidden='0' w:uiPriority='73' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Shading Accent 2' w:semiHidden='0' w:uiPriority='60' w:unhideWhenUsed='0'/><w:lsdException w:name='Light List Accent 2' w:semiHidden='0' w:uiPriority='61' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Grid Accent 2' w:semiHidden='0' w:uiPriority='62' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 1 Accent 2' w:semiHidden='0' w:uiPriority='63' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 2 Accent 2' w:semiHidden='0' w:uiPriority='64' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 1 Accent 2' w:semiHidden='0' w:uiPriority='65' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 2 Accent 2' w:semiHidden='0' w:uiPriority='66' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 1 Accent 2' w:semiHidden='0' w:uiPriority='67' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 2 Accent 2' w:semiHidden='0' w:uiPriority='68' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 3 Accent 2' w:semiHidden='0' w:uiPriority='69' w:unhideWhenUsed='0'/><w:lsdException w:name='Dark List Accent 2' w:semiHidden='0' w:uiPriority='70' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Shading Accent 2' w:semiHidden='0' w:uiPriority='71' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful List Accent 2' w:semiHidden='0' w:uiPriority='72' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Grid Accent 2' w:semiHidden='0' w:uiPriority='73' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Shading Accent 3' w:semiHidden='0' w:uiPriority='60' w:unhideWhenUsed='0'/><w:lsdException w:name='Light List Accent 3' w:semiHidden='0' w:uiPriority='61' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Grid Accent 3' w:semiHidden='0' w:uiPriority='62' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 1 Accent 3' w:semiHidden='0' w:uiPriority='63' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 2 Accent 3' w:semiHidden='0' w:uiPriority='64' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 1 Accent 3' w:semiHidden='0' w:uiPriority='65' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 2 Accent 3' w:semiHidden='0' w:uiPriority='66' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 1 Accent 3' w:semiHidden='0' w:uiPriority='67' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 2 Accent 3' w:semiHidden='0' w:uiPriority='68' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 3 Accent 3' w:semiHidden='0' w:uiPriority='69' w:unhideWhenUsed='0'/><w:lsdException w:name='Dark List Accent 3' w:semiHidden='0' w:uiPriority='70' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Shading Accent 3' w:semiHidden='0' w:uiPriority='71' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful List Accent 3' w:semiHidden='0' w:uiPriority='72' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Grid Accent 3' w:semiHidden='0' w:uiPriority='73' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Shading Accent 4' w:semiHidden='0' w:uiPriority='60' w:unhideWhenUsed='0'/><w:lsdException w:name='Light List Accent 4' w:semiHidden='0' w:uiPriority='61' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Grid Accent 4' w:semiHidden='0' w:uiPriority='62' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 1 Accent 4' w:semiHidden='0' w:uiPriority='63' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 2 Accent 4' w:semiHidden='0' w:uiPriority='64' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 1 Accent 4' w:semiHidden='0' w:uiPriority='65' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 2 Accent 4' w:semiHidden='0' w:uiPriority='66' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 1 Accent 4' w:semiHidden='0' w:uiPriority='67' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 2 Accent 4' w:semiHidden='0' w:uiPriority='68' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 3 Accent 4' w:semiHidden='0' w:uiPriority='69' w:unhideWhenUsed='0'/><w:lsdException w:name='Dark List Accent 4' w:semiHidden='0' w:uiPriority='70' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Shading Accent 4' w:semiHidden='0' w:uiPriority='71' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful List Accent 4' w:semiHidden='0' w:uiPriority='72' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Grid Accent 4' w:semiHidden='0' w:uiPriority='73' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Shading Accent 5' w:semiHidden='0' w:uiPriority='60' w:unhideWhenUsed='0'/><w:lsdException w:name='Light List Accent 5' w:semiHidden='0' w:uiPriority='61' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Grid Accent 5' w:semiHidden='0' w:uiPriority='62' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 1 Accent 5' w:semiHidden='0' w:uiPriority='63' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 2 Accent 5' w:semiHidden='0' w:uiPriority='64' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 1 Accent 5' w:semiHidden='0' w:uiPriority='65' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 2 Accent 5' w:semiHidden='0' w:uiPriority='66' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 1 Accent 5' w:semiHidden='0' w:uiPriority='67' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 2 Accent 5' w:semiHidden='0' w:uiPriority='68' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 3 Accent 5' w:semiHidden='0' w:uiPriority='69' w:unhideWhenUsed='0'/><w:lsdException w:name='Dark List Accent 5' w:semiHidden='0' w:uiPriority='70' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Shading Accent 5' w:semiHidden='0' w:uiPriority='71' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful List Accent 5' w:semiHidden='0' w:uiPriority='72' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Grid Accent 5' w:semiHidden='0' w:uiPriority='73' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Shading Accent 6' w:semiHidden='0' w:uiPriority='60' w:unhideWhenUsed='0'/><w:lsdException w:name='Light List Accent 6' w:semiHidden='0' w:uiPriority='61' w:unhideWhenUsed='0'/><w:lsdException w:name='Light Grid Accent 6' w:semiHidden='0' w:uiPriority='62' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 1 Accent 6' w:semiHidden='0' w:uiPriority='63' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Shading 2 Accent 6' w:semiHidden='0' w:uiPriority='64' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 1 Accent 6' w:semiHidden='0' w:uiPriority='65' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium List 2 Accent 6' w:semiHidden='0' w:uiPriority='66' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 1 Accent 6' w:semiHidden='0' w:uiPriority='67' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 2 Accent 6' w:semiHidden='0' w:uiPriority='68' w:unhideWhenUsed='0'/><w:lsdException w:name='Medium Grid 3 Accent 6' w:semiHidden='0' w:uiPriority='69' w:unhideWhenUsed='0'/><w:lsdException w:name='Dark List Accent 6' w:semiHidden='0' w:uiPriority='70' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Shading Accent 6' w:semiHidden='0' w:uiPriority='71' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful List Accent 6' w:semiHidden='0' w:uiPriority='72' w:unhideWhenUsed='0'/><w:lsdException w:name='Colorful Grid Accent 6' w:semiHidden='0' w:uiPriority='73' w:unhideWhenUsed='0'/><w:lsdException w:name='Subtle Emphasis' w:semiHidden='0' w:uiPriority='19' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Intense Emphasis' w:semiHidden='0' w:uiPriority='21' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Subtle Reference' w:semiHidden='0' w:uiPriority='31' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Intense Reference' w:semiHidden='0' w:uiPriority='32' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Book Title' w:semiHidden='0' w:uiPriority='33' w:unhideWhenUsed='0' w:qFormat='1'/><w:lsdException w:name='Bibliography' w:uiPriority='37'/><w:lsdException w:name='TOC Heading' w:uiPriority='39' w:qFormat='1'/></w:latentStyles>";
    //HashMap arrParametros = new HashMap <Object> ();
    Map arrParametros = new HashMap();
    arrParametros.put("type", "paragraph");
    arrParametros.put("def", "1");
    arrParametros.put("styleId", "Normal");
    arrParametros.put("name", "Normal");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "000415ED");
    ArrayList  myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Ttulo1");
    arrParametros.put("name", "heading 1");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "00CF3BFC");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("next", "Normal");
    arrParametros.put("link", "Ttulo1Car");
    arrParametros.put("uiPriority", "9");
    arrParametros.put("keepNext", "1");
    arrParametros.put("keepLines", "1");
    arrParametros.put("spacing_before", "480");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("outlineLvl", "0");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("b", "1");
    arrParametros.put("bCs", "1");
    arrParametros.put("color_val", "365F91");
    arrParametros.put("color_themeColor", "accent1");
    arrParametros.put("color_themeShade", "BF");
    arrParametros.put("sz", "28");
    arrParametros.put("szCs", "28");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Ttulo2");
    arrParametros.put("name", "heading 2");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("next", "Normal");
    arrParametros.put("link", "Ttulo2Car");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("uiPriority", "9");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "005235DC");
    arrParametros.put("keepNext", "1");
    arrParametros.put("keepLines", "1");
    arrParametros.put("spacing_before", "200");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("outlineLvl", "1");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("b", "1");
    arrParametros.put("bCs", "1");
    arrParametros.put("color_val", "200290");
    arrParametros.put("sz", "32");
    arrParametros.put("szCs", "26");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Ttulo3");
    arrParametros.put("name", "heading 3");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("next", "Normal");
    arrParametros.put("link", "Ttulo3Car");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("uiPriority", "9");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "005235DC");
    arrParametros.put("keepNext", "1");
    arrParametros.put("keepLines", "1");
    arrParametros.put("spacing_before", "200");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("outlineLvl", "2");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("b", "1");
    arrParametros.put("bCs", "1");
    arrParametros.put("color_val", "000000");
    arrParametros.put("color_themeColor", "text1");
    arrParametros.put("sz", "28");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Ttulo4");
    arrParametros.put("name", "heading 4");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("next", "Normal");
    arrParametros.put("link", "Ttulo4Car");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("uiPriority", "9");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "007D6ADC");
    arrParametros.put("keepNext", "1");
    arrParametros.put("keepLines", "1");
    arrParametros.put("spacing_before", "200");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("outlineLvl", "3");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("b", "1");
    arrParametros.put("bCs", "1");
    arrParametros.put("i", "1");
    arrParametros.put("iCs", "1");
    arrParametros.put("color_val", "4F81BD");
    arrParametros.put("color_themeColor", "accent1");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap();
    arrParametros.put("type", "character");
    arrParametros.put("def", "1");
    arrParametros.put("styleId", "Fuentedeprrafopredeter");
    arrParametros.put("name", "Default Paragraph Font");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("uiPriority", "1");
    arrParametros.put("semiHidden", "1");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap();
    arrParametros.put("type", "table");
    arrParametros.put("def", "1");
    arrParametros.put("styleId", "Tablanormal");
    arrParametros.put("name", "Normal Table");
    arrParametros.put("semiHidden", "1");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("qFormat", "1");
    arrParametros.put("tblInd_w", "0");
    arrParametros.put("tblInd_type", "dxa");
    arrParametros.put("top_w", "0");
    arrParametros.put("top_type", "dxa");
    arrParametros.put("left_w", "108");
    arrParametros.put("left_type", "dxa");
    arrParametros.put("bottom_w", "0");
    arrParametros.put("bottom_type", "dxa");
    arrParametros.put("right_w", "0");
    arrParametros.put("right_type", "dxa");
    arrParametros.put("b", "1");
    arrParametros.put("bCs", "1");
    arrParametros.put("i", "1");
    arrParametros.put("iCs", "1");
    arrParametros.put("color_val", "4F81BD");
    arrParametros.put("color_themeColor", "accent1");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap();
    arrParametros.put("type", "numbering");
    arrParametros.put("def", "1");
    arrParametros.put("styleId", "Sinlista");
    arrParametros.put("name", "No List");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("semiHidden", "1");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap();
    arrParametros.put("type", "character");
    arrParametros.put("customStyle", "1");
    arrParametros.put("styleId", "Ttulo1Car");
    arrParametros.put("name", "Titulo 1 Car");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("link", "Ttulo1");
    arrParametros.put("uiPriority", "9");
    arrParametros.put("rsid", "005235DC");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("b", "1");
    arrParametros.put("bCs", "1");
    arrParametros.put("color_val", "365F91");
    arrParametros.put("color_themeColor", "accent1");
    arrParametros.put("color_themeShade", "BF");
    arrParametros.put("sz", "28");
    arrParametros.put("szCs", "28");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap();
    arrParametros.put("type", "character");
    arrParametros.put("customStyle", "1");
    arrParametros.put("styleId", "Ttulo2Car");
    arrParametros.put("name", "Titulo 2 Car");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("link", "Ttulo2");
    arrParametros.put("uiPriority", "9");
    arrParametros.put("rsid", "00CF3BFC");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("b", "1");
    arrParametros.put("bCs", "1");
    arrParametros.put("color_val", "200290");
    arrParametros.put("sz", "32");
    arrParametros.put("szCs", "26");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "character");
    arrParametros.put("customStyle", "1");
    arrParametros.put("styleId", "Ttulo3Car");
    arrParametros.put("name", "Titulo 3 Car");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("link", "Ttulo3");
    arrParametros.put("uiPriority", "9");
    arrParametros.put("rsid", "005235DC");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("b", "1");
    arrParametros.put("bCs", "1");
    arrParametros.put("color_val", "000000");
    arrParametros.put("color_themeColor", "text1");
    arrParametros.put("sz", "28");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Ttulo");
    arrParametros.put("name", "Title");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("next", "Normal");
    arrParametros.put("link", "TtuloCar");
    arrParametros.put("uiPriority", "10");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "00CF3BFC");
    arrParametros.put("pBdr_bottom_val", "single");
    arrParametros.put("pBdr_bottom_sz", "8");
    arrParametros.put("pBdr_bottom_space", "4");
    arrParametros.put("pBdr_bottom_color", "4F81BD");
    arrParametros.put("pBdr_bottom_themeColor", "accent1");
    arrParametros.put("spacing_after", "300");
    arrParametros.put("spacing_line", "240");
    arrParametros.put("spacing_lineRule", "auto");
    arrParametros.put("contextualSpacing", "1");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("color_val", "17365D");
    arrParametros.put("color_themeColor", "text2");
    arrParametros.put("color_themeShade", "BF");
    arrParametros.put("rPr_spacing", "5");
    arrParametros.put("kern", "28");
    arrParametros.put("sz", "52");
    arrParametros.put("szCs", "52");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "character");
    arrParametros.put("customStyle", "1");
    arrParametros.put("styleId", "TtuloCar");
    arrParametros.put("name", "Titulo Car");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("link", "Ttulo");
    arrParametros.put("uiPriority", "10");
    arrParametros.put("rsid", "00CF3BFC");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("color_val", "17365D");
    arrParametros.put("color_themeColor", "text2");
    arrParametros.put("color_themeShade", "BF");
    arrParametros.put("rPr_spacing", "5");
    arrParametros.put("kern", "28");
    arrParametros.put("sz", "52");
    arrParametros.put("szCs", "52");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Subttulo");
    arrParametros.put("name", "Subtitle");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("next", "Normal");
    arrParametros.put("link", "SubttuloCar");
    arrParametros.put("uiPriority", "11");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "00CF3BFC");
    arrParametros.put("ilvl", "1");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("color_val", "4F81BD");
    arrParametros.put("color_themeColor", "accent1");
    arrParametros.put("rPr_spacing", "15");
    arrParametros.put("i", "1");
    arrParametros.put("iCs", "i");
    arrParametros.put("sz", "24");
    arrParametros.put("szCs", "24");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Sinespaciado");
    arrParametros.put("name", "No Spacing");
    arrParametros.put("uiPriority", "1");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "007D6ADC");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("spacing_line", "240");
    arrParametros.put("spacing_lineRule", "auto");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "character");
    arrParametros.put("customStyle", "1");
    arrParametros.put("styleId", "Ttulo4Car");
    arrParametros.put("name", "Titulo 4 Car");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("link", "Ttulo4");
    arrParametros.put("uiPriority", "9");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "007D6ADC");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("color_val", "4F81BD");
    arrParametros.put("color_themeColor", "accent1");
    arrParametros.put("i", "1");
    arrParametros.put("iCs", "i");
    arrParametros.put("b", "1");
    arrParametros.put("bCs", "i");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "character");
    arrParametros.put("styleId", "Hipervnculo");
    arrParametros.put("name", "Hyperlink");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("rsid", "00EF1859");
    arrParametros.put("color_val", "0000FF");
    arrParametros.put("color_themeColor", "hyperlink");
    arrParametros.put("u", "single");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Prrafodelista");
    arrParametros.put("name", "List Paragraph");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("uiPriority", "34");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "007D6ADC");
    arrParametros.put("ind_left", "720");
    arrParametros.put("contextualSpacing", "1");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "table");
    arrParametros.put("styleId", "Tablaconcuadrcula");
    arrParametros.put("name", "Table Grid");
    arrParametros.put("basedOn", "Tablanormal");
    arrParametros.put("uiPriority", "59");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "007D6ADC");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("spacing_line", "240");
    arrParametros.put("spacing_lineRule", "auto");
    arrParametros.put("tblInd_w", "0");
    arrParametros.put("tblInd_type", "dxa");
    arrParametros.put("top_w", "0");
    arrParametros.put("top_type", "dxa");
    arrParametros.put("left_w", "108");
    arrParametros.put("left_type", "dxa");
    arrParametros.put("bottom_w", "0");
    arrParametros.put("bottom_type", "dxa");
    arrParametros.put("right_w", "108");
    arrParametros.put("right_type", "dxa");
    arrParametros.put("tblborder_top_val", "single");
    arrParametros.put("tblborder_top_sz", "4");
    arrParametros.put("tblborder_top_space", "0");
    arrParametros.put("tblborder_top_color", "000000");
    arrParametros.put("tblborder_top_themeColor", "text1");
    arrParametros.put("tblborder_left_val", "single");
    arrParametros.put("tblborder_left_sz", "4");
    arrParametros.put("tblborder_left_space", "0");
    arrParametros.put("tblborder_left_color", "000000");
    arrParametros.put("tblborder_left_themeColor", "text1");
    arrParametros.put("tblborder_bottom_val", "single");
    arrParametros.put("tblborder_bottom_sz", "4");
    arrParametros.put("tblborder_bottom_space", "0");
    arrParametros.put("tblborder_bottom_color", "000000");
    arrParametros.put("tblborder_bottom_themeColor", "text1");
    arrParametros.put("tblborder_right_val", "single");
    arrParametros.put("tblborder_right_sz", "4");
    arrParametros.put("tblborder_right_space", "0");
    arrParametros.put("tblborder_right_color", "000000");
    arrParametros.put("tblborder_right_themeColor", "text1");
    arrParametros.put("tblborder_insideH_val", "single");
    arrParametros.put("tblborder_insideH_sz", "4");
    arrParametros.put("tblborder_insideH_space", "0");
    arrParametros.put("tblborder_insideH_color", "000000");
    arrParametros.put("tblborder_insideH_themeColor", "text1");
    arrParametros.put("tblborder_insideV_val", "single");
    arrParametros.put("tblborder_insideV_sz", "4");
    arrParametros.put("tblborder_insideV_space", "0");
    arrParametros.put("tblborder_insideV_color", "000000");
    arrParametros.put("tblborder_insideV_themeColor", "text1");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Textodeglobo");
    arrParametros.put("name", "Balloon Text");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("link", "TextodegloboCar");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("semiHidden", "1");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("rsid", "007D6ADC");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("spacing_line", "240");
    arrParametros.put("spacing_lineRule", "auto");
    arrParametros.put("sz", "16");
    arrParametros.put("szCs", "16");
    arrParametros.put("rFonts_ascii", "Tahoma");
    arrParametros.put("rFonts_hAnsi", "Tahoma");
    arrParametros.put("rFonts_cs", "Tahoma");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "character");
    arrParametros.put("customStyle", "1");
    arrParametros.put("styleId", "TextodegloboCar");
    arrParametros.put("name", "Fuentedeprrafopredeter");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("link", "Textodeglobo");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("semiHidden", "1");
    arrParametros.put("rsid", "007D6ADC");
    arrParametros.put("sz", "16");
    arrParametros.put("szCs", "16");
    arrParametros.put("rFonts_ascii", "Tahoma");
    arrParametros.put("rFonts_hAnsi", "Tahoma");
    arrParametros.put("rFonts_cs", "Tahoma");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Encabezado");
    arrParametros.put("name", "header");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("link", "EncabezadoCar");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("rsid", "007D6ADC");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("spacing_line", "240");
    arrParametros.put("spacing_lineRule", "auto");
    arrParametros.put("tab_center", "4252");
    arrParametros.put("tab_right", "8504");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "character");
    arrParametros.put("customStyle", "1");
    arrParametros.put("styleId", "EncabezadoCar");
    arrParametros.put("name", "Encabezado Car");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("link", "Encabezado");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("rsid", "007D6ADC");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Piedepgina");
    arrParametros.put("name", "footer");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("link", "PiedepginaCar");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("rsid", "007D6ADC");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("spacing_line", "240");
    arrParametros.put("spacing_lineRule", "auto");
    arrParametros.put("tab_center", "4252");
    arrParametros.put("tab_right", "8504");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "character");
    arrParametros.put("customStyle", "1");
    arrParametros.put("styleId", "PiedepginaCar");
    arrParametros.put("name", "Pie de pagina Car");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("link", "Piedepgina");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("rsid", "007D6ADC");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "TDC1");
    arrParametros.put("name", "toc 1");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("next", "Normal");
    arrParametros.put("uiPriority", "39");
    arrParametros.put("autoRedefine", "1");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("rsid", "00EF1859");
    arrParametros.put("spacing_after", "100");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "numbering");
    arrParametros.put("customStyle", "1");
    arrParametros.put("styleId", "Sinlista");
    arrParametros.put("name", "No List");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("semiHidden", "1");
    arrParametros.put("unhideWhenUsed", "1");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "TDC2");
    arrParametros.put("name", "toc 2");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("next", "Normal");
    arrParametros.put("uiPriority", "39");
    arrParametros.put("autoRedefine", "1");
    arrParametros.put("semiHidden", "1");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("rsid", "005F706C");
    arrParametros.put("spacing_after", "100");
    arrParametros.put("ind_left", "220");
    arrParametros.put("b", "1");
    arrParametros.put("sz", "24");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "TDC3");
    arrParametros.put("name", "toc 3");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("next", "Normal");
    arrParametros.put("uiPriority", "39");
    arrParametros.put("autoRedefine", "1");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("rsid", "00EF1859");
    arrParametros.put("spacing_after", "100");
    arrParametros.put("ind_left", "440");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "table");
    arrParametros.put("styleId", "Cuadrculamedia3-nfasis1");
    arrParametros.put("name", "Medium Grid 3 Accent 1");
    arrParametros.put("basedOn", "Tablanormal");
    arrParametros.put("uiPriority", "69");
    arrParametros.put("rsid", "00387AE9");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("spacing_line", "240");
    arrParametros.put("spacing_lineRule", "auto");
    arrParametros.put("tblInd_w", "0");
    arrParametros.put("tblInd_type", "dxa");
    arrParametros.put("top_w", "0");
    arrParametros.put("top_type", "dxa");
    arrParametros.put("left_w", "108");
    arrParametros.put("left_type", "dxa");
    arrParametros.put("bottom_w", "0");
    arrParametros.put("bottom_type", "dxa");
    arrParametros.put("right_w", "108");
    arrParametros.put("right_type", "dxa");
    arrParametros.put("tbl_style_row", "1");
    arrParametros.put("tbl_style_col", "1");
    arrParametros.put("tblborder_top_val", "single");
    arrParametros.put("tblborder_top_sz", "8");
    arrParametros.put("tblborder_top_space", "0");
    arrParametros.put("tblborder_top_color", "FFFFFF");
    arrParametros.put("tblborder_top_themeColor", "background1");
    arrParametros.put("tblborder_left_val", "single");
    arrParametros.put("tblborder_left_sz", "8");
    arrParametros.put("tblborder_left_space", "0");
    arrParametros.put("tblborder_left_color", "FFFFFF");
    arrParametros.put("tblborder_left_themeColor", "background1");
    arrParametros.put("tblborder_bottom_val", "single");
    arrParametros.put("tblborder_bottom_sz", "8");
    arrParametros.put("tblborder_bottom_space", "0");
    arrParametros.put("tblborder_bottom_color", "FFFFFF");
    arrParametros.put("tblborder_bottom_themeColor", "background1");
    arrParametros.put("tblborder_right_val", "single");
    arrParametros.put("tblborder_right_sz", "8");
    arrParametros.put("tblborder_right_space", "0");
    arrParametros.put("tblborder_right_color", "FFFFFF");
    arrParametros.put("tblborder_right_themeColor", "background1");
    arrParametros.put("tblborder_insideH_val", "single");
    arrParametros.put("tblborder_insideH_sz", "6");
    arrParametros.put("tblborder_insideH_space", "0");
    arrParametros.put("tblborder_insideH_color", "FFFFFF");
    arrParametros.put("tblborder_insideH_themeColor", "background1");
    arrParametros.put("tblborder_insideV_val", "single");
    arrParametros.put("tblborder_insideV_sz", "6");
    arrParametros.put("tblborder_insideV_space", "0");
    arrParametros.put("tblborder_insideV_color", "FFFFFF");
    arrParametros.put("tblborder_insideV_themeColor", "background1");
    arrParametros.put("shd_val", "clear");
    arrParametros.put("shd_color", "auto");
    arrParametros.put("shd_fill", "D3DFEE");
    arrParametros.put("shd_themeFill", "accent1");
    arrParametros.put("shd_themeFillTint", "3F");
    Map  arrParametrosT1 = new HashMap ();
    arrParametrosT1.put("type", "firstRow");
    arrParametrosT1.put("b", "1");
    arrParametrosT1.put("bCs", "1");
    arrParametrosT1.put("i", "1");
    arrParametrosT1.put("iCs", "1");
    arrParametrosT1.put("color_val", "FFFFFF");
    arrParametrosT1.put("color_themeColor", "background1");
    arrParametrosT1.put("tblPr", "1");
    arrParametrosT1.put("tblborder_top_val", "single");
    arrParametrosT1.put("tblborder_top_sz", "8");
    arrParametrosT1.put("tblborder_top_space", "0");
    arrParametrosT1.put("tblborder_top_color", "FFFFFF");
    arrParametrosT1.put("tblborder_top_themeColor", "background1");
    arrParametrosT1.put("tblborder_left_val", "single");
    arrParametrosT1.put("tblborder_left_sz", "8");
    arrParametrosT1.put("tblborder_left_space", "0");
    arrParametrosT1.put("tblborder_left_color", "FFFFFF");
    arrParametrosT1.put("tblborder_left_themeColor", "background1");
    arrParametrosT1.put("tblborder_bottom_val", "single");
    arrParametrosT1.put("tblborder_bottom_sz", "8");
    arrParametrosT1.put("tblborder_bottom_space", "0");
    arrParametrosT1.put("tblborder_bottom_color", "FFFFFF");
    arrParametrosT1.put("tblborder_bottom_themeColor", "background1");
    arrParametrosT1.put("tblborder_right_val", "single");
    arrParametrosT1.put("tblborder_right_sz", "8");
    arrParametrosT1.put("tblborder_right_space", "0");
    arrParametrosT1.put("tblborder_right_color", "FFFFFF");
    arrParametrosT1.put("tblborder_right_themeColor", "background1");
    arrParametrosT1.put("tblborder_insideH_val", "nil");
    arrParametrosT1.put("tblborder_insideV_val", "single");
    arrParametrosT1.put("tblborder_insideV_sz", "8");
    arrParametrosT1.put("tblborder_insideV_space", "0");
    arrParametrosT1.put("tblborder_insideV_color", "FFFFFF");
    arrParametrosT1.put("tblborder_insideV_themeColor", "background1");
    arrParametrosT1.put("shd_val", "clear");
    arrParametrosT1.put("shd_color", "auto");
    arrParametrosT1.put("shd_fill", "4F81BD");
    arrParametrosT1.put("shd_themeFill", "accent1");
    Map  arrParametrosT2 = new HashMap ();
    arrParametrosT2.put("type", "lastRow");
    arrParametrosT2.put("b", "1");
    arrParametrosT2.put("bCs", "1");
    arrParametrosT2.put("i", "0");
    arrParametrosT2.put("iCs", "0");
    arrParametrosT2.put("color_val", "FFFFFF");
    arrParametrosT2.put("color_themeColor", "background1");
    arrParametrosT2.put("tblPr", "1");
    arrParametrosT2.put("tblborder_top_val", "single");
    arrParametrosT2.put("tblborder_top_sz", "24");
    arrParametrosT2.put("tblborder_top_space", "0");
    arrParametrosT2.put("tblborder_top_color", "FFFFFF");
    arrParametrosT2.put("tblborder_top_themeColor", "background1");
    arrParametrosT2.put("tblborder_left_val", "single");
    arrParametrosT2.put("tblborder_left_sz", "8");
    arrParametrosT2.put("tblborder_left_space", "0");
    arrParametrosT2.put("tblborder_left_color", "FFFFFF");
    arrParametrosT2.put("tblborder_left_themeColor", "background1");
    arrParametrosT2.put("tblborder_bottom_val", "single");
    arrParametrosT2.put("tblborder_bottom_sz", "8");
    arrParametrosT2.put("tblborder_bottom_space", "0");
    arrParametrosT2.put("tblborder_bottom_color", "FFFFFF");
    arrParametrosT2.put("tblborder_bottom_themeColor", "background1");
    arrParametrosT2.put("tblborder_right_val", "single");
    arrParametrosT2.put("tblborder_right_sz", "8");
    arrParametrosT2.put("tblborder_right_space", "0");
    arrParametrosT2.put("tblborder_right_color", "FFFFFF");
    arrParametrosT2.put("tblborder_right_themeColor", "background1");
    arrParametrosT2.put("tblborder_insideH_val", "nil");
    arrParametrosT2.put("tblborder_insideV_val", "single");
    arrParametrosT2.put("tblborder_insideV_sz", "8");
    arrParametrosT2.put("tblborder_insideV_space", "0");
    arrParametrosT2.put("tblborder_insideV_color", "FFFFFF");
    arrParametrosT2.put("tblborder_insideV_themeColor", "background1");
    arrParametrosT2.put("shd_val", "clear");
    arrParametrosT2.put("shd_color", "auto");
    arrParametrosT2.put("shd_fill", "4F81BD");
    arrParametrosT2.put("shd_themeFill", "accent1");
    Map  arrParametrosT3 = new HashMap ();
    arrParametrosT3.put("type", "firstCol");
    arrParametrosT3.put("b", "1");
    arrParametrosT3.put("bCs", "1");
    arrParametrosT3.put("i", "0");
    arrParametrosT3.put("iCs", "0");
    arrParametrosT3.put("color_val", "FFFFFF");
    arrParametrosT3.put("color_themeColor", "background1");
    arrParametrosT3.put("tblPr", "1");
    arrParametrosT3.put("tblborder_left_val", "single");
    arrParametrosT3.put("tblborder_left_sz", "8");
    arrParametrosT3.put("tblborder_left_space", "0");
    arrParametrosT3.put("tblborder_left_color", "FFFFFF");
    arrParametrosT3.put("tblborder_left_themeColor", "background1");
    arrParametrosT3.put("tblborder_right_val", "single");
    arrParametrosT3.put("tblborder_right_sz", "24");
    arrParametrosT3.put("tblborder_right_space", "0");
    arrParametrosT3.put("tblborder_right_color", "FFFFFF");
    arrParametrosT3.put("tblborder_right_themeColor", "background1");
    arrParametrosT3.put("tblborder_insideH_val", "nil");
    arrParametrosT3.put("tblborder_insideV_val", "nil");
    arrParametrosT3.put("shd_val", "clear");
    arrParametrosT3.put("shd_color", "auto");
    arrParametrosT3.put("shd_fill", "4F81BD");
    arrParametrosT3.put("shd_themeFill", "accent1");
    Map  arrParametrosT4 = new HashMap ();
    arrParametrosT4.put("type", "lastCol");
    arrParametrosT4.put("b", "1");
    arrParametrosT4.put("bCs", "1");
    arrParametrosT4.put("i", "0");
    arrParametrosT4.put("iCs", "0");
    arrParametrosT4.put("color_val", "FFFFFF");
    arrParametrosT4.put("color_themeColor", "background1");
    arrParametrosT4.put("tblPr", "1");
    arrParametrosT4.put("tblborder_top_val", "nil");
    arrParametrosT4.put("tblborder_left_val", "single");
    arrParametrosT4.put("tblborder_left_sz", "24");
    arrParametrosT4.put("tblborder_left_space", "0");
    arrParametrosT4.put("tblborder_left_color", "FFFFFF");
    arrParametrosT4.put("tblborder_left_themeColor", "background1");
    arrParametrosT4.put("tblborder_bottom_val", "nil");
    arrParametrosT4.put("tblborder_right_val", "nil");
    arrParametrosT4.put("tblborder_insideH_val", "nil");
    arrParametrosT4.put("tblborder_insideV_val", "nil");
    arrParametrosT4.put("shd_val", "clear");
    arrParametrosT4.put("shd_color", "auto");
    arrParametrosT4.put("shd_fill", "4F81BD");
    arrParametrosT4.put("shd_themeFill", "accent1");
    Map  arrParametrosT5 = new HashMap ();
    arrParametrosT5.put("type", "band1Vert");
    arrParametrosT5.put("tblPr", "1");
    arrParametrosT5.put("tblborder_top_val", "single");
    arrParametrosT5.put("tblborder_top_sz", "8");
    arrParametrosT5.put("tblborder_top_space", "0");
    arrParametrosT5.put("tblborder_top_color", "FFFFFF");
    arrParametrosT5.put("tblborder_top_themeColor", "background1");
    arrParametrosT5.put("tblborder_left_val", "single");
    arrParametrosT5.put("tblborder_left_sz", "8");
    arrParametrosT5.put("tblborder_left_space", "0");
    arrParametrosT5.put("tblborder_left_color", "FFFFFF");
    arrParametrosT5.put("tblborder_left_themeColor", "background1");
    arrParametrosT5.put("tblborder_bottom_val", "single");
    arrParametrosT5.put("tblborder_bottom_sz", "8");
    arrParametrosT5.put("tblborder_bottom_space", "0");
    arrParametrosT5.put("tblborder_bottom_color", "FFFFFF");
    arrParametrosT5.put("tblborder_bottom_themeColor", "background1");
    arrParametrosT5.put("tblborder_right_val", "single");
    arrParametrosT5.put("tblborder_right_sz", "8");
    arrParametrosT5.put("tblborder_right_space", "0");
    arrParametrosT5.put("tblborder_right_color", "FFFFFF");
    arrParametrosT5.put("tblborder_right_themeColor", "background1");
    arrParametrosT5.put("tblborder_insideH_val", "nil");
    arrParametrosT5.put("tblborder_insideV_val", "nil");
    arrParametrosT5.put("shd_val", "clear");
    arrParametrosT5.put("shd_color", "auto");
    arrParametrosT5.put("shd_fill", "A7BFDE");
    arrParametrosT5.put("shd_themeFill", "accent1");
    arrParametrosT5.put("shd_themeFillTint", "7F");
    Map  arrParametrosT6 = new HashMap ();
    arrParametrosT6.put("type", "band1Horz");
    arrParametrosT6.put("tblPr", "1");
    arrParametrosT6.put("tblborder_top_val", "single");
    arrParametrosT6.put("tblborder_top_sz", "8");
    arrParametrosT6.put("tblborder_top_space", "0");
    arrParametrosT6.put("tblborder_top_color", "FFFFFF");
    arrParametrosT6.put("tblborder_top_themeColor", "background1");
    arrParametrosT6.put("tblborder_left_val", "single");
    arrParametrosT6.put("tblborder_left_sz", "8");
    arrParametrosT6.put("tblborder_left_space", "0");
    arrParametrosT6.put("tblborder_left_color", "FFFFFF");
    arrParametrosT6.put("tblborder_left_themeColor", "background1");
    arrParametrosT6.put("tblborder_bottom_val", "single");
    arrParametrosT6.put("tblborder_bottom_sz", "8");
    arrParametrosT6.put("tblborder_bottom_space", "0");
    arrParametrosT6.put("tblborder_bottom_color", "FFFFFF");
    arrParametrosT6.put("tblborder_bottom_themeColor", "background1");
    arrParametrosT6.put("tblborder_right_val", "single");
    arrParametrosT6.put("tblborder_right_sz", "8");
    arrParametrosT6.put("tblborder_right_space", "0");
    arrParametrosT6.put("tblborder_right_color", "FFFFFF");
    arrParametrosT6.put("tblborder_right_themeColor", "background1");
    arrParametrosT6.put("tblborder_insideH_val", "single");
    arrParametrosT6.put("tblborder_insideH_sz", "8");
    arrParametrosT6.put("tblborder_insideH_space", "0");
    arrParametrosT6.put("tblborder_insideH_color", "FFFFFF");
    arrParametrosT6.put("tblborder_insideH_themeColor", "background1");
    arrParametrosT6.put("tblborder_insideV_val", "single");
    arrParametrosT6.put("tblborder_insideV_sz", "8");
    arrParametrosT6.put("tblborder_insideV_space", "0");
    arrParametrosT6.put("tblborder_insideV_color", "FFFFFF");
    arrParametrosT6.put("tblborder_insideV_themeColor", "background1");
    arrParametrosT6.put("shd_val", "clear");
    arrParametrosT6.put("shd_color", "auto");
    arrParametrosT6.put("shd_fill", "A7BFDE");
    arrParametrosT6.put("shd_themeFill", "accent1");
    arrParametrosT6.put("shd_themeFillTint", "7F");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    myArr.add(arrParametrosT1);
    myArr.add(arrParametrosT2);
    myArr.add(arrParametrosT3);
    myArr.add(arrParametrosT4);
    myArr.add(arrParametrosT5);
    myArr.add(arrParametrosT6);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "character");
    arrParametros.put("styleId", "nfasis");
    arrParametros.put("name", "Emphasis");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("uiPriority", "20");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "005235DC");
    arrParametros.put("i", "1");
    arrParametros.put("iCs", "1");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "table");
    arrParametros.put("styleId", "Sombreadomedio1");
    arrParametros.put("name", "Medium Shading 1");
    arrParametros.put("basedOn", "Tablanormal");
    arrParametros.put("uiPriority", "63");
    arrParametros.put("rsid", "00E559CE");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("spacing_line", "240");
    arrParametros.put("spacing_lineRule", "auto");
    arrParametros.put("tblInd_w", "0");
    arrParametros.put("tblInd_type", "dxa");
    arrParametros.put("top_w", "0");
    arrParametros.put("top_type", "dxa");
    arrParametros.put("left_w", "108");
    arrParametros.put("left_type", "dxa");
    arrParametros.put("bottom_w", "0");
    arrParametros.put("bottom_type", "dxa");
    arrParametros.put("right_w", "108");
    arrParametros.put("right_type", "dxa");
    arrParametros.put("tbl_style_row", "1");
    arrParametros.put("tbl_style_col", "1");
    arrParametros.put("tblborder_top_val", "single");
    arrParametros.put("tblborder_top_sz", "8");
    arrParametros.put("tblborder_top_space", "0");
    arrParametros.put("tblborder_top_color", "404040");
    arrParametros.put("tblborder_top_themeColor", "text1");
    arrParametros.put("tblborder_top_themeTint", "BF");
    arrParametros.put("tblborder_left_val", "single");
    arrParametros.put("tblborder_left_sz", "8");
    arrParametros.put("tblborder_left_space", "0");
    arrParametros.put("tblborder_left_color", "404040");
    arrParametros.put("tblborder_left_themeColor", "text1");
    arrParametros.put("tblborder_left_themeTint", "BF");
    arrParametros.put("tblborder_bottom_val", "single");
    arrParametros.put("tblborder_bottom_sz", "8");
    arrParametros.put("tblborder_bottom_space", "0");
    arrParametros.put("tblborder_bottom_color", "404040");
    arrParametros.put("tblborder_bottom_themeColor", "text1");
    arrParametros.put("tblborder_bottom_themeTint", "BF");
    arrParametros.put("tblborder_right_val", "single");
    arrParametros.put("tblborder_right_sz", "8");
    arrParametros.put("tblborder_right_space", "0");
    arrParametros.put("tblborder_right_color", "404040");
    arrParametros.put("tblborder_right_themeColor", "text1");
    arrParametros.put("tblborder_right_themeTint", "BF");
    arrParametros.put("tblborder_insideH_val", "single");
    arrParametros.put("tblborder_insideH_sz", "6");
    arrParametros.put("tblborder_insideH_space", "0");
    arrParametros.put("tblborder_insideH_color", "404040");
    arrParametros.put("tblborder_insideH_themeColor", "text1");
    arrParametros.put("tblborder_insideH_themeTint", "BF");
    arrParametrosT1 = new HashMap ();
    arrParametrosT1.put("type", "firstRow");
    arrParametrosT1.put("spacing_after", "0");
    arrParametrosT1.put("spacing_line", "240");
    arrParametrosT1.put("spacing_lineRule", "auto");
    arrParametrosT1.put("bCs", "1");
    arrParametrosT1.put("b", "1");
    arrParametrosT1.put("color_val", "FFFFFF");
    arrParametrosT1.put("color_themeColor", "background1");
    arrParametrosT1.put("tblPr", "1");
    arrParametrosT1.put("tblborder_top_val", "single");
    arrParametrosT1.put("tblborder_top_sz", "8");
    arrParametrosT1.put("tblborder_top_space", "0");
    arrParametrosT1.put("tblborder_top_color", "404040");
    arrParametrosT1.put("tblborder_top_themeColor", "text1");
    arrParametrosT1.put("tblborder_top_themeTint", "BF");
    arrParametrosT1.put("tblborder_left_val", "single");
    arrParametrosT1.put("tblborder_left_sz", "8");
    arrParametrosT1.put("tblborder_left_space", "0");
    arrParametrosT1.put("tblborder_left_color", "404040");
    arrParametrosT1.put("tblborder_left_themeColor", "text1");
    arrParametrosT1.put("tblborder_left_themeTint", "BF");
    arrParametrosT1.put("tblborder_bottom_val", "single");
    arrParametrosT1.put("tblborder_bottom_sz", "8");
    arrParametrosT1.put("tblborder_bottom_space", "0");
    arrParametrosT1.put("tblborder_bottom_color", "404040");
    arrParametrosT1.put("tblborder_bottom_themeColor", "text1");
    arrParametrosT1.put("tblborder_bottom_themeTint", "BF");
    arrParametrosT1.put("tblborder_right_val", "single");
    arrParametrosT1.put("tblborder_right_sz", "8");
    arrParametrosT1.put("tblborder_right_space", "0");
    arrParametrosT1.put("tblborder_right_color", "404040");
    arrParametrosT1.put("tblborder_right_themeColor", "text1");
    arrParametrosT1.put("tblborder_right_themeTint", "BF");
    arrParametrosT1.put("tblborder_insideH_val", "nil");
    arrParametrosT1.put("tblborder_insideV_val", "nil");
    arrParametrosT1.put("shd_val", "clear");
    arrParametrosT1.put("shd_color", "auto");
    arrParametrosT1.put("shd_fill", "000000");
    arrParametrosT1.put("shd_themeFill", "text1");
    arrParametrosT2 = new HashMap ();
    arrParametrosT2.put("type", "firstRow");
    arrParametrosT2.put("spacing_before", "0");
    arrParametrosT2.put("spacing_after", "0");
    arrParametrosT2.put("spacing_before", "240");
    arrParametrosT2.put("spacing_lineRule", "auto");
    arrParametrosT2.put("bCs", "1");
    arrParametrosT2.put("b", "1");
    arrParametrosT2.put("tblPr", "1");
    arrParametrosT2.put("tblborder_top_val", "double");
    arrParametrosT2.put("tblborder_top_sz", "6");
    arrParametrosT2.put("tblborder_top_space", "0");
    arrParametrosT2.put("tblborder_top_color", "404040");
    arrParametrosT2.put("tblborder_top_themeColor", "text1");
    arrParametrosT2.put("tblborder_top_themeTint", "BF");
    arrParametrosT2.put("tblborder_left_val", "single");
    arrParametrosT2.put("tblborder_left_sz", "8");
    arrParametrosT2.put("tblborder_left_space", "0");
    arrParametrosT2.put("tblborder_left_color", "404040");
    arrParametrosT2.put("tblborder_left_themeColor", "text1");
    arrParametrosT2.put("tblborder_left_themeTint", "BF");
    arrParametrosT2.put("tblborder_bottom_val", "single");
    arrParametrosT2.put("tblborder_bottom_sz", "8");
    arrParametrosT2.put("tblborder_bottom_space", "0");
    arrParametrosT2.put("tblborder_bottom_color", "404040");
    arrParametrosT2.put("tblborder_bottom_themeColor", "text1");
    arrParametrosT2.put("tblborder_bottom_themeTint", "BF");
    arrParametrosT2.put("tblborder_right_val", "single");
    arrParametrosT2.put("tblborder_right_sz", "8");
    arrParametrosT2.put("tblborder_right_space", "0");
    arrParametrosT2.put("tblborder_right_color", "404040");
    arrParametrosT2.put("tblborder_right_themeColor", "text1");
    arrParametrosT2.put("tblborder_right_themeTint", "BF");
    arrParametrosT2.put("tblborder_insideH_val", "nil");
    arrParametrosT2.put("tblborder_insideV_val", "nil");
    arrParametrosT3 = new HashMap ();
    arrParametrosT3.put("type", "firstCol");
    arrParametrosT3.put("bCs", "1");
    arrParametrosT3.put("b", "1");
    arrParametrosT4 = new HashMap ();
    arrParametrosT4.put("type", "lastCol");
    arrParametrosT4.put("bCs", "1");
    arrParametrosT4.put("b", "1");
    arrParametrosT5 = new HashMap ();
    arrParametrosT5.put("type", "band1Vert");
    arrParametrosT5.put("tblPr", "1");
    arrParametrosT5.put("shd_val", "clear");
    arrParametrosT5.put("shd_color", "auto");
    arrParametrosT5.put("shd_fill", "C0C0C0");
    arrParametrosT5.put("shd_themeFill", "text1");
    arrParametrosT5.put("shd_themeFillTint", "3F");
    arrParametrosT6 = new HashMap ();
    arrParametrosT6.put("type", "band1Horz");
    arrParametrosT6.put("tblPr", "1");
    arrParametrosT6.put("tblborder_insideH_val", "nil");
    arrParametrosT6.put("tblborder_insideV_val", "nil");
    arrParametrosT6.put("shd_val", "clear");
    arrParametrosT6.put("shd_color", "auto");
    arrParametrosT6.put("shd_fill", "C0C0C0");
    arrParametrosT6.put("shd_themeFill", "text1");
    arrParametrosT6.put("shd_themeFillTint", "3F");
    Map  arrParametrosT7 = new HashMap ();
    arrParametrosT7.put("type", "band1Horz");
    arrParametrosT7.put("tblPr", "1");
    arrParametrosT7.put("tblborder_insideH_val", "nil");
    arrParametrosT7.put("tblborder_insideV_val", "nil");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    myArr.add(arrParametrosT1);
    myArr.add(arrParametrosT2);
    myArr.add(arrParametrosT3);
    myArr.add(arrParametrosT4);
    myArr.add(arrParametrosT5);
    myArr.add(arrParametrosT6);
    myArr.add(arrParametrosT7);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Ttulo4");
    arrParametros.put("name", "heading 4");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("next", "Normal");
    arrParametros.put("link", "Ttulo4Car");
    arrParametros.put("uiPriority", "9");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("qFormat", "1");
    arrParametros.put("rsid", "00C967D7");
    arrParametros.put("keepNext", "1");
    arrParametros.put("keepLines", "1");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("spacing_line", "200");
    arrParametros.put("outlineLvl", "3");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("b", "1");
    arrParametros.put("bCs", "1");
    arrParametros.put("i", "1");
    arrParametros.put("iCs", "1");
    arrParametros.put("color_val", "244061");
    arrParametros.put("color_themeColor", "accent1");
    arrParametros.put("color_themeShade", "80");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "character");
    arrParametros.put("customStyle", "1");
    arrParametros.put("styleId", "Ttulo4Car");
    arrParametros.put("name", "Titulo 4 Car");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("link", "Ttulo4");
    arrParametros.put("uiPriority", "9");
    arrParametros.put("rsid", "00C967D7");
    arrParametros.put("rFonts_asciiTheme", "majorHAnsi");
    arrParametros.put("rFonts_eastAsiaTheme", "majorEastAsia");
    arrParametros.put("rFonts_hAnsiTheme", "majorHAnsi");
    arrParametros.put("rFonts_cstheme", "majorBidi");
    arrParametros.put("b", "1");
    arrParametros.put("bCs", "1");
    arrParametros.put("i", "1");
    arrParametros.put("iCs", "1");
    arrParametros.put("color_val", "244061");
    arrParametros.put("color_themeColor", "accent1");
    arrParametros.put("color_themeShade", "80");
    arrParametros.put("sz", "24");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "paragraph");
    arrParametros.put("styleId", "Textonotaalfinal");
    arrParametros.put("name", "endnote text");
    arrParametros.put("basedOn", "Normal");
    arrParametros.put("link", "TextonotaalfinalCar");
    arrParametros.put("semiHidden", "1");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("rsid", "00684540");
    arrParametros.put("spacing_after", "0");
    arrParametros.put("spacing_line", "240");
    arrParametros.put("spacing_lineRule", "auto");
    arrParametros.put("szCs", "20");
    arrParametros.put("sz", "20");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "character");
    arrParametros.put("customStyle", "1");
    arrParametros.put("styleId", "TextonotaalfinalCar");
    arrParametros.put("name", "Texto nota al final Car");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("link", "Textonotaalfinal");
    arrParametros.put("semiHidden", "1");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("rsid", "00684540");
    arrParametros.put("szCs", "20");
    arrParametros.put("sz", "20");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
    arrParametros = new HashMap ();
    arrParametros.put("type", "character");
    arrParametros.put("styleId", "Refdenotaalfinal");
    arrParametros.put("name", "endnote reference");
    arrParametros.put("basedOn", "Fuentedeprrafopredeter");
    arrParametros.put("semiHidden", "1");
    arrParametros.put("uiPriority", "99");
    arrParametros.put("unhideWhenUsed", "1");
    arrParametros.put("rsid", "00684540");
    arrParametros.put("vertAlign", "superscript");
    myArr = new ArrayList ();
    myArr.add(arrParametros);
    this.fAddStyle(myArr);
  }

  public void fAddStyleTabla(HashMap arrParam) {
    cCreateStyleTable objStyle = cCreateStyleTable.getInstance();
    objStyle.fAddStyleTable(arrParam);
    this.xml = this.xml.replace("__GENERATESTYLE__", objStyle.toString());
  }
}
