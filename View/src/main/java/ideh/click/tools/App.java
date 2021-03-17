package ideh.click.tools;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.res.ResourcesCompat;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.click.R;

import es.dmoral.toasty.Toasty;


/**
 * Created by Sadegh-PC on 30/04/2017.
 */

public class App extends MultiDexApplication {
    private static App enableMultiDex;
    public static Context context;


    public App() {
        enableMultiDex = this;
    }

    public static App getEnableMultiDexApp() {
        return enableMultiDex;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        context = getApplicationContext();

        Toasty.Config.getInstance()
                .setTextColor(getResources().getColor(R.color.white))
                .setInfoColor(getResources().getColor(R.color.colorPrimary))
                .setToastTypeface(ResourcesCompat.getFont(context, R.font.iy_regular))
                .setTextSize(12)
                .apply();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
