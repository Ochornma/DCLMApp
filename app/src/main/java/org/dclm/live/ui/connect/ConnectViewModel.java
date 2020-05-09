package org.dclm.live.ui.connect;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import org.dclm.live.R;

public class ConnectViewModel extends ViewModel {
    private Context context;

    public ConnectViewModel(Context context){
        this.context = context;
    }



    public void socialMedia(String socialMedia) {
        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(socialMedia));
        context.startActivity(intent2);
    }

    public void email(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("message/rfc822");
        emailIntent.setData(Uri.parse("mailto:ict@deeperlifeonline.org")); // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "info@deeperlifeonline.org");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.inquiry));

        try {
            context.startActivity(Intent.createChooser(emailIntent,  context.getResources().getString(R.string.chose_app)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,  context.getResources().getString(R.string.no_app), Toast.LENGTH_LONG).show();
        }
    }
}
