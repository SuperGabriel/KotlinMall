package com.kotlin.user.ui.activity

import android.os.Bundle
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.user.R
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.ResetPwdPresenter
import com.kotlin.user.presenter.view.ResetPwdView
import kotlinx.android.synthetic.main.activity_reset_pwd.*
import org.jetbrains.anko.*

class ResetPwdActivity : BaseMvpActivity<ResetPwdPresenter>(), ResetPwdView {
    override fun injectComponent() {
        DaggerUserComponent.builder()
            .activityComponent(mActivityComponent)
            .userModule(UserModule())
            .build().inject(this)
        mPresenter.mView = this
    }

    override fun onResetPwdResult(result: String) {
        if (mPwdEt.text.toString() != mPwdConfirmEt.text.toString()) {
            toast("两次输入密码不一致")
            return
        }
        toast(result)
        startActivity(intentFor<LoginActivity>().singleTop().clearTop())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)

        initView()
    }

    private fun initView() {
        mConfirmBtn.enable(mPwdEt, ::isBtnEnable)
        mConfirmBtn.enable(mPwdConfirmEt, ::isBtnEnable)

        mConfirmBtn.onClick { mPresenter.resetPwd(intent.getStringExtra("mobile"), mPwdEt.text.toString()) }
    }

    private fun isBtnEnable(): Boolean {
        return mPwdEt.text.isNullOrEmpty().not() and
                mPwdConfirmEt.text.isNullOrEmpty().not()
    }
}
