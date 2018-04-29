package Baum;

import java.util.ArrayList;
import java.util.List;

public class TElement {
	private List<treeListener> treeListeners = new ArrayList<treeListener>();					// Liste der TreeListener. Vorgesehen f�r TreePanel
	
	private int data;
	private int hoehe;
	private TElement left;
	private TElement right;

	public TElement(treeListener tl) {
		super();
		this.hoehe = 0;
		addListener(tl);
	}

	public TElement(int hoehe) {
		super();
		this.hoehe = hoehe;
	}

	public int getHoehe() {
		return hoehe;
	}

	public void setHoehe(int hoehe) {
		this.hoehe = hoehe;
	}

	public int getWert() {
		return data;
	}

	public void setWert(int wert) {
		this.data = wert;
		notifyOnTreeChangedListeners();
	}

	public TElement getLeft() {
		return left;
	}

	public void setLeft(TElement left) {
		this.left = left;
		notifyOnTreeChangedListeners();
	}

	public TElement getRight() {
		return right;
	}

	public void setRight(TElement right) {
		this.right = right;
		notifyOnTreeChangedListeners();
	}
	
	/**
	 * Ersetzt Attributswerte
	 * param:	replace	: TElement	: Baum
	 * Version 1
	 * Erstellt
	 */
	public void replace(TElement replace) {
		this.data = replace.getWert();
		this.hoehe = replace.getHoehe();
		this.left = replace.getLeft();
		this.right = replace.getRight();
		notifyOnTreeChangedListeners();
	}

	/**
	 * F�gt Listener hinzu
	 * param:	toAdd: treeListener	: Listener
	 * Version 1
	 * Erstellt
	 */
    public void addListener(treeListener toAdd) {												// F�gt einen Listener hinzu
        treeListeners.add(toAdd);
    }
    
    /**
	 * Ruft OnTreeChanged Listeners auf
	 * Version 1
	 * Erstellt
	 */
    public void notifyOnTreeChangedListeners() {												// Ruft alle onTreeChanged Methoden der Listener auf
    	for (treeListener tl : treeListeners) {
			tl.onTreeChanged();
		}
    }
}
