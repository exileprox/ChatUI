/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import g53sqm.chat.client.ClientListener;
import g53sqm.chat.client.servercommand.IdentityCommand;
import g53sqm.chat.client.servermessage.Message;
import g53sqm.chat.client.servermessage.ResultCode;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Chris
 */
public class RegisterPanel extends javax.swing.JPanel {

    private ChatClientMainFrame frameRef;
    private SessionObject sessionObj;
    private static final Color BAD_COLOR = Color.RED;
    private static final Color OK_COLOR = Color.GREEN;
    private static final Color UNKNOWN_COLOR = Color.GRAY;
    private boolean shouldNext = false;
    /**
     * Creates new form RegisterPanel
     */
    public RegisterPanel(ChatClientMainFrame frameRef, SessionObject sessionObj) {
        initComponents();
        this.frameRef = frameRef;
        this.sessionObj = sessionObj;
        sessionObj.getClient().addListener(new ClientListener() {

            @Override
            public void onServerMessageReceived(Message message) {
                if (message.getResultCode() == ResultCode.OK) {
                    updateStatusText(message.getMessage(), OK_COLOR);
                    if (shouldNext && message.getMessage().startsWith("Welcome to the chat server")) {
                        nextPage();
                    }
                } else if (message.getResultCode() == ResultCode.BAD) {
                    updateStatusText(message.getMessage(), BAD_COLOR);
                } else {
                    updateStatusText(message.getMessage(), UNKNOWN_COLOR);
                }
            }
        });
    }

    private void nextPage() {
        frameRef.setPanel(new ChatPanel(frameRef, SessionObject.getInstance()));
    }

    private void updateStatusText(String status, Color c) {
        final String statusTxt = status;
        final Color color = c;
        Thread updateTextThread = new Thread(new Runnable() {
            public void run() {
                synchronizedStatusUpdate(statusTxt, color);
            }
        });
        updateTextThread.start();
    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    private synchronized void synchronizedStatusUpdate(String status, Color c) {
        appendToPane(jTextPaneStatus, status + "\n", c);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldName = new javax.swing.JTextField();
        jLabelName = new javax.swing.JLabel();
        jScrollPaneStatus = new javax.swing.JScrollPane();
        jTextPaneStatus = new javax.swing.JTextPane();
        jButtonRegister = new javax.swing.JButton();

        jTextFieldName.setName("jTextFieldName"); // NOI18N

        jLabelName.setText("Name:");

        jTextPaneStatus.setBackground(new java.awt.Color(204, 204, 204));
        jTextPaneStatus.setFocusable(false);
        jTextPaneStatus.setName("jTextPaneStatus"); // NOI18N
        jScrollPaneStatus.setViewportView(jTextPaneStatus);

        jButtonRegister.setText("Register");
        jButtonRegister.setToolTipText("");
        jButtonRegister.setName("jButtonRegister"); // NOI18N
        jButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegisterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabelName, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldName))
                    .addComponent(jScrollPaneStatus)
                    .addComponent(jButtonRegister, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonRegister)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegisterActionPerformed
        shouldNext=true;
        SessionObject.getInstance().getClient().sendCommandToServer(new IdentityCommand(jTextFieldName.getText()));
    }//GEN-LAST:event_jButtonRegisterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRegister;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JScrollPane jScrollPaneStatus;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextPane jTextPaneStatus;
    // End of variables declaration//GEN-END:variables
}
