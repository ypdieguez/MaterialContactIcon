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

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.android.contacts.common.ContactPhotoManager;
import com.android.contacts.common.ContactPhotoManager.DefaultImageRequest;
import com.android.contacts.common.lettertiles.LetterTileDrawable;

import io.github.ypdieguez.cursorrecycleradapter.CursorRecyclerAdapter;
import io.github.ypdieguez.materialcontacticon.MaterialQuickContactBadge;

class ContactListAdapter extends CursorRecyclerAdapter<ContactListAdapter.ViewHolder> {

    private static final int mIdIndex = 0;
    private static final int mLookupIndex = 1;
    private static final int mNameIndex = 2;
    private static final int mPhotoUriIndex = 3;

    private ContactPhotoManager mPhotoManager;

    ContactListAdapter(Cursor c, ContactPhotoManager photoManager) {
        super(c);
        mPhotoManager = photoManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_contact_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        long id = cursor.getLong(mIdIndex);
        String uriString = cursor.getString(mPhotoUriIndex);
        String lookupKey = cursor.getString(mLookupIndex);
        String name = cursor.getString(mNameIndex);

        Uri lookupUri = ContentUris.withAppendedId(
                Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey), id);
        Uri photoUri = (uriString != null ? Uri.parse(uriString) : null);

        if (MainActivity.VIEW == MainActivity.VIEW_MATERIAL_QUICK_CONTACT_BADGE) {
            ((MaterialQuickContactBadge) holder.mContactView).assignContactUri(lookupUri);
        } else if (MainActivity.VIEW == MainActivity.VIEW_QUICK_CONTACT_BADGE) {
            ((QuickContactBadge) holder.mContactView).assignContactUri(lookupUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !MainActivity.WHITH_OVERLAY) {
                ((QuickContactBadge) holder.mContactView).setOverlay(null);
            }
        }

        if (MainActivity.METHOD == MainActivity.METHOD_CONTACT_PHOTO_MANAGER) {
            DefaultImageRequest request = new DefaultImageRequest(name, lookupKey, MainActivity.IS_CIRCLULAR);
            mPhotoManager.loadPhoto(holder.mContactView, photoUri, -1, false, MainActivity.IS_CIRCLULAR, request);
        } else {
            final LetterTileDrawable drawable = new LetterTileDrawable(holder.itemView.getResources());
            drawable.setLetterAndColorFromContactDetails(name, lookupKey);
            drawable.setIsCircular(MainActivity.IS_CIRCLULAR);

            holder.mContactView.setImageDrawable(drawable);
        }

        holder.mTextView.setText(name);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mContactView;

        ViewHolder(View itemView) {
            super(itemView);

            switch (MainActivity.VIEW) {
                case MainActivity.VIEW_MATERIAL_QUICK_CONTACT_BADGE:
                    mContactView =
                            (MaterialQuickContactBadge) itemView.findViewById(R.id.material_quick_contact_badge);
                    break;
                case MainActivity.VIEW_QUICK_CONTACT_BADGE:
                    mContactView = (QuickContactBadge) itemView.findViewById(R.id.quick_contact_badge);
                    break;
                case MainActivity.VIEW_IMAGE_VIEW:
                    mContactView = (ImageView) itemView.findViewById(R.id.image_view);
                    break;
            }
            mTextView = (TextView) itemView.findViewById(R.id.tvName);
            mContactView.setVisibility(View.VISIBLE);
        }
    }
}
