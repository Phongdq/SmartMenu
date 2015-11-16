/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.GestureChangeEvent;
import com.google.gwt.event.dom.client.GestureChangeHandler;
import com.google.gwt.event.dom.client.GestureEndEvent;
import com.google.gwt.event.dom.client.GestureEndHandler;
import com.google.gwt.event.dom.client.GestureStartEvent;
import com.google.gwt.event.dom.client.GestureStartHandler;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.nnm.emenu.client.listener.ClickListener;

/**
 * @author bizluvsakura
 *
 */
public class CustomImageButton extends Composite implements HasText {

	private static CustomImageButtonUiBinder uiBinder = GWT
			.create(CustomImageButtonUiBinder.class);

	interface CustomImageButtonUiBinder extends
			UiBinder<Widget, CustomImageButton> {
	}

	@UiField
	public FlexPanel parent;
	@UiField
	public Image icon;
	@UiField
	public Button btn;

	boolean isHandleListener;

	public CustomImageButton(String urlIcon, String width, String height) {
		initWidget(uiBinder.createAndBindUi(this));

		parent.setSize(width, height);
		parent.setOrientation(Orientation.HORIZONTAL);
		icon.setUrl(urlIcon);
	}

	public CustomImageButton(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		// Can access @UiField after calling createAndBindUi
	}

	public void setText(String text) {
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
		}
	}
	
	public String getText() {
		return "";
	}

}
