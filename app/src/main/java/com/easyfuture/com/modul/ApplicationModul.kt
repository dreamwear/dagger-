package com.easyfuture.com.modul

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.easyfuture.com.app.AopApplication
import com.easyfuture.com.datasource.*
import com.easyfuture.com.logger.LoggerFactory
import com.easyfuture.com.net.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Singleton


//全局context
@Module
@Singleton
class  ApplicationModul( var  aopApplications: AopApplication){
    val APPFILE = "app_info"
    //全局Context
    @Singleton
    @Provides
    fun   providerApplication(aopApplications: AopApplication) :Context {
        return  aopApplications}
    //shared
    @Singleton
    @Provides
    fun   providerSharePrefrens(context: Context) =context.getSharedPreferences(APPFILE, Context.MODE_PRIVATE)

    //真是网络请求
    @Singleton
    @Provides
   fun   providerHttpRequst(apiService: ApiService):HttpRequst = RetofitHttpRequst(apiService)


    //远程服务
    @Singleton
    @Provides
    fun   providerBaseHttpDataSource(httpRequst: HttpRequst):BaseRemoveDataSource = HttpDataSource(httpRequst)


    //share数据存储管理
    @Singleton
    @Provides
    fun  provideBaseSharePreDataSource(sharedPreferences: SharedPreferences):BaseShareprefSource = SharePrefrenDataSource(sharedPreferences)
    //数据仓库
    @Provides
    @Singleton
    fun   provideReposity(baseRemoveDataSource: BaseRemoveDataSource  ,baseShareprefSource: BaseShareprefSource) = RepositoryFactory(baseRemoveDataSource,baseShareprefSource)



    @Singleton
    @Provides
    fun   providerInterceptor():NetInterrupter = NetInterrupter()

    inner class   NetInterrupter : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            var request : Request = chain!!.request();
            return   chain!!.proceed(request)


        }

    }



}