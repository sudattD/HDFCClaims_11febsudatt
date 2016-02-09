package com.hdfc.claims.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.hdfc.claims.R;


/**
 * This class contains Utility methods for this application
 * 
 * @author Zen Bhatt
 * 
 */
public class Utility {

	static NoticeDialogListener mListener;

	public interface NoticeDialogListener {
		public void onDialogPositiveClick(DialogInterface builder);

		public void onDialogNegativeClick(DialogInterface dialog);
	}

	/**
	 * method to show an alert with OK Button
	 * 
	 * @param message
	 *            message of application
	 * @param mContext
	 *            Context of that class to open dialog
	 */
	public static void showAlert(String message, Context mContext,
			final NoticeDialogListener mDialogListener) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

		builder.setTitle(mContext.getResources().getString(R.string.app_name));

		// mListener = (NoticeDialogListener) mContext;
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setPositiveButton(
				mContext.getResources().getString(R.string.alert_ok),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						mDialogListener.onDialogPositiveClick(dialog);
					}
				});
		builder.show();

	}
	
	
	public static void showAlertWarning(String message, Context mContext) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

		builder.setTitle("Error");

		// mListener = (NoticeDialogListener) mContext;
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setPositiveButton(
				mContext.getResources().getString(R.string.alert_ok),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						
					}
				});
		builder.show();

	}

	/**
	 * Show an Alert Dialog with Two Buttons Ok,Cancel
	 * 
	 * @param message
	 *            message of Alert Dialog
	 * @param mContext
	 *            Context to open dialog
	 * @param Listener
	 *            Listener to call
	 */
	public static void showAlertOKCancel(String message, Context mContext,
			NoticeDialogListener Listener) {

		final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

		builder.setTitle(mContext.getResources().getString(R.string.app_name));
		mListener = Listener;

		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setPositiveButton(
				mContext.getResources().getString(R.string.alert_ok),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						mListener.onDialogPositiveClick(dialog);
					}
				});
		builder.setNegativeButton(
				mContext.getResources().getString(R.string.alert_cancel),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						mListener.onDialogNegativeClick(dialog);
					}
				});
		builder.show();
	}

	/**
	 * method to check Internet connectivity
	 * 
	 * @param context
	 *            -context of activity
	 * @return true if Internet is available
	 */
	public static boolean checkInternetConnection(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager.getActiveNetworkInfo() != null

		&& connectivityManager.getActiveNetworkInfo().isAvailable()

		&& connectivityManager.getActiveNetworkInfo().isConnected()) {

			return true;
		} else {
			Log.v("INTERNETWORKING", "Internet not present");
			return false;
		}

	}

	/***
	 * method to check whether text of Edit text is empty
	 * 
	 * @param editText
	 *            -edtit text to be checked
	 * @return -false if empty
	 */
	public static boolean isTextEmpty(EditText editText) {
		if (editText.getText().toString().trim().equalsIgnoreCase("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method is for to check the email is valid or not
	 * 
	 * @param email
	 *            :- String from edit text
	 * @return
	 */

	public static boolean isEmailValid(String email) {
		boolean isValid;
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		} else {

			Log.i(".....Email", "Not valid");

			isValid = false;

		}
		return isValid;
	}
    
	


	

	
	

}
