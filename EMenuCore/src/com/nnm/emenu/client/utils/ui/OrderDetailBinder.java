/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;

/**
 * @author bizluvsakura
 *
 */
public class OrderDetailBinder extends Composite implements HasText {

	private static OrderDetailBinderUiBinder uiBinder = GWT
			.create(OrderDetailBinderUiBinder.class);

	interface OrderDetailBinderUiBinder extends
			UiBinder<Widget, OrderDetailBinder> {
	}

	@UiField
	FlexPanel parent;
	@UiField
	FlexPanel header;
	@UiField
	Label lbHeader;
	@UiField
	FlexPanel content;
	@UiField
	Label lbTitle;
	@UiField
	Label lbPrice;
	@UiField
	FlexPanel panelAmount;
	@UiField
	Label lbAmount;
	@UiField
	TextBox tbAmount;
	@UiField
	FlexPanel panelButton;
	@UiField
	Button btnAdd;
	@UiField
	Button btnCancel;

	public int id;
	public int price;

	boolean isHandleButtonAdd;
	boolean isHandleButtonCancel;

	public OrderDetailBinder() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public OrderDetailBinder(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		header.getElement().getStyle().setBackgroundColor("#116388");
		lbHeader.setText("Đặt món - Bàn ");
		lbTitle.setText("Tên : ");
		lbPrice.setText("Giá tiền : ");
		lbAmount.setText("Số lượng ");
		panelAmount.setOrientation(Orientation.HORIZONTAL);
		panelButton.setOrientation(Orientation.HORIZONTAL);
		panelButton.getElement().getStyle().setBackgroundColor("#116388");
		btnAdd.setText("Đặt món");
		btnCancel.setText("Hủy");

		config();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			lbHeader.getElement().getStyle().setFontSize(150, Unit.PCT);
			btnAdd.getElement().getStyle().setFontSize(140, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(140, Unit.PCT);
			btnAdd.getElement().getStyle().setPaddingTop(12, Unit.PX);
			btnCancel.getElement().getStyle().setPaddingTop(12, Unit.PX);
			btnAdd.setHeight("40px");
			btnCancel.setHeight("40px");
			lbTitle.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbAmount.getElement().getStyle().setFontSize(130, Unit.PCT);
			tbAmount.getElement().getStyle().setFontSize(130, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			lbHeader.getElement().getStyle().setFontSize(150, Unit.PCT);
			btnAdd.getElement().getStyle().setFontSize(140, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(140, Unit.PCT);
			btnAdd.setHeight("40px");
			btnCancel.setHeight("40px");
			btnAdd.getElement().getStyle().setPaddingTop(17, Unit.PX);
			btnCancel.getElement().getStyle().setPaddingTop(17, Unit.PX);
			lbTitle.getElement().getStyle().setFontSize(145, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(145, Unit.PCT);
			lbAmount.getElement().getStyle().setFontSize(145, Unit.PCT);
			tbAmount.getElement().getStyle().setFontSize(145, Unit.PCT);
		} else {
			lbHeader.getElement().getStyle().setFontSize(130, Unit.PCT);
			btnAdd.getElement().getStyle().setFontSize(110, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(110, Unit.PCT);
			btnAdd.setHeight("25px");
			btnCancel.setHeight("25px");
			btnAdd.getElement().getStyle().setPaddingTop(10, Unit.PX);
			btnCancel.getElement().getStyle().setPaddingTop(10, Unit.PX);
			lbTitle.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbAmount.getElement().getStyle().setFontSize(120, Unit.PCT);
			tbAmount.getElement().getStyle().setFontSize(120, Unit.PCT);
		}
	}

	public void setOrderInfo(int table, int id, String title, int price) {
		this.id = id;
		this.price = price;
		lbHeader.setText("Đặt món - Bàn " + table);
		lbTitle.setText("Tên : " + title);
		lbPrice.setText("Giá tiền : " + price);
		tbAmount.setText("1");
	}

	public int getAmount() {
		try {
			return Integer.valueOf(tbAmount.getText());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void handleButtonAdd(final ClickListener listener) {
		if (!isHandleButtonAdd) {
			isHandleButtonAdd = true;
			btnAdd.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void handleButtonCancel(final ClickListener listener) {
		if (!isHandleButtonCancel) {
			isHandleButtonCancel = true;
			btnCancel.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					listener.onClick();
				}
			});
		}
	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
