package uart.rs232.gpiocontroller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class PlotActivity extends ActionBarActivity {

  private PlotView mPlotView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_plot);
    MAIN_ACTIVITY_LOG_DEBUG("_ON_CREATE");
    mPlotView = (PlotView) findViewById(R.id.plot_view_CC);
    mPlotView.init();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu _Menu) {
    getMenuInflater().inflate(R.menu.menu_plot, _Menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem _MenuItem) {
    switch (_MenuItem.getItemId()) {
      case R.id.action_settings:
        return true;
    }
    return super.onOptionsItemSelected(_MenuItem);
  }

  private void MAIN_ACTIVITY_LOG_DEBUG(String _Message) {
    final String MAIN_ACTIVITY_TAG = "MAIN_ACTIVITY_TAG";
    Log.d(MAIN_ACTIVITY_TAG, "// " + _Message + " //");
  }
}
