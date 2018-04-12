package ua.lelpel.phonebook.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ua.lelpel.phonebook.R;
import ua.lelpel.phonebook.data.entities.Contact;
import ua.lelpel.phonebook.ui.adapters.vh.ContactItemViewHolder;
import ua.lelpel.phonebook.ui.interfaces.OnCallListener;
import ua.lelpel.phonebook.ui.interfaces.OnContactItemClickListener;
import ua.lelpel.phonebook.ui.interfaces.OnShareListener;
import ua.lelpel.phonebook.ui.interfaces.OnSmsListener;

/**
 * Created by bruce on 31.03.2018.
 */

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactItemViewHolder> {
    private final List<Contact> data;

    private OnCallListener onCallListener;
    private OnSmsListener onSmsListener;
    private OnShareListener onShareListener;
    private OnContactItemClickListener onClickListener;

    public ContactsRecyclerViewAdapter(List<Contact> data, OnCallListener onCallListener, OnSmsListener onSmsListener, OnShareListener onShareListener, OnContactItemClickListener onClickListener) {
        this.data = data;
        this.onCallListener = onCallListener;
        this.onSmsListener = onSmsListener;
        this.onShareListener = onShareListener;
        this.onClickListener = onClickListener;
    }

    @Override
    public ContactItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        final ContactItemViewHolder vh = new ContactItemViewHolder(view, onShareListener, onCallListener, onSmsListener, onClickListener);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickListener.onClick(data.get(vh.getAdapterPosition()));
//            }
//        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ContactItemViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void update(List<Contact> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public Contact getItemById(int adapterPosition) {
        return data.get(adapterPosition);
    }
}
