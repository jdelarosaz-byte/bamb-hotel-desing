package com.bambu.hotel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

/**
 * Formulario de Inicio de Sesión - BAMBÚ Hotel & Nature Suites
 * Diseñado para Java Swing en NetBeans con validación reactiva en tiempo real.
 */
public class LoginForm extends JFrame {

    // Paleta de colores armónica BAMBÚ
    private static final Color COLOR_BG_CANVAS = new Color(0xF6, 0xF4, 0xEE);     // #F6F4EE
    private static final Color COLOR_CARD_BG = new Color(0xFA, 0xF9, 0xF5);       // #FAF9F5
    private static final Color COLOR_PRIMARY = new Color(0x1B, 0x35, 0x24);       // #1B3524
    private static final Color COLOR_PRIMARY_HOVER = new Color(0x12, 0x24, 0x18);
    private static final Color COLOR_AMBER = new Color(0xB4, 0x53, 0x09);         // Dorado / Ámbar
    private static final Color COLOR_TEXT_DARK = new Color(0x29, 0x25, 0x24);
    private static final Color COLOR_TEXT_MUTED = new Color(0x78, 0x71, 0x6C);
    private static final Color COLOR_BORDER = new Color(0xE7, 0xE5, 0xE4);
    private static final Color COLOR_SUCCESS = new Color(0x15, 0x80, 0x3D);       // Verde validación
    private static final Color COLOR_ERROR = new Color(0xDC, 0x26, 0x26);         // Rojo error

    // Expresión regular para correo electrónico
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    // Componentes del Formulario
    private JTextField txtEmail;
    private JLabel lblEmailError;

    private JPasswordField txtPassword;
    private JButton btnTogglePassword;
    private boolean isPasswordVisible = false;

    // Indicadores de requisitos de contraseña
    private JLabel lblReqMinLength;
    private JLabel lblReqUppercase;
    private JLabel lblReqNumber;
    private JLabel lblReqSpecial;
    private JProgressBar barPasswordStrength;
    private JLabel lblStrengthText;

    private JCheckBox chkRemember;
    private JButton btnLogin;

    public LoginForm() {
        initComponents();
        setupRealTimeValidation();
    }

    private void initComponents() {
        setTitle("BAMBÚ Hotel & Nature Suites - Portal de Huéspedes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COLOR_BG_CANVAS);
        setLayout(new GridBagLayout());

        // Tarjeta contenedora central redondeada
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Fondo de tarjeta
                g2.setColor(COLOR_CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                // Borde suave
                g2.setColor(COLOR_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                // Franja decorativa superior
                g2.setColor(COLOR_PRIMARY);
                g2.fillRoundRect(0, 0, getWidth(), 6, 6, 6);
                g2.dispose();
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setPreferredSize(new Dimension(410, 670));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // 1. Cabecera de Marca
        JLabel lblLogo = new JLabel("B");
        lblLogo.setFont(new Font("Serif", Font.BOLD, 22));
        lblLogo.setForeground(new Color(0xFE, 0xF3, 0xC7));
        lblLogo.setOpaque(true);
        lblLogo.setBackground(COLOR_PRIMARY);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setPreferredSize(new Dimension(46, 46));
        lblLogo.setMaximumSize(new Dimension(46, 46));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblBrandSub = new JLabel("HOTEL & NATURE SUITES");
        lblBrandSub.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblBrandSub.setForeground(COLOR_AMBER);
        lblBrandSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblBrandTitle = new JLabel("BAMBÚ");
        lblBrandTitle.setFont(new Font("Serif", Font.BOLD, 30));
        lblBrandTitle.setForeground(COLOR_PRIMARY);
        lblBrandTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPortal = new JLabel("Portal de Huéspedes");
        lblPortal.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblPortal.setForeground(COLOR_TEXT_MUTED);
        lblPortal.setAlignmentX(Component.CENTER_ALIGNMENT);

        cardPanel.add(lblLogo);
        cardPanel.add(Box.createVerticalStrut(8));
        cardPanel.add(lblBrandSub);
        cardPanel.add(lblBrandTitle);
        cardPanel.add(lblPortal);
        cardPanel.add(Box.createVerticalStrut(20));

        // 2. Campo Correo Electrónico
        JLabel lblEmail = new JLabel("Correo electrónico");
        lblEmail.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEmail.setForeground(COLOR_TEXT_DARK);
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(lblEmail);
        cardPanel.add(Box.createVerticalStrut(4));

        txtEmail = new JTextField();
        estilizarCampo(txtEmail);
        cardPanel.add(txtEmail);

        lblEmailError = new JLabel(" ");
        lblEmailError.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblEmailError.setForeground(COLOR_ERROR);
        lblEmailError.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(lblEmailError);
        cardPanel.add(Box.createVerticalStrut(10));

        // 3. Campo Contraseña con Toggle de visibilidad
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblPassword.setForeground(COLOR_TEXT_DARK);
        lblPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(lblPassword);
        cardPanel.add(Box.createVerticalStrut(4));

        JPanel pnlPasswordInput = new JPanel(new BorderLayout(5, 0));
        pnlPasswordInput.setOpaque(false);
        pnlPasswordInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        txtPassword = new JPasswordField();
        estilizarCampo(txtPassword);
        pnlPasswordInput.add(txtPassword, BorderLayout.CENTER);

        btnTogglePassword = new JButton("👁");
        btnTogglePassword.setPreferredSize(new Dimension(42, 38));
        btnTogglePassword.setFocusPainted(false);
        btnTogglePassword.setBackground(Color.WHITE);
        btnTogglePassword.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        btnTogglePassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTogglePassword.setToolTipText("Mostrar/Ocultar Contraseña");
        btnTogglePassword.addActionListener(e -> togglePasswordVisibility());
        pnlPasswordInput.add(btnTogglePassword, BorderLayout.EAST);
        pnlPasswordInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(pnlPasswordInput);

        // Barra de fuerza de contraseña
        JPanel pnlStrength = new JPanel(new BorderLayout());
        pnlStrength.setOpaque(false);
        pnlStrength.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        lblStrengthText = new JLabel("Fuerza: —");
        lblStrengthText.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblStrengthText.setForeground(COLOR_TEXT_MUTED);
        pnlStrength.add(lblStrengthText, BorderLayout.EAST);
        pnlStrength.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(Box.createVerticalStrut(6));
        cardPanel.add(pnlStrength);

        barPasswordStrength = new JProgressBar(0, 4);
        barPasswordStrength.setValue(0);
        barPasswordStrength.setPreferredSize(new Dimension(350, 6));
        barPasswordStrength.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
        barPasswordStrength.setForeground(COLOR_BORDER);
        barPasswordStrength.setBackground(new Color(0xE2, 0xE0, 0xD8));
        barPasswordStrength.setBorderPainted(false);
        barPasswordStrength.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(barPasswordStrength);
        cardPanel.add(Box.createVerticalStrut(10));

        // Lista de Requisitos de Contraseña
        JPanel pnlRequirements = new JPanel();
        pnlRequirements.setLayout(new BoxLayout(pnlRequirements, BoxLayout.Y_AXIS));
        pnlRequirements.setBackground(new Color(0xF3, 0xF1, 0xEB));
        pnlRequirements.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER),
            new EmptyBorder(8, 12, 8, 12)
        ));
        pnlRequirements.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblReqMinLength = crearEtiquetaRequisito("Mínimo 8 caracteres");
        lblReqUppercase = crearEtiquetaRequisito("Al menos una mayúscula (A-Z)");
        lblReqNumber = crearEtiquetaRequisito("Al menos un número (0-9)");
        lblReqSpecial = crearEtiquetaRequisito("Al menos un carácter especial (!@#$...)");

        pnlRequirements.add(lblReqMinLength);
        pnlRequirements.add(Box.createVerticalStrut(3));
        pnlRequirements.add(lblReqUppercase);
        pnlRequirements.add(Box.createVerticalStrut(3));
        pnlRequirements.add(lblReqNumber);
        pnlRequirements.add(Box.createVerticalStrut(3));
        pnlRequirements.add(lblReqSpecial);
        cardPanel.add(pnlRequirements);
        cardPanel.add(Box.createVerticalStrut(12));

        // 4. Recordar acceso
        chkRemember = new JCheckBox("Recordar mi acceso en este equipo");
        chkRemember.setFont(new Font("SansSerif", Font.PLAIN, 12));
        chkRemember.setForeground(COLOR_TEXT_DARK);
        chkRemember.setOpaque(false);
        chkRemember.setFocusPainted(false);
        chkRemember.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(chkRemember);
        cardPanel.add(Box.createVerticalStrut(16));

        // 5. Botón de Iniciar Sesión (Deshabilitado hasta validar)
        btnLogin = new JButton("Ingresar a BAMBÚ") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isEnabled()) {
                    g2.setColor(getModel().isRollover() ? COLOR_PRIMARY_HOVER : COLOR_PRIMARY);
                } else {
                    g2.setColor(new Color(0xD6, 0xD3, 0xCB));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(350, 44));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setEnabled(false);
        btnLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnLogin.addActionListener(this::ejecutarLogin);
        cardPanel.add(btnLogin);

        add(cardPanel);
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campo.setPreferredSize(new Dimension(350, 38));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setBackground(Color.WHITE);
        campo.setForeground(COLOR_TEXT_DARK);
        campo.setCaretColor(COLOR_PRIMARY);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JLabel crearEtiquetaRequisito(String texto) {
        JLabel lbl = new JLabel("✕  " + texto);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(COLOR_TEXT_MUTED);
        return lbl;
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            txtPassword.setEchoChar('•');
            btnTogglePassword.setText("👁");
            isPasswordVisible = false;
        } else {
            txtPassword.setEchoChar((char) 0);
            btnTogglePassword.setText("🙈");
            isPasswordVisible = true;
        }
    }

    /**
     * Vincula listeners de cambios en tiempo real a los campos.
     */
    private void setupRealTimeValidation() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validarFormularioEnTiempoReal(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validarFormularioEnTiempoReal(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validarFormularioEnTiempoReal(); }
        };

        txtEmail.getDocument().addDocumentListener(listener);
        txtPassword.getDocument().addDocumentListener(listener);
    }

    /**
     * Lógica de validación continua ejecutada en cada pulsación de tecla.
     */
    private void validarFormularioEnTiempoReal() {
        String email = txtEmail.getText().trim();
        String pass = new String(txtPassword.getPassword());

        // 1. Validación de Correo Electrónico
        boolean isEmailValid = false;
        if (email.isEmpty()) {
            lblEmailError.setText(" ");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            lblEmailError.setForeground(COLOR_ERROR);
            lblEmailError.setText("Formato inválido (ej. huesped@bambuhotels.com)");
        } else {
            lblEmailError.setForeground(COLOR_SUCCESS);
            lblEmailError.setText("✓ Correo válido");
            isEmailValid = true;
        }

        // 2. Validación de Requisitos de Contraseña
        boolean minLength = pass.length() >= 8;
        boolean hasUpper = Pattern.compile("[A-Z]").matcher(pass).find();
        boolean hasNumber = Pattern.compile("[0-9]").matcher(pass).find();
        boolean hasSpecial = Pattern.compile("[^a-zA-Z0-9]").matcher(pass).find();

        actualizarEtiqueta(lblReqMinLength, minLength, "Mínimo 8 caracteres");
        actualizarEtiqueta(lblReqUppercase, hasUpper, "Al menos una mayúscula (A-Z)");
        actualizarEtiqueta(lblReqNumber, hasNumber, "Al menos un número (0-9)");
        actualizarEtiqueta(lblReqSpecial, hasSpecial, "Al menos un carácter especial (!@#$...)");

        // Cálculo de nivel de fuerza
        int score = 0;
        if (minLength) score++;
        if (hasUpper) score++;
        if (hasNumber) score++;
        if (hasSpecial) score++;

        barPasswordStrength.setValue(score);
        switch (score) {
            case 0:
                barPasswordStrength.setForeground(COLOR_BORDER);
                lblStrengthText.setText("Fuerza: —");
                lblStrengthText.setForeground(COLOR_TEXT_MUTED);
                break;
            case 1:
                barPasswordStrength.setForeground(new Color(0xEF, 0x44, 0x44));
                lblStrengthText.setText("Fuerza: Débil");
                lblStrengthText.setForeground(new Color(0xDC, 0x26, 0x26));
                break;
            case 2:
            case 3:
                barPasswordStrength.setForeground(new Color(0xF5, 0x9E, 0x0B));
                lblStrengthText.setText("Fuerza: Aceptable");
                lblStrengthText.setForeground(new Color(0xD9, 0x77, 0x06));
                break;
            case 4:
                barPasswordStrength.setForeground(COLOR_SUCCESS);
                lblStrengthText.setText(pass.length() >= 10 ? "Fuerza: Excelente" : "Fuerza: Fuerte");
                lblStrengthText.setForeground(COLOR_SUCCESS);
                break;
        }

        boolean isPassValid = (score == 4);

        // Habilitar o deshabilitar botón en tiempo real
        btnLogin.setEnabled(isEmailValid && isPassValid);
    }

    private void actualizarEtiqueta(JLabel label, boolean cumple, String texto) {
        if (cumple) {
            label.setText("✓  " + texto);
            label.setForeground(COLOR_SUCCESS);
        } else {
            label.setText("✕  " + texto);
            label.setForeground(COLOR_TEXT_MUTED);
        }
    }

    private void ejecutarLogin(ActionEvent e) {
        String email = txtEmail.getText().trim();
        JOptionPane.showMessageDialog(
            this,
            "¡Bienvenido a BAMBÚ Hotel & Nature Suites!\n\n"
            + "Huésped: " + email + "\n"
            + "Suite asignada: Villa Bambú #14\n"
            + "Llave digital y Concierge 24/7 activados.",
            "Acceso Autorizado",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        // Establecer apariencia nativa del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}