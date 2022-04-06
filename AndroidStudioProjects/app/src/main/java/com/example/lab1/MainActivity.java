package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab1.activity.DeveloperActivity;
import com.example.lab1.activity.EndGameActivity;
import com.example.lab1.activity.StartGameActivity;
import com.example.lab1.animatorPackage.cross.CrossAnimator;
import com.example.lab1.animatorPackage.cross.CrossAnimatorTask;
import com.example.lab1.animatorPackage.cross.CrossResource;
import com.example.lab1.animatorPackage.frog.FrogAnimator;
import com.example.lab1.animatorPackage.meat.AnimationTask;
import com.example.lab1.animatorPackage.meat.MeatResource;
import com.example.lab1.animatorPackage.meat.MeatAnimator;
import com.example.lab1.db.model.DBPlayerController;
import com.example.lab1.db.view.Player;
import com.example.lab1.enums.ObjectPosition;
import com.example.lab1.fragments.EndGameDialogFragment;
import com.example.lab1.fragments.Switcher;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class MainActivity extends AppCompatActivity implements Switcher {

    private final LinkedList<ImageView> meat_view_list = new LinkedList<>();
    private final LinkedList<ImageView> cross_view_list = new LinkedList<>();
    private DBPlayerController playerController = null;
    private SQLiteDatabase db = null;
    private MeatAnimator m_a = null;
    private FrogAnimator f_a = null;
    private CrossAnimator cr_a = null;
    private boolean isDropping = false;
    private boolean isPlayerExistsFlag = false;
    private String player_name = "";
    private Player cur_player;
    private Date start_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player_name = StartGameActivity.player_name;
        start_date = new Date();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerController = new DBPlayerController(getApplicationContext());
        db = playerController.getWritableDatabase();
        isPlayerExistsFlag = isPlayerExists();
        if(isPlayerExistsFlag){
            TextView best_score = findViewById(R.id.bestScoreText);
            best_score.setText(cur_player.getMax_score());
        }else{
            cur_player = new Player(player_name, "0");
            cur_player.setMax_score("0");
        }
    }

    private boolean isPlayerExists(){
        cur_player = playerController.GetDataByName(db, player_name);
        return (!cur_player.getNickname().equals(""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerController.close();
        db.close();
    }

    private void showEndGameDialog(){
        EndGameDialogFragment dl = new EndGameDialogFragment();
        dl.show(getSupportFragmentManager(), "custom");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        fillMeatList();
        fillCrossList();
        m_a =  new MeatAnimator(R.drawable.meat ,findViewById(R.id.meatOne), findViewById(R.id.satietyNumber));
        f_a = new FrogAnimator(R.drawable.frog, findViewById(R.id.frog_view));
        cr_a = new CrossAnimator(R.drawable.red_cross_ph, findViewById(R.id.cross1));
        m_a.setMeatDefaultY(findViewById(R.id.meatOne).getY());
        m_a.setFrogPointF(f_a.getFrogPointF());
        return true;
    }
    private void fillMeatList(){
        meat_view_list.add(findViewById(R.id.meatOne));
        meat_view_list.add(findViewById(R.id.meatTwo));
        meat_view_list.add(findViewById(R.id.meatThree));
    }
    private void fillCrossList(){
        cross_view_list.add(findViewById(R.id.cross1));
        cross_view_list.add(findViewById(R.id.cross2));
        cross_view_list.add(findViewById(R.id.cross3));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(MainActivity.this, DeveloperActivity.class);
        startActivityForResult(intent, 0);
        return super.onOptionsItemSelected(item);
    }

    private ImageView curr_meat = null;
    private ImageView curr_cross = null;
    private int meat_list_iterator = 0;
    private final Semaphore  sem = new Semaphore(1);
    public void animateMeatDropping(View view) {
        if (!isDropping) {
            isDropping = true;
            runMeatFallCycle();
            runCrossCycle();
        } else {
            isDropping = false;
        }
    }
    private void runAnimThread(ImageView view){
        new Thread(() -> runOnUiThread(new AnimationTask(m_a, new MeatResource(R.drawable.meat, view)))
        ).start();
    }
    private void runCrossAnimThread(ImageView view){
        new Thread(()-> runOnUiThread(new CrossAnimatorTask(cr_a, new CrossResource(R.drawable.red_cross_ph, view)))).start();
    }
    private void runCrossCycle(){
        new Thread(()->{
            while(cr_a.isGameOver()){
                if(m_a.isMeatDropped()){
                    try {
                        curr_cross = cross_view_list.get(cr_a.getFail_counter());
                        runCrossAnimThread(curr_cross);
                        m_a.setMeatDropped(false);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            isDropping = false;
            showEndGameDialog();

        },"CrossCycleThread").start();
    }

    private void runMeatFallCycle(){
        new Thread(() -> {
            while(isDropping){
                try{
                    sem.acquire();
                    curr_meat = meat_view_list.get(meat_list_iterator);
                    runAnimThread(curr_meat);
                    meat_list_iterator = (meat_list_iterator < meat_view_list.size() - 1) ? ++meat_list_iterator : 0;
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    sem.release();
                }
            }
        }).start();
    }
    public void moveButtonRight(View view){
        f_a.runFrogAnimatorRight();
        m_a.setFrogPointF(f_a.getFrogPointF());
        runCrossCycle();
    }


    public void moveButtonLeft(View view){
        f_a.runFrogAnimatorLeft();
        m_a.setFrogPointF(f_a.getFrogPointF());

    }

    @Override
    public void getToActivity() {
        Date date = new Date();
        long mill = (start_date.before(date))?date.getTime()-start_date.getTime():start_date.getTime() - date.getTime();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(mill));
        String time = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":"+ calendar.get(Calendar.SECOND);
        Intent intent = new Intent(MainActivity.this, EndGameActivity.class);
        String score = Integer.toString(m_a.getSatietyIndex());
        cur_player.setScore(score);
        cur_player.setTime(time);
        EndGameActivity.cur_player = cur_player;
        if(Integer.parseInt(cur_player.getMax_score()) < m_a.getSatietyIndex()){
            cur_player.setMax_score(score);
        }
        if(isPlayerExistsFlag){
            playerController.updateData(db, cur_player);
        }else{
            playerController.addData(db, cur_player);
        }
        startActivityForResult(intent, 0);
    }
}