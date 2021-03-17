package ideh.click.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.click.R;
import com.click.pc.Constant;
import com.click.pc.GenreModel;
import com.click.pc.ListModel;
import com.click.presenter.genre.GenreContract;
import com.click.presenter.genre.GenrePresenterImpl;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import ideh.click.fragment.HomeFragment;
import ideh.click.fragment.NewIdeaFragment;
import ideh.click.fragment.UserListFragment;
import ideh.click.tools.ConfirmDialog;
import ideh.click.view.EditTextSelectable.ClickToSelectEditText;
import ideh.click.view.EditTextSelectable.Listable;
import ideh.click.view.MultiSpinner;

public class MainActivity extends AppCompatActivity implements GenreContract.GenreView {
    private IntentFilter intentFilter;
    private List<ListModel> list = new ArrayList<>();
    private boolean isShowFilter = false;
    private GenreContract.GenrePresenter presenter;

    @BindView(R.id.lblTitle)
    TextView lblTitle;

    @BindView(R.id.btnFilter)
    ImageView btnFilter;

    @BindView(R.id.ddlGenre)
    MultiSpinner ddlGenre;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        presenter = new GenrePresenterImpl(this, this);
        presenter.getGenreList();

        setView(Constant.DRAWER_MENU.HOME);
        bottom_navigation.setSelectedItemId(R.id.action_home);
        bottom_navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    setView(Constant.DRAWER_MENU.HOME);
                    break;
                case R.id.action_new_idea:
                    setView(Constant.DRAWER_MENU.NEW_IDEA);
                    break;
                case R.id.action_user:
                    setView(Constant.DRAWER_MENU.USER_LIST);
                    break;
            }
            return true;
        });

        initIntentFilter();
    }

    private void initIntentFilter() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("update");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @OnClick({R.id.btnFilter, R.id.btnExit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFilter:
                if (isShowFilter) {
                    lblTitle.setVisibility(View.VISIBLE);
                    btnFilter.setImageResource(R.drawable.ic_filter);
                    ddlGenre.setVisibility(View.GONE);
                    ddlGenre.clearSelection();
                    isShowFilter = false;

                    for (ListModel listModel : list) {
                        listModel.setSelected(false);
                    }

                    Intent intent = new Intent();
                    intent.setAction("filterList");
                    intent.putExtra("filter", (Serializable) list);

                    sendBroadcast(intent);
                } else {
                    lblTitle.setVisibility(View.GONE);
                    btnFilter.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_black));
                    ddlGenre.setVisibility(View.VISIBLE);
                    ddlGenre.clearSelection();
                    isShowFilter = true;
                }
                break;
            case R.id.btnExit:
                ConfirmDialog.confirmDialog(MainActivity.this, "", "آیا برای خروج اطمینان دارید؟", new ConfirmDialog.Callback() {
                    @Override
                    public void onPositiveButtonClick(DialogInterface dialogInterface) {
                        presenter.logout();
                        dialogInterface.dismiss();
                    }

                    @Override
                    public void onNegativeButtonClick(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                    }
                });
                break;
        }
    }

    private void setView(Constant.DRAWER_MENU menu) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        //ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);

        lblTitle.setVisibility(View.VISIBLE);
        btnFilter.setVisibility(View.GONE);
        btnFilter.setImageResource(R.drawable.ic_filter);
        ddlGenre.setVisibility(View.GONE);
        isShowFilter = false;
        for (ListModel listModel : list) {
            listModel.setSelected(false);
        }

        switch (menu) {
            case HOME:
                btnFilter.setVisibility(View.VISIBLE);
                lblTitle.setText("لیست ایده ها");
                fragment = HomeFragment.newInstance();

                String mainFragmentName = fragment.getClass().getName();

                boolean mainFragmentPopped = manager.popBackStackImmediate(mainFragmentName, 0);

                if (!mainFragmentPopped && manager.findFragmentByTag(mainFragmentName) == null) {
                    ft.replace(R.id.container, fragment, mainFragmentName);
                    ft.addToBackStack(mainFragmentName);
                }
                break;
            case NEW_IDEA:
                lblTitle.setText("ثبت ایده جدید");
                fragment = NewIdeaFragment.newInstance();

                String myRequestListFragmentListFragmentName = fragment.getClass().getName();

                boolean myRequestListFragmentListFragmentPopped = manager.popBackStackImmediate(myRequestListFragmentListFragmentName, 0);

                if (!myRequestListFragmentListFragmentPopped && manager.findFragmentByTag(myRequestListFragmentListFragmentName) == null) {
                    ft.replace(R.id.container, fragment, myRequestListFragmentListFragmentName);
                    ft.addToBackStack(myRequestListFragmentListFragmentName);
                }
                break;
            case USER_LIST:
                lblTitle.setText("لیست کاربران");
                fragment = UserListFragment.newInstance();

                String myJobListFragmentName = fragment.getClass().getName();

                boolean myJobListFragmentPopped = manager.popBackStackImmediate(myJobListFragmentName, 0);

                if (!myJobListFragmentPopped && manager.findFragmentByTag(myJobListFragmentName) == null) {
                    ft.replace(R.id.container, fragment, myJobListFragmentName);
                    ft.addToBackStack(myJobListFragmentName);
                }
                break;
        }

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if (fragment != null && (fragment instanceof HomeFragment || fragment instanceof UserListFragment ||
                fragment instanceof NewIdeaFragment)) {

            if (Constant.BACK_PRESSED + 2000 > System.currentTimeMillis()) {
                finish();
            } else
                Toasty.info(MainActivity.this, getString(R.string.back_button_pressed), Toast.LENGTH_SHORT, false).show();

            Constant.BACK_PRESSED = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void getGenreList(GenreModel data) {
        list = new ArrayList<>();
        for (String item : data.getGenere()) {
            list.add(new ListModel(item, false));
        }

        ddlGenre.setItems(list, "ژانر را انتخاب نمائید", selected -> {
            Intent intent = new Intent();
            intent.setAction("filterList");
            intent.putExtra("filter", (Serializable) list);

            sendBroadcast(intent);
        });
    }

    @Override
    public void onNetworkError() {
        Toasty.warning(MainActivity.this, getString(R.string.network_error), Toast.LENGTH_LONG, false).show();
    }

    @Override
    public void naToLogin(String message) {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(loginIntent);
    }

    @Override
    public void onError(String message) {
        Toasty.error(MainActivity.this, message, Toast.LENGTH_LONG, false).show();
    }

    private class TypeModel implements Listable {
        private int id;
        private String title;
        private String code;

        public TypeModel(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public TypeModel(int id, String title, String code) {
            this.id = id;
            this.title = title;
            this.code = code;
        }

        public TypeModel(String title) {
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String getLabel() {
            return getTitle();
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            bottom_navigation.setSelectedItemId(R.id.action_home);
        }
    };
}