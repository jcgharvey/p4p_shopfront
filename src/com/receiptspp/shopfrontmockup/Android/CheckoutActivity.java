package com.receiptspp.shopfrontmockup.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.receiptspp.shopfrontmockup.R;
import com.receiptspp.shopfrontmockup.business.Cart;
import com.receiptspp.shopfrontmockup.business.MockReceipt;
import com.receiptspp.shopfrontmockup.business.Product;
import com.receiptspp.shopfrontmockup.common.Keys;
import com.receiptspp.shopfrontmockup.common.Util;

public class CheckoutActivity extends Activity {

	@SuppressWarnings("unused")
	private NfcAdapter mNfcAdapter;
	private Cart cart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout);

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

		cart = Cart.getInstance();

		TextView subTotalView = (TextView) findViewById(R.id.checkoutProductSubtotal);
		subTotalView.setText(Util.generatePriceString(cart
				.getTotalTransaction()));

		ListView checkoutProductContainer = (ListView) findViewById(R.id.checkoutProductContainer);

		ArrayAdapter<Product> adapter = new CartViewArrayAdapter(this,
				cart.getProductsInCart());

		checkoutProductContainer.setAdapter(adapter);

		Button checkoutConfirmButton = (Button) findViewById(R.id.checkoutConfirmButton);

		checkoutConfirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String userId = cart.getUserId();
				String text = "";
				if (userId == null) {
					text = "No user ID Received, can't upload receipt";
					Toast.makeText(getApplicationContext(), text,
							Toast.LENGTH_LONG).show();
				} else {
					text = "User ID is " + userId;
					Toast.makeText(getApplicationContext(), text,
							Toast.LENGTH_LONG).show();
					MockReceipt receipt = new MockReceipt(true);
					receipt.setName("Trimtex Mock Store");
					SubmitJsonToServer jsonUpload = new SubmitJsonToServer();
					try {
						String jsonString = receipt.toJSON().toString();
						jsonUpload.execute(jsonString);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.checkout, menu);
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
		// Check to see that the Activity started due to an Android Beam
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processIntent(getIntent());
		}
	}

	/**
	 * Get any messages from the intent and upload them to the server using the
	 * SubmitVitalStats task
	 * 
	 * @param intent
	 */
	void processIntent(Intent intent) {

		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		// only one message sent during the beam
		Toast.makeText(getApplicationContext(), "Got intent",
				Toast.LENGTH_SHORT).show();
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		String json = new String(msg.getRecords()[0].getPayload());
		String userId = "";
		long receivedTime = 0;
		try {
			JSONObject userData = new JSONObject(json);
			userId = (String) userData.get("id");
			receivedTime = (long) userData.getLong("time");
			Toast.makeText(getApplicationContext(),
					"Received NDEF from " + userId + " @" + receivedTime,
					Toast.LENGTH_LONG).show();
			Cart cart = Cart.getInstance();
			cart.setUserId(userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Async task for network access
	 * 
	 * @author Jourdan Harvey
	 * 
	 */
	private class SubmitJsonToServer extends AsyncTask<String, Void, Boolean> {

		private DefaultHttpClient httpclient;

		@Override
		protected Boolean doInBackground(String... params) {

			// instantiates httpclient to make request
			httpclient = new DefaultHttpClient();

			// passes the results to a string builder/entity
			StringEntity jsonSE = null;
			String jsonString;
			try {
				jsonString = params[0].toString();
			} catch (IndexOutOfBoundsException e) {
				// incorrect msg
				return false;
			}

			try {
				jsonSE = new StringEntity(jsonString);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			}

			boolean result = true;

			String userId = Cart.getInstance().getUserId();

			String url = Keys.receiptsEndpointStart + userId
					+ Keys.receiptsEndpointEnd;

			Log.v("checkout", url);
			Log.v("checkout", jsonString);

			// call httpPost method to post the json to the server
			result = httpPost(jsonSE, new HttpPost(url));

			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		}

		/**
		 * Take an HttpPost and execute it
		 * 
		 * @param se
		 * @param httppost
		 * @return
		 */
		private boolean httpPost(StringEntity se, HttpPost httppost) {
			// sets the post request as the resulting string
			httppost.setEntity(se);
			// sets a request header so the page receiving the request
			// will know what to do with it
			httppost.setHeader("Accept", "application/json");
			httppost.setHeader("Content-type", "application/json");

			try {
				// execute, get response, read
				HttpResponse response = httpclient.execute(httppost);
				InputStream content = response.getEntity().getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						content));
				String line;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				Log.v("checkout", "Response:" + sb.toString());
				// failing catches
			} catch (ClientProtocolException e) {

				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			// winning
			return true;
		}
	}

}
