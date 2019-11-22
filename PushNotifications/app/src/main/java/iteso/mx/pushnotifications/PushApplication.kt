package iteso.mx.pushnotifications

import android.app.Application
import com.parse.Parse

class PushApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Parse.initialize(Parse.Configuration.Builder(this)
            .applicationId("k3p2Pk04BrYsjjh3SlasJWqRqR5brgM0oWOoj3wF")
            .clientKey("ZvBsoEEMSUt52IYPMVuzfTXHivxmA3ToqtkN4rGm")
            .server("https://parseapi.back4app.com/")
            .build())
    }
}