package vegetaisnotavegetable.spidertask3;

import  android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class logclass
{
    public static void logmess(String message)
    {
        Log.d("Spider",message);
    }
    public static void disp(Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }


}
