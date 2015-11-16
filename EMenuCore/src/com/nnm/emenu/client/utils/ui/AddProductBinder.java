/**
 * 
 */
package com.nnm.emenu.client.utils.ui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.button.Button;
import com.googlecode.mgwt.ui.client.widget.image.ImageHolder;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPanel;
import com.googlecode.mgwt.ui.client.widget.panel.flex.FlexPropertyHelper.Orientation;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;
import com.nnm.emenu.shared.FoodCategory;

/**
 * @author bizluvsakura
 *
 */
public class AddProductBinder extends Composite implements HasText {

	private static AddProductBinderUiBinder uiBinder = GWT
			.create(AddProductBinderUiBinder.class);

	interface AddProductBinderUiBinder extends
			UiBinder<Widget, AddProductBinder> {
	}

	@UiField
	FlexPanel parent;
	// header
	@UiField
	FlexPanel header;
	@UiField
	Label title;
	CustomImageButton btnClose;
	// content1
	@UiField
	FlexPanel content1;
	@UiField
	FlexPanel content11;
	@UiField
	public Image image;
	@UiField
	public Label lbImage;
	@UiField
	FlexPanel content12;
	@UiField
	FlexPanel panelCode;
	@UiField
	Label lbCode;
	@UiField
	public TextBox tbCode;
	@UiField
	FlexPanel panelName;
	@UiField
	Label lbName;
	@UiField
	public TextBox tbName;
	@UiField
	FlexPanel panelPrice;
	@UiField
	Label lbPrice;
	@UiField
	public TextBox tbPrice;
	@UiField
	FlexPanel panelCategory;
	@UiField
	Label lbCategory;
	@UiField
	public ListBox ltbCategory;
	@UiField
	FlexPanel panelUnit;
	@UiField
	Label lbUnit;
	@UiField
	public TextBox tbUnit;
	public FormPanel form;
	public FileUpload fileUpload;
	TextBox fileName;

	// content2
	@UiField
	FlexPanel content2;
	@UiField
	Button btnInfo;
	@UiField
	Button btnConvert;
	public int selected;
	public static final int TAB_INFO = 1;
	public static final int TAB_CONVERT = 2;

	// content3
	@UiField
	FlexPanel content3;
	@UiField
	FlexPanel contentInfo;
	@UiField
	FlexPanel contentInfo1;
	@UiField
	FlexPanel panelPriceInfo;
	@UiField
	Label lbPriceInfo;
	@UiField
	TextBox tbPriceInfo;
	@UiField
	FlexPanel panelProducerInfo;
	@UiField
	Label lbProducerInfo;
	@UiField
	TextBox tbProducerInfo;
	@UiField
	FlexPanel contentInfo2;
	@UiField
	Label lbDes;
	@UiField
	public TextArea taDes;
	@UiField
	FlexPanel contentConvert;

	// content4
	@UiField
	FlexPanel content4;
	@UiField
	Button btnSave;
	@UiField
	Button btnSaveAndNew;

	boolean isHandleButtonInfo;
	boolean isHandleButtonConvert;
	boolean isHandleButtonSave;
	boolean isHandleButtonSaveAndNew;
	boolean isHandleButtonClose;
	boolean isHandleFileUpload;
	boolean isHandleForm;
	boolean isHandleImage;

	public boolean saveandnew;

	int STATE_NEW_PRODUCT = 0;
	int STATE_UPDATE_PRODUCT = 1;
	int state = STATE_NEW_PRODUCT;

	public AddProductBinder() {
		initWidget(uiBinder.createAndBindUi(this));
		createElement();
	}

	public AddProductBinder(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	void createElement() {
		parent.getElement().getStyle().setBorderColor("black");
		parent.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		parent.getElement().getStyle().setBorderWidth(1, Unit.PX);

		header.setOrientation(Orientation.HORIZONTAL);
		header.getElement().getStyle().setBorderColor("black");
		header.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		header.getElement().getStyle().setBorderWidth(1, Unit.PX);
		title.setText("Thêm sản phẩm");

		float btnClose_height = Constants.HEIGHT * 0.8f * 0.1f * 0.9f;
		btnClose = new CustomImageButton("image/iconClose.png", btnClose_height
				+ "px", btnClose_height + "px");
		header.add(btnClose);

		content1.setOrientation(Orientation.HORIZONTAL);

		int content11_height = (int) (Constants.HEIGHT * 0.38f * 0.8f * 0.9f);
		content11.setPixelSize(content11_height, content11_height);
		content11.getElement().getStyle().setBorderColor("black");
		content11.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		content11.getElement().getStyle().setBorderWidth(1, Unit.PX);
		content11.getElement().getStyle().setMarginTop(1, Unit.PCT);
		content11.getElement().getStyle().setMarginLeft(2, Unit.PCT);
		image.setResource(ImageHolder.get().picture());
		lbImage.setText("Add Image");
		form = new FormPanel();
		form.setMethod(FormPanel.METHOD_POST);
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setAction("emenucore/upload");
		fileUpload = new FileUpload();
		fileUpload.setName("filename");
		fileName = new TextBox();
		fileName.setName("filedown");
		FlexPanel holder = new FlexPanel();
		holder.add(fileUpload);
		holder.add(fileName);
		form.add(holder);
		content12.setWidth(Constants.WIDTH * 0.7 - content11_height
				- 40 + "px");
		content12.getElement().getStyle().setMarginLeft(40, Unit.PX);
		content12.getElement().getStyle().setMarginTop(1, Unit.PCT);
		panelCode.setOrientation(Orientation.HORIZONTAL);
		panelName.setOrientation(Orientation.HORIZONTAL);
		panelPrice.setOrientation(Orientation.HORIZONTAL);
		panelCategory.setOrientation(Orientation.HORIZONTAL);
		panelUnit.setOrientation(Orientation.HORIZONTAL);

		lbCode.setText("Mã sản phẩm");
		lbName.setText("Tên sản phẩm");
		lbPrice.setText("Giá tiền");
		lbCategory.setText("Nhóm thực đơn");
		lbUnit.setText("Đơn vị tính");

		ltbCategory.setWidth(5
				+ (Constants.WIDTH * 0.7 - content11_height - 40) / 2
				+ "px");

		content2.setOrientation(Orientation.HORIZONTAL);
		btnInfo.setText("Thông tin");
		btnConvert.setText("Quy đổi");

		// content3
		content3.getElement().getStyle().setBorderColor("black");
		content3.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		content3.getElement().getStyle().setBorderWidth(1, Unit.PX);
		contentInfo.setOrientation(Orientation.HORIZONTAL);
		panelPriceInfo.setOrientation(Orientation.HORIZONTAL);
		panelPriceInfo.getElement().getStyle().setMarginTop(5, Unit.PX);
		panelProducerInfo.setOrientation(Orientation.HORIZONTAL);
		lbPriceInfo.setText("Giá nhập");
		lbProducerInfo.setText("Nhà sản xuất");

		contentInfo2.setOrientation(Orientation.HORIZONTAL);
		lbDes.setText("Mô tả");

		// content4
		content4.setOrientation(Orientation.HORIZONTAL);
		btnSave.setText("Lưu");
		btnSaveAndNew.setText("Lưu + Thêm mới");
		btnSave.setImportant(true);
		btnSaveAndNew.setImportant(true);

		form.setSize("0px", "0px");
		form.setVisible(false);
		content11.add(form);

		config();

		setSelected(TAB_INFO);
		
		form.addFormHandler(new FormHandler() {
			
			@Override
			public void onSubmitComplete(FormSubmitCompleteEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSubmit(FormSubmitEvent event) {
				// TODO Auto-generated method stub
				Window.alert("Form Submit");
			}
		});
	}

	void config() {
		if (Constants.WIDTH > 800 && Constants.WIDTH <= 1024) {
			title.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbCategory.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbCode.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbDes.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbImage.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbName.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbPriceInfo.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbProducerInfo.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbUnit.getElement().getStyle().setFontSize(110, Unit.PCT);
			btnInfo.getElement().getStyle().setFontSize(110, Unit.PCT);
			btnConvert.getElement().getStyle().setFontSize(110, Unit.PCT);
			btnSave.getElement().getStyle().setFontSize(110, Unit.PCT);
			btnSaveAndNew.getElement().getStyle().setFontSize(110, Unit.PCT);
			tbCode.getElement().getStyle().setFontSize(110, Unit.PCT);
			tbName.getElement().getStyle().setFontSize(110, Unit.PCT);
			tbPrice.getElement().getStyle().setFontSize(110, Unit.PCT);
			tbPriceInfo.getElement().getStyle().setFontSize(110, Unit.PCT);
			tbProducerInfo.getElement().getStyle().setFontSize(110, Unit.PCT);
			tbUnit.getElement().getStyle().setFontSize(110, Unit.PCT);
			ltbCategory.getElement().getStyle().setFontSize(110, Unit.PCT);
		} else if (Constants.WIDTH > 1024) {
			title.getElement().getStyle().setFontSize(140, Unit.PCT);
			lbCategory.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbCode.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbDes.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbImage.getElement().getStyle().setFontSize(130, Unit.PCT);
			lbName.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbPriceInfo.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbProducerInfo.getElement().getStyle().setFontSize(120, Unit.PCT);
			lbUnit.getElement().getStyle().setFontSize(120, Unit.PCT);
			btnInfo.getElement().getStyle().setFontSize(120, Unit.PCT);
			btnConvert.getElement().getStyle().setFontSize(120, Unit.PCT);
			btnSave.getElement().getStyle().setFontSize(120, Unit.PCT);
			btnSaveAndNew.getElement().getStyle().setFontSize(120, Unit.PCT);
			tbCode.getElement().getStyle().setFontSize(120, Unit.PCT);
			tbName.getElement().getStyle().setFontSize(120, Unit.PCT);
			tbPrice.getElement().getStyle().setFontSize(120, Unit.PCT);
			tbPriceInfo.getElement().getStyle().setFontSize(120, Unit.PCT);
			tbProducerInfo.getElement().getStyle().setFontSize(120, Unit.PCT);
			tbUnit.getElement().getStyle().setFontSize(120, Unit.PCT);
			ltbCategory.getElement().getStyle().setFontSize(120, Unit.PCT);
		} else {
			title.getElement().getStyle().setFontSize(110, Unit.PCT);
			lbCategory.getElement().getStyle().setFontSize(95, Unit.PCT);
			lbCode.getElement().getStyle().setFontSize(95, Unit.PCT);
			lbDes.getElement().getStyle().setFontSize(95, Unit.PCT);
			lbImage.getElement().getStyle().setFontSize(80, Unit.PCT);
			lbName.getElement().getStyle().setFontSize(95, Unit.PCT);
			lbPrice.getElement().getStyle().setFontSize(95, Unit.PCT);
			lbPriceInfo.getElement().getStyle().setFontSize(95, Unit.PCT);
			lbProducerInfo.getElement().getStyle().setFontSize(95, Unit.PCT);
			lbUnit.getElement().getStyle().setFontSize(95, Unit.PCT);
			btnInfo.getElement().getStyle().setFontSize(75, Unit.PCT);
			btnConvert.getElement().getStyle().setFontSize(75, Unit.PCT);
			btnSave.getElement().getStyle().setFontSize(75, Unit.PCT);
			btnSaveAndNew.getElement().getStyle().setFontSize(75, Unit.PCT);
			tbCode.getElement().getStyle().setFontSize(95, Unit.PCT);
			tbName.getElement().getStyle().setFontSize(95, Unit.PCT);
			tbPrice.getElement().getStyle().setFontSize(95, Unit.PCT);
			tbPriceInfo.getElement().getStyle().setFontSize(95, Unit.PCT);
			tbProducerInfo.getElement().getStyle().setFontSize(95, Unit.PCT);
			tbUnit.getElement().getStyle().setFontSize(95, Unit.PCT);
			ltbCategory.getElement().getStyle().setFontSize(95, Unit.PCT);
		}
	}

	public void createListBoxParent(List<FoodCategory> listFoodCategory) {
		ltbCategory.clear();
		ltbCategory.addItem("Nhóm thực đơn", "0");
		for (int i = 0; i < listFoodCategory.size(); i++) {
			ltbCategory.addItem(listFoodCategory.get(i).getTitle(),
					listFoodCategory.get(i).getId() + "");
		}
		Loading.getInstance().hide();
	}

	public boolean checkFull() {
		if (tbCode.getText().equals("") || tbName.getText().equals("")
				|| tbPrice.getText().equals("") || tbUnit.equals("")
				|| taDes.equals("")) {
			return false;
		}
		return true;
	}

	public void setSelected(int selected) {
		if (selected == TAB_INFO) {
			btnInfo.getElement().getStyle().setBackgroundColor("white");
			btnConvert.getElement().getStyle().setBackgroundColor("#AAAAAA");
			contentInfo.setVisible(true);
			contentConvert.setVisible(false);
		} else if (selected == TAB_CONVERT) {
			btnInfo.getElement().getStyle().setBackgroundColor("#AAAAAA");
			btnConvert.getElement().getStyle().setBackgroundColor("white");
			contentInfo.setVisible(false);
			contentConvert.setVisible(true);
		}
	}

	public void reset() {
		lbImage.setText("");
		tbName.setText("");
		ltbCategory.setSelectedIndex(0);
		tbCode.setText("");
		taDes.setText("");
		tbPrice.setText("");
		tbUnit.setText("");
		tbProducerInfo.setText("");
		tbPriceInfo.setText("");
	}

	public void handleButtonInfo(final ClickListener listener) {
		if (!isHandleButtonInfo) {
			isHandleButtonInfo = true;
			btnInfo.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void handleButtonConvert(final ClickListener listener) {
		if (!isHandleButtonConvert) {
			isHandleButtonConvert = true;
			btnConvert.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void handleButtonSave(final ClickListener listener) {
		if (!isHandleButtonSave) {
			isHandleButtonSave = true;
			btnSave.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					saveandnew = false;
					listener.onClick();
				}
			});
		}
	}

	public void handleButtonSaveAndNew(final ClickListener listener) {
		if (!isHandleButtonSaveAndNew) {
			isHandleButtonSaveAndNew = true;
			btnSaveAndNew.addTapHandler(new TapHandler() {

				@Override
				public void onTap(TapEvent event) {
					saveandnew = true;
					listener.onClick();
				}
			});
		}
	}

	public void handleButtonClose(final ClickListener listener) {
		btnClose.handleListener(listener);
	}

	public void handleFileUpload(final ClickListener listener) {
		if (!isHandleFileUpload) {
			isHandleFileUpload = true;
			fileUpload.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					listener.onClick();
				}
			});
		}
	}

	public void handleForm(final ClickListener listener) {
		if (!isHandleForm) {
			isHandleForm = true;
			form.addSubmitCompleteHandler(new SubmitCompleteHandler() {

				@Override
				public void onSubmitComplete(SubmitCompleteEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void handleImage(final ClickListener listener) {
		if (!isHandleImage) {
			isHandleImage = true;
			image.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					listener.onClick();
				}
			});
		}
	}

	public void setStateNew() {
		this.state = STATE_NEW_PRODUCT;
		reset();
		image.setResource(ImageHolder.get().picture());
		image.setSize("50%", "50%");
		lbImage.setText("Add Image");
		tbCode.setEnabled(true);
		lbImage.setVisible(true);
		btnSaveAndNew.setVisible(true);
	}

	public void setStateUpdate() {
		this.state = STATE_UPDATE_PRODUCT;
		image.setSize("80%", "80%");
		lbImage.setText("");
		lbImage.setVisible(false);
		btnSaveAndNew.setVisible(false);
	}

	public boolean isStateNew() {
		if (state == STATE_NEW_PRODUCT)
			return true;
		return false;
	}

	public boolean isStateUpdate() {
		if (state == STATE_UPDATE_PRODUCT)
			return true;
		return false;
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
