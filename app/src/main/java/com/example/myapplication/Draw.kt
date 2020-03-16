package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 2020/03/13
 * 学习资料来源于扔物线的自定义view课程和其他博客
 */
class Draw @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val path = Path()


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //设置画笔颜色
        paint.color= Color.BLUE
        //设置绘制模式
        paint.style=Paint.Style.STROKE
        //线条宽度像素
        paint.strokeWidth=5f
        //抗锯齿,默认关闭，大多数情况建议开启，原理是修改图形边缘处的像素颜色，从而让图形边缘处看上去更加平滑
        paint.isAntiAlias=true


        canvas?.let {


            /*========================================分割线Canvas基本使用=============================================*/
            //画前景
            //it.drawColor(Color.parseColor("#88880000"))

            //画圆
            it.drawCircle(300f, 300f, 200f, paint)

            //画矩形，左上右下，可以这样理解：左上确定xy坐标，右下确定宽和长
            it.drawRect(600f,400f,900f,600f,paint)

            //画单个点
            paint.strokeWidth=30f
            paint.strokeCap=Paint.Cap.ROUND
            it.drawPoint(300f,300f,paint)
            //画多个点
            /*
            pts 这个数组是点的坐标，每两个成一对；offset 表示跳过数组的前几个数再开始记坐标；count 表示一共要绘制几个点。
             */
            paint.color=Color.BLACK
            paint.strokeCap=Paint.Cap.BUTT
            val points= floatArrayOf(0f,0f,200f,200f,400f,200f,200f,400f,400f,400f)
            it.drawPoints(points,2,8,paint)

            //画椭圆
            paint.color=Color.GREEN
            //it.drawOval(600f,600f,800f,800f,paint)
            it.drawOval(RectF(600f,100f,900f,300f),paint)

            //画线
            paint.color=Color.BLACK
            val lines= floatArrayOf(100f,700f,800f,700f,  100f,800f,800f,800f,  100f,900f,800f,900f)
            it.drawLines(lines,paint)

            //画圆角矩形
            //paint.style=Paint.Style.FILL
            it.drawRoundRect(RectF(100f,1000f,600f,1200f),60f,60f,paint)

            /*
            画弧线或扇形或弧线,
            左上右下代表这个图像所在的椭圆，
            startAngle 是弧形的起始角度（x 轴的正向，即正右的方向，是 0 度的位置；顺时针为正角度，逆时针为负角度）
            sweepAngle是弧线划过的角度,
            userCeneter表示是否连接到圆心，如果不连接到圆心就是弧型，如果连接就是扇形，
            起点（0）为右边，顺时针为正
             */
            val arcRectF=RectF(100f,1300f,600f,1500f)
            paint.style=Paint.Style.FILL
            it.drawArc(arcRectF,20f,100f,true,paint)


            /*========================================分割线Path的基本使用==============================================*/
            /**
             * 自定义图形 ,复杂图形，组合图形 Path
             * Path 可以描述直线、二次曲线、三次曲线、圆、椭圆、弧形、矩形、圆角矩形。
             * 两大类方法：1，直接描叙路径(子图形和画直线画曲线)  2，辅助的设置和计算
             */

            //adXxx()添加子图形
            //参数dir 路径的绘制方向，CW为顺时针，CCW为逆时针，影响梯形等不规则图形和自相交图形的填充 的绘制效果
            path.addCircle(800f,1300f,50f,Path.Direction.CW)
            it.drawPath(path,paint)

            //xxxTo()画线
            path.reset()
            paint.style= Paint.Style.STROKE
            paint.strokeWidth=2f
            //lineTo参数是绝对位置，rLineTo的参数是相对位置，
            path.lineTo(100f,100f)
            path.rLineTo(100f,0f)
            it.drawPath(path,paint)


            //画二次贝塞尔曲线
            path.reset()
            path.quadTo(800f,500f,900f,300f)
            it.drawPath(path,paint)

            //画三次次贝塞尔曲线
            path.reset()
            path.cubicTo(10f,10f,200f,200f,100f,400f)
            it.drawPath(path,paint)


            //设置图形的起点 movetTo移动
            //当前位置：最后一次调用画 Path 的方法的终点位置。初始值为原点 (0, 0)
            //path.xxTo()的参数填的是距离当前位置的绝对坐标
            //path.rXxxTo()的参数填的是距离当前位置的相对坐标

            paint.color=Color.RED
            path.reset()
            path.moveTo(200f,200f)
            path.rLineTo(300f,400f)
            it.drawPath(path,paint)
            path.rMoveTo(200f,-400f)
            path.rLineTo(0f,400f)
            it.drawPath(path,paint)

            //arcTo 画弧形， forceMoveTo true为抬一下笔移动过去，否则直接一笔画过去,默认为不抬笔
            //addArc() = arcTo(forceMoveTo=true)的简写
            path.reset()
            paint.color=Color.GREEN
            path.moveTo(400f,800f)
            path.rLineTo(400f,200f)
            var rectF=RectF(800f,1000f,900f,1100f)
            path.arcTo(rectF,-90f,90f,false)
            it.drawPath(path,paint)

            /*
            path.close() 封闭子图形：
            就是落笔位置向当前子图形的起点绘制一条直线。
            close() 和 lineTo(起点坐标) 是完全等价的。
             */
            path.reset()
            paint.color=Color.BLACK
            path.moveTo(700f,1230f)
            path.rLineTo(200f,0f)
            path.rLineTo(-95f,200f)
            path.close()
            it.drawPath(path,paint)

            //自动封闭子图形，前提Paint.style=FILL or FILL_AND_STROKE
            path.reset()
            paint.style=Paint.Style.FILL
            path.moveTo(200f,550f)
            path.rLineTo(100f,0f)
            path.rLineTo(-140f,100f)
            it.drawPath(path,paint)


            /*以上就是 Path 的第一类方法：直接描述路径的。*/

            //Path方法第二类：辅助的设置和计算

            /*
             1,path.fillType 设置图形自相交(相切不算)时的填充算法，全填充还是交叉填充，相交是重合，相切是紧连
             2,EVEN_ODD 奇偶原则 交叉填充；
                图形中在任意点射出任意方向一条线，记录交点数；
                如果是交点是奇数则认为在内部区域，否则认为在外部区域，只填充内部区域也就是奇数部分
             3,WINDING  非零环绕数原则  相交的图形绘制方向一致 就全填充，否则交叉填充(如两个圆相交 但是绘制方向不一样 还是会按交叉填充)， 绘制方向由Path.Direction控制；
                以0为初始值，射线，遇到顺时针的交点就+1，遇到逆时针就-1，最后如果焦点数不为0就认为在图形内部，只填充内部区域
             4，INVERSE_XX 为上两个的填充算法的反转
             */
            path.reset()
            path.addCircle(900f,900f,100f,Path.Direction.CCW)
            path.addCircle(900f,1000f,100f,Path.Direction.CW)
            path.fillType=Path.FillType.WINDING
            it.drawPath(path,paint)


            //画图

            //1,直接画，给图和图的左上角坐标就行
            var bitmap=BitmapFactory.decodeResource(resources,android.R.mipmap.sym_def_app_icon)
            it.drawBitmap(bitmap,100f,1230f,paint)

            //处理拉伸效果，rect参数为null就绘制完整的图
            it.drawBitmap(bitmap,null,RectF(200f,1230f,400f,1400f),paint)

            //同时处理绘制哪部分和拉伸效果
            it.drawBitmap(bitmap,Rect(0,0,bitmap.width,bitmap.height/3),RectF(400f,1230f,bitmap.width+400f,(bitmap.height+1230f)),paint)

            //矩阵变换，可以对目标图片缩放(scale)，平移(translate)，旋转(rotate)，左右倾斜(skew)等
            val matrix=Matrix()
            //倾斜
            matrix.setSkew(0.2f,0f)
            it.drawBitmap(bitmap,matrix,paint)

            //TODO Canvas 扭曲图像 drawBitmapMesh


            //画字
            paint.textSize=18f
            it.drawText("Hello World",420f,40f,paint)
            paint.textSize=24f
            it.drawText("Hello World",420f,80f,paint)
            paint.textSize=30f
            it.drawText("Hello World",420f,120f,paint)

        }
    }
}