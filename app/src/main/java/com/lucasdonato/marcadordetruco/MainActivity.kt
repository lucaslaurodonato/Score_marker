package com.lucasdonato.marcadordetruco

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.lucasdonato.marcadordetruco.const.EXTRA_PLAYER_1
import com.lucasdonato.marcadordetruco.const.EXTRA_PLAYER_2
import com.lucasdonato.marcadordetruco.databinding.ActivityMainBinding
import com.lucasdonato.marcadordetruco.extensions.toast
import com.lucasdonato.marcadordetruco.model.PlayersDao

class MainActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context, playerOne: String? = null, playerTwo: String? = null) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_PLAYER_1, playerOne)
                putExtra(EXTRA_PLAYER_2, playerTwo)
            }
    }

    private lateinit var binding: ActivityMainBinding
    private var playerOneName: String? = null
    private var playerTwoName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        this.binding.activity = this
        receiveNamePlayers()
        setupNamePlayer()
        setupListeners()
    }

    private fun setupListeners() {
        binding.restartGameActionButton.setOnClickListener { dialogRestartGame() }
    }

    private fun receiveNamePlayers() {
        playerOneName = intent?.getStringExtra(EXTRA_PLAYER_1)
        playerTwoName = intent?.getStringExtra(EXTRA_PLAYER_2)
    }

    private fun setupNamePlayer() {
        playerOneName?.let { this.binding.dupla1 = PlayersDao(it) }
        playerTwoName?.let { this.binding.dupla2 = PlayersDao(it) }
    }

    private fun restart() {
        binding.dupla1?.score = 0
        binding.dupla2?.score = 0
    }

    fun sumScore(view: View, dupla: PlayersDao) {
        val button: Button = view as Button
        var sumScore = 0
        when (button.text) {
            getString(R.string.one) -> sumScore = 1
            getString(R.string.truco) -> sumScore = 3
            getString(R.string.six) -> sumScore = 6
            getString(R.string.nine) -> sumScore = 9
            getString(R.string.twelve) -> sumScore = 12
        }
        dupla.score += sumScore
        if (dupla.score >= 12) {
            dialogEndMatch(dupla)
        }
    }

    private fun dialogRestartGame() {
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(getString(R.string.yes)) { _, _ -> restart() }
                setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
            }
                .setMessage(getString(R.string.play_again))
            builder.create()
        }
        alertDialog?.show()
    }

    private fun dialogEndMatch(player: PlayersDao) {
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(getString(R.string.now)) { _, _ -> restart() }
                setNegativeButton(getString(R.string.run_bitch)) { _, _ -> messageFinish() }
            }
            builder.setTitle("${player.name} VENCEU!")
                .setMessage(getString(R.string.play_again))
            builder.create()
        }
        alertDialog?.show()
    }

    private fun messageFinish() {
        toast(getString(R.string.toast_info_new_match))
    }

}