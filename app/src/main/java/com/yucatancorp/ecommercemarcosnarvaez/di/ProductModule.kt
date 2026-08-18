package com.yucatancorp.ecommercemarcosnarvaez.di

import android.content.Context
import com.yucatancorp.ecommercemarcosnarvaez.R
import com.yucatancorp.ecommercemarcosnarvaez.data.ProductRepositoryImpl
import com.yucatancorp.ecommercemarcosnarvaez.data.ProductsService
import com.yucatancorp.ecommercemarcosnarvaez.domain.GetProductUseCase
import com.yucatancorp.ecommercemarcosnarvaez.domain.ProductsRepository
import com.yucatancorp.ecommercemarcosnarvaez.utils.ServiceConstants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductModule {

    @Provides
    @Singleton
    fun providesAPI(): ProductsService {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ProductsService::class.java)
    }

    @Provides
    @Singleton
    fun providesProductsRepository(api: ProductsService): ProductsRepository {
        return ProductRepositoryImpl(api = api)
    }

    @Provides
    @Singleton
    fun providesUseCase(productsRepository: ProductsRepository): GetProductUseCase {
        return GetProductUseCase(productsRepository)
    }
}