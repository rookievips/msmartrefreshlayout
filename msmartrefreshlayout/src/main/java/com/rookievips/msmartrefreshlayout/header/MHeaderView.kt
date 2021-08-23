package com.rookievips.msmartrefreshlayout.header

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.rookievips.msmartrefreshlayout.R
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.simple.SimpleComponent

class MHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SimpleComponent(context, attrs, defStyleAttr) {
    private lateinit var tips: TextView
    private lateinit var lottie: LottieAnimationView
    private var vibrate: Boolean = true

    companion object {
        const val REFRESH_HEADER_PULLING = "下拉可以刷新"
        const val REFRESH_HEADER_LOADING = "正在刷新..."
        const val REFRESH_HEADER_RELEASE = "释放立即刷新"
        const val REFRESH_HEADER_FINISH = "刷新成功"
        const val REFRESH_HEADER_FAILED = "刷新失败"
    }

    constructor(
        context: Context,
        lottieFile: String,
        lottieWidth: Int,
        lottieHeight: Int,
        enableTips: Boolean,
        tipsSize: Float,
        enableVibrate: Boolean
    ) : this(context) {
        val view = LayoutInflater.from(context).inflate(R.layout.msmartrefreshlayout_item_refresh_header, this)
        tips = view.findViewById(R.id.tv_tips)
        lottie = view.findViewById(R.id.lottie_view)
        if (enableTips) tips.visibility = View.VISIBLE else tips.visibility = View.GONE
        tips.setTextSize(TypedValue.COMPLEX_UNIT_PX, tipsSize)
        lottie.setAnimation(lottieFile)
        lottie.layoutParams?.width = lottieWidth
        lottie.layoutParams?.height = lottieHeight
        vibrate = enableVibrate
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        if (success) {
            tips.text = REFRESH_HEADER_FINISH
        } else {
            tips.text = REFRESH_HEADER_FAILED
        }
        super.onFinish(refreshLayout, success)
        return 500
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        when (newState) {
            RefreshState.PullDownToRefresh -> tips.text = REFRESH_HEADER_PULLING
            RefreshState.ReleaseToRefresh -> {
                tips.text = REFRESH_HEADER_RELEASE
                if (vibrate) performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            }
            RefreshState.Refreshing -> tips.text = REFRESH_HEADER_LOADING
            else -> {}
        }
    }
}