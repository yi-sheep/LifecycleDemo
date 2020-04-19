package com.gaoxianglong.lifecycledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val TAG = "Lifecycle"

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

    /**
     * 当活动被展示在用户眼前时调用
     * 如果活动出现在前台紧接着是onResume()
     * 如果活动直接隐藏则紧接着是onStop()
     */
    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart()") // 这里使用Log.d在控制台打印该函数的函数名
    }

    /**
     * 当活动将开始与用户进行交互时调用
     * 在这个时间点当前活动将会在活动堆栈的顶端
     * 并且处于运行状态
     */
    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume()") // 这里使用Log.d在控制台打印该函数的函数名
    }

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

    /**
     * 这个函数会在活动被摧毁之前调用
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy()") // 这里使用Log.d在控制台打印该函数的函数名
    }

    /**
     * 这个函数是从停止状态变为运行状态时调用
     */
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG,"onRestart()") // 这里使用Log.d在控制台打印该函数的函数名
    }
}
