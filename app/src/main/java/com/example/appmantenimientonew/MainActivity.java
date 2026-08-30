package com.example.appmantenimientonew;

import android.os.Bundle;
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
    private Button btnGrabar, btnEditar, btnEliminar, btnNuevo;
    private TextView txtResultado;
    private ListView listProforma;

    private ArrayList<Proformaltem> lista = new ArrayList<>();
    private ArrayAdapter<Proformaltem> adaptador;
    private int posicionSeleccionada = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtcodigo = findViewById(R.id.txtcodigo);
        txtProducto = findViewById(R.id.txtProducto);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtResultado = findViewById(R.id.txtResultado);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        listProforma = findViewById(R.id.ListProforma);

        // Adaptador enlazado a tu diseño personalizado proforma_item.xml
        adaptador = new ArrayAdapter<>(this, R.layout.proforma_item, R.id.txtItemTexto, lista);
        listProforma.setAdapter(adaptador);

        // Botón Nuevo
        btnNuevo.setOnClickListener(v -> limpiarCampos());

        // Botón Grabar
        btnGrabar.setOnClickListener(v -> {
            String codigo = txtcodigo.getText().toString().trim();
            String producto = txtProducto.getText().toString().trim();
            String precioStr = txtPrecio.getText().toString().trim();
            String cantidadStr = txtCantidad.getText().toString().trim();

            if (codigo.isEmpty() || producto.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double precio = Double.parseDouble(precioStr);
                int cantidad = Integer.parseInt(cantidadStr);
                double total = precio * cantidad;

                txtResultado.setText("S/. " + String.format("%.2f", total));
                lista.add(new Proformaltem(codigo, producto, precio, cantidad));
                adaptador.notifyDataSetChanged();
                limpiarCampos();
                Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show();
            }
        });

        // Seleccionar elemento de la lista
        listProforma.setOnItemClickListener((parent, view, position, id) -> {
            posicionSeleccionada = position;
            Proformaltem item = lista.get(position);
            txtcodigo.setText(item.getCodigo());
            txtProducto.setText(item.getProducto());
            txtPrecio.setText(String.valueOf(item.getPrecio()));
            txtCantidad.setText(String.valueOf(item.getCantidad()));
            txtResultado.setText("S/. " + String.format("%.2f", item.getTotal()));
        });

        // Botón Editar (Actual)
        btnEditar.setOnClickListener(v -> {
            if (posicionSeleccionada >= 0 && posicionSeleccionada < lista.size()) {
                String precioStr = txtPrecio.getText().toString().trim();
                String cantidadStr = txtCantidad.getText().toString().trim();

                if (precioStr.isEmpty() || cantidadStr.isEmpty()) {
                    Toast.makeText(this, "Complete precio y cantidad para editar", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Proformaltem item = lista.get(posicionSeleccionada);
                    item.setCodigo(txtcodigo.getText().toString().trim());
                    item.setProducto(txtProducto.getText().toString().trim());
                    item.setPrecio(Double.parseDouble(precioStr));
                    item.setCantidad(Integer.parseInt(cantidadStr));

                    adaptador.notifyDataSetChanged();
                    txtResultado.setText("S/. " + String.format("%.2f", item.getTotal()));
                    Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Seleccione un elemento de la lista", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón Eliminar (Borrar)
        btnEliminar.setOnClickListener(v -> {
            if (posicionSeleccionada >= 0 && posicionSeleccionada < lista.size()) {
                lista.remove(posicionSeleccionada);
                adaptador.notifyDataSetChanged();
                Toast.makeText(this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Seleccione un elemento para eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiarCampos() {
        txtcodigo.setText("");
        txtProducto.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        txtResultado.setText("S/. 0.00");
        posicionSeleccionada = -1;
        txtcodigo.requestFocus();
    }
}