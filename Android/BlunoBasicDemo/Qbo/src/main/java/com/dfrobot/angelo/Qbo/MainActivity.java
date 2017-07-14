package com.dfrobot.angelo.Qbo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity  extends BlunoLibrary {
	private Button buttonScan;
	private Button button1,button2,button3,button4,button5,button6;

	//private EditText serialSendText;
	private TextView serialReceivedText;

	//private byte[] b={55 aa 11 01 00 01 00 00 00 00 15};



	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        onCreateProcess();														//onCreate Process by BlunoLibrary


        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

       // serialReceivedText=(TextView) findViewById(R.id.serialReveicedText);	//initial the EditText of the received data
      //  serialSendText=(EditText) findViewById(R.id.serialSendText);			//initial the EditText of the sending data
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String code1 = new String("55 aa 11 01 00 01 00 00 00 00 12");
				serialSend(code1);				//send the data to the BLUNO
			}
		});

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String code2 = new String("55 aa 11 01 00 02 00 00 00 00 13");
				serialSend(code2);				//send the data to the BLUNO
			}
		});

		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String code3 = new String("55 aa 11 01 00 03 00 00 00 00 14");
				serialSend(code3);				//send the data to the BLUNO
			}
		});

		button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String code4 = new String("55 aa 11 01 00 04 00 00 00 00 15");
				serialSend(code4);				//send the data to the BLUNO
			}
		});
		button5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String code5 = new String("55 aa 11 01 00 05 00 00 00 00 16");
				serialSend(code5);				//send the data to the BLUNO
			}
		});
		button6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String code6 = new String("55 aa 11 01 00 06 00 00 00 00 17");
				serialSend(code6);				//send the data to the BLUNO
			}
		});

		buttonScan = (Button) findViewById(R.id.bluetooth);					//initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
			}
		});

		JoystickView joystick = (JoystickView) findViewById(R.id.joystickView);
		joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
			@Override
			public void onMove(int angle, int strength) {
				// do whatever you want
			}
		});
	}

	protected void onResume(){
		super.onResume();
		System.out.println("BlUNOActivity onResume");
		onResumeProcess();														//onResume Process by BlunoLibrary
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
		super.onActivityResult(requestCode, resultCode, data);
	}
	
    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }
	
	protected void onStop() {
		super.onStop();
		onStopProcess();														//onStop Process by BlunoLibrary
	}
    
	@Override
    protected void onDestroy() {
        super.onDestroy();	
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

	@Override
	public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
		switch (theConnectionState) {											//Four connection state
		case isConnected:
			buttonScan.setBackgroundResource(R.drawable.bluetooth_connected);
			//buttonScan.setText("Connected");
			break;
		case isConnecting:
			//buttonScan.setText("Connecting");
			break;
		case isToScan:
			//buttonScan.setText("Scan");
			break;
		case isScanning:
			//buttonScan.setText("Scanning");
			break;
		case isDisconnecting:
			buttonScan.setBackgroundResource(R.drawable.bluetooth);
			//buttonScan.setText("isDisconnecting");
			break;
		default:
			break;
		}
	}

	@Override
	public void onSerialReceived(String theString) {							//Once connection data received, this function will be called
		// TODO Auto-generated method stub
	//	serialReceivedText.append(theString);							//append the text into the EditText
		//The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
		//((ScrollView)serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);
	}

}