package com.example.quiz_game1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView questionTextView;
    TextView totalQuestionTextView;
    Button ansA,ansB,ansC,ansD;
    Button btn_submit;
    int score=0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex=0;
    String selectedAnswers="";
    boolean canChangeButtonColor = true;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA=findViewById(R.id.ans_a);
        ansB=findViewById(R.id.ans_b);
        ansC=findViewById(R.id.ans_c);
        ansD=findViewById(R.id.ans_d);
        btn_submit=findViewById(R.id.btn_submit);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        totalQuestionTextView.setText("Total question: "+totalQuestion);

        loadNewQuestion();
        Button instructionButton = findViewById(R.id.instructionButton);
        instructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInstructionPopup();
            }
        });
        Button exitButton = findViewById(R.id.btn_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the app
            }
        });
    }
    private void showInstructionPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("How to Play Quiz App");
        builder.setMessage("Welcome to the Quiz App!\n" +
                "* Start the Game: Open the quiz application on your device.\n"+
                "* Read the Question: The main area of the screen will display a question.\n"+
                 "* Select an Answer: Below the question, you'll find four buttons labeled 'Ans A,' 'Ans B,' 'Ans C,' and 'Ans D.'\n"+
                "* Each corresponds to a multiple-choice answer.\n"+
                 "* Choose Your Answer: Tap the button that corresponds to the answer you believe is correct.\n"+
                "* Submit Your Answer: After selecting your answer, tap the 'Submit' button located at the bottom of the screen.\n"+
                "* Next Question: After receiving feedback, the next question will automatically appear on the screen.\n"+
                "* Repeat Steps 2-7: Continue answering questions until you've completed the quiz.\n"+
                "* View Total Score: Once you've answered all the questions, you'll see your total score displayed on the screen.\n"+
                "* Restart or Exit: You can choose to restart the quiz or exit the application, depending on your preference.\n");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close the dialog if OK button is clicked
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void loadNewQuestion(){
        if(currentQuestionIndex == totalQuestion){
            finishQuiz();
            return;
        }
        String questionText = "" + (currentQuestionIndex + 1) + ":  " + QuestionAnswer.question[currentQuestionIndex];
        questionTextView.setText(questionText);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

        selectedAnswers="";
        btn_submit.setBackgroundColor(Color.parseColor("#31F338"));
        canChangeButtonColor = true;

    }
    private void finishQuiz(){
        String passStatus;
        if(score >= totalQuestion*0.6) {
            passStatus = "Passed";
        }else {
            passStatus="Failed";

        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Your score is "+score+" Out of "+totalQuestion)
                .setPositiveButton("Restart",((dialog, i) -> restartQuiz() ))
                .setCancelable(false)
                .show();
    }
    private void restartQuiz(){
        score=0;
        currentQuestionIndex=0;
        loadNewQuestion();
    }
    // Trigger the instruction popup when a button is clicked

    @Override
    public void onClick(View view){
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if(clickedButton.getId() == R.id.btn_submit){
            if(!selectedAnswers.isEmpty()){
                if(selectedAnswers.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                    score++;
                }else{
                    clickedButton.setBackgroundColor(Color.MAGENTA);
                }
                currentQuestionIndex++;
                loadNewQuestion();
            }else {

            }
            }
        else {
            selectedAnswers = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.YELLOW);
        }
    }
}
