package ideh.click.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.click.R;
import com.click.pc.Constant;
import com.click.pc.DataInfoModel;
import com.click.pc.ListModel;
import com.click.presenter.idea_save.SaveIdeaContract;
import com.click.presenter.idea_save.SaveIdeaPresenterImpl;
import com.google.android.material.textfield.TextInputEditText;
import com.kosarsoft.chipinputlibrary.ChipsInput;
import com.kosarsoft.chipinputlibrary.model.ChipInterface;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import ideh.click.activity.LoginActivity;
import ideh.click.view.CompletionView;
import ideh.click.view.MultiSpinner;
import ideh.click.view.loading_button.KS_LoadingButton;


public class NewIdeaFragment extends Fragment implements SaveIdeaContract.SaveIdeaView {
    private Unbinder unbinder;
    private FragmentActivity context;
    private SaveIdeaContract.SaveIdeaPresenter presenter;

    private List<ChipModel> items = new ArrayList<>();
    private List<ListModel> genreList = new ArrayList<>();
    private List<ListModel> keyWordList = new ArrayList<>();
    private List<ListModel> reviewTitleList = new ArrayList<>();
    private List<ListModel> reviewDescriptionList = new ArrayList<>();


    @BindView(R.id.llLoading)
    LinearLayout llLoading;

    @BindView(R.id.prbLoading)
    ProgressBar prbLoading;

    @BindView(R.id.btnRetry)
    Button btnRetry;

    @BindView(R.id.llData)
    CoordinatorLayout llData;

    @BindView(R.id.txtTitle)
    TextInputEditText txtTitle;

    @BindView(R.id.ddlKeyWord)
    ChipsInput ddlKeyWord;

    @BindView(R.id.txtDescription)
    TextInputEditText txtDescription;

    @BindView(R.id.ddlGenre)
    MultiSpinner ddlGenre;

    @BindView(R.id.ddlReviewTitle)
    MultiSpinner ddlReviewTitle;

    @BindView(R.id.ddlReviewDescription)
    MultiSpinner ddlReviewDescription;

    @BindView(R.id.btnSubmit)
    KS_LoadingButton btnSubmit;


    public NewIdeaFragment() {

    }


    public static NewIdeaFragment newInstance() {
        NewIdeaFragment fragment = new NewIdeaFragment();
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
        View view = inflater.inflate(R.layout.fragment_new_idea, container, false);

        unbinder = ButterKnife.bind(this, view);
        presenter = new SaveIdeaPresenterImpl(context, this);
        presenter.getDataInfo();

        return view;
    }


    @OnClick({R.id.btnSubmit, R.id.btnCategory, R.id.btnRetry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                saveIdea();
                break;
            case R.id.btnCategory:
                break;
            case R.id.btnRetry:
                llData.setVisibility(View.GONE);
                llLoading.setVisibility(View.VISIBLE);
                presenter.getDataInfo();
                break;
        }
    }

    private void saveIdea() {
        String title = txtTitle.getText().toString();
        String description = txtDescription.getText().toString();

        if (title.matches("")) {
            Toasty.warning(context, "عنوان ایده را وارد نمائید", Toast.LENGTH_LONG, false).show();
            txtTitle.requestFocus();
            return;
        }

        if (description.matches("")) {
            Toasty.warning(context, "متن ایده را وارد نمائید", Toast.LENGTH_LONG, false).show();
            txtTitle.requestFocus();
            return;
        }

        List<ListModel> selectedGenreList = new ArrayList<>();
        for (ListModel listModel : genreList) {
            if (listModel.isSelected()) {
                selectedGenreList.add(listModel);
            }
        }

        List<ListModel> selectedKeyWordList = new ArrayList<>();
        for (ChipInterface listModel : ddlKeyWord.getSelectedChipList()) {
            ListModel item = new ListModel();
            item.setTitle(listModel.getLabel());
            selectedKeyWordList.add(item);
        }

        List<ListModel> selectedReviewTitleList = new ArrayList<>();
        for (ListModel listModel : reviewTitleList) {
            if (listModel.isSelected()) {
                selectedReviewTitleList.add(listModel);
            }
        }

        List<ListModel> selectedReviewDescriptionList = new ArrayList<>();
        for (ListModel listModel : reviewDescriptionList) {
            if (listModel.isSelected()) {
                selectedReviewDescriptionList.add(listModel);
            }
        }

        btnSubmit.startLoading();
        presenter.saveIdea(title, description, selectedGenreList, selectedKeyWordList,
                selectedReviewTitleList, selectedReviewDescriptionList);
    }


    @Override
    public void onDetach() {
        super.onDetach();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void getDataInfo(DataInfoModel data) {
        List<String> genreListItem = data.getSubCategoryTitleTow();
        for (String item : genreListItem) {
            ListModel dataModel = new ListModel(item, false);
            this.genreList.add(dataModel);
        }

        List<String> keyWordListItem = data.getKeyWrd();
        for (String item : keyWordListItem) {
            ListModel dataModel = new ListModel(item, false);
            this.keyWordList.add(dataModel);
        }

        List<String> reviewTitleListItem = data.getMenuItem();
        for (int i = 0; i < reviewTitleListItem.size(); i++) {
            String item = reviewTitleListItem.get(i);
            String id = data.getMenuItemNumber().get(i);

            ListModel dataModel = new ListModel(id, item, false);
            this.reviewTitleList.add(dataModel);
        }

        List<String> reviewDescriptionListItem = data.getMenuItem();
        for (int i = 0; i < reviewDescriptionListItem.size(); i++) {
            String item = reviewDescriptionListItem.get(i);
            String id = data.getMenuItemNumber().get(i);

            ListModel dataModel = new ListModel(id, item, false);
            this.reviewDescriptionList.add(dataModel);
        }

        llLoading.setVisibility(View.GONE);
        llData.setVisibility(View.VISIBLE);


        ddlGenre.setItems(this.genreList, "ژانر ایده خود را انتخاب نمائید ", selected -> {
            this.genreList = selected;
        });

        ListModel[] keywordArray = this.keyWordList.toArray(new ListModel[this.keyWordList.size()]);
        for (ListModel listModel : keywordArray) {
            items.add(new ChipModel(listModel.getTitle()));
        }
        ddlKeyWord.setFilterableList(items);

        ddlReviewTitle.setItems(this.reviewTitleList, "حق بازبینی عنوان ایده را انتخاب نمائید ", selected -> {
            this.reviewTitleList = selected;
        });

        ddlReviewDescription.setItems(this.reviewDescriptionList, "حق بازبینی متن ایده را انتخاب نمائید ", selected -> {
            this.reviewDescriptionList = selected;
        });

        txtTitle.requestFocus();
    }

    @Override
    public void onSuccess(String message) {
        btnSubmit.stopLoading();

        txtTitle.setText("");
        txtDescription.setText("");

        for (ListModel listModel : genreList) {
            listModel.setSelected(false);
        }

        for (ListModel listModel : keyWordList) {
            listModel.setSelected(false);
        }

        for (ListModel listModel : reviewTitleList) {
            listModel.setSelected(false);
        }

        for (ListModel listModel : reviewDescriptionList) {
            listModel.setSelected(false);
        }

        ddlGenre.clearSelection();
        ddlKeyWord.removeChip();
        ddlReviewTitle.clearSelection();
        ddlReviewDescription.clearSelection();

        Toasty.success(context, message, Toast.LENGTH_LONG, false).show();
    }

    @Override
    public void onNetworkError() {
        btnRetry.setVisibility(View.VISIBLE);
        prbLoading.setVisibility(View.GONE);
        btnSubmit.stopLoading();

        Toasty.warning(context, getString(R.string.network_error), Toast.LENGTH_LONG, false).show();
    }

    @Override
    public void navToLogin(String message) {
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
        btnSubmit.stopLoading();

        Toasty.error(context, message, Toast.LENGTH_LONG, false).show();
    }


    private void showData(Constant.LIST_TYPE type, List<ListModel> list) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater li = LayoutInflater.from(getActivity());
        View view = li.inflate(R.layout.dialog_view, null);
        builder.setView(view);
        builder.setTitle("");

        ListView listView = (ListView) view.findViewById(R.id.listview);

        ListModelAdapter ad = new ListModelAdapter(type, list);
        listView.setAdapter(ad);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setDivider(null);

        builder.setPositiveButton("تایید", (dialogInterface, i) -> {
            setTag(type, list);
            dialogInterface.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        AppCompatButton button1 = dialog.getWindow().findViewById(android.R.id.button1);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.iy_bold);
        button1.setTypeface(typeface);
        button1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    private class ListModelAdapter extends BaseAdapter {
        private Constant.LIST_TYPE type;
        private List<ListModel> list;

        public ListModelAdapter(Constant.LIST_TYPE type, List<ListModel> list) {
            this.type = type;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.dialog_list_item, null);
            }

            CheckBox chkSelected = view.findViewById(R.id.chkSelected);

            ListModel listModel = list.get(i);

            chkSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    switch (type) {
                        case GENRE:
                            genreList.get(i).setSelected(b);
                            break;
                        case KEYWORD:
                            keyWordList.get(i).setSelected(b);
                            break;
                        case REVIEW_TITLE:
                            reviewTitleList.get(i).setSelected(b);
                            break;
                        case REVIEW_DESCRIPTION:
                            reviewDescriptionList.get(i).setSelected(b);
                            break;
                    }
                }
            });

            chkSelected.setText(listModel.getTitle());
            chkSelected.setChecked(listModel.isSelected());

            return view;
        }
    }

    private void setTag(Constant.LIST_TYPE type, List<ListModel> list) {
        TagAdapter tagAdapter = new TagAdapter(type, list);

        switch (type) {
            case GENRE:
                break;
            case KEYWORD:
                break;
            case REVIEW_TITLE:
                break;
            case REVIEW_DESCRIPTION:
                break;
        }
    }

    private class TagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Constant.LIST_TYPE type;
        private List<ListModel> list;

        public TagAdapter(Constant.LIST_TYPE type, List<ListModel> list) {
            this.type = type;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TagAdapter.itemHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tag_view, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ListModel model = list.get(position);

            TagAdapter.itemHolder itemHolder = (TagAdapter.itemHolder) holder;
            itemHolder.setData(model, position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class itemHolder extends RecyclerView.ViewHolder {
            LinearLayout llItem;
            TextView lblName;
            ImageView btnRemove;


            itemHolder(View view) {
                super(view);
                this.llItem = view.findViewById(R.id.llItem);
                this.lblName = view.findViewById(R.id.lblName);
                this.btnRemove = view.findViewById(R.id.btnRemove);
            }

            public void setData(ListModel listModel, int position) {
                lblName.setText(listModel.getTitle());

                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (type) {
                            case GENRE:
                                genreList.get(position).setSelected(false);
                                break;
                            case KEYWORD:
                                keyWordList.get(position).setSelected(false);
                                break;
                            case REVIEW_TITLE:
                                reviewTitleList.get(position).setSelected(false);
                                break;
                            case REVIEW_DESCRIPTION:
                                reviewDescriptionList.get(position).setSelected(false);
                                break;
                        }

                        notifyDataSetChanged();
                    }
                });

                if (listModel.isSelected()) {
                    llItem.setVisibility(View.VISIBLE);
                } else {
                    llItem.setVisibility(View.GONE);
                }
            }
        }
    }


    private class PersonAdapter extends FilteredArrayAdapter<ListModel> {
        @LayoutRes
        private int layoutId;

        PersonAdapter(Context context, @LayoutRes int layoutId, ListModel[] people) {
            super(context, layoutId, people);
            this.layoutId = layoutId;
        }

        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {

                LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = l.inflate(layoutId, parent, false);
            }

            ListModel p = getItem(position);
            ((TextView) convertView.findViewById(R.id.lblTitle)).setText(p.getTitle());

            return convertView;
        }

        @Override
        protected boolean keepObject(ListModel person, String mask) {
            mask = mask.toLowerCase();
            return person.getTitle().toLowerCase().startsWith(mask);
        }
    }

    private class ChipModel implements ChipInterface {
        private String title;

        public ChipModel(String title) {
            this.title = title;
        }

        @Override
        public Object getId() {
            return null;
        }

        @Override
        public Uri getAvatarUri() {
            return null;
        }

        @Override
        public Drawable getAvatarDrawable() {
            return null;
        }

        @Override
        public String getLabel() {
            return title;
        }

        @Override
        public String getInfo() {
            return null;
        }
    }
}