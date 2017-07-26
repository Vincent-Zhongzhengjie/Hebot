package com.dfrobot.angelo.Qbo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

public class MainActivity  extends BlunoLibrary implements GamepadImageButton.GamepadPressListener {
	private Button buttonScan;
	private GamepadImageButton button1,button2,button3,button4,button5,button6;

	private ArrayList<Byte> mButton = new ArrayList<Byte>();
	private ArrayList<Byte> mCoordinate = new ArrayList<Byte>(){{
		add((byte)0x80);
		add((byte)0x80);
		add((byte)0x0);
		add((byte)0x0);
	}};
	private ArrayList<Byte> mCmd = new ArrayList<Byte>(){{
		add((byte)0x55);
		add((byte)((int)0xaa & 0xff));
		add((byte)0x11);
		add((byte)0x0);
	}
	};

	//private EditText serialSendText;
	private TextView serialReceivedText;
	private int joyX=0x80;
	private int joyY=0x80;
	private int lastJoyX = 0x80;
	private int lastJoyY = 0x80;
	private boolean joyXUpdate = false;
	private boolean joyYUpdate = false;
	//private byte[] b={55 aa 11 01 00 01 00 00 00 00 15};


	@Override
	public void onButtonAction(View v, boolean isPressed) {
		int key = 0;
		if(v == button1){
			key = 1;
		}else if(v == button2){
			key = 2;
		}else if(v == button3){
			key = 3;
		}else if(v == button4){
			key = 4;
		}else if(v == button5){
			key = 5;
		}else if(v == button6){
			key = 6;
		}

		if(isPressed){
			if(!mButton.contains((byte)(key & 0xff))){
				mButton.add((byte)(key & 0xff));
			}
		}else{
			mButton.remove(mButton.indexOf((byte)(key & 0xff)));
		}

		byte[] cmd = encodeCmd();
		serialSend(cmd);
	}


	private byte[] encodeCmd(){
		 ArrayList<Byte> mCmd = new ArrayList<Byte>(){{
			add((byte)0x55);
			add((byte)((int)0xaa & 0xff));
			add((byte)0x11);
			add((byte)0x0);
		}
		};
	/*fill corrdinate */
		mCoordinate.set(0, (byte) (joyX & 0xff));
		mCoordinate.set(1, (byte) (joyY & 0xff));
		mCmd.add(3, (byte) (mButton.size()&0xff));
		if(mButton.size()>0) {
		/*set pressed button number in cmd*/
			mCmd.addAll(mButton);

		}
		mCmd.addAll(mCoordinate);
		int sum = 0;
		for(int i =0;i<mCmd.size();i++){
			sum = sum + mCmd.get(i);
		}
		mCmd.add((byte)(sum & 0xff));

		byte[] cmd = new byte[mCmd.size()];
		for(int i=0; i <mCmd.size();i++){
			cmd[i] = mCmd.get(i);
		}
/*
		for(int i=mCmd.size();mCmd.size()>4;i--){
			mCmd.remove(i-1);
		}
		mCmd.set(mCmd.size()-1,(byte)0);
		*/
		return cmd;
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
        onCreateProcess();														//onCreate Process by BlunoLibrary


        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200


       // serialReceivedText=(TextView) findViewById(R.id.serialReveicedText);	//initial the EditText of the received data
      //  serialSendText=(EditText) findViewById(R.id.serialSendText);			//initial the EditText of the sending data
		button1 = (GamepadImageButton) findViewById(R.id.button1);
		button2 = (GamepadImageButton) findViewById(R.id.button2);
		button3 = (GamepadImageButton) findViewById(R.id.button3);
		button4 = (GamepadImageButton) findViewById(R.id.button4);
		button5 = (GamepadImageButton) findViewById(R.id.button5);
		button6 = (GamepadImageButton) findViewById(R.id.button6);
		button1.setGamepadPressListener(this);
		button2.setGamepadPressListener(this);
		button3.setGamepadPressListener(this);
		button4.setGamepadPressListener(this);
		button5.setGamepadPressListener(this);
		button6.setGamepadPressListener(this);

		/*
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] cmd = encodeCmd(1);
				//String code1 = new String("55 aa 11 01 00 01 80 80 00 00 12");
				//button1.setAnimation();
				serialSend(cmd);				//send the data to the BLUNO
			}
		});

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] cmd = encodeCmd(2);
				//String code2 = new String("55 aa 11 01 00 02 80 80 00 00 13");
				serialSend(cmd);				//send the data to the BLUNO
			}
		});

		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] cmd = encodeCmd(3);
				//String code3 = new String("55 aa 11 01 00 03 80 80 00 00 14");
				serialSend(cmd);				//send the data to the BLUNO
			}
		});

		button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] cmd = encodeCmd(4);
				//String code4 = new String("55 aa 11 01 00 04 80 80 00 00 15");
				serialSend(cmd);				//send the data to the BLUNO
			}
		});
		button5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] cmd = encodeCmd(5);
				//String code5 = new String("55 aa 11 01 00 05 80 80 00 00 16");
				serialSend(cmd);				//send the data to the BLUNO
			}
		});
		button6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				byte[] cmd = encodeCmd(6);
				//String code6 = new String("55 aa 11 01 00 06 80 80 00 00 17");
				serialSend(cmd);				//send the data to the BLUNO
			}
		});
*/
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
				//byte coordinate[] = {0x55,0x0,0x11,0x0,0x3,0x00,0x00,0x00,0x00,0x15};
               // coordinate[1] = (byte)(0xaa&0xff);
				int x=128,y=128;
				double curves=0;
				if(angle == 0){
					x = 128;
					if(strength >0) {
						y = 128 + strength * 128 / 100;
					}else {
						y = 128;
					}
				}else if(angle > 0 && angle <90) {

					curves = angle * PI /180;
					x = (int)(128 + 128*strength / 100 * Math.sin(curves));
					y = (int)(128 + 128*strength / 100 * Math.cos(curves));

				}else if(angle == 90){

					x = 128 + strength * 128 / 100;
					y = 128;

				}else if(angle > 90 && angle <180){

					curves = (180 - angle) * PI /180;
					x = (int)(128 + (128*strength / 100 * Math.sin(curves)));
					y = (int)(128 + (- 128*strength / 100 * Math.cos(curves)));
					//x = strength * 128 / 100;
					//y = -(int)(x / Math.tan(curves));
				}else if(angle == 180){
					x = 128;
					y = 128 + (-strength * 128 / 100);
				}else if(angle > 180 && angle < 270){
					curves = (angle - 180) * PI /180;
					x = -(int)(128*strength / 100 * Math.sin(curves)) + 128;
					y = -(int)(128*strength / 100 * Math.cos(curves)) + 128;
					//x = -strength * 128 / 100;
					//y = (int)(x / Math.tan(curves));
				}else if(angle == 270){
					x = -strength * 128 / 100 + 128;
					y = 128;
				}else if(angle > 270){
					curves = (360 - angle) * PI /180;
					x = -(int)(128*strength / 100 * Math.sin(curves)) + 128;
					y = (int)(128*strength / 100 * Math.cos(curves)) + 128;
					//x = -strength * 128 / 100;
					//y = (int)(x / Math.tan(curves));
				}

				System.out.println("angle:" + angle);
				System.out.println("strength:" + strength);
				System.out.println("curves:" + curves);
				System.out.println("x:"+ x);
				System.out.println("y:" + y);




				if(abs(x - lastJoyX) > 10){
					joyX = x;
					joyXUpdate = true;

				}
				if(abs(y - lastJoyY) > 10){
					joyY = y;
					joyYUpdate = true;
				}
                //coordinate[1] = (byte)(0xaa&0xff);
                //coordinate[6] = (byte)(x & 0xff);
                //coordinate[7] = (byte)(y & 0xff);

				if(joyXUpdate || joyYUpdate ){
					 byte[] cmd = encodeCmd();
					serialSend(cmd);
					joyXUpdate = false;
					joyYUpdate = false;
					lastJoyX = joyX;
					lastJoyY = joyY;

				}

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