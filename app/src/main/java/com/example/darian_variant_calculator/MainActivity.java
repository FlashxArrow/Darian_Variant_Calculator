package com.example.darian_variant_calculator;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

        import com.google.android.material.button.MaterialButton;


        import javax.script.ScriptEngine;
        import javax.script.ScriptEngineManager;
        import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView solution,result;

    MaterialButton button_c,button_open_bracket,button_close_bracket;

    MaterialButton button_divide,button_multiply,button_plus,button_minus,button_equal;

    MaterialButton button_0,button_1,button_2,button_3,button_4,button_5,button_6,button_7,
            button_8,button_9;

    MaterialButton button_ac,button_dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        solution = findViewById(R.id.solution);

        AssignId(button_c,R.id.button_c);
        AssignId(button_open_bracket,R.id.button_open_bracket);
        AssignId(button_close_bracket,R.id.button_close_bracket);
        AssignId(button_divide,R.id.button_divide);
        AssignId(button_multiply,R.id.button_multiply);
        AssignId(button_plus,R.id.button_plus);
        AssignId(button_minus,R.id.button_minus);
        AssignId(button_equal,R.id.button_equal);
        AssignId(button_0,R.id.button_0);
        AssignId(button_1,R.id.button_1);
        AssignId(button_2,R.id.button_2);
        AssignId(button_3,R.id.button_3);
        AssignId(button_4,R.id.button_4);
        AssignId(button_5,R.id.button_5);
        AssignId(button_6,R.id.button_6);
        AssignId(button_7,R.id.button_7);
        AssignId(button_8,R.id.button_8);
        AssignId(button_9,R.id.button_9);
        AssignId(button_ac,R.id.button_ac);
        AssignId(button_dot,R.id.button_dot);
    }

    void AssignId(MaterialButton btn, int id)
    {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {

        MaterialButton button = (MaterialButton) view;

        String buttonText = button.getText().toString();

        String datatoCalculate = solution.getText().toString();


        if(buttonText.equals("AC"))
        {
            solution.setText("");

            result.setText("0");

            CanUseDot = true;

            CanUseNumber = true;

            CanUseFirstOrderOperator = false;

            CanUseSecondOrderOperator = true;

            return;
        }

        if(buttonText.equals("="))
        {
            solution.setText(result.getText());

            return;
        }

        if(buttonText.equals("C"))
        {
            if (datatoCalculate.length() - 1 > 0) {
                datatoCalculate = datatoCalculate.substring(0, datatoCalculate.length() - 1);
            } else datatoCalculate = "0";
        }
        else if (CanAddNumber(buttonText) == 1)
        {
            datatoCalculate = datatoCalculate + buttonText;
        }
        else if (CanAddFirstOrderOperator(buttonText) == 1)
        {
            datatoCalculate = datatoCalculate + buttonText;
        }
        else if (CanAddSecondOrderOperator(buttonText) == 1)
        {
            datatoCalculate = datatoCalculate + buttonText;
        }
        else if (CanYouDot(buttonText) == 1)
        {
            datatoCalculate = datatoCalculate + buttonText;
        }
        else if (CanAddOpenBracket(buttonText) == 1)
        {
            datatoCalculate = datatoCalculate + buttonText;
        }
        else if (CanAddClosedBracket(buttonText) == 1)
        {
            datatoCalculate = datatoCalculate + buttonText;
        }

        solution.setText(datatoCalculate);

        String finalResult = getResult(datatoCalculate);

        if(!finalResult.equals("Error"))
        {
            result.setText(finalResult);
        }
    }

    String getResult(String data)
    {
        Double result = null;
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        try {
            result = (double)engine.eval(data);
            String FinalResult = "0";
            if(result != null)
            {FinalResult = result.toString();
                if(FinalResult.endsWith(".0"))
                {
                    FinalResult = FinalResult.replace(".0","");
                }}
            return FinalResult;
        } catch (ScriptException e)
        {
            return "Error";
        }

    }

    boolean CanUseSecondOrderOperator = false;

    boolean CanUseNumber = true;

    boolean CanUseDot = true;

    boolean CanUseFirstOrderOperator = false;

    int NumberOfOpenBracket = 0, NumberOfClosedBracket = 0;

    int CanAddFirstOrderOperator(String buttonText)
    {
        if(buttonText.equals("+")||buttonText.equals("-"))
        {
            if(CanUseFirstOrderOperator == true)
            {
                CanUseDot = true;
                CanUseFirstOrderOperator = false;
                CanUseSecondOrderOperator = false;
                CanUseNumber = true;
                return 1;
            }
            else return 0;
        }
        else
            return 0;
    }


    int CanAddSecondOrderOperator(String buttonText)
    {
        if(buttonText.equals("*")||buttonText.equals("/"))
        {
            if(CanUseSecondOrderOperator == true)
            {
                CanUseDot = true;
                CanUseFirstOrderOperator = false;
                CanUseSecondOrderOperator = false;
                CanUseNumber = true;
                return 1;
            }
            else return 0;
        }
        else
            return 0;
    }


    int CanYouDot(String ButtonText)
    {
        if(ButtonText.equals(".") && CanUseDot == true &&
                CanUseFirstOrderOperator == true && CanUseSecondOrderOperator == true)
        {
            CanUseDot = false;
            return 1;
        }
        else
            return 0;
    }
    int CanAddNumber(String buttonText)
    {
        if(buttonText.equals("0")||buttonText.equals("1")||buttonText.equals("2")||buttonText.equals("3")
                ||buttonText.equals("4")||buttonText.equals("5")||buttonText.equals("6")|| buttonText.equals("7")
                ||buttonText.equals("8")||buttonText.equals("9"))
        {
            if(CanUseNumber == true)
            {
                CanUseFirstOrderOperator = true;
                CanUseSecondOrderOperator = true;
                return 1;
            }
            else return 0;
        }
        else
            return 0;
    }

    int CanAddOpenBracket(String buttonText)
    {
        if(buttonText.equals("(") && CanUseFirstOrderOperator == false)
        {
            NumberOfOpenBracket = NumberOfOpenBracket + 1;
            CanUseFirstOrderOperator = true;
            return 1;
        }
        else
            return 0;
    }

    int CanAddClosedBracket(String buttonText)
    {
        if(buttonText.equals(")") && NumberOfClosedBracket < NumberOfOpenBracket)
        {
            NumberOfClosedBracket = NumberOfClosedBracket + 1;
            CanUseNumber = false;
            CanUseFirstOrderOperator = true;
            CanUseSecondOrderOperator = true;
            return 1;
        }
        else
            return 0;
    }

}

