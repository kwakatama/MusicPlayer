package com.practiceApp.app;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Mp3Filter implements FilenameFilter
{
    public boolean accept(File dir, String name)
    {
        return (name.endsWith(".mp3"));
    }
}


public class MainActivity extends ListActivity
{

    private static final String SD_PATH = new String("/sdcard/");
    private List<String> songs = new ArrayList<String>();
    private MediaPlayer mp = new MediaPlayer();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updatePlaylist();

        Button stopPlay = (Button) findViewById(R.id.stopBtn);
        stopPlay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mp.stop();
            }
        });


    }

    protected void onListItemClick(ListView List, View view, int position, long id)
    {
        try
        {
            mp.reset();
            mp.setDataSource(SD_PATH + songs.get(position));
            mp.prepare();
            mp.start();
        } catch (IOException e)
        {
            Log.v(getString(R.string.app_name), e.getMessage());
        }
    }

    private void updatePlaylist()
    {
        File home = new File(SD_PATH);
        if (home.listFiles(new Mp3Filter()).length > 0)
        {
            for (File file : home.listFiles(new Mp3Filter()))
            {
                songs.add(file.getName());
            }

            ArrayAdapter<String> songList = new ArrayAdapter<String>(this,R.layout.song_item,songs);
            setListAdapter(songList);
        }
    }


}
