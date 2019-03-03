package com.kotlin.order.injection.module

import com.kotlin.order.service.ShipAddressService
import com.kotlin.order.service.impl.ShipAddressServiceImpl
import dagger.Module
import dagger.Provides

/*
    收货人信息Module
 */
@Module
class ShipAddressModule {

    @Provides
    fun provideShipAddressService(shipAddressService: ShipAddressServiceImpl): ShipAddressService {
        return shipAddressService
    }

}
