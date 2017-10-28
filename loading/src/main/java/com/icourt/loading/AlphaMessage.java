package com.icourt.loading;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by chrisding on 2017/1/3.
 * Function: CommonsProj
 */

public class AlphaMessage {

    private final Dialog dialog;
    private final TextView msgView;
    private final ImageView iconView;
    private final boolean loading;
    private final int durationToDismiss;
    private boolean showing = false;
    private Listener listener;
    private Handler handler;

    private AlphaMessage(Builder b) {
        this.loading = b.loading;

        Dialog dialog = new Dialog(b.context, R.style.style_alpha_loading);
        dialog.setContentView(R.layout.alpha_loading);

        ImageView iconView = (ImageView) dialog.findViewById(R.id.alpha_iv_icon);
        TextView msgView = (TextView) dialog.findViewById(R.id.alpha_tv_message);
        this.iconView = iconView;
        this.msgView = msgView;
        if (b.icon != null) {
            iconView.setImageDrawable(b.icon);
        } else if (b.loading) {
            iconView.setImageResource(R.drawable.alpha_loading);
        } else {
            iconView.setVisibility(View.GONE);
        }
        msgView.setText(b.message);

        dialog.setCancelable(b.cancelable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                    handler = null;
                }
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                    handler = null;
                }
            }
        });

        Window window = dialog.getWindow();
        if (window != null) {
            if (b.translucent) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.dimAmount = 0.3f;
                window.setAttributes(lp);
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        }

        this.durationToDismiss = b.durationToDismiss;
        this.dialog = dialog;
    }

    public void show() {
        if (showing) {
            // throw new IllegalStateException("already showing.");
            return;
        }
        showing = true;
        dialog.show();

        if (loading) {
            AnimationDrawable drawable = (AnimationDrawable) iconView.getDrawable();
            if (!drawable.isRunning()) {
                drawable.start();
            }
        } else {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            // auto dismiss
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showing = false;
                    if (listener != null) {
                        listener.onDismiss();
                    }
                }
            }, durationToDismiss);
        }
    }

    public void setMessage(String message) {
        msgView.setText(message);
    }

    public AlphaMessage dismissWith(Listener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Dismiss tips if loading.
     * Only work for loading tips.
     */
    public void dismiss() {
        if (loading && showing) {
            dialog.dismiss();
            AnimationDrawable drawable = (AnimationDrawable) iconView.getDrawable();
            if (drawable.isRunning()) {
                drawable.stop();
            }
            if (listener != null) {
                listener.onDismiss();
            }
        }
    }

    public void dismissBy(Drawable byIcon, String byMsg, long byDuration) {
        if (loading && showing) {
            dialog.dismiss();
            AnimationDrawable drawable = (AnimationDrawable) iconView.getDrawable();
            if (drawable.isRunning()) {
                drawable.stop();
            }
            if (listener != null) {
                listener.onDismiss();
            }
        }
    }

    public void dismissBy(Drawable byIcon, String byMsg) {

    }

    public void dismissBy(@DrawableRes int byIconRes, @StringRes int byMsgRes, long byDuration) {

    }

    public void dismissBy(@DrawableRes int byIconRes, @StringRes int byMsgRes) {

    }

    public void dismissByOk() {

    }

    public void dismissByFail() {

    }

    public Context getContext() {
        return dialog.getContext();
    }

    public interface Listener {
        void onDismiss();
    }

    public static AlphaMessage make(Context context, Drawable icon, String msg, boolean translucent, int duration) {
        return new Builder(context).icon(icon).message(msg).translucent(translucent).autoDismiss(duration).build();
    }

    public static AlphaMessage make(Context context, @DrawableRes int icon, @StringRes int msg, boolean translucent, int duration) {
        Resources res = context.getResources();
        return make(context, res.getDrawable(icon), res.getString(msg), translucent, duration);
    }

    public static AlphaMessage make(Context context, @DrawableRes int icon, String msg, int duration) {
        return new Builder(context).icon(icon).message(msg).translucent(true).autoDismiss(duration).build();
    }

    public static AlphaMessage make(Context context, @DrawableRes int icon, String msg) {
        return new Builder(context).icon(icon).message(msg).translucent(true).autoDismiss(2000).build();
    }

    public static AlphaMessage make(Context context, @DrawableRes int icon, @StringRes int msg) {
        return make(context, icon, context.getString(msg));
    }

    public static AlphaMessage makeOk(Context context, String msg, int duration) {
        return make(context, R.drawable.alpha_ic_ok, msg, duration);
    }

    public static AlphaMessage makeOk(Context context, @StringRes int msg, int duration) {
        return makeOk(context, context.getString(msg), duration);
    }

    public static AlphaMessage makeOk(Context context, String msg) {
        return makeOk(context, msg, 2000);
    }

    public static AlphaMessage makeOk(Context context, @StringRes int msg) {
        return makeOk(context, context.getString(msg));
    }

    public static AlphaMessage makeErr(Context context, String msg, int duration) {
        return make(context, R.drawable.alpha_ic_err, msg, duration);
    }

    public static AlphaMessage makeErr(Context context, @StringRes int msg, int duration) {
        return makeErr(context, context.getString(msg), duration);
    }

    public static AlphaMessage makeErr(Context context, String msg) {
        return makeErr(context, msg, 2000);
    }

    public static AlphaMessage makeErr(Context context, @StringRes int msg) {
        return makeErr(context, context.getString(msg));
    }

    public static AlphaMessage makeLoading(Context context, String msg) {
        return new Builder(context).icon(null).message(msg).translucent(true).loading(true).build();
    }

    public static AlphaMessage makeLoading(Context context, @StringRes int msg) {
        return makeLoading(context, context.getString(msg));
    }

    public static class Builder {

        private final Context context;

        private String message;
        private Drawable icon;
        private int durationToDismiss;
        private boolean translucent;
        private boolean loading;
        private boolean cancelable;

        public Builder(Context context) {
            this.context = context;
            this.translucent = true;
            this.loading = false;
            this.cancelable = false;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder message(int messageRes) {
            return message(context.getString(messageRes));
        }

        public Builder icon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        public Builder icon(int iconRes) {
            return icon(context.getResources().getDrawable(iconRes));
        }

        public Builder autoDismiss(int durationToDismiss) {
            this.durationToDismiss = durationToDismiss;
            return this;
        }

        public Builder translucent(boolean translucent) {
            this.translucent = translucent;
            return this;
        }

        public Builder loading(boolean loading) {
            this.loading = loading;
            return this;
        }

        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public AlphaMessage build() {
            if (TextUtils.isEmpty(message) && icon == null) {
                throw new IllegalArgumentException("neither of message or icon.");
            }

            if (durationToDismiss <= 0) {
                durationToDismiss = 300;
            }
            return new AlphaMessage(this);
        }
    }
}