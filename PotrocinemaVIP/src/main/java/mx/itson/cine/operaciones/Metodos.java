/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.cine.operaciones;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * La clase Metodos proporciona una serie de métodos estáticos que facilitan
 * la gestión de operaciones en un cine, como la compra de boletos, la
 * inicialización de asientos y la generación de reportes.
 * 
 * @author ImLuisAlvarado
 */
public class Metodos {

    /**
     * Muestra un mensaje de bienvenida al usuario.
     * Este método utiliza un cuadro de diálogo para informar al usuario
     * sobre la experiencia que le espera en el cine.
     */
    public static void mensajeBienvenida() {
        JOptionPane.showMessageDialog(null, 
            "Hola, sea bienvenido.\n" +
            "Preparese para una experiencia inolvidable", 
            "Bienvenido al Potrocinema VIP", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Inicializa todos los asientos en un arreglo tridimensional como "Disponible".
     *
     * @param asientos Un arreglo tridimensional que representa los asientos
     *                 en diferentes películas y funciones.
     */
    public static void inicializarAsientos(String[][][] asientos) {
        for (int i = 0; i < asientos.length; i++) {
            for (int j = 0; j < asientos[i].length; j++) {
                for (int k = 0; k < asientos[i][j].length; k++) {
                    asientos[i][j][k] = "Disponible";
                }
            }
        }
    }

    /**
     * Muestra un formulario para la compra de un boleto.
     * Este método solicita el nombre del cliente, actualiza el estado del
     * asiento seleccionado y agrega la venta a una lista de ventas.
     *
     * @param fila              La fila del asiento seleccionado.
     * @param columna           La columna del asiento seleccionado.
     * @param botonesAsientos   Un arreglo de botones que representan los asientos.
     * @param pelicula          El nombre de la película.
     * @param indicePelicula    El índice de la película en el arreglo de películas.
     * @param asientos          El arreglo tridimensional que representa los asientos.
     * @param ventas            La lista de ventas realizadas.
     * @param precioBoleto      El precio del boleto.
     */
    public static void mostrarFormularioCompra(int fila, int columna, JButton[] botonesAsientos, String pelicula, int indicePelicula, String[][][] asientos, ArrayList<Venta> ventas, double precioBoleto) {
        String nombreCliente = JOptionPane.showInputDialog(null, "Ingrese el nombre del cliente:");
        if (nombreCliente == null) {
            nombreCliente = "Cliente sin registrar";
        }

        int asientoSeleccionado = fila * 4 + columna;
        asientos[indicePelicula][fila][columna] = "Ocupado";
        botonesAsientos[asientoSeleccionado].setBackground(Color.white);

        Venta venta = new Venta(nombreCliente, pelicula, asientoSeleccionado + 1, precioBoleto);
        ventas.add(venta);

        JOptionPane.showMessageDialog(null, "¡Boleto comprado con éxito!\n" +
                "Cliente: " + nombreCliente + "\nPelícula: " + pelicula + "\nAsiento: " + (asientoSeleccionado + 1) + "\nPrecio: " + precioBoleto);
    }

    /**
     * Muestra un reporte general de todas las ventas realizadas.
     * Este método genera un resumen que incluye el nombre del cliente,
     * la película, el número de asiento y el precio de cada venta.
     *
     * @param ventas La lista de ventas realizadas.
     */
    public static void mostrarReporteGeneral(ArrayList<Venta> ventas) {
        StringBuilder reporte = new StringBuilder();
        double gananciaTotal = 0;

        for (Venta venta : ventas) {
            reporte.append("Cliente: ").append(venta.nombreCliente)
                    .append(", Película: ").append(venta.pelicula)
                    .append(", Asiento: ").append(venta.asiento)
                    .append(", Precio: ").append(venta.precio)
                    .append("\n");
            gananciaTotal += venta.precio;
        }

        reporte.append("\nTotal de boletos vendidos: ").append(ventas.size())
                .append("\nGanancia total: ").append(gananciaTotal);
        
        JOptionPane.showMessageDialog(null, reporte.toString(), "Reporte General", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un reporte de ventas por función.
     * Este método genera un resumen que incluye la cantidad de boletos vendidos
     * y la ganancia total por cada película.
     *
 * @param ventas   La lista de ventas realizadas.
     * @param peliculas Un arreglo de nombres de películas.
     */
    public static void mostrarReportePorFuncion(ArrayList<Venta> ventas, String[] peliculas) {
        StringBuilder reporte = new StringBuilder();
        
        for (int i = 0; i < peliculas.length; i++) {
            int boletosVendidos = 0;
            double gananciaFuncion = 0;

            for (Venta venta : ventas) {
                if (venta.pelicula.equals(peliculas[i])) {
                    boletosVendidos++;
                    gananciaFuncion += venta.precio;
                }
            }

            reporte.append("Función: ").append(peliculas[i])
                    .append("\nBoletos vendidos: ").append(boletosVendidos)
                    .append("\nGanancia por función: ").append(gananciaFuncion)
                    .append("\n\n");
        }

        JOptionPane.showMessageDialog(null, reporte.toString(), "Reporte por Función", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Clase interna que representa una venta de boleto.
     * Contiene información sobre el cliente, la película, el asiento y el precio.
     */
    public static class Venta {
        String nombreCliente;
        String pelicula;
        int asiento;
        double precio;

        /**
         * Constructor de la clase Venta.
         *
         * @param nombreCliente El nombre del cliente que realiza la compra.
         * @param pelicula      El nombre de la película para la cual se compra el boleto.
         * @param asiento       El número del asiento comprado.
         * @param precio        El precio del boleto.
         */
        public Venta(String nombreCliente, String pelicula, int asiento, double precio) {
            this.nombreCliente = nombreCliente;
            this.pelicula = pelicula;
            this.asiento = asiento;
            this.precio = precio;
        }
    }
}
