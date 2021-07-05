package com.shaksham.presenter.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shaksham.R;
import com.shaksham.utils.PrefrenceFactory;
import com.shaksham.utils.PrefrenceManager;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangeMpinActivity extends AppCompatActivity {


    EditText id;

    EditText password;

    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mpin);
        id=(EditText)findViewById(R.id.id_fgd) ;
        password=(EditText)findViewById(R.id.passwd_fgd);
        submit=(Button) findViewById(R.id.submit_fgd);

        submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String s=     PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(),ChangeMpinActivity.this);
        String k=     PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyPassword(),ChangeMpinActivity.this);
        if((id!=null )&& (password!=null) &&(id.getText().toString().equalsIgnoreCase(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrfKeyLoginIdFromLocal(),ChangeMpinActivity.this))) && (password.getText().toString().equalsIgnoreCase(PrefrenceFactory.getInstance().getSharedPrefrencesData(PrefrenceManager.getPrefKeyPassword(),ChangeMpinActivity.this)))) {
            PrefrenceFactory.getInstance().saveSharedPrefrecesData(PrefrenceManager.getPrefPinStatus(), "1", ChangeMpinActivity.this);

            Intent intent = new Intent(ChangeMpinActivity.this, MpinActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(ChangeMpinActivity.this,getString(R.string.please_enter_valid_pin_details),Toast.LENGTH_SHORT).show();
        }
      }
    });
    }
}
