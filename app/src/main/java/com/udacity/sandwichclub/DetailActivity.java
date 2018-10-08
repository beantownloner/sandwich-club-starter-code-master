package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = DetailActivity.class.getSimpleName();
    private ImageView ingredientsIv ;
    private TextView alsoKnownAs;
    private TextView ingredients;
    private TextView origin ;
    private TextView description ;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

         ingredientsIv = findViewById(R.id.image_iv);
         alsoKnownAs = findViewById(R.id.also_known_tv);
         ingredients = findViewById(R.id.ingredients_tv);
         origin = findViewById(R.id.origin_tv);
         description = findViewById(R.id.description_tv);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        Log.d(TAG,position+"");
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Log.d(TAG,json);
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable

            closeOnError();
            return;
        }

        populateUI();

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
        for (String aka : sandwich.getAlsoKnownAs()) {

            alsoKnownAs.append(aka+"\n");
        }

        for (String igredient : sandwich.getIngredients()) {

            ingredients.append(igredient+"\n");
        }
        description.setText(sandwich.getDescription());
        origin.setText((sandwich.getPlaceOfOrigin()));
    }
}
