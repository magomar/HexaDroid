package deludobellico.hexadroid.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import deludobellico.hexadroid.app.R;
import deludobellico.hexadroid.app.map.Board;
import deludobellico.hexadroid.app.map.TerrainType;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Spinner spinner = (Spinner) findViewById(R.id.terrain_type_spinner);
//        ArrayAdapter<TerrainType> dataAdapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_spinner_item, TerrainType.ALL_TERRAIN_TYPES);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(dataAdapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    private final class RandomMapButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            EditText mapWidth = (EditText) findViewById(R.id.editText_mapWidth);
            EditText mapHeight = (EditText) findViewById(R.id.editText_mapHeight);
            Spinner spinner = (Spinner) findViewById(R.id.spinner_terrainType);
            TerrainType terrainType = (TerrainType) spinner.getSelectedItem();
            Board newBoard = Board.createRandomMap(
                    Integer.parseInt(mapWidth.getText().toString()),
                    Integer.parseInt(mapHeight.getText().toString()),
                    terrainType
            );
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            intent.putExtra(MapActivity.BOARD_KEY, newBoard);
            startActivity(intent);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            assert rootView != null;
            Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner_terrainType);
            ArrayAdapter<TerrainType> dataAdapter = new ArrayAdapter<TerrainType>(rootView.getContext(),
                    android.R.layout.simple_spinner_item, TerrainType.ALL_TERRAIN_TYPES);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            spinner.setSelection(1);

            Button button = (Button) rootView.findViewById(R.id.button_randomMap);
            button.setOnClickListener(new RandomMapButtonListener());
            return rootView;
        }
    }
}
