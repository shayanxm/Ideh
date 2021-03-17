package ideh.click.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.click.R;
import com.click.pc.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IdeaDetailActivity extends AppCompatActivity {
    private String title = "";
    private String description = "";

    @BindView(R.id.lblTitle)
    TextView lblTitle;

    @BindView(R.id.lblDescription)
    TextView lblDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_detail);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString(Constant._TITLE);
            description = bundle.getString(Constant._DESCRIPTION);
        }

        initView();
    }

    private void initView() {
        lblTitle.setText(title);
        lblDescription.setText(description);
    }
}