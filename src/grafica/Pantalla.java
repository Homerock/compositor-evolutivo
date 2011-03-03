package grafica;

import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Dimension;
import javax.swing.JButton;

import compositor.Aprendiz;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.Point;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Pantalla extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JScrollPane jScrollPane = null;
	private JButton jButtonPausa;
	private JButton jButtonStop;
	private JButton jButtonPlay;
	private JTextArea jTextArea = null;
	private JButton jButtonAprender = null;
	private JButton jButtonComponer = null;
	private JButton jButtonGuardar = null;
	private JButton jButtonLimpiar = null;
	private JButton jButtonSalir = null;
	private JButton jButtonAceptarComp = null;
	private JTextField jTextFieldTonica = null;
	private JLabel jLabelTonica = null;
	private Aprendiz aprendiz;

	
		
	/**
	 * This is the default constructor
	 */
	public Pantalla(Aprendiz aprendiz) {
		super();
		this.aprendiz = aprendiz;
		aprendiz.setInterfaz(this);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(850, 600);
		this.setContentPane(getJPanel());
		this.setTitle("Compositor musical evolutivo");
		this.setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJButtonAprender(), null);
			jPanel.add(getJButtonComponer(), null);
			jPanel.add(getJButtonGuardar(), null);
			jPanel.add(getJButtonLimpiar(), null);
			jPanel.add(getJButtonSalir(), null);
			jPanel.add(getJButtonAceptarComp(), null);
			jPanel.add(getJTextFieldTonica(), null);
			jPanel.add(getjLabelTonica(), null);
			jPanel.add(getJScrollPane(), null);
			jPanel.add(getJButtonPlay());
			jPanel.add(getJButtonPausa());
			jPanel.add(getJButtonStop());
		}
		return jPanel;
	}

	/**
	 * This method initializes jButtonAprender	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonAprender() {
		if (jButtonAprender == null) {
			jButtonAprender = new JButton();
			jButtonAprender.setBounds(new Rectangle(30, 50, 180, 50));
			jButtonAprender.setText("Aprender");
			jButtonAprender.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					aprendiz.iniciar();
				}
			});
		}
		return jButtonAprender;
	}

	/**
	 * This method initializes jButtonComponer
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonComponer() {
		if (jButtonComponer == null) {
			jButtonComponer = new JButton();
			jButtonComponer.setText("Componer");
			jButtonComponer.setBounds(new Rectangle(30, 120, 180, 50));
			jButtonComponer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Pantalla.this.jLabelTonica.setVisible(true);
					Pantalla.this.jTextFieldTonica.setVisible(true);
					Pantalla.this.jButtonAceptarComp.setVisible(true);
				}
			});
		}
		return jButtonComponer;
	}
	
	/**
	 * @return
	 */
	private JButton getJButtonGuardar() {
		if (jButtonGuardar == null) {
			jButtonGuardar = new JButton();
			jButtonGuardar.setBounds(new Rectangle(30, 190, 180, 50));
			jButtonGuardar.setText("Actualizar BD");
			jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					aprendiz.guardar();
				}
			});
		}
		return jButtonGuardar;
	}
	
	/**
	 * @return
	 */
	private JButton getJButtonLimpiar() {
		if (jButtonLimpiar == null) {
			jButtonLimpiar = new JButton();
			jButtonLimpiar.setText("Limpiar mem/BD");
			jButtonLimpiar.setBounds(new Rectangle(30, 260, 180, 50));
			jButtonLimpiar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					aprendiz.limpiar();
				}
			});
		}
		return jButtonLimpiar;
	}
	
	/**
	 * @return
	 */
	private JButton getJButtonSalir() {
		if (jButtonSalir == null) {
			jButtonSalir = new JButton();
			jButtonSalir.setText("Salir");
			jButtonSalir.setBounds(new Rectangle(30, 330, 180, 50));
			jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					aprendiz.salir();
				}
			});
		}
		return jButtonSalir;
	}
	
	/**
	 * @return
	 */
	private JButton getJButtonAceptarComp() {
		if (jButtonAceptarComp == null) {
			jButtonAceptarComp = new JButton();
			jButtonAceptarComp.setText("Ok");
			jButtonAceptarComp.setVisible(false);
			jButtonAceptarComp.setBounds(new Rectangle(600, 50, 70, 30));
			jButtonAceptarComp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String tonica = Pantalla.this.getJTextFieldTonica().getText();
					aprendiz.componer(tonica);
				}
			});
		}
		return jButtonAceptarComp;
	}
	
	/**
	 * @return
	 */
	private JTextField getJTextFieldTonica() {
		if (jTextFieldTonica == null) {
			jTextFieldTonica = new JTextField();
			jTextFieldTonica.setVisible(false);
			jTextFieldTonica.setLocation(new Point(530, 50));
			jTextFieldTonica.setSize(new Dimension(50, 30));
		}
		return jTextFieldTonica;
	}
	
	/**
	 * @return
	 */
	private JLabel getjLabelTonica() {
		if (jLabelTonica == null) {
			jLabelTonica = new JLabel();
			jLabelTonica.setVisible(false);
			jLabelTonica.setText("Tónica para componer:");
			jLabelTonica.setLocation(new Point(350, 50));
			jLabelTonica.setSize(new Dimension(250, 30));
		}
		return jLabelTonica;
	}
	
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jTextArea = new JTextArea();
			jScrollPane = new JScrollPane(jTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jScrollPane.setBounds(new Rectangle(300, 100, 500, 400));
		}
		return jScrollPane;
	}
	
	public void actualizarLog(String info){
		this.jTextArea.append(info);
	}
	
	private JButton getJButtonPlay() {
		if(jButtonPlay == null) {
			jButtonPlay = new JButton();
			jButtonPlay.setText("Play");
			jButtonPlay.setBounds(440, 530, 76, 21);
		}
		return jButtonPlay;
	}
	
	private JButton getJButtonPausa() {
		if(jButtonPausa == null) {
			jButtonPausa = new JButton();
			jButtonPausa.setText("Pause");
			jButtonPausa.setBounds(521, 530, 86, 21);
		}
		return jButtonPausa;
	}
	
	private JButton getJButtonStop() {
		if(jButtonStop == null) {
			jButtonStop = new JButton();
			jButtonStop.setText("Stop");
			jButtonStop.setBounds(612, 530, 81, 21);
		}
		return jButtonStop;
	}

} 
