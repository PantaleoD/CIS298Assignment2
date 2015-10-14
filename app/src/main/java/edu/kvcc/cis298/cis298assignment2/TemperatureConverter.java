// PANTALEO   ASSIGNMENT 2 TEMPERATURE CONVERTER  OCT 12 2015
// From & To Celsius, Fahrenheit, Kelvin and Rankin - includes validation/ rotation/ method calls/ calculation instantiation and class

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

    private TextView mResult;                   // viewed variable - assigned to Radio Button (R.id) in onCreate
    private  TextView mFormula;

      // *************** Input and Class Variable to hold Converted Temperature Variables *****************************
    private int mInputTemp;                // input temp
    Calculate ConvertedTempInstance = new Calculate();

    // ************************* Output Variables **************
    private String mFormulaString;                // formula
    private String outputTemp;

    String resultString;

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

//*******   SET THE ID's FROM THE LAYOUT TO THE PRIVATE VARIABLES DECLARED ABOVE (MUST HAVE CLASSS LEVEL VARIABLE W/ MULTIPLE METHODS (IE: DISPLAY RESULT METHOD)
        mLeftFromGroup = (RadioGroup) findViewById(R.id.left_from_group);           // group for the 'FROM' left group  & 'TO' right group of Radio Buttons -
        mRightToGroup = (RadioGroup) findViewById(R.id.right_to_group);             //     only group needed...can choose selected from inside group

         mResult = (TextView)findViewById(R.id.result_text);                       // ASSOCIATE TEXTVIEW VARIABLE W/RESPECTIVE TEXTVIEW ID IN LAYOUT
        mFormula = (TextView) findViewById(R.id.formula_text);                      //  THIS IS THEN USED TO DISPLAY THE RESULTS,FORMULA VALUES, RESP.

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
                    EditText InputTemp = (EditText) findViewById(R.id.initial_input);         // !!!!!!!!   ASSIGN ALL THINGS IN LAYOUT TO mVARIABLES..THEN NTHE TEXTVIEW.TEXTRESULT LINES BELOW ARE n/a
                    stringInputTemp = InputTemp.getText().toString();
                    InputTempDouble = Double.valueOf(stringInputTemp).doubleValue();

                    // *******************************
                    //      TextView resultText =  (TextView)findViewById(R.id.result_text);      // display for TESTING
                    //      resultText.setText(Double.toString(InputTempDouble));
                    //     int selectedFromRadioButtonId = mLeftFromGroup.getCheckedRadioButtonId();    // ASSIGNS RB FROM THE LEFT (FROM) GROUP -
                    //                                                          NA FOR THIS PRG USED NEXT LINE

                    try {                    // ASSIGN TEXT FROM RB STRING TO From and To string variables & TEST A RB IS SELECTED FROM EACH GROUP
                        final String mFromString = ((RadioButton) findViewById(mLeftFromGroup.getCheckedRadioButtonId())).getText().toString();
                        final String mToString = ((RadioButton) findViewById(mRightToGroup.getCheckedRadioButtonId())).getText().toString();

                        // ********************* PROCESSING METHOD CALLS ***********************
                        if (InputTempDouble >= 0) {                         // TEST INPUT VALUE IS 0 OR GREATER (NOT DOING NEGATIVES - DON'T KNOW WHY EXCEPT TO PLAY WITH VALIDATION :) )

                            if (mFromString != mToString) {                           //TEST SAME RB IS NOT SELECTED FROM BOTH GROUPS
                                // AT THIS POINT..ALL DATA IS VALID &
                              CheckCalculation(mFromString, mToString, InputTempDouble);    // METHOD TO DETERMINE CONVERSION,  INSTANTIATE CALCULATE CLASS WHERE FOR CONVERSION
                              DisplayResults(mFromString, mToString);                       // METHOD TO OUTPUT RESULTS OF CONVERSION AND THE FORUMULA USED
                            }
                            else {
                                Toast.makeText(TemperatureConverter.this, R.string.toast_duplicate_rbs, LENGTH_SHORT).show();  // TOAST/ERROR WHEN DUPLICATE RB'S SELECTED
                                ClearOutput();
                            }

                        } else {
                            Toast.makeText(TemperatureConverter.this, R.string.toast_input_not_positive, LENGTH_SHORT).show();   // TOAST/ERROR  WHEN INPUT NOT 0 OR GREATER
                            ClearOutput();
                        }
                    } catch (Exception x) {
                        Toast.makeText(TemperatureConverter.this, R.string.toast_no_rb_selected, LENGTH_SHORT).show();    // TOAST/ERROR  WHEN NO RADIO BUTTON SELECTED
                        ClearOutput();
                    }
                } catch (Exception x) {
                    Toast.makeText(TemperatureConverter.this, R.string.toast_input_not_valid, LENGTH_SHORT).show();        // TOAST/ERROR  WHEN INPUT NOT AN INTEGER
                    ClearOutput();
                }
            }
        });         // ********   END OF setOnClickListener

        if (savedInstanceState != null){                                    // TEST IF ROTATON OCCURED AND PAUSED..IF SO...RETRIEVE OUTPUT VALUES SAVED BEFORE ROTATION
            Log.i(TAG, "onSavedInstanceState get string");                      // DO A LOG JUST TO MAKE SURE IT'S HAPPENING!
            resultString = savedInstanceState.getString(KEY_RESULT, "");              // RE-ASSIGN OUTPUT VALUES (SAVED IN BUNDLE OF onSaveInstanceState(Bundle savedInstanceState
            mFormulaString = savedInstanceState.getString(KEY_FORMULA, "");

             mResult.setText(resultString);                                         // DISPLAY RETRIEVED RESULTS ON NEW VIEW
             mFormula.setText(mFormulaString);
        }
    }            // ********* end  of OnCreate
    // ******************** code to save the result and formula for display upon ROTATION

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){           // METHOD THAT OCCURS WITH ROTATION..TO SAVE OUTPUT VALUES PRIOR TO ROTATION

        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSavedInstanceState put string");                                       // LOG TO ENSURE THIS METHOD EXECUTED
        savedInstanceState.putString(KEY_RESULT,  resultString );               // SAVE RESULT AND FORMULA IN KEY-_____  VALUE (DEFINED AT CLASS LEVEL)
        savedInstanceState.putString(KEY_FORMULA, mFormulaString.toString());   //     WHICH ARE THEN RETRIEVED IN  if(savedInstanceState != null) CODE
    }

    private  void ClearOutput() {                     // cALLED WITH EACH TOAST: clears output as it could be wrong
        mFormula.setText("");                         //    added to rb errors too - in case of further development where all input is cleared
        mResult.setText("");                            //              although I think that could be a pain from an input POV
    }
    // **********************  METHOD TO OUTPUT RESULTS & FORMULAS STRING -     parameters = string from RB of Temp Scale
    private void DisplayResults(String FromType, String ToType) {
            //            casting output temp to 2 decimals...I'm sure there has to be an easier way!
        Double outputConvertedTemp = ConvertedTempInstance.getToTempDouble();    //
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
         outputTemp = decimalFormat.format(outputConvertedTemp);

        //  CREATION OF STRING TO OUTPUT FROM...TO VALUES
        //      used  SUBSTRING TO OUTPUT THE FIRST LETTER OF EACH SCALE (C,F,K,R)
        //  Code for superscripted/'degrees' symbol:  \u00B0  is the unicode symbol in java    Celsuis, Fahrenheit codes are:  \u2103 = 'C'     \u2109  = 'F'
         resultString =  stringInputTemp + "\u00B0"  + FromType.substring(0,1) + " Equals " +
                                                     outputTemp  + "\u00B0"  + ToType.substring(0,1)   ;

      //  OUTPUT RESULT STRING AND FORMULA STRING IN RESPECTIVE TEXTBOXES - ASSIGNED TO TEXTVIEW VARIBLES AT TOP OF onCreate()
        mResult.setText(resultString);
        mFormula.setText(mFormulaString);
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
            //        ODDLY .. IT ALLOWED FOR LITERAL COMPARES IN SWITCH AND I DIDN'T CHANGE THEM TO OBJECTS - PROBABLY SHOULD HAVE??
            //                  DID TRY THAT AND SWITCH WAS LOOKING FOR A 'CONSTANT' VALUE...DIDN'T EVEN LIKE THE STRINGS CREATED JUST BELOW

                         //   ONLY USED THEM HERE SO DECLARED LOCALLY FOR THE IF STATEMENTS -   I KNOW CAN USE VALUES IN THE IF...THIS JUST LOOKS BETTER (TO ME! :) IN if's
        final String CelsiusString = (getText(R.string.left_celsius).toString());
        final String FahrenheitString = (getText(R.string.left_fahrenheit).toString());
        final String KelvinString = (getText(R.string.left_kelvin).toString());
        final String RankinString = (getText(R.string.left_rankin).toString());

                            // ignore this - JUST NOTES FOR TESTING PURPOSES DISPLAYS - COULD (SHOULD?)) HAVE USED LOG
                            //  TextView resultText = (TextView) findViewById(R.id.result_text);
                            //        resultText.setText(FromTemp + " " + mToString + Double.toString(InputTemp));      // KEPT HERE FOR EXAMPLE PURPOSES ONLY
                            //        resultText.setText(Double.toString(InputTemp));
                            //        resultText.setText(getString(R.string.right_fahrenheit));
                            //        resultText.setText(FromTemp + " " + mToString + Double.toString(InputTemp) );

  //      if (FromTemp == ToTemp) {
  //          duplicateBoolean = false;
         //    int msgID = R.string.toast_duplicate_rbs
         //    Toast.makeText(this, msgID, LENGTH_SHORT).show();        // TOAST ERROR MESSAGE WHEN SAME CONVERT TYPE
  //      }
  //         else                                            // ONLY CONTINUE WHEN NO ERROR
  //      {
  //          duplicateBoolean = true;
     // *****DETERMINE WHICH CONVERSION CALCULATION TO EXECUTE & UPON DETERMINATION:
     //                                             PASS STRING CODE AND INPUT TEMP TO WITH Calculate CLASS INSTANTIATION
     //                                             ASSIGN FORMULA STRING TO STRING VARIABLE - FOR OUTPUT PURPOSES ONLY

             if (FromTemp == CelsiusString) {                                           //  COMPARES OBJECTS..SO CAN'T COMPARE STRINGS - FROM CELSIUS IF
              switch (ToTemp.trim()) {                                                // TESTING EACH VALID CONVERT WITHIN FROM CELSIUS
                  case "Fahrenheit":                                                             //  NOTE: JAVA  does allow for "" compare w/ switch..should have used ref.id anyway?
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
            if (FromTemp == FahrenheitString) {    // TESTING FROM  FAHRENHEIT
                switch (ToTemp.trim()) {                                // TEST EACH VALID CONVERSION TYPE FROM FAHRENHEIT
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
                    switch (ToTemp.trim()) {                   // SWITCH FOR EVERY VALID CONVERSION FROM KELVIN
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
                        switch (ToTemp.trim()) {                 // SWITCH FOR EVERY VALID CONVERSION FROM RANKIN
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
              // end of CheckCalculation(int FromTemp, int ToTemp)

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
