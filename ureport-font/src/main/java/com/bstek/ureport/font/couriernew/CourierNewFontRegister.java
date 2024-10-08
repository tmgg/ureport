package com.bstek.ureport.font.couriernew;

import com.bstek.ureport.export.pdf.font.FontRegister;
import org.springframework.stereotype.Component;

/**
 * @author Jacky.gao
 * @since 2014年5月7日
 */
@Component
public class CourierNewFontRegister implements FontRegister {

	public String getFontName() {
		return "Courier New";
	}

	public String getFontPath() {
		return "com/bstek/ureport/font/couriernew/COUR.TTF";
	}
}
