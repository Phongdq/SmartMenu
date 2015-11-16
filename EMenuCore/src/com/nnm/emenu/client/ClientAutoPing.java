/**
 * 
 */
package com.nnm.emenu.client;

import java.util.ArrayList;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.nnm.emenu.client.events.OrderDataReceiveEvent;
import com.nnm.emenu.shared.OrderData;

/**
 * @author bizluvsakura
 *
 */
public class ClientAutoPing {
	private static final int TIME_DELAY = 3000;

	Timer timer;
	boolean isRunning = false;

	public ClientAutoPing() {
		timer = new Timer() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				getDataVersion();
			}
		};
	}

	public void run() {
		timer.scheduleRepeating(TIME_DELAY);
	}

	public void cancel() {
		timer.cancel();
	}

	private void getDataVersion() {
		EMenuCore.greetingService.getDataVersion(new AsyncCallback<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Có lỗi xảy ra!!!");
			}

			@Override
			public void onSuccess(Long result) {
				if (result != EMenuCore.dataVersion
						&& (result > EMenuCore.dataVersion || (result < 0 && EMenuCore.dataVersion > 0))) {
					System.out.println(EMenuCore.user.getName() + " Has notify");
					EMenuCore.dataVersion = result;
					refresh();
				}
			}
		});
	}

	public void refresh() {
		EMenuCore.greetingService
				.refresh(new AsyncCallback<ArrayList<OrderData>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ArrayList<OrderData> result) {
						EMenuCore.clientFactory.getEventBus().fireEvent(
								new OrderDataReceiveEvent(result));
					}
				});
	}
}
