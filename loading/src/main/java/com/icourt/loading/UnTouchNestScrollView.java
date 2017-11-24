package com.icourt.loading;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author youxuan  E-mail:xuanyouwu@163.com
 * @version 2.2.1
 * @Description
 * @Company Beijing icourt
 * @date createTime：2017/11/24
 */
public class UnTouchNestScrollView extends NestedScrollView {
    public UnTouchNestScrollView(Context context) {
        super(context);
    }

    public UnTouchNestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnTouchNestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public final boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
