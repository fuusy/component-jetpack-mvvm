package com.fuusy.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlin.math.abs

class FSmartRefreshLayout(context: Context?, attrs: AttributeSet?) :
    SmartRefreshLayout(context, attrs) {

    /**
     * 按下时的坐标
     */
    private var startX = 0
    private var startY = 0

    /**
     * 只在此处通知父View，不影响内部事件处理结果
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        when(ev.action){
            MotionEvent.ACTION_DOWN->{
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                //通知父View不拦截事件
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE->{
                //当前手指的位置
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                //x,y轴移动的距离
                val disX = abs(endX - startX)
                val disY = abs(endY - startY)
                //如果x轴移动距离大于y轴移动距离视为水平滑动
                if (disX>disY){
                    //水平滑动通知不限制父View拦截
                    parent.requestDisallowInterceptTouchEvent(false)
                }else{
                    //如果达到垂直滑动条件，通知父View不做事件拦截
                    parent.requestDisallowInterceptTouchEvent(disX>8)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL->{
                //本次事件序列结束时通知父View拦截
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

}