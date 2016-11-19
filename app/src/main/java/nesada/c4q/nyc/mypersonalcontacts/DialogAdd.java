package nesada.c4q.nyc.mypersonalcontacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import io.realm.Realm;
import nesada.c4q.nyc.mypersonalcontacts.beans.Drop;

/**
 * Created by Nesada on 10/23/2016.
 */
// this class we are using to create a custom dialog, with a custom layout. pretty much same like we would use an activity

public class DialogAdd extends DialogFragment {

    private ImageButton mBtnClose;
    private EditText mInputWhat;
    private EditText mContactNumber;
    private Button mBtnAddIt;

    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) { // if the button 'Add it' is clicked call the method 'addAction'
                case R.id.btn_add_it:
                    addAction();
                break;
            }
            dismiss();
        }
    };

    private void addAction() {
        String what = mInputWhat.getText().toString(); // get the value of the 'goal' or 'to do'
        String phone = mContactNumber.getText().toString();

        Realm realm = Realm.getDefaultInstance();
        //we create the object for the class 'Drop'
        Drop drop = new Drop(what,phone);
        realm.beginTransaction();
        realm.copyToRealm(drop);
        realm.commitTransaction();
        realm.close();
    }

    public DialogAdd() {//constructor of this class
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClose = (ImageButton) view.findViewById(R.id.btn_close);
        mInputWhat = (EditText)view.findViewById(R.id.et_drop);
        mContactNumber = (EditText) view.findViewById(R.id.et_contact_number);
        mBtnAddIt = (Button) view.findViewById(R.id.btn_add_it);

       mBtnClose.setOnClickListener(mBtnClickListener);
       mBtnAddIt.setOnClickListener(mBtnClickListener);

    }
}
