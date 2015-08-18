package uart.rs232.gpiocontroller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class ControlPanelActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_control_panel);

    //  'RadioGroup'
    RadioGroup _RadioGroup = (RadioGroup) findViewById(R.id.r_group_turn_on_of_ll_0_acp);
    _RadioGroup.check(R.id.r_button_turn_off_ll_0_acp);

    //  'RadioButton'
    RadioButton _RadioButtonOn = (RadioButton) findViewById(R.id.r_button_turn_on_ll_0_acp);
    _RadioButtonOn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View _View) {
        ConnectActivity.mBluetoothController.sendMessageViaSocket("on");
      }
    });

    //  'RadioButton'
    RadioButton _RadioButtonOff = (RadioButton) findViewById(R.id.r_button_turn_off_ll_0_acp);
    _RadioButtonOff.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View _View) {
        ConnectActivity.mBluetoothController.sendMessageViaSocket("off");
      }
    });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu _Menu) {
    getMenuInflater().inflate(R.menu.menu_control_panel, _Menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem _Item) {
    switch (_Item.getItemId())  {
      case R.id.menu_item_to_activity_plot:
        Intent _Intent = new Intent(ControlPanelActivity.this, PlotActivity.class);
        startActivity(_Intent);
        return true;
    }
    return super.onOptionsItemSelected(_Item);
  }
}
