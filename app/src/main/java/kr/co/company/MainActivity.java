///////////////음악 재생하기//////////
package kr.co.company;
//여러 import 정의
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kr.co.company.media.R;

//메인 클래스 정의
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MusicServiceTest";
    Button start, stop;  //버튼 사용하기 위해 명시

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //xml과 연결
        start = (Button) findViewById(R.id.start); //start 버튼 설정
        stop = (Button) findViewById(R.id.stop);   //stop 버튼 설정
        start.setOnClickListener(this);    //클릭 이벤트를 위해
        stop.setOnClickListener(this);     //클릭 이벤트를 위해
    }
    //클릭 이벤트 함수 정의
    public void onClick(View src) {
        //스위치문 사용
        switch (src.getId()) {
            case R.id.start:  //start 버튼을 누르면
                Log.d(TAG, "onClick()start");
                startService(new Intent(this, MusicService.class)); //start서비스
                break;

            case R.id.stop: //stop 버튼을 누르면
                Log.d(TAG, "onClick()stop");
                stopService(new Intent(this, MusicService.class)); //stop서비스
                break;
        }
    }
}       //kr.co.company.MusicService 클래스 정의
class MusicService extends Service {
    private static final String TAG = "kr.co.company.MusicService";
    MediaPlayer player;  //미디어플레이어 사용하기 위해 명시
    //service를 사용하면 onBind함수가 필요하다
    public IBinder onBind(Intent intent) {
        return null;
    }
    //onCreate 함수 정의
    public void onCreate(){
        Log.d(TAG, "onCreate()");
        player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(false);
    }
    //onDestroy 함수 정의
    public void onDestroy(){
        //메시지 출력
        Toast.makeText(this,"Music Service가 중지 되었습니다.", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
        player.stop();
    }
    //onStartCommand 함수 정의
    public int onStartCommand(Intent intent, int flags, int starId){
        //메시지 출력
        Toast.makeText(this, "Music Service가 시작되었습니다.", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onStart()");
        player.start();
        return super.onStartCommand(intent, flags, starId);
    }
}
