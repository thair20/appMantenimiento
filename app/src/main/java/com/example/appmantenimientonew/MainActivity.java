package com.example.appmantenimientonew;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtcodigo, txtProducto, txtPrecio, txtCantidad;
    private Button btnGrabar, btnEditar, btnEliminar, btnNuevo;
    private ListView listProforma;
    ArrayList<ProformaItem> lista = new ArrayList<>();
    ArrayAdapter<ProformaItem> adaptador;
    int posicionSeleccionada = -1;
    TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtcodigo = findViewById(R.id.txtcodigo);
        txtProducto = findViewById(R.id.txtProducto);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtResultado = findViewById(R.id.txtResultado);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        listProforma = findViewById(R.id.listProforma);

        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listProforma.setAdapter(adaptador);

        // Nuevo
        btnNuevo.setOnClickListener(v -> {
            txtcodigo.setText("");
            txtProducto.setText("");
            txtPrecio.setText("");
            txtCantidad.setText("");
            txtResultado.setText("S/. 0.00");
            txtcodigo.requestFocus();
        });

        // Grabar
        btnGrabar.setOnClickListener(v -> {
            String cod = txtcodigo.getText().toString().trim();
            String prod = txtProducto.getText().toString().trim();
            String precioStr = txtPrecio.getText().toString().trim();
            String cantStr = txtCantidad.getText().toString().trim();

            if (cod.isEmpty() || prod.isEmpty() || precioStr.isEmpty() || cantStr.isEmpty()) {
                Toast.makeText(this, getString(R.string.msg_completar_campos), Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double precio = Double.parseDouble(precioStr);
                int cant = Integer.parseInt(cantStr);
                double total = precio * cant;
                txtResultado.setText(getString(R.string.formato_total, total));
                lista.add(new ProformaItem(cod, prod, precio, cant));
                adaptador.notifyDataSetChanged();
                limpiarCampos();
            } catch (NumberFormatException e) {
                Toast.makeText(this, getString(R.string.msg_precio_cantidad_invalidos), Toast.LENGTH_SHORT).show();
            }
        });

        // Seleccionar item
        listProforma.setOnItemClickListener((parent, view, position, id) -> {
            posicionSeleccionada = position;
            ProformaItem item = lista.get(position);
            txtcodigo.setText(item.getCodigo());
            txtProducto.setText(item.getProducto());
            txtPrecio.setText(String.valueOf(item.getPrecio()));
            txtCantidad.setText(String.valueOf(item.getCantidad()));
            txtResultado.setText(getString(R.string.formato_total, item.getTotal()));
        });

        // Editar
        btnEditar.setOnClickListener(v -> {
            if (posicionSeleccionada == -1) {
                Toast.makeText(this, getString(R.string.msg_seleccionar_item), Toast.LENGTH_SHORT).show();
                return;
            }

            String precioStr = txtPrecio.getText().toString().trim();
            String cantStr = txtCantidad.getText().toString().trim();

            if (precioStr.isEmpty() || cantStr.isEmpty()) {
                Toast.makeText(this, getString(R.string.msg_completar_precio_cantidad), Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                ProformaItem item = lista.get(posicionSeleccionada);
                item.setProducto(txtProducto.getText().toString().trim());
                item.setPrecio(Double.parseDouble(precioStr));
                item.setCantidad(Integer.parseInt(cantStr));
                adaptador.notifyDataSetChanged();
                limpiarCampos();
            } catch (NumberFormatException e) {
                Toast.makeText(this, getString(R.string.msg_precio_cantidad_invalidos), Toast.LENGTH_SHORT).show();
            }
        });

        // Eliminar
        btnEliminar.setOnClickListener(v -> {
            if (posicionSeleccionada == -1) {
                Toast.makeText(this, getString(R.string.msg_seleccionar_item), Toast.LENGTH_SHORT).show();
                return;
            }
            lista.remove(posicionSeleccionada);
            adaptador.notifyDataSetChanged();
            limpiarCampos();
        });
    }

    private void limpiarCampos() {
        txtcodigo.setText("");
        txtProducto.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        posicionSeleccionada = -1;
    }
}