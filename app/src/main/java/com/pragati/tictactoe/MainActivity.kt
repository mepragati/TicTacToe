package com.pragati.tictactoe

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    //Variable declared for Grid Layout Activity
    var activePlayer = 1
    var gameIsActive = true
    var count = 0
    var lapse =0
    var playerchance = 0
    var countBack = 0 //Variable declared for exiting not by once back
    //Variable declared for Changing Active and Inactive Player Background
    var colorInactive = Color.TRANSPARENT
    var colorActive = Color.BLACK
    //Variable declared for defining winning positions in grid
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
    //Variables declared for Views Button and Layout to make it functional
    lateinit var txtPlayer1:TextView
    lateinit var txtPlayer2:TextView
    lateinit var editPlayer1:EditText
    lateinit var editPlayer2:EditText
    lateinit var buttonStart:Button
    lateinit var buttonRestart:Button
    lateinit var buttonExit:Button
    lateinit var layoutPlayer1:RelativeLayout
    lateinit var layoutPlayer2:RelativeLayout
    lateinit var gridLayout: GridLayout
    lateinit var start : LinearLayout
    lateinit var layout : RelativeLayout

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        title=" Tic Tac Toe"

        initialize() //Calling initialize function

        //Making the details dialog visible as the activity starts
        start.visibility =View.VISIBLE

        //Making Start Button Functional
        buttonStart.setOnClickListener(View.OnClickListener {
            //Giving Default Value to Player's Name Details
            var name1: String = "Player O"
            var name2: String = "Player X"
            //Parsing EditText input value to top Bezel if value is given otherwise parsing the default value
            if(editPlayer1.text.toString().trim() != "")
                name1 = editPlayer1.text.toString()
            if(editPlayer2.text.toString().trim() != "")
                name2 = editPlayer2.text.toString()
            txtPlayer1.text = name1
            txtPlayer2.text = name2

            //Setting the EditText Box Blank Again
            editPlayer1.setText("")
            editPlayer2.setText("")
            //Making the details dialog invisible when start button is clicked
            start.visibility = View.INVISIBLE
            lapse=0
            //To hide the keyboard once input is taken and start button is pressed
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        })

        //Making Restart button functional
        buttonRestart.setOnClickListener(View.OnClickListener {
            layout.visibility = View.INVISIBLE //Making the winner dialog invisible
            start.visibility =View.VISIBLE //Making the details dialog visible when restart button is pressed
            backGroundPlayer() //Calling function
            restartGame(it) //Calling function
        })

        //Making Exit Button functional
        buttonExit.setOnClickListener ( View.OnClickListener {
            //Activity is finished showing the toast when exit button is clicked
                finish()
            Toast.makeText(this,"You Pressed Exit Game!", Toast.LENGTH_SHORT).show()
        })
        backGroundPlayer() //Calling function
    }

    //Declaring function to toggle background color as the game proceeds
    private fun backGroundPlayer() {
        //Giving the default color when game is not active
        if(!gameIsActive) {
            layoutPlayer1.setBackgroundColor(colorInactive)
            layoutPlayer2.setBackgroundColor(colorInactive)
        }

        else if (gameIsActive && lapse == 0)
        {
            layoutPlayer1.setBackgroundColor(colorActive)
            layoutPlayer2.setBackgroundColor(colorInactive)
        }
        //Switching the background color according to the active player
        else{
            if(activePlayer == 1){
                layoutPlayer1.setBackgroundColor(colorInactive)
                layoutPlayer2.setBackgroundColor(colorActive)
            }
            else if(activePlayer == 0) {
                layoutPlayer1.setBackgroundColor(colorActive)
                layoutPlayer2.setBackgroundColor(colorInactive)
            }
        }
    }

    //Declaring function to initialize all the declared variable
    private fun initialize() {
        txtPlayer1 = findViewById<TextView>(R.id.txt_details_o_name)
        txtPlayer2 = findViewById<TextView>(R.id.txt_details_x_name)
        editPlayer1 = findViewById(R.id.edtPlayer1)
        editPlayer2 = findViewById(R.id.edtPlayer2)
        buttonStart = findViewById(R.id.btnStart)
        buttonRestart = findViewById(R.id.btnRestart)
        buttonExit = findViewById(R.id.btnExit)
        layoutPlayer1 = findViewById(R.id.ll_Player1)
        layoutPlayer2 = findViewById(R.id.ll_Player2)
        gridLayout = findViewById(R.id.gridLayout)
        start = findViewById<LinearLayout>(R.id.details)
        layout = findViewById<RelativeLayout>(R.id.winner)
        gridLayout = findViewById<GridLayout>(R.id.gridLayout)
    }

    //Declaring function to get the symbol for player according to the active player and declaring winner accordingly
    fun getSym(view: View) {
        //variables declared
        val counter = view as ImageView
        val txt = findViewById<TextView>(R.id.winner1)
        val tappedCounter = counter.tag.toString().toInt()

        var name1 = txtPlayer1.text
        var name2 = txtPlayer1.text

        //As the game becomes active making the details dialog invisible
        if(gameIsActive)
        {
            start.visibility = View.INVISIBLE
        }
        lapse++
        //Proceeding the game while toggling the symbol according to the active player
        if (gameState[tappedCounter] == 2 && gameIsActive) {
            backGroundPlayer() //Calling function
            playerchance=1
            //Setting the symbol to O when active player is 1
            if (activePlayer == 1) {
                counter.setImageResource(R.drawable.o)
                activePlayer = 0 //changing active player to 0
                count++ //proceeding the game
                gameState[tappedCounter] = 1
            } else {
                //Setting the symbol to X when active player is 0 or otherwise
                counter.setImageResource(R.drawable.x)
                activePlayer = 1 //changing active player to 1
                count++  //proceeding the game
                gameState[tappedCounter] = 0
            }
            //Checking whether game has reached winning postion till now or not
            for (winningPosition in winningPositions) {
                //Checking the winning position and declaring the winnwer accordingly
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2
                ) {
                    //declaring the winner according to the winning position
                    if (gameState[winningPosition[0]] == 0) txt.text =
                        "$name2 Wins!!" else if (gameState[winningPosition[0]] == 1
                    ) txt.text = "$name1 Wins!!"
                    layout.visibility = View.VISIBLE //making winning dialog box appear
                    gameIsActive = false //making game inactive
                    backGroundPlayer() //Calling function
                }
            }
        }
        //When all grids are filled and game is still active
        if (gameIsActive && count == 9) {
            txt.text = "DRAW!!" //declaring the game as draw
            layout.visibility = View.VISIBLE //making winning dialog box appear
            gameIsActive = false //making the game inactive
            backGroundPlayer() //Calling function
        }
    }

    //Function declared to play again while keeping the name on top bezel same
    fun playAgain(view: View?) {
        activePlayer = 1
        gameIsActive = true
        count= 0
        lapse=0
        //setting the board to new again
        for (i in gameState.indices) {
            gameState[i] = 2
        }
        //making both the dialog box invisible as the play again function is called
        layout.visibility = View.INVISIBLE
        start.visibility = View.INVISIBLE
        for (i in 0 until gridLayout.childCount) {
            (gridLayout.getChildAt(i) as ImageView).setImageResource(0)
        }
        playerchance=1
        backGroundPlayer()
    }

    //Function declared to restart game while taking the player's name details again
    private fun restartGame(view: View?) {
        activePlayer = 1
        gameIsActive = true
        count= 0
        lapse=0
        //setting the board to new again
        for (i in gameState.indices) {
            gameState[i] = 2
        }
        for (i in 0 until gridLayout.childCount) {
            (gridLayout.getChildAt(i) as ImageView).setImageResource(0)
        }
        playerchance=1
        backGroundPlayer()//Calling Function
    }

    //Showing the toast and exiting the game on double back press
    override fun onBackPressed() {
        if (countBack == 1){
            super.onBackPressed()
            return
        }
        countBack++
        Toast.makeText(this,"Press Again to Exit Game!", Toast.LENGTH_SHORT).show()
    }
}