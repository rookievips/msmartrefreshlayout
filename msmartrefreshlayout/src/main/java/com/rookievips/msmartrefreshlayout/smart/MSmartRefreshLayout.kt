package com.rookievips.msmartrefreshlayout.smart

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import com.rookievips.msmartrefreshlayout.R
import com.rookievips.msmartrefreshlayout.header.MGifHeaderView
import com.rookievips.msmartrefreshlayout.header.MHeaderView
import com.rookievips.msmartrefreshlayout.utils.SizeUtil
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.simple.SimpleComponent

object MSmartRefreshConfig {
    const val STYLE_DEFAULT = 0
    const val STYLE_MATERIAL = 1
    const val STYLE_CLASSIC = 2
    const val STYLE_GIF = 3

    var defLottieFile = "lottie/refresh_loading.json"
    var defLottieWidth = 20f
    var defLottieHeight = 20f
    var defEnableTips = true
    var defTipsSize = 12f
    var defEnableVibrate = true
    var defEnableOverScrollBounce = false
    var defRefreshStyle = STYLE_DEFAULT
    var defMaterialSchemeColor = -1
    var defGifRes = R.mipmap.litter_bull
    var defGifWidth = 50f
    var defGifHeight = 50f

    fun init(
        lottieFile:String = defLottieFile,
        lottieWidth:Float = defLottieWidth,
        lottieHeight:Float = defLottieHeight,
        enableTips:Boolean = defEnableTips,
        tipsSize:Float = defTipsSize,
        enableVibrate:Boolean = defEnableVibrate,
        enableOverScrollBounce:Boolean = defEnableOverScrollBounce,
        refreshStyle:Int = defRefreshStyle,
        materialSchemeColor:Int = defMaterialSchemeColor,
        gifRes:Int = defGifRes,
        gifWidth:Float = defGifWidth,
        gifHeight:Float = defGifHeight
    ){
        defLottieFile = lottieFile
        defLottieWidth = lottieWidth
        defLottieHeight = lottieHeight
        defEnableTips = enableTips
        defTipsSize = tipsSize
        defEnableVibrate = enableVibrate
        defEnableOverScrollBounce = enableOverScrollBounce
        defRefreshStyle = refreshStyle
        defMaterialSchemeColor = materialSchemeColor
        defGifRes = gifRes
        defGifWidth = gifWidth
        defGifHeight = gifHeight
    }
}

class MSmartRefreshLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SmartRefreshLayout(context, attrs) {
    private var lottieFile: String
    private var lottieWidth: Int
    private var lottieHeight: Int
    private var enableTips: Boolean
    private var tipsSize: Float
    private var enableVibrate: Boolean
    private var enableOverScrollBounce: Boolean
    private var refreshStyle: Int
    private var materialSchemeColor: Int
    private var gifRes: Int
    private var gifWidth: Int
    private var gifHeight: Int

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["onRefreshListener"], requireAll = true)
        fun setOnRefreshListener(
            view: MSmartRefreshLayout,
            listener: OnRefreshListener
        ) {
            view.setOnRefreshListener(listener)
        }
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MSmartRefreshLayout)
        lottieFile = ta.getString(
            R.styleable.MSmartRefreshLayout_lottieFile
        ) ?: MSmartRefreshConfig.defLottieFile
        enableTips = ta.getBoolean(
            R.styleable.MSmartRefreshLayout_enableTips,
            MSmartRefreshConfig.defEnableTips
        )
        tipsSize = ta.getDimension(
            R.styleable.MSmartRefreshLayout_tipsSize,
            SizeUtil.sp2px(MSmartRefreshConfig.defTipsSize).toFloat()
        )
        enableVibrate = ta.getBoolean(
            R.styleable.MSmartRefreshLayout_enableVibrate,
            MSmartRefreshConfig.defEnableVibrate
        )
        enableOverScrollBounce = ta.getBoolean(
            R.styleable.MSmartRefreshLayout_enableOverScrollBounce,
            MSmartRefreshConfig.defEnableOverScrollBounce
        )
        refreshStyle = ta.getInt(
            R.styleable.MSmartRefreshLayout_refreshStyle,
            MSmartRefreshConfig.defRefreshStyle
        )
        lottieWidth = ta.getDimensionPixelSize(
            R.styleable.MSmartRefreshLayout_lottieWidth,
            SizeUtil.dp2px(MSmartRefreshConfig.defLottieWidth)
        )
        lottieHeight = ta.getDimensionPixelSize(
            R.styleable.MSmartRefreshLayout_lottieHeight,
            SizeUtil.dp2px(MSmartRefreshConfig.defLottieHeight)
        )
        materialSchemeColor = ta.getResourceId(
            R.styleable.MSmartRefreshLayout_materialSchemeColor,
            MSmartRefreshConfig.defMaterialSchemeColor
        )
        gifRes = ta.getResourceId(
            R.styleable.MSmartRefreshLayout_gif,
            MSmartRefreshConfig.defGifRes
        )
        gifWidth = ta.getDimensionPixelSize(
            R.styleable.MSmartRefreshLayout_gifWidth,
            SizeUtil.dp2px(MSmartRefreshConfig.defGifWidth)
        )
        gifHeight = ta.getDimensionPixelSize(
            R.styleable.MSmartRefreshLayout_gifHeight,
            SizeUtil.dp2px(MSmartRefreshConfig.defGifHeight)
        )
        ta.recycle()

        setEnableOverScrollBounce(enableOverScrollBounce)
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        var headerView: SimpleComponent? = null
        when (refreshStyle) {
            MSmartRefreshConfig.STYLE_DEFAULT -> {
                headerView = MHeaderView(
                    context,
                    lottieFile = lottieFile,
                    lottieWidth = lottieWidth,
                    lottieHeight = lottieHeight,
                    enableTips = enableTips,
                    tipsSize = tipsSize,
                    enableVibrate = enableVibrate
                )
            }
            MSmartRefreshConfig.STYLE_GIF -> {
                headerView = MGifHeaderView(
                    context,
                    gifRes = gifRes,
                    gifWidth = gifWidth,
                    gifHeight = gifHeight,
                    enableTips = enableTips,
                    tipsSize = tipsSize,
                    enableVibrate = enableVibrate
                )
            }
            MSmartRefreshConfig.STYLE_CLASSIC -> {
                headerView = ClassicsHeader(
                    context
                )
            }
            MSmartRefreshConfig.STYLE_MATERIAL -> {
                headerView = MaterialHeader(
                    context
                )
                if (materialSchemeColor != -1) headerView.setColorSchemeResources(
                    materialSchemeColor
                )
            }
        }
        headerView?.layoutParams = lp
        addView(headerView, 0)
    }

    fun setRefreshing(refreshing: Boolean) {
        if (refreshing) autoRefresh() else finishRefresh()
    }
}