package co.cyware.ftpclient.service.persistence;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Anoop S S on 20/12/15.
 * anoopss@schoolspeak.com
 */
public class PersistenceImpl implements IPersistenceService {

    private SharedPreferences mSharedPreference;

    private static final String PREF_NAME = "FTP_FOX_SHARED_PREF";

    public PersistenceImpl(Context context) {
        mSharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public String getString(String key) {
        return mSharedPreference.getString(key, null);
    }

    @Override
    public void clearAll() {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.clear();
        editor.commit();
    }
}
