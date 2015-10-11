package edu.kvcc.cis298.cis298assignment2;

/**
 * Created by dpantaleo on 10/6/2015.
 */
public class Calculate {

    private String convertTypeString;
    private double fromTempDouble;
    private  double toTempDouble;

    public Calculate()
    {}

    public Calculate(String convertString, int inputTemp) {
        convertTypeString = convertString;
        fromTempDouble = inputTemp;
        TestConvertType();
    }

    public void setConvertTypeString(String convertTypeString) {        //set needed
        this.convertTypeString = convertTypeString;
    }

    public void setFromTempDouble(double fromTempDouble) {            // set needed
        this.fromTempDouble = fromTempDouble;
    }

    public double getToTempDouble() {                               // get needed
        return toTempDouble;
    }

    public void setToTempDouble(double toTempDouble) {              // na
        this.toTempDouble = toTempDouble;
    }

    private  void  TestConvertType()
    {
    switch (convertTypeString)
    {
        case "c2f":
            CelsiusToFahrenheit();
            break;
        case "c2k":
            CelsiusToKelvin();
            break;
        case "c2r":
            CelsiusToRankin();
            break;

        case "f2c":
            FahrenheitToCelsius();
            break;
        case "f2k":
            FahrenheitToKelvin();
            break;
        case "f2r":
            FahrenheitToRankin();
            break;

        case "k2c":
            KelvinToCelsius();
            break;
        case "k2f":
            KelvinToFahrenheit();
            break;
        case "k2r":
            KelvinToRankin();
            break;

        case "r2c":
            RankinToCelsius();
            break;
        case "r2f":
            RankinToFahrenheit();
            break;
        case "r2k":
            RankinToKelvin();
            break;
        }
    }

    private void CelsiusToFahrenheit(){
        toTempDouble = fromTempDouble * 1.8 + 32;
    }

    private void CelsiusToKelvin() {
        toTempDouble =  fromTempDouble  + 273.15;
    }

    private void CelsiusToRankin() {
        toTempDouble = fromTempDouble * 1.8 + 32 + 459.67;
    }

    private void FahrenheitToCelsius() {
          toTempDouble =( fromTempDouble - 32) / 1.8;
    }

    private void FahrenheitToKelvin() {
          toTempDouble = (fromTempDouble + 459.67) / 1.8;
    }

    private void FahrenheitToRankin() {
          toTempDouble = fromTempDouble + 459.67;
    }

    private void KelvinToCelsius() {
         toTempDouble = fromTempDouble - 273.15;
    }

    private void KelvinToFahrenheit() {
          toTempDouble = fromTempDouble * 1.8 - 459.67;
    }

    private void KelvinToRankin() {
          toTempDouble = fromTempDouble * 1.8;
    }

    private void RankinToCelsius() {
        toTempDouble = ( fromTempDouble - 32 - 459.67) / 1.8;
    }

    private void  RankinToFahrenheit() {
          toTempDouble =  fromTempDouble - 459.67;
    }

    private void RankinToKelvin() {
          toTempDouble = fromTempDouble / 1.8;
    }

}


