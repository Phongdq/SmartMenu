package com.nnm.emenu.client.activities.revenue;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.nnm.emenu.client.EMenuCore;
import com.nnm.emenu.client.activities.home.HomePlace;
import com.nnm.emenu.client.listener.ClickListener;
import com.nnm.emenu.client.ultis.Constants;

public class ReportedRevenueActivity extends MGWTAbstractActivity {
	ReportedRevenueView reportedRevenueView;

	public ReportedRevenueActivity() {
		super();
		reportedRevenueView = EMenuCore.clientFactory.getReportedRevenueView();
		handlerListener();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(reportedRevenueView.asWidget());
		super.start(panel, eventBus);
	}

	void handlerListener() {
		reportedRevenueView.getButtonHome().handleListener(new ClickListener() {

			@Override
			public void onClick() {
				EMenuCore.clientFactory.getPlaceController().goTo(
						new HomePlace(Constants.HOME_PLACE));
			}
		});

		reportedRevenueView.handleButtonDate(new ClickListener() {

			@Override
			public void onClick() {
				reportedRevenueView
						.setTabSelected(ReportedRevenueViewImpl.TAB_TOTAL_REVENUE);
			}
		});

		reportedRevenueView.handleButtonProduct(new ClickListener() {

			@Override
			public void onClick() {
				reportedRevenueView
						.setTabSelected(ReportedRevenueViewImpl.TAB_PRODUCT);
			}
		});

		reportedRevenueView.handleButtonCategory(new ClickListener() {

			@Override
			public void onClick() {
				reportedRevenueView
						.setTabSelected(ReportedRevenueViewImpl.TAB_USER);
			}
		});

		reportedRevenueView.handleButtonFilterDay(new ClickListener() {

			@Override
			public void onClick() {
				reportedRevenueView
						.setFilterSelected(ReportedRevenueViewImpl.FILTER_DAY);
			}
		});

		reportedRevenueView.handleButtonFilterWeek(new ClickListener() {

			@Override
			public void onClick() {
				reportedRevenueView
						.setFilterSelected(ReportedRevenueViewImpl.FILTER_WEEK);
			}
		});

		reportedRevenueView.handleButtonFilterMonth(new ClickListener() {

			@Override
			public void onClick() {
				reportedRevenueView
						.setFilterSelected(ReportedRevenueViewImpl.FILTER_MONTH);
			}
		});

		reportedRevenueView.handleButtonFilterYear(new ClickListener() {

			@Override
			public void onClick() {
				reportedRevenueView
						.setFilterSelected(ReportedRevenueViewImpl.FILTER_YEAR);
			}
		});

		reportedRevenueView.handleButtonView(new ClickListener() {

			@Override
			public void onClick() {
				reportedRevenueView.requestRevenue();
			}
		});
	}
}
