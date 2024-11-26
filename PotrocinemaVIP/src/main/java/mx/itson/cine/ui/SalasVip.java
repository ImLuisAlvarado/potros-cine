package mx.itson.cine.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import mx.itson.cine.operaciones.Metodos;

/**
 * La clase SalasVip proporciona la interfaz gráfica de usuario para el sistema
 * de compra de boletos en un cine VIP. Permite a los usuarios seleccionar
 * asientos disponibles en diferentes películas y generar reportes de ventas.
 * 
 * @author ImLuisAlvarado
 */
public class SalasVip {
    static final int NUM_ASIENTOS = 16; // Número total de asientos por sala.
    static final String[][][] asientos = new String[3][4][4]; // Arreglo tridimensional que representa los asientos.
    static final String[] peliculas = {"Venom el último baile", "Sonríe 2", "Gladiador (ReEstreno)"}; // Lista de películas.
    static final double PRECIO_BOLETO = 10.0; // Precio del boleto.
    static ArrayList<Metodos.Venta> ventas = new ArrayList<>(); // Lista de ventas realizadas.

    /**
     * Método principal que inicia la aplicación.
     * Muestra un mensaje de bienvenida, inicializa los asientos, y crea la interfaz gráfica.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        Metodos.mensajeBienvenida();
        Metodos.inicializarAsientos(asientos);

        JFrame frame = new JFrame("Potrocinema VIP");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Venom el último baile", crearPanelSala(0));
        tabbedPane.addTab("Sonríe 2", crearPanelSala(1));
        tabbedPane.addTab("Gladiador (ReEstreno)", crearPanelSala(2));

        frame.add(tabbedPane);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Reportes");
        JMenuItem menuItemReporteGeneral = new JMenuItem("Reporte General");
        JMenuItem menuItemReporteFuncion = new JMenuItem("Reporte por Función");
        menu.add(menuItemReporteGeneral);
        menu.add(menuItemReporteFuncion);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        menuItemReporteGeneral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Metodos.mostrarReporteGeneral(ventas);
            }
        });

        menuItemReporteFuncion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Metodos.mostrarReportePorFuncion(ventas, peliculas);
            }
        });

        frame.setVisible(true);
    }

    /**
     * Crea un panel que representa la sala de cine para una película específica.
     * El panel incluye botones para cada asiento y maneja la lógica de selección de asientos.
     *
     * @param indicePelicula El índice de la película en el arreglo de películas.
     * @return Un JPanel que representa la sala de cine.
     */
    public static JPanel crearPanelSala(int indicePelicula) {
        JPanel panelSala = new JPanel();
        panelSala.setLayout(new BorderLayout());

        JPanel panelAsientos = new JPanel();
        panelAsientos.setLayout(new GridLayout(4, 4)); // 4 filas y 4 columnas para los asientos.
        JButton[] botonesAsientos = new JButton[NUM_ASIENTOS];
        
        for (int i = 0; i < NUM_ASIENTOS; i++) {
            int fila = i / 4;
            int columna = i % 4;
            botonesAsientos[i] = new JButton("Asiento " + (i + 1));
            botonesAsientos[i].setBackground(Color.lightGray);
            botonesAsientos[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int asientoSeleccionado = Integer.parseInt(((JButton)e.getSource()).getText().split(" ")[1]) - 1;
                    int fila = asientoSeleccionado / 4;
                    int columna = asientoSeleccionado % 4;
                    if (asientos[indicePelicula][fila][columna].equals("Disponible")) {
                        Metodos.mostrarFormularioCompra(fila, columna, botonesAsientos, peliculas[indicePelicula], indicePelicula, asientos, ventas, PRECIO_BOLETO);
                    } else {
                        JOptionPane.showMessageDialog(panelSala, "Este asiento ya está ocupado.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            panelAsientos.add(botonesAsientos[i]);
 }
        
        panelSala.add(panelAsientos, BorderLayout.CENTER);
        return panelSala;
    }
}