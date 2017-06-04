package com.zpj.app_addons_1_message;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton = null;
    private static final String TAG = "MessageTest";
    private int ButtonCount = 0;
    private Thread myThread;

    class MyRunnable implements Runnable {
//        long minPrime;
//        MyRunnable(long minPrime) {
//            this.minPrime = minPrime;
//        }

        public void run() {
            // compute primes larger than minPrime
            int count = 0;
            for (;;)
            {
                Log.d(TAG, "MyThread "+count);
                count++;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Log.d(TAG, "Send Message "+ ButtonCount);
                ButtonCount++;
            }
        });

        myThread = new Thread(new MyRunnable(), "MessageTestThread");/* 线程; 线程名 */
        myThread.start(); /*启动线程*/

    }
}
