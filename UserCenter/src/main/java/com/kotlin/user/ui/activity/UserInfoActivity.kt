package com.kotlin.user.ui.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.bigkoo.alertview.AlertView
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.GlideUtils
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.user.R
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.UserInfoPresenter
import com.kotlin.user.presenter.view.UserInfoView
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.TResult
import com.kotlin.base.utils.DateUtils
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import permissions.dispatcher.*
import java.io.File

@RuntimePermissions
class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView, View.OnClickListener,
    TakePhoto.TakeResultListener {

    private var mUserIcon: String? = null
    private var mUserName: String? = null
    private var mUserMobile: String? = null
    private var mUserGender: String? = null
    private var mUserSign: String? = null

    private lateinit var mTakePhoto: TakePhoto
    private val mUploadManager: UploadManager by lazy { UploadManager() }
    private var mLocalFileUrl: String? = null
    private var mRemoteFileUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)
        initView()
        initData()
    }

    override fun injectComponent() {
        DaggerUserComponent.builder()
            .activityComponent(mActivityComponent)
            .userModule(UserModule())
            .build().inject(this)
        mPresenter.mView = this
    }

    private fun initView() {
        mUserIconIv.onClick {
            showAlertViewWithPermissionCheck()
        }
        mHeaderBar.getRightView().onClick {
            mPresenter.editUser(
                mRemoteFileUrl ?: "",
                mUserNameEt.text?.toString() ?: "",
                if (mGenderMaleRb.isChecked) "0" else "1",
                mUserSignEt.text?.toString() ?: ""
            )
        }
    }

    private fun initData() {
        mUserIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
        mUserName = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        mUserMobile = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE)
        mUserGender = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)
        mUserSign = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)
        mRemoteFileUrl = mUserIcon
        GlideUtils.loadUrlImage(this, mUserIcon, mUserIconIv)
        mUserNameEt.setText(mUserName)
        mUserMobileTv.text = mUserMobile
        if (mUserGender == "0") mGenderMaleRb.isChecked = true
        else mGenderFemaleRb.isChecked = true
        mUserSignEt.setText(mUserSign)
    }

    override fun onClick(v: View) {
//        when (v.id) {
//
//            else -> return
//        }
    }

    @NeedsPermission(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun showAlertView() {
        AlertView.Builder().setContext(this)
            .setStyle(AlertView.Style.ActionSheet)
            .setTitle("选择图片")
            .setMessage(null)
            .setCancelText("取消")
            .setDestructive("拍照", "从相册中选择")
            .setOthers(null)
            .setOnItemClickListener { o, position ->
                mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), true)
                when (position) {
                    0 -> {
                        val tempFile = createTempFile()
                        mTakePhoto.onPickFromCapture(Uri.fromFile(tempFile))
                    }
                    1 -> mTakePhoto.onPickFromGallery()
                }
            }.build()
            .show()
    }

    override fun onEditUserResult(result: UserInfo) {
        toast("修改成功")
    }

    private fun createTempFile(): File {
        val tempFileName = "${DateUtils.curTime}.png"
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            File(Environment.getExternalStorageDirectory(), tempFileName)
        } else {
            File(filesDir, tempFileName)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @OnShowRationale(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun showRationaleForCamera(request: PermissionRequest) {
        request.proceed()
//        AlertDialog.Builder(this)
//            .setPositiveButton("同意") { _, _ -> request.proceed() }
//            .setNegativeButton("拒绝") { _, _ -> request.cancel() }
//            .setCancelable(false)
//            .setMessage("拍照需要取得相机权限，是否授予？")
//            .show()
    }

    @OnPermissionDenied(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun onCameraDenied() {
        toast("权限被拒绝，请前往开启")
    }

    override fun takeSuccess(result: TResult?) {
        println(result?.image?.compressPath)
        println(result?.image?.originalPath)

        mLocalFileUrl = result?.image?.compressPath
        mPresenter.getUploadToken()
    }

    override fun takeCancel() {
    }

    override fun takeFail(result: TResult?, msg: String?) {
        println("TakeFailure :$msg")
    }

    override fun onGetUploadTokenResult(result: String) {
        mUploadManager.put(mLocalFileUrl, null, result, object : UpCompletionHandler {
            override fun complete(key: String?, info: ResponseInfo?, response: JSONObject?) {
                response?.apply {
                    mRemoteFileUrl = BaseConstant.IMAGE_SERVER_ADDRESS + get("hash")
                    println("fileUri:$mRemoteFileUrl")
                    GlideUtils.loadUrlImage(this@UserInfoActivity, mRemoteFileUrl, mUserIconIv)
                }
            }
        }, null)
    }
}
