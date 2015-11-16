package com.nnm.emenu.client;

import com.google.gwt.appengine.channel.client.Channel;
import com.google.gwt.appengine.channel.client.ChannelError;
import com.google.gwt.appengine.channel.client.ChannelFactory;
import com.google.gwt.appengine.channel.client.ChannelFactoryImpl;
import com.google.gwt.appengine.channel.client.Socket;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.user.client.Window;
import com.nnm.emenu.client.activities.AppActivityMapper;
import com.nnm.emenu.client.activities.ClientFactory;

public class ChannelConnection {

	private Channel channel;
	private Socket socket;
	ClientFactory clientFactory;
	AppActivityMapper appActivityMapper;

	public ChannelConnection(ClientFactory clientFactory,
			AppActivityMapper appActivityMapper) {
		this.clientFactory = clientFactory;
		this.appActivityMapper = appActivityMapper;
	}

	public void initChannel(String token) {
		ChannelFactory channelFactory = new ChannelFactoryImpl();
		channel = channelFactory.createChannel(token);
		socket = channel.open(new SocketListener() {

			@Override
			public void onOpen() {

			}

			@Override
			public void onMessage(String message) {
				
			}

			@Override
			public void onError(ChannelError error) {
				// TODO Auto-generated method stub
				Window.alert("error");
			}

			@Override
			public void onClose() {
				// TODO Auto-generated method stub
				Window.alert("close");
			}
		});
	}
}
