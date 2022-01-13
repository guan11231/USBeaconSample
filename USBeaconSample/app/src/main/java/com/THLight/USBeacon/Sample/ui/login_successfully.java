package com.THLight.USBeacon.Sample.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.THLight.USBeacon.Sample.R;

public class login_successfully extends Activity {

    private String CHANNEL_ID = "Coder";
    private String get_user_name = "";
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_successfully);
        layout = (LinearLayout) findViewById(R.id.layout);
        TextView user_name = (TextView) findViewById(R.id.user_name);
        get_user_name = (String)getIntent().getExtras().getString("user");
        user_name.setText(get_user_name);

        Button btn_to_game = (Button) findViewById(R.id.btn_to_game);
        btn_to_game.setOnClickListener(onClickListener);
        Button btn_to_roll_call = (Button) findViewById(R.id.btn_to_roll_call);
        btn_to_roll_call.setOnClickListener(onClickListener);
        Button btn_to_search_today = (Button) findViewById(R.id.btn_to_search_today);
        btn_to_search_today.setOnClickListener(onClickListener);
        Button btn_to_search_user = (Button) findViewById(R.id.btn_to_search_user);
        btn_to_search_user.setOnClickListener(onClickListener);
        /*Bundle bundle = new Bundle();
        bundle.putString("user",get_user_name);
        intent.putExtras(bundle);*/
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_to_game:
                    bToGame();
                    break;
                case R.id.btn_to_roll_call:
                    bRollCall();
                    break;
                case R.id.btn_to_search_today:
                    bToSearchD();
                    break;
                case R.id.btn_to_search_user:
                    bToSearchU();
                    break;
                default:
                    break;
            }
        }
    };

    public void bToGame() {
        Intent intent = new Intent(login_successfully.this, com.THLight.USBeacon.Sample.ui.GameActivity9.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Bundle bundle = new Bundle();
        bundle.putString("user", get_user_name);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
            /*new Thread(new Runnable() {
                @Override
                public void run(){
                    MysqlCon con = new MysqlCon();
                    con.run();
                    //con.setFlag("computer",get_user_name);
                    con.roll_call(get_user_name);
                }
            }).start();*/
    }

    public void bRollCall(){
        //if(MainActivity.isConnect == true) {
        new Thread(new Runnable() {
            @Override
            public void run(){
                MysqlCon con = new MysqlCon();
                con.run();
                //con.setFlag("computer",get_user_name);
                /**建立要嵌入在通知裡的介面*/
                RemoteViews view = new RemoteViews(getPackageName(),R.layout.custom_notification);

                /**初始化Intent，攔截點擊事件*/

                Intent intent = new Intent(login_successfully.this, NotificationReceiver.class);
                Bundle bundle = new Bundle();
                bundle.putString("user", get_user_name);
                intent.putExtras(bundle);

                /**設置通知內"Hi"這個按鈕的點擊事件(以Intent的Action傳送標籤，標籤為Hi)*/
                intent.setAction("roll_call");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(login_successfully.this
                        ,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

                /**設置通知內"Close"這個按鈕的點擊事件(以Intent的Action傳送標籤，標籤為Close)*/
                    /*intent.setAction("Close");
                    PendingIntent close = PendingIntent.getBroadcast(login_successfully.this
                            ,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);*/

                /**設置通知內的控件要做的事*/
                /*設置標題*/
                view.setTextViewText(R.id.textView_Title,"簽到確認通知");
                /*設置圖片*/
                view.setImageViewResource(
                        R.id.imageView_Icon,R.drawable.ic_baseline_roll_call_finished_24);

                view.setTextViewText(R.id.course,"課程:" + con.getClassName());
                view.setTextViewText(R.id.room,"教室:" + con.getClassroom());
                view.setTextViewText(R.id.sid,"學號:" + con.getAccount(get_user_name));

                /*設置"roll_call"按鈕點擊事件(綁pendingIntent)*/
                view.setOnClickPendingIntent(R.id.button_roll_call,pendingIntent);
                /*設置"Close"按鈕點擊事件(綁close)*/
                //view.setOnClickPendingIntent(R.id.button_Noti_Close,close);

                /**建置通知欄位的內容*/
                NotificationCompat.Builder builder
                        = new NotificationCompat.Builder(login_successfully.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_roll_call_finished_24)
                        .setContent(view)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE);
                NotificationManagerCompat notificationManagerCompat
                        = NotificationManagerCompat.from(login_successfully.this);
                /**發出通知*/
                notificationManagerCompat.notify(1,builder.build());
                layout.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "通知已發送", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
            /*NotificationCompat.Builder builder
                    = new NotificationCompat.Builder(login_successfully.this,CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_roll_call_finished_24) //設置小icon
                    .setContentTitle("您已完成點名囉！")                         //設置標題
                    //.setContentText("跟你打個招呼啊～")                        //設置內容
                    .setAutoCancel(true)                                        //設置是否點到後自動消失
                    .setPriority(NotificationCompat.PRIORITY_HIGH)              //設置通知等級
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE);          //設置通知類型

            //發出通知
            NotificationManagerCompat notificationManagerCompat
                    = NotificationManagerCompat.from(login_successfully.this);
            notificationManagerCompat.notify(1,builder.build());*/
    }

    public void bToSearchD() {
        Intent intent = new Intent();
        intent.setClass(login_successfully.this, todayAttendantInquire.class);
        Bundle bundle = new Bundle();
        bundle.putString("user", get_user_name);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void bToSearchU() {
        Intent intent = new Intent();
        intent.setClass(login_successfully.this, Attendant.class);
        Bundle bundle = new Bundle();bundle.putString("user", get_user_name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /*
    // Disable back button
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }*/

}

