package creationsofali.asynctaskdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etSleepTime;
    private Button buttonRunAsync;
    private TextView tvAfterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencing views
        buttonRunAsync = (Button) findViewById(R.id.buttonRunAsync);
        tvAfterTask = (TextView) findViewById(R.id.tvAfterTask);
        etSleepTime = (EditText) findViewById(R.id.etSleepTime);

        buttonRunAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = etSleepTime.getText().toString();

                //Check if the time is provided
                if(sleepTime.equals("")){
                    //if field is empty, user must enter the time
                    Toast.makeText(MainActivity.this, "Field is empty! Please provide sleep time.", Toast.LENGTH_LONG).show();
                }else {
                    //if the time is provided, execute task
                    runner.execute(sleepTime);
                }
            }
        });
    }

    //AsyncTask generic types, <Params, Progress, Result>
    private class AsyncTaskRunner extends AsyncTask<String, String, String>{

        String response;
        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {
            publishProgress("ZZZzzzz... .!");  //Calls onProgressUpdate()

            try{
                //Capture passed time (seconds) and convert it to milliseconds
                int time = Integer.parseInt(params[0]) * 1000;
                //Sleep
                Thread.sleep(time);

                response = "Slept for "+ params[0] +" seconds";

            }catch (InterruptedException e){
                e.printStackTrace();
                response = e.getMessage();

            }catch (Exception e){
                e.printStackTrace();
                response = e.getMessage();
            }

            return response;
        }

        //Called by publishProgress()
        @Override
        protected void onProgressUpdate(String... values) {
            //Setting value passed by publishProgress()
            tvAfterTask.setText(values[0]);
        }

        //During execution
        @Override
        protected void onPreExecute() {
            //3 params on show(context, dialog title, dialog message)
            progressDialog = ProgressDialog.show(MainActivity.this, "On Sleep",
                    "Wait for "+ etSleepTime.getText().toString() +" seconds!");
        }


        //After complete task
        @Override
        protected void onPostExecute(String s) {
            //Setting results after a complete execution of the task
            progressDialog.dismiss();
            tvAfterTask.setText(s);
        }
    }
}
