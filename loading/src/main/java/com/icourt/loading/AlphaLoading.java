package com.icourt.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Junkang.Ding dingjunkang@icourt.cc
 * @version 1.0.0
 * @company Beijing iCourt
 * @project project_loading
 * @date 2017/10/28
 * @desc alpha loading object
 */
public class AlphaLoading {

    private static final String TAG = "AlphaLoading";

    public static final int STATE_INIT = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_RESULTING = 2;
    public static final int STATE_DEAD = 3;

    private final Dialog mDialog;
    private final ImageView mIconView;
    private final TextView mMsgView;
    private Handler mHandler;
    @DrawableRes
    private final int mLoadDrawable;
    @State
    private int mState;

    private final long mResultDuration;
    private final int mOkDrawableRes;
    private final int mFailDrawableRes;

    private AlphaLoading(Builder b) {
        mState = STATE_INIT;

        mOkDrawableRes = b.okIcon;
        mFailDrawableRes = b.failIcon;

        Dialog dialog = new Dialog(b.context, R.style.style_alpha_loading);
        dialog.setContentView(R.layout.alpha_loading);

        ImageView iconView = (ImageView) dialog.findViewById(R.id.alpha_iv_icon);
        TextView msgView = (TextView) dialog.findViewById(R.id.alpha_tv_message);
        this.mIconView = iconView;
        this.mMsgView = msgView;

        setMessage(b.message);
        iconView.setImageResource(mLoadDrawable = b.loadDrawable);

        dialog.setCancelable(b.cancelable);
        dialog.setCanceledOnTouchOutside(b.cancelable);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "onCancel() called with: dialog = [" + dialog + "]");
                destroy();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.d(TAG, "onDismiss() called with: dialog = [" + dialog + "]");
                destroy();
            }
        });

        Window window = dialog.getWindow();
        if (window != null) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.dimAmount = 0.3f;
            window.setAttributes(lp);
        }

        this.mResultDuration = b.resultDuration;
        this.mDialog = dialog;
    }

    /**
     * 更新消息
     *
     * @param message 消息
     */
    public void setMessage(String message) {
        Log.d(TAG, "setMessage() called with: message = [" + message + "]");
        if (TextUtils.isEmpty(message)) {
            mMsgView.setVisibility(View.GONE);
            mMsgView.setText(null);
        } else {
            mMsgView.setVisibility(View.VISIBLE);
            mMsgView.setText(message);
        }
    }

    /**
     * 显示loading
     */
    public void show() {
        if (mState == STATE_INIT) {

            Log.d(TAG, "show: state init");

            mState = STATE_LOADING;

            mDialog.show();
            mIconView.setImageResource(mLoadDrawable);
            startLoadingAnimation();

            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper());
            }

        } else {
            Log.d(TAG, "show: state  = " + mState);
        }
    }

    /**
     * 立马结束loading
     */
    public void dismissImmediately() {
        if (mState == STATE_LOADING) {

            Log.d(TAG, "dismissImmediately: state loading");

            mState = STATE_DEAD;

            mDialog.dismiss();
            stopLoadingAnimation();
        } else {
            Log.d(TAG, "dismissImmediately: state = " + mState);
        }
    }

    /**
     * 以正确状态结束，结束的时间为预先配置的值
     *
     * @param okMsg 结束消息
     */
    public void dismissOk(String okMsg) {
        Log.d(TAG, "dismissOk() called with: okMsg = [" + okMsg + "]");
        dismissWithResult(okMsg, mOkDrawableRes);
    }

    public void dismissFail(String failMsg) {
        Log.d(TAG, "dismissFail() called with: failMsg = [" + failMsg + "]");
        dismissWithResult(failMsg, mFailDrawableRes);
    }

    private void dismissWithResult(String msg, @DrawableRes final int resultIconRes) {
        if (mState == STATE_LOADING) {
            Log.d(TAG, "dismissWithResult state loading");
            mState = STATE_RESULTING;
            setMessage(msg);
            mIconView.animate().alpha(0).setDuration(200).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    stopLoadingAnimation();
                    mIconView.setImageResource(resultIconRes);
                    mIconView.animate().alpha(1).setListener(null).start();
                }
            }).start();
            if (mHandler != null) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                        mState = STATE_DEAD;
                        Log.d(TAG, "dead: ");
                    }
                }, 200 + mResultDuration);
            }
        } else {
            Log.d(TAG, "dismissWithResult state = " + mState);
        }
    }

    private void stopLoadingAnimation() {
        Drawable drawable = mIconView.getDrawable();
        if (drawable != null && drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();
            }
        }
    }

    private void startLoadingAnimation() {
        Drawable drawable = mIconView.getDrawable();
        if (drawable != null && drawable instanceof Animatable) {
            if (!((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).start();
            }
        }
    }

    private void destroy() {
        Log.d(TAG, "destroy() called");

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }

        if (mState == STATE_LOADING) {
            stopLoadingAnimation();
        } else if (mState == STATE_RESULTING) {
            mIconView.animate().cancel();
        }
        // mState = STATE_DEAD;
        mState = STATE_INIT;
    }

    @State
    public int getState() {
        return mState;
    }

    public static class Builder {

        private final Context context;
        private String message;
        private boolean cancelable;
        private long resultDuration;
        private int okIcon, failIcon;
        private int loadDrawable;

        public Builder(@NonNull Context context) {
            this.context = context;
            this.cancelable = false;
            this.resultDuration = 1000;
            this.okIcon = -1;
            this.failIcon = -1;
            this.loadDrawable = -1;
        }

        /**
         * @param message 消息
         */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder message(@StringRes int messageRes) {
            try {
                this.message = context.getResources().getString(messageRes);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return this;
        }

        /**
         * @param cancelable 是否可以手动取消, 默认不可以
         */
        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        /**
         * @param resultDuration 结果动画的时常, 负数和默认都是1000
         */
        public Builder resultDuration(long resultDuration) {
            this.resultDuration = resultDuration;
            return this;
        }

        public Builder okIcon(@DrawableRes int drawableRes) {
            okIcon = drawableRes;
            return this;
        }

        public Builder failIcon(@DrawableRes int drawableRes) {
            this.failIcon = drawableRes;
            return this;
        }

        public Builder loadDrawable(@DrawableRes int drawableRes) {
            this.loadDrawable = drawableRes;
            return this;
        }

        public AlphaLoading create() {
            if (resultDuration < 0) {
                resultDuration = 1000;
            }
            if (okIcon == -1) {
                okIcon = R.drawable.alpha_ic_ok;
            }
            if (failIcon == -1) {
                failIcon = R.drawable.alpha_ic_err;
            }
            if (loadDrawable == -1) {
                loadDrawable = R.drawable.alpha_loading;
            }

            return new AlphaLoading(this);
        }

        @Nullable
        private Drawable getDrawable(@DrawableRes int drawableRes) {
            try {
                return context.getResources().getDrawable(drawableRes);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @IntDef({STATE_INIT, STATE_LOADING, STATE_RESULTING, STATE_DEAD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

}
