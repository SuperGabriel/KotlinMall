package com.kotlin.provider.router

/**
 * Create by Pidan
 */
object RouterPath {
    class UserCenter {
        companion object {
            const val PATH_LOGIN = "/userCenter/login"
        }
    }

    class OrderCenter {
        companion object {
            const val PATH_ORDER_CONFIRM = "/orderCenter/confirm"
            const val PATH_ORDER_LIST = "/orderCenter/list"
            const val PATH_ORDER_DETAIL = "/orderCenter/detail"
        }
    }

    //支付模块
    class PaySDK{
        companion object {
            const val PATH_PAY = "/paySDK/pay"
        }
    }

    //消息模块
    class MessageCenter{
        companion object {
            const val PATH_MESSAGE_PUSH = "/messageCenter/push"
        }
    }
}