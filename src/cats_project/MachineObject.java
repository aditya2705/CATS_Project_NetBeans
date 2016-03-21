/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cats_project;

import java.awt.Canvas;
import javax.swing.JPanel;

/**
 *
 * @author Aditya Rathi
 */
public class MachineObject {
    
    private String machineName, machineAddress;
    private Canvas videoCanvas = new Canvas();
    private JPanel videoPanel = new JPanel();

    public MachineObject(String machineName, String machineAddress) {
        this.machineName = machineName;
        this.machineAddress = machineAddress;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMachineAddress() {
        return machineAddress;
    }

    public void setMachineAddress(String machineAddress) {
        this.machineAddress = machineAddress;
    }

    public Canvas getVideoCanvas() {
        return videoCanvas;
    }

    public void setVideoCanvas(Canvas videoCanvas) {
        this.videoCanvas = videoCanvas;
    }

    public JPanel getVideoPanel() {
        return videoPanel;
    }

    public void setVideoPanel(JPanel videoPanel) {
        this.videoPanel = videoPanel;
    }
    
    
    
    
    
    
}
