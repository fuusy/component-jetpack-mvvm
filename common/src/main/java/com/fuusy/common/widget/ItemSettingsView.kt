package com.fuusy.common.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.fuusy.common.R
import com.fuusy.common.databinding.ItemSettingBinding

/**
 * @date：2021/5/20
 * @author fuusy
 * @instruction：自定义的设置item的组合控件
 */
class SettingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    /*左侧显示文本*/
    private var mLeftText: String? = null

    /*右侧图标*/
    private var mRightIcon: Drawable? = null

    /*左侧显示文本大小*/
    private val mTextSize = 0

    /*左侧显示文本颜色*/
    private val mTextColor = 0

    /*右侧显示文本大小*/
    private val mRightTextSize = 0f

    /*右侧显示文本颜色*/
    private val mRightTextColor = 0

    /*右侧图标展示风格*/
    private var mRightStyle = 0

    /*选中状态*/
    private val mChecked = false

    /*点击事件*/
    private var mOnSettingItemClick: OnSettingItemClick? = null
    private var mOnCheckChangeListener: OnCheckChangeListener? = null
    private var mOnEditTextChangeListener: OnEditTextChangeListener? = null
    private var mBinding: ItemSettingBinding? = null
    fun setOnSettingItemClick(onSettingItemClick: OnSettingItemClick?) {
        mOnSettingItemClick = onSettingItemClick
    }

    fun setOnCheckedChangeListener(listener: OnCheckChangeListener?) {
        mOnCheckChangeListener = listener
    }

    fun addOnEditTextChangeListener(listener: OnEditTextChangeListener?) {
        mOnEditTextChangeListener = listener
    }

    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attrs
     */
    @SuppressLint("CustomViewStyleable")
    private fun getCustomStyle(
        context: Context,
        attrs: AttributeSet?
    ) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SettingItem)
        val n = a.indexCount
        for (i in 0 until n) {
            when (val attr = a.getIndex(i)) {
                R.styleable.SettingItem_leftText -> {
                    mLeftText = a.getString(attr)
                    mBinding?.tvLefttext?.text = mLeftText
                }
                R.styleable.SettingItem_rightIcon -> {
                    // 右侧图标
                    mRightIcon = a.getDrawable(attr)
                    mBinding?.ivEnter?.setImageDrawable(mRightIcon)
                }
                R.styleable.SettingItem_rightStyle -> {
                    mRightStyle = a.getInt(attr, 0)
                }
                R.styleable.SettingItem_rightText -> {
                    mBinding?.tvRighttext?.text = a.getString(attr)
                }
                R.styleable.SettingItem_editTextHInt -> {
                    mBinding?.editTextRight?.hint = a.getString(attr)
                }
                R.styleable.SettingItem_rightIconText -> {
                    mBinding?.tvTightTextAndIcon?.text = a.getString(attr)
                }
                R.styleable.SettingItem_bottomText -> {
                    mBinding?.tvMainContent?.text = a.getString(attr)
                }
                R.styleable.SettingItem_leftTopText -> {
                    mBinding?.tvLeftTop?.text = a.getString(attr)
                }
            }
        }
        a.recycle()
    }

    /**
     * 根据设定切换右侧展示样式，同时更新点击事件处理方式
     *
     * @param mRightStyle
     */
    private fun switchRightStyle(mRightStyle: Int) {
        when (mRightStyle) {
            0 -> {
                //默认展示样式,左右文字
                mBinding?.tvLefttext?.visibility = View.VISIBLE
                mBinding?.tvRighttext?.visibility = View.VISIBLE
            }
            1 -> {
                //文字+icon
                mBinding?.tvLefttext?.visibility = View.VISIBLE
                mBinding?.tvTightTextAndIcon?.visibility = View.VISIBLE
                mBinding?.ivEnter?.visibility = View.VISIBLE
            }
            2 -> {
                //左文字 右icon
                mBinding?.tvLefttext?.visibility = View.VISIBLE
                mBinding?.ivEnter?.visibility = View.VISIBLE
            }
            3 -> {
                //左文字，右边radiobutton
                mBinding?.radioGroup2?.visibility = View.VISIBLE
                mBinding?.tvLefttext?.visibility = View.VISIBLE
            }
            4 -> {
                //左文字，右边EditText
                mBinding?.tvLefttext?.visibility = View.VISIBLE
                mBinding?.editTextRight?.visibility = View.VISIBLE
            }
            5 -> {
                //上下文字
                mBinding?.tvLefttext?.visibility = View.VISIBLE
                mBinding?.tvMainContent?.visibility = View.VISIBLE
            }
            else -> {
            }
        }
    }

    private fun initView(context: Context) {
        mBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_setting, this, true)
        mBinding?.rbMan?.isChecked = true
    }

    interface OnSettingItemClick {
        fun click(v: View?)
    }

    interface OnCheckChangeListener {
        fun onCheck(sexId: Int)
    }

    interface OnEditTextChangeListener {
        fun afterTextChanged(s: Editable?)
    }

    /**
     * 动态设置右侧EditText文字（没有icon）
     *
     * @param text
     */
    fun setRightEditText(text: String?) {
        mBinding?.editTextRight?.setText(text)
    }

    /**
     * 动态设置右侧带有icon的文字内容
     *
     * @param text
     */
    fun setRightTextIcon(text: String?) {
        mBinding?.tvTightTextAndIcon?.setText(text)
    }

    /**
     * 动态获取右侧文字
     *
     * @return
     */
    /**
     * 动态设置右侧文字（没有icon）
     *
     * @param text
     */
    var rightText: String?
        get() = mBinding?.tvRighttext?.text.toString()
        set(text) {
            mBinding?.tvRighttext?.text = text
        }

    /**
     * 动他获取EditText的内容
     *
     * @return
     */
    val editText: String
        get() = mBinding?.editTextRight?.text.toString()

    /**
     * 设置EditText的inputType.
     *
     * @param type
     */
    fun setInputType(type: Int) {
        mBinding?.editTextRight?.inputType = type
    }

    /**
     * 设置内容
     *
     * @param text
     */
    fun setMainContent(text: String) {
        mBinding?.tvMainContent?.text = text
    }


    companion object {
        private const val TAG = "SettingView"
    }

    init {
        initView(context)
        getCustomStyle(context, attrs)
        //获取到右侧展示风格，进行样式切换
        switchRightStyle(mRightStyle)
        mBinding?.root?.setOnClickListener { v ->
            if (null != mOnSettingItemClick) {
                mOnSettingItemClick!!.click(v)
            }
        }

        mBinding?.editTextRight?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                if (mOnEditTextChangeListener != null) {
                    mOnEditTextChangeListener!!.afterTextChanged(s)
                }
            }
        })
    }
}