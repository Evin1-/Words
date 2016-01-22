package mx.evin.apps.words.viewmodel.animations;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by evin on 1/22/16.
 */
public class Animator {
    public void fadeOut(final View view){
        AlphaAnimation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);//fade from 1 to 0 alpha
        fadeOutAnimation.setDuration(500);
//        fadeOutAnimation.setFillEnabled(true);
//        fadeOutAnimation.setFillAfter(true);
//        fadeOutAnimation.setFillAfter(true);//to keep it at 0 when animation ends
//        view.setVisibility(View.GONE);
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(fadeOutAnimation);
    }
}
