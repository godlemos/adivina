package com.example.adivina;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int numeroAleatorio;
    final int rangoMinimo = 1;
    int rangoMaximo = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar rango = findViewById(R.id.barraRango);
        TextView valorRango = findViewById(R.id.txtRango);

        rango.setMax(9);
        rango.setProgress(5);
        valorRango.setText(String.valueOf(rango.getProgress() + rangoMinimo));

        numeroAleatorio = generarNumeroAleatorio(rango.getProgress() + rangoMinimo);

        rango.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rangoMaximo = progress + rangoMinimo;
                valorRango.setText(String.valueOf(rangoMaximo));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                numeroAleatorio = generarNumeroAleatorio(rangoMaximo);
                Toast.makeText(MainActivity.this, "Número aleatorio generado nuevamente", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(android.R.id.content).getViewTreeObserver().addOnPreDrawListener(() -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                getWindow().getInsetsController().hide(android.view.WindowInsets.Type.systemBars());
            }
            return true;
        });
    }

    private int generarNumeroAleatorio(int max) {
        Random aleatorio = new Random();
        return aleatorio.nextInt(max - rangoMinimo + 1) + rangoMinimo;
    }

    public void verificar(View v) {
        EditText vericable = findViewById(R.id.txtNumero);

        String textoIngresado = vericable.getText().toString();
        if (textoIngresado.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa un número", Toast.LENGTH_SHORT).show();
            return;
        }

        int numeroVerificable;
        try {
            numeroVerificable = Integer.parseInt(textoIngresado);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Entrada inválida. Ingresa un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (numeroVerificable < rangoMinimo || numeroVerificable > rangoMaximo) {
            Toast.makeText(this, "El número debe estar entre 1 y " + rangoMaximo, Toast.LENGTH_SHORT).show();
            return;
        }

        if (numeroVerificable > numeroAleatorio) {
            Toast.makeText(this, "No adivinaste el número, intenta otra vez", Toast.LENGTH_SHORT).show();
        } else if (numeroVerificable < numeroAleatorio) {
            Toast.makeText(this, "No adivinaste el número intenta otra vez", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "¡Felicidades! Descubriste el número oculto", Toast.LENGTH_SHORT).show();
        }
    }
}
