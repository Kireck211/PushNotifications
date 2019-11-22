package iteso.mx.pushnotifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.parse.ParseCloud
import com.parse.ParseException
import com.parse.FunctionCallback
import com.parse.ParseInstallation





class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var button: Button
    private lateinit var activityNotificationButton: Button

    companion object {
        const val CHANNEL_ID = "AndroidCourse"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.activity_main_button)
        activityNotificationButton = findViewById(R.id.activity_main_button_notification_activity)

        createNotificationChannel()

        button.setOnClickListener(this)

        activityNotificationButton.setOnClickListener(this)


        val channels = arrayListOf<String>("Course")
        val installation = ParseInstallation.getCurrentInstallation()
        installation.put("channels", channels)
        installation.saveInBackground {
            val params = HashMap<String, String>()
            ParseCloud.callFunctionInBackground("pushsample", params,
                FunctionCallback<Any> { value, e ->
                    if (e != null) {
                        Log.d("MainActivityCourse", e.toString())
                    }
                })
        }



    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "mx.iteso.ANDROID",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManger = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManger.createNotificationChannel(notificationChannel)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.activity_main_button -> {
                val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentTitle("Ya es hora de despertarse")
                    .setContentText("Lorem ipsum dolor sit amet consectetur adipiscing elit curabitur, dictumst tincidunt taciti integer est ultricies metus aenean")
                    .setStyle(NotificationCompat.BigTextStyle())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                NotificationManagerCompat.from(this).notify(10, builder.build())
            }
            R.id.activity_main_button_notification_activity -> {
                val intent = Intent(this, ActivityPushNotification::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

                val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentTitle("Ya es hora de despertarse")
                    .setContentText("Abre la actividad de ActivityPush")
                    .setStyle(NotificationCompat.BigTextStyle())
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                NotificationManagerCompat.from(this).notify(10, builder.build())
            }
        }
    }
}
