package org.dclm.live.ui.listen;

import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.dclm.live.R;
import org.dclm.live.databinding.RadioFragmentBinding;
import org.dclm.live.ui.LanguageSelection;
import org.dclm.live.ui.ondemand.audio.PodcastService;
import org.dclm.live.util.SubtitleReceived;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.BIND_AUTO_CREATE;
import static android.content.Context.MODE_PRIVATE;
import static android.os.Looper.getMainLooper;

public class RadioFragment extends Fragment implements PopupMenu.OnMenuItemClickListener, SubtitleReceived {

    private RadioViewModel mViewModel;
    private static final String PREFRENCES = "org.dclm.radio";
    public static String link;
    private String url;
    private DCLMRadioService dclmRadioService;
    private boolean mBound = false;
    private RadioFragmentBinding binding;
    public static boolean state = false;
    public boolean stateStart = false;
    private SharedPreferences sharedPreferences, prefs;
    private SharedPreferences.Editor editor;
    private RequestQueue mQueue;
    public static boolean whereFrom = false;
    private String nowLanguage;
    private String[] languageList;
    private int languageNumber = 0;


    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            DCLMRadioService.RadioBinder binder2 = (DCLMRadioService.RadioBinder) service;
            dclmRadioService = binder2.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i("create", "create");
        Intent intent = new Intent(getActivity(), PodcastService.class);
        getActivity().stopService(intent);

      binding = DataBindingUtil.inflate(inflater, R.layout.radio_fragment, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(PREFRENCES, MODE_PRIVATE);
        prefs = getActivity().getSharedPreferences(PREFRENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        languageList = new String[]{"ENGLISH", "FRENCH", "PORTUGUESE/EGUN", "YORUBA","IGBO", "HAUSA" };
        binding.selectLanguage.setOnClickListener( v -> {
            Handler mHandler = new Handler(getMainLooper());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    LanguageSelection.fromVideo = false;
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_radioFragment_to_languageSelection);
                }
            });

        });

      return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("activity", "activity");
        RadioFactory factory = new RadioFactory(getActivity(), this);
        mViewModel = new ViewModelProvider(this, factory).get(RadioViewModel.class);
        url = prefs.getString("url", getActivity().getResources().getString(R.string.dclm_api));
        link = prefs.getString("link", getActivity().getResources().getString(R.string.radio_link));
        mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        setRepeatingAsyncTask();
        Intent intent = new Intent(getActivity(), DCLMRadioService.class);
        binding.setViewmodel(mViewModel);
        binding.setState(state);
        mViewModel.checkButton.observe(getViewLifecycleOwner(), aBoolean -> {
            if (!aBoolean) {
                //Util.startForegroundService(getActivity(), intent);
                RadioFragment.this.getActivity().startService(intent);
                //Util.startForegroundService(getActivity(),intent);
                dclmRadioService.startPlayer();
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.play.setImageResource(R.drawable.ic_pause);
                    }
                });
            } else {
                //getActivity().stopService(new Intent(getActivity(), DCLMRadioService.class));
                dclmRadioService.pausePlayer();

                mBound = false;
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.play.setImageResource(R.drawable.ic_play);
                    }
                });
            }
            stateStart = !stateStart;
        });
        if (whereFrom){
            mViewModel.link.postValue(RadioFragmentArgs.fromBundle(getArguments()).getLink());
            whereFrom = false;
        } else {
            String linkLanguage = RadioFragmentArgs.fromBundle(getArguments()).getLink();
            if (linkLanguage == null){
                prefs = getActivity().getSharedPreferences(PREFRENCES, MODE_PRIVATE);
                Log.i("notu", prefs.getString("language", getActivity().getResources().getString(R.string.select_french_language)));
                mViewModel.link.postValue(prefs.getString("language", getActivity().getResources().getString(R.string.select_english_language)));
               // getActivity().startService(new Intent(getActivity(), DCLMRadioService.class));
            } else {
                linkLanguage = prefs.getString("language", getActivity().getResources().getString(R.string.select_english_language));
                mViewModel.link.postValue(linkLanguage);
                Log.i("notu", linkLanguage);
            }
        }

        binding.next.setOnClickListener( v ->{
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    skipNext();
                }
            });

        });

        binding.previous.setOnClickListener( v ->{
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    skipPrevious();
                }
            });

        });
        mViewModel.link.observe(getViewLifecycleOwner(), language ->{

            switch (language) {
                case "ENGLISH":
                case "ANGLAISE":
                    url = "https://stat1.dclm.org/api/nowplaying/1";
                    link = "https://airtime.dclm.org/radio/8000/live?1569692293";
                    nowLanguage = "ENGLISH";


                    break;
                case "FRENCH":
                case "FRANÇAISE":
                    url = "https://stat1.dclm.org/api/nowplaying/3";
                    link = "https://airtime.dclm.org/radio/8050/french";
                    nowLanguage = "FRENCH";


                    break;
                case "YORUBA":

                    url = "https://stat1.dclm.org/api/nowplaying/2";
                    link = "https://airtime.dclm.org/radio/8060/yoruba";
                    nowLanguage = "YORUBA";
                    break;

                case "IGBO":
                    url = "https://stat1.dclm.org/api/nowplaying/5";
                    link = "https://airtime.dclm.org/radio/8090/igbo";
                    nowLanguage = "IGBO";

                    break;

                case "HAUSA":
                    url = "https://stat1.dclm.org/api/nowplaying/4";
                    link = "https://airtime.dclm.org/radio/8070/hausa";
                    nowLanguage = "HAUSA";
                    break;

                case "PORTUGUESE/EGUN":
                case "PORTUGAIS/EGUN":
                    url = "https://stat1.dclm.org/api/nowplaying/6";
                    link = "https://airtime.dclm.org/radio/8110/portuguese";
                    nowLanguage = "PORTUGUESE/EGUN";

                    break;
            }
            if (DCLMRadioService.checkPlay){
                getActivity().startService(intent);
                //Util.startForegroundService(getActivity(), intent);
            }

            editor.putString("url", url);
            editor.putString("link", link);
            editor.putString("language", nowLanguage);
            editor.apply();
        });
        // mViewModel.link.postValue(RadioFragmentArgs.fromBundle(getArguments()).getLink());
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            getView().getViewTreeObserver().addOnWindowFocusChangeListener(hasFocus -> {
                if (hasFocus){
                    if (DCLMRadioService.checkPlay){
                        Handler handler1 = new Handler();
                        handler1.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.play.setImageResource(R.drawable.ic_pause);
                            }
                        });

                    } else {
                        Handler handler2 = new Handler();
                        handler2.post(new Runnable() {
                            @Override
                            public void run() {
                                binding.play.setImageResource(R.drawable.ic_play);
                            }
                        });
                    }
                }
            });
        }

    }

    private void skipNext(){
        if (languageNumber < languageList.length && languageNumber != languageList.length-1){
            languageNumber = languageNumber + 1;
            mViewModel.link.postValue(languageList[languageNumber]);
        } else if (languageNumber == languageList.length - 1){
            languageNumber = 0;
            mViewModel.link.postValue(languageList[languageNumber]);
        }
    }

    private void skipPrevious(){
        if (languageNumber == 0){
            languageNumber = languageList.length - 1;
            mViewModel.link.postValue(languageList[languageNumber]);
        } else if (languageNumber > 0){
            languageNumber = languageNumber - 1;
            mViewModel.link.postValue(languageList[languageNumber]);
        }
    }
    private void setRepeatingAsyncTask() {

        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        mViewModel.jsonParse(url);
                    }
                });
            }
        };

        timer.schedule(task, 0, 60*1000);  // interval of one minute
    }






    @Override
    public void onStart() {
        super.onStart();
        Log.i("start", "start");
        // Bind to DCLMService
        Intent intent = new Intent(getContext(), DCLMRadioService.class);
        getActivity().bindService(intent, connection, BIND_AUTO_CREATE);
        mBound = true;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("resume", "resume");

            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (stateStart){
                        if(!state){
                            binding.play.setImageResource(R.drawable.ic_play);
                        } else {
                            binding.play.setImageResource(R.drawable.ic_pause);
                        }
                    } else {
                        if (DCLMRadioService.checkPlay){
                            binding.play.setImageResource(R.drawable.ic_pause);
                        } else {
                            binding.play.setImageResource(R.drawable.ic_play);
                        }
                    }
                }
            });

        prefs.getBoolean("playState", true);


    }



    @Override
    public void onStop() {
        super.onStop();
        Log.i("stop", "stop");
       // if(audioIsPlaying != 5) {
            getActivity().unbindService(connection);
            mBound = false;
            editor.putBoolean("playState", DCLMRadioService.checkPlay);
            editor.putString("language", nowLanguage);
            editor.apply();
       // }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       /* Intent intent = new Intent(getActivity(), DCLMRadioService.class);
        getActivity().stopService(intent);*/
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

            switch ((item.getItemId())){
                case R.id.english:
                    mViewModel.link.postValue("ENGLISH");
                    return true;

                case R.id.french:
                    mViewModel.link.postValue("FRENCH");
                    return true;

                case R.id.portugal:
                    mViewModel.link.postValue("PORTUGUESE/EGUN");
                    return true;

                case R.id.yoruba:
                    mViewModel.link.postValue("YORUBA");
                    return true;

                case R.id.igbo:
                    mViewModel.link.postValue("IGBO");
                    return true;

                case R.id.hausa:
                    mViewModel.link.postValue("HAUSA");
                    return true;
        }
        return false;
    }

    @Override
    public void subtitle(SubTitle subTitles) {
        binding.setData(subTitles);
    }

    @Override
    public void error(SubTitle subTitles) {
        binding.setData(subTitles);
    }
}
