package cn.thxy.imagerecognition.ratio;

import android.animation.TypeEvaluator;


public class HomingRatioFrameEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        RatioFrame startRatioFrame = (RatioFrame) startValue;
        RatioFrame endRatioFrame = (RatioFrame) endValue;
        int left = (int)(startRatioFrame.mLeft + (endRatioFrame.mLeft - startRatioFrame.mLeft) * fraction);
        int top = (int)(startRatioFrame.mTop + (endRatioFrame.mTop - startRatioFrame.mTop) * fraction);
        int right = (int)(startRatioFrame.mRight + (endRatioFrame.mRight - startRatioFrame.mRight) * fraction);
        int bottom = (int)(startRatioFrame.mBottom + (endRatioFrame.mBottom - startRatioFrame.mBottom) * fraction);
        return new RatioFrame(left,top,right,bottom);
    }
}
