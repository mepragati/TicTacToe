package com.pragati.tictactoe

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var activePlayer = 1
    var gameIsActive = true
    var count = 0
    var countBack = 0
    var playerchance = 0
    var colorInactive = Color.TRANSPARENT
    var colorActive = Color.BLACK
    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    var winningPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    lateinit var txtPlayer1:TextView
    lateinit var txtPlayer2:TextView
    lateinit var editPlayer1:EditText
    lateinit var editPlayer2:EditText
    lateinit var buttonStart:Button
    lateinit var buttonRestart:Button
    lateinit var buttonExit:Button
    lateinit var layoutPlayer1:RelativeLayout
    lateinit var layoutPlayer2:RelativeLayout

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        title=" Tic Tac Toe"

        initialize();

        val start = findViewById<LinearLayout>(R.id.details)
        start.visibility =View.VISIBLE;

        buttonStart.setOnClickListener(View.OnClickListener {
            var name1: String = "Player O";
            var name2: String = "Player X";

            if(editPlayer1.getText().toString().trim() != "")
                name1 = editPlayer1.getText().toString();
            if(editPlayer2.getText().toString().trim() != "")
                name2 = editPlayer2.getText().toString();
            txtPlayer1.setText(name1);
            txtPlayer2.setText(name2);
            editPlayer1.setText("")
            editPlayer2.setText("")
            start.visibility = View.INVISIBLE
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        });

        buttonRestart.setOnClickListener(View.OnClickListener {
            start.visibility =View.VISIBLE;
            backGroundPlayer()
            restartGame(it)
        });

        buttonExit.setOnClickListener ( View.OnClickListener {
                finish();
            Toast.makeText(this,"You Pressed Exit Game!", Toast.LENGTH_SHORT).show()
        });
        backGroundPlayer();
    }

    private fun backGroundPlayer() {
        if(gameIsActive == false) {
            layoutPlayer1.setBackgroundColor(colorInactive);
            layoutPlayer2.setBackgroundColor(colorInactive);
        }
        else{
            if(activePlayer == 1){
                layoutPlayer1.setBackgroundColor(colorActive);
                layoutPlayer2.setBackgroundColor(colorInactive);
            }
            else{
                layoutPlayer1.setBackgroundColor(colorInactive);
                layoutPlayer2.setBackgroundColor(colorActive);
            }
        }
    }

    private fun initialize() {
        txtPlayer1 = findViewById<TextView>(R.id.txt_details_o_name);
        txtPlayer2 = findViewById<TextView>(R.id.txt_details_x_name);
        editPlayer1 = findViewById(R.id.edtPlayer1);
        editPlayer2 = findViewById(R.id.edtPlayer2);
        buttonStart = findViewById(R.id.btnStart);
        buttonRestart = findViewById(R.id.btnRestart);
        buttonExit = findViewById(R.id.btnExit)
        layoutPlayer1 = findViewById(R.id.ll_Player1)
        layoutPlayer2 = findViewById(R.id.ll_Player2)
    }


    fun getSym(view: View) {
        val counter = view as ImageView
        val txt = findViewById<TextView>(R.id.winner1)
        val layout = findViewById<RelativeLayout>(R.id.winner)
        val start = findViewById<LinearLayout>(R.id.details)

        val tappedCounter = counter.tag.toString().toInt()

        var name1 = txtPlayer1.getText()
        var name2 = txtPlayer1.getText()

        if(gameIsActive)
        {
            start.visibility = View.INVISIBLE
        }


        if (gameState[tappedCounter] == 2 && gameIsActive) {
            backGroundPlayer();
            playerchance=1
            if (activePlayer == 1) {
                counter.setImageResource(R.drawable.o)
                activePlayer = 0
                count++
                gameState[tappedCounter] = 1
            } else {
                counter.setImageResource(R.drawable.x)
                activePlayer = 1
                count++
                gameState[tappedCounter] = 0
            }

            for (winningPosition in winningPositions) {

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2
                ) {
                    if (gameState[winningPosition[0]] == 0) txt.text =
                        "$name2 Wins!!" else if (gameState[winningPosition[0]] == 1
                    ) txt.text = "$name1 Wins!!"
                    layout.visibility = View.VISIBLE
                    gameIsActive = false
                    backGroundPlayer()
                }
            }
        }
        if (gameIsActive && count == 9) {
            txt.text = "DRAW!!"
            layout.visibility = View.VISIBLE
            gameIsActive = false
            backGroundPlayer()
        }
    }

    fun playAgain(view: View?) {
        activePlayer = 1
        gameIsActive = true
        count= 0
        val layout = findViewById<RelativeLayout>(R.id.winner)
        val start:LinearLayout = findViewById(R.id.details)
        val gridLayout =
            findViewById<GridLayout>(R.id.gridLayout)
        for (i in gameState.indices) {
            gameState[i] = 2
        }
        layout.visibility = View.INVISIBLE
        start.visibility = View.INVISIBLE
        for (i in 0 until gridLayout.childCount) {
            (gridLayout.getChildAt(i) as ImageView).setImageResource(0)
        }
        playerchance=1
    }

    fun restartGame(view: View?) {
        activePlayer = 1
        gameIsActive = true
        count= 0
        val gridLayout =
            findViewById<GridLayout>(R.id.gridLayout)
        for (i in gameState.indices) {
            gameState[i] = 2
        }
        for (i in 0 until gridLayout.childCount) {
            (gridLayout.getChildAt(i) as ImageView).setImageResource(0)
        }
        playerchance=1
    }

    override fun onBackPressed() {
        if (countBack == 1){
            super.onBackPressed()
            return
        }
        countBack++;
        Toast.makeText(this,"Press Again to Exit Game!", Toast.LENGTH_SHORT).show()
    }
}