package com.kotlin.user.data.repository

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.user.data.api.UserApi
import com.kotlin.user.data.protocol.*
import javax.inject.Inject

/**
 * Create by Pidan
 */
class UserRepository @Inject constructor() {
    fun register(mobile: String, psw: String, verifyCode: String) =
        RetrofitFactory.create(UserApi::class.java)
            .register(RegisterReq(mobile, psw, verifyCode))


    fun login(mobile: String, psw: String, pushId: String) =
        RetrofitFactory.create(UserApi::class.java)
            .login(LoginReq(mobile, psw, pushId))

    fun forgetPwd(mobile: String, verifyCode: String) =
        RetrofitFactory.create(UserApi::class.java)
            .forgetPwd(ForgetPwdReq(mobile, verifyCode))

    fun resetPwd(mobile: String, psw: String) =
        RetrofitFactory.create(UserApi::class.java)
            .resetPwd(ResetPwdReq(mobile, psw))

    fun editUser(userIcon: String, userName: String, userGender: String, userSign: String) =
        RetrofitFactory.create(UserApi::class.java)
            .editUser(EditUserReq(userIcon, userName, userGender, userSign))
}