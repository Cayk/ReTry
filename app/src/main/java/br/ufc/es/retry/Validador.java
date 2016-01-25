package br.ufc.es.retry;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

/**
 * Created by kerran on 24/01/16.
 */
public class Validador {

    public static boolean validateNotNull(View view, String mssg){
        if(view instanceof EditText){
            EditText editText = (EditText) view;
            Editable text = editText.getText();

            if(text != null){
                String strText = text.toString();
                if(!TextUtils.isEmpty(strText) && strText.trim().length()>0)
                    return true;
            }

            editText.setError(mssg);
            editText.setFocusable(true);
            editText.requestFocus();
            return false;
        }
        return false;
    }

    public static boolean validadeEmail(String email){
        if(TextUtils.isEmpty(email)){
            return false;
        }
        else{
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}
