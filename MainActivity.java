package com.kpw.demo;

import android.content.Context;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //视频相关
    private PlayerView player;
    private PowerManager.WakeLock wakeLock;
//    private String url = "rtmp://14.152.83.103:58828/NewMGMlive/YT01";

    private String url = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new LifeRecycleDemo());
        Mygrid mygrid1 = findViewById(R.id.grid1);
        Mygrid mygrid2 = findViewById(R.id.grid2);
        Mygrid mygrid3 = findViewById(R.id.grid3);
//        mygrid.setBitmap(R.drawable.ic_launcher);
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 135; i++) {
            arrayList.add(R.drawable.d99);
        }
        mygrid1.setbitmapList(arrayList);

        ArrayList<Integer> arrayList1 = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            arrayList1.add(R.drawable.ic_launcher);
        }
        mygrid2.setbitmapList(arrayList1);

        ArrayList<Integer> arrayList2 = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            arrayList2.add(R.drawable.ic_launcher);
        }
        mygrid3.setbitmapList(arrayList2);
//
//
//        Mygrid gri2=findViewById(R.id.grid2);
//        ArrayList<Integer> arrayList2=new ArrayList<>();
//        for (int i=0;i<56;i++){
//            arrayList2.add(R.drawable.d99);
//        }
//
//        gri2.setbitmapList(arrayList2);

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        player = new PlayerView(this)
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .hideSteam(true)
                .setForbidDoulbeUp(true)
                .hideCenterPlayer(true)
                .hideControlPanl(true);

        player.setPlaySource(url)
                .startPlay();
    }
}
