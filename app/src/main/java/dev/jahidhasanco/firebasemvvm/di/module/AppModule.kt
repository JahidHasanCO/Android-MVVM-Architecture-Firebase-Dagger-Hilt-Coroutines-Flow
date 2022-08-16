package dev.jahidhasanco.firebasemvvm.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.jahidhasanco.firebasemvvm.repository.AuthRepository
import dev.jahidhasanco.firebasemvvm.repository.UserRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    fun provideAuthRepository(@ApplicationContext app: Context) = AuthRepository(app)

    @Provides
    fun provideUserRepository() = UserRepository()
}