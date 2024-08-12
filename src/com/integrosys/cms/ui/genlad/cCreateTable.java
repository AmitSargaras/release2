/**
 * Generate tables.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class cCreateTable extends cCreateElement {

  private static cCreateTable rscInstance = null;

   
  public String toString() {
    this.fCleanTemplate();
    return this.xml;
  }

  public static cCreateTable getInstance() {
    if (cCreateTable.rscInstance == null) {
      cCreateTable.rscInstance = new cCreateTable();
    }
    return cCreateTable.rscInstance;
  }

  private void fCleanTemplateR() {
    this.xml = this.xml.replace("__GENERATETR__", "");
  }

  protected void fAddList(String strList) {
    this.xml = this.xml.replace("__GENERATEP__", strList);
  }

  protected void fGenerateGRIDCOLS(String w) {
    this.xml = this.xml.replace("__GENERATEGRIDCOLS__", "<" + cCreateElement.NAMESPACE + ":tblGrid " + cCreateElement.NAMESPACE + ":w='" + w + "'></" + cCreateElement.NAMESPACE + ":tblGrid>__GENERATEGRIDCOLS__");
  }

  protected void fGenerateHMERGE(String val) {
  }

   
  protected void fGenerateJC(String strVal) {
    this.xml = this.xml.replace("__GENERATEJC__", "<" + cCreateElement.NAMESPACE + ":jc " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":jc>");
  }

   
  protected void fGenerateP() {
    this.xml = this.xml.replace("__GENERATETC__", "<" + cCreateElement.NAMESPACE + ":p >__GENERATEP__</" + cCreateElement.NAMESPACE + ":p>");
  }

  protected void fGenerateSHD(String val, String color, String fill, String bgcolor) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":shd val='" + val + "'";
    if ("".equals(val)) {
      val = "horz-cross";
    }
    if ("".equals(color)) {
      xmlAux += " color='" + color + "'";
    }
    if ("".equals(fill)) {
      xmlAux += " fill='" + fill + "'";
    }
    if ("".equals(bgcolor)) {
      xmlAux += " wx:bgcolor='" + bgcolor + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":shd>";
    this.xml = this.xml.replace("__GENERATESHD__", xmlAux);
  }

  protected void fGenerateTBLBORDERS() {
    this.xml = this.xml.replace("__GENERATETBLBORDERS__", "<" + cCreateElement.NAMESPACE + ":tblBorders>__GENERATETBLBORDER__</" + cCreateElement.NAMESPACE + ":tblBorders>");
  }

  protected void fGenerateTBLBOTTOM(String val, String sz, String space, String color) {
    if ("".equals(val)) {
      val = "single";
    }
    if ("".equals(sz)) {
      sz = "4";
    }
    if ("".equals(space)) {
      space = "0";
    }
    if ("".equals(color)) {
      color = "auto";
    }
    this.xml = this.xml.replace("__GENERATETBLBORDER__", "<" + cCreateElement.NAMESPACE + ":bottom " + cCreateElement.NAMESPACE + ":val='" + val + "' " + cCreateElement.NAMESPACE + ":sz='" + sz + "' " + cCreateElement.NAMESPACE + ":space='" + space + "' " + cCreateElement.NAMESPACE + ":color='" + color + "'></" + cCreateElement.NAMESPACE + ":bottom>__GENERATETBLBORDER__");
  }

  protected void fGenerateTBL() {
    this.xml = "<" + cCreateElement.NAMESPACE + ":tbl>__GENERATETBL__</" + cCreateElement.NAMESPACE + ":tbl>";
  }

  protected void fGenerateTBLSTYLE(String strVal) {
    if ("".equals(strVal)) {
      strVal = "TableGrid";
    }
    this.xml = this.xml.replace("__GENERATETBLSTYLE__", "<" + cCreateElement.NAMESPACE + ":tblStyle " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":tblStyle>");
  }

  protected void fGenerateTBLCELLSPACING(String w, String type) {
    if ("".equals(w)) {
      w = "";
    }
    if ("".equals(type)) {
      type = "";
    }
    this.xml = this.xml.replace("__GENERATETBLCELLSPACING__", "<" + cCreateElement.NAMESPACE + ":tblCellSpacing " + cCreateElement.NAMESPACE + ":w='" + w + "' " + cCreateElement.NAMESPACE + ":type='" + type + "'></" + cCreateElement.NAMESPACE + ":tblCellSpacing>");
  }

  protected void fGenerateTBLGRID() {
    this.xml = this.xml.replace("__GENERATETBL__", "<" + cCreateElement.NAMESPACE + ":tblGrid>__GENERATEGRIDCOLS__</" + cCreateElement.NAMESPACE + ":tblGrid>__GENERATETBL__");
  }

  protected void fGenerateTBLINSIDEH(String val, String sz, String space, String color) {
    if ("".equals(val)) {
      val = "single";
    }
    if ("".equals(sz)) {
      sz = "4";
    }
    if ("".equals(space)) {
      space = "0";
    }
    if ("".equals(color)) {
      color = "auto";
    }
    this.xml = this.xml.replace("__GENERATETBLBORDER__", "<" + cCreateElement.NAMESPACE + ":insideH " + cCreateElement.NAMESPACE + ":val='" + val + "' " + cCreateElement.NAMESPACE + ":sz='" + sz + "' " + cCreateElement.NAMESPACE + ":space='" + space + "' " + cCreateElement.NAMESPACE + ":color='" + color + "'></" + cCreateElement.NAMESPACE + ":insideH>__GENERATETBLBORDER__");
  }

  protected void fGenerateTBLINSIDEV(String val, String sz, String space, String color) {
    if ("".equals(val)) {
      val = "single";
    }
    if ("".equals(sz)) {
      sz = "4";
    }
    if ("".equals(space)) {
      space = "0";
    }
    if ("".equals(color)) {
      color = "auto";
    }
    this.xml = this.xml.replace("__GENERATETBLBORDER__", "<" + cCreateElement.NAMESPACE + ":insideV " + cCreateElement.NAMESPACE + ":val='" + val + "' " + cCreateElement.NAMESPACE + ":sz='" + sz + "' " + cCreateElement.NAMESPACE + ":space='" + space + "' " + cCreateElement.NAMESPACE + ":color='" + color + "'></" + cCreateElement.NAMESPACE + ":insideV>__GENERATETBLBORDER__");
  }

  protected void fGenerateTBLLEFT(String val, String sz, String space, String color) {
    if ("".equals(val)) {
      val = "single";
    }
    if ("".equals(sz)) {
      sz = "4";
    }
    if ("".equals(space)) {
      space = "0";
    }
    if ("".equals(color)) {
      color = "auto";
    }
    this.xml = this.xml.replace("__GENERATETBLBORDER__", "<" + cCreateElement.NAMESPACE + ":left " + cCreateElement.NAMESPACE + ":val='" + val + "' " + cCreateElement.NAMESPACE + ":sz='" + sz + "' " + cCreateElement.NAMESPACE + ":space='" + space + "' " + cCreateElement.NAMESPACE + ":color='" + color + "'></" + cCreateElement.NAMESPACE + ":left>__GENERATETBLBORDER__");
  }

  protected void fGenerateTBLLOOK(String strVal) {
    if ("".equals(strVal)) {
      strVal = "000001E0";
    }
    this.xml = this.xml.replace("__GENERATETBLLOOK__", "<" + cCreateElement.NAMESPACE + ":tblLook " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":tblLook>");
  }

  protected void fGenerateTBLOVERLAP(String strVal) {
    if ("".equals(strVal)) {
      strVal = "never";
    }
    this.xml = this.xml.replace("__GENERATETBLOVERLAP__", "<" + cCreateElement.NAMESPACE + ":tblOverlap " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":tblOverlap>");
  }

  protected void fGenerateTBLPR() {
    this.xml = this.xml.replace("__GENERATETBL__", "<" + cCreateElement.NAMESPACE + ":tblPr>__GENERATETBLSTYLE____GENERATEJC____GENERATETBLW____GENERATETBLLOOK____GENERATETBLOVERLAP____GENERATETBLCELLSPACING____GENERATETBLBORDERS____GENERATESHD__</" + cCreateElement.NAMESPACE + ":tblPr>__GENERATETBL__");
  }

  protected void fGenerateTBLRIGHT(String val, String sz, String space, String color) {
    if ("".equals(val)) {
      val = "single";
    }
    if ("".equals(sz)) {
      sz = "4";
    }
    if ("".equals(space)) {
      space = "0";
    }
    if ("".equals(color)) {
      color = "auto";
    }
    this.xml = this.xml.replace("__GENERATETBLBORDER__", "<" + cCreateElement.NAMESPACE + ":right " + cCreateElement.NAMESPACE + ":val='" + val + "' " + cCreateElement.NAMESPACE + ":sz='" + sz + "' " + cCreateElement.NAMESPACE + ":space='" + space + "' " + cCreateElement.NAMESPACE + ":color='" + color + "'></" + cCreateElement.NAMESPACE + ":right>__GENERATETBLBORDER__");
  }

  protected void fGenerateTBLTOP(String val, String sz, String space, String color) {
    if ("".equals(val)) {
      val = "single";
    }
    if ("".equals(sz)) {
      sz = "4";
    }
    if ("".equals(space)) {
      space = "0";
    }
    if ("".equals(color)) {
      color = "auto";
    }
    this.xml = this.xml.replace("__GENERATETBLBORDER__", "<" + cCreateElement.NAMESPACE + ":top " + cCreateElement.NAMESPACE + ":val='" + val + "' " + cCreateElement.NAMESPACE + ":sz='" + sz + "' " + cCreateElement.NAMESPACE + ":space='" + space + "' " + cCreateElement.NAMESPACE + ":color='" + color + "'></" + cCreateElement.NAMESPACE + ":top>__GENERATETBLBORDER__");
  }

  protected void fGenerateTBLW(String strType, String strW) {
    if ("".equals(strType)) {
      strType = "auto";
    }
    if ("".equals(strW)) {
      strW = "0";
    }
    this.xml = this.xml.replace("__GENERATETBLW__", "<" + cCreateElement.NAMESPACE + ":tblW " + cCreateElement.NAMESPACE + ":w='" + strW + "' " + cCreateElement.NAMESPACE + ":type='" + strType + "'></" + cCreateElement.NAMESPACE + ":tblW>");
  }

  protected void fGenerateTC() {
    this.xml = this.xml.replace("__GENERATETR__", "<" + cCreateElement.NAMESPACE + ":tc >__GENERATETC__</" + cCreateElement.NAMESPACE + ":tc>__GENERATETR__");
  }

  protected void fGenerateTCPR() {
    this.xml = this.xml.replace("__GENERATETC__", "<" + cCreateElement.NAMESPACE + ":tcPr>__GENERATETCPR__</" + cCreateElement.NAMESPACE + ":tcPr>__GENERATETC__");
  }

  protected void fGenerateTCW(String w, String type) {
    if ("".equals(w)) {
      w = "";
    }
    if ("".equals(type)) {
      type = "dxa";
    }
    this.xml = this.xml.replace("__GENERATETCPR__", "<" + cCreateElement.NAMESPACE + ":tcW " + cCreateElement.NAMESPACE + ":w='" + w + "' " + cCreateElement.NAMESPACE + ":type='" + type + "'></" + cCreateElement.NAMESPACE + ":tcW>");
  }

  protected void fGenerateTR() {
    this.xml = this.xml.replace("__GENERATETBL__", "<" + cCreateElement.NAMESPACE + ":tr >__GENERATETR__</" + cCreateElement.NAMESPACE + ":tr>__GENERATETBL__");
  }

  protected void fGenerateTRPR() {
  }

  protected void fGenerateVMERGE(String val) {
  }

  public void fCreateTable(HashMap arrArgs) {
    this.xml = "";
    if (arrArgs.containsKey("data")) {
      this.fGenerateTBL();
      this.fGenerateTBLPR();
      this.fGenerateTBLSTYLE("");
      this.fGenerateJC("");
      this.fGenerateTBLW("auto", "0");
      if (arrArgs.containsKey("border")) {
        this.fGenerateTBLBORDERS();
        this.fGenerateTBLBOTTOM(arrArgs.get("border").toString(), "4", "0", "auto");
        this.fGenerateTBLLEFT(arrArgs.get("border").toString(), "4", "0", "auto");
        this.fGenerateTBLTOP(arrArgs.get("border").toString(), "4", "0", "auto");
        this.fGenerateTBLRIGHT(arrArgs.get("border").toString(), "4", "0", "auto");
        this.fGenerateTBLINSIDEH(arrArgs.get("border").toString(), "4", "0", "auto");
        this.fGenerateTBLINSIDEV(arrArgs.get("border").toString(), "4", "0", "auto");
      }
      this.fGenerateTBLLOOK("");
      this.fGenerateTBLOVERLAP("");
      int intLine = 0;
      Iterator arrDatDepth = ((ArrayList) ((HashMap) arrArgs).get("data")).iterator();

      while (arrDatDepth.hasNext()) {
        ArrayList arrDatDepthValue = (ArrayList) arrDatDepth.next();
        this.fGenerateTR();
        intLine++;
        Iterator  unkDat = arrDatDepthValue.iterator();
        while (unkDat.hasNext()) {
          Object unkDatValue = unkDat.next();
          this.fGenerateTC();
          this.fGenerateP();
          this.fGenerateR();
          this.fGenerateT(unkDatValue.toString());
        }
        this.fCleanTemplateR();
      }
    } else {
    	DefaultLogger.debug(this,"There is not data fot the table.");
    }
  }
}
