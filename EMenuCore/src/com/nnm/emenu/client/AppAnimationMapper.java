package com.nnm.emenu.client;

import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.AnimationMapper;
import com.googlecode.mgwt.ui.client.widget.animation.Animation;
import com.googlecode.mgwt.ui.client.widget.animation.Animations;

public class AppAnimationMapper implements AnimationMapper {

	Animation current = Animations.SLIDE;

	@Override
	public Animation getAnimation(Place oldPlace, Place newPlace) {
		return current;
	}

	public void setAnimation(Animation animation) {
		this.current = animation;
	}
}
