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
public class ItemMenu extends Composite implements HasText {

	private static ItemMenuUiBinder uiBinder = GWT
			.create(ItemMenuUiBinder.class);

	interface ItemMenuUiBinder extends UiBinder<Widget, ItemMenu> {
	}

	@UiField
	public FlexPanel parent;
	@UiField
	public Image icon;
	@UiField
	public Label title;
	@UiField
	public Button btn;

	boolean isHandleListener;

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public ItemMenu() {
		initWidget(uiBinder.createAndBindUi(this));
		configFontSize();
	}

	public ItemMenu(String urlIcon, String title) {
		initWidget(uiBinder.createAndBindUi(this));

		parent.setOrientation(Orientation.HORIZONTAL);
		int height = (int) (0.25f * 1.33f * Constants.WIDTH * 0.18f
				* 100 / 80);
		parent.setHeight(height + "px");

		icon.setUrl(urlIcon);
		// icon.setSize("15%", "80%");
		this.title.setText(title);
		configFontSize();

	}
	
	void configFontSize() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			title.getElement().getStyle().setFontSize(140, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			title.getElement().getStyle().setFontSize(170, Unit.PCT);
		} else {
			title.getElement().getStyle().setFontSize(110, Unit.PCT);
		}
	}

	public void handleListener(final ClickListener listener) {
		if (!isHandleListener) {
			isHandleListener = true;
			btn.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
			icon.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
			title.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
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
