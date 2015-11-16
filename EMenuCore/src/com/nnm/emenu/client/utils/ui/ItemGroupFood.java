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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.FoodCategory;

/**
 * @author bizluvsakura
 *
 */
public class ItemGroupFood extends Composite implements HasText {

	private static ItemGroupFoodUiBinder uiBinder = GWT
			.create(ItemGroupFoodUiBinder.class);

	interface ItemGroupFoodUiBinder extends UiBinder<Widget, ItemGroupFood> {
	}

	@UiField
	public Image imgFood;
	@UiField
	public Label titleFood;
	public FoodCategory foodCategory;
	boolean isHandlerListener;

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public ItemGroupFood() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ItemGroupFood(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ItemGroupFood(FoodCategory foodCategory) {
		initWidget(uiBinder.createAndBindUi(this));
		this.foodCategory = foodCategory;
		// float perH = 81 * Window.getClientHeight() / 100 / 2 * 50 / 100;
		float perH = Constants.WIDTH * 0.667f * 0.7f * 9 / 42;
		this.imgFood.setHeight(perH + "px");
		this.imgFood.setUrl(Constants.ROOT + "emenucore/download?filedown="
				+ foodCategory.getUrl());
		this.titleFood.setText(foodCategory.getTitle());
		config();
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			titleFood.getElement().getStyle().setFontSize(125, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			titleFood.getElement().getStyle().setFontSize(140, Unit.PCT);
		} else {
			titleFood.getElement().getStyle().setFontSize(100, Unit.PCT);
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

	public void hasListener(ItemGroupFood item, final ClickListener listener) {
		if (!isHandlerListener) {
			isHandlerListener = true;
			imgFood.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}
}
