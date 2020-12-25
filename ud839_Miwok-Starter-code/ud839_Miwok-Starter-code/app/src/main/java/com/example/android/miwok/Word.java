package com.example.android.miwok;

public class Word {

    private String mMiwokTranslation;
    private String mDefaultTranslation;

    public Word(String defaultTranslation, String miwokTranslation) {
        this.mDefaultTranslation = defaultTranslation;
        this.mMiwokTranslation = miwokTranslation;
    };

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    };

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    };

}
