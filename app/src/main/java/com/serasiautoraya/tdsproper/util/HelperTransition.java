package com.serasiautoraya.tdsproper.util;

import android.content.Context;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;

import com.serasiautoraya.tdsproper.R;

/**
 * Created by Randi Dwi Nandra on 13/03/2017.
 */

public class HelperTransition {

    public static void startEnterTransition(HelperKey.TransitionType transitionType, Window window, Context context) {
        switch (transitionType) {
            case Explode: {
                Explode enterTransition = new Explode();
                enterTransition.setDuration(300);
                window.setEnterTransition(enterTransition);
                window.setReenterTransition(enterTransition);
                window.setExitTransition(enterTransition);
                break;
            }

            case Slide: {
                Slide enterTransition = new Slide();
                enterTransition.setSlideEdge(Gravity.TOP);
                enterTransition.setDuration(600);
                enterTransition.setInterpolator(new AnticipateOvershootInterpolator());
                window.setEnterTransition(enterTransition);
                window.setReenterTransition(enterTransition);
                window.setExitTransition(enterTransition);
                break;
            }

            case Fade: {
                Transition enterTransition = TransitionInflater.from(context).inflateTransition(R.transition.explode);
                window.setEnterTransition(enterTransition);
                window.setReenterTransition(enterTransition);
                window.setExitTransition(enterTransition);
                break;

            }
        }

        window.setAllowEnterTransitionOverlap(false);
        window.setAllowReturnTransitionOverlap(false);
    }

}
