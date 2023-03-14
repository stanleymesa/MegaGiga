package com.stanleymesa.megagiga.di

import com.stanleymesa.core.domain.interactor.Interactor
import com.stanleymesa.core.domain.usecase.LoginUseCase
import com.stanleymesa.core.domain.usecase.ProductUseCase
import com.stanleymesa.core.domain.usecase.RegisterUseCase
import com.stanleymesa.core.domain.usecase.SupplierUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideLoginUseCase(interactor: Interactor): LoginUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideRegisterUseCase(interactor: Interactor): RegisterUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideProductUseCase(interactor: Interactor): ProductUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideSupplierUseCase(interactor: Interactor): SupplierUseCase

}