package ua.lelpel.phonebook.ui.interfaces;

import android.app.Activity;

import ua.lelpel.phonebook.data.entities.Contact;

/**
 * Created by bruce on 31.03.2018.
 */

public interface OnSmsListener {
    void onSend(Contact contact);
}
