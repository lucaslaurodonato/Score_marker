package com.lucasdonato.marcadordetruco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lucasdonato.marcadordetruco.extensions.get
import kotlinx.android.synthetic.main.activity_players_name.*

class PlayerNameActivity : AppCompatActivity() {

    private var playerOne: String? = null
    private var playerTwo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players_name)
        setupListeners()
    }

    private fun setupListeners() {
        startGame.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        val playerOneVerify = edit_players_1.get().isEmpty()
        val playerTwoVerify = edit_players_2.get().isEmpty()

        when {
            playerOneVerify -> edit_players_1.error = getString(R.string.invalid_name_player_one)
            playerTwoVerify -> edit_players_2.error = getString(R.string.invalid_name_player_two)
            else -> savePlayerName()
        }
    }

    private fun savePlayerName() {
        playerOne = edit_players_1.get()
        playerTwo = edit_players_2.get()

        startMainActivity()
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getStartIntent(this, playerOne, playerTwo))
        finish()
    }

}