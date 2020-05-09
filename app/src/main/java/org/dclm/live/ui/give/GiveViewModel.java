package org.dclm.live.ui.give;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.lifecycle.ViewModel;

public class GiveViewModel extends ViewModel {

    public void give(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dclm.org/give-online/"));
        context.startActivity(intent);
    }


}
