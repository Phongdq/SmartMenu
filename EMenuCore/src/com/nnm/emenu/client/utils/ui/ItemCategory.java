/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.widget.button.ImageButton;
import com.googlecode.mgwt.ui.client.widget.image.ImageHolder;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.FoodCategory;

/**
 * @author bizluvsakura
 *
 */
public class ItemCategory extends Composite implements HasText {

	private static ItemCategoryUiBinder uiBinder = GWT
			.create(ItemCategoryUiBinder.class);

	interface ItemCategoryUiBinder extends UiBinder<Widget, ItemCategory> {
	}

	@UiField
	FlexPanel parent;
	@UiField
	FlexPanel panelImage;
	@UiField
	Image image;
	@UiField
	FlexPanel panelName;
	@UiField
	Label lbName;
	@UiField
	FlexPanel panelDes;
	@UiField
	Label lbDes;
	@UiField
	FlexPanel panelNote;

	public CustomImageButton btnEdit;
	public CustomImageButton btnDelete;

	public FoodCategory foodCategory;

	public ItemCategory() {
		initWidget(uiBinder.createAndBindUi(this));

		parent.setOrientation(Orientation.HORIZONTAL);
		parent.setHeight("33px");
		parent.getElement().getStyle().setMarginLeft(2, Unit.PCT);

		image.removeFromParent();
		Label lbImage = new Label("Ảnh");
		lbImage.getElement().getStyle().setColor("#116388");
		lbImage.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lbImage.getElement().getStyle().setFontSize(110, Unit.PCT);
		lbImage.getElement().getStyle().setPaddingTop(10, Unit.PX);
		lbImage.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		panelImage.add(lbImage);

		lbName.setText("Tên nhóm");
		lbName.getElement().getStyle().setColor("#116388");
		lbName.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lbName.getElement().getStyle().setFontSize(110, Unit.PCT);
		lbName.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		lbDes.setText("Mô tả");
		lbDes.getElement().getStyle().setColor("#116388");
		lbDes.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		lbDes.getElement().getStyle().setFontSize(110, Unit.PCT);
		lbDes.getElement().getStyle().setTextAlign(TextAlign.CENTER);

		panelNote.getElement().getStyle().setBorderColor("black");
		panelNote.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelNote.getElement().getStyle().setBorderWidth(1, Unit.PX);
	}

	public ItemCategory(FoodCategory foodCategory) {
		initWidget(uiBinder.createAndBindUi(this));

		this.foodCategory = foodCategory;

		parent.setOrientation(Orientation.HORIZONTAL);
		float width = Constants.WIDTH * 0.2f * 0.96f * 0.6f * 0.94f
				* 0.8f;
		parent.setHeight(width + "px");
		parent.getElement().getStyle().setMarginLeft(2, Unit.PCT);

		lbName.setText(foodCategory.getTitle());
		lbDes.setText(foodCategory.getTitle());
		image.setUrl(Constants.ROOT + "emenucore/download?filedown="
				+ foodCategory.getUrl());
		panelNote.setOrientation(Orientation.HORIZONTAL);
		panelNote.getElement().getStyle().setBorderColor("black");
		panelNote.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		panelNote.getElement().getStyle().setBorderWidth(1, Unit.PX);

		btnEdit = new CustomImageButton("image/iconEdit.png", width / 2 + "px",
				width / 2 + "px");
		btnDelete = new CustomImageButton("image/iconClose.png", width / 2
				+ "px", width / 2 + "px");
		btnEdit.getElement().getStyle().setMarginLeft(10, Unit.PCT);
		btnDelete.getElement().getStyle().setMarginLeft(10, Unit.PCT);

		panelNote.add(btnEdit);
		panelNote.add(btnDelete);
	}

	public ItemCategory(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));

		initWidget(uiBinder.createAndBindUi(this));

		parent.setOrientation(Orientation.HORIZONTAL);
		parent.getElement().getStyle().setMarginLeft(2, Unit.PCT);

	}

	public void setText(String text) {
	}

	public String getText() {
		return "";
	}

}
