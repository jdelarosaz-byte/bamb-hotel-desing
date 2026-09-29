package bambuhotelapp;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Sistema de Reservaciones y Disponibilidad en Tiempo Real
 * Hoteles BAMBÚ - Guatemala
 */
public class ReservasBambú extends JFrame {

    // Constantes de color corporativo BAMBÚ
    private static final Color COLOR_PRIMARIO = new Color(39, 64, 41);
    // Verde follaje
     // Verde bosque profundo
    private static final Color COLOR_FONDO = new Color(247, 246, 242);       // Crema natural
    private static final Color COLOR_PANEL = Color.WHITE;
    private static final Color COLOR_BORDE = new Color(220, 215, 203);
    private static final Color COLOR_TEXTO = new Color(30, 47, 32);
    private static final Color COLOR_ALERTA = new Color(153, 27, 27);
    private static final Color COLOR_EXITO = new Color(22, 101, 52);

    // Tipografías
    private static final Font FONT_TITULO = new Font("Georgia", Font.BOLD, 22);
    private static final Font FONT_SUBTITULO = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font FONT_SECCION = new Font("Georgia", Font.BOLD, 14);
    private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 12);
    private static final Font FONT_INPUT = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 12);

    // Formateadores
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private final NumberFormat monedaFormat = NumberFormat.getCurrencyInstance(new Locale("es", "GT"));

    // --- Componentes del Formulario ---
    private JRadioButton rbtnTodas;
    private JRadioButton rbtnDentroCapital;
    private JRadioButton rbtnFueraCapital;
    private ButtonGroup grupoUbicacion;
    private JComboBox<HotelItem> cbxHoteles;

    private JSpinner spFechaEntrada;
    private JSpinner spFechaSalida;
    private JSpinner spAdultos;
    private JSpinner spNinos;
    private JSpinner spHabitaciones;

    private JComboBox<HabitacionItem> cbxHabitaciones;

    private JLabel lblEstadoDisponibilidad;
    private JLabel lblDetalleNoches;
    private JLabel lblSubtotal;
    private JLabel lblImpuestos;
    private JLabel lblTotalPagar;

    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JComboBox<String> cbxTipoDocumento;
    private JTextField txtNumDocumento;
    private JComboBox<String> cbxHoraLlegada;
    private JTextField txtPeticiones;
    private JRadioButton rbtnPagoLlegada;
    private JRadioButton rbtnGarantiaTarjeta;
    private ButtonGroup grupoPago;

    private JButton btnVerificar;
    private JButton btnConfirmarReserva;
    private JButton btnLimpiar;

    private final java.util.List<HotelItem> catalogoHoteles = new ArrayList<>();

    public ReservasBambú() {
        super("BAMBÚ Hoteles Guatemala - Sistema de Reservación y Disponibilidad");
        inicializarCatalogo();
        configurarVentana();
        construirInterfaz();
        actualizarListaHoteles();
        verificarDisponibilidadTiempoReal();
    }

    private void inicializarCatalogo() {
        // --- DENTRO DE LA CIUDAD DE GUATEMALA ---
        HotelItem h1 = new HotelItem("BAMBÚ Grand Urbana", "Zona 10 - Zona Viva, Cd. Guatemala", true);
        h1.agregarHabitacion(new HabitacionItem("Deluxe Bambú (King)", 850.0, 8, "Cama King, balcón francés, Wi-Fi 6"));
        h1.agregarHabitacion(new HabitacionItem("Executive Garden Suite", 1350.0, 4, "Terraza privada, tina profunda, sala lounge"));
        h1.agregarHabitacion(new HabitacionItem("BAMBÚ Sky Penthouse", 2800.0, 2, "2 Camas King, vista panorámica, jacuzzi"));
        catalogoHoteles.add(h1);

        HotelItem h2 = new HotelItem("BAMBÚ Boutique Las Américas", "Zona 14, Cd. Guatemala", true);
        h2.agregarHabitacion(new HabitacionItem("Suite Zen con Balcón", 920.0, 6, "1 Cama King, desayuno gourmet, vista arbolada"));
        h2.agregarHabitacion(new HabitacionItem("Master Residence Bambú", 1650.0, 3, "1 King + 2 Twin, kitchenette, sala"));
        catalogoHoteles.add(h2);

        HotelItem h3 = new HotelItem("BAMBÚ Cayalá Nature Lodge", "Zona 16 - Paseo Cayalá, Cd. Guatemala", true);
        h3.agregarHabitacion(new HabitacionItem("Forest View Studio", 980.0, 7, "Ventanales al bosque, cama King"));
        h3.agregarHabitacion(new HabitacionItem("Family Canopy Villa", 1950.0, 3, "1 King + 2 Queen, dos niveles, chimenea"));
        catalogoHoteles.add(h3);

        // --- FUERA DE LA CIUDAD DE GUATEMALA ---
        HotelItem h4 = new HotelItem("BAMBÚ Sanctuary Lake Atitlán", "Santa Catarina Palopó, Sololá", false);
        h4.agregarHabitacion(new HabitacionItem("Suite Volcanes & Lago", 1450.0, 5, "Vista directa a 3 volcanes, chimenea, terraza"));
        h4.agregarHabitacion(new HabitacionItem("Master Cliff Villa con Jacuzzi", 2400.0, 2, "Jacuzzi de piedra volcánica, muelle privado"));
        h4.agregarHabitacion(new HabitacionItem("Cabaña Nido de Bambú", 1100.0, 4, "Arquitectura 100% bambú, ducha al aire libre"));
        catalogoHoteles.add(h4);

        HotelItem h5 = new HotelItem("BAMBÚ Colonial & Spa Antigua", "Antigua Guatemala, Sacatepéquez", false);
        h5.agregarHabitacion(new HabitacionItem("Habitación Colonial Virreinal", 1150.0, 6, "Techos altos, chimenea funcional, tina de cobre"));
        h5.agregarHabitacion(new HabitacionItem("Master Suite Claustro Real", 1850.0, 3, "Patio privado, cama con dosel, sala de té"));
        catalogoHoteles.add(h5);

        HotelItem h6 = new HotelItem("BAMBÚ Eco-Resort Río Dulce", "Río Dulce / Livingston, Izabal", false);
        h6.agregarHabitacion(new HabitacionItem("Overwater Bamboo Bungalow", 1280.0, 6, "Sobre el agua, muelle y hamaca suspendida"));
        h6.agregarHabitacion(new HabitacionItem("Villa Selva & Manglar", 1750.0, 4, "2 Camas Queen, terraza amplia con mosquitero"));
        catalogoHoteles.add(h6);

        HotelItem h7 = new HotelItem("BAMBÚ Rainforest Tikal", "Parque Nacional Tikal, Petén", false);
        h7.agregarHabitacion(new HabitacionItem("Canopy Maya Suite", 1390.0, 5, "Elevada en copa de árboles, balcón de aves"));
        h7.agregarHabitacion(new HabitacionItem("Master Safari Villa Petenera", 2150.0, 2, "Plunge pool privada, fogata, guía privado"));
        catalogoHoteles.add(h7);

        HotelItem h8 = new HotelItem("BAMBÚ Semuc Oasis", "Lanquín, Alta Verapaz", false);
        h8.agregarHabitacion(new HabitacionItem("Cabaña Ribereña Bambú", 890.0, 6, "Mirador al río Cahabón, hamaca y desayuno"));
        catalogoHoteles.add(h8);
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(980, 840);
        setMinimumSize(new Dimension(860, 720));
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
    }

    private void construirInterfaz() {
        setLayout(new BorderLayout(0, 0));

        add(crearPanelEncabezado(), BorderLayout.NORTH);

        JPanel panelCuerpo = new JPanel(new GridBagLayout());
        panelCuerpo.setBackground(COLOR_FONDO);
        panelCuerpo.setBorder(new EmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 8, 8, 8);

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.add(crearPanelUbicacionYHotel());
        panelIzquierdo.add(Box.createVerticalStrut(12));
        panelIzquierdo.add(crearPanelFechasYOcupacion());
        panelIzquierdo.add(Box.createVerticalStrut(12));
        panelIzquierdo.add(crearPanelDatosHuesped());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.58;
        gbc.weighty = 1.0;
        panelCuerpo.add(panelIzquierdo, gbc);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setOpaque(false);
        panelDerecho.add(crearPanelDisponibilidadLiquidacion());

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.42;
        gbc.weighty = 1.0;
        panelCuerpo.add(panelDerecho, gbc);

        JScrollPane scrollPane = new JScrollPane(panelCuerpo);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        add(crearPanelBotonesAccion(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(COLOR_PRIMARIO);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        JPanel textoPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        textoPanel.setOpaque(false);

        JLabel lblTitulo = new JLabel("HOTEL Y RESORT BAMBÚ");
        lblTitulo.setFont(FONT_TITULO);
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Sistema Oficial de Reservas en Línea • Validación en Tiempo Real (Guatemala)");
        lblSub.setFont(FONT_SUBTITULO);
        lblSub.setForeground(new Color(196, 222, 198));

        textoPanel.add(lblTitulo);
        textoPanel.add(lblSub);

        header.add(textoPanel, BorderLayout.WEST);

        JLabel lblInsignia = new JLabel("PBX: 2339-BAMBU");
        lblInsignia.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblInsignia.setForeground(Color.WHITE);
        header.add(lblInsignia, BorderLayout.EAST);

        return header;
    }

    private JPanel crearPanelUbicacionYHotel() {
        JPanel panel = crearContenedorTarjeta("1. Ubicación y Hotel BAMBÚ");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 6, 4, 6);

        JLabel lblFiltro = new JLabel("Destino:");
        lblFiltro.setFont(FONT_LABEL);
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        panel.add(lblFiltro, g);

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelFiltros.setOpaque(false);
        rbtnTodas = new JRadioButton("Todos", true);
        rbtnDentroCapital = new JRadioButton("Dentro de Cd. Guatemala");
        rbtnFueraCapital = new JRadioButton("Fuera de la Capital");

        rbtnTodas.setOpaque(false);
        rbtnDentroCapital.setOpaque(false);
        rbtnFueraCapital.setOpaque(false);

        grupoUbicacion = new ButtonGroup();
        grupoUbicacion.add(rbtnTodas);
        grupoUbicacion.add(rbtnDentroCapital);
        grupoUbicacion.add(rbtnFueraCapital);

        panelFiltros.add(rbtnTodas);
        panelFiltros.add(rbtnDentroCapital);
        panelFiltros.add(rbtnFueraCapital);

        g.gridx = 1;
        g.gridy = 0;
        g.gridwidth = 2;
        panel.add(panelFiltros, g);

        JLabel lblHotel = new JLabel("Hotel:");
        lblHotel.setFont(FONT_LABEL);
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 1;
        panel.add(lblHotel, g);

        cbxHoteles = new JComboBox<>();
        cbxHoteles.setFont(FONT_INPUT);
        g.gridx = 1;
        g.gridy = 1;
        g.gridwidth = 2;
        panel.add(cbxHoteles, g);

        JLabel lblHabitacion = new JLabel("Habitación:");
        lblHabitacion.setFont(FONT_LABEL);
        g.gridx = 0;
        g.gridy = 2;
        g.gridwidth = 1;
        panel.add(lblHabitacion, g);

        cbxHabitaciones = new JComboBox<>();
        cbxHabitaciones.setFont(FONT_INPUT);
        g.gridx = 1;
        g.gridy = 2;
        g.gridwidth = 2;
        panel.add(cbxHabitaciones, g);

        ActionListener listenerFiltro = e -> {
            actualizarListaHoteles();
            verificarDisponibilidadTiempoReal();
        };
        rbtnTodas.addActionListener(listenerFiltro);
        rbtnDentroCapital.addActionListener(listenerFiltro);
        rbtnFueraCapital.addActionListener(listenerFiltro);

        cbxHoteles.addActionListener(e -> {
            actualizarListaHabitaciones();
            verificarDisponibilidadTiempoReal();
        });

        cbxHabitaciones.addActionListener(e -> verificarDisponibilidadTiempoReal());

        return panel;
    }

    private JPanel crearPanelFechasYOcupacion() {
        JPanel panel = crearContenedorTarjeta("2. Fechas de Estancia y Huéspedes");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 6, 4, 6);

        Date hoy = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(hoy);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date manana = cal.getTime();

        spFechaEntrada = new JSpinner(new SpinnerDateModel(hoy, hoy, null, Calendar.DAY_OF_MONTH));
        spFechaEntrada.setEditor(new JSpinner.DateEditor(spFechaEntrada, "dd/MM/yyyy"));
        spFechaEntrada.setFont(FONT_INPUT);

        spFechaSalida = new JSpinner(new SpinnerDateModel(manana, hoy, null, Calendar.DAY_OF_MONTH));
        spFechaSalida.setEditor(new JSpinner.DateEditor(spFechaSalida, "dd/MM/yyyy"));
        spFechaSalida.setFont(FONT_INPUT);

        JLabel lblIn = new JLabel("Check-in:");
        lblIn.setFont(FONT_LABEL);
        g.gridx = 0;
        g.gridy = 0;
        panel.add(lblIn, g);

        g.gridx = 1;
        g.gridy = 0;
        panel.add(spFechaEntrada, g);

        JLabel lblOut = new JLabel("Check-out:");
        lblOut.setFont(FONT_LABEL);
        g.gridx = 2;
        g.gridy = 0;
        panel.add(lblOut, g);

        g.gridx = 3;
        g.gridy = 0;
        panel.add(spFechaSalida, g);

        JLabel lblAd = new JLabel("Adultos:");
        lblAd.setFont(FONT_LABEL);
        g.gridx = 0;
        g.gridy = 1;
        panel.add(lblAd, g);

        spAdultos = new JSpinner(new SpinnerNumberModel(2, 1, 6, 1));
        spAdultos.setFont(FONT_INPUT);
        g.gridx = 1;
        g.gridy = 1;
        panel.add(spAdultos, g);

        JLabel lblNi = new JLabel("Niños:");
        lblNi.setFont(FONT_LABEL);
        g.gridx = 2;
        g.gridy = 1;
        panel.add(lblNi, g);

        spNinos = new JSpinner(new SpinnerNumberModel(0, 0, 4, 1));
        spNinos.setFont(FONT_INPUT);
        g.gridx = 3;
        g.gridy = 1;
        panel.add(spNinos, g);

        JLabel lblHab = new JLabel("Habitaciones:");
        lblHab.setFont(FONT_LABEL);
        g.gridx = 0;
        g.gridy = 2;
        panel.add(lblHab, g);

        spHabitaciones = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
        spHabitaciones.setFont(FONT_INPUT);
        g.gridx = 1;
        g.gridy = 2;
        panel.add(spHabitaciones, g);

        spFechaEntrada.addChangeListener(e -> verificarDisponibilidadTiempoReal());
        spFechaSalida.addChangeListener(e -> verificarDisponibilidadTiempoReal());
        spHabitaciones.addChangeListener(e -> verificarDisponibilidadTiempoReal());

        return panel;
    }

    private JPanel crearPanelDatosHuesped() {
        JPanel panel = crearContenedorTarjeta("3. Datos del Huésped Principal");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 6, 4, 6);

        g.gridx = 0; g.gridy = 0;
        panel.add(new JLabel("Nombres *"), g);
        txtNombres = new JTextField(12);
        txtNombres.setFont(FONT_INPUT);
        g.gridx = 1; g.gridy = 0;
        panel.add(txtNombres, g);

        g.gridx = 2; g.gridy = 0;
        panel.add(new JLabel("Apellidos *"), g);
        txtApellidos = new JTextField(12);
        txtApellidos.setFont(FONT_INPUT);
        g.gridx = 3; g.gridy = 0;
        panel.add(txtApellidos, g);

        g.gridx = 0; g.gridy = 1;
        panel.add(new JLabel("Correo Electrónico *"), g);
        txtCorreo = new JTextField(12);
        txtCorreo.setFont(FONT_INPUT);
        g.gridx = 1; g.gridy = 1;
        panel.add(txtCorreo, g);

        g.gridx = 2; g.gridy = 1;
        panel.add(new JLabel("Teléfono / Celular *"), g);
        txtTelefono = new JTextField(12);
        txtTelefono.setFont(FONT_INPUT);
        g.gridx = 3; g.gridy = 1;
        panel.add(txtTelefono, g);

        g.gridx = 0; g.gridy = 2;
        panel.add(new JLabel("Documento *"), g);
        cbxTipoDocumento = new JComboBox<>(new String[]{"DPI (Guatemala)", "Pasaporte"});
        cbxTipoDocumento.setFont(FONT_INPUT);
        g.gridx = 1; g.gridy = 2;
        panel.add(cbxTipoDocumento, g);

        g.gridx = 2; g.gridy = 2;
        panel.add(new JLabel("No. Documento *"), g);
        txtNumDocumento = new JTextField(12);
        txtNumDocumento.setFont(FONT_INPUT);
        g.gridx = 3; g.gridy = 2;
        panel.add(txtNumDocumento, g);

        g.gridx = 0; g.gridy = 3;
        panel.add(new JLabel("Llegada Estimada:"), g);
        cbxHoraLlegada = new JComboBox<>(new String[]{
            "15:00 hrs (Check-in estándar)",
            "16:00 hrs", "17:00 hrs", "18:00 hrs", "19:00 hrs", "20:00 hrs o más"
        });
        cbxHoraLlegada.setFont(FONT_INPUT);
        g.gridx = 1; g.gridy = 3;
        panel.add(cbxHoraLlegada, g);

        g.gridx = 2; g.gridy = 3;
        panel.add(new JLabel("Petición Especial:"), g);
        txtPeticiones = new JTextField(12);
        txtPeticiones.setFont(FONT_INPUT);
        g.gridx = 3; g.gridy = 3;
        panel.add(txtPeticiones, g);

        g.gridx = 0; g.gridy = 4;
        panel.add(new JLabel("Modalidad de Pago:"), g);

        JPanel panelMetodo = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        panelMetodo.setOpaque(false);
        rbtnPagoLlegada = new JRadioButton("Pagar al Check-in en Hotel", true);
        rbtnGarantiaTarjeta = new JRadioButton("Garantía con Tarjeta");
        rbtnPagoLlegada.setOpaque(false);
        rbtnGarantiaTarjeta.setOpaque(false);

        grupoPago = new ButtonGroup();
        grupoPago.add(rbtnPagoLlegada);
        grupoPago.add(rbtnGarantiaTarjeta);

        panelMetodo.add(rbtnPagoLlegada);
        panelMetodo.add(rbtnGarantiaTarjeta);

        g.gridx = 1; g.gridy = 4;
        g.gridwidth = 3;
        panel.add(panelMetodo, g);

        return panel;
    }

    private JPanel crearPanelDisponibilidadLiquidacion() {
        JPanel panel = crearContenedorTarjeta("Validación y Desglose de Tarifa");
        panel.setLayout(new BorderLayout(10, 10));

        JPanel panelEstado = new JPanel(new BorderLayout());
        panelEstado.setBackground(new Color(238, 246, 237));
        panelEstado.setBorder(new CompoundBorder(
                new LineBorder(new Color(185, 219, 188), 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));

        lblEstadoDisponibilidad = new JLabel("Consultando disponibilidad en tiempo real...", JLabel.CENTER);
        lblEstadoDisponibilidad.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblEstadoDisponibilidad.setForeground(COLOR_EXITO);
        panelEstado.add(lblEstadoDisponibilidad, BorderLayout.CENTER);

        panel.add(panelEstado, BorderLayout.NORTH);

        JPanel panelDesglose = new JPanel(new GridBagLayout());
        panelDesglose.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(6, 6, 6, 6);

        g.gridx = 0; g.gridy = 0;
        panelDesglose.add(new JLabel("Duración de Estancia:"), g);
        lblDetalleNoches = new JLabel("0 noches", JLabel.RIGHT);
        lblDetalleNoches.setFont(FONT_BOLD);
        g.gridx = 1; g.gridy = 0;
        panelDesglose.add(lblDetalleNoches, g);

        g.gridx = 0; g.gridy = 1;
        panelDesglose.add(new JLabel("Tarifa Base Hospedaje:"), g);
        lblSubtotal = new JLabel("Q 0.00", JLabel.RIGHT);
        lblSubtotal.setFont(FONT_BOLD);
        g.gridx = 1; g.gridy = 1;
        panelDesglose.add(lblSubtotal, g);

        g.gridx = 0; g.gridy = 2;
        panelDesglose.add(new JLabel("Impuestos (IVA 12% + INGUAT 10%):"), g);
        lblImpuestos = new JLabel("Q 0.00", JLabel.RIGHT);
        lblImpuestos.setFont(FONT_BOLD);
        g.gridx = 1; g.gridy = 2;
        panelDesglose.add(lblImpuestos, g);

        JSeparator sep = new JSeparator();
        g.gridx = 0; g.gridy = 3;
        g.gridwidth = 2;
        panelDesglose.add(sep, g);

        g.gridwidth = 1;
        g.gridx = 0; g.gridy = 4;
        JLabel lblTotalEtiqueta = new JLabel("TOTAL ESTIMADO (GTQ):");
        lblTotalEtiqueta.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTotalEtiqueta.setForeground(COLOR_PRIMARIO);
        panelDesglose.add(lblTotalEtiqueta, g);

        lblTotalPagar = new JLabel("Q 0.00", JLabel.RIGHT);
        lblTotalPagar.setFont(new Font("Georgia", Font.BOLD, 18));
        lblTotalPagar.setForeground(COLOR_PRIMARIO);
        g.gridx = 1; g.gridy = 4;
        panelDesglose.add(lblTotalPagar, g);

        panel.add(panelDesglose, BorderLayout.CENTER);

        JTextArea txtPolitica = new JTextArea(
               
                 
        );
        txtPolitica.setFont(new Font("SansSerif", Font.ITALIC, 10));
        txtPolitica.setForeground(new Color(90, 105, 92));
        txtPolitica.setOpaque(false);
        txtPolitica.setEditable(false);
        txtPolitica.setBorder(new EmptyBorder(8, 4, 4, 4));

        panel.add(txtPolitica, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelBotonesAccion() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        bar.setBackground(new Color(236, 233, 224));
        bar.setBorder(new LineBorder(COLOR_BORDE, 1));

        btnLimpiar = new JButton("Limpiar Campos");
        btnLimpiar.setFont(FONT_LABEL);
        btnLimpiar.setBackground(Color.WHITE);
        btnLimpiar.setForeground(COLOR_TEXTO);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        btnVerificar = new JButton("Verificar Disponibilidad");
        btnVerificar.setFont(FONT_LABEL);
        btnVerificar.setBackground(new Color(230, 238, 229));
        btnVerificar.setForeground(COLOR_PRIMARIO);
        btnVerificar.setFocusPainted(false);
        btnVerificar.addActionListener(e -> verificarDisponibilidadTiempoReal());

        btnConfirmarReserva = new JButton("Confirmar y Emitir Reserva");
        btnConfirmarReserva.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnConfirmarReserva.setBackground(COLOR_PRIMARIO);
        btnConfirmarReserva.setForeground(Color.WHITE);
        btnConfirmarReserva.setFocusPainted(false);
        btnConfirmarReserva.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmarReserva.addActionListener(e -> procesarReserva());

        bar.add(btnLimpiar);
        bar.add(btnVerificar);
        bar.add(btnConfirmarReserva);

        return bar;
    }

    private JPanel crearContenedorTarjeta(String titulo) {
        JPanel panel = new JPanel();
        panel.setBackground(COLOR_PANEL);
        TitledBorder border = BorderFactory.createTitledBorder(
                new LineBorder(COLOR_BORDE, 1, true),
                titulo,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                FONT_SECCION,
                COLOR_PRIMARIO
        );
        panel.setBorder(new CompoundBorder(border, new EmptyBorder(8, 10, 10, 10)));
        return panel;
    }

    private void actualizarListaHoteles() {
        cbxHoteles.removeAllItems();
        boolean filtrarCapital = rbtnDentroCapital.isSelected();
        boolean filtrarFuera = rbtnFueraCapital.isSelected();

        for (HotelItem h : catalogoHoteles) {
            if (filtrarCapital && !h.isDentroDeCapital()) continue;
            if (filtrarFuera && h.isDentroDeCapital()) continue;
            cbxHoteles.addItem(h);
        }

        if (cbxHoteles.getItemCount() > 0) {
            cbxHoteles.setSelectedIndex(0);
        }
        actualizarListaHabitaciones();
    }

    private void actualizarListaHabitaciones() {
        cbxHabitaciones.removeAllItems();
        HotelItem hotelSeleccionado = (HotelItem) cbxHoteles.getSelectedItem();
        if (hotelSeleccionado != null) {
            for (HabitacionItem hab : hotelSeleccionado.getHabitaciones()) {
                cbxHabitaciones.addItem(hab);
            }
        }
    }

    private boolean verificarDisponibilidadTiempoReal() {
        Date fEntrada = (Date) spFechaEntrada.getValue();
        Date fSalida = (Date) spFechaSalida.getValue();

        if (!fSalida.after(fEntrada)) {
            lblEstadoDisponibilidad.setText("Fecha de salida debe ser posterior a entrada.");
            lblEstadoDisponibilidad.setForeground(COLOR_ALERTA);
            lblDetalleNoches.setText("0 noches");
            lblSubtotal.setText("Q 0.00");
            lblImpuestos.setText("Q 0.00");
            lblTotalPagar.setText("Q 0.00");
            btnConfirmarReserva.setEnabled(false);
            return false;
        }

        long diff = fSalida.getTime() - fEntrada.getTime();
        int noches = (int) (diff / (1000 * 60 * 60 * 24));
        lblDetalleNoches.setText(noches + (noches == 1 ? " noche" : " noches"));

        HabitacionItem hab = (HabitacionItem) cbxHabitaciones.getSelectedItem();
        int cantHabitaciones = (Integer) spHabitaciones.getValue();

        if (hab == null) {
            btnConfirmarReserva.setEnabled(false);
            return false;
        }

        boolean disponible = hab.getHabitacionesDisponibles() >= cantHabitaciones;

        if (disponible) {
            lblEstadoDisponibilidad.setText("✓ " + hab.getHabitacionesDisponibles() + " habitaciones libres (Disponibilidad inmediata)");
            lblEstadoDisponibilidad.setForeground(COLOR_EXITO);
            btnConfirmarReserva.setEnabled(true);
        } else {
            lblEstadoDisponibilidad.setText("Agotado para estas fechas (Solo quedan " + hab.getHabitacionesDisponibles() + ")");
            lblEstadoDisponibilidad.setForeground(COLOR_ALERTA);
            btnConfirmarReserva.setEnabled(false);
            return false;
        }

        double subtotal = hab.getPrecioPorNoche() * noches * cantHabitaciones;
        double impuestos = subtotal * 0.22;
        double cargoServicio = subtotal * 0.05;
        double total = subtotal + impuestos + cargoServicio;

        lblSubtotal.setText(monedaFormat.format(subtotal));
        lblImpuestos.setText(monedaFormat.format(impuestos + cargoServicio));
        lblTotalPagar.setText(monedaFormat.format(total));

        return true;
    }

    private boolean validarCamposHuesped() {
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String numDoc = txtNumDocumento.getText().trim();

        if (nombres.isEmpty()) {
            mostrarError("Por favor, ingrese sus nombres.", txtNombres);
            return false;
        }
        if (apellidos.isEmpty()) {
            mostrarError("Por favor, ingrese sus apellidos.", txtApellidos);
            return false;
        }
        if (correo.isEmpty() || !correo.contains("@") || !correo.contains(".")) {
            mostrarError("Ingrese un correo electrónico válido (ejemplo: usuario@correo.com).", txtCorreo);
            return false;
        }
        if (telefono.isEmpty() || telefono.length() < 8) {
            mostrarError("Ingrese un número de teléfono válido de al menos 8 dígitos.", txtTelefono);
            return false;
        }
        if (numDoc.isEmpty()) {
            mostrarError("Ingrese el número de identificación (DPI o Pasaporte).", txtNumDocumento);
            return false;
        }
        return true;
    }

    private void mostrarError(String mensaje, JComponent componente) {
        JOptionPane.showMessageDialog(this, mensaje, "Campo Requerido", JOptionPane.WARNING_MESSAGE);
        if (componente != null) {
            componente.requestFocus();
        }
    }

    private void procesarReserva() {
        if (!validarCamposHuesped()) {
            return;
        }
        if (!verificarDisponibilidadTiempoReal()) {
            JOptionPane.showMessageDialog(this, "Las fechas o habitación no están disponibles.", "Error de Disponibilidad", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int randomCode = 10000 + new Random().nextInt(90000);
        String codigoReserva = "BAMBU-GT-" + randomCode;

        HotelItem hotel = (HotelItem) cbxHoteles.getSelectedItem();
        HabitacionItem hab = (HabitacionItem) cbxHabitaciones.getSelectedItem();
        String entradaStr = dateFormat.format((Date) spFechaEntrada.getValue());
        String salidaStr = dateFormat.format((Date) spFechaSalida.getValue());

        StringBuilder voucher = new StringBuilder();
        voucher.append("====================================================\n");
        voucher.append("       COMPROBANTE OFICIAL DE RESERVA BAMBÚ        \n");
        voucher.append("====================================================\n\n");
        voucher.append("CÓDIGO DE RESERVA: ").append(codigoReserva).append("\n\n");
        voucher.append("Hotel: ").append(hotel.getNombre()).append("\n");
        voucher.append("Ubicación: ").append(hotel.getRegion()).append("\n");
        voucher.append("Habitación: ").append(hab.getNombre()).append("\n");
        voucher.append("Check-in: ").append(entradaStr).append(" (a partir de 15:00 hrs)\n");
        voucher.append("Check-out: ").append(salidaStr).append(" (hasta 12:00 hrs)\n");
        voucher.append("Duración: ").append(lblDetalleNoches.getText()).append("\n");
        voucher.append("Huéspedes: ").append(spAdultos.getValue()).append(" Adultos, ")
               .append(spNinos.getValue()).append(" Niños\n");
        voucher.append("Habitaciones: ").append(spHabitaciones.getValue()).append("\n\n");
        voucher.append("----------------------------------------------------\n");
        voucher.append("Huésped Titular: ").append(txtNombres.getText().trim()).append(" ")
               .append(txtApellidos.getText().trim()).append("\n");
        voucher.append("Documento (").append(cbxTipoDocumento.getSelectedItem()).append("): ")
               .append(txtNumDocumento.getText().trim()).append("\n");
        voucher.append("Correo: ").append(txtCorreo.getText().trim()).append("\n");
        voucher.append("Teléfono: ").append(txtTelefono.getText().trim()).append("\n");
        voucher.append("Modalidad de Pago: ").append(rbtnPagoLlegada.isSelected() ? "En Hotel al Check-in" : "Garantía con Tarjeta").append("\n");
        if (!txtPeticiones.getText().trim().isEmpty()) {
            voucher.append("Peticiones: ").append(txtPeticiones.getText().trim()).append("\n");
        }
        voucher.append("----------------------------------------------------\n");
        voucher.append("Total Liquidado: ").append(lblTotalPagar.getText()).append("\n");
        voucher.append("(Incluye 12% IVA + 10% Impuesto Hotelero INGUAT)\n\n");
        voucher.append("Se ha enviado una copia de este voucher a su correo.\n");
        voucher.append("¡Le esperamos en Hoteles BAMBÚ Guatemala!");

        JTextArea areaVoucher = new JTextArea(voucher.toString());
        areaVoucher.setEditable(false);
        areaVoucher.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaVoucher.setCaretPosition(0);

        JScrollPane scroll = new JScrollPane(areaVoucher);
        scroll.setPreferredSize(new Dimension(520, 420));

        JOptionPane.showMessageDialog(this, scroll, "¡Reserva Confirmada Exitosamente!", JOptionPane.INFORMATION_MESSAGE);

        hab.reducirDisponibilidad((Integer) spHabitaciones.getValue());
        verificarDisponibilidadTiempoReal();
    }

    private void limpiarFormulario() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtNumDocumento.setText("");
        txtPeticiones.setText("");
        cbxTipoDocumento.setSelectedIndex(0);
        cbxHoraLlegada.setSelectedIndex(0);
        rbtnPagoLlegada.setSelected(true);
        rbtnTodas.setSelected(true);
        spAdultos.setValue(2);
        spNinos.setValue(0);
        spHabitaciones.setValue(1);
        actualizarListaHoteles();
        verificarDisponibilidadTiempoReal();
    }

    static class HotelItem {
        private final String nombre;
        private final String region;
        private final boolean dentroDeCapital;
        private final java.util.List<HabitacionItem> habitaciones = new ArrayList<>();

        public HotelItem(String nombre, String region, boolean dentroDeCapital) {
            this.nombre = nombre;
            this.region = region;
            this.dentroDeCapital = dentroDeCapital;
        }

        public void agregarHabitacion(HabitacionItem h) {
            habitaciones.add(h);
        }

        public String getNombre() { return nombre; }
        public String getRegion() { return region; }
        public boolean isDentroDeCapital() { return dentroDeCapital; }
        public java.util.List<HabitacionItem> getHabitaciones() { return habitaciones; }

        @Override
        public String toString() {
            return nombre + " (" + region + ")";
        }
    }

    static class HabitacionItem {
        private final String nombre;
        private final double precioPorNoche;
        private int habitacionesDisponibles;
        private final String detalles;

        public HabitacionItem(String nombre, double precioPorNoche, int habitacionesDisponibles, String detalles) {
            this.nombre = nombre;
            this.precioPorNoche = precioPorNoche;
            this.habitacionesDisponibles = habitacionesDisponibles;
            this.detalles = detalles;
        }

        public String getNombre() { return nombre; }
        public double getPrecioPorNoche() { return precioPorNoche; }
        public int getHabitacionesDisponibles() { return habitacionesDisponibles; }
        public void reducirDisponibilidad(int cant) {
            this.habitacionesDisponibles = Math.max(0, this.habitacionesDisponibles - cant);
        }

        @Override
        public String toString() {
            return nombre + " - Q " + String.format("%.2f", precioPorNoche) + " / noche";
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            ReservasBambú app = new ReservasBambú();
            app.setVisible(true);
        });
    }
}