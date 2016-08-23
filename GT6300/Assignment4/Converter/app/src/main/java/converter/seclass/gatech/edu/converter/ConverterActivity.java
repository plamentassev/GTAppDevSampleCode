package converter.seclass.gatech.edu.converter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ConverterActivity extends AppCompatActivity {
    public static final CharSequence[] DAYS_OPTIONS = {"Mile", "Foot", "Inch", "Kilometer", "Meter", "Centimeter"};
    public static final CharSequence[] TO_OPTIONS= {"Mile", "Foot", "Inch", "Kilometer", "Meter", "Centimeter"};

    private static final String MILE = "Mile";
    private static final String FOOT = "Foot";
    private static final String INCH = "Inch";
    private static final String KM = "Kilometer";
    private static final String METER = "Meter";
    private static final String CM = "Centimeter";

    //Primary Values
    private static final double MILES_TO_FEET_FACTOR = 5280;
    private static final double MILES_TO_INCH_FACTOR = 63360;
    private static final double MILES_TO_KM_FACTOR = 1.60934;
    private static final double MILES_TO_METER_FACTOR = 1609.34;
    private static final double MILES_TO_CM_FACTOR = 160934;

    private static final double FEET_TO_INCH_FACTOR = 12;
    private static final double FEET_TO_CM_FACTOR = 30.48;

    private static final double INCH_TO_CM_FACTOR = 2.54;

    private static final double KM_TO_FEET_FACTOR = 3280.84;
    private static final double KM_TO_INCH_FACTOR = 39370.1;
    private static final double KM_TO_METER_FACTOR = 1000;
    private static final double KM_TO_CM_FACTOR = 100000;

    private static final double METER_TO_FEET_FACTOR = 3.28084;
    private static final double METER_TO_INCH_FACTOR = 39.3701;
    private static final double METER_TO_CM_FACTOR = 100;

    //Derived values
    private static final double FEET_TO_MILES_FACTOR = 1 / MILES_TO_FEET_FACTOR;
    private static final double FEET_TO_KM_FACTOR = 1 / KM_TO_FEET_FACTOR;
    private static final double FEET_TO_METER_FACTOR = 1 / METER_TO_FEET_FACTOR;

    private static final double INCH_TO_MILES_FACTOR = 1 / MILES_TO_INCH_FACTOR;
    private static final double INCH_TO_FEET_FACTOR = 1 / FEET_TO_INCH_FACTOR;
    private static final double INCH_TO_KM_FACTOR = 1 / KM_TO_INCH_FACTOR;
    private static final double INCH_TO_METER_FACTOR = 1 / METER_TO_INCH_FACTOR;

    private static final double KM_TO_MILES_FACTOR = 1 / MILES_TO_FEET_FACTOR;

    private static final double METER_TO_MILES_FACTOR = 1 / MILES_TO_METER_FACTOR;
    private static final double METER_TO_KM_FACTOR = 1 / KM_TO_METER_FACTOR;


    private static final double CM_TO_MILES_FACTOR = 1 / MILES_TO_CM_FACTOR;
    private static final double CM_TO_FEET_FACTOR =  1 / FEET_TO_CM_FACTOR;
    private static final double CM_TO_INCH_FACTOR = 1 / INCH_TO_CM_FACTOR;
    private static final double CM_TO_KM_FACTOR = 1 / KM_TO_CM_FACTOR;
    private static final double CM_TO_CM_METER = 1 / METER_TO_CM_FACTOR;

    private EditText distValue;
    private EditText distResult;
    private String spinnerFromSelection;
    private String spinnerToSelection;
    private Spinner spinnerFrom;
    private Spinner spinnerTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        spinnerFrom = (Spinner) findViewById(R.id.spinnerFrom);
        ArrayAdapter<CharSequence> adapterTo = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, DAYS_OPTIONS);
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        spinnerFrom.setAdapter(adapterTo); // Apply the adapter to the spinner

        spinnerTo = (Spinner) findViewById(R.id.spinnerTo);
        ArrayAdapter<CharSequence> adapterFrom = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, TO_OPTIONS);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        spinnerTo.setAdapter(adapterFrom); // Apply the adapter to the spinner


        distValue = (EditText)findViewById(R.id.distValue);
        distResult = (EditText)findViewById(R.id.distResult);
    }

    public void handleClick(View view){

        spinnerFromSelection = spinnerFrom.getSelectedItem().toString();
        spinnerToSelection = spinnerTo.getSelectedItem().toString();

        String unitFrom  = "Mile";
        String unitTo = "Mile";

        switch (view.getId()){
            case R.id.buttonConvert:
                String value = distValue.getText().toString();
                if(value.length() > 0){
                    if(spinnerFromSelection.equalsIgnoreCase(MILE)){
                        unitFrom = MILE;
                    }
                    if(spinnerToSelection.equalsIgnoreCase(MILE)){
                        unitTo = MILE;
                    }
                    if(spinnerFromSelection.equalsIgnoreCase(FOOT)){
                        unitFrom = FOOT;
                    }
                    if(spinnerToSelection.equalsIgnoreCase(FOOT)){
                        unitTo = FOOT;
                    }
                    if(spinnerFromSelection.equalsIgnoreCase(INCH)){
                        unitFrom = INCH;
                    }
                    if(spinnerToSelection.equalsIgnoreCase(INCH)){
                        unitTo = INCH;
                    }
                    if(spinnerFromSelection.equalsIgnoreCase(KM)) {
                        unitFrom = KM;
                    }
                    if(spinnerToSelection.equalsIgnoreCase(KM)){
                        unitTo = KM;
                    }
                    if(spinnerFromSelection.equalsIgnoreCase(KM)){
                        unitFrom = KM;
                    }
                    if(spinnerToSelection.equalsIgnoreCase(METER)){
                        unitTo = METER;
                    }
                    if(spinnerFromSelection.equalsIgnoreCase(CM)){
                        unitFrom = CM;
                    }
                    if(spinnerToSelection.equalsIgnoreCase(CM)){
                        unitTo = CM;
                    }
                    if(unitFrom.contentEquals(unitTo)){
                        distResult.setText(value);
                    }
                    if(unitFrom.contentEquals(unitTo)){
                        distResult.setText(value);
                    } else {
                        if (unitFrom.contentEquals(MILE)){
                            if(unitTo.contentEquals(FOOT)){
                                distResult.setText(convertMeasurements(value, MILES_TO_FEET_FACTOR));
                            } else if (unitTo.contentEquals(INCH)){
                                distResult.setText(convertMeasurements(value, MILES_TO_INCH_FACTOR));
                            } else if (unitTo.contentEquals(KM)){
                                distResult.setText(convertMeasurements(value, MILES_TO_KM_FACTOR));
                            } else if (unitTo.contentEquals(METER)){
                                distResult.setText(convertMeasurements(value, MILES_TO_METER_FACTOR));
                            } else {
                                distResult.setText(convertMeasurements(value, MILES_TO_CM_FACTOR));
                            }
                        } else if(unitFrom.contentEquals(FOOT)){
                            if (unitTo.contentEquals(MILE)){
                                distResult.setText(convertMeasurements(value, FEET_TO_MILES_FACTOR));
                            } else if (unitTo.contentEquals(INCH)){
                                distResult.setText(convertMeasurements(value, FEET_TO_INCH_FACTOR));
                            } else if (unitTo.contentEquals(KM)){
                                distResult.setText(convertMeasurements(value, FEET_TO_KM_FACTOR));
                            }else if(unitTo.contentEquals(METER)){
                                distResult.setText(convertMeasurements(value, FEET_TO_METER_FACTOR));
                            }else {
                                distResult.setText(convertMeasurements(value, FEET_TO_CM_FACTOR));
                            }
                        } else if(unitFrom.contentEquals(INCH)){
                            if (unitTo.contentEquals(MILE)){
                                distResult.setText(convertMeasurements(value, INCH_TO_MILES_FACTOR));
                            } else if (unitTo.contentEquals(FOOT)){
                                distResult.setText(convertMeasurements(value, INCH_TO_FEET_FACTOR));
                            } else if (unitTo.contentEquals(KM)){
                                distResult.setText(convertMeasurements(value, INCH_TO_KM_FACTOR));
                            }else if(unitTo.contentEquals(METER)){
                                distResult.setText(convertMeasurements(value, INCH_TO_METER_FACTOR));
                            }else {
                                distResult.setText(convertMeasurements(value, INCH_TO_CM_FACTOR));
                            }
                        } else if(unitFrom.contentEquals(KM)){
                            if (unitTo.contentEquals(MILE)){
                                distResult.setText(convertMeasurements(value, KM_TO_MILES_FACTOR));
                            } else if (unitTo.contentEquals(FOOT)){
                                distResult.setText(convertMeasurements(value, KM_TO_FEET_FACTOR));
                            } else if (unitTo.contentEquals(INCH)){
                                distResult.setText(convertMeasurements(value, KM_TO_INCH_FACTOR));
                            }else if(unitTo.contentEquals(METER)){
                                distResult.setText(convertMeasurements(value, KM_TO_METER_FACTOR));
                            }else {
                                distResult.setText(convertMeasurements(value, KM_TO_CM_FACTOR));
                            }
                        } else if(unitFrom.contentEquals(METER)){
                            if (unitTo.contentEquals(MILE)){
                                distResult.setText(convertMeasurements(value, METER_TO_MILES_FACTOR));
                            } else if (unitTo.contentEquals(FOOT)){
                                distResult.setText(convertMeasurements(value, METER_TO_FEET_FACTOR));
                            } else if (unitTo.contentEquals(INCH)){
                                distResult.setText(convertMeasurements(value, METER_TO_INCH_FACTOR));
                            }else if(unitTo.contentEquals(KM)){
                                distResult.setText(convertMeasurements(value, METER_TO_KM_FACTOR));
                            }else {
                                distResult.setText(convertMeasurements(value, METER_TO_CM_FACTOR));
                            }
                        } else if(unitFrom.contentEquals(CM)){
                            if (unitTo.contentEquals(MILE)){
                                distResult.setText(convertMeasurements(value, CM_TO_MILES_FACTOR));
                            } else if (unitTo.contentEquals(FOOT)){
                                distResult.setText(convertMeasurements(value, CM_TO_FEET_FACTOR));
                            } else if (unitTo.contentEquals(INCH)){
                                distResult.setText(convertMeasurements(value, CM_TO_INCH_FACTOR));
                            }else if(unitTo.contentEquals(KM)){
                                distResult.setText(convertMeasurements(value, CM_TO_KM_FACTOR));
                            }else {
                                distResult.setText(convertMeasurements(value, CM_TO_CM_METER));
                            }
                        }
                    }

                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Empty value!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                break;
            case R.id.buttonReset:
                distValue.setText("");
                distResult.setText("");
                break;
        }
    }

    public String convertMeasurements(String strInput, double factor){
        double input = Double.parseDouble(strInput);
        double result  = input * factor;
        DecimalFormat format = new DecimalFormat("#.##");
        return String.valueOf(result);
    }
}
