package ua.lelpel.phonebook.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.lelpel.phonebook.App;
import ua.lelpel.phonebook.R;
import ua.lelpel.phonebook.data.entities.Contact;
import ua.lelpel.phonebook.data.repositories.ContactsRepository;
import ua.lelpel.phonebook.presentation.ContactsDetailsPresenter;
import ua.lelpel.phonebook.presentation.ContactsListPresenter;

import static com.bumptech.glide.request.RequestOptions.circleCropTransform;

public class ContactDetailsActivity extends AppCompatActivity {
    @BindView(R.id.details_toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_details_first_name)
    TextView firstName;

    @BindView(R.id.tv_details_last_name)
    TextView lastName;

    @BindView(R.id.tv_details_phone)
    TextView phone;

    @BindView(R.id.tv_details_email)
    TextView email;

    @BindView(R.id.img_details_photo)
    ImageView photo;

    public static final String ARG_CONTACT_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        App app = (App) getApplicationContext();
        ContactsDetailsPresenter presenter = new ContactsDetailsPresenter(new ContactsRepository(app.getDb()), this);

        presenter.onAttach(getIntent().getLongExtra(ARG_CONTACT_ID, 0));
    }

    public void showData(Contact c) {
        firstName.setText(c.getFirstName());
        lastName.setText(c.getLastName());
        phone.setText(c.getPhoneNumber());
        email.setText(c.getEmail());

        Glide.with(this).load(Uri.parse(c.getImagePath())).apply(circleCropTransform()).into(photo);
    }
}
