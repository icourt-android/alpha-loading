package com.icourt.loading;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author youxuan  E-mail:xuanyouwu@163.com
 * @version 2.2.1
 * @Description 支持嵌套滚动
 * @Company Beijing icourt
 * @date createTime：2017/11/15
 */
public class NestFrameLayout extends FrameLayout implements NestedScrollingChild {

    private NestedScrollingChildHelper mChildHelper;

    public NestedScrollingChildHelper getmChildHelper() {
        if (mChildHelper == null) {
            init();
        }
        return mChildHelper;
    }

    private void init() {
        setNestedScrollingEnabled(true);
        mChildHelper = new NestedScrollingChildHelper(this);
    }

    public NestFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public NestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getmChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getmChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getmChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getmChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getmChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return getmChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getmChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getmChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getmChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }
}
