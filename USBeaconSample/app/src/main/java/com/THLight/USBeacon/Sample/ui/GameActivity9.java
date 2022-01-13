package com.THLight.USBeacon.Sample.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Typeface;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.THLight.USBeacon.Sample.R;
import com.THLight.USBeacon.Sample.service.Cards;
import com.THLight.USBeacon.Sample.service.Sound;

public class GameActivity9 extends Activity{

    private final int N = 3;
    String get_user_name;
    Cards cards;
    private ImageButton[][] button;
    private final int[][] BUTTON_ID = {{R.id.b900, R.id.b901, R.id.b902},
            {R.id.b910, R.id.b911, R.id.b912},
            {R.id.b920, R.id.b921, R.id.b922}};
    private final int[] PASSWORD_ID = {R.drawable.card905_1, R.drawable.card905_2,
            R.drawable.card906_1, R.drawable.card906_2,
            R.drawable.card907_1, R.drawable.card907_2,
            R.drawable.card908_1, R.drawable.card908_2
    };

    private final int[] CADRS_ID = {R.drawable.card900, R.drawable.card901, R.drawable.card902,
            R.drawable.card903, R.drawable.card904, R.drawable.card905,
            R.drawable.card906, R.drawable.card907, R.drawable.card908};

    private TextView tScore;
    private int numbSteps;
    private TextView tBestScore;
    private int numbBestSteps;

    private Sound sound;
    private int clickSound;
    private int victorySound;
    private int setButtonSound;
    private ImageButton ibSound;
    ;


    private int checkIint = 5;
    private int checkD = 1;



    private boolean check;

    //1220
    String iDBM = "0";
    //1220
    //1222
    private String CHANNEL_ID = "Password";
    //1222

    //1226
    private ArrayList<String> passwordI = new ArrayList<String>();

    //1226
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game9);
        get_user_name = getIntent().getExtras().getString("user");

        Button btButtonCustomDialog = findViewById(R.id.button_CustomButtonDialog);
        btButtonCustomDialog.setOnClickListener(onClickListener);

        button = new ImageButton[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                button[i][j] = this.findViewById(BUTTON_ID[i][j]);
                button[i][j].setOnClickListener(onClickListener);
            }
        ImageButton bNewGame = this.findViewById(R.id.bNewGame);
        bNewGame.setOnClickListener(onClickListener);
        ImageButton bBackMenu = this.findViewById(R.id.bBackMenu);
        bBackMenu.setOnClickListener(onClickListener);
        ImageButton bAbout = this.findViewById(R.id.bAboutGame);
        bAbout.setOnClickListener(onClickListener);

        ibSound = this.findViewById(R.id.bSoundOffOn);
        ibSound.setOnClickListener(onClickListener);


        Typeface digitalFont = Typeface.createFromAsset(this.getAssets(), "font.ttf");
        TextView tPyatnashki = this.findViewById(R.id.tPyatnashki);
        tPyatnashki.setTypeface(digitalFont);
        TextView tSScore = this.findViewById(R.id.tSScore);
        tSScore.setTypeface(digitalFont);
        tScore = this.findViewById(R.id.tScore);
        tScore.setTypeface(digitalFont);
        TextView tBestSScore = this.findViewById(R.id.tBestSScore);
        tBestSScore.setTypeface(digitalFont);
        tBestScore = this.findViewById(R.id.tBestScore);
        tBestScore.setTypeface(digitalFont);

        AssetManager AM = getAssets();
        sound = new Sound(AM);
        clickSound = sound.loadSound("Schelchok.mp3");
        victorySound = sound.loadSound("Victory.mp3");
        setButtonSound = sound.loadSound("Schelchok1.mp3");

        try {
            sound.setCheckSound(getIntent().getExtras().getBoolean("checkSound"));
        } catch (Exception e) {
            sound.setCheckSound(true);
        }

        cards = new Cards(N, N);
        try {
            if (getIntent().getExtras().getInt("keyGame") == 1) {
                continueGame();
                checkFinish();
            } else newGame();
        } catch (Exception e) {
            newGame();
        }
        //1220
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //要做的事情
                iDBM = getSharedPreferences("rssi", MODE_PRIVATE)
                        .getString("rssi1", "");
                //Toast.makeText(GameActivity9.this, "iDBM = "+ Integer.valueOf(iDBM), Toast.LENGTH_SHORT).show();
                TextView dbmT = GameActivity9.this.findViewById(R.id.dbmT);
                dbmT.setText("密碼金鑰： " + iDBM + " ");
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 5000);




            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }



        //1220
        //1226
        passwordI.removeAll(passwordI);
        passwordI.add("410777023");
        passwordI.add("973707");
        passwordI.add("228922");
        passwordI.add("150449");
        //1226
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            if (!check)
                for (int i = 0; i < N; i++)
                    for (int j = 0; j < N; j++)
                        if (v.getId() == BUTTON_ID[i][j])
                            buttonFunction(i, j);

            switch (v.getId()) {
                case R.id.bNewGame:
                    sound.playSound(setButtonSound);
                    newGame();
                    break;
                case R.id.bBackMenu:
                    sound.playSound(setButtonSound);
                    backMenu();
                    break;
                case R.id.bAboutGame:
                    sound.playSound(setButtonSound);
                    aboutOnClick();
                    break;
                case R.id.bSoundOffOn:
                    soundOffOn();
                    sound.playSound(setButtonSound);
                    break;
                case R.id.button_CustomButtonDialog:
                    if(passwordI.size() > 0)
                    {
                        example();
                    }
                    else
                    {
                        findOK();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void buttonFunction(int row, int columb) {
        cards.moveCards(row, columb);
        if (cards.resultMove()) {
            sound.playSound(clickSound);
            numbSteps++;
            showGame();
            checkFinish();
        }
    }

    public void newGame() {
        cards.getNewCards();
        String test = "board = ";
        for (int[] i :cards.getIntBoard())
        {
            for (int j :i)
            {
                test = test + j + ", ";
            }
        }
        //Toast.makeText(GameActivity9.this, test, Toast.LENGTH_SHORT).show();
        numbSteps = 0;
        numbBestSteps = Integer.parseInt(readFile("fbs9"));
        tBestScore.setText(Integer.toString(numbBestSteps));
        //1226
        checkIint = 5;
        passwordI.removeAll(passwordI);
        passwordI.add("410777023");
        passwordI.add("973707");
        passwordI.add("228922");
        passwordI.add("150449");
        //1226
        showGame();
        check = false;
    }

    private void continueGame() {
        String text = getPreferences(MODE_PRIVATE).getString("savePyatnashki", "");
        int k = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                cards.setValueBoard(i, j, Integer.parseInt("" + text.charAt(k) + text.charAt(k + 1)));
                k += 2;
            }

        numbSteps = Integer.parseInt(readFile("fileScore"));
        numbBestSteps = Integer.parseInt(readFile("fbs9"));
        tBestScore.setText(Integer.toString(numbBestSteps));

        showGame();
        check = false;
    }

    public void backMenu() {
        saveValueBoard();
        Intent intent = new Intent(GameActivity9.this, login_successfully.class);
        intent.putExtra("checkSound", sound.getCheckSound());
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Bundle bundle = new Bundle();
        bundle.putString("user", get_user_name);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void saveValueBoard() {
        String text = "";
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (cards.getValueBoard(i, j) < 10)
                    text += "0" + cards.getValueBoard(i, j);
                else text += cards.getValueBoard(i, j);
            }

        getPreferences(MODE_PRIVATE).edit().putString("savePyatnashki", text).apply();
        writeFile(Integer.toString(numbSteps), "fileScore");
        writeFile(Integer.toString(N), "fileN");
    }

    public void aboutOnClick() {
        startActivity(new Intent(GameActivity9.this, login_successfully.class));
    }

    public void showGame() {
        tScore.setText(Integer.toString(numbSteps));

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                int checkI = cards.getValueBoard(i, j);

                button[i][j].setImageResource(CADRS_ID[checkI]);
                button[i][j].setTag(Integer.valueOf(CADRS_ID[checkI]));

                if (checkI > checkIint)
                {
                    button[i][j].setAlpha(0.0f);
                }
                else if (checkI == checkIint)
                {
                    button[i][j].setAlpha(0.0f);
                    checkD = (Integer) button[i][j].getTag();
                }
                else
                {
                    button[i][j].setAlpha(1.0f);
                }
            }
        }

        if (sound.getCheckSound())
            ibSound.setImageResource(R.drawable.soundon);
        else ibSound.setImageResource(R.drawable.soundoff);
    }

    public void checkFinish() {
        if (cards.finished(N, N)) {
            showGame();
            sound.playSound(victorySound);
            Toast.makeText(GameActivity9.this, R.string.you_won, Toast.LENGTH_SHORT).show();
            if ((numbSteps < numbBestSteps) || (numbBestSteps == 0)) {
                writeFile(Integer.toString(numbSteps), "fbs9");
                tBestScore.setText(Integer.toString(numbSteps));
            }
            button[2][2].setImageResource(R.drawable.card900_1);
            check = true;
        }
    }

    public void soundOffOn() {
        sound.setCheckSound(!sound.getCheckSound());
        showGame();
    }

    public void writeFile(String text, String file) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(file, MODE_PRIVATE)));
            bw.write(text);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String file) {
        String text;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(file)));
            text = br.readLine();
        } catch (IOException e) {
            text = "0";
        }
        return text;
    }
/*
    LayoutInflater inflater = getLayoutInflater();
    final View v = inflater.inflate(R.layout.set_custom_dialog_layout_with_button, null);

    public void example(){
        Toast.makeText(this, "fialure", Toast.LENGTH_SHORT).show();
    }!!!!!!!!!!!!!!!!!!到這邊
*/
    Dialog dialog;
    View viewdialog;
    EditText ededed;
    Button buttonCancel, button_ok;
    private void example() {
        int quotient = Integer.valueOf(iDBM) / -10;
        switch(quotient) {
            case 10:
                Toast.makeText(GameActivity9.this, "密碼8_2", Toast.LENGTH_SHORT).show();
                sendNotification(PASSWORD_ID[7]);
            case 9:
                Toast.makeText(GameActivity9.this, "密碼8_1", Toast.LENGTH_SHORT).show();
                sendNotification(PASSWORD_ID[6]);
            case 8:
                Toast.makeText(GameActivity9.this, "密碼7_2", Toast.LENGTH_SHORT).show();
                sendNotification(PASSWORD_ID[5]);
            case 7:
                Toast.makeText(GameActivity9.this, "密碼7_1", Toast.LENGTH_SHORT).show();
                sendNotification(PASSWORD_ID[4]);
                break;
            case 6:
                Toast.makeText(GameActivity9.this, "密碼6_2", Toast.LENGTH_SHORT).show();
                sendNotification(PASSWORD_ID[3]);
                break;
            case 5:
                Toast.makeText(GameActivity9.this, "密碼6_1", Toast.LENGTH_SHORT).show();
                sendNotification(PASSWORD_ID[2]);
                break;
            case 4:
                Toast.makeText(GameActivity9.this, "密碼5_2", Toast.LENGTH_SHORT).show();
                sendNotification(PASSWORD_ID[1]);
                break;
            default:
                Toast.makeText(GameActivity9.this, "密碼5_1", Toast.LENGTH_SHORT).show();
                sendNotification(PASSWORD_ID[0]);
        }

        dialog = new Dialog(GameActivity9.this);
        //這邊是要綁定自定義的dialog.xml
        viewdialog = getLayoutInflater().inflate(R.layout.set_custom_dialog_layout_with_button, null);
        //然後把綁好的xml連接到dialog上面
        dialog.setContentView(viewdialog);
        //因為是自定義的子元件，後面綁定id記得都要綁上面的view
        ededed = viewdialog.findViewById(R.id.ededed);
        button_ok = viewdialog.findViewById(R.id.button_ok);
        buttonCancel = viewdialog.findViewById(R.id.buttonCancel);
        //跟AlterDialog一樣要.show
        dialog.show();

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int passN = 0;
                for( passN = 0; ((passwordI.size() != 0) && passN < passwordI.size()); passN++)
                {
                    if (ededed.getText().toString().equals(passwordI.get(passN))){
                        Toast.makeText(GameActivity9.this, "成功", Toast.LENGTH_SHORT).show();
                        checkIint++;
                        int drawableK = 0;
                        for (int i = 0; i < N; i++)
                        {
                            for (int j = 0; j < N; j++)
                            {
                                drawableK = (Integer) button[i][j].getTag();
                                if(drawableK == checkD)
                                {
                                    button[i][j].animate().alpha(1).setDuration(2000);
                                }
                            }
                        }
                        passwordI.remove(passN);
                        showGame();
                        dialog.dismiss();
                        break;
                    }
                }
                if(passwordI.size() == 0)
                {
                    Toast.makeText(GameActivity9.this, "已解鎖完畢", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else if(passN == passwordI.size()) {
                    Toast.makeText(GameActivity9.this, "密碼錯誤", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    //1222
    private void sendNotification(int PASSWORD_ID) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, GameActivity9.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), PASSWORD_ID);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("PassWord")
                .setContentText("Hello password!")
                .setLargeIcon(bmp)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bmp)
                        .bigLargeIcon(null))
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }
        //1220
    //1226
    private void findOK() {
        Toast.makeText(GameActivity9.this, "已解鎖完畢", Toast.LENGTH_SHORT).show();
    }
    //1226
}