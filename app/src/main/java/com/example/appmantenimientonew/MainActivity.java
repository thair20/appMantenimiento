package com.example.appmantenimientonew;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtcodigo, txtProducto, txtPrecio, txtCantidad;
    private TextView txtResultado;
    private Button btnGrabar, btnEditar, btnEliminar, btnNuevo;
    private ListView ListProforma;

    private ArrayList<ProformaItem> lista = new ArrayList<>();
    private ArrayAdapter<ProformaItem> adaptador;
    private int posicionSeleccionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajuste de márgenes para EdgeToEdge (pantalla completa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // QUITAR DE COMENTARIOS PARA LA VINCUILACION XML CON OBJETO DE JAVA
        // txtcodigo = findViewById(R.id.txtcodigo);
        //txtProducto = findViewById(R.id.txtProducto);
        //txtPrecio = findViewById(R.id.txtPrecio);
        //txtCantidad = findViewById(R.id.txtCantidad);
        //txtResultado = findViewById(R.id.txtResultado);

        //btnNuevo = findViewById(R.id.btnNuevo);
        //btnGrabar = findViewById(R.id.btnGrabar);
        //btnEditar = findViewById(R.id.btnEditar);
        //btnEliminar = findViewById(R.id.btnEliminar);

        //ListProforma = findViewById(R.id.ListProforma);

        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        ListProforma.setAdapter(adaptador);

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampos();
            }
        });

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()) {
                    String dni = txtcodigo.getText().toString();
                    String prod = txtProducto.getText().toString();
                    double precio = Double.parseDouble(txtPrecio.getText().toString());
                    int cant = Integer.parseInt(txtCantidad.getText().toString());

                    lista.add(new ProformaItem(dni, prod, precio, cant));
                    adaptador.notifyDataSetChanged();

                    actualizarTotalGeneral();
                    limpiarCampos();
                }
            }
        });

        ListProforma.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posicionSeleccionada = position;
                ProformaItem item = lista.get(position);

                txtcodigo.setText(item.getCodigo());
                txtProducto.setText(item.getProducto());
                txtPrecio.setText(String.valueOf(item.getPrecio()));
                txtCantidad.setText(String.valueOf(item.getCantidad()));
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posicionSeleccionada != -1) {
                    if (validarCampos()) {
                        ProformaItem item = lista.get(posicionSeleccionada);
                        item.setCodigo(txtcodigo.getText().toString());
                        item.setProducto(txtProducto.getText().toString());
                        item.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
                        item.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));

                        adaptador.notifyDataSetChanged();
                        actualizarTotalGeneral();
                        limpiarCampos();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Seleccione un ítem de la lista", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (posicionSeleccionada != -1) {
                    lista.remove(posicionSeleccionada);
                    adaptador.notifyDataSetChanged();
                    actualizarTotalGeneral();
                    limpiarCampos();
                } else {
                    Toast.makeText(MainActivity.this, "Seleccione un ítem a eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void limpiarCampos() {
        txtcodigo.setText("");
        txtProducto.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        txtcodigo.requestFocus();
        posicionSeleccionada = -1;
    }
    private void actualizarTotalGeneral() {
        double totalGeneral = 0;
        for (ProformaItem item : lista) {
            totalGeneral += item.getTotal();
        }
        txtResultado.setText("Total: S/. " + String.format("%.2f", totalGeneral));
    }
    private boolean validarCampos() {
        if (txtcodigo.getText().toString().trim().isEmpty() ||
                txtProducto.getText().toString().trim().isEmpty() ||
                txtPrecio.getText().toString().trim().isEmpty() ||
                txtCantidad.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}