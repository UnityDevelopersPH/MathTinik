package mathtinik.thesis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class challenge_template extends AppCompatActivity {

    ArrayList<Integer> answers = new ArrayList<>();
    ObjectAnimator anim;
    int locatiionOfCorrectAnswer;
    int num1;
    int num2;
    int checkCounts,wrongCounts;
    TextView question_One,question_Two,operation,ans1,ans2,ans3,ans4;
    Button submitBtn;
    EditText targetAns;
    StoringData storingData;
    int coinCounts;
    TextView checkCount,wrongCount,coinCount;
    ImageView life_one,life_two,life_three;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_template);
        question_One = findViewById(R.id.question_One);
        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);
        ans3 = findViewById(R.id.ans3);
        ans4 = findViewById(R.id.ans4);
        storingData = new StoringData();
        checkCount = findViewById(R.id.countCheck);
        wrongCount = findViewById(R.id.countWrong);
        coinCount = findViewById(R.id.coinCount);
        operation = findViewById(R.id.operation);
        life_one = findViewById(R.id.life_one);
        life_two = findViewById(R.id.life_two);
        life_three = findViewById(R.id.life_three);

        question_Two = findViewById(R.id.question_Two);

        submitBtn = findViewById(R.id.submitBtn);
        targetAns = findViewById(R.id.targetAns);

        ans1.setOnLongClickListener(longClickListener);
        ans2.setOnLongClickListener(longClickListener);
        ans3.setOnLongClickListener(longClickListener);
        ans4.setOnLongClickListener(longClickListener);

        targetAns.setOnDragListener(dragListener);

        generateQuestion();
        submitBtn();
        if (MainActivity.prefs.getInt("Coins",0) == 0){
            coinCount.setText("0");
        }else{
            coinCount.setText(String.valueOf(MainActivity.prefs.getInt("Coins",0)));
        }

        if (MainActivity.prefs.getString("operation",null) == "Addition"){
            operation.setText("+");
        }else if(MainActivity.prefs.getString("operation",null) == "Subraction"){
            operation.setText("-");
        }else if(MainActivity.prefs.getString("operation",null) == "Multiplication"){
            operation.setText("x");
        }else if(MainActivity.prefs.getString("operation",null) == "Division"){
            operation.setText("÷");
        }


    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data,shadowBuilder,v,0);
            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();
            final View view = (View)event.getLocalState();
            switch (dragEvent){

                case DragEvent.ACTION_DRAG_ENTERED:

                    if (view.getId() == R.id.ans1){
                        String getAns1= ans1.getText().toString();
                        targetAns.setText(getAns1);
                    }else  if (view.getId() == R.id.ans2){
                        String getAns2= ans2.getText().toString();
                        targetAns.setText(getAns2);
                    }else  if (view.getId() == R.id.ans3){
                        String getAns3= ans3.getText().toString();
                        targetAns.setText(getAns3);
                    }else  if (view.getId() == R.id.ans4){
                        String getAns4= ans4.getText().toString();
                        targetAns.setText(getAns4);
                    }
                    break;
                case  DragEvent.ACTION_DRAG_EXITED:
                    if (view.getId() == R.id.ans1){
                        String getAns1= ans1.getText().toString();
                        targetAns.setText(getAns1);
                    }else  if (view.getId() == R.id.ans2){
                        String getAns2= ans2.getText().toString();
                        targetAns.setText(getAns2);
                    }else  if (view.getId() == R.id.ans3){
                        String getAns3= ans3.getText().toString();
                        targetAns.setText(getAns3);
                    }else  if (view.getId() == R.id.ans4){
                        String getAns4= ans4.getText().toString();
                        targetAns.setText(getAns4);
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    if (view.getId() == R.id.ans1){
                        String getAns1= ans1.getText().toString();
                        targetAns.setText(getAns1);
                    }else  if (view.getId() == R.id.ans2){
                        String getAns2= ans2.getText().toString();
                        targetAns.setText(getAns2);
                    }else  if (view.getId() == R.id.ans3){
                        String getAns3= ans3.getText().toString();
                        targetAns.setText(getAns3);
                    }else  if (view.getId() == R.id.ans4){
                        String getAns4= ans4.getText().toString();
                        targetAns.setText(getAns4);
                    }
            }
            return true;
        }
    };


    public void generateQuestion(){
        Random rand= new Random();
       int answer=0;
        int threeWrongAnswers=0;
        num1 = rand.nextInt(10)+1;
        num2 = rand.nextInt(10)+1;
        if (MainActivity.prefs.getString("operation",null) == "Addition"){
           answer = num1 + num2;
        }else if(MainActivity.prefs.getString("operation",null) == "Subraction"){
            answer = num1 - num2;
        }else if(MainActivity.prefs.getString("operation",null) == "Multiplication"){
            answer = num1 * num2;
        }else if(MainActivity.prefs.getString("operation",null) == "Division"){
            answer = num1 / num2;
        }
        question_One.setText(Integer.toString(num1));
        question_Two.setText(Integer.toString(num2));
        answers.add(Math.abs(answer));
        answers.add(Math.abs(answer+1));
        answers.add(Math.abs(answer+2));
        answers.add(Math.abs(answer-1));
        Collections.shuffle(answers);

        for(int x = 0;x<answers.size();x++){
            if (answer == answers.get(x)){
                locatiionOfCorrectAnswer = x;
            }
        }

        ans1.setText(Integer.toString(answers.get(0)));
        ans2.setText(Integer.toString(answers.get(1)));
        ans3.setText(Integer.toString(answers.get(2)));
        ans4.setText(Integer.toString(answers.get(3)));

        Log.d("showError",Integer.toString(answers.get(0))+Integer.toString(answers.get(1)));

        answers.clear();


    }

    public void submitBtn(){

       submitBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               int Fnum1 = Integer.parseInt(question_One.getText().toString());
               int Fnum2 = Integer.parseInt(question_Two.getText().toString());
               int checkAns = 0;
               if (MainActivity.prefs.getString("operation",null) == "Addition"){
                   checkAns = Fnum1 + Fnum2;
               }else if(MainActivity.prefs.getString("operation",null) == "Subraction"){
                    checkAns = Fnum1 - Fnum2;
               }else if(MainActivity.prefs.getString("operation",null) == "Multiplication"){
                   checkAns = Fnum1 * Fnum2;
               }else if(MainActivity.prefs.getString("operation",null) == "Division"){
                   checkAns = Fnum1 / Fnum2;
               }

               Log.d("s",String.valueOf(checkAns));

               if (targetAns.getText().toString().equals("")) {

                   Toast.makeText(challenge_template.this, "Please Drag and Drop your Answer", Toast.LENGTH_SHORT).show();

               }else if (targetAns.getText().toString().equals(String.valueOf(checkAns))){

                           checkCounts++;
                           checkCount.setText(String.valueOf(checkCounts));
                           int getCoins = Integer.parseInt(coinCount.getText().toString());
                           getCoins++;
                               int getLevelUnlock = MainActivity.prefs.getInt("UnlockLevel1-30",1);
                               getLevelUnlock++;
                               MainActivity.editor.putInt("UnlockLevel1-30", getLevelUnlock);
                           MainActivity.editor.putInt("Coins", getCoins);
                           MainActivity.editor.apply();
                   final AlertDialog cdialog = new AlertDialog.Builder(challenge_template.this).create();
                   LayoutInflater inflater = getLayoutInflater();
                   View cView = (View) inflater.inflate(R.layout.correct, null);
                   cdialog.setView(cView);
                   cdialog.getWindow().getAttributes().windowAnimations = R.style.DialogScale;
                   cdialog.setCanceledOnTouchOutside(true);
                   cdialog.setCancelable(false);
                   cdialog.show();
                   final Handler handler = new Handler();
                   final Runnable r = new Runnable()
                   {
                       public void run()
                       {
                           generateQuestion();
                           cdialog.dismiss();
                       }
                   };
                   handler.postDelayed(r, 1000);
                           coinCount.setText(String.valueOf(MainActivity.prefs.getInt("Coins",0)));
                           targetAns.setText("");
                           generateQuestion();

                       }else{
                           wrongCounts++;
                           wrongCount.setText(String.valueOf(wrongCounts));
                           targetAns.setText("");

                           final AlertDialog wdialog = new AlertDialog.Builder(challenge_template.this).create();
                           LayoutInflater inflater = getLayoutInflater();
                           View wView = (View) inflater.inflate(R.layout.wrong, null);
                           wdialog.setView(wView);
                           wdialog.getWindow().getAttributes().windowAnimations = R.style.DialogScale;
                           wdialog.setCanceledOnTouchOutside(true);
                           wdialog.setCancelable(false);
                           wdialog.show();
                           final Handler handler = new Handler();
                           final Runnable r = new Runnable()
                           {
                               public void run()
                               {
                                   generateQuestion();
                                   wdialog.dismiss();
                               }
                           };
                           handler.postDelayed(r, 1000);

                           if (wrongCount.getText().toString().equals("1")){
                               life_one.setVisibility(View.GONE);
                           }
                           if (wrongCount.getText().toString().equals("2")){
                               life_two.setVisibility(View.GONE);
                           }
                           if (wrongCount.getText().toString().equals("3")){
                               life_two.setVisibility(View.GONE);
                               final AlertDialog gdialog = new AlertDialog.Builder(challenge_template.this).create();
                               LayoutInflater ginflater = getLayoutInflater();
                               View gView = (View) inflater.inflate(R.layout.gameover, null);
                               TextView getCoins = gView.findViewById(R.id.getCoins);
                               Button close_gameover = gView.findViewById(R.id.close_gameover);
                               getCoins.setText(checkCount.getText().toString());
                               gdialog.setView(gView);

                               close_gameover.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent backToHome = new Intent(challenge_template.this,gamechoices.class);
                                       startActivity(backToHome);
                                   }
                               });
                               gdialog.setCanceledOnTouchOutside(true);
                               gdialog.setCancelable(false);

                               gdialog.getWindow().getAttributes().windowAnimations = R.style.DialogScale;

                               gdialog.show();
                           }

                       }
                   }

       });

    }


    private void restoreUI(){
        ans1.setBackgroundColor(ContextCompat.getColor(challenge_template.this,R.color.colorPrimary));
        ans2.setBackgroundColor(ContextCompat.getColor(challenge_template.this,R.color.colorPrimary));
        ans3.setBackgroundColor(ContextCompat.getColor(challenge_template.this,R.color.colorPrimary));
        ans4.setBackgroundColor(ContextCompat.getColor(challenge_template.this,R.color.colorPrimary));
    }













}