package br.com.cvargas.whitelabel.data.di

import br.com.cvargas.whitelabel.data.FirebaseProductDataSource
import br.com.cvargas.whitelabel.data.ProductDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
sealed interface DataSourceModule{
    @Singleton
    @Binds
    fun bindFirebaseProductDataSource(dataSource: FirebaseProductDataSource): ProductDataSource
}