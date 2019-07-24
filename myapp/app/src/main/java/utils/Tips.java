package utils;

import android.content.Context;
import android.widget.Toast;

public class Tips {
    public static void toast(Context context,String infor){
        Toast.makeText(context,infor,Toast.LENGTH_SHORT).show();

    }
}
