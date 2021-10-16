package com.example.firestoretrail;

import android.os.Handler;


class utils {
        public interface DelayCallback{
            void afterDelay();
        }

        public static void delay(int secs, final DelayCallback delayCallback){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    delayCallback.afterDelay();
                }
            }, secs * 10); // afterDelay will be executed after (secs*1000) milliseconds.
        }
    }

