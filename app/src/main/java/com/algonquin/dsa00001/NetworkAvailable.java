package com.algonquin.dsa00001;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Button;
import android.widget.LinearLayout;

public class NetworkAvailable {
    static boolean isConnected = false;
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected =  activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if(!isConnected)
            networkConnectionDialog(mContext);

        return isConnected;
    }

    public static void networkConnectionDialog(Context mContext) {
        final Dialog dialog = new Dialog(mContext, R.style.CustomAlertDialog);
        dialog.setContentView(R.layout.network_connection_dialog);
        dialog.setCancelable(false);
        Button btnOk;
        btnOk = dialog.findViewById(R.id.btn_ok);

        dialog.show();
        dialog.getWindow().setLayout(((int) mContext.getResources().getDimension(R.dimen._250sdp)), LinearLayout.LayoutParams.WRAP_CONTENT);

        btnOk.setOnClickListener(v -> dialog.dismiss());
    }
}
