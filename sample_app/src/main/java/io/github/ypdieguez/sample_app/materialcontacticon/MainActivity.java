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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final int VIEW_MATERIAL_QUICK_CONTACT_BADGE = 1;
    public static final int VIEW_QUICK_CONTACT_BADGE = 2;
    public static final int VIEW_IMAGE_VIEW = 3;

    public static final int METHOD_CONTACT_PHOTO_MANAGER = 1;
    public static final int METHOD_LETTER_TILE_DRAWABLE = 2;

    public static boolean IS_CIRCLULAR = true;
    public static int VIEW = 1;
    public static int METHOD = 1;
    public static boolean WHITH_OVERLAY = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_contact_photo_manager:
                METHOD = METHOD_CONTACT_PHOTO_MANAGER;
                break;
            case R.id.rb_letter_tile_drawable:
                METHOD = METHOD_LETTER_TILE_DRAWABLE;
                break;
            case R.id.rb_material_quick_contact_badge:
                VIEW = VIEW_MATERIAL_QUICK_CONTACT_BADGE;
                break;
            case R.id.rb_quick_contact_badge:
                VIEW = VIEW_QUICK_CONTACT_BADGE;
                break;
            case R.id.rb_image_view:
                VIEW = VIEW_IMAGE_VIEW;
                break;
            case R.id.rb_circle:
                IS_CIRCLULAR = true;
                break;
            case R.id.rb_square:
                IS_CIRCLULAR = false;
                break;
        }
    }

    public void onButtonClicked(View v) {
        Intent i = new Intent(this, ContactListActivity.class);
        startActivity(i);
    }
}
