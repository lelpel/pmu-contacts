package ua.lelpel.phonebook.presentation;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ua.lelpel.phonebook.App;
import ua.lelpel.phonebook.data.entities.Contact;
import ua.lelpel.phonebook.data.repositories.ContactsRepository;
import ua.lelpel.phonebook.ui.ContactsListActivity;

/**
 * Created by bruce on 31.03.2018.
 */

public class ContactsListPresenter {
    private final ContactsRepository repository;
    private final ContactsListActivity view;
    private Contact byId;

    public ContactsListPresenter(ContactsRepository repository, ContactsListActivity view) {
        this.repository = repository;
        this.view = view;
        //repository = new ContactsRepository(app.getDb());
    }

    public List<Contact> fetchData() {
        return repository.getAllContacts();
    }

    public void onAdd(String firstName, String lastName, String phone, String email, Uri uri) {
        repository.addContact(firstName, lastName, phone, email, uri);
        view.showData(repository.getAllContacts());
    }

    public void onSwipeLeft(Contact c) {
        repository.delete(c);
        view.showData(repository.getAllContacts());
    }

    public Contact getById() {
        return byId;
    }
}
