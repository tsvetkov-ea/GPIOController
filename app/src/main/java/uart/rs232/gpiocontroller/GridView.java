package uart.rs232.gpiocontroller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

class GridView extends View {

  private int mBackgroundColor;
  private Paint mPaintTile;
  private Resources mResources = getResources();

  private int mXGridTileSize = 60;
  private int mYGridTileSize = 60;

  //  constructors
  public GridView(Context _Context) {
    super(_Context);
  }

  public GridView(Context _Context, AttributeSet _AttributeSet) {
    super(_Context, _AttributeSet);
  }

  public GridView(Context _Context, AttributeSet _AttributeSet, int _DefStyle) {
    super(_Context, _AttributeSet, _DefStyle);
  }


  public void init() {
    mBackgroundColor = mResources.getColor(R.color.white);
    mPaintTile = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaintTile.setColor(mResources.getColor(R.color.black));
  }

  public void setTileSize(int _XTileQuantity, int _YTileQuantity) {
    mXGridTileSize = getWidth() / _XTileQuantity;
    mYGridTileSize = getHeight() / _YTileQuantity;
    GRID_VIEW_LOG_DEBUG("_GET_WIDTH: " + getWidth());
    GRID_VIEW_LOG_DEBUG("_GET_HEIGHT: " + getHeight());
  }

  @Override
  public void onDraw(Canvas _Canvas) {
    super.onDraw(_Canvas);
    _Canvas.drawColor(mBackgroundColor);
    for (int _x = 0; _x < getWidth(); _x += mXGridTileSize) {
      for (int _y = 0; _y < getHeight(); _y += mYGridTileSize) {
        _Canvas.drawLine(_x, 0, _x, getHeight(), mPaintTile);
        _Canvas.drawLine(0, _y, getWidth(), _y, mPaintTile);
      }
    }
  }

  private void GRID_VIEW_LOG_DEBUG(String _Message) {
    final String GRID_VIEW_TAG = "GRID_VIEW_TAG";
    Log.d(GRID_VIEW_TAG, "// " + _Message + " //");
  }
}