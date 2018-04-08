import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;

import Baum.TElement;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;

public class Main {
	private static final int FEHLER = 0;														// Konstanten f�r den Status der Funktion zum Einf�gen von Elementen
	private static final int OK = 1;
	private static final int VORHANDEN = 2;

	private JFrame frmBinrerBaum;
	private JTextField addTxtF;
	private JTextField orderTxtF;
	private JLabel statusMessage;
	
	private TElement root;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmBinrerBaum.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBinrerBaum = new JFrame();
		frmBinrerBaum.setResizable(false);
		frmBinrerBaum.setTitle("Bin\u00E4rer Baum");
		frmBinrerBaum.setBounds(100, 100, 683, 482);
		frmBinrerBaum.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBinrerBaum.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("TitledBorder.border"));
		panel.setBounds(10, 11, 657, 61);
		frmBinrerBaum.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Wert");
		label.setBounds(10, 11, 94, 14);
		panel.add(label);
		
		JButton addBtn = new JButton("hinzuf\u00FCgen");
		@SuppressWarnings("serial")
		Action addBtnAction = new AbstractAction() {											// Erstellen einer Action, um sie sowohl f�r den addBtn als auch f�r addTxtF zu setzen
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int insertWert = Integer.parseInt(addTxtF.getText());						// Eingef�gten Text zu Integer konvertieren	
					if(root == null) {															// Root setzen, falls nicht vorhanden
						root = new TElement();
						root.setWert(insertWert);
						statusMessage.setText("Root mit Wert " + insertWert + " gesetzt");
					} else {																	
							TElement insertElement = new TElement();							// Element setzen und einf�gen
							insertElement.setWert(insertWert);				
							switch (insertElement(root, insertElement)) {
							case 0:
								statusMessage.setText("Beim Einf�gen ist ein Fehler aufgetreten");
								break;
							case 1:
								statusMessage.setText("Element mit Wert " + insertWert + " eingef�gt");
								break;
							case 2:
								statusMessage.setText("Element mit Wert " + insertWert + " ist bereits vorhanden");
								break;
							}
					}												
				} catch (NumberFormatException ex) {											// Fange, wenn eingef�gter Text kein Integer ist
					System.err.println(ex);														// Gebe Fehler in Konsole und auf dem Bildschirm aus
					JOptionPane.showMessageDialog(frmBinrerBaum, "Ung�ltige Eingabe");
				} finally {
					addTxtF.requestFocus();														// F�r neue Eingabe vorbereiten
					addTxtF.selectAll();		
				}
			}
		};
		addBtn.addActionListener(addBtnAction);
		addBtn.setBounds(120, 26, 100, 23);
		panel.add(addBtn);
		
		addTxtF = new JTextField();
		addTxtF.setToolTipText("Nur ganze Zahlen");
		addTxtF.setColumns(10);
		addTxtF.addActionListener(addBtnAction);
		addTxtF.setBounds(10, 27, 100, 20);
		panel.add(addTxtF);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(UIManager.getBorder("TitledBorder.border"));
		panel_1.setBounds(10, 376, 657, 43);
		frmBinrerBaum.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton postOrderBtn = new JButton("PostOrder");
		postOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					orderTxtF.setText(PostOrder(root));
				} catch (NullPointerException ex) {
					System.err.println(ex);
					JOptionPane.showMessageDialog(frmBinrerBaum, "Root is nicht gesetzt");
				}
			}
		});
		postOrderBtn.setBounds(210, 11, 95, 23);
		panel_1.add(postOrderBtn);
		
		JButton inOrderBtn = new JButton("InOrder");
		inOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					orderTxtF.setText(InOrder(root));
				} catch (NullPointerException ex) {
					System.err.println(ex);
					JOptionPane.showMessageDialog(frmBinrerBaum, "Root is nicht gesetzt");
				}
			}
		});
		inOrderBtn.setBounds(115, 11, 85, 23);
		panel_1.add(inOrderBtn);
		
		JButton preOrderBtn = new JButton("PreOrder");
		preOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					orderTxtF.setText(PreOrder(root));
				} catch (NullPointerException ex) {
					System.err.println(ex);
					JOptionPane.showMessageDialog(frmBinrerBaum, "Root is nicht gesetzt");
				}
			}
		});
		preOrderBtn.setBounds(10, 11, 95, 23);
		panel_1.add(preOrderBtn);
		
		orderTxtF = new JTextField();
		orderTxtF.setBounds(315, 12, 332, 20);
		panel_1.add(orderTxtF);
		orderTxtF.setEditable(false);
		orderTxtF.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(0, 430, 677, 23);
		frmBinrerBaum.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		statusMessage = new JLabel("Lege den Wert des Root Elements fest!");
		statusMessage.setBounds(5, 4, 652, 14);
		panel_2.add(statusMessage);
	}
	
	/**
	 * F�gt ein Objekt der Klasse TElement in einem Baum ein
	 * param:	r: TElement	: aktuelles Element
	 * 			e: TElement	: einzuf�gendes Element
	 * return:	 : int		: 0: Fehler, 1: OK, 2: Element schon im Baum
	 * Version 1
	 * H�he setzen entfernt
	 */
	private static int insertElement(TElement r, TElement e) {
		try {
			int status = FEHLER;																// Status der Funktion f�r Wiedergabe
			if (r.getWert() == e.getWert())	{													// �berpr�fen, ob die Werte des aktuellen Elements und des Einzuf�genden Elements gleich sind
				return VORHANDEN;																		
			} else if(e.getWert() < r.getWert()) {												// Links rekursiv einf�gen, wenn der Wert des einzuf�genden Elements kleiner als des aktuellen Elements ist und
				if(r.getLeft() != null) {														// ein linkes Element gesetzt ist
					status = insertElement(r.getLeft(), e);
				} else {
					r.setLeft(e);																// Einzuf�gendes Element im aktuellen Element links setzen
					status = OK;
				}
			} else if(e.getWert() > r.getWert()) {												// Rechts rekursiv einf�gen, wenn der Wert des einzuf�genden Elements gr��er als des aktuellen Elements ist und
				if(r.getRight() != null) {														// ein rechtes Element gesetzt ist
					status = insertElement(r.getRight(), e);
				} else {
					r.setRight(e);																// Einzuf�gendes Element im aktuellen Element rechts setzen
					status = OK;
				}
			}
			r.setHoehe(countHoehe(r));															// H�he aktualisieren
			return status;																		// Status wiedergeben
		} catch (Exception ex) {
			System.err.println(ex);
			return FEHLER;
		}
	}
	
	/**
	 * Findet ein Element mit bestimmten Wert
	 * param:	r	: TElement	: Baum
	 * 			wert: int		: Wert
	 * return:	 	: int		: 0: Fehler, 1: Gefunden, 2: Nicht gefunden
	 * Version 1
	 * Noch nicht durchdacht
	 */
	private static int FindElement(TElement r, int wert) {
		try {
			return 0;
		} catch (Exception ex) {
			System.err.println(ex);
			return 0;
		}
	}

	/**
	 * Gibt einen Baum mit PreOrder als String wieder
	 * param:	r: TElement	: Baum
	 * return:	 : String	: PreOrder
	 * Version 1
	 * Erstellt
	 */
	private static String PreOrder(TElement r) {
		ArrayList<String> preOrderList = new ArrayList<>();										// ArrayList um die Werte zum Schluss zusammenzuf�gen
		preOrderList.add(String.valueOf(r.getWert()));											// Wert des aktuellen Elements der ArrayList hinzuf�gen
		if (r.getLeft() != null) {																// PreOrder von linkem Element der ArrayList hinzuf�gen, falls vorhanden
			preOrderList.add(PreOrder(r.getLeft()));
		}
		if (r.getRight() != null) {																// PreOrder von rechtem Element der ArrayList hinzuf�gen, falls vorhanden
			preOrderList.add(PreOrder(r.getRight()));
		}
		return String.join(", ", preOrderList);													// ArrayList mit Komma zusammengef�gt zur�ckgeben
	}
	
	/**
	 * Gibt einen Baum mit PostOrder als String wieder
	 * param:	r: TElement	: Baum
	 * return:	 : String	: PostOrder
	 * Version 1
	 * Erstellt
	 */
	private static String PostOrder(TElement r) {
		ArrayList<String> postOrderList = new ArrayList<>();									// ArrayList um die Werte zum Schluss zusammenzuf�gen
		if (r.getLeft() != null) {																// PostOrder von linkem Element der ArrayList hinzuf�gen, falls vorhanden
			postOrderList.add(PostOrder(r.getLeft()));
		}
		if (r.getRight() != null) {																// PostOrder von rechtem Element der ArrayList hinzuf�gen, falls vorhanden
			postOrderList.add(PostOrder(r.getRight()));
		}
		postOrderList.add(String.valueOf(r.getWert()));											// Wert des aktuellen Elements der ArrayList hinzuf�gen
		return String.join(", ", postOrderList);												// ArrayList mit Komma zusammengef�gt zur�ckgeben
	}
	
	/**
	 * Gibt einen Baum mit InOrder als String wieder
	 * param:	r: TElement	: Baum
	 * return:	 : String	: InOrder
	 * Version 1
	 * Erstellt
	 */
	private static String InOrder(TElement r) {
		ArrayList<String> inOrderList = new ArrayList<>();										// ArrayList um die Werte zum Schluss zusammenzuf�gen
		if (r.getLeft() != null) {																// InOrder von linkem Element der ArrayList hinzuf�gen, falls vorhanden
			inOrderList.add(InOrder(r.getLeft()));
		}
		inOrderList.add(String.valueOf(r.getWert()));											// Wert des aktuellen Elements der ArrayList hinzuf�gen
		if (r.getRight() != null) {																// InOrder von rechtem Element der ArrayList hinzuf�gen, falls vorhanden
			inOrderList.add(InOrder(r.getRight()));
		}
		return String.join(", ", inOrderList);													// ArrayList mit Komma zusammengef�gt zur�ckgeben
	}
	
	/**
	 * Z�hlt das Maximum der am Baum h�ngenden Ebenen
	 * param:	r: TElement	: Baum
	 * Version 1
	 * Erstellt
	 */
	private static int countHoehe(TElement r) {													
		return countHoehe(r, 0);																
	}
	private static int countHoehe(TElement r, int curHoehe) {
		int leftHoehe=0;																		// 0 als standard H�he Wert
		int rightHoehe=0;
		if(r.getLeft()!= null) {
			leftHoehe = countHoehe(r.getLeft(), curHoehe + 1);									// Links mit einer Ebene erh�ht fortfahren Ergebnis zwischenspeicher
		}
		if(r.getRight()!= null) {
			rightHoehe = countHoehe(r.getRight(), curHoehe + 1);								// Rechts mit einer Ebene erh�ht fortfahren und Ergebnis zwischenspeicher
		}
		if(leftHoehe>curHoehe) {
			curHoehe=leftHoehe;																	// Links als aktuelle H�he setzen, wenn aktuelle H�he kleiner ist
		}
		if(rightHoehe>curHoehe) {
			curHoehe=rightHoehe;																// Rechts als aktuelle H�he setzen, wenn aktuelle H�he kleiner ist
		} 
		return curHoehe;																		// Aktuelle H�he zur�ckgeben
	}
}
