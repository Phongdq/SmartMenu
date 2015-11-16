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
public class ChangeTableBinder extends Composite implements HasText {

	private static ChangeTableBinderUiBinder uiBinder = GWT
			.create(ChangeTableBinderUiBinder.class);

	interface ChangeTableBinderUiBinder extends
			UiBinder<Widget, ChangeTableBinder> {
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
	FlexPanel panelContent;
	@UiField
	Label lb;
	@UiField
	public TextBox tb;
	@UiField
	FlexPanel panelButton;
	@UiField
	Button btnOk;
	@UiField
	Button btnCancel;

	boolean isHandleButtonOk;
	boolean isHandleButtonCancel;
	public int table;

	public ChangeTableBinder(int table) {
		initWidget(uiBinder.createAndBindUi(this));
		this.table = table;
		initElement();
	}

	public ChangeTableBinder(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		// Can access @UiField after calling createAndBindUi
	}

	void initElement() {
		header.getElement().getStyle().setBackgroundColor("#116388");
		lbHeader.setText("Chuyển bàn");
		panelContent.setOrientation(Orientation.HORIZONTAL);

		lb.setText("Chuyển bàn " + table + " sang bàn ");

		panelButton.setOrientation(Orientation.HORIZONTAL);
		panelButton.getElement().getStyle().setBackgroundColor("#116388");
		btnOk.setText("Xác nhận");
		btnCancel.setText("Từ chối");
		config();
	}

	public void setTable(int table) {
		this.table = table;

		lb.setText("Chuyển bàn " + table + " sang bàn ");
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			lbHeader.getElement().getStyle().setFontSize(150, Unit.PCT);
			btnOk.getElement().getStyle().setFontSize(140, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(140, Unit.PCT);
			btnOk.getElement().getStyle().setPaddingTop(12, Unit.PX);
			btnCancel.getElement().getStyle().setPaddingTop(12, Unit.PX);
			btnOk.setHeight("40px");
			btnCancel.setHeight("40px");
			lb.getElement().getStyle().setFontSize(130, Unit.PCT);
			tb.getElement().getStyle().setFontSize(130, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			lbHeader.getElement().getStyle().setFontSize(150, Unit.PCT);
			btnOk.getElement().getStyle().setFontSize(140, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(140, Unit.PCT);
			btnOk.setHeight("40px");
			btnCancel.setHeight("40px");
			btnOk.getElement().getStyle().setPaddingTop(17, Unit.PX);
			btnCancel.getElement().getStyle().setPaddingTop(17, Unit.PX);
			lb.getElement().getStyle().setFontSize(150, Unit.PCT);
			tb.getElement().getStyle().setFontSize(150, Unit.PCT);
		} else {
			lbHeader.getElement().getStyle().setFontSize(130, Unit.PCT);
			btnOk.getElement().getStyle().setFontSize(110, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(110, Unit.PCT);
			btnOk.setHeight("25px");
			btnCancel.setHeight("25px");
			btnOk.getElement().getStyle().setPaddingTop(10, Unit.PX);
			btnCancel.getElement().getStyle().setPaddingTop(10, Unit.PX);
			lb.getElement().getStyle().setFontSize(120, Unit.PCT);
			tb.getElement().getStyle().setFontSize(120, Unit.PCT);
		}
	}

	public void handleButtonOk(final ClickListener listener) {
		if (!isHandleButtonOk) {
			isHandleButtonOk = true;
			btnOk.addTapHandler(new TapHandler() {

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
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public int getNewTable() {
		try {
			return Integer.valueOf(tb.getText());
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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
