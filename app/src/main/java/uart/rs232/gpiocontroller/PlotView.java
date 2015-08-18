package uart.rs232.gpiocontroller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

class PlotView extends GridView {

  private Resources mResources = getResources();

  private Paint mPaintPlot;
  private Paint mPaintPoint;

  private int[][] mPoints = {{80, 15}, {110, 75}, {165, 100}, {175, 180}, {230, 210}};

  //  constructors
  public PlotView(Context _Context) {
    super(_Context);
  }

  public PlotView(Context _Context, AttributeSet _AttributeSet) {
    super(_Context, _AttributeSet);
  }

  public PlotView(Context _Context, AttributeSet _AttributeSet, int _DefStyle) {
    super(_Context, _AttributeSet, _DefStyle);
  }

  @Override
  public void init() {
    super.init();
    mPaintPlot = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaintPlot.setColor(mResources.getColor(R.color.purple_900));
    mPaintPlot.setStrokeWidth(6);
    mPaintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaintPoint.setColor(mResources.getColor(R.color.purple_900));
  }

  @Override
  public void onDraw(Canvas _Canvas) {
    super.setTileSize(16, 10);
    super.onDraw(_Canvas);
    _Canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mPaintPlot);
    _Canvas.drawLine(0, 0, 0, getHeight(), mPaintPlot);
    addPoints(_Canvas, mPoints);
  }

  private void addPoints(Canvas _Canvas, int[][] _Points) {
    int _x, _y, _PPoint, _CPoint;
    _PPoint = 0;
    for (_x = 0; _x < _Points.length; _x++) {
      for (_y = 0; _y < _Points[_x].length; _y++) {
        _PPoint = _Points[_x][_y];
      }
      _CPoint = _Points[_x][--_y];
      _Canvas.drawCircle(_PPoint, _CPoint, 4, mPaintPoint);
    }
  }

  private void PLOT_VIEW_LOG_DEBUG(String _Message) {
    final String PLOT_VIEW_TAG = "PLOT_VIEW_TAG";
    Log.d(PLOT_VIEW_TAG, "// " + _Message + " //");
  }
}