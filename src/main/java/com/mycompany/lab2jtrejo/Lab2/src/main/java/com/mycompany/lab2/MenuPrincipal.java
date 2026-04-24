/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab2;

import javax.swing.*;

public class MenuPrincipal extends JFrame {

    JMenuBar barra;
    JMenu menuGestion, menuSesion;
    JMenuItem itemUsuarios, itemClave, itemCerrar;

    public MenuPrincipal(){

        setTitle("Menú Principal");
        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        barra = new JMenuBar();

        menuGestion = new JMenu("Gestión");
        menuSesion = new JMenu("Sesión");

        itemUsuarios = new JMenuItem("Mantenimiento de Usuarios");
        itemClave = new JMenuItem("Reinicio de Clave");
        itemCerrar = new JMenuItem("Cerrar Sesión");

        menuGestion.add(itemUsuarios);
        menuGestion.add(itemClave);

        menuSesion.add(itemCerrar);

        barra.add(menuGestion);
        barra.add(menuSesion);

        setJMenuBar(barra);

        itemUsuarios.addActionListener(e -> {
            new MantenimientoUsuarios().setVisible(true);
        });

        itemClave.addActionListener(e -> {
            new ReinicioClave().setVisible(true);
        });

        itemCerrar.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });
    }
}
