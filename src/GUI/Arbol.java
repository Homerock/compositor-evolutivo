package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.util.Collections;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import utiles.FuncionesComunes;

/**
 * Display a file system in a JTree view
 * 
 * @version $Id: FileTree.java,v 1.9 2004/02/23 03:39:22 ian Exp $
 * @author Ian Darwin
 */
public class Arbol extends JPanel {
  /** Construct a FileTree */
	public JTree tree;
  public Arbol(File dir) {
    setLayout(new BorderLayout());
    
    // Make a tree list with all the nodes, and make it a JTree
    tree = new JTree(addNodes(null, dir));

    // Lastly, put the JTree into a JScrollPane.
    JScrollPane scrollpane = new JScrollPane();
    scrollpane.getViewport().add(tree);
    add(BorderLayout.CENTER, scrollpane);
  }

  /** Add nodes from under "dir" into curTop. Highly recursive. */
  private DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
    String curPath = dir.getPath();
   
    DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
    if (curTop != null) { // should only be null at root
      curTop.add(curDir);
    }
    Vector ol = new Vector();
    String[] tmp = dir.list();
    
    for (int i = 0; i < tmp.length; i++){
    	if(tmp[i]!=null)
    	ol.addElement(tmp[i]);
    }
    Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
    File f;
    Vector files = new Vector();
    // Make two passes, one for Dirs and one for Files. This is #1.
    for (int i = 0; i < ol.size(); i++) {
    	try{	
	      String thisObject = (String) ol.elementAt(i);
	      String newPath;
	      
	       
	      if (thisObject.startsWith(".") ) {
	    	  continue;
	      }
	      if (curPath.equals("."))
	        newPath = thisObject;
	      else
	        newPath = curPath + File.separator + thisObject;
	    	 
	      if ((f = new File(newPath)).isDirectory())
	    		addNodes(curDir, f);
	      else
	    	if (!thisObject.startsWith(".")) {
	    		files.addElement(thisObject);
	    	}
    	}catch(NullPointerException e ){
    		continue;
    	}
    }
    // Pass two: for files.
    for (int fnum = 0; fnum < files.size(); fnum++)
      curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
    return curDir;
  }

  public Dimension getMinimumSize() {
    return new Dimension(200, 400);
  }

  public Dimension getPreferredSize() {
    return new Dimension(200, 400);
  }

  /** Main: make a Frame, add a FileTree */
  public static void main(String[] av) {

    JFrame frame = new JFrame("FileTree");
    frame.setForeground(Color.black);
    frame.setBackground(Color.lightGray);
    Container cp = frame.getContentPane();

    if (av.length == 0) {
      cp.add(new Arbol(new File(FuncionesComunes.getPathOS())));
    } else {
      cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
      for (int i = 0; i < av.length; i++)  
        cp.add(new Arbol(new File(av[i])));
    }

    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}