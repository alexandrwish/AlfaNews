package com.alfa.news.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.alfa.news.R;
import com.alfa.news.common.Constants;
import com.alfa.news.common.PhoneNumberWatcher;
import com.alfa.news.listener.PhoneListener;

import butterknife.BindView;
import butterknife.OnClick;

@Layout(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements PhoneListener {

    @BindView(R.id.country)
    ImageView mCountry;
    @BindView(R.id.phone)
    EditText mPhone;

    private PhoneNumberWatcher mPhoneNumberWatcher = new PhoneNumberWatcher(Constants.DEFAULT_COUNTRY);

    @OnClick(R.id.login)
    public void onClick() {
        goNext();
    }

    protected void initActivity() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        if (preferences.getBoolean(Constants.FIRST_RUN, false)) {
            goNext();
        } else {
            mPhoneNumberWatcher.setListener(this);
            mPhone.addTextChangedListener(mPhoneNumberWatcher);
        }
        preferences.edit().putBoolean(Constants.FIRST_RUN, true).apply();
    }

    private void goNext() {
        startActivity(new Intent(SplashActivity.this, NewsActivity.class));
    }

    public void find(String s) {
        if (s != null && Constants.COUNTRY_2_PHONE.containsKey(s)) {
            mCountry.setImageDrawable(getDrawable(Constants.COUNTRY_2_PHONE.get(s)));
        } else {
            mCountry.setImageDrawable(getDrawable(R.drawable._unknown));
        }
    }
}