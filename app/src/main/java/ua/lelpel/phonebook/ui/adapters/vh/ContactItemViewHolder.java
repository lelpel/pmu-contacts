package ua.lelpel.phonebook.ui.adapters.vh;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import ua.lelpel.phonebook.R;

import butterknife.ButterKnife;
import ua.lelpel.phonebook.data.entities.Contact;
import ua.lelpel.phonebook.ui.interfaces.OnCallListener;
import ua.lelpel.phonebook.ui.interfaces.OnContactItemClickListener;
import ua.lelpel.phonebook.ui.interfaces.OnShareListener;
import ua.lelpel.phonebook.ui.interfaces.OnSmsListener;

import static com.bumptech.glide.request.RequestOptions.circleCropTransform;
/**
 * Created by bruce on 31.03.2018.
 */

public class ContactItemViewHolder extends RecyclerView.ViewHolder implements Toolbar.OnMenuItemClickListener {
    private final OnShareListener shareListener;
    private final OnCallListener callListener;
    private final OnSmsListener smsListener;
    private final OnContactItemClickListener onClickListener;

    @BindView(R.id.text_view_contact)
    TextView name;
    @BindView(R.id.text_view_phone_number)
    TextView phone;
    @BindView(R.id.img_contact_photo)
    ImageView photo;
    @BindView(R.id.contact_item_toolbar)
    Toolbar toolbar;

    Contact item;
    View view;

    public ContactItemViewHolder(View itemView, OnShareListener shareListener, OnCallListener callListener, OnSmsListener smsListener, OnContactItemClickListener onClickListener) {
        super(itemView);
        view = itemView;
        this.shareListener = shareListener;
        this.callListener = callListener;
        this.smsListener = smsListener;
        this.onClickListener = onClickListener;

        ButterKnife.bind(this, itemView);

        toolbar.inflateMenu(R.menu.menu_contact_item);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareListener.onShare(this.item);
                return true;
            case R.id.action_sms:
                smsListener.onSend(this.item);
                return true;
            case R.id.action_call:
                callListener.onCall(this.item);
                return true;
            default:
                throw new IllegalArgumentException("Wrong menu item");
        }
    }

    public void bind(Contact contact) {
        this.item = contact;

        name.setText(item.getFullName());
        phone.setText(item.getPhoneNumber());

        //todo: pic
        Glide.with(view).load(Uri.parse(item.getImagePath())).apply(circleCropTransform()).into(photo);
    }
//
//    @Override
//    public void onClick(g) {
//        onClickListener.onClick();
//    }
}
