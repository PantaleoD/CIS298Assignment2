// PANTALEO   ASSIGNMENT 2 TEMPERATURE CONVERTER  OCT 12 2015
// From & To Celsius, Fahrenheit, Kelvin and Rankin

package edu.kvcc.cis298.cis298assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import static android.widget.Toast.LENGTH_SHORT;

public class TemperatureConverter extends AppCompatActivity {

    /// ********************* Radio Button Variables ************
    private RadioGroup mLeftFromGroup;          // each group of Radio Buttons
    private RadioGroup mRightToGroup;           // will be associated with the layout ID's below - w/ a group no need to define indiv. RB's

      // *************** Input and Class Variable t hold Converted Temperature Variables *****************************
    private int mInputTemp;                // input temp

    Calculate ConvertedTempInstance = new Calculate();

    // ************************* Output Variables **************
    private String mFormulaString;                // formula
    private String outputTemp;

    //  ********************** Convert Button Variable ***************
    private Button mConvertButton;                 //  BUTTON VARIABLE - STARTS THE setOnClickListener METHOD

    String stringInputTemp;                        // INPUT VALUE FROM EDIT TEXT (THE INPUT TEMP) - STRING THEN
    Double InputTempDouble;                         // CONVERTED TO A DECIMAL VALUE

    // ***************                      Key Fields for ROTATION purposes .....
    private  static final String TAG = "TemperatureConverter";
    private  static final String KEY_RESULT = "result";
    private  static final String KEY_FORMULA = "formula";

    private  boolean duplicateBoolean;

 //  private static final String FinalOutputTemp = "";
 //  private static final String FinalFormulaString = "";

    // ************ OnCreate *********************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_converter);

//*******   SET THE ID's FROM THE LAYOUT TO THE PRIVATE VARIABLES DECLARED ABOVE
        // group for the 'FROM' left group  & 'TO' right group of Radio Buttons -
        //              only group needed...can choose selected from inside group
        mLeftFromGroup = (RadioGroup) findViewById(R.id.left_from_group);
        mRightToGroup = (RadioGroup) findViewById(R.id.right_to_group);

 // *************   START OF setOnClickListener METHOD
        mConvertButton = (Button) findViewById(R.id.convert_CAPS);
        mConvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // *********          INPUT VALUE FROM EDIT VIEW (Like Text View ..actually a 'skin' over text view)
                    // CAST EDIT TEXT TO A DOUBLE IN 3 STEPS:
                    //                   A)  get the text   B) cast to a STRING THEN c) cast to a DOUBLE
                    //                                                   - SEEMS LIKE THERE WOULD BE AN EASIER WAY!
                    EditText InputTemp = (EditText) findViewById(R.id.initial_input);
                    stringInputTemp = InputTemp.getText().toString();
                    InputTempDouble = Double.valueOf(stringInputTemp).doubleValue();

                    // *******************************
                    //      TextView resultText =  (TextView)findViewById(R.id.result_text);      // display for TESTING
                    //      resultText.setText(Double.toString(InputTempDouble));
                    //     int selectedFromRadioButtonId = mLeftFromGroup.getCheckedRadioButtonId();    // ASSIGNS RB FROM THE LEFT (FROM) GROUP -
                    //                                                          NA FOR THIS PRG USED NEXT LINE
                    try {                    // ASSIGN TEXT FROM RB STRING TO From and To string variables
                        final String mFromString = ((RadioButton) findViewById(mLeftFromGroup.getCheckedRadioButtonId())).getText().toString();
                        final String mToString = ((RadioButton) findViewById(mRightToGroup.getCheckedRadioButtonId())).getText().toString();

                        // ********************* PROCESSING METHOD CALLS ***********************
                        if (InputTempDouble >= 0) {
                                //              METHOD TO TEST BUTTONS, INSTANTIATE CALCULATE CLASS WHERE TEMP. CONVERSION IS COMPLETED
                                CheckCalculation(mFromString, mToString, InputTempDouble);
                                //             METHOD TO OUTPUT RESULTS OF CONVERSION AND THE FORUMULA USED
                                DisplayResults(mFromString, mToString);
                        }
                         else
                        {
                            int msgToastID = R.string.toast_input_not_positive;
                            Toast.makeText(TemperatureConverter.this, R.string.toast_input_not_positive, LENGTH_SHORT).show();        // TOAST ERROR MESSAGE WHEN
                        }
                    }
                    catch (Exception x )
                    {
                        int invalidInputToastID = R.string.toast_no_rb_selected;
                        Toast.makeText(TemperatureConverter.this, R.string.toast_no_rb_selected, LENGTH_SHORT).show();        // TOAST ERROR MESSAGE WHEN
                    }
                }
             catch (Exception x)
            {
                int invalidInputToastID = R.string.toast_input_not_valid;
                Toast.makeText(TemperatureConverter.this, R.string.toast_input_not_valid, LENGTH_SHORT).show();        // TOAST ERROR MESSAGE WHEN
            }
          }
        });         // ********   END OF setOnClickListener

        if (savedInstanceState != null){
            Log.i(TAG, "onSavedInstanceState get string");
            outputTemp = savedInstanceState.getString(KEY_RESULT, "");
            mFormulaString = savedInstanceState.getString(KEY_FORMULA, "");
        }
    }            // ********* end  of OnCreate
    // ******************** code to save the result and formula for display upon ROTATION

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSavedInstanceState put string");                         // THIS INDICATES IT HITS THIS METHOD
        savedInstanceState.putString(KEY_RESULT,  outputTemp.toString());
        savedInstanceState.putString(KEY_FORMULA, mFormulaString.toString());
    }

    // **********************  METHOD TO OUTPUT RESULTS & FORMULAS STRING -     parameters = string from RB of Temp Scale
    private void DisplayResults(String FromType, String ToType) {
            //            casting output temp to 2 decimals...I'm sure there has to be an easier way!
        Double outputConvertedTemp = ConvertedTempInstance.getToTempDouble();    //
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
         outputTemp = decimalFormat.format(outputConvertedTemp);

        //  CREATION OF STRING TO OUTPUT FROM...TO VALUES
        //     USED SUBSTRING TO OUTPUT THE FIRST LETTER OF EACH SCALE (C,F,K,R)

        // CANT GET SUPERSCRIPT TO WORK !!!!!! *****************************(getText(R.string.left_celsius).toString())
        String resultString =  stringInputTemp + " Degrees " + FromType.substring(0,1) + " Equals " +
                                                     outputTemp  + " Degrees "   + ToType.substring(0,1)   ;
// THIS DOESN'T WORK - WHY???
     //   String resultString =  stringInputTemp + " " + (getText(R.string.degrees).toString()) + FromType.substring(0,1) + " = " +
     //                                       outputTemp  + (getText(R.string.degrees).toString())+ " " + ToType.substring(0,1)   ;

     //    String resultString = stringInputTemp +  (Html.fromHtml("<sup>^0</sup>")).toString() + FromType.substring(0,1) + " = " +
     //                               outputTemp  + Html.fromHtml("<sup>^0</sup>")+  ToType.substring(0,1)  ;

      // SET TEXTVIEW VALUES TO OUTPUT RESULT STRING AND FORMULA STRING

        TextView result = (TextView)findViewById(R.id.result_text);
        result.setText(resultString);

       TextView formulaText =  (TextView)findViewById(R.id.formula_text);
       formulaText.setText(mFormulaString);
     }                                          // End of DisplayResults() Method

    //          ****** METHOD TO :
    //                      A) TEST IF SAME CONVERT SCALE CHECKED FOR FROM AND TO AND TOAST IF EQUAL
    //                      B) TEST FOR WHICH CALCULATION TO COMPLETE... NESTED IF USED FOR FROM (LEFT SIDE) DECISIONS AND
    //                              NESTED THE TO (RIGHT SIDE) DECISION USING AN SWITCH COMMAND
    //                      C) PASSED CONVERSION STRING (CODED) & INPUT TEMPERATURECALLED WITH INSTANTIATION TO THE Calculate CLASS
    //                              WHERE DECISION ON CONVERSION STRING IS MADE AND APPROPRIATE CALCULATION IS COMPLETED AND
    //                              STORED IN INSTANCE VARIABLE
    //                              WHERE I USED THE RETURN VALUE OF gettoTempDouble (property/method)FROM IT'S GETTER FOR DISPLAY PURPOSED
    private void CheckCalculation(String FromTemp, String ToTemp, double InputTemp) {
          // Assign text from RB string on 'from' (right side) group for IF decision... more variables...more meaningful code... maybe? :-)
            //    USE STRINGS.XML STRINGS RB TEXT FOR IF  BECAUSE JAVA TESTS OBJECTS NOT STRINGS (C# IS KINDER :)
            //        ODDLY .. IT ALLOWED FOR LITERAL COMPARES IN STRINGS AND I DIDN'T CHANGE THEM TO OBJECTS - PROBABLY SHOULD HAVE
            //        ONLY USED THEM HERE SO DECLARED LOCALLY

        final String CelsiusString = (getText(R.string.left_celsius).toString());
        final String FahrenheitString = (getText(R.string.left_fahrenheit).toString());
        final String KelvinString = (getText(R.string.left_kelvin).toString());
        final String RankinString = (getText(R.string.left_rankin).toString());

                            // ignore this - JUST NOTES FOR TESTING PURPOSES DISPLAYS - COULD (SHOULD?)) HAVE USED LOG
                            //  TextView resultText = (TextView) findViewById(R.id.result_text);
                            //   resultText.setText(FromTemp + " " + mToString + Double.toString(InputTemp));      // KEPT HERE FOR EXAMPLE PURPOSES ONLY
                            //        resultText.setText(Double.toString(InputTemp));
                            //       resultText.setText(getString(R.string.right_fahrenheit));
                            //        resultText.setText(FromTemp + " " + mToString + Double.toString(InputTemp) );

        if (FromTemp == ToTemp) {                                         //VALIDATION TEST FOR SAME CONVERSION RB - FROM BOTH GROUPS
            duplicateBoolean = false;
             int msgID = R.string.toast;
             Toast.makeText(this, R.string.toast, LENGTH_SHORT).show();        // TOAST ERROR MESSAGE WHEN SAME CONVERT TYPE
        }
           else                                            // ONLY CONTINUE WHEN NO ERROR
        {
            duplicateBoolean = true;
     // *****DETERMINE WHICH CONVERSION CALCULATION TO EXECUTE & UPON DETERMINATION:
     //                                             PASS STRING CODE AND INPUT TEMP TO WITH Calculate CLASS INSTANTIATION
     //                                             ASSIGN FORMULA STRING TO STRING VARIABLE - FOR OUTPUT PURPOSES ONLY

             if (FromTemp == CelsiusString) {                                           //  COMPARES OBJECTS..SO CAN'T COMPARE STRINGS.
              switch (ToTemp.trim()) {
                case "Fahrenheit":                                                      //  JAVA  does allow for "" compare w/ switch..should have used ref.id anyway?
                    ConvertedTempInstance = new Calculate("c2f", mInputTemp);
                    mFormulaString = (String) getText(R.string.celsius__to_fahrenheit_calc);
                    break;
                case "Kelvin":
                    ConvertedTempInstance = new Calculate("c2k", mInputTemp);
                    mFormulaString = (String) getText(R.string.celsius__to_kelvin_calc);
                    break;
                case "Rankin":
                    ConvertedTempInstance = new Calculate("c2r", mInputTemp);
                    mFormulaString = (String) getText(R.string.celsius__to_rankin_calc);
            }
        }
         else {                                   // END OF CELSIUS DECISION STRUCTURE ON TO...
            if (FromTemp == FahrenheitString) {    // FAHRENHEIT
                switch (ToTemp.trim()) {
                    case "Celsius":
                        ConvertedTempInstance = new Calculate("f2c", mInputTemp);
                        mFormulaString = (String) getText(R.string.fahrenheit_to_celsius_calc);
                        break;
                    case "Kelvin":
                        ConvertedTempInstance = new Calculate("f2k", mInputTemp);
                        mFormulaString = (String) getText(R.string.fahrenheit_to_kelvin_calc);
                        break;
                    case "Rankin":
                        ConvertedTempInstance = new Calculate("f2r", mInputTemp);
                        mFormulaString = (String) getText(R.string.fahrenheit_to_rankin_calc);
                        break;
                }
            } else {                     // END OF FAHRENHEIT DECISION STRUCTURE ON TO...
                if (FromTemp == KelvinString) {                // FROM KELVIN
                    switch (ToTemp.trim()) {
                        case "Celsius":

                            ConvertedTempInstance = new Calculate("k2c", mInputTemp);
                            mFormulaString = (String) getText(R.string.kelvin__to_celsius_calc);
                            break;
                        case "Fahrenheit":
                            ConvertedTempInstance = new Calculate("k2f", mInputTemp);
                            mFormulaString = (String) getText(R.string.kelvin__to_fahrenheit_calc);
                            break;
                        case "Rankin":
                            ConvertedTempInstance = new Calculate("k2r", mInputTemp);
                            mFormulaString = (String) getText(R.string.kelvin__to_rankin_calc);
                            break;
                    }
                } else {                 // END OF KELVIN DECISION STRUCTURE ON TO...
                    if (FromTemp == RankinString) {              // FROM RANKIN
                        switch (ToTemp.trim()) {
                            case "Celsius":
                                ConvertedTempInstance = new Calculate("r2c", mInputTemp);
                                mFormulaString = (String) getText(R.string.rankin__to_celsius_calc);
                                break;
                            case "Fahrenheit":
                                ConvertedTempInstance = new Calculate("r2f", mInputTemp);
                                mFormulaString = (String) getText(R.string.rankin__to_fahrenheit_calc);
                                break;
                            case "Kelvin":
                                ConvertedTempInstance = new Calculate("r2k", mInputTemp);
                                mFormulaString = (String) getText(R.string.rankin__to_kelvin_calc);
                                break;
                        }
                    }
                }                        // END OF RANKIN DECISION STRUCTURE ON TO...
            }                          // END OF KELVIN
        }                            // END OF FAHRENHEIT
    }                               // END OF CELSUIS
}              // end of CheckCalculation(int FromTemp, int ToTemp)

    // **************************** STILL IGNORING THIS...**************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_temperature_converter, menu);
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
