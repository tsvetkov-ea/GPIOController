package uart.rs232.gpiocontroller;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ConnectActivity extends ActionBarActivity {

  //  list of instances of classes
  public static final BluetoothController mBluetoothController = new BluetoothController();
  private final FNDReceiver mFNDReceiver = new FNDReceiver();

  //  ui elements
  public static ListView mListViewMacAddress;
  public static ProgressDialog mProgressDialog;

  public static List<String> mListMacAddress;

  // activity's life cycle
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_connect);

    //  register 'BroadcastReceiver'
    this.registerFNDReceiver(mFNDReceiver);

    mListViewMacAddress = (ListView) findViewById(R.id.list_view_ll_0_ac);

    // register component for context menu
    registerForContextMenu(mListViewMacAddress);

    Button _ButtonShow = (Button) findViewById(R.id.button_0_ll_2_ac);
    _ButtonShow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View _View) {
        mBluetoothController.startBluetoothDiscovery();
      }
    });
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mBluetoothController.disableBluetooth();
  }


  //  -->  action bar menu
  @Override
  public boolean onCreateOptionsMenu(Menu _Menu) {
    getMenuInflater().inflate(R.menu.menu_connect, _Menu);

    if(mBluetoothController.isBluetoothEnabled()) {
      MenuItem _MenuItem = _Menu.findItem(R.id.menu_item_bluetooth_adapter_ac);
      if(mBluetoothController.isBluetoothEnabled()) {
        _MenuItem.setChecked(true);
      }
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem _MenuItem) {
    switch (_MenuItem.getItemId()) {
      case R.id.menu_item_bluetooth_adapter_ac:
        if (mBluetoothController.isBluetoothEnabled())
        {
          mBluetoothController.disableBluetooth();
          _MenuItem.setChecked(!_MenuItem.isChecked());
        }
        else
        {
          mBluetoothController.enableBluetooth();
          _MenuItem.setChecked(!_MenuItem.isChecked());
        }
        return true;
      case R.id.menu_item_control_panel_ac:
        if(mBluetoothController.isConnectionReady())
        {
          Intent _Intent = new Intent(ConnectActivity.this, ControlPanelActivity.class);
          startActivity(_Intent);
        }
        else
        {
          Toast.makeText(getApplicationContext(), "// no device //", Toast.LENGTH_LONG).show();
        }
      default:
        return super.onOptionsItemSelected(_MenuItem);
    }
  }


  //  --> context menu
  @Override
  public void onCreateContextMenu(ContextMenu _ContextMenu, View _View, ContextMenu.ContextMenuInfo _ContextMenuInfo) {
    super.onCreateContextMenu(_ContextMenu, _View, _ContextMenuInfo);
    MenuInflater _MenuInflater = getMenuInflater();
    _MenuInflater.inflate(R.menu.menu_context, _ContextMenu);
  }

  @Override
  public boolean onContextItemSelected(MenuItem _MenuItem)  {
    AdapterView.AdapterContextMenuInfo _AdapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) _MenuItem.getMenuInfo();
    switch (_MenuItem.getItemId()) {
      case R.id.cmi_connect:
        int _ID = (int) _AdapterContextMenuInfo.id;
        mBluetoothController.setMacAddress(mListMacAddress.get(_ID));
        mBluetoothController.createConnection();
        return true;
      default:
        return super.onContextItemSelected(_MenuItem);
    }
  }


  //  'BroadcastReceiver' with 'IntentFilter'
  private void registerFNDReceiver(FNDReceiver _FNDReceiver) {
    IntentFilter _IntentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    _IntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
    _IntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    registerReceiver(_FNDReceiver, _IntentFilter);
  }

}
