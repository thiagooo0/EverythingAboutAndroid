package com.kowksiuwang.everythingaboutandroid.View

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import com.kowksiuwang.everythingaboutandroid.util.KLog

/**
 *
 * jvmOverloads的作用是让编译器根据构造方法去创造多个重载方法，而这些重载方法最后都是指向最多参数的那个方法。
 * 但是可能存在问题，在继承view的类中使用时，xml中生成是调用两个参数的方法的，而某些view类在自己两个参数构造方法中，会加上自定义的第三个参数，调用自身的三参数方法。
 * 但使用jvmOverLoad，会再自定义的view层面上就直接调用三参数的方法。跳过了父view加自定义的第三个参数这一步。
 * Created by GuoShaoHong on 2020/7/23.
 */
class ViewInitOrder : View {
    val log = KLog("ViewInitOrder_LOG")

    /**
     * 顺序：
     * init
     * constructor 2 0 0
     * onFinishInflate 0 0
     * onAttachedToWindow 0 0
     * init
     * onMeasure 0 450
     * onMeasure 0 450
     * onSizeChanged 450 450
     * onLayout 450 450
     * onWindowFocusChanged 450 450
     * onDraw
     */
    constructor(context: Context) : super(context) {
        log.d("constructor 1 $width $measuredWidth")
    }

    constructor(
        context: Context,
        attrs: AttributeSet? = null
    ) : super(context, attrs) {
        log.d("constructor 2 $width $measuredWidth")
    }

    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        log.d("constructor 3 $width $measuredWidth")
    }

    init {
        log.d("init")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        log.d("onFinishInflate $width $measuredWidth")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        log.d("onMeasure $width $measuredWidth")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        log.d("onLayout $width $measuredWidth")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        log.d("onSizeChanged $width $measuredWidth")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        log.d("onDraw")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        log.d("onKeyDown")
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        log.d("onKeyUp")
        return super.onKeyUp(keyCode, event)
    }

    override fun onTrackballEvent(event: MotionEvent?): Boolean {
        log.d("onTrackballEvent")
        return super.onTrackballEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        log.d("onTouchEvent")
        return super.onTouchEvent(event)
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        log.d("onFocusChanged")
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        log.d("onWindowFocusChanged $width $measuredWidth")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        log.d("onAttachedToWindow $width $measuredWidth")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        log.d("onDetachedFromWindow")
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        log.d("init")
    }
}

