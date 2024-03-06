package android.example.dai2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    RadioGroup rg;
    RadioButton rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        rg= (RadioGroup) findViewById(R.id.rgroup);}

    public void rbclick(View v){

        int radiobuttonid = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) findViewById(radiobuttonid);
        Toast.makeText(getBaseContext(), rb.getText(), Toast.LENGTH_SHORT).show();
    }


}
