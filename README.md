# LifecycleDemo

这是将Android第一行代码第二版翻译成kotlin的第二个项目，是从第二章开始的，当前项目使用kotlin演示了Activity的生命周期、Activity的启动模式。

Activity的生命周期:

活动有7个生命周期函数，分别是

onCreate()
```kotlin
/**
 * 这个函数会在活动第一次被创建的时候调用
 * 这个函数一般用来做初始化操作，比如加载布局、绑定事件
 * 该函数会提供了一个包含之前活动的冻结状态信息bundle包，可以用于恢复数据
 */
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Log.d(TAG,"onCreate()") // 这里使用Log.d在控制台打印该函数的函数名
    button.setOnClickListener {
        val intent = Intent(this, Main2Activity::class.java) // 跳转到第二个活动
        startActivity(intent) // 启动活动
    }
}
```
onStart()
```kotlin
/**
 * 当活动被展示在用户眼前时调用
 * 如果活动出现在前台紧接着是onResume()
 * 如果活动直接隐藏则紧接着是onStop()
 */
override fun onStart() {
    super.onStart()
    Log.d(TAG,"onStart()") // 这里使用Log.d在控制台打印该函数的函数名
}
```
onResume()
```kotlin
/**
 * 当活动将开始与用户进行交互时调用
 * 在这个时间点当前活动将会在活动堆栈的顶端
 * 并且处于运行状态
 */
override fun onResume() {
    super.onResume()
    Log.d(TAG,"onResume()") // 这里使用Log.d在控制台打印该函数的函数名
}
```
onPause()
```kotlin
/**
 * 当系统将要恢复一个之前的活动。
 * 这是一个有代表性的常常用于提交未被存储的信息为持久数据，关闭动画和消耗CPU的东西等
 * 实现该函数必须要特别的迅速，因为在此函数返回之前，下一个活动将不会恢复
 * 如果活动将返回到前台则接下来调用onResume()
 * 如果要隐藏到用户看不见的地方时，则调用onStop()
 */
override fun onPause() {
    super.onPause()
    Log.d(TAG,"onPause()") // 这里使用Log.d在控制台打印该函数的函数名
}
```
onStop()
```kotlin
/**
 * 当另一个活动被恢复且完全覆盖该活动，而该Activity将不在展示给用户时调用
 * 和onPause()有点类似，可以暂时理解成onPause()调用时新活动不会完全覆盖掉当前活动
 * 比如新启动的活动是一个对话框它就不会让当前活动处于完全不可见的状态
 * 而onStop()是启动了一个正常的活动，把当前的活动变到完全不可见的状态了
 */
override fun onStop() {
    super.onStop()
    Log.d(TAG,"onStop()") // 这里使用Log.d在控制台打印该函数的函数名
}
```
onDestroy()
```kotlin
/**
 * 这个函数会在活动被摧毁之前调用
 */
override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG,"onDestroy()") // 这里使用Log.d在控制台打印该函数的函数名
}
```
onRestart()
```kotlin
/**
 * 这个函数是从停止状态变为运行状态时调用
 */
override fun onRestart() {
    super.onRestart()
    Log.d(TAG,"onRestart()") // 这里使用Log.d在控制台打印该函数的函数名
}
```

以上就是7个生命周期函数，理解透彻它们的调用时机，在对应的时机做对应的事。

来看看官方的描述图

<img src="https://upload-images.jianshu.io/upload_images/12239817-57bb34bbf201853d.png?imageMogr2/auto-orient/strip|imageView2/2/w/1074/format/webp"/>

要怎么观察到呢，先创建一个项目，然后再创建一个活动Main2Activity，这样就有两个活动了,在两个活动的布局里都添加一个button分别用于跳转、返回。
然后将上面的7个生命周期函数添加到活动1中，运行观察打印的日志。

Activity的启动模式:

活动有4种启动模式，分别是 standard、singleTop、singleTask、singleInstance。在AndroidManifest.xml(配置文件)中通过给`<activity>`标签指定android:launchMode属性来选择启动模式。

standard
默认模式，可以不用写配置。在这个模式下，所有活动都会默认创建一个新的实例。因此，在这种模式下，会有多个相同的实例，也允许多个相同Activity叠加。
举个栗子：
现在有一个活动名为A1, 活动有一个按钮可跳转到A1。那么如果我点击按钮，便会新启一个活动A1叠在刚才的A1之上，再点击，又会再新启一个在它之上如此反复会出现很多同样的活动A1，点系统返回键会依照栈顺序依次退出。
```xml
<activity android:name=".MainActivity"
    android:launchMode="standard">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```
singleTop
可以有多个实例，但是不允许多个相同Activity叠加。如果活动在栈顶的时候，启动相同的活动，不会创建新的实例，而会调用其onNewIntent方法。
举个栗子：
现在有两个活动名为A1,A2,两个活动都有两个按钮可以跳到A1和A2，唯一不同的是A1为standard，A2为singleTop。我在A1中点击跳到A1的按钮会创建一个新的A1，再点击跳到A2的按钮会创建一个A2，这个时候堆栈中从底部向上看分别是A1->A1->A2.在A2中点击跳到A2的按钮就不会创建一个A2，这个时候的堆栈依然是A1->A1->A2.再点击跳到A1的按钮，这时候堆栈变成了这样A1->A1->A2->A1。
```xml
<activity android:name=".MainActivity"
    android:launchMode="singleTop">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```
singleTask
同一个活动只有一个实例。在同一个应用程序中启动他的时候，若活动在堆栈中不存在，则会在当前的栈顶创建一个新的实例，若存在，则会把堆栈中在其之上的其它活动摧毁掉并调用它的onNewIntent方法。
举个栗子：
现在堆栈中有这样的活动实例顺序 A->B->C->D 它们的启动模式都是singleTask，栈顶是D，目前我们应用程序显示在屏幕上的活动就是D，如果在D中启动了C，堆栈就会变成这样 A->B->C. 如果在D中启动了B，堆栈就会变成这样 A->B. 如果在D中启动了A，那么堆栈中就只有A一个活动了。
```xml
<activity android:name=".MainActivity"
    android:launchMode="singleTask">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```
singleInstance
只有一个实例，并且这个实例独立运行在一个栈中，这个栈只有这个实例，不允许有别的活动存在。
举个栗子：
假如你的这个应用程序的某一个活动要共享给其他应用程序使用，如果是使用上面的那三种启动模式，多少都会影响当前应用程序的其他活动。如果是将要共享的活动启动模式设置为singleInstance，那么当启动这个活动的时候会创建一个新的栈来存放这个活动，也就是说这个栈是属于这个活动的，这样就不会影响其他的活动了。
```xml
<activity android:name=".MainActivity"
    android:launchMode="singleInstance">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

启动模式我的Demo里不好同时演示，所以上面把4种都列了出来，自己去改，然后体验一下，他们的区别。