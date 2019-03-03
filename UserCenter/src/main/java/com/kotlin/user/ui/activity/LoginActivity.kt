package com.kotlin.user.ui.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.provider.PushProvider
import com.kotlin.provider.router.RouterPath
import com.kotlin.user.R
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.LoginPresenter
import com.kotlin.user.presenter.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

@Route(path = RouterPath.UserCenter.PATH_LOGIN)
class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView, View.OnClickListener {

    @Autowired(name = RouterPath.MessageCenter.PATH_MESSAGE_PUSH)
    @JvmField
    var mPushProvider: PushProvider? = null

    override fun injectComponent() {
        DaggerUserComponent.builder()
            .activityComponent(mActivityComponent)
            .userModule(UserModule())
            .build().inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        mLoginBtn.enable(mMobileEt, ::isBtnEnable)
        mLoginBtn.enable(mPwdEt, ::isBtnEnable)

        mHeaderBar.getRightView().onClick(this)
        mLoginBtn.onClick(this)
        mForgetPwdTv.onClick(this)

        mMobileEt.setText("123")
        mPwdEt.setText("123")
    }

    private var pressTime: Long = 0

//    override fun onBackPressed() {
//        val time = System.currentTimeMillis()
//        if (time - pressTime > 2000) {
//            toast("再按一次退出程序")
//            pressTime = time
//        } else {
//            AppManager.exitApp(this)
//        }
//    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mRightTv -> startActivity<RegisterActivity>()
            R.id.mLoginBtn ->
                mPresenter.login(
                mMobileEt.text.toString(),
                mPwdEt.text.toString(),
                mPushProvider?.getPushId() ?: ""
            )
            R.id.mForgetPwdTv -> startActivity<ForgetPwdActivity>()
            else -> return
        }
    }

    override fun onLoginResult(result: UserInfo) {
        finish()
    }

    private fun isBtnEnable(): Boolean {
        return mMobileEt.text.isNullOrEmpty().not() and
                mPwdEt.text.isNullOrEmpty().not()
    }
}
