package deludobellico.hexadroid.app.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import deludobellico.hexadroid.app.R;
import deludobellico.hexadroid.app.map.Board;

public class MapActivity extends ActionBarActivity {

    public static String BOARD_KEY = "BOARD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (savedInstanceState == null) {
            Board board = (Board) getIntent().getSerializableExtra(BOARD_KEY);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment(board))
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {
        private Board board;

        public PlaceholderFragment(Board board) {
            this.board = board;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_map, container, false);
            assert rootView != null;
            // Create map image
            HexagonalMap hexagonalMap = new HexagonalMap(board, getApplicationContext());
            Bitmap mapImage = hexagonalMap.getMapImage();
            // set the image view with the map image
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView_map);
            imageView.setImageBitmap(mapImage);
            imageView.invalidate();
//            Util.storeImage(getApplicationContext(),mapImage);

            return rootView;
        }

    }
}
