/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;

/**
 * @author bizluvsakura
 *
 */
public class TableObject {
	public static final int STATE_EMPTY = 0;
	public static final int STATE_NOT_EMPTY = 1;
	public static final int STATE_HAVE_NOTIFY = 2;

	private int number;
	private Image icon;
	private int state;
	private Label title;
	FlexPanel panel;

	Animation animation;
	int x;
	int xend;
	int k;

	boolean isHandlerListener;

	// state = 1 --- ban trong
	// state = 2 --- ban co ng
	// state = 3 --- ban co thong bao doi voi phuc vu/dau bep

	public TableObject() {
		panel = new FlexPanel();
		icon = new Image();
		icon.setStyleName("image");
		title = new Label("Bàn ");
		title.setStyleName("title");
		panel.add(icon);
		panel.add(title);
		configFontSize();
	}

	void configFontSize() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			title.getElement().getStyle().setFontSize(110, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			title.getElement().getStyle().setFontSize(140, Unit.PCT);
		} else {
			title.getElement().getStyle().setFontSize(80, Unit.PCT);
		}
	}

	public TableObject(int number) {
		// order = new OrderDataClient(number);
		this.number = number;
		this.state = 1;
		title = new Label("Bàn " + number);
		title.setStyleName("title");
		icon = new Image("image/table" + state + ".png");
		icon.setStyleName("image");
		// icon.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// // TODO Auto-generated method stub
		// setState(Random.nextInt(3) + 1);
		// xend += 20;
		// animation.run(4000);
		// }
		// });
		panel = new FlexPanel();
		panel.setStyleName("tablecomponent");
		panel.add(icon);
		panel.add(title);

		animation = new Animation() {

			@Override
			protected void onUpdate(double delta) {
				moveTo(xend, delta);
				System.out.println("update " + k++);
			}
		};

		configFontSize();
	}

	public void handlerListener(final ClickListener listener) {
		if (!isHandlerListener) {
			isHandlerListener = true;
			icon.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					listener.onClick();
				}
			});
		}
	}

	private void moveTo(int x, double delta) {
		if (this.x < x) {
			this.x++;
		}
		panel.getElement().getStyle().setLeft(this.x, Unit.PX);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
		title.setText("Bàn " + number);
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
		if (state == STATE_EMPTY) {
			icon.setUrl("image/table1.png");
		} else if (state == STATE_NOT_EMPTY) {
			icon.setUrl("image/table2.png");
		} else if (state == STATE_HAVE_NOTIFY) {
			icon.setUrl("image/table3.png");
		}
	}

	public FlexPanel asWidget() {
		return panel;
	}
}
