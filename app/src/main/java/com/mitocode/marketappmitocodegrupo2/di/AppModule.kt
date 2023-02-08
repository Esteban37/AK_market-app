package com.mitocode.marketappmitocodegrupo2.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mitocode.marketappmitocodegrupo2.data.NetworkInterceptor
import com.mitocode.marketappmitocodegrupo2.data.RemoteService
import com.mitocode.marketappmitocodegrupo2.data.database.AppDatabase
import com.mitocode.marketappmitocodegrupo2.data.database.CategoryDao
import com.mitocode.marketappmitocodegrupo2.data.datasource.*
import com.mitocode.marketappmitocodegrupo2.framework.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkInterceptor: NetworkInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .addInterceptor(loggingInterceptor)
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteService(okHttpClient: OkHttpClient): RemoteService {

        return Retrofit.Builder()
            .baseUrl("http://18.234.189.188:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create()
    }

    @Provides
    @Singleton
    fun provideSharePreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            "PREFERENCES_TOKEN",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "BdMarket"
    ).build()

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase): CategoryDao = db.categoryDao()


}


//Interfaces
@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindModule {

    @Binds
    abstract fun bindUserRemoteDataSource(userRemoteDataSourceImp: UserRemoteDataSourceImp): UserRemoteDataSource

    @Binds
    abstract fun bindGenderRemoteDataSource(genderRemoteDataSourceImp: GenderRemoteDataSourceImp): GenderRemoteDataSource

    @Binds
    abstract fun bindCategoryRemoteDataSource(categoryRemoteDataSourceImp: CategoryRemoteDataSourceImp): CategoryRemoteDataSource

    @Binds
    abstract fun bindProductRemoteDataSource(productRemoteDataSourceImp: ProductRemoteDataSourceImp): ProductRemoteDataSource

    @Binds
    abstract fun bindCategoryLocallyDataSource(categoryLocallyDataSourceImp: CategoryLocallyDataSourceImp): CategoryLocallyDataSource
}