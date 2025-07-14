package com.rcolejnr.rickmorty.core.network.di

import com.rcolejnr.rickmorty.core.network.RMDataSourceNetwork
import com.rcolejnr.rickmorty.core.network.RMRetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RMModuleNetwork {

    @Binds
    fun bindsRMDataSourceNetwork(
        dataSource: RMRetrofitNetwork
    ): RMDataSourceNetwork
}