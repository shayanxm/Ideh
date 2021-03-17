package ideh.click.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.click.R;
import com.click.pc.ListModel;
import com.tokenautocomplete.TokenCompleteTextView;

public class CompletionView extends TokenCompleteTextView<ListModel> {

    public CompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getViewForObject(ListModel person) {

        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TokenTextView view = (TokenTextView) l.inflate(R.layout.tag_text, (ViewGroup) getParent(), false);
        view.setText(person.getTitle());

        return view;
    }

    @Override
    protected ListModel defaultObject(String completionText) {
        int index = completionText.indexOf('@');
        if (index == -1) {
            return new ListModel(completionText,false);
        } else {
            return new ListModel(completionText.substring(0, index), false);
        }
    }
}