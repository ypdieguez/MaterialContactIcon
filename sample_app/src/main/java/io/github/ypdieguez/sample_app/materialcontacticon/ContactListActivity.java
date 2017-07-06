/*
 * Copyright (C) 2017 Yordan P. Dieguez <ypdieguez@tuta.io>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ypdieguez.sample_app.materialcontacticon;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.contacts.common.ContactPhotoManager;

public class ContactListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER = 425;

    private static final String[] CONTACT_PROJECTION = new String[]{
            Contacts._ID,                           // 0
            Contacts.LOOKUP_KEY,                    // 1
            Contacts.DISPLAY_NAME_PRIMARY,          // 2
            Contacts.PHOTO_URI,                     // 3
            Contacts.PHOTO_THUMBNAIL_URI,           // 4

    };

    private ContactListAdapter mAdapter;

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        mAdapter = new ContactListAdapter(null, ContactPhotoManager.getInstance(this));

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mButton = (Button) findViewById(R.id.button);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_contact_list);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.CATEGORY_APP_CONTACTS);
                startActivity(i);
            }
        });

        getSupportLoaderManager().initLoader(LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Contacts.CONTENT_URI, CONTACT_PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            mAdapter.changeCursor(data);
            mProgressBar.setVisibility(View.GONE);
            mButton.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            mButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }
}

