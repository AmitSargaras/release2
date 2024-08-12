/**
 * Generate elements.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class cCreateElement {
  //private cCreateImage objImage = new cCreateImage();

  protected static final String MATHNAMESPACE = "m";
  protected static final String NAMESPACE = "w";
  protected static final String IMGNAMESPACE = "wp";
  protected static final String NAMESPACE1 = "a";
  protected static final String NAMESPACE2 = "pic";
  protected static final int CONSTWORD = 360000;
  protected static final int TAMBORDER = 12700;
  protected String xml;

  public cCreateElement() {
  }

  protected void fGenerateP() {
    this.xml = "<" + cCreateElement.NAMESPACE + ":p>__GENERATEP__</" + cCreateElement.NAMESPACE + ":p>";
  }

  protected void fGeneratePPR() {
    this.xml = this.xml.replace("__GENERATEP__", "<" + cCreateElement.NAMESPACE + ":pPr>__GENERATEPPR__</" + cCreateElement.NAMESPACE + ":pPr>__GENERATER__");
  }

  protected void fGenerateT(String strDat) {
    this.xml = this.xml.replace("__GENERATER__", "<" + cCreateElement.NAMESPACE + ":t>" + strDat + "</" + cCreateElement.NAMESPACE + ":t>");
  }

  protected void fGenerateRPR() {
    this.xml = this.xml.replace("__GENERATER__", "<" + cCreateElement.NAMESPACE + ":rPr>__GENERATERPR__</" + cCreateElement.NAMESPACE + ":rPr>__GENERATER__");
  }

  protected void fGenerateJC(String strVal) {
    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":jc " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":jc>");
  }

  protected void fGenerateNOPROOF() {
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":noProof></" + cCreateElement.NAMESPACE + ":noProof>__GENERATEPPR__");
  }

  protected void fGenerateR() {
    if (this.xml.length() != 0) {
      if (this.xml.indexOf("__GENERATEP__") != -1) {
        this.xml = this.xml.replace("__GENERATEP__", "<" + cCreateElement.NAMESPACE + ":r>__GENERATER__</" + cCreateElement.NAMESPACE + ":r>");
      } else {
        if (this.xml.indexOf("__GENERATER__") != -1) {
          this.xml = this.xml.replace("__GENERATER__", "<" + cCreateElement.NAMESPACE + ":r>__GENERATER__</" + cCreateElement.NAMESPACE + ":r>");
        }
      }
    } else {
      this.xml = "<" + cCreateElement.NAMESPACE + ":r>__GENERATER__</" + cCreateElement.NAMESPACE + ":r>";
    }
  }

  protected void fGenerateDRAWING() {
    this.xml = this.xml.replace("__GENERATER__", "<" + cCreateElement.NAMESPACE + ":drawing>__GENERATEDRAWING__</" + cCreateElement.NAMESPACE + ":drawing>");
  }

  protected void fGenerateINLINE(String distT, String distB, String distL, String distR) {
    if ("".equals(distT)) {
      distT = "0";
    }
    if ("".equals(distB)) {
      distB = "0";
    }
    if ("".equals(distL)) {
      distL = "0";
    }
    if ("".equals(distR)) {
      distR = "0";
    }

    this.xml = this.xml.replace("__GENERATEDRAWING__", "<" + this.IMGNAMESPACE + ":inline distT='" + distT + "' distB='" + distB + "' distL='" + distL + "' distR='" + distR + "'>__GENERATEINLINE__</" + this.IMGNAMESPACE + ":inline>");
  }

  protected void fGenerateANCHOR(String behindDoc, String distT, String distB, String distL, String distR, String simplePos, String relativeHeight, String locked, String layoutInCell, String allowOverlap) {
    if ("".equals(behindDoc)) {
      behindDoc = "0";
    }
    if ("".equals(distT)) {
      distT = "0";
    }
    if ("".equals(distB)) {
      distB = "0";
    }
    if ("".equals(distL)) {
      distL = "114300";
    }
    if ("".equals(distR)) {
      distR = "114300";
    }
    if ("".equals(simplePos)) {
      simplePos = "0";
    }
    if ("".equals(relativeHeight)) {
      relativeHeight = "251658240";
    }
    if ("".equals(locked)) {
      locked = "0";
    }
    if ("".equals(layoutInCell)) {
      layoutInCell = "1";
    }
    if ("".equals(allowOverlap)) {
      allowOverlap = "1";
    }

    this.xml = this.xml.replace("__GENERATEDRAWING__", "<" + this.IMGNAMESPACE + ":anchor distT='" + distT + "' distB='" + distB + "' distL='" + distL + "' distR='" + distR + "' simplePos='" + simplePos + "' relativeHeight='" + relativeHeight + "' behindDoc='" + behindDoc + "' locked='" + locked + "' layoutInCell='" + layoutInCell + "' allowOverlap='" + allowOverlap + "'>__GENERATEINLINE__</" + this.IMGNAMESPACE + ":anchor>");
  }

  protected void fGenerateSIMPLEPOS(String x, String y) {
    if ("".equals(x)) {
      x = "0";
    }
    if ("".equals(y)) {
      y = "0";
    }

    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.IMGNAMESPACE + ":simplePos x='" + x + "' y='" + y + "'></" + this.IMGNAMESPACE + ":simplePos>__GENERATEINLINE__");
  }

  protected void fGeneratePOSITIONH(String relativeFrom) {
    if ("".equals(relativeFrom)) {
      relativeFrom = "column";
    }

    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.IMGNAMESPACE + ":positionH relativeFrom='" + relativeFrom + "'>__GENERATEPOSITION__</" + this.IMGNAMESPACE + ":positionH>__GENERATEINLINE__");
  }

  protected void fGeneratePOSITIONV(String relativeFrom) {
    if ("".equals(relativeFrom)) {
      relativeFrom = "paragraph";
    }

    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.IMGNAMESPACE + ":positionV relativeFrom='" + relativeFrom + "'>__GENERATEPOSITION__</" + this.IMGNAMESPACE + ":positionV>__GENERATEINLINE__");
  }

  protected void fGeneratePOSOFFSET(String num) {
    this.xml = this.xml.replace("__GENERATEPOSITION__", "<" + this.IMGNAMESPACE + ":posOffset>" + num + "</" + this.IMGNAMESPACE + ":posOffset>");
  }

  protected void fGenerateEXTENT(String cx, String cy) {
    if ("".equals(cx)) {
      cx = "2986543";
    }
    if ("".equals(cy)) {
      cy = "2239906";
    }

    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.IMGNAMESPACE + ":extent cx='" + cx + "' cy='" + cy + "'></" + this.IMGNAMESPACE + ":extent>__GENERATEINLINE__");
  }

  protected void fGenerateEFFECTEXTENT(String l, String t, String r, String b) {
    if ("".equals(l)) {
      l = "19050";
    }
    if ("".equals(t)) {
      t = "0";
    }
    if ("".equals(r)) {
      r = "4307";
    }
    if ("".equals(b)) {
      b = "0";
    }

    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.IMGNAMESPACE + ":effectExtent l='" + l + "' t='" + t + "' r='" + r + "' b='" + b + "'></" + this.IMGNAMESPACE + ":effectExtent>__GENERATEINLINE__");
  }

  protected void fGenerateWRAPSQUARE(String wrapText) {
    if ("".equals(wrapText)) {
      wrapText = "bothSides";
    }

    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.IMGNAMESPACE + ":wrapSquare wrapText='" + wrapText + "'></" + this.IMGNAMESPACE + ":wrapSquare>__GENERATEINLINE__");
  }

  protected void fGenerateWRAPNONE() {
    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.IMGNAMESPACE + ":wrapNone></" + this.IMGNAMESPACE + ":wrapNone>__GENERATEINLINE__");
  }

  protected void fGenerateWRAPTOPANDBOTTOM() {
    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.IMGNAMESPACE + ":wrapTopAndBottom></" + this.IMGNAMESPACE + ":wrapTopAndBottom>__GENERATEINLINE__");
  }

  protected void fGenerateWRAPTHROUGH(String wrapText) {
    if ("".equals(wrapText)) {
      wrapText = "bothSides";
    }

    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.IMGNAMESPACE + ":wrapThrough wrapText='" + wrapText + "'>__GENERATEWRAPTHROUGH__</" + this.IMGNAMESPACE + ":wrapThrough>__GENERATEINLINE__");
  }

  protected void fGenerateWRAPPOLYGON(String edited) {
    if ("".equals(edited)) {
      edited = "0";
    }

    this.xml = this.xml.replace("__GENERATEWRAPTHROUGH__", "<" + this.IMGNAMESPACE + ":wrapPolygon edited='" + edited + "'>__GENERATEWRAPPOLYGON__</" + this.IMGNAMESPACE + ":wrapPolygon>");
  }

  protected void fGenerateSTART(String x, String y) {
    if ("".equals(x)) {
      x = "-198";
    }
    if ("".equals(y)) {
      y = "0";
    }

    this.xml = this.xml.replace("__GENERATEWRAPPOLYGON__", "<" + this.IMGNAMESPACE + ":start x='" + x + "' y='" + y + "'></" + this.IMGNAMESPACE + ":start>__GENERATEWRAPPOLYGON__");
  }

  protected void fGenerateLINETO(String x, String y) {
    if ("".equals(x)) {
      x = "-198";
    }
    if ("".equals(y)) {
      y = "21342";
    }

    this.xml = this.xml.replace("__GENERATEWRAPPOLYGON__", "<" + this.IMGNAMESPACE + ":lineTo x='" + x + "' y='" + y + "'></" + this.IMGNAMESPACE + ":lineTo>__GENERATEWRAPPOLYGON__");
  }

  protected void fGenerateDOCPR(String id, String name) {
    if ("".equals(id)) {
      id = "1";
    }
    if ("".equals(name)) {
      name = "0 Imagen";
    }

    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.IMGNAMESPACE + ":docPr id='" + id + "' name='" + name + "' descr='" + name + "'></" + this.IMGNAMESPACE + ":docPr>__GENERATEINLINE__");
  }

  protected void fGenerateCNVGRAPHICFRAMEPR() {
    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.IMGNAMESPACE + ":cNvGraphicFramePr>__GENERATECNVGRAPHICFRAMEPR__</" + this.IMGNAMESPACE + ":cNvGraphicFramePr>__GENERATEINLINE__");
  }

  protected void fGenerateGRAPHICPRAMELOCKS(String noChangeAspect) {
    if ("".equals(noChangeAspect)) {
      noChangeAspect = "";
    }

    String xmlAux = "<" + this.NAMESPACE1 + ":graphicFrameLocks xmlns:a='http://schemas.openxmlformats.org/drawingml/2006/main'";
    if (!"".equals(noChangeAspect)) {
      xmlAux += " noChangeAspect='" + noChangeAspect + "'";
    }
    xmlAux += "></" + this.NAMESPACE1 + ":graphicFrameLocks>";

    this.xml = this.xml.replace("__GENERATECNVGRAPHICFRAMEPR__", xmlAux);
  }

  protected void fGenerateGRAPHIC(String xmlns) {
    if ("".equals(xmlns)) {
      xmlns = "http://schemas.openxmlformats.org/drawingml/2006/main";
    }

    this.xml = this.xml.replace("__GENERATEINLINE__", "<" + this.NAMESPACE1 + ":graphic xmlns:a='" + xmlns + "'>__GENERATEGRAPHIC__</" + this.NAMESPACE1 + ":graphic>");
  }

  protected void fGenerateGRAPHICDATA(String uri) {
    if ("".equals(uri)) {
      uri = "http://schemas.openxmlformats.org/drawingml/2006/picture";
    }

    this.xml = this.xml.replace("__GENERATEGRAPHIC__", "<" + this.NAMESPACE1 + ":graphicData uri='" + uri + "'>__GENERATEGRAPHICDATA__</" + this.NAMESPACE1 + ":graphicData>");
  }

  protected void fGeneratePIC(String pic) {
    if ("".equals(pic)) {
      pic = "http://schemas.openxmlformats.org/drawingml/2006/picture";
    }

    this.xml = this.xml.replace("__GENERATEGRAPHICDATA__", "<" + this.NAMESPACE2 + ":pic xmlns:pic='" + pic + "'>__GENERATEPIC__</" + this.NAMESPACE2 + ":pic>");
  }

  protected void fGenerateNVPICPR() {
    this.xml = this.xml.replace("__GENERATEPIC__", "<" + this.NAMESPACE2 + ":nvPicPr>__GENERATENVPICPR__</" + this.NAMESPACE2 + ":nvPicPr>__GENERATEPIC__");
  }

  protected void fGenerateCNVPR(String id, String name) {
    if ("".equals(id)) {
      id = "0";
    }
    if ("".equals(name)) {
      name = "";
    }

    this.xml = this.xml.replace("__GENERATENVPICPR__", "<" + this.NAMESPACE2 + ":cNvPr id='" + id + "' name='" + name + "'></" + this.NAMESPACE2 + ":cNvPr>__GENERATENVPICPR__");
  }

  protected void fGenerateCNVPICPR() {
    this.xml = this.xml.replace("__GENERATENVPICPR__", "<" + this.NAMESPACE2 + ":cNvPicPr></" + this.NAMESPACE2 + ":cNvPicPr>__GENERATENVPICPR__");
  }

  protected void fGenerateBLIPFILL() {
    this.xml = this.xml.replace("__GENERATEPIC__", "<" + this.NAMESPACE2 + ":blipFill>__GENERATEBLIPFILL__</" + this.NAMESPACE2 + ":blipFill>__GENERATEPIC__");
  }

  protected void fGenerateSTRETCH() {
    this.xml = this.xml.replace("__GENERATEBLIPFILL__", "<" + this.NAMESPACE1 + ":stretch>__GENERATESTRETCH__</" + this.NAMESPACE1 + ":stretch>__GENERATEBLIPFILL__");
  }

  protected void fGenerateFILLRECT() {
    this.xml = this.xml.replace("__GENERATESTRETCH__", "<" + this.NAMESPACE1 + ":fillRect></" + this.NAMESPACE1 + ":fillRect>");
  }

  protected void fGenerateB(String str) {
    try {
      String strVal;
      if ("".equals(str)) {
        strVal = "single";
      } else {
        strVal = str;
      }
      this.xml = this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":b " + cCreateElement.NAMESPACE + ":val='" + strVal + "'></" + cCreateElement.NAMESPACE + ":b>__GENERATERPR__");
    } catch (Exception e) {
    	DefaultLogger.debug(this,"There is a problem generating the 'b' tag " + e.toString());
    }
  }

  protected void fGenerateBCS() {
    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":bCs></" + cCreateElement.NAMESPACE + ":bCs>__GENERATERPR__");
  }

  protected void fGenerateSPPR() {
    this.xml = this.xml.replace("__GENERATEPIC__", "<" + this.NAMESPACE2 + ":spPr>__GENERATESPPR__</" + this.NAMESPACE2 + ":spPr>");
  }

  protected void fGenerateXFRM() {
    this.xml = this.xml.replace("__GENERATESPPR__", "<" + this.NAMESPACE1 + ":xfrm>__GENERATEXFRM__</" + this.NAMESPACE1 + ":xfrm>__GENERATESPPR__");
  }

  protected void fGenerateOFF(String x, String y) {
    if ("".equals(x)) {
      x = "0";
    }
    if ("".equals(y)) {
      y = "0";
    }

    this.xml = this.xml.replace("__GENERATEXFRM__", "<" + this.NAMESPACE1 + ":off x='" + x + "' y='" + y + "'></" + this.NAMESPACE1 + ":off>__GENERATEXFRM__");
  }

  protected void fGenerateEXT(String cx, String cy) {
    if ("".equals(cx)) {
      cx = "2997226";
    }
    if ("".equals(cy)) {
      cy = "2247918";
    }

    this.xml = this.xml.replace("__GENERATEXFRM__", "<" + this.NAMESPACE1 + ":ext cx='" + cx + "' cy='" + cy + "'></" + this.NAMESPACE1 + ":ext>__GENERATEXFRM__");
  }

  protected void fGeneratePRSTGEOM(String prst) {
    if ("".equals(prst)) {
      prst = "rect";
    }

    this.xml = this.xml.replace("__GENERATESPPR__", "<" + this.NAMESPACE1 + ":prstGeom prst='" + prst + "'>__GENERATEPRSTGEOM__</" + this.NAMESPACE1 + ":prstGeom>__GENERATESPPR__");
  }

  protected void fGenerateAVLST() {
    this.xml = this.xml.replace("__GENERATEPRSTGEOM__", "<" + this.NAMESPACE1 + ":avLst></" + this.NAMESPACE1 + ":avLst>__GENERATEPRSTGEOM__");
  }

  protected void fGenerateLN(String w) {
    if ("".equals(w)) {
      w = "12700";
    }

    this.xml = this.xml.replace("__GENERATESPPR__", "<" + this.NAMESPACE1 + ":ln w='" + w + "'>__GENERATELN__</" + this.NAMESPACE1 + ":ln>__GENERATESPPR__");
  }

  protected void fGenerateSOLIDFILL() {
    this.xml = this.xml.replace("__GENERATELN__", "<" + this.NAMESPACE1 + ":solidFill>__GENERATESOLIDFILL__</" + this.NAMESPACE1 + ":solidFill>__GENERATELN__");
  }

  protected void fGenerateSCHEMECLR(String val) {
    if ("".equals(val)) {
      val = "tx1";
    }

    this.xml = this.xml.replace("__GENERATESOLIDFILL__", "<" + this.NAMESPACE1 + ":schemeClr val='" + val + "'></" + this.NAMESPACE1 + ":schemeClr>");
  }

  protected void fGeneratePRSTDASH(String val) {
    if ("".equals(val)) {
      val = "sysDash";
    }

    this.xml = this.xml.replace("__GENERATELN__", "<" + this.NAMESPACE1 + ":prstDash val='" + val + "'></" + this.NAMESPACE1 + ":prstDash>__GENERATELN__");
  }

  protected void fCleanTemplate() {
    this.xml = this.xml.replaceAll("__[A-Z]*__", "");
  }

  protected void fGenerateQUITAR() {
    this.xml = "<" + cCreateElement.NAMESPACE + ":r>__GENERATER__</" + cCreateElement.NAMESPACE + ":r>";
  }

  protected void fGeneratePSTYLE(String val) {
    if ("".equals(val)) {
      val = "Textonotaalfinal";
    }

    this.xml = this.xml.replace("__GENERATEPPR__", "<" + cCreateElement.NAMESPACE + ":pStyle " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":pStyle>");
  }

  protected void fGenerateRSTYLE(String val) {
    if ("".equals(val)) {
      val = "Refdenotaalfinal";
    }

    this.xml = this.xml.replace("__GENERATERPR__", "<" + cCreateElement.NAMESPACE + ":rStyle " + cCreateElement.NAMESPACE + ":val='" + val + "'></" + cCreateElement.NAMESPACE + ":rStyle>");
  }
}
