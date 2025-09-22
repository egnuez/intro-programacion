package com.emiliano.crudexample.user.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.emiliano.crudexample.user.domain.User;
import com.emiliano.crudexample.user.presentation.IUserView;
import com.emiliano.crudexample.user.presentation.UserPresenter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SwingUserView extends JFrame implements IUserView {
 
    private UserPresenter presentador;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtEmail;
    private JButton btnCrear, btnBorrar;
    private JLabel lblIdSeleccionado;
    private JButton btnCancelar;

    public SwingUserView() {
        
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Email"}, 0);
        tablaUsuarios = new JTable(modeloTabla);
        this.add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2));

        lblIdSeleccionado = new JLabel("ID: ");
        panelFormulario.add(lblIdSeleccionado);
        panelFormulario.add(new JLabel(""));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        btnCrear = new JButton("Crear Usuario");
        panelFormulario.add(btnCrear);

        btnBorrar = new JButton("Borrar Usuario Seleccionado");
        panelFormulario.add(btnBorrar);

        btnCancelar = new JButton("Cancelar");
        panelFormulario.add(btnCancelar);

        this.add(panelFormulario, BorderLayout.SOUTH);

        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (presentador != null) {
                    String id = lblIdSeleccionado.getText().replace("ID: ", "");
                    String nombre = txtNombre.getText();
                    String email = txtEmail.getText();
                    
                    if (id.isEmpty()) {
                        presentador.agregarUsuario(nombre, email);
                    } else {
                        presentador.actualizarUsuario(Integer.parseInt(id), nombre, email);
                    }
                    limpiarCampos();
                    presentador.mostrarUsuarios();
                }
            }
        });

        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] filasSeleccionadas = tablaUsuarios.getSelectedRows();
                if (filasSeleccionadas.length > 0 && presentador != null) {
                    for (int fila : filasSeleccionadas) {
                        int id = (int) modeloTabla.getValueAt(fila, 0);
                        presentador.borrarUsuario(id);
                    }
                    presentador.mostrarUsuarios();
                } else {
                    mostrarMensaje("Seleccione uno o mas usuarios para borrar.");
                }
            }
        });

        tablaUsuarios.getSelectionModel().addListSelectionListener(event -> {
            int filaSeleccionada = tablaUsuarios.getSelectedRow();
            if (filaSeleccionada != -1) {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
                String email = (String) modeloTabla.getValueAt(filaSeleccionada, 2);

                lblIdSeleccionado.setText("ID: " + id);
                txtNombre.setText(nombre);
                txtEmail.setText(email);
                btnCrear.setText("Actualizar Usuario");
            }
        });
    
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
                btnCrear.setText("Crear Usuario");
                lblIdSeleccionado.setText("ID: ");
                tablaUsuarios.clearSelection();
            }
        });
    }
    
    @Override
    public void mostrarListaUsuarios(List<User> usuarios) {
        modeloTabla.setRowCount(0);
        for (User usuario : usuarios) {
            modeloTabla.addRow(new Object[]{usuario.getId(), usuario.getNombre(), usuario.getEmail()});
        }
    }

    @Override
    public void mostrarUsuario(User usuario) {
        modeloTabla.addRow(new Object[]{usuario.getId(), usuario.getNombre(), usuario.getEmail()});
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setPresenter(UserPresenter presenter) {
        this.presentador = presenter;
        this.presentador.mostrarUsuarios();
    }

    @Override
    public void mostrarError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtEmail.setText("");
        lblIdSeleccionado.setText("ID: ");
    }

}