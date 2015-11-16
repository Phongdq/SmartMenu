/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;

/**
 * @author bizluvsakura
 *
 */
public class CustomSearchTextBox extends Composite implements HasText {

	private static CustomSearchTextBoxUiBinder uiBinder = GWT
			.create(CustomSearchTextBoxUiBinder.class);

	interface CustomSearchTextBoxUiBinder extends
			UiBinder<Widget, CustomSearchTextBox> {
	}

	@UiField
	HorizontalPanel parent;
	@UiField
	public TextBox tb;
	@UiField
	public Image icon;

	boolean isHandleIconListener;

	public CustomSearchTextBox(float width, float height) {
		initWidget(uiBinder.createAndBindUi(this));
		asWidget().setSize(width + "px", height + "px");

		float h = 0.8f * height;
		icon.setSize(h + "px", h + "px");
		parent.setSize(width + "px", height + "px");
		parent.setCellWidth(tb, (width - height) + "px");
		parent.setCellWidth(icon, height + "px");
		config();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			tb.getElement().getStyle().setFontSize(140, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			tb.getElement().getStyle().setFontSize(170, Unit.PCT);
		} else {
			tb.getElement().getStyle().setFontSize(110, Unit.PCT);
		}
	}

	public void setHint(String hint) {
		tb.getElement().setAttribute("placeholder", hint);
	}

	public void handleIconSearch(final ClickListener listener) {
		if (!isHandleIconListener) {
			isHandleIconListener = true;
			icon.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void setText(String text) {
		tb.setText(text);
	}

	public String getText() {
		return tb.getText();
	}

}
