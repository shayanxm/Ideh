package ideh.click.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.click.R;
import com.click.pc.Constant;
import com.click.pc.IdeaListModel;
import com.click.pc.ListModel;
import com.click.presenter.idea_list.IdeaListContract;
import com.click.presenter.idea_list.IdeaListPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import ideh.click.activity.IdeaDetailActivity;
import ideh.click.activity.LoginActivity;
import ideh.click.tools.EndlessRecyclerViewScrollListener;
import ideh.click.view.loading_button.KS_LoadingButton;


public class HomeFragment extends Fragment implements IdeaListContract.IdeaListView {
    private Unbinder unbinder;
    private boolean isFilter = false;
    private List<ListModel> filter = new ArrayList<>();
    private FragmentActivity context;
    private IdeaListContract.IdeaListPresenter presenter;

    private int currentPage = 1;
    private EndlessRecyclerViewScrollListener scrollListener;

    private IdeaListAdapter adapter;
    private List<IdeaListModel> list = new ArrayList<>();

    private List<String> genreList = new ArrayList<>();


    @BindView(R.id.llLoading)
    LinearLayout llLoading;

    @BindView(R.id.prbLoading)
    ProgressBar prbLoading;

    @BindView(R.id.btnRetry)
    Button btnRetry;

    @BindView(R.id.llData)
    CoordinatorLayout llData;

    @BindView(R.id.rcvResult)
    RecyclerView rcvResult;

    @BindView(R.id.btnMore)
    KS_LoadingButton btnMore;


    public HomeFragment() {
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        currentPage = 1;
        filter.clear();

        unbinder = ButterKnife.bind(this, view);
        presenter = new IdeaListPresenterImpl(context, this);
        presenter.getIdeaList(currentPage, filter);

        LinearLayoutManager layoutManager = (LinearLayoutManager) rcvResult.getLayoutManager();
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                currentPage = page + 1;
                btnMore.startLoading();
                btnMore.setVisibility(View.VISIBLE);

                presenter.getIdeaList(currentPage, filter);
            }
        };

        list.clear();
        adapter = new IdeaListAdapter();
        rcvResult.setAdapter(adapter);
        rcvResult.addOnScrollListener(scrollListener);


        initIntentFilter();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @OnClick({R.id.btnRetry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRetry:
                llData.setVisibility(View.GONE);
                llLoading.setVisibility(View.VISIBLE);
                presenter.getIdeaList(currentPage, filter);
                break;
        }
    }

    @Override
    public void getIdeaList(List<IdeaListModel> model, int total) {
        btnMore.stopLoading();
        btnMore.setVisibility(View.INVISIBLE);

        if (currentPage <= 1) {
            list.clear();
        }

        if ((this.list.size() + model.size()) <= total) {
            this.list.addAll(model);
            adapter.notifyDataSetChanged();
        }

        llLoading.setVisibility(View.GONE);
        llData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkError() {
        btnRetry.setVisibility(View.VISIBLE);
        prbLoading.setVisibility(View.GONE);

        Toasty.warning(context, getString(R.string.network_error), Toast.LENGTH_LONG, false).show();
    }

    @Override
    public void naToLogin(String message) {
        Intent loginIntent = new Intent(context, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(loginIntent);
    }

    @Override
    public void onError(String message) {
        btnRetry.setVisibility(View.VISIBLE);
        prbLoading.setVisibility(View.GONE);

        Toasty.error(context, message, Toast.LENGTH_LONG, false).show();
    }


    private class IdeaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new itemHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rcv_idea_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            IdeaListModel model = list.get(position);

            itemHolder itemHolder = (IdeaListAdapter.itemHolder) holder;
            itemHolder.setData(model, position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class itemHolder extends RecyclerView.ViewHolder {
            CardView item;
            TextView lblId;
            TextView lblTitle;
            TextView lblGenre;
            TextView lblKeyWord;


            itemHolder(View view) {
                super(view);
                this.item = view.findViewById(R.id.item);
                this.lblId = view.findViewById(R.id.lblId);
                this.lblTitle = view.findViewById(R.id.lblTitle);
                this.lblGenre = view.findViewById(R.id.lblGenre);
                this.lblKeyWord = view.findViewById(R.id.lblKeyWord);
            }

            void setData(IdeaListModel model, int position) {
                lblId.setText(String.valueOf(model.getId()));

                lblTitle.setText(model.getTitle());

                String[] genreArray = model.getGenreArray();
                String genre = genreArray[0];
                lblGenre.setText(genre);

                String[] keyWordArray = model.getKeyWordArray();
                StringBuilder keyWord = new StringBuilder();
                for (int i = 0; i < keyWordArray.length; i++) {
                    if (i > 1)
                        continue;

                    String item = keyWordArray[i];
                    keyWord.append(item).append(" ");
                }

                lblKeyWord.setText(keyWord.toString().trim());

                item.setOnClickListener(view -> {
                    Intent intent = new Intent(context, IdeaDetailActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString(Constant._TITLE, model.getTitle());
                    bundle.putString(Constant._DESCRIPTION, model.getDescription());

                    intent.putExtras(bundle);

                    startActivity(intent);

                });
            }

            void addKeyWord(LinearLayout body, String keyWord) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.tag_view, null);

                TextView lblTag = view.findViewById(R.id.lblName);
                ImageView imgDelete = view.findViewById(R.id.btnRemove);

                imgDelete.setVisibility(View.GONE);

                lblTag.setText(keyWord);

                body.addView(view);
            }
        }
    }

    private void initIntentFilter() {
        /** register broadcast for update activity title from fragments */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("filterList");
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            filter = (List<ListModel>) intent.getSerializableExtra("filter");
            currentPage = 1;

            isFilter = filter.size() > 0;

            if (llData != null )
                llData.setVisibility(View.GONE);

            llLoading.setVisibility(View.VISIBLE);
            presenter.getIdeaList(currentPage, filter);
        }
    };
}