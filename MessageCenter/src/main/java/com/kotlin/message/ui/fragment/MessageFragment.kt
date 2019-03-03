package com.kotlin.message.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.message.R
import com.kotlin.message.data.protocol.Message
import com.kotlin.message.injection.component.DaggerMessageComponent
import com.kotlin.message.injection.module.MessageModule
import com.kotlin.message.presenter.MessagePresenter
import com.kotlin.message.presenter.view.MessageView
import com.kotlin.message.ui.adapter.MessageAdapter
import com.kotlin.provider.event.MessageBadgeEvent
import kotlinx.android.synthetic.main.fragment_message.*

/**
 * Create by Pidan
 */
class MessageFragment : BaseMvpFragment<MessagePresenter>(), MessageView {

    private lateinit var mAdapter: MessageAdapter

    override fun injectComponent() {
        DaggerMessageComponent.builder()
            .activityComponent(mActivityComponent)
            .messageModule(MessageModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            Bus.send(MessageBadgeEvent(false))
        }
    }

    private fun initView() {
        mMessageRv.layoutManager = LinearLayoutManager(context)
        mAdapter = MessageAdapter(context!!)
        mMessageRv.adapter = mAdapter
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getMessageList()
    }

    override fun onGetMessageResult(result: MutableList<Message>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }
}