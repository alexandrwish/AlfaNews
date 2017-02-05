package com.alfa.news.common;

import android.annotation.TargetApi;
import android.os.Build;
import android.telephony.PhoneNumberFormattingTextWatcher;

import com.alfa.news.listener.PhoneListener;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

public class PhoneNumberWatcher extends PhoneNumberFormattingTextWatcher {

    private static final PhoneNumberUtil sPhoneUtil = PhoneNumberUtil.getInstance();
    private String mIso = Constants.DEFAULT_COUNTRY;
    private PhoneListener mListener;

    @SuppressWarnings("unused")
    public PhoneNumberWatcher() {
        super();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PhoneNumberWatcher(String countryCode) {
        super(countryCode);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        CharSequence tmp = s;
        String iso = getIso(s);
        while (iso == null
                && tmp.toString().replaceAll("[\\D]", "").length() > 0
                && tmp.toString().replaceAll("[\\D]", "").length() < 14) {
            tmp = tmp + "0";
            iso = getIso(tmp);
        }
        if (iso != null) {
            if (!mIso.equalsIgnoreCase(iso)) {
                mIso = iso;
            }
            mListener.find(mIso);
        } else {
            mListener.find(mIso);
        }
    }

    public void setListener(PhoneListener listener) {
        mListener = listener;
    }

    private String getIso(CharSequence s) {
        try {
            return sPhoneUtil.getRegionCodeForNumber(sPhoneUtil.parse(s.toString(), mIso));
        } catch (Exception ignore) {
        }
        return null;
    }
}