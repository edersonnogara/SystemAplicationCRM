//Classe que habilita a abertura de uma tela apenas.
package systeminfo.dal;

import java.beans.PropertyVetoException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;


public class Gerentedetelas {
    private static JDesktopPane Desktop;
    
    public Gerentedetelas(JDesktopPane Desktop) {
        Gerentedetelas.Desktop = Desktop;
    }
    
    public void abrirjanelas(JInternalFrame jInternalFrame){
        if(jInternalFrame.isVisible()){
        jInternalFrame.toFront();
        //jInternalFrame.getFocusOwner();
        jInternalFrame.requestFocus();
        
        try{
            jInternalFrame.setSelected(true);
            
        }catch (PropertyVetoException ex){
            
             Logger.getLogger(Gerentedetelas.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else{
            Desktop.add(jInternalFrame);
            jInternalFrame.setVisible(true);
        }
    }
}
