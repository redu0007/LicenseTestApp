package licensetestapp;

import gns.license.manager.Feature;
import gns.license.manager.LicenseManager;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Rahman Md Redwanur <r.rahman@gne-it.com>
 */
public class RunnableThread implements Runnable {
    private Thread t;
    private final LicenseManager lm;
    private boolean bIsActive;
    
    RunnableThread(LicenseManager lm){
        this.lm = lm;
        System.out.println("Creating thread");
    }
    
    @Override
    public void run() {
        
        System.out.println("Running thread");
        
        try {
            
            
            while( bIsActive ){
                
            
                try{
                    if(!lm.isLicensePresent()){
                            showInSystemTray(lm.getLicenseType(),lm.getLastError());
                            ShowDialogMessage(lm.getLicenseType(),lm.getLastError());
                    }else{
                        
                        Feature[] result = lm.getAllFearutesStatus();

                        for (Feature feature : result) {
                            System.out.println(feature.getId() +" Status : "+feature.getStatus() +" Name: "+feature.getName());

                            if(feature.getId() == 1 && feature.getStatus() == false){

                                showInSystemTray(lm.getLicenseType(),"Featur id 1 not active");
                                ShowDialogMessage(lm.getLicenseType(),"Featur id 1 not active");
                            }
                        }

                        try
                        {
                           Random ran = new Random(System.currentTimeMillis());
                           Thread.sleep(ran.nextInt(10));
                        }catch (InterruptedException ie){
                        }
                    }

                }catch(Exception exp){
                }finally{
                }
            }
            
        } catch (Exception ex) {
            System.out.println("Thread  exception : " +ex.getMessage());
        }
        
        System.out.println("Thread exiting.");
    }

    public void start (){
        System.out.println("Starting thread");
        
        if( !bIsActive ){
            
            if (t == null){
                t = new Thread (this);
                t.start ();
            }
            bIsActive = true;
        }
    }
    
    public void Stop(){

        if( bIsActive ){
            bIsActive = false;
            
            try{
                t.interrupt();
            } catch (SecurityException se){
                System.out.println("Thread  interrupt : " +se.getMessage()); 
            }
        }
        
    }
    
    private void ShowDialogMessage(String sType,String sMessage){
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(false);
        JOptionPane.showMessageDialog(dialog,sMessage,String.format("%s License Error",sType),JOptionPane.ERROR_MESSAGE);  
    }
    
    private void showInSystemTray(String sType,String sMessage)  {
        
        try {

            SystemTray tray = SystemTray.getSystemTray();
            ImageIcon icon;
            
            if(sType.equals(LicenseManager.LICESNE_TYPE_HARDWARE)){
                icon = new ImageIcon(getClass().getResource("hasp_pro.png"));
            }else{
                icon = new ImageIcon(getClass().getResource("license.png"));
            }
            
            Image image = icon.getImage();
            TrayIcon trayIcon = new TrayIcon(image, String.format("%s License Error",sType));
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip(sMessage);
            tray.add(trayIcon);
            trayIcon.displayMessage(sMessage, String.format("%s License Error",sType), TrayIcon.MessageType.ERROR);
            Thread.sleep(4000);
            tray.remove(trayIcon);

            
        } catch (AWTException ex) {
        } catch (InterruptedException ex) {
        }
        
    }

}
