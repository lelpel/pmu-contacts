package ua.lelpel.phonebook.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.lelpel.phonebook.App;
import ua.lelpel.phonebook.R;
import ua.lelpel.phonebook.data.entities.Contact;
import ua.lelpel.phonebook.data.repositories.ContactsRepository;
import ua.lelpel.phonebook.presentation.ContactsListPresenter;
import ua.lelpel.phonebook.ui.adapters.ContactsRecyclerViewAdapter;
import ua.lelpel.phonebook.ui.dialogs.AddContactDialog;
import ua.lelpel.phonebook.ui.interfaces.OnCallListener;
import ua.lelpel.phonebook.ui.interfaces.OnContactItemClickListener;
import ua.lelpel.phonebook.ui.interfaces.OnShareListener;
import ua.lelpel.phonebook.ui.interfaces.OnSmsListener;

public class ContactsListActivity extends AppCompatActivity implements AddContactDialog.OnDialogInteractionListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.rv_all_contacts)
    RecyclerView allContactsList;

    private ContactsRecyclerViewAdapter adapter;
    private ContactsListPresenter presenter;
    private OnContactItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setSupportActionBar(toolbar);

        //allContactsList = findViewById(R.id.rv_all_contacts);
        ButterKnife.bind(this);

        App app = (App) getApplicationContext();

        presenter = new ContactsListPresenter(new ContactsRepository(app.getDb()), this);
        adapter = new ContactsRecyclerViewAdapter(presenter.fetchData(), callListener, smsListener, shareListener, onItemClickListener);
        //adapter = new ContactsRecyclerViewAdapter(presenter.fetchData(), callListener, smsListener, shareListener);
        //allContactsList.setLayoutManager();
        allContactsList.setAdapter(adapter);

        itemTouchHelper.attachToRecyclerView(allContactsList);

        toolbar.setTitle("Contacts");

        onItemClickListener = new OnContactItemClickListener() {
            @Override
            public void onClick(Contact c) {
            }
        };
    }

    private List<Contact> dummyData() {
        List<Contact> dummy = new ArrayList<>();
        dummy.add(new Contact("Lol", "Kek", "0123", "a@b.c", "/storage0/pic.png"));
        dummy.add(new Contact("KEX", "PEX", "0123", "a@b.c", "/storage0/pic.png"));
        return dummy;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    void onAddButtonClick() {
        AddContactDialog dialog = new AddContactDialog();
        dialog.show(getSupportFragmentManager(), "ADD_DIALOG");
    }

    private final OnCallListener callListener = new OnCallListener() {
        @Override
        public void onCall(Contact contact) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    };

    private final OnShareListener shareListener = new OnShareListener() {
        @Override
        public void onShare(Contact contact) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, contact.getFullName() + "\nPhone:" + contact.getPhoneNumber());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    };

    private final OnSmsListener smsListener = new OnSmsListener() {
        @Override
        public void onSend(Contact contact) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("smsto:" + contact.getPhoneNumber()));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    };

    @Override
    public void onContactAddPositive(AddContactDialog dialog) {
        presenter.onAdd(dialog.getFirstName(), dialog.getLastName(), dialog.getPhone(), dialog.getEmail(), dialog.getUri());

        //adapter.update(presenter.fetchData());

        Log.i("CLACTIVITY", "ADDED");
    }

    @Override
    public void onContactAddNegative(AddContactDialog dialog) {
        dialog.dismiss();
    }

    public void showData(List<Contact> data) {
        adapter.update(data);
    }


    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            Contact c = adapter.getItemById(viewHolder.getAdapterPosition());

            if (swipeDir == ItemTouchHelper.LEFT) {
                presenter.onSwipeLeft(c);
            } else if (swipeDir == ItemTouchHelper.RIGHT) {
                Intent i = new Intent();
                i.setClass(ContactsListActivity.this, ContactDetailsActivity.class);
                i.putExtra(ContactDetailsActivity.ARG_CONTACT_ID, c.getId());
                startActivity(i);
                adapter.update(presenter.fetchData());
            }
        }
    };

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
}
