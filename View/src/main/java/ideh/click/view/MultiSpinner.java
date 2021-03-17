package ideh.click.view;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;

import com.click.R;
import com.click.pc.ListModel;

import java.util.List;

public class MultiSpinner extends AppCompatSpinner implements
        DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    //private List<String> items;
    private List<ListModel> list;
    //private boolean[] selected;
    private String defaultText;
    private MultiSpinnerListener listener;
    AlertDialog alertDialog;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked)
            list.get(which).setSelected(true);
        else
            list.get(which).setSelected(false);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        // refresh text on spinner
        StringBuffer spinnerBuffer = new StringBuffer();
        boolean someSelected = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                spinnerBuffer.append(list.get(i).getTitle());
                spinnerBuffer.append(", ");
                someSelected = true;
            }
        }
        String spinnerText;
        if (someSelected) {
            spinnerText = spinnerBuffer.toString();
            if (spinnerText.length() > 2)
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
        } else {
            spinnerText = defaultText;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.selected_simple_spinner_item, new String[]{spinnerText});
        setAdapter(adapter);
        listener.onItemsSelected(list);
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String[] items = new String[list.size()];
        boolean[] selected = new boolean[list.size()];

        for (int i = 0; i < list.size(); i++) {
            items[i] = list.get(i).getTitle();
            selected[i] = list.get(i).isSelected();
        }

        builder.setMultiChoiceItems(items, selected, this);

        builder.setPositiveButton("تایید",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    public void setItems(List<ListModel> items, String allText,
                         MultiSpinnerListener listener) {
        this.list = items;
        this.defaultText = allText;
        this.listener = listener;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().matches("اعضا")){
                list.get(i).setSelected(true);
            }else {
                list.get(i).setSelected(false);
            }
        }

        // all text on the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item, new String[]{allText});
        setAdapter(adapter);

        onCancel(null);
    }

    public void clearSelection() {
        for (int i = 0; i < list.size(); i++)
            list.get(i).setSelected(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simple_spinner_item, new String[]{this.defaultText});
        setAdapter(adapter);
    }

    public interface MultiSpinnerListener {
        public void onItemsSelected(List<ListModel> list);
    }
}