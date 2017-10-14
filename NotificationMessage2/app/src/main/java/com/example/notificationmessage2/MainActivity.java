package com.example.notificationmessage2;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final int NOTIFICATION_FLAG = 1;
    //private static final int NOTIFICATION_FLAG=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.btnDefault);
        Button button2 = (Button) findViewById(R.id.btnDefaultAPI11);
        Button button3 = (Button) findViewById(R.id.btnDefaultAPI16);
        Button button4 = (Button) findViewById(R.id.btnSelf);
        Button button5 = (Button) findViewById(R.id.btnClear);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        switch (v.getId()){
            case R.id.btnDefault:
                // 创建一个PendingIntent，和Intent类似，不同的是由于不是马上调用，需要在下拉状态条出发的activity，所以采用的是PendingIntent,即点击Notification跳转启动到哪个Activity
                PendingIntent pendingIntent = PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);
                /*Notification notification = new Notification();
                notification.icon = R.mipmap.ic_launcher;
                notification.tickerText = "TickerText:你有新短信，请注意查收!";
                notification.when = System.currentTimeMillis();
                notification.setLatestEventInfo(this,"Notification Title","这是一条通知信息",pendingIntent);//这里不使用的原因是谷歌把这个使用方法已经废弃了，现在使用的是Builder方法。
                notification.number=1;
                */
                Notification notification = new Notification.Builder(this)
                        .setContentTitle("这是通知标题")
                        .setContentText("这是通知内容")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker("你有新通知，请注意查看")
                        .setContentIntent(pendingIntent)
                        .setNumber(1)
                        .getNotification();
                // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加一个提示
                manager.notify(NOTIFICATION_FLAG,notification);
                break;
            case R.id.btnDefaultAPI11:
                PendingIntent pendingIntent2 = PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);
                Notification notification2 = new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker("你有新短消息，请查收!")//这个是当新短信来临的时候，最上面弹出来的提醒语句。
                        .setContentTitle("通知标题")
                        .setContentText("这是通知消息的内容")
                        .setContentIntent(pendingIntent2)
                        .setNumber(1)
                        .getNotification();
                notification2.flags |= Notification.FLAG_AUTO_CANCEL;
                manager.notify(NOTIFICATION_FLAG,notification2);
                break;
            case R.id.btnDefaultAPI16:
            PendingIntent pendingIntent3 = PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),0);
            Notification notification3 = new Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("你有新短消息，请查收!")
                    .setContentTitle("通知标题")
                    .setContentText("这是通知消息的内容")
                    .setContentIntent(pendingIntent3)
                    .setNumber(1)
                    .build();
            notification3.flags |= Notification.FLAG_AUTO_CANCEL;
            manager.notify(NOTIFICATION_FLAG,notification3);
            break;
            case R.id.btnSelf:
                Notification myNotify = new Notification();//这个地方和下面的设置图标好像不一样，因为这个是方法里面必须写上的三个属性，除了flags以外3个属性都是Notification括号里面的必须实现的方法，这里只不过写在下面了。
                myNotify.icon = R.mipmap.ic_launcher;//通知图标
                myNotify.tickerText = "你有短消息，请注意查收!";
                myNotify.when = System.currentTimeMillis();//显示当前系统的时间(这里的这句话并没有起作用，因为自定义按钮点击了以后后面并没有显示系统时间)
                myNotify.flags = Notification.FLAG_NO_CLEAR;//不能够自动清除(自定义模式好像只能使用这个)

                RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.my_notification);
                remoteViews.setTextViewText(R.id.text_title,"这是标题");//自定义通知的标题名字
                remoteViews.setTextViewText(R.id.text_content,"这是自定义短消息的内容");//因为自定义的xml文件里面就设置了一个edittext，所以说当你点击自定义的时候就只显示一个文本。
                //remoteViews.setImageViewResource(R.id.icon,R.mipmap.ic_launcher);//自定义标题的头像,因为xml布局文件里已经设置了，这里就不需要了(但是不知道上面设置的那个图标是用在什么地方了)
                myNotify.contentView = remoteViews;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                PendingIntent contentIntent = PendingIntent.getActivity(this,1,intent,0);
                myNotify.contentIntent = contentIntent;
                manager.notify(NOTIFICATION_FLAG,myNotify);
                break;
            case R.id.btnClear:
                //清除ID为NOTIFICATION_FLAG的通知
                manager.cancel(NOTIFICATION_FLAG);
                //清除所有通知
                //manager.cancelAll();
                break;
            default:
                break;

        }
    }
}
