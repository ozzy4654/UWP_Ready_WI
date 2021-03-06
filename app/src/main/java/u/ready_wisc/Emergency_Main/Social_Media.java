/*
*
*  Copyright 2015 University of Wisconsin - Parkside
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*
*
*/

package u.ready_wisc.Emergency_Main;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import u.ready_wisc.MediaItem;
import u.ready_wisc.R;
import u.ready_wisc.VolunteerDBHelper;

/**
 * Holds social media item which will populate a listview
 */
public class Social_Media extends ActionBarActivity {

    static VolunteerDBHelper vdbHelper;
    static Button facebookButt;
    static Button twitterButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        vdbHelper = new VolunteerDBHelper(this);

        final MediaItem item = vdbHelper.getMediaData().get(0);

        facebookButt = (Button) findViewById(R.id.button2);
        twitterButt = (Button) findViewById(R.id.button3);

        facebookButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean haveF;
                try {
                    ApplicationInfo info = getPackageManager().getApplicationInfo("com.facebook.katana",0);
                    haveF = true;
                } catch (PackageManager.NameNotFoundException e){
                    haveF = false;
                }
                if(haveF){
                    // Strip all but page name and ID from url
                    String fbString = item.getFacebook();
                    fbString = fbString.substring(fbString.lastIndexOf("/") + 1);

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + fbString));
                    startActivity(intent);
                } else {
                    // must add http:// prefix to url before it will open
                    if (!item.getFacebook().equals(" ")) {
                        Uri uri;
                        if (!item.getFacebook().contains("http://") && !item.getFacebook().contains("https://")) {
                            uri = Uri.parse("http://" + item.getFacebook());
                        } else {
                            uri = Uri.parse(item.getFacebook());
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }
            }
        });

        twitterButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean haveT;
                try {
                    ApplicationInfo info = getPackageManager().getApplicationInfo("com.twitter.android",0);
                    haveT = true;
                } catch (PackageManager.NameNotFoundException e){
                    haveT = false;
                }
                if (!item.getTwitter().equals(" ")) {
                    if(haveT){
                        // Strip all but username from twitter url
                        String twitString = item.getTwitter();
                        twitString = twitString.substring(twitString.lastIndexOf("/") + 1);

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitString));
                        startActivity(intent);
                    } else {
                        Uri uri;
                        // must add http:// prefix to url before it will open
                        if (!item.getTwitter().contains("http://") && !item.getTwitter().contains("https://")) {
                            uri = Uri.parse("http://" + item.getTwitter());
                        } else {
                            uri = Uri.parse(item.getTwitter());
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //TODO add options menu
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
