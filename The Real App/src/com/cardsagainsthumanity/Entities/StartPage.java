package com.cardsagainsthumanity.Entities;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StartPage extends Activity {
	
	private EditText inputUsername;
	private EditText inputPassword;
	private String userName;
	private String password;
    private EditText urlText;
    private TextView login;
    private String results;
    private String UserId;
    Button v;
    public static final String SPREF_USER = "othPrefs";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		v = (Button) findViewById(R.id.LogIn);
		
		
		inputUsername = (EditText) findViewById(R.id.username);
		inputPassword = (EditText) findViewById(R.id.password);
		login = (TextView) findViewById(R.id.myText);
		
		v.setOnClickListener(new OnClickListener() {
			
	        public void onClick(View v) 
	        {
	        	//login
	        	userName = inputUsername.getText().toString();
	        	password = inputPassword.getText().toString();
	        	String stringUrl = "http://54.225.225.185:8080/ServerAPP/Login?User="+userName+"&password="+password;
	        	ConnectivityManager connMgr = (ConnectivityManager) 
        		getSystemService(Context.CONNECTIVITY_SERVICE);
                
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    Toast.makeText(v.getContext(), "Logging in", Toast.LENGTH_SHORT).show();
                    new DownloadWebpageText().execute(stringUrl);
                    
                } else {
                    login.setText("No network connection available.");
                }
                
        	}
			

        }); 
		
		Button ca = (Button) findViewById(R.id.CreateAccount);
	
		ca.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) 
			{
				Intent myIntent = new Intent(v.getContext(), CreateAccount.class);
                startActivity(myIntent);
				
			}
			
		});
	}
	
	 private class DownloadWebpageText extends AsyncTask {
	        
	    	@Override
	        protected Object doInBackground(Object... urls) {
	            // params comes from the execute() call: params[0] is the url.
	            try {
	                return downloadUrl((String) urls[0]);
	            } catch (IOException e) {
	                return "Unable to retrieve web page. URL may be invalid.";
	            }
	        }
	    	
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(Object result) {
	            results = (String) result.toString();
	            results = results.trim();
	            //check the result for the what's needed to move on
	            String[] resultArr = results.split(":"); 
                if(resultArr[0].equals("User")){
                	
                	//Store Username in SharedPref
                	SharedPreferences othSettings = getSharedPreferences(SPREF_USER, 0);
                	SharedPreferences.Editor spEditor = othSettings.edit();
                	spEditor.putString("UserName", userName.toString()).commit();
                	UserId = resultArr[1].toString();
                	//End storing username
                	
                	//Need to encrypt password and store
                	//Random rng = new Random();
                	//String kcharacters = "abcdefghijklmnopqrstuvwxyz0123456789";
                	//int kl = 16;
                	//String ciphSeed = generateString(rng, kcharacters, kl);
                	try { 
                		Toast.makeText(v.getContext(),"FUCK", Toast.LENGTH_LONG).show();
						byte[] userInput = Base64.decode(password);
						Toast.makeText(v.getContext(),"FUCK2", Toast.LENGTH_LONG).show();
						byte[] sKey = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6};
						Toast.makeText(v.getContext(),"FUCK3", Toast.LENGTH_LONG).show();
	                	//Random generate byte array
	                	/* sKey[0] = (byte) 'p';
	                	sKey[1] = (byte) 'a';
	                	sKey[2] = (byte) 's';
	                	sKey[3] = (byte) 's';
	                	sKey[4] = (byte) 'w';
	                	sKey[5] = (byte) 'o';
	                	sKey[6] = (byte) 'r';
	                	sKey[7] = (byte) 'd';
	                	sKey[8] = (byte) 'c';
	                	sKey[9] = (byte) 'o';
	                	sKey[10] = (byte) 'm';
	                	sKey[11] = (byte) 'r';
	                	sKey[12] = (byte) 'a';
	                	sKey[13] = (byte) 'd';
	                	sKey[14] = (byte) 'e';
	                	sKey[15] = (byte) 's'; */
	                	
	                	for(int i=0; i< userInput.length; i++)
	                	{
	                		sKey[i] = userInput[i];
	                	}
	                	String epw = encrypt(password, sKey);
	                	spEditor.putString("digest", epw);
	                	//Test encryption
	                	Log.d("nig", epw);
	                	
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	
                	//End store password
                	
                	//Inform user username stored -------JK 
                	
                	/*String testun = othSettings.getString("UserName", null);
                	if(testun.equals(null))
                		Toast.makeText(v.getContext(), "UserName not stored", Toast.LENGTH_SHORT).show();
                	if(testun.equals(userName))
                		Toast.makeText(v.getContext(), userName + "Logged in", Toast.LENGTH_SHORT).show();
                	else
                		Toast.makeText(v.getContext(), "undet", Toast.LENGTH_SHORT).show(); */
                	//End  inform  --------------JK
                	

                	
                	Intent myIntent = new Intent(v.getContext(), MainMenu.class);
                	myIntent.putExtra("UserName", userName);
                	myIntent.putExtra("UserId", UserId);
                	login.setText("");
                	startActivityForResult(myIntent, 0);
                	StartPage.this.finish();	//Close login page when Main<enu starts
                	
                }
                else{
                	login.setText(results);
                }
	            
	            
	       }

	     }
	
	 private String downloadUrl(String myurl) throws IOException {
	      InputStream is = null;
	      // Only display the first 500 characters of the retrieved
	      // web page content.
	      int len = 500;
	          
	      try {
	          URL url = new URL(myurl);
	          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	          conn.setReadTimeout(10000 /* milliseconds */);
	          conn.setConnectTimeout(15000 /* milliseconds */);
	          conn.setRequestMethod("GET");
	          conn.setDoInput(true);
	          // Starts the query
	          conn.connect();
	          int response = conn.getResponseCode();
	          Log.d("FUCK", "The response is: " + response);
	          is = conn.getInputStream();

	          // Convert the InputStream into a string
	          String contentAsString = readIt(is, len);
	          return contentAsString;
	          
	      // Makes sure that the InputStream is closed after the app is
	      // finished using it.
	      } finally {
	          if (is != null) {
	              is.close();
	          } 
	      }
	  }
		
	  	//Reads an InputStream and converts it to a String.
		public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
		   Reader reader = null;
		   reader = new InputStreamReader(stream, "UTF-8");        
		   char[] buffer = new char[len];
		   reader.read(buffer);
		   return new String(buffer);
		}  
	     
		//Generates random String for password encryption
		public static String generateString(Random rng, String characters, int length)
		{
		    char[] text = new char[length];
		    for (int i = 0; i < length; i++)
		    {
		        text[i] = characters.charAt(rng.nextInt(characters.length()));
		    }
		    return new String(text);
		}
		
		public static String encrypt(String toEncrypt, byte[ ] key) throws Exception {
	        //Create your Secret Key Spec, which defines the key transformations
	        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
	       

	        //Get the cipher
	        Cipher cipher = Cipher.getInstance("AES");

	        //Initialize the cipher
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

	        //Encrypt the string into bytes
	        byte[ ] encryptedBytes = cipher.doFinal(toEncrypt.getBytes());

	        //Convert the encrypted bytes back into a string
	        String encrypted = Base64.encodeBytes(encryptedBytes);

	        return encrypted;
	}
		
	public static String decrypt(String encryptedText, byte[ ] key) throws Exception {
	        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
	      
	        Cipher cipher = Cipher.getInstance("AES");

	        cipher.init(Cipher.DECRYPT_MODE, skeySpec);

	        byte[] toDecrypt = Base64.decode(encryptedText);
	        
	        byte[] encrypted = cipher.doFinal(toDecrypt);

	        return new String(encrypted);
	}	
	
}
