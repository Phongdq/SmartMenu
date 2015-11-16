/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.OrderDetail;

/**
 * @author bizluvsakura
 *
 */
public class OrderItem extends Composite implements HasText {

	private static OrderItemUiBinder uiBinder = GWT
			.create(OrderItemUiBinder.class);

	interface OrderItemUiBinder extends UiBinder<Widget, OrderItem> {
	}

	public int index;
	@UiField
	FlexPanel orderItem;

	@UiField
	public Button btnCancel;
	@UiField
	public Label lbTitle;
	@UiField
	public Label lbAmount;
	@UiField
	public Label lbPrice;
	@UiField
	public Label lbTotalMoney;

	public String title;
	public int amount;
	public int price;
	public int totalMoney;
	public long id;
	boolean isHandlerListener = false;

	OrderDetail orderDetail;

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public OrderItem() {
		initWidget(uiBinder.createAndBindUi(this));
		orderItem.setOrientation(Orientation.HORIZONTAL);
		lbTitle.setText(title);
		lbAmount.setText(amount + "");
		lbPrice.setText(price + "");
		lbTotalMoney.setText(totalMoney + "");

		config();
	}

	public OrderItem(String title, long id, int amount, int price,
			int totalMoney, int index) {
		initWidget(uiBinder.createAndBindUi(this));
		// Can access @UiField after calling createAndBindUi
		orderItem.setOrientation(Orientation.HORIZONTAL);
		this.id = id;
		this.title = title;
		this.amount = amount;
		this.price = price;
		this.totalMoney = totalMoney;
		this.index = index;

		lbTitle.setText(title);
		lbAmount.setText(amount + "");
		lbPrice.setText(price + "");
		lbTotalMoney.setText(totalMoney + "");

		lbTitle.getElement().getStyle().setTextAlign(TextAlign.LEFT);
		lbTitle.getElement().getStyle().setPaddingLeft(5, Unit.PX);
		config();
	}

	public OrderItem(String title, String amount, String price,
			String totalMoney) {
		initWidget(uiBinder.createAndBindUi(this));
		orderItem.setOrientation(Orientation.HORIZONTAL);
		// Can access @UiField after calling createAndBindUi
		btnCancel.setText("");
		btnCancel.setDisabled(true);
		lbTitle.setText(title);
		lbAmount.setText(amount + "");
		lbPrice.setText(price + "");
		lbTotalMoney.setText(totalMoney + "");

		config();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			lbTitle.getElement().getStyle().setFontSize(90, Unit.PCT);
			lbAmount.getElement().getStyle().setFontSize(90, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(90, Unit.PCT);
			lbTotalMoney.getElement().getStyle().setFontSize(90, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			lbTitle.getElement().getStyle().setFontSize(90, Unit.PCT);
			lbAmount.getElement().getStyle().setFontSize(90, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(90, Unit.PCT);
			lbTotalMoney.getElement().getStyle().setFontSize(90, Unit.PCT);
		} else {
			lbTitle.getElement().getStyle().setFontSize(55, Unit.PCT);
			lbAmount.getElement().getStyle().setFontSize(55, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(55, Unit.PCT);
			lbTotalMoney.getElement().getStyle().setFontSize(55, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(70, Unit.PCT);
		}
	}

	public void setAmount(int amount) {
		this.amount = amount;
		lbAmount.setText(amount + "");
	}

	public void hasListener(OrderItem item, final ClickListener listener) {
		if (!isHandlerListener) {
			isHandlerListener = true;
			btnCancel.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void setText(String text) {

	}

	/**
	 * Gets invoked when the default constructor is called and a string is
	 * provided in the ui.xml file.
	 */
	public String getText() {
		return "";
	}

}
