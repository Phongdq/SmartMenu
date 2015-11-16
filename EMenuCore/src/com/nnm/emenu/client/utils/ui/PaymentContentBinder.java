/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import com.nnm.emenu.client.ultis.StringUtils;

/**
 * @author bizluvsakura
 *
 */
public class PaymentContentBinder extends Composite implements HasText {

	private static PaymentContentBinderUiBinder uiBinder = GWT
			.create(PaymentContentBinderUiBinder.class);

	interface PaymentContentBinderUiBinder extends
			UiBinder<Widget, PaymentContentBinder> {
	}

	@UiField
	FlexPanel parent;

	@UiField
	FlexPanel header;
	@UiField
	Label titleHeader;
	@UiField
	Button btnCancel;

	@UiField
	FlexPanel row1;
	@UiField
	Label lbtitle1;
	@UiField
	TextBox tb1;
	@UiField
	Label lbprice1;

	@UiField
	FlexPanel row2;
	@UiField
	Label lbtitle2;
	@UiField
	TextBox tb2;
	@UiField
	Label lbprice2;

	@UiField
	FlexPanel row3;
	@UiField
	Label lbtitle3;
	@UiField
	TextBox tb3;
	@UiField
	Label lbprice3;

	@UiField
	FlexPanel row4;
	@UiField
	Label lbtitle4;
	@UiField
	TextBox tb4;
	@UiField
	Label lbprice4;

	@UiField
	FlexPanel row5;
	@UiField
	Label lbtitle5;
	@UiField
	TextBox tb5;
	@UiField
	Label lbprice5;

	@UiField
	FlexPanel row6;
	@UiField
	Label lbtitle6;
	@UiField
	TextBox tb6;
	@UiField
	Label lbprice6;

	@UiField
	FlexPanel footer;
	@UiField
	Button btnPayment;
	@UiField
	Button btnPrint;

	boolean isHandleButtonCancel;
	boolean isHandleButtonPayment;
	boolean isHandleButtonPrint;

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public PaymentContentBinder() {
		initWidget(uiBinder.createAndBindUi(this));

		creatElement();
	}

	public PaymentContentBinder(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		creatElement();
	}

	void creatElement() {
		header.setOrientation(Orientation.HORIZONTAL);
		header.getElement().getStyle().setBorderColor("#FFFFFF");
		header.getElement().getStyle().setBorderWidth(2, Unit.PX);
		header.getElement().getStyle().setMarginLeft(-1, Unit.PCT);
		header.getElement().getStyle().setMarginTop(-1, Unit.PCT);
		header.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		titleHeader.setText("Thanh toán hóa đơn");
		btnCancel.setText("X");

		row1.setOrientation(Orientation.HORIZONTAL);
		row1.getElement().getStyle().setMarginTop(3, Unit.PCT);
		lbtitle1.setText("Số tiền thanh toán");
		lbprice1.setText("VNĐ");

		row2.setOrientation(Orientation.HORIZONTAL);
		lbtitle2.setText("Chiết khấu");
		lbprice2.setText("%");

		row3.setOrientation(Orientation.HORIZONTAL);
		lbtitle3.setText("VAT");
		lbprice3.setText(" % ");

		row4.setOrientation(Orientation.HORIZONTAL);
		lbtitle4.setText("Số tiền phải thanh toán");
		lbprice4.setText("VNĐ");

		row5.setOrientation(Orientation.HORIZONTAL);
		lbtitle5.setText("Số tiền khách hàng trả");
		lbprice5.setText("VNĐ");

		row6.setOrientation(Orientation.HORIZONTAL);
		lbtitle6.setText("Trả lại khách hàng");
		lbprice6.setText("VNĐ");

		footer.setOrientation(Orientation.HORIZONTAL);
		footer.getElement().getStyle().setMarginTop(8, Unit.PCT);
		btnPayment.setText("Thanh toán");
		btnPrint.setText("In hóa đơn");

		tb1.getElement().setAttribute("type", "numeric");
		handleAutoCalculate();

		config();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			titleHeader.getElement().getStyle().setFontSize(160, Unit.PCT);
			lbprice1.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbprice2.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbprice3.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbprice4.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbprice5.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbprice6.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbtitle1.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbtitle2.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbtitle3.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbtitle4.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbtitle5.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbtitle6.getElement().getStyle().setFontSize(120, Unit.PCT);
			tb1.getElement().getStyle().setFontSize(120, Unit.PCT);
			tb2.getElement().getStyle().setFontSize(120, Unit.PCT);
			tb3.getElement().getStyle().setFontSize(120, Unit.PCT);
			tb4.getElement().getStyle().setFontSize(120, Unit.PCT);
			tb5.getElement().getStyle().setFontSize(120, Unit.PCT);
			tb6.getElement().getStyle().setFontSize(120, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(200, Unit.PCT);
			btnPayment.getElement().getStyle().setFontSize(125, Unit.PCT);
			btnPrint.getElement().getStyle().setFontSize(125, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			titleHeader.getElement().getStyle().setFontSize(170, Unit.PCT);
			lbprice1.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbprice2.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbprice3.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbprice4.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbprice5.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbprice6.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbtitle1.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbtitle2.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbtitle3.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbtitle4.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbtitle5.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbtitle6.getElement().getStyle().setFontSize(130, Unit.PCT);
			tb1.getElement().getStyle().setFontSize(130, Unit.PCT);
			tb2.getElement().getStyle().setFontSize(130, Unit.PCT);
			tb3.getElement().getStyle().setFontSize(130, Unit.PCT);
			tb4.getElement().getStyle().setFontSize(130, Unit.PCT);
			tb5.getElement().getStyle().setFontSize(130, Unit.PCT);
			tb6.getElement().getStyle().setFontSize(130, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(200, Unit.PCT);
			btnPayment.getElement().getStyle().setFontSize(130, Unit.PCT);
			btnPrint.getElement().getStyle().setFontSize(130, Unit.PCT);
		} else {
			titleHeader.getElement().getStyle().setFontSize(125, Unit.PCT);
			lbprice1.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbprice2.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbprice3.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbprice4.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbprice5.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbprice6.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbtitle1.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbtitle2.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbtitle3.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbtitle4.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbtitle5.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbtitle6.getElement().getStyle().setFontSize(80, Unit.PCT);
			tb1.getElement().getStyle().setFontSize(80, Unit.PCT);
			tb2.getElement().getStyle().setFontSize(80, Unit.PCT);
			tb3.getElement().getStyle().setFontSize(80, Unit.PCT);
			tb4.getElement().getStyle().setFontSize(80, Unit.PCT);
			tb5.getElement().getStyle().setFontSize(80, Unit.PCT);
			tb6.getElement().getStyle().setFontSize(80, Unit.PCT);
			btnCancel.getElement().getStyle().setFontSize(130, Unit.PCT);
			btnPayment.getElement().getStyle().setFontSize(85, Unit.PCT);
			btnPrint.getElement().getStyle().setFontSize(85, Unit.PCT);
		}
	}

	void handleAutoCalculate() {
		// tb1.addKeyDownHandler(new KeyDownHandler() {
		//
		// @Override
		// public void onKeyDown(KeyDownEvent event) {
		// if (event.getNativeKeyCode() > 32
		// && (event.getNativeKeyCode() < 48 || event
		// .getNativeKeyCode() > 57)) {
		// Window.alert(event.getNativeKeyCode() + " : ");
		// return;
		// }
		// }
		// });

		// tb1.addKeyPressHandler(new KeyPressHandler() {
		//
		// @Override
		// public void onKeyPress(KeyPressEvent event) {
		// if (event.getCharCode() > 32
		// && (event.getCharCode() < 48 || event.getCharCode() > 57)) {
		// Window.alert(event.getCharCode() + " : ");
		// event.stopPropagation();
		// return;
		// }
		// }
		// });
		tb1.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (tb3.getText().equals("")) {
					tb4.setText((getMoneyTotal() - getMoneyDiscount()) + "");
				} else if (tb2.getText().equals("")) {
					tb4.setText((getMoneyTotal() + getMoneyTotal() * getVAT()
							/ 100)
							+ "");
				} else {
					tb4.setText(((getMoneyTotal() - getMoneyDiscount()) + (getMoneyTotal() - getMoneyDiscount())
							* getVAT() / 100)
							+ "");
				}
			}
		});

		tb2.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (tb1.getText().equals("")) {
					return;
				} else {
					if (tb3.getText().equals("")) {
						tb4.setText((getMoneyTotal() - getMoneyDiscount()) + "");
					} else {
						double money = (getMoneyTotal() - getMoneyDiscount())
								+ (getMoneyTotal() - getMoneyDiscount())
								* getVAT() / 100;
						tb4.setText(money + "");
					}
				}
			}
		});

		tb3.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (tb1.getText().equals("")) {
					return;
				} else {
					if (tb2.getText().equals("")) {
						tb4.setText((getMoneyTotal() + getMoneyTotal()
								* getVAT() / 100)
								+ "");
					} else {
						tb4.setText(((getMoneyTotal() - getMoneyDiscount()) + (getMoneyTotal() - getMoneyDiscount())
								* getVAT() / 100)
								+ "");
					}
				}
			}
		});
		tb4.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (tb5.getText().equals("") || tb4.getText().equals("")) {
					return;
				}
				tb6.setText((Double.valueOf(tb5.getText()) - Double.valueOf(tb4
						.getText())) + "");
			}
		});

		tb5.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (tb5.getText().equals("") || tb4.getText().equals("")) {
					return;
				}
				tb6.setText((Double.valueOf(tb5.getText()) - Double.valueOf(tb4
						.getText())) + "");
			}
		});
	}

	public void handleButtonCloseListener(final ClickListener listener) {
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

	public void handleButtonPaymentListener(final ClickListener listener) {
		if (!isHandleButtonPayment) {
			isHandleButtonPayment = true;
			btnPayment.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void handleButtonPrintListener(final ClickListener listener) {
		if (!isHandleButtonPrint) {
			isHandleButtonPrint = true;
			btnPrint.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public boolean checkFullInfo() {
		if (tb1.getText().equals("") || tb2.getText().equals("")
				|| tb3.getText().equals("") || tb4.getText().equals("")
				|| tb5.getText().equals("") || tb6.getText().equals("")) {
			return false;
		}
		return true;
	}

	public void clearTextBox() {
		tb1.setText("");
		tb2.setText("");
		tb3.setText("");
		tb4.setText("");
		tb5.setText("");
		tb6.setText("");
	}

	public Double getMoneyTotal() {
		try {
			return Double.valueOf(tb1.getText());
		} catch (Exception e) {
			return 0d;
		}
	}

	public Double getMoneyDiscount() {
		try {
			return Double.valueOf(tb2.getText()) * getMoneyTotal() / 100;
		} catch (Exception e) {
			return 0d;
		}

	}

	public Float getVAT() {
		try {
			return Float.valueOf(tb3.getText());
		} catch (Exception e) {
			return 0f;
		}
	}

	public Double getMoneyPayment() {
		try {
			return Double.valueOf(tb4.getText());
		} catch (Exception e) {
			return 0d;
		}
	}

	public Double getMoneyCustomer() {
		try {
			return Double.valueOf(tb5.getText());
		} catch (Exception e) {
			return 0d;
		}
	}

	public Double getMoneyReturn() {
		try {
			return Double.valueOf(tb6.getText());
		} catch (Exception e) {
			return 0d;
		}
	}

	public void setPaymentMoney(long money) {
		this.tb1.setText(money + "");
	}

	public void setVAT(float vat) {
		tb3.setText(vat + "");
	}

	public void calculatorMoneyPayment() {
		tb4.setText((getMoneyTotal() + getMoneyTotal() * getVAT() / 100) + "");
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
