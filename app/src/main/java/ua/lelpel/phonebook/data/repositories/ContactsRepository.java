package ua.lelpel.phonebook.data.repositories;

import android.net.Uri;
import android.util.Log;

import java.util.List;

import ua.lelpel.phonebook.data.AppDb;
import ua.lelpel.phonebook.data.dao.ContactDao;
import ua.lelpel.phonebook.data.entities.Contact;

/**
 * Created by bruce on 30.03.2018.
 */

public class ContactsRepository {
    private final AppDb db;
    private ContactDao dao;

    public ContactsRepository(AppDb db) {
        this.db = db;
        dao = db.contactDao();
    }


    public List<Contact> getAllContacts() {
        return dao.getAll();
    }

    public void addContact(String firstName, String lastName, String phone, String email, Uri uri) {
        dao.add(new Contact(firstName, lastName, phone, email, uri.toString()));
        Log.i("REPO", "ADD");
    }

    public void delete(Contact c) {
        dao.delete(c);
    }

    public Contact getById(long id) {
        return dao.get(id);
    }
}
