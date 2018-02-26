package com.example.li.myviewdemos

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.RectF
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View

/**
 * 自定义View 满足进度显示需要
 */

class PizzaView : View {

    private var layout_height = 0
    private var layout_width = 0

    // size
    var outLineWidth = 1
    var totalPiece = 8
    var currentPiece = 2
    var pzHeight = 0
    var pzWidth = 0

    // color
    @ColorInt
    private var outLineColor = 0xFFFB8D04.toInt()
    @ColorInt
    private var defaultFullColor = 0x55000000.toInt()
    @ColorInt
    private var fullColor = 0xFFFEFFCB.toInt()
    // paint
    private val outLinePaint = Paint()
    private val pzFullPaint = Paint()
    private val pzDefaultFullPaint = Paint()

    // rect
    internal var ovalRectF: RectF? = null

    // angle
    internal var angleOffset: Float = 0.0f
    private var defaultArrcAngle: FloatArray? = null
    private var currentArrcAngle: Float = 0.0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        //xml中绑定自定义属性
        parseAttributeSet(context.obtainStyledAttributes(attrs, R.styleable.PizzaView))

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh)

        layout_width = w
        layout_height = h

        setupBounds()
        setupPaints()

        updateUI()

        invalidate()

    }


    /**
     * 更新画面
     */
    fun updateUI() {

        angleOffset = (360 / totalPiece).toFloat()

        defaultArrcAngle = FloatArray(totalPiece)
        for (i in 0 until totalPiece) {
            val angle = angleOffset * i - 90
            defaultArrcAngle!![i] = angle
        }
        currentArrcAngle = angleOffset * currentPiece

        //重新绘制canvas
        invalidate()
    }

    /**
     * 设置画笔的样式
     */
    private fun setupPaints() {
        // TODO Auto-generated method stub
        outLinePaint.color = outLineColor
        outLinePaint.isAntiAlias = true
        outLinePaint.style = Style.STROKE
        outLinePaint.strokeWidth = outLineWidth.toFloat()

        pzFullPaint.color = fullColor
        pzFullPaint.isAntiAlias = true
        pzFullPaint.style = Style.FILL

        pzDefaultFullPaint.color = defaultFullColor
        pzDefaultFullPaint.isAntiAlias = true
        pzDefaultFullPaint.style = Style.FILL

    }


    /**
     * 设置边界和view大小
     */
    private fun setupBounds() {
        // TODO Auto-generated method stub
        pzWidth = width - paddingRight
        pzHeight = height - paddingBottom

        ovalRectF = RectF(paddingLeft.toFloat(), paddingTop.toFloat(), pzWidth.toFloat(), pzHeight.toFloat())

    }

    override fun onAttachedToWindow() {
        // TODO Auto-generated method stub
        super.onAttachedToWindow()

    }


    /**
     * 设置用户自定义的属性值
     */
    private fun parseAttributeSet(a: TypedArray) {
        outLineWidth = a.getDimension(R.styleable.PizzaView_pzOutLineSize, outLineWidth.toFloat()).toInt()
        totalPiece = a.getInteger(R.styleable.PizzaView_pzTotalPiece, totalPiece)
        currentPiece = a.getInteger(R.styleable.PizzaView_pzCurrentPiece, currentPiece)
        pzHeight = a.getDimensionPixelOffset(R.styleable.PizzaView_pzHeight, pzHeight)
        pzWidth = a.getDimensionPixelOffset(R.styleable.PizzaView_pzWidth, pzWidth)
        outLineColor = a.getColor(R.styleable.PizzaView_pzOutLineColor, outLineColor)
        defaultFullColor = a.getColor(R.styleable.PizzaView_pzDefaultFullColor, defaultFullColor)
        fullColor = a.getColor(R.styleable.PizzaView_pzFullColor, fullColor)

        a.recycle()

    }


    fun setCurrentPice(currentPiece: Int) {
        this.currentPiece = currentPiece
        updateUI()
    }

    fun updateUI(totalPiece: Int, currentPiece: Int) {
        this.totalPiece = totalPiece
        this.currentPiece = currentPiece
        updateUI()
    }

    fun getOutLineColor(): Int {
        return outLineColor
    }

    fun setOutLineColor(outLineColor: Int) {
        this.outLineColor = outLineColor

        postInvalidate()
    }


    /**
     * 重写绘制方法 画出业务需要的图形
     */
    override fun onDraw(canvas: Canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas)

        // 绘制底盘
        canvas.drawArc(ovalRectF, -90.0f, 360.0f, true, pzDefaultFullPaint)

        //绘制目前的份数
        canvas.drawArc(ovalRectF, -90.0f, currentArrcAngle, true, pzFullPaint)

        //绘制pizza分割线
        for (i in defaultArrcAngle!!.indices) {
            val startAngle = defaultArrcAngle!![i]
            canvas.drawArc(ovalRectF, startAngle, angleOffset, true, outLinePaint)
        }

    }

}