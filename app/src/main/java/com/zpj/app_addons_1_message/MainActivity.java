package com.zpj.app_addons_1_message;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.HandlerThread;

public class MainActivity extends AppCompatActivity {

    private Button mButton = null;
    private static final String TAG = "MessageTest";
    private int ButtonCount = 0;
    private Thread myThread;
    private MyThread myThread2;
    private Handler mHandler;
    private Handler mHandler3;
    private int mMessageCount = 0;
    private HandlerThread myThread3;

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
                    Thread.sleep(3000);/*休眠3000毫秒 免得让系统卡死*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyThread extends Thread {/*创建一个新线程*/
        private Looper mLooper;
        @Override
        public void run() {/*复写run方法*/
            super.run();
            Looper.prepare(); /*给子线程添加消息处理机制 Looper.java里*/
            /*---------------------------------------------*/
//            mLooper = Looper.myLooper();
            synchronized (this) {
                mLooper = Looper.myLooper();/*mLooper在这里赋值*/
                notifyAll();/*唤醒进程*/
            }
            /*---------------------------------------------*/
            Looper.loop();
        }

        public Looper getLooper() {
            return Looper.myLooper();
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

                Message msg= new Message();/*这里发消息*/
                mHandler.sendMessage(msg);

                mHandler3.post(new Runnable() {/*用post发送消息*/
                    @Override
                    public void run() {/*当发送一个消息过去最终会导致这里的run方法被调用*/
                        Log.d(TAG, "get Message for myThread3 "+ mMessageCount);
                        mMessageCount++;
                    }
                });
            }
        });

        /*实例化线程*/
        myThread = new Thread(new MyRunnable(), "MessageTestThread");/* 线程; 线程名 */
        myThread.start(); /*启动线程*/

        myThread2 = new MyThread();
        myThread2.start();

        /**/
        /*消息发给线程2里的Looper, 当这个线程收到消息时会调用这个handleMessage来处理*/
        mHandler = new Handler(myThread2.getLooper(), new Handler.Callback(){
            @Override
            public boolean handleMessage(Message msg) {/*这里处理*/
                Log.d(TAG, "get Message "+ mMessageCount);
                mMessageCount++;
                return false;
            }
        });

        /*实例化*/
        myThread3 = new HandlerThread("MessageTestThread3");
        myThread3.start();
        mHandler3 = new Handler(myThread3.getLooper());
    }
}
