package ua.lelpel.phonebook.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.lelpel.phonebook.R;

/**
 * Created by bruce on 01.04.2018.
 */

public class AddContactDialog extends DialogFragment {
    private OnDialogInteractionListener listener;

    @BindView(R.id.et_add_contact_firstname)
    EditText firstNameEt;

    @BindView(R.id.et_add_contact_lastname)
    EditText lastNameEt;

    @BindView(R.id.et_add_contact_phone)
    EditText phoneEt;

    @BindView(R.id.et_add_contact_email)
    EditText emailEt;

    @BindView(R.id.button_photo_picker)
    Button photoPicker;

    private Uri uri;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_contact_dialog, null);

        builder.setMessage(R.string.add).setView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onContactAddPositive(AddContactDialog.this);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onContactAddNegative(AddContactDialog.this);
            }
        });

        ButterKnife.bind(this, view);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (OnDialogInteractionListener) getActivity();
    }

    @OnClick(R.id.button_photo_picker)
    void pickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
        }
    }

    public Uri getUri() {
        return uri == null ? getBlankImage() : uri;
    }

    private Uri getBlankImage() {
        int drawableId = R.drawable.ic_launcher_background;

        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getActivity().getApplicationContext().getResources().getResourcePackageName(drawableId)
                + '/' + getActivity().getApplicationContext().getResources().getResourceTypeName(drawableId)
                + '/' + getActivity().getApplicationContext().getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

    public interface OnDialogInteractionListener {
        void onContactAddPositive(AddContactDialog dialog);

        void onContactAddNegative(AddContactDialog dialog);
    }

    public String getFirstName() {
        return firstNameEt.getText().toString();
    }

    public String getLastName() {
        return lastNameEt.getText().toString();
    }

    public String getPhone() {
        return phoneEt.getText().toString();
    }

    public String getEmail() {
        return emailEt.getText().toString();
    }
}
