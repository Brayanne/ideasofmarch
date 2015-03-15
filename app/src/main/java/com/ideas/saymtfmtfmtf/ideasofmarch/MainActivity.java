package com.ideas.saymtfmtfmtf.ideasofmarch;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {

    public final static String PROP_SYMBOL = "com.ideas.saymtfmtfmtf.ideasofmatch.PROP"; // unique

    TableLayout propTableScrollView;
    SharedPreferences propMeasures;
    SharedPreferences propMeasureBox;


    // Like to pull data from Californian measures
    // Pre-made Measures
    String propMeasuresEntered1;
    String propMeasuresEntered2;
    String propMeasuresEntered3;
    String propMeasuresEntered4;
    String propMeasuresEntered5;
    String propMeasuresEntered6;
    String propMeasuresEntered7;
    String propMeasuresEntered8;

    int  i= 0;

    //corresponding url
    String urls[] = {
        "http://ballotpedia.org/California_Proposition_41,_Veterans_Housing_and_Homeless_Prevention_Bond_(2014)",
        "http://ballotpedia.org/California_Proposition_42,_Compliance_of_Local_Agencies_with_Public_Records_(2014)",
        "http://ballotpedia.org/California_Proposition_1,_Water_Bond_%282014%29",
        "http://ballotpedia.org/California_Proposition_2,_Rainy_Day_Budget_Stabilization_Fund_Act_(2014)",
        "http://ballotpedia.org/California_Proposition_45,_Public_Notice_Required_for_Insurance_Company_Rates_Initiative_(2014)",
        "http://ballotpedia.org/California_Proposition_46,_Medical_Malpractice_Lawsuits_Cap_and_Drug_Testing_of_Doctors_(2014)",
        "http://ballotpedia.org/California_Proposition_47,_Reduced_Penalties_for_Some_Crimes_Initiative_(2014)",
        "http://ballotpedia.org/California_Proposition_48,_Referendum_on_Indian_Gaming_Compacts_(2014)"}; // insert urls



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        propMeasures = getSharedPreferences("propList", MODE_PRIVATE);
        propMeasureBox = getSharedPreferences("answersList", MODE_PRIVATE);


        propTableScrollView = (TableLayout) findViewById(R.id.propTableScrollView);


        insertMeasures();
        updateSavedPropList(null);
    }


    /* Manually Input Measures */
    public void insertMeasures() {
        propMeasuresEntered1 = new String("Proposition 41");
        updateSavedPropList(propMeasuresEntered1);
        propMeasuresEntered2 = new String("Proposition 42");
        updateSavedPropList(propMeasuresEntered2);
        propMeasuresEntered3 = new String("Proposition 1");
        updateSavedPropList(propMeasuresEntered3);
        propMeasuresEntered4 = new String("Proposition 2");
        updateSavedPropList(propMeasuresEntered4);
        propMeasuresEntered5 = new String("Proposition 45");
        updateSavedPropList(propMeasuresEntered5);
        propMeasuresEntered6 = new String("Proposition 46");
        updateSavedPropList(propMeasuresEntered6);
        propMeasuresEntered7 = new String("Proposition 47");
        updateSavedPropList(propMeasuresEntered7);
        propMeasuresEntered8 = new String("Proposition 48");
        updateSavedPropList(propMeasuresEntered8);
    }


    /**
     * @param textView
     *            textView who's text you want to change
     * @param linkThis
     *            a regex of what text to turn into a link
     * @param toThis
     *            the url you want to send them to
     */
    public static void addLinks(TextView textView, String linkThis, String toThis) {
        Pattern pattern = Pattern.compile(linkThis);
        String scheme = toThis;
        android.text.util.Linkify.addLinks(textView, pattern, scheme, new Linkify.MatchFilter() {
            @Override
            public boolean acceptMatch(CharSequence s, int start, int end) {
                return true;
            }
        }, new Linkify.TransformFilter() {

            @Override
            public String transformUrl(Matcher match, String url) {
                return "";
            }
        });
    }



    public void updateSavedPropList(String newPropLabel) {
        String[] measure = propMeasures.getAll().keySet().toArray(new String[0]);



        if(newPropLabel != null) {
            insertPropInScrollView(newPropLabel, Arrays.binarySearch(measure, newPropLabel));

        }else {
            // display the stocks on screen
            for(int i = 0; i < measure.length; i++) {
                insertPropInScrollView(measure[i], i);
            }
        }
    }

    private void insertPropInScrollView(String measure, int arrayIndex) {
        // allows create/set a measure_row into the scroll view (dynamically)
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // putting it into a view
        View newPropRow = inflator.inflate(R.layout.activity_measure_row, null);

        // text view for scroll view row
        TextView newMeasureTextView = (TextView) newPropRow.findViewById(R.id.measureSymbolTextView);


        // add the measure to Measure TextView
        newMeasureTextView.setText(measure);

        String userCanSeeThis = measure;
        String correspondingUrl = urls[i];
        newMeasureTextView.setText(userCanSeeThis);
        addLinks(newMeasureTextView, userCanSeeThis, correspondingUrl);
        i++;


        //Add newStockRow View to the stockTableScrollView TableLayout
        propTableScrollView.addView(newPropRow, arrayIndex);
    }

    private void saveMeasureButton(String button, boolean checked) {
        Boolean isTheMeasureButtonNew = propMeasureBox.getBoolean(button, checked);
        SharedPreferences.Editor editor = propMeasureBox.edit();

        editor.putBoolean(button, checked);
        editor.apply();
    }


    private void savePropSymbol(String newProp) {
        String isThePropNew = propMeasures.getString(newProp, null);

        SharedPreferences.Editor preferencesEditor = propMeasures.edit();

        preferencesEditor.putString(newProp, newProp);
        preferencesEditor.apply();

        //if new
        if(isThePropNew == null) {
            updateSavedPropList(newProp);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
