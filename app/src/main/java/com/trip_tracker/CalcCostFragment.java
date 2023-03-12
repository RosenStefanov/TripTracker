package com.trip_tracker;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalcCostFragment extends Fragment {

    private TextView distanceText;
    private double distance;
    private EditText tripConsInput;
    private EditText fuelPriceInput;
    private Button calcCostBtn;
    private TextView outputText;

    public CalcCostFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_calc_cost, container, false);
        distance = getArguments().getDouble("distance");
        distanceText =  inf.findViewById(R.id.CCF_distance);
        distanceText.setText(getString(R.string.CCF_distance_text, String.valueOf(distance)));
        tripConsInput =  inf.findViewById(R.id.CCF_ex_cons_edittext);
        fuelPriceInput =  inf.findViewById(R.id.CCF_fuel_price_edittext);
        calcCostBtn =  inf.findViewById(R.id.CCF_calc_cost_btn);
        outputText =  inf.findViewById(R.id.CCF_output);
        calcCostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateCost();
            }
        });
        return inf;
    }

    public void calculateCost(){
        String consString = tripConsInput.getText().toString();
        String priceString = fuelPriceInput.getText().toString();
        double consd = 0;
        double priced = 0;
        double output;
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        if (!consString.isEmpty()) {
            consd = Double.parseDouble(consString);
        }
        if (!priceString.isEmpty()) {
            priced = Double.parseDouble(priceString);
        }
        if (consd != 0 && priced != 0){
            output = distance / 100 * consd * priced;
            output = Math.round(output * 100) / 100D;
            outputText.setText(String.valueOf(output));
        }
    }
}