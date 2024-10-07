package br.com.jef.dollarmonitor.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.jef.dollarmonitor.R
import br.com.jef.dollarmonitor.state.ScreenState
import br.com.jef.dollarmonitor.viewmodel.CryptoViewModel
import br.com.jef.dollarmonitor.viewmodel.CryptoViewModelFactory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val viewModel: CryptoViewModel by viewModels {
        CryptoViewModelFactory()
    }

    val txtDollar: TextView by lazy { findViewById(R.id.txtDollar) }
    val txtDate: TextView by lazy { findViewById(R.id.txtDate) }
    val btnRefresh: Button by lazy { findViewById(R.id.btnRefresh) }
    val progressBar: ProgressBar by lazy { findViewById(R.id.progress) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.tickerLiveData.observe(this) { state ->
            when (state) {
                is ScreenState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    btnRefresh.visibility = View.GONE
                }

                is ScreenState.Success -> {
                    progressBar.visibility = View.GONE
                    btnRefresh.visibility = View.VISIBLE
                    txtDollar.text = NumberFormat.getCurrencyInstance(Locale("pt", "br")).let {
                        it.format(state.data.last.toBigDecimal())
                    }
                    txtDate.text = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date())
                }

                is ScreenState.Error -> {
                    progressBar.visibility = View.GONE
                    btnRefresh.visibility = View.VISIBLE
                    Toast.makeText(this, "Ocorreu Um Erro", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}