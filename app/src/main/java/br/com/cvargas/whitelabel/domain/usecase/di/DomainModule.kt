package br.com.cvargas.whitelabel.domain.usecase.di

import br.com.cvargas.whitelabel.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
sealed interface DomainModule {
    @Binds
    fun bindCreateProductUseCase(useCase: CreateProductUseCaseImpl): CreateProductUseCase

    @Binds
    fun bindGetProductsUseCase(useCase: GetProductsUseCaseImpl): GetProductsUseCase

    @Binds
    fun bindGetProductUseCase(useCase: GetProductUseCaseImpl): GetProductUseCase

    @Binds
    fun bindUploadProductImageUseCase(useCase: UploadProductImageUseCaseImpl): UploadProductImageUseCase
}