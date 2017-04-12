package cz.cattie.farmanimalsquiz;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * When animal ImageButton is clicked
     */
    public void animalImageClick(View view){

        int resourceId;
        switch(view.getId()){
            default:
                resourceId = R.raw.sheep; break;

            case R.id.animal2Image:
            case R.id.animal12Image:
                resourceId = R.raw.chicken; break;

            case R.id.animal3Image:
            case R.id.animal13Image:
                resourceId = R.raw.duck; break;

            case R.id.animal4Image:
                resourceId = R.raw.rooster; break;

            case R.id.animal5Image:
                resourceId = R.raw.pig; break;

            case R.id.animal6Image:
                resourceId = R.raw.cat; break;

            case R.id.animal7Image:
                resourceId = R.raw.dog; break;

            case R.id.animal8Image:
                resourceId = R.raw.cow; break;

            case R.id.animal9Image:
                resourceId = R.raw.goat; break;

            case R.id.animal10Image:
                resourceId = R.raw.horse; break;
        }

        // we play sound here
        MediaPlayer mp = MediaPlayer.create(this, resourceId);
        mp.start();

    }

    /**
     * When finish button is clicked
     */
    public void finishClick(View view){

        EditText userNameET = (EditText)findViewById(R.id.user_name);
        String userName = userNameET.getText().toString();
        if(userName.isEmpty()) {
            Toast.makeText(this, R.string.error_name, Toast.LENGTH_LONG).show();
            return;
        }

        RadioGroup group1 = (RadioGroup)findViewById(R.id.animal1Group);
        RadioGroup group2 = (RadioGroup)findViewById(R.id.animal2Group);
        RadioGroup group3 = (RadioGroup)findViewById(R.id.animal3Group);
        RadioGroup group4 = (RadioGroup)findViewById(R.id.animal4Group);
        RadioGroup group5 = (RadioGroup)findViewById(R.id.animal5Group);
        RadioGroup group6 = (RadioGroup)findViewById(R.id.animal6Group);
        RadioGroup group7 = (RadioGroup)findViewById(R.id.animal7Group);
        RadioGroup group8 = (RadioGroup)findViewById(R.id.animal8Group);
        RadioGroup group9 = (RadioGroup)findViewById(R.id.animal9Group);
        RadioGroup group10 = (RadioGroup)findViewById(R.id.animal10Group);

        RadioGroup[] radioGroups = { group1, group2, group3, group4, group5, group6, group7, group8, group9, group10 };

        int correctAnswers = 0;

        for(int i=0; i<radioGroups.length; i++){
            if(!isAnswerSelected(radioGroups[i])){
                Toast.makeText(this, R.string.error_missing_answer, Toast.LENGTH_LONG).show();
                return;
            }

            if(isAnswerCorrect(radioGroups[i]))
                correctAnswers++;
        }

        LinearLayout answers1 = (LinearLayout)findViewById(R.id.correctAnswers1);
        LinearLayout answers2 = (LinearLayout)findViewById(R.id.correctAnswers2);
        LinearLayout answers3 = (LinearLayout)findViewById(R.id.correctAnswers3);

        LinearLayout[] multiAnswers = {answers1, answers2, answers3 };
        for(int i=0; i<multiAnswers.length; i++) {
            if(isAnswerCorrect(multiAnswers[i]))
                correctAnswers++;
        }

        Toast.makeText(this, String.format(getString(R.string.finish_message), userName, correctAnswers), Toast.LENGTH_LONG).show();
    }

    /**
     * Determine if RadioGroup has checked RadioButton
     */
    private boolean isAnswerSelected(RadioGroup radioGroup){
        int id = radioGroup.getCheckedRadioButtonId();
        return id != -1;
    }

    /**
     * Determine if answers are correct in LinearLayout childrens (CheckBoxes)
     */
    private boolean isAnswerCorrect(LinearLayout linearLayout){
        for(int i=0; i<linearLayout.getChildCount(); i++){
            CheckBox checkBox = (CheckBox)linearLayout.getChildAt(i);
            boolean shouldBeChecked = Boolean.parseBoolean(checkBox.getTag().toString());
            if(checkBox.isChecked() && !shouldBeChecked) {
                return false;
            } else if(!checkBox.isChecked() && shouldBeChecked) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determine if selected answer on RadioGroup is correct
     */
    private boolean isAnswerCorrect(RadioGroup radioGroup){
        // we store correct answer position in RadioGroup tag
        int correctAnswerPosition = Integer.parseInt(radioGroup.getTag().toString());
        for(int i=0; i<radioGroup.getChildCount(); i++){
            RadioButton radioButton = (RadioButton)radioGroup.getChildAt(i);
            if(radioButton.isChecked()){
                if(i == correctAnswerPosition) return true;
                return false;
            }
        }

        return false;
    }
}
