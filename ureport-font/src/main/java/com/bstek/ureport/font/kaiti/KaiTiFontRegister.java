package com.bstek.ureport.font.kaiti;

import com.bstek.ureport.export.pdf.font.FontRegister;
import org.springframework.stereotype.Component;

/**
 * @author Jacky.gao
 * @since 2014年5月7日
 */
@Component
public class KaiTiFontRegister implements FontRegister {

	public String getFontName() {
		return "楷体";
	}

	public String getFontPath() {
		return "com/bstek/ureport/font/kaiti/SIMKAI.TTF";
	}
}
