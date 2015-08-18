package uart.rs232.gpiocontroller;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;


public class FNDReceiver extends BroadcastReceiver {

//  public static final String FND_RECEIVER = "uart.rs232.gpiocontroller.action.FND_RECEIVER";

  @Override
  public void onReceive(final Context _Context, final Intent _Intent) {

    String _Action = _Intent.getAction();

    if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(_Action)) {
      //  create a 'ProgressDialog'. it is showing while 'BluetoothAdapter' search nearby devices
      ConnectActivity.mProgressDialog = ProgressDialog.show(_Context, "Searching..", "Please, wait..", true);

      ConnectActivity.mBluetoothController.onActionDiscoveryStarted();
    }

    if (BluetoothDevice.ACTION_FOUND.equals(_Action)) {
      ConnectActivity.mBluetoothController.onActionFound(_Intent);
    }

    if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(_Action)) {
      //  dismiss 'ProgressDialog'
      ConnectActivity.mProgressDialog.dismiss();

      //  create and fill 'List<String>' by founded MAC addresses
      ConnectActivity.mListMacAddress = ConnectActivity.mBluetoothController.onActionDiscoveryFinished();

      //  use 'ArrayAdapter' and 'List<String>' to fill 'ListView' from 'ConnectActivity'
      ArrayAdapter<String> _ArrayAdapterMacAddress = new ArrayAdapter<>(_Context, R.layout.list_item, ConnectActivity.mListMacAddress);
      ConnectActivity.mListViewMacAddress.setAdapter(_ArrayAdapterMacAddress);
    }

  }
}

