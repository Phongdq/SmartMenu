/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;

/**
 * @author bizluvsakura
 *
 */
public class ItemRevenue extends Composite implements HasText {

	private static ItemRevenueUiBinder uiBinder = GWT
			.create(ItemRevenueUiBinder.class);

	interface ItemRevenueUiBinder extends UiBinder<Widget, ItemRevenue> {
	}

	@UiField
	FlexPanel parent;
	@UiField
	Label title;
	@UiField
	FlexPanel panelValue;
	@UiField
	Label value;
	@UiField
	Label unit;

	public ItemRevenue() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ItemRevenue(String title, String value, String unit) {
		initWidget(uiBinder.createAndBindUi(this));

		parent.getElement().getStyle().setBorderWidth(1, Unit.PX);
		parent.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		parent.getElement().getStyle().setBorderColor("gray");

		panelValue.setOrientation(Orientation.HORIZONTAL);

		this.title.setText(title);
		this.value.setText(value);
		this.unit.setText(unit);
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
