package com.sequenia.autoservices.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sequenia.autoservices.Global;
import com.sequenia.autoservices.R;
import com.sequenia.autoservices.widgets.CarMarkSpinner;
import com.sequenia.autoservices.widgets.EditTextWithLabel;

import java.util.ArrayList;

public class LoginActivity extends ActionBarActivity {

    private EditTextWithLabel nameEditText;
    private EditTextWithLabel phoneEditText;
    private EditTextWithLabel registrationNumberEditText;
    private CarMarkSpinner carMarkSpinner;

    private TextView nameLabel;
    private TextView phoneLabel;
    private TextView registrationNumberLabel;
    private TextView carMarkLabel;

    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initInputs();
        initButtons();
        initLabels();
        bindLabels();

        setupColors();
    }

    private void initInputs() {
        nameEditText = (EditTextWithLabel) findViewById(R.id.name);
        phoneEditText = (EditTextWithLabel) findViewById(R.id.phone);
        registrationNumberEditText = (EditTextWithLabel) findViewById(R.id.car_registration_number);
        carMarkSpinner = (CarMarkSpinner) findViewById(R.id.car_mark);
    }

    private void initLabels() {
        nameLabel = (TextView) findViewById(R.id.name_label);
        phoneLabel = (TextView) findViewById(R.id.phone_label);
        registrationNumberLabel = (TextView) findViewById(R.id.car_registration_number_label);
        carMarkLabel = (TextView) findViewById(R.id.car_mark_label);
    }

    private void bindLabels() {
        nameEditText.setLabel(nameLabel);
        phoneEditText.setLabel(phoneLabel);
        registrationNumberEditText.setLabel(registrationNumberLabel);
        carMarkSpinner.setLabel(carMarkLabel);
    }

    private void setupColors() {
        Resources resources = getResources();
        int textColor = resources.getColor(R.color.color_white);
        int hintColor = resources.getColor(R.color.white70);
        int background = R.drawable.edit_text_bg_light;

        nameEditText.setTextColor(textColor);
        phoneEditText.setTextColor(textColor);
        registrationNumberEditText.setTextColor(textColor);
        carMarkSpinner.setTextColor(textColor);

        nameEditText.setHintTextColor(hintColor);
        phoneEditText.setHintTextColor(hintColor);
        registrationNumberEditText.setHintTextColor(hintColor);
        carMarkSpinner.setTextHintColor(hintColor);

        nameEditText.setBackgroundResource(background);
        phoneEditText.setBackgroundResource(background);
        registrationNumberEditText.setBackgroundResource(background);
        carMarkSpinner.setBackgroundResource(background);

        nameLabel.setTextColor(hintColor);
        phoneLabel.setTextColor(hintColor);
        registrationNumberLabel.setTextColor(hintColor);
        carMarkLabel.setTextColor(hintColor);
    }

    private void initButtons() {
        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        Integer carMarkId = carMarkSpinner.getSelectedCarMarkId();
        String registrationNumber = registrationNumberEditText.getText().toString();

        if(Global.validateName(this, name) &&
                Global.validatePhone(this, phone) &&
                Global.validateCarMarkId(this, carMarkId) &&
                Global.validateRegistrationNumber(this, registrationNumber)) {
            System.out.println("ADASDASDADAAD");
        }
    }

    private void showMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
