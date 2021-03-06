package vegetaisnotavegetable.spidertask3;


import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.os.Handler;




public class WebTimer extends ActionBarActivity {

    static Handler handler , handler1;
    Thread thread , thread1;
    public static String TAG="TAG";
    static int i;
    static String string;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_timer);
        final TextView timerval = (TextView) findViewById(R.id.timervalue);
        final TextView webval = (TextView)findViewById(R.id.webvalue);
        button = (Button)findViewById(R.id.exitbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        try
        {
            thread1 = new Thread(new thread1ng());
            thread1.start();
            handler1 = new Handler()
            {
                Runnable runnable;
                @Override
                public void handleMessage(Message msg) {


                    timerval.setText("" + msg.arg1);
                    if(msg.arg1==0)
                    {

                        thread1.interrupt();
                        Log.d(TAG,""+thread1.isInterrupted());
                        try {
                            spidertask task = new spidertask();
                            string = task.execute().get();
                            webval.setText(string);
                            i = string.length() - 1;
                            thread = new Thread(new mythread());
                            thread.start();
                            handler = new Handler()
                            {
                                @Override
                                public void handleMessage(Message msg) {
                                    timerval.setText("" + msg.arg1);
                                }
                            };

                        } catch (Exception e) {}
                    }

                }
            };
        }catch (Exception e){}



    }

    public class thread1ng implements Runnable
    {
       @Override
       public void run()
       {   while(!Thread.currentThread().isInterrupted())
          {
             for(int k=10;k>=0;k--)
             {
                Message message = Message.obtain();
                message.arg1=k;
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {return;}
                handler1.sendMessage(message);

             }
          }
       }
    }

    public static class mythread implements Runnable
    {
        @Override
        public void run() {
            for(int j=Character.getNumericValue(string.charAt(i));j>=0;j--)
            {   Log.d(TAG,""+j);
                Message message = Message.obtain();
                message.arg1=j;
                if(j==0)
                {
                    i--;
                    j=Character.getNumericValue(string.charAt(i));
                    j++;
                }
                if(i==0)
                    i=string.length()-1;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
                handler.sendMessage(message);
            }

        }
    }

    public static class spidertask extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... params) {
            String response ="";
            String S = "http://timergen.ngrok.io/timegen.php";
            //The Above link is hosted from my system (as a server) , so expect app to crash when my system is not online :P
            InputStream inputStream = null;
            try {
                URL url = new URL(S);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    inputStream = httpURLConnection.getInputStream();
                    Log.d(TAG, inputStream.toString());
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
                    String t ="";
                    while ((t = buffer.readLine()) != null) {
                        response += t;
                        Log.d(TAG, response);
                    }
                } catch (IOException e) {}
            } catch (MalformedURLException e) {}
            return response;
        }
    }
}
