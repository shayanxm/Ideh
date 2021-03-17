package ideh.click.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DialogTitle;
import androidx.core.content.res.ResourcesCompat;

import com.click.R;


/**
 * Created by Sadegh-Pc on 5/12/2018.
 */

public class ConfirmDialog {

    public static void confirmDialog(Context context, Callback callback) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("پیام سیستم");

        // Setting Dialog Message
        alertDialog.setMessage("آیا برای حذف اطمینان دارید؟");

        // On pressing Settings button
        alertDialog.setPositiveButton("حذف شود", (dialog, which) -> {
            callback.onPositiveButtonClick(dialog);
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("انصراف", (dialog, which) -> {
            callback.onNegativeButtonClick(dialog);
        });

        // Showing Alert Message
        AlertDialog dialog = alertDialog.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        /** find dialog element and set typeface, fontSize and textColor */
        AppCompatTextView textView = dialog.getWindow().findViewById(android.R.id.message);
        DialogTitle alertTitle = dialog.getWindow().findViewById(R.id.alertTitle);
        AppCompatButton button1 = dialog.getWindow().findViewById(android.R.id.button1);
        AppCompatButton button2 = dialog.getWindow().findViewById(android.R.id.button2);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.iy_regular);

        textView.setTypeface(typeface);
        textView.setTextSize(14f);
        textView.setTextColor(context.getResources().getColor(R.color.colorAccent));

        alertTitle.setTypeface(typeface);

        button1.setTypeface(typeface);
        button1.setTextColor(context.getResources().getColor(R.color.colorAccent));

        button2.setTypeface(typeface);
        button2.setTextColor(context.getResources().getColor(R.color.colorAccent));
    }

    public static void confirmDialog(Context context, String title, String message, Callback callback) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(Html.fromHtml(message));

        // On pressing Settings button
        alertDialog.setPositiveButton("تایید", (dialog, which) -> {
            callback.onPositiveButtonClick(dialog);
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("انصراف", (dialog, which) -> {
            callback.onNegativeButtonClick(dialog);
        });

        // Showing Alert Message
        AlertDialog dialog = alertDialog.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        /** find dialog element and set typeface, fontSize and textColor */
        AppCompatTextView textView = dialog.getWindow().findViewById(android.R.id.message);
        DialogTitle alertTitle = dialog.getWindow().findViewById(R.id.alertTitle);
        AppCompatButton button1 = dialog.getWindow().findViewById(android.R.id.button1);
        AppCompatButton button2 = dialog.getWindow().findViewById(android.R.id.button2);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.iy_regular);

        textView.setTypeface(typeface);
        textView.setTextSize(14f);
        textView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10.0f, context.getResources().getDisplayMetrics()), 1.0f);
        textView.setTextColor(context.getResources().getColor(R.color.black));

        alertTitle.setTypeface(typeface);

        button1.setTypeface(typeface);
        button1.setTextColor(context.getResources().getColor(R.color.colorPrimary));

        button2.setTypeface(typeface);
        button2.setTextColor(context.getResources().getColor(R.color.black));
    }

    public static void confirmDialog(Context context, String title, String message, String positiveText, String negativeText, Callback callback) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // On pressing Settings button
        alertDialog.setPositiveButton(positiveText, (dialog, which) -> {
            callback.onPositiveButtonClick(dialog);
        });

        // on pressing cancel button
        alertDialog.setNegativeButton(negativeText, (dialog, which) -> {
            callback.onNegativeButtonClick(dialog);
        });

        // Showing Alert Message
        AlertDialog dialog = alertDialog.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        /** find dialog element and set typeface, fontSize and textColor */
        AppCompatTextView textView = dialog.getWindow().findViewById(android.R.id.message);
        DialogTitle alertTitle = dialog.getWindow().findViewById(R.id.alertTitle);
        AppCompatButton button1 = dialog.getWindow().findViewById(android.R.id.button1);
        AppCompatButton button2 = dialog.getWindow().findViewById(android.R.id.button2);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.iy_regular);

        textView.setTypeface(typeface);
        textView.setTextSize(14f);
        textView.setTextColor(context.getResources().getColor(R.color.black));

        alertTitle.setTypeface(typeface);

        button1.setTypeface(typeface);
        button1.setTextColor(context.getResources().getColor(R.color.black));

        button2.setTypeface(typeface);
        button2.setTextColor(context.getResources().getColor(R.color.colorAccent));
    }

    public static void confirmDialog(Context context, View view, String positiveText, String negativeText, Callback callback) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Message
        alertDialog.setView(view);

        // On pressing Settings button
        alertDialog.setPositiveButton(positiveText, (dialog, which) -> {
            callback.onPositiveButtonClick(dialog);
        });

        // on pressing cancel button
        alertDialog.setNegativeButton(negativeText, (dialog, which) -> {
            callback.onNegativeButtonClick(dialog);
        });

        // Showing Alert Message
        AlertDialog dialog = alertDialog.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        /** find dialog element and set typeface, fontSize and textColor */
        AppCompatTextView textView = dialog.getWindow().findViewById(android.R.id.message);
        DialogTitle alertTitle = dialog.getWindow().findViewById(R.id.alertTitle);
        AppCompatButton button1 = dialog.getWindow().findViewById(android.R.id.button1);
        AppCompatButton button2 = dialog.getWindow().findViewById(android.R.id.button2);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.iy_regular);

        textView.setTypeface(typeface);
        textView.setTextSize(14f);
        textView.setTextColor(context.getResources().getColor(R.color.colorAccent));

        alertTitle.setTypeface(typeface);

        button1.setTypeface(typeface);
        button1.setTextColor(context.getResources().getColor(R.color.colorAccent));
        button1.setTextColor(Color.parseColor("#212121"));

        button2.setTypeface(typeface);
        button2.setTextColor(context.getResources().getColor(R.color.colorAccent));
        button2.setTextColor(Color.parseColor("#212121"));
    }

    public static AlertDialog confirmDialogWithoutButton(Context context, View view) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Message
        alertDialog.setView(view);
        
        // Showing Alert Message
        AlertDialog dialog = alertDialog.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        /** find dialog element and set typeface, fontSize and textColor */
        AppCompatTextView textView = dialog.getWindow().findViewById(android.R.id.message);
        DialogTitle alertTitle = dialog.getWindow().findViewById(R.id.alertTitle);
        AppCompatButton button1 = dialog.getWindow().findViewById(android.R.id.button1);
        AppCompatButton button2 = dialog.getWindow().findViewById(android.R.id.button2);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.iy_regular);

        textView.setTypeface(typeface);
        textView.setTextSize(14f);
        textView.setTextColor(context.getResources().getColor(R.color.colorAccent));

        alertTitle.setTypeface(typeface);

        button1.setTypeface(typeface);
        button1.setTextColor(context.getResources().getColor(R.color.colorAccent));
        button1.setTextColor(Color.parseColor("#212121"));

        button2.setTypeface(typeface);
        button2.setTextColor(context.getResources().getColor(R.color.colorAccent));
        button2.setTextColor(Color.parseColor("#212121"));

        return dialog;
    }

    public static void alertDialog(Context context, View view, String confirmText, Callback callback) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Message
        alertDialog.setView(view);

        // On pressing Settings button
        alertDialog.setPositiveButton(confirmText, (dialog, which) -> {
            callback.onPositiveButtonClick(dialog);
        });

        // Showing Alert Message
        AlertDialog dialog = alertDialog.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        /** find dialog element and set typeface, fontSize and textColor */
        AppCompatTextView textView = dialog.getWindow().findViewById(android.R.id.message);
        DialogTitle alertTitle = dialog.getWindow().findViewById(R.id.alertTitle);
        AppCompatButton button1 = dialog.getWindow().findViewById(android.R.id.button1);
        AppCompatButton button2 = dialog.getWindow().findViewById(android.R.id.button2);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.iy_regular);

        textView.setTypeface(typeface);
        textView.setTextSize(14f);
        textView.setTextColor(context.getResources().getColor(R.color.colorAccent));

        alertTitle.setTypeface(typeface);

        button1.setTypeface(typeface);
        button1.setTextColor(context.getResources().getColor(R.color.colorAccent));
        button1.setTextColor(Color.parseColor("#212121"));


        button2.setTypeface(typeface);
        button2.setTextColor(context.getResources().getColor(R.color.colorAccent));
        button2.setTextColor(Color.parseColor("#212121"));
    }

    public static void alertDialog(Context context, String message, String confirmText, Callback callback) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Message
        alertDialog.setMessage(Html.fromHtml(message));

        // On pressing Settings button
        alertDialog.setPositiveButton(confirmText, (dialog, which) -> {
            callback.onPositiveButtonClick(dialog);
        });

        // Showing Alert Message
        AlertDialog dialog = alertDialog.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        /** find dialog element and set typeface, fontSize and textColor */
        AppCompatTextView textView = dialog.getWindow().findViewById(android.R.id.message);
        DialogTitle alertTitle = dialog.getWindow().findViewById(R.id.alertTitle);
        AppCompatButton button1 = dialog.getWindow().findViewById(android.R.id.button1);
        AppCompatButton button2 = dialog.getWindow().findViewById(android.R.id.button2);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.iy_regular);

        textView.setTypeface(typeface);
        textView.setTextSize(16f);
        textView.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f,  context.getResources().getDisplayMetrics()), 1.0f);
        textView.setTextColor(Color.parseColor("#989898"));

        alertTitle.setTypeface(typeface);

        button1.setTypeface(typeface);
        button1.setTextColor(context.getResources().getColor(R.color.colorAccent));
        button1.setTextColor(Color.parseColor("#212121"));


        button2.setTypeface(typeface);
        button2.setTextColor(context.getResources().getColor(R.color.colorAccent));
        button2.setTextColor(Color.parseColor("#212121"));
    }

    public static AlertDialog.Builder listDialog(Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Showing Alert Message
        AlertDialog dialog = alertDialog.create();
        dialog.setCanceledOnTouchOutside(false);

        /** find dialog element and set typeface, fontSize and textColor */
        AppCompatTextView textView = dialog.getWindow().findViewById(android.R.id.message);
        DialogTitle alertTitle = dialog.getWindow().findViewById(R.id.alertTitle);
        AppCompatButton button1 = dialog.getWindow().findViewById(android.R.id.button1);
        AppCompatButton button2 = dialog.getWindow().findViewById(android.R.id.button2);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.iy_regular);

//        textView.setTypeface(typeface);
//        textView.setTextSize(14f);
//        textView.setTextColor(context.getResources().getColor(R.color.gray));

        //alertTitle.setTypeface(typeface);

//        button1.setTypeface(typeface);
//        button1.setTextColor(context.getResources().getColor(R.color.gray));
//        button1.setTextColor(Color.parseColor("#212121"));
//
//
//        button2.setTypeface(typeface);
//        button2.setTextColor(context.getResources().getColor(R.color.gray));
//        button2.setTextColor(Color.parseColor("#212121"));

        return alertDialog;
    }

    public interface Callback {
        void onPositiveButtonClick(DialogInterface dialogInterface);

        void onNegativeButtonClick(DialogInterface dialogInterface);
    }

    public static float dp2px(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
