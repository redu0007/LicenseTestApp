/*
 * LicenseTestView.java
 */

package licensetestapp;

import gns.license.keys.LicenseKeys;
import gns.license.manager.Feature;
import gns.license.manager.LicenseManager;
import gns.license.software.SoftwareLicenseManager;
import gns.license.utility.ApplicationPaths;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The application's main frame.
 */
public class LicenseTestView extends FrameView {
    private LicenseManager lcm;
    private LicenseKeys lk;
    private ApplicationPaths paths;
    private RunnableThread R1;
    private ArrayList<Integer> featureIds;
    public LicenseTestView(SingleFrameApplication app) {
        super(app);

        initComponents();
        try {
            
            featureIds = new ArrayList<Integer>();
            featureIds.add(1);
            featureIds.add(2);
            featureIds.add(3);
            featureIds.add(4);
            int keyIndex = LicenseData.keyIndex;
            paths = ApplicationPaths.getInstance("LicenseTestApp", "", "", true);
            lk = new LicenseKeys(keyIndex,LicenseData.getAES_A(keyIndex),LicenseData.getAES_B(keyIndex),LicenseData.getRSAPublicPem(keyIndex));
            lcm = LicenseManager.getInstance(paths,featureIds,lk);
            
            if(!lcm.isLicensePresent()){
                if(lcm.getLastHardwareError().isEmpty())
                    throw new Exception(lcm.getLastSoftwareError());
                else 
                    throw new Exception(lcm.getLastHardwareError());
            }

            if(!lcm.areAllFearutesPresent()){
                Feature[] featuresList = lcm.getAllFearutesStatus();
                for (Feature feature : featuresList) {
                    if(feature != null){
                        if( feature.getId() == 1 && feature.getStatus() == false){
                            throw new Exception("Feature not active");
                        }
                    }
                }
            }
            
            R1 = new RunnableThread(lcm);
            R1.start();
           
            jlbLicenseType.setText(lcm.getLicenseType());
            jlbLicenseSerialnumber.setText(lcm.getLicenseSerialNumber());
            jlbLicenseVersion.setText(lcm.getLicenseVersion());
            jlbLicenseManagerVersion.setText(lcm.getLicenseManagerVersion());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
            //System.exit(0);
        }

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = LicenseTestApp.getApplication().getMainFrame();
            aboutBox = new LicenseTestAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        LicenseTestApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jbtnClose = new javax.swing.JButton();
        jbtnStopLicenseChecking = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jlbLicenseType = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jlbLicenseSerialnumber = new javax.swing.JLabel();
        jlbLicenseVersion = new javax.swing.JLabel();
        jlbLicenseManagerVersion = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jmiGenerateClientInfo = new javax.swing.JMenuItem();
        jmiLoadLicense = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(licensetestapp.LicenseTestApp.class).getContext().getResourceMap(LicenseTestView.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jbtnClose.setText(resourceMap.getString("jbtnClose.text")); // NOI18N
        jbtnClose.setName("jbtnClose"); // NOI18N
        jbtnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCloseActionPerformed(evt);
            }
        });

        jbtnStopLicenseChecking.setText(resourceMap.getString("jbtnStopLicenseChecking.text")); // NOI18N
        jbtnStopLicenseChecking.setName("jbtnStopLicenseChecking"); // NOI18N
        jbtnStopLicenseChecking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnStopLicenseCheckingActionPerformed(evt);
            }
        });

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jlbLicenseType.setFont(resourceMap.getFont("jlbLicenseType.font")); // NOI18N
        jlbLicenseType.setText(resourceMap.getString("jlbLicenseType.text")); // NOI18N
        jlbLicenseType.setName("jlbLicenseType"); // NOI18N

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jlbLicenseSerialnumber.setFont(resourceMap.getFont("jlbLicenseSerialnumber.font")); // NOI18N
        jlbLicenseSerialnumber.setText(resourceMap.getString("jlbLicenseSerialnumber.text")); // NOI18N
        jlbLicenseSerialnumber.setName("jlbLicenseSerialnumber"); // NOI18N

        jlbLicenseVersion.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jlbLicenseVersion.setText(resourceMap.getString("jlbLicenseVersion.text")); // NOI18N
        jlbLicenseVersion.setName("jlbLicenseVersion"); // NOI18N

        jlbLicenseManagerVersion.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jlbLicenseManagerVersion.setText(resourceMap.getString("jlbLicenseManagerVersion.text")); // NOI18N
        jlbLicenseManagerVersion.setName("jlbLicenseManagerVersion"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbtnStopLicenseChecking)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnClose))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlbLicenseType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlbLicenseSerialnumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlbLicenseVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlbLicenseManagerVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jlbLicenseType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jlbLicenseSerialnumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jlbLicenseVersion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jlbLicenseManagerVersion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnClose)
                    .addComponent(jbtnStopLicenseChecking))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jmiGenerateClientInfo.setText(resourceMap.getString("jmiGenerateClientInfo.text")); // NOI18N
        jmiGenerateClientInfo.setName("jmiGenerateClientInfo"); // NOI18N
        jmiGenerateClientInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiGenerateClientInfoActionPerformed(evt);
            }
        });
        fileMenu.add(jmiGenerateClientInfo);

        jmiLoadLicense.setText(resourceMap.getString("jmiLoadLicense.text")); // NOI18N
        jmiLoadLicense.setName("jmiLoadLicense"); // NOI18N
        jmiLoadLicense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiLoadLicenseActionPerformed(evt);
            }
        });
        fileMenu.add(jmiLoadLicense);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(licensetestapp.LicenseTestApp.class).getContext().getActionMap(LicenseTestView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 230, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCloseActionPerformed
       
        if(lcm != null)
           lcm.stopLicenseChecking();
        
        if(R1 != null)
            R1.Stop();
        
        System.exit(0);
    }//GEN-LAST:event_jbtnCloseActionPerformed

    private void jbtnStopLicenseCheckingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnStopLicenseCheckingActionPerformed
        
        if(lcm != null)
           lcm.stopLicenseChecking();
        
        if(R1 != null)
            R1.Stop();
    }//GEN-LAST:event_jbtnStopLicenseCheckingActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        if(lcm != null)
           lcm.stopLicenseChecking();
        
        if(R1 != null)
            R1.Stop();
        
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void jmiGenerateClientInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiGenerateClientInfoActionPerformed
        GenerateClientInfo info = new GenerateClientInfo(lk);
        info.setVisible(true);
    }//GEN-LAST:event_jmiGenerateClientInfoActionPerformed

    private void jmiLoadLicenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiLoadLicenseActionPerformed
        try {
            
            String currentPath = SoftwareLicenseManager.getFilePath(getFrame(),"", "act");
            if(currentPath.isEmpty()) throw new Exception("Load license path is empty");

            SoftwareLicenseManager.loadClientLicense(currentPath, paths);
            if(!lcm.isLicensePresent()){
                SoftwareLicenseManager.deleteLicense(paths);
                throw new Exception(lcm.getLastError());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(getFrame(), ex.getMessage());
        }
    }//GEN-LAST:event_jmiLoadLicenseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JButton jbtnClose;
    private javax.swing.JButton jbtnStopLicenseChecking;
    private javax.swing.JLabel jlbLicenseManagerVersion;
    private javax.swing.JLabel jlbLicenseSerialnumber;
    private javax.swing.JLabel jlbLicenseType;
    private javax.swing.JLabel jlbLicenseVersion;
    private javax.swing.JMenuItem jmiGenerateClientInfo;
    private javax.swing.JMenuItem jmiLoadLicense;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
