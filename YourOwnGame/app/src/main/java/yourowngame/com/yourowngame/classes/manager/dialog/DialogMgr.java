package yourowngame.com.yourowngame.classes.manager.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.RelativeLayout;

import yourowngame.com.yourowngame.R;
import yourowngame.com.yourowngame.classes.annotations.Enhance;
import yourowngame.com.yourowngame.classes.global_configuration.Constants;
import yourowngame.com.yourowngame.classes.manager.interfaces.ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation;

@Enhance (message = "Maybe make this to a dialog base class which overwrites show() etc. and " +
        "prevents many errors. Good cohesion etc. So easily many types of dialogues manageable.")
public class DialogMgr {
    private Activity context;
    private static final String TAG = "DialogMgr";

    public DialogMgr(Activity context) {
        //Context MUST be an activity, because we reload it if purchase was a success!!
        this.setContext(context);
    }

    /** Use this method to prevent badtoken exceptions! */
    public void showDialog(@NonNull Dialog dialog) {
        dialog.setCancelable(false); //normally we want to force our user to see the dialog (prevent outside and back touch/click) [especially for levelachieved dialog]

        if (!getContext().isFinishing()) {
            dialog.show();
        }
    }

    /**
     * @param lblNegativeBtn: By providing an empty string ("") [not null!] there will only the OK button added
     */
    public Dialog createDialog_Generic(@Nullable String title, @Nullable String msg, @Nullable String lblPositiveBtn, @Nullable String lblNegativeBtn, int icon, @Nullable final ExecuteIfTrueSuccess_or_ifFalseFailure_afterCompletation executeIfTrueSuccess_or_ifFalseFailure_afterCompletation) { //to nullable icon just put a negative getValue in it
        Log.d(TAG, "createDialog_Generic: Trying to show dialog.");
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }

        builder.setTitle((title == null) ? this.getContext().getResources().getString(R.string.dialog_generic_error_title) : title)
                .setMessage((msg == null) ? this.getContext().getResources().getString(R.string.dialog_generic_error_msg) : msg)
                .setIcon((icon < 0) ? Constants.APP_ICON : icon) //ids have to be positive
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
                return builder.create();
                //return dialog now and exit method (we dont want a negative button because NOT null and only empty string provided.
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
        }); //if btnLabelNegative was not an empty string, but null OR another string then we add a negativeButton

        return builder.create();
    }

    /** Only use this method to create levelAchieved dialogues, so dialogMgr is responsible
     * for all sorts of dialogues.*/
    public Dialog createDialog_LevelAchieved() {
        return new GameSuccessDialog(this.getContext());
    }


    //GETTER/SETTER ----------------------------------

    public Activity getContext() {
        return this.context;
    }

    public void setContext(Activity context) {
        this.context = context;
    }
}