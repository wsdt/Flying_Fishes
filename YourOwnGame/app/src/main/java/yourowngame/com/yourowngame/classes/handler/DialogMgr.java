package yourowngame.com.yourowngame.classes.handler;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.handler.interfaces.ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation;

public class DialogMgr {
    private Activity context;
    private static final String TAG = "DialogMgr";

    public DialogMgr(Activity context) {
        //Context MUST be an activity, because we reload it if purchase was a success!!
        this.setContext(context);
    }

    /**
     * @param lblNegativeBtn: By providing an empty string ("") [not null!] there will only the OK button added
     */
    public void showDialog_Generic(@Nullable String title, @Nullable String msg, @Nullable String lblPositiveBtn, @Nullable String lblNegativeBtn, int icon, @Nullable final ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation executeIfTrueSuccess_or_ifFalseFailure_afterCompletation) { //to nullable icon just put a negative value in it
        Log.d(TAG, "showDialog_Generic: Trying to show dialog.");
        if (!getContext().isFinishing()) { //really important
            Log.d(TAG, "showDialog_Generic: Allowed to show dialog.");
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getContext());
            }

            builder.setTitle((title == null) ? this.getContext().getResources().getString(R.string.dialog_generic_error_title) : title)
                    .setMessage((msg == null) ? this.getContext().getResources().getString(R.string.dialog_generic_error_msg) : msg)
                    .setIcon((icon < 0) ? R.drawable.app_icon_gameboy : icon) //ids have to be positive
                    .setPositiveButton((lblPositiveBtn == null) ? this.getContext().getResources().getString(R.string.dialog_generic_button_positive) : lblPositiveBtn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "showDialog_InAppProductPromotion: Closing dialog (Positive Button).");
                            dialog.dismiss();
                            if (executeIfTrueSuccess_or_ifFalseFailure_afterCompletation != null) {
                                executeIfTrueSuccess_or_ifFalseFailure_afterCompletation.success_is_true();
                            }
                        }
                    });
            //use provided listener if not null otherwise use default one below, but if empty string then use no negative btn
            if (lblNegativeBtn != null) {
                if (lblNegativeBtn.equals("")) {
                    builder.show();
                    return; //show dialog now and exit method (we dont want a negative button because NOT null and only empty string provided.
                }
            } //not else!!
            builder.setNegativeButton((lblNegativeBtn == null) ? this.getContext().getResources().getString(R.string.dialog_generic_button_negative) : lblNegativeBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, "showDialog_InAppProductPromotion: Closing dialog (Negative Button).");
                    dialog.dismiss();
                    if (executeIfTrueSuccess_or_ifFalseFailure_afterCompletation != null) {
                        executeIfTrueSuccess_or_ifFalseFailure_afterCompletation.failure_is_false();
                    }
                }
            }).show(); //if btnLabelNegative was not an empty string, but null OR another string then we add a negativeButton
        } else {
            Log.d(TAG, "showDialog_Generic: Activity/Context is finishing. Did not show dialog. Prevented bad token exception.");
        }
    }


    //GETTER/SETTER ----------------------------------

    public Activity getContext() {
        return this.context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }
}