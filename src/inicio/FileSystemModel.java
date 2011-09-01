package inicio;

import java.io.File;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

class FileSystemModel implements TreeModel {
  private File root;

  private Vector listeners = new Vector();

  public FileSystemModel() {
      
      // Definimos la cadena del interprete de comandos del sistema 
      String ruta=null; 
      // Recuperamos el sistema operativo 
      String osName = System.getProperty ( "os.name" ); 
              
      // Cargamos la cadena del interprete de comandos según el sistema operativo y el comando a ejecutar 
      if ( osName.equals ("Windows XP") ) 
          ruta = "c:\\"; 
      else 
          if ( osName.equals ("Windows 95") || osName.equals ("Windows 98") ) 
                ruta = "c:\\";  
          else { 
                ruta = "/home";
        } 
    root = new File(ruta);
    
  }

  public Object getRoot() {
    return root;
  }
  
  public Object getChild(Object parent, int index) {
    File directory = (File) parent;
    String[] children = directory.list(); 
    return new TreeFile(directory, children[index]);
  }

  public int getChildCount(Object parent) {
    File file = (File) parent;
    if (file.isDirectory()) {
      String[] fileList = file.list();
      if (fileList != null)
        return file.list().length;
    }
    return 0;
  }

  public boolean isLeaf(Object node) {
    File file = (File) node;
    return file.isFile();
  }

  public int getIndexOfChild(Object parent, Object child) {
    File directory = (File) parent;
    File file = (File) child;
    String[] children = directory.list();
    for (int i = 0; i < children.length; i++) {
      if (file.getName().equals(children[i])) {
        return i;
      }
    }
    return -1;

  }

  public void valueForPathChanged(TreePath path, Object value) {
    File oldFile = (File) path.getLastPathComponent();
    String fileParentPath = oldFile.getParent();
    String newFileName = (String) value;
    File targetFile = new File(fileParentPath, newFileName);
    oldFile.renameTo(targetFile);
    File parent = new File(fileParentPath);
    int[] changedChildrenIndices = { getIndexOfChild(parent, targetFile) };
    Object[] changedChildren = { targetFile };
    fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);

  }

  private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
    TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
    Iterator iterator = listeners.iterator();
    TreeModelListener listener = null;
    while (iterator.hasNext()) {
      listener = (TreeModelListener) iterator.next();
      listener.treeNodesChanged(event);
    }
  }

  public void addTreeModelListener(TreeModelListener listener) {
    listeners.add(listener);
  }

  public void removeTreeModelListener(TreeModelListener listener) {
    listeners.remove(listener);
  }

  private class TreeFile extends File {
    public TreeFile(File parent, String child) {
      super(parent, child);
    }

    public String toString() {
      return getName();
    }
  }
}