package ua.lelpel.phonebook.presentation;

import ua.lelpel.phonebook.data.entities.Contact;
import ua.lelpel.phonebook.data.repositories.ContactsRepository;
import ua.lelpel.phonebook.ui.ContactDetailsActivity;
import ua.lelpel.phonebook.ui.ContactsListActivity;

/**
 * @author bruce
 * @date 11.04.2018
 */
public class ContactsDetailsPresenter {

    private final ContactsRepository repository;
    private final ContactDetailsActivity view;

    public ContactsDetailsPresenter(ContactsRepository repository, ContactDetailsActivity view) {
        this.repository = repository;
        this.view = view;
    }

    public void onAttach(long id) {
        Contact c = repository.getById(id);
        view.showData(c);
    }
}
