package com.kotlin.user.injection.module

import com.kotlin.user.service.UploadService
import com.kotlin.user.service.impl.UploadServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UploadModule {
    @Provides
    fun providesUploadService(uploadService: UploadServiceImpl): UploadService = uploadService
}