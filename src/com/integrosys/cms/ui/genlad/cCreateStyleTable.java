/**
 * Generate tables styles.
 * @author 2mdc
 */
package com.integrosys.cms.ui.genlad;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class cCreateStyleTable extends cCreateStyle {

  private static cCreateStyleTable rscInstance = null;

  public static cCreateStyleTable getInstance() {
    if (cCreateStyleTable.rscInstance == null) {
      cCreateStyleTable.rscInstance = new cCreateStyleTable();
    }
    return cCreateStyleTable.rscInstance;
  }
  protected void fGenerateTBLSTYLEPR(String type) {
    this.xml += "<" + cCreateElement.NAMESPACE + ":tblStylePr " + cCreateElement.NAMESPACE + ":type='" + type + "'>__GENERATESTYLE__</" + cCreateElement.NAMESPACE + ":tblStylePr>";
  }

  //@Override 
  protected void fGenerateTBLPR() {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":tblPr></" + cCreateElement.NAMESPACE + ":tblPr>__GENERATESTYLE__");
  }

 // @Override
  protected void fGenerateTCPR() {
    this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":tcPr>__GENERATETBLPR__</" + cCreateElement.NAMESPACE + ":tcPr>__GENERATESTYLE__");
  }
  protected void fGenerateTCBORDERS() {
    this.xml = this.xml.replace("__GENERATETBLPR__", "<" + cCreateElement.NAMESPACE + ":tcBorders>__GENERATETBLBORDERS__</" + cCreateElement.NAMESPACE + ":tcBorders>__GENERATETBLPR__");
  }

   
  protected void fGenerateRPR() {
    try {
      this.xml = this.xml.replace("__GENERATESTYLE__", "<" + cCreateElement.NAMESPACE + ":rPr>__GENERATERPR__</" + cCreateElement.NAMESPACE + ":rPr>__GENERATESTYLE__");
    } catch (Exception e) {
    	DefaultLogger.debug(this,"There is a exception when generate the style table:" + e.toString() + "\n" + e.getMessage() + "\n" + e.getStackTrace());
    }
  }

   
  protected void fGenerateSHD(String val, String color, String fill, String themeFill, String themeFillTint) {
    String xmlAux = "<" + cCreateElement.NAMESPACE + ":shd " + cCreateElement.NAMESPACE + ":val='" + val + "'";
    if (color.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":color='" + color + "'";
    }
    if (fill.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":fill='" + fill + "'";
    }
    if (themeFill.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeFill='" + themeFill + "'";
    }
    if (themeFillTint.equals("")) {
      xmlAux += " " + cCreateElement.NAMESPACE + ":themeFillTint='" + themeFillTint + "'";
    }
    xmlAux += "></" + cCreateElement.NAMESPACE + ":shd>__GENERATETBLPR__";
    this.xml = this.xml.replace("__GENERATETBLPR__", xmlAux);
  }

  public void fAddStyleTable(HashMap arrArgs) {
    this.xml = "";
    if (arrArgs.containsKey("type")) {
      this.fGenerateTBLSTYLEPR(arrArgs.get("type").toString());
      if (arrArgs.containsKey("name")) {
        this.fGenerateNAME(arrArgs.get("name").toString());
      }
      if (arrArgs.containsKey("basedOn")) {
        this.fGenerateBASEDON(arrArgs.get("basedOn").toString());
      }
      if (arrArgs.containsKey("next")) {
        this.fGenerateNEXT(arrArgs.get("next").toString());
      }
      if (arrArgs.containsKey("link")) {
        this.fGenerateLINK(arrArgs.get("link").toString());
      }
      if (arrArgs.containsKey("autoRedefine") && arrArgs.get("autoRedefine").toString() == "1") {
        this.fGenerateAUTOREDEFINE();
      }
      if (arrArgs.containsKey("uiPriority")) {
        this.fGenerateUIPRIORITY(arrArgs.get("uiPriority").toString());
      }
      if (arrArgs.containsKey("semiHidden") && arrArgs.get("semiHidden").toString() == "1") {
        this.fGenerateSEMIHIDDEN();
      }
      if (arrArgs.containsKey("unhideWhenUsed") && arrArgs.get("unhideWhenUsed").toString() == "1") {
        this.fGenerateUNHIDEWHENUSED();
      }
      if (arrArgs.containsKey("qFormat") && arrArgs.get("qFormat").toString() == "1") {
        this.fGenerateQFORMAT();
      }
      if (arrArgs.containsKey("rsid")) {
        this.fGenerateRSID(arrArgs.get("rsid").toString());
      }
      if ((arrArgs.containsKey("keepNext") && arrArgs.get("keepNext").toString() == "1") || arrArgs.containsKey("keepLines") || (arrArgs.containsKey("spacing_before") || arrArgs.containsKey("spacing_after") || arrArgs.containsKey("spacing_line")
              || arrArgs.containsKey("spacing_lineRule")) || arrArgs.containsKey("outlineLvl") || (arrArgs.containsKey("contextualSpacing") && arrArgs.get("contextualSpacing").toString() == "1") || arrArgs.containsKey("ilvl") || arrArgs.containsKey("ind_left")) {
        this.fGeneratePPR();
        if (arrArgs.containsKey("keepNext") && arrArgs.get("keepNext").toString() == "1") {
          this.fGenerateKEEPNEXT();
        }
        if (arrArgs.containsKey("keepLines")) {
          this.fGenerateKEEPLINES();
        }
        if (arrArgs.containsKey("pBdr_bottom_val") && arrArgs.containsKey("pBdr_bottom_sz") && arrArgs.containsKey("pBdr_bottom_space") && arrArgs.containsKey("pBdr_bottom_color") && arrArgs.containsKey("pBdr_bottom_themeColor")) {
          this.fGeneratePBDR();
          this.fGeneratePBDR_BOTTOM(arrArgs.get("pBdr_bottom_val").toString(), arrArgs.get("pBdr_bottom_sz").toString(), arrArgs.get("pBdr_bottom_space").toString(), arrArgs.get("pBdr_bottom_color").toString(), arrArgs.get("pBdr_bottom_themeColor").toString());
        }
        if (arrArgs.containsKey("tab_center") || arrArgs.containsKey("tab_right")) {
          this.fGenerateTABS();
          if (arrArgs.containsKey("tab_center")) {
            this.fGenerateTABCENTER(arrArgs.get("tab_center").toString());
          }
          if (arrArgs.containsKey("tab_right")) {
            this.fGenerateTABRIGHT(arrArgs.get("tab_right").toString());
          }
        }
        if (arrArgs.containsKey("spacing_before") || arrArgs.containsKey("spacing_after") || arrArgs.containsKey("spacing_line") || arrArgs.containsKey("spacing_lineRule")) {
          String spacing_before = "";
          if (arrArgs.containsKey("spacing_before")) {
            spacing_before = arrArgs.get("spacing_before").toString();
          }
          String spacing_after = "";
          if (arrArgs.containsKey("spacing_after")) {
            spacing_after = arrArgs.get("spacing_after").toString();
          }
          String spacing_line = "";
          if (arrArgs.containsKey("spacing_line")) {
            spacing_line = arrArgs.get("spacing_line").toString();
          }
          String spacing_lineRule = "";
          if (arrArgs.containsKey("spacing_lineRule")) {
            spacing_lineRule = arrArgs.get("spacing_lineRule").toString();
          }
          this.fGenerateSPACING(spacing_before, spacing_after, spacing_line, spacing_lineRule);
        }
        if (arrArgs.containsKey("ind_left")) {
          this.fGenerateIND(arrArgs.get("ind_left").toString());
        }
        if (arrArgs.containsKey("contextualSpacing") && arrArgs.get("contextualSpacing").toString() == "1") {
          this.fGenerateCONTEXTUALSPACING();
        }
        if (arrArgs.containsKey("outlineLvl")) {
          this.fGenerateOUTLINELVL(arrArgs.get("outlineLvl").toString());
        }
        if (arrArgs.containsKey("ilvl")) {
          this.fGenerateNUMPR();
          this.fGenerateILVL(arrArgs.get("ilvl").toString());
        }
      }
      if ((arrArgs.containsKey("rFonts_asciiTheme") && arrArgs.containsKey("rFonts_eastAsiaTheme") && arrArgs.containsKey("rFonts_hAnsiTheme") && arrArgs.containsKey("rFonts_cstheme"))
              || (arrArgs.containsKey("b") && "1".equals(arrArgs.get("b").toString())) || (arrArgs.containsKey("bCs") && "1".equals(arrArgs.get("bCs").toString())) || (arrArgs.containsKey("i") && "1".equals(arrArgs.get("i").toString()))
              || (arrArgs.containsKey("iCs") && "1".equals(arrArgs.get("iCs").toString())) || (arrArgs.containsKey("u") && "1".equals(arrArgs.get("u").toString())) || arrArgs.containsKey("color_val")
              || arrArgs.containsKey("sz") || arrArgs.containsKey("szCs") || arrArgs.containsKey("kern") || arrArgs.containsKey("rPr_spacing") || arrArgs.containsKey("u")) {
        this.fGenerateRPR();
        if (arrArgs.containsKey("rFonts_asciiTheme") && arrArgs.containsKey("rFonts_eastAsiaTheme") && arrArgs.containsKey("rFonts_hAnsiTheme") && arrArgs.containsKey("rFonts_cstheme")) {
          this.fGenerateRFONTS(arrArgs.get("rFonts_asciiTheme").toString(), arrArgs.get("rFonts_eastAsiaTheme").toString(), arrArgs.get("rFonts_hAnsiTheme").toString(), arrArgs.get("rFonts_cstheme").toString());
        }
        if (arrArgs.containsKey("rFonts_ascii") && arrArgs.containsKey("rFonts_hAnsi") && arrArgs.containsKey("rFonts_cs")) {
          this.fGenerateRFONTS2(arrArgs.get("rFonts_ascii").toString(), arrArgs.get("rFonts_hAnsi").toString(), arrArgs.get("rFonts_cs").toString());
        }
        if (arrArgs.containsKey("b") && "1".equals(arrArgs.get("b").toString())) {
          this.fGenerateB();
        }
        if (arrArgs.containsKey("bCs") && "1".equals(arrArgs.get("bCs").toString())) {
          this.fGenerateBCS();
        }
        if (arrArgs.containsKey("i")) {
          this.fGenerateI(arrArgs.get("i").toString());
        }
        if (arrArgs.containsKey("iCs")) {
          this.fGenerateICS(arrArgs.get("iCs").toString());
        }
        if (arrArgs.containsKey("u")) {
          this.fGenerateU(arrArgs.get("u").toString());
        }
        if (arrArgs.containsKey("color_val")) {
          String color_themeColor = "";
          if (arrArgs.containsKey("color_themeColor")) {
            color_themeColor = arrArgs.get("color_themeColor").toString();
          }
          String color_themeShade = "";
          if (arrArgs.containsKey("color_themeShade")) {
            color_themeShade = arrArgs.get("color_themeShade").toString();
          }
          this.fGenerateCOLOR(arrArgs.get("color_val").toString(), color_themeColor, color_themeShade);
        }
        if (arrArgs.containsKey("u")) {
          this.fGenerateU(arrArgs.get("u").toString());
        }
        if (arrArgs.containsKey("rPr_spacing")) {
          this.fGenerateRPR_SPACING(arrArgs.get("rPr_spacing").toString());
        }
        if (arrArgs.containsKey("kern")) {
          this.fGenerateKERN(arrArgs.get("kern").toString());
        }
        if (arrArgs.containsKey("sz")) {
          this.fGenerateSZ(arrArgs.get("sz").toString());
        }
        if (arrArgs.containsKey("szCs")) {
          this.fGenerateSZCS(arrArgs.get("szCs").toString());
        }
      }
      if (arrArgs.containsKey("tblPr")) {
        this.fGenerateTBLPR();
      }
      if ((arrArgs.containsKey("top_w") && arrArgs.containsKey("top_type")) || (arrArgs.containsKey("left_w") && arrArgs.containsKey("left_type")) || (arrArgs.containsKey("bottom_w")
              && arrArgs.containsKey("bottom_type")) || (arrArgs.containsKey("right_w") && arrArgs.containsKey("right_type")) || (arrArgs.containsKey("tblInd_w") && arrArgs.containsKey("tblInd_type"))
              && arrArgs.containsKey("tblborder_top_val") || arrArgs.containsKey("tblborder_left_val") || arrArgs.containsKey("tblborder_bottom_val") || arrArgs.containsKey("tblborder_right_val")
              || arrArgs.containsKey("tblborder_insideH_val") || arrArgs.containsKey("tblborder_insideV_val") || (arrArgs.containsKey("shd_val") && arrArgs.containsKey("shd_color")
              && arrArgs.containsKey("shd_fill") && arrArgs.containsKey("shd_themeFill"))) {
        this.fGenerateTCPR();
        if (arrArgs.containsKey("tbl_style_row")) {
          this.fGenerateTBLSTYLEROWBANDSIZE(arrArgs.get("tbl_style_row").toString());
        }
        if (arrArgs.containsKey("tbl_style_col")) {
          this.fGenerateTBLSTYLECOLBANDSIZE(arrArgs.get("tbl_style_col").toString());
        }
        if (arrArgs.containsKey("tblInd_w") && arrArgs.containsKey("tblInd_type")) {
          this.fGenerateTBLIND(arrArgs.get("tblInd_w").toString(), arrArgs.get("tblInd_type").toString());
        }
        if (arrArgs.containsKey("tblborder_top_val") || arrArgs.containsKey("tblborder_left_val") || arrArgs.containsKey("tblborder_bottom_val") || arrArgs.containsKey("tblborder_right_val")
                || arrArgs.containsKey("tblborder_insideH_val") || arrArgs.containsKey("tblborder_insideV_val")) {
          this.fGenerateTCBORDERS();
          if (arrArgs.containsKey("tblborder_top_val")) {
            String tblborder_top_sz = "";
            if (arrArgs.containsKey("tblborder_top_sz")) {
              tblborder_top_sz = arrArgs.get("tblborder_top_sz").toString();
            }
            String tblborder_top_space = "";
            if (arrArgs.containsKey("tblborder_top_space")) {
              tblborder_top_space = arrArgs.get("tblborder_top_space").toString();
            }
            String tblborder_top_color = "";
            if (arrArgs.containsKey("tblborder_top_color")) {
              tblborder_top_color = arrArgs.get("tblborder_top_color").toString();
            }
            String tblborder_top_themeColor = "";
            if (arrArgs.containsKey("tblborder_top_themeColor")) {
              tblborder_top_themeColor = arrArgs.get("tblborder_top_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrArgs.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrArgs.get("tblborder_insideV_themeTint").toString();
            }

            this.fGenerateTBLBORDERS_TOP(arrArgs.get("tblborder_top_val").toString(), tblborder_top_sz, tblborder_top_space, tblborder_top_color, tblborder_top_themeColor, tblborder_insideV_themeTint);
          }
          if (arrArgs.containsKey("tblborder_left_val")) {
            String tblborder_left_sz = "";
            if (arrArgs.containsKey("tblborder_left_sz")) {
              tblborder_left_sz = arrArgs.get("tblborder_left_sz").toString();
            }
            String tblborder_left_space = "";
            if (arrArgs.containsKey("tblborder_left_space")) {
              tblborder_left_space = arrArgs.get("tblborder_left_space").toString();
            }
            String tblborder_left_color = "";
            if (arrArgs.containsKey("tblborder_left_color")) {
              tblborder_left_color = arrArgs.get("tblborder_left_color").toString();
            }
            String tblborder_left_themeColor = "";
            if (arrArgs.containsKey("tblborder_left_themeColor")) {
              tblborder_left_themeColor = arrArgs.get("tblborder_left_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrArgs.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrArgs.get("tblborder_insideV_themeTint").toString();
            }
            this.fGenerateTBLBORDERS_LEFT(arrArgs.get("tblborder_left_val").toString(), tblborder_left_sz, tblborder_left_space, tblborder_left_color, tblborder_left_themeColor, tblborder_insideV_themeTint);
          }
          if (arrArgs.containsKey("tblborder_bottom_val")) {
            String tblborder_bottom_sz = "";
            if (arrArgs.containsKey("tblborder_bottom_sz")) {
              tblborder_bottom_sz = arrArgs.get("tblborder_bottom_sz").toString();
            }
            String tblborder_bottom_space = "";
            if (arrArgs.containsKey("tblborder_bottom_space")) {
              tblborder_bottom_space = arrArgs.get("tblborder_bottom_space").toString();
            }
            String tblborder_bottom_color = "";
            if (arrArgs.containsKey("tblborder_bottom_color")) {
              tblborder_bottom_color = arrArgs.get("tblborder_bottom_color").toString();
            }
            String tblborder_bottom_themeColor = "";
            if (arrArgs.containsKey("tblborder_bottom_themeColor")) {
              tblborder_bottom_themeColor = arrArgs.get("tblborder_bottom_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrArgs.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrArgs.get("tblborder_insideV_themeTint").toString();
            }
            this.fGenerateTBLBORDERS_BOTTOM(arrArgs.get("tblborder_bottom_val").toString(), tblborder_bottom_sz, tblborder_bottom_space, tblborder_bottom_color, tblborder_bottom_themeColor, tblborder_insideV_themeTint);
          }
          if (arrArgs.containsKey("tblborder_right_val")) {
            String tblborder_right_sz = "";
            if (arrArgs.containsKey("tblborder_right_sz")) {
              tblborder_right_sz = arrArgs.get("tblborder_right_sz").toString();
            }
            String tblborder_right_space = "";
            if (arrArgs.containsKey("tblborder_right_space")) {
              tblborder_right_space = arrArgs.get("tblborder_right_space").toString();
            }
            String tblborder_right_color = "";
            if (arrArgs.containsKey("tblborder_right_color")) {
              tblborder_right_color = arrArgs.get("tblborder_right_color").toString();
            }
            String tblborder_right_themeColor = "";
            if (arrArgs.containsKey("tblborder_right_themeColor")) {
              tblborder_right_themeColor = arrArgs.get("tblborder_right_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrArgs.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrArgs.get("tblborder_insideV_themeTint").toString();
            }
            this.fGenerateTBLBORDERS_RIGHT(arrArgs.get("tblborder_right_val").toString(), tblborder_right_sz, tblborder_right_space, tblborder_right_color, tblborder_right_themeColor, tblborder_insideV_themeTint);
          }
          if (arrArgs.containsKey("tblborder_insideH_val")) {
            String tblborder_insideH_sz = "";
            if (arrArgs.containsKey("tblborder_insideH_sz")) {
              tblborder_insideH_sz = arrArgs.get("tblborder_insideH_sz").toString();
            }
            String tblborder_insideH_space = "";
            if (arrArgs.containsKey("tblborder_insideH_space")) {
              tblborder_insideH_space = arrArgs.get("tblborder_insideH_space").toString();
            }
            String tblborder_insideH_color = "";
            if (arrArgs.containsKey("tblborder_insideH_color")) {
              tblborder_insideH_color = arrArgs.get("tblborder_insideH_color").toString();
            }
            String tblborder_insideH_themeColor = "";
            if (arrArgs.containsKey("tblborder_insideH_themeColor")) {
              tblborder_insideH_themeColor = arrArgs.get("tblborder_insideH_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrArgs.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrArgs.get("tblborder_insideV_themeTint").toString();
            }
            this.fGenerateTBLBORDERS_INSIDEH(arrArgs.get("tblborder_insideH_val").toString(), tblborder_insideH_sz, tblborder_insideH_space, tblborder_insideH_color, tblborder_insideH_themeColor, tblborder_insideV_themeTint);
          }
          if (arrArgs.containsKey("tblborder_insideV_val")) {
            String tblborder_insideV_sz = "";
            if (arrArgs.containsKey("tblborder_insideV_sz")) {
              tblborder_insideV_sz = arrArgs.get("tblborder_insideV_sz").toString();
            }
            String tblborder_insideV_space = "";
            if (arrArgs.containsKey("tblborder_insideV_space")) {
              tblborder_insideV_space = arrArgs.get("tblborder_insideV_space").toString();
            }
            String tblborder_insideV_color = "";
            if (arrArgs.containsKey("tblborder_insideV_color")) {
              tblborder_insideV_color = arrArgs.get("tblborder_insideV_color").toString();
            }
            String tblborder_insideV_themeColor = "";
            if (arrArgs.containsKey("tblborder_insideV_themeColor")) {
              tblborder_insideV_themeColor = arrArgs.get("tblborder_insideV_themeColor").toString();
            }
            String tblborder_insideV_themeTint = "";
            if (arrArgs.containsKey("tblborder_insideV_themeTint")) {
              tblborder_insideV_themeTint = arrArgs.get("tblborder_insideV_themeTint").toString();
            }
            this.fGenerateTBLBORDERS_INSIDEV(arrArgs.get("tblborder_insideV_val").toString(), tblborder_insideV_sz, tblborder_insideV_space, tblborder_insideV_color, tblborder_insideV_themeColor, tblborder_insideV_themeTint);
          }
        }
        if (arrArgs.containsKey("shd_val") && arrArgs.containsKey("shd_color") && arrArgs.containsKey("shd_fill") && arrArgs.containsKey("shd_themeFill")) {
          String shd_themeFillTint = "";
          if (arrArgs.containsKey("shd_themeFillTint")) {
            shd_themeFillTint = arrArgs.get("shd_themeFillTint").toString();
          }
          this.fGenerateSHD(arrArgs.get("shd_val").toString(), arrArgs.get("shd_color").toString(), arrArgs.get("shd_fill").toString(), arrArgs.get("shd_themeFill").toString(), shd_themeFillTint);
        }
        if ((arrArgs.containsKey("top_w") && arrArgs.containsKey("top_type")) || (arrArgs.containsKey("left_w") && arrArgs.containsKey("left_type")) || (arrArgs.containsKey("bottom_w") && arrArgs.containsKey("bottom_type")) || (arrArgs.containsKey("right_w") && arrArgs.containsKey("right_type"))) {
          this.fGenerateTBLCELLMAR();
          if (arrArgs.containsKey("top_w") && arrArgs.containsKey("top_type")) {
            this.fGenerateTOP(arrArgs.get("top_w").toString(), arrArgs.get("top_type").toString());
          }
          if (arrArgs.containsKey("left_w") && arrArgs.containsKey("left_type")) {
            this.fGenerateLEFT(arrArgs.get("left_w").toString(), arrArgs.get("left_type").toString());
          }
          if (arrArgs.containsKey("bottom_w") && arrArgs.containsKey("bottom_type")) {
            this.fGenerateBOTTOM(arrArgs.get("bottom_w").toString(), arrArgs.get("bottom_type").toString());
          }
          if (arrArgs.containsKey("right_w") && arrArgs.containsKey("right_type")) {
            this.fGenerateRIGHT(arrArgs.get("right_w").toString(), arrArgs.get("right_type").toString());
          }
        }
      }
      this.fCleanTemplate();
      this.xml += "__GENERATESTYLE__";
    }
  }
}
