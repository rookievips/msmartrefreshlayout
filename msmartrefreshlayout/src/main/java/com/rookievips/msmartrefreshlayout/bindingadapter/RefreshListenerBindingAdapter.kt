package com.rookievips.msmartrefreshlayout.bindingadapter

import androidx.databinding.BindingAdapter
import com.rookievips.msmartrefreshlayout.smart.MSmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

object RefreshListenerBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["onRefreshListener"], requireAll = true)
    fun setOnRefreshListener(
        view: MSmartRefreshLayout,
        listener: OnRefreshListener
    ) {
        view.setOnRefreshListener(listener)
    }
}