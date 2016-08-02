package ua.eldarium.chat;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.net.InetAddress;

import fi.iki.elonen.util.ServerRunner;

public class MainActivity extends AppCompatActivity {


    Server server;

    @Override
    protected void onDestroy() {
        server.closeAllConnections();
        server.stop();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button start = (Button) findViewById(R.id.startser);
        Button send = (Button)findViewById(R.id.sendshit);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ServerMaker().execute();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ServerMaker extends AsyncTask<Void,Void,Void>{
        String host;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                server = new Server();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                server.start();
                host=server.getHostname();
            }
            catch (Exception ex){
                Log.d("asda",ex.getMessage());
            }
            finally {
                if (server!= null) server.stop();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //WebView webv;
            //webv= (WebView) findViewById(R.id.webv);
            //webv.loadUrl("http://localhost:65335");
            WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            String ipAddress = Formatter.formatIpAddress(ip);
            try {
                Toast.makeText(MainActivity.this, ipAddress, Toast.LENGTH_LONG).show();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
