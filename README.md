# alpha-loading


效果图

![image](https://github.com/icourt-android/alpha-loading/raw/master/snapshot/loading.gif)

gradle import:

根项目build.gradle引入
```
allprojects {
    repositories {
        ...
        maven {
             url 'https://jitpack.io'
        }
    }
}
```
目标项目build.gradle引入
```
dependencies {
    ...
    compile 'com.github.icourt-android:alpha-loading:0.9'
}
```

AlphaLoading: loading对话框，可以设置自定义加载动画，失败成功图标，以及提示消息  

Usage:
```
AlphaLoading loading = new AlphaLoading.Builder(context)
    .message("我在加载...")                      // 初始化message，可null
    .loadingDrawable(R.drawable.ani_loading)    // 加载动画/图片, 有默认值
    .okIcon(R.drawable.icon_ok)                 // 成功图标, 有默认值
    .failIcon(R.drawable.icon_fail)             // 失败图标, 有默认值
    .cancelable(true)                           // 是否可以手动取消(点击空白区域或返回键)
    .resultDuration(1000)                       // ok/fail持续时间(milliseconds)
    .create();

loading.show();     // 显示加载

loading.setMessage("开始上传...");  // 更新message

loading.dismissImmediately();      // 立即关闭

loading.dismissOk("上传成功");      // 显示成功图标,停留resultDuration时长后消失

loading.dismissFail("用户名不正确"); // 显示失败图标,停留resultDuration时长后消失

loading.dismissWithResult("some msg", R.drawable.some_icon);    // 显示自定义图标,停留resultDuration时长后消失

```

全局默认值，建议在application初始化的时候设置
```
AlphaLoading.setDefaultLoadingDrawable(R.drawable.ani_default_loading);
AlphaLoading.setDefaultOkIcon(R.drawable.icon_default_ok);
AlphaLoading.setDefaultFailIcon(R.drawable.icon_default_fail);
AlphaLoading.setDefaultCancelable(true);
AlphaLoading.setDefaultResultDuration(1000);    // >= 0
```

AlphaLoadingView: Loading视图 

Usage:
```
<com.icourt.app.loading.AlphaLoadingView
    android:layout_width="68dp"
    android:layout_height="68dp"
    android:src="@drawable/alpha_loading"/>
```

end