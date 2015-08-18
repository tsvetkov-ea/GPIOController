package uart.rs232.gpiocontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

final class BluetoothController {

  private _Connector mConnector;
  private _ConnectionManager mConnectionManager;

  private BluetoothAdapter mBluetoothAdapter = null;
  private BluetoothDevice mBluetoothDevice = null;
  private BluetoothSocket mBluetoothClientSocket = null;

  private final List<String> mListMacAddress = new ArrayList<>();
  private String mMacAddress;
  private boolean mConnectionState = false;

  BluetoothController() {
    mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
  }


  public boolean isBluetoothEnabled()  {
    return(mBluetoothAdapter.isEnabled());
  }

  public void enableBluetooth()  {
    mBluetoothAdapter.enable();
  }

  public void disableBluetooth() {
    mBluetoothAdapter.disable();
  }

  public void startBluetoothDiscovery() {
    if(mBluetoothAdapter.isEnabled())
    {
      if (mBluetoothAdapter.isDiscovering())
      {
        mBluetoothAdapter.cancelDiscovery();
      }
      else
      {
        mBluetoothAdapter.startDiscovery();
      }
    }
  }

  public void setMacAddress(String _macAddress) {
    mMacAddress = _macAddress;
  }

  public void createConnection() {
    mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mMacAddress);
    mConnector = new _Connector(mBluetoothDevice);
    mConnector.start();

    try {
      mConnector.join();
      mBluetoothClientSocket = mConnector.getBluetoothClientSocket();
    } catch (Exception _e)  {}

    mConnectionManager = new _ConnectionManager(mBluetoothClientSocket);
    mConnectionState = true;
  }

  public void sendMessageViaSocket(String _outputBuffer) {
    mConnectionManager.run();
    mConnectionManager.sendMessage(_outputBuffer);
  }

  public boolean isConnectionReady()  {
    return mConnectionState;
  }


  // methods used in 'BroadcastReceiver'
  public void onActionDiscoveryStarted()  {
    mListMacAddress.clear();
  }

  public void onActionFound(Intent _intent) {
    mBluetoothDevice = _intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
    mListMacAddress.add(mBluetoothDevice.getAddress());
  }

  /**
   * @return List<String>
   */
  public List<String> onActionDiscoveryFinished() {
    return mListMacAddress;
  }


  //  private classes
  private class _Connector extends Thread {

    private final BluetoothSocket mmBluetoothClientSocket;
    private final BluetoothDevice mmBluetoothDevice;

    _Connector(BluetoothDevice _BluetoothDevice)  {

      mmBluetoothDevice = _BluetoothDevice;
      BluetoothSocket _tmp = null;

      try {
        _tmp = (BluetoothSocket) mmBluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(_BluetoothDevice, 1);
      }
      catch (Exception _e)
      {
        try {
          final UUID _UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
          _tmp = mmBluetoothDevice.createRfcommSocketToServiceRecord(_UUID);
        } catch (Exception _ee) {}
      }
      mmBluetoothClientSocket = _tmp;
    }

    @Override
    public void run() {
      super.run();
      try {
        mmBluetoothClientSocket.connect();
      }
      catch(IOException _e)
      {
        try {
          mmBluetoothClientSocket.close();
        } catch(IOException _ee)  {}
      }
    }

    public BluetoothSocket getBluetoothClientSocket() {
      return mmBluetoothClientSocket;
    }
  }

  private class _ConnectionManager extends Thread {

    private final BluetoothSocket mmBluetoothSocket;
    private final InputStream mmInputStream;
    private final OutputStream mmOutputStream;

    _ConnectionManager(BluetoothSocket _BluetoothSocket)  {

      mmBluetoothSocket = _BluetoothSocket;
      InputStream _InputStream = null;
      OutputStream _OutputStream = null;

      try {
        _InputStream = mmBluetoothSocket.getInputStream();
        _OutputStream = mmBluetoothSocket.getOutputStream();
      } catch (Exception _e)  {}

      mmInputStream = _InputStream;
      mmOutputStream = _OutputStream;
    }

    public void run() {}

    public void sendMessage(String _OutputBuffer) {

      try {
        mmOutputStream.flush();
      } catch (Exception _e)  {}

      try {
        mmOutputStream.write(_OutputBuffer.getBytes());
      } catch (Exception _e)  {}
    }

//    public String receiveMessage();
  }

  //  debug 'BluetoothController.java'
//  private void BLUETOOTH_CONTROLLER_LOG_DEBUG(String _Message)  {
//    final String BLUETOOTH_CONTROLLER_TAG = "BLUETOOTH_CONTROLLER_TAG";
//    _Message = "// BLUETOOTH_CONTROLLER: " + _Message + " //";
//    Log.d(BLUETOOTH_CONTROLLER_TAG, _Message);
//  }
}



