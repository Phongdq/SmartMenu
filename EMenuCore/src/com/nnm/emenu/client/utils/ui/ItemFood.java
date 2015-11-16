/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.nnm.emenu.client.EMenuCore;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.FoodInfo;
import com.nnm.emenu.shared.UserInfo;

/**
 * @author bizluvsakura
 *
 */
public class ItemFood extends Composite implements HasText {

	private static ItemFoodUiBinder uiBinder = GWT
			.create(ItemFoodUiBinder.class);

	interface ItemFoodUiBinder extends UiBinder<Widget, ItemFood> {
	}

	@UiField
	FlexPanel parent;
	@UiField
	public Image imgFood;
	@UiField
	public Label titleFood;
	@UiField
	public Label lbPrice;
	@UiField
	public Button btnAdd;

	public int id;
	public int price;
	boolean isHandlerListener;
	public FoodInfo foodInfo;

	public ItemFood() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ItemFood(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		// Can access @UiField after calling createAndBindUi
	}

	public ItemFood(FoodInfo foodInfo) {
		initWidget(uiBinder.createAndBindUi(this));
		this.foodInfo = foodInfo;
		this.id = foodInfo.getId();
		this.price = foodInfo.getPrice();
		// float perH = 81 * Window.getClientHeight() / 100 / 2 * 50 / 100;
		float perH = Constants.WIDTH * 0.667f * 0.7f * 9 / 42;
		this.imgFood.setHeight(perH + "px");
		System.out.println("==============url : " + Constants.ROOT
				+ "emenucore/download?filedown=" + foodInfo.getImage()
				+ "=================");

		this.imgFood.setUrl(Constants.ROOT + "emenucore/download?filedown="
				+ foodInfo.getImage());
		this.titleFood.setText(foodInfo.getTitle());
		this.lbPrice.setText(price + "");
		if (EMenuCore.user.getRoleId() == UserInfo.ROLE_CHEF) {
			this.btnAdd.setVisible(false);
		}
		config();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			titleFood.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(125, Unit.PCT);
			btnAdd.getElement().getStyle().setFontSize(100, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			titleFood.getElement().getStyle().setFontSize(125, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(135, Unit.PCT);
			btnAdd.setHeight("9%");
			btnAdd.getElement().getStyle().setFontSize(110, Unit.PCT);
			btnAdd.getElement().getStyle().setPaddingTop(2, Unit.PCT);
		} else {
			titleFood.getElement().getStyle().setFontSize(85, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(100, Unit.PCT);
			btnAdd.getElement().getStyle().setFontSize(80, Unit.PCT);
		}
	}

	public void hasListener(ItemFood itemFood, final ClickListener listener) {
		if (!isHandlerListener) {
			isHandlerListener = true;
			btnAdd.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					if (EMenuCore.user.getRoleId() != UserInfo.ROLE_CHEF) {
						listener.onClick();
					}
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
