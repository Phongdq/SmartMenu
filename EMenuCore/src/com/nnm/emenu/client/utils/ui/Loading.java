/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.PopupPanel;
import com.googlecode.mgwt.ui.client.widget.progress.ProgressIndicator;

/**
 * @author bizluvsakura
 *
 */
public class Loading {
	private static Loading INSTANCE;
	PopupPanel loading;

	public Loading() {
		loading = new PopupPanel();
		loading.setStyleName("popuploading");
		loading.setAnimationEnabled(false);
		ProgressIndicator pro = new ProgressIndicator();
		pro.setSize("20%", "20%");
		pro.getElement().getStyle().setMarginLeft(48, Unit.PCT);
		pro.getElement().getStyle().setMarginTop(18, Unit.PCT);
		loading.add(pro);
		loading.setAutoHideEnabled(true);
	}

	public void show() {
		loading.show();
	}

	public void hide() {
		loading.hide();
	}

	public static Loading getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Loading();
		}
		return INSTANCE;
	}
}
