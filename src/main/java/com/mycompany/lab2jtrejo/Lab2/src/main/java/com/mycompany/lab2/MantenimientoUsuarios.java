/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab2;

import javax.swing.*;

public class MantenimientoUsuarios extends JFrame {

    public MantenimientoUsuarios(){

        setTitle("Mantenimiento de Usuarios");
        setSize(400,300);
        setLocationRelativeTo(null);

        JLabel lbl = new JLabel("Pantalla de Usuarios");
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        add(lbl);
    }
}
