/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basicgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class FarmGui {

    ArrayList<Farm> farmlist = new ArrayList<Farm>();
    String header[] = new String[]{"Id", "Breed Type", "Weight"};
    DefaultTableModel dtm = new DefaultTableModel(header, 1);

    FarmGui() {

        JFrame frame = new JFrame("Farm Database System");
        frame.setSize(300, 450);

        JLabel jlabel = new JLabel("ID");
        jlabel.setBounds(10, 10, 60, 20);
        frame.add(jlabel);

        JLabel jlabela = new JLabel("BREED TYPE");
        jlabela.setBounds(10, 30, 60, 20);
        frame.add(jlabela);

        JLabel jlabelb = new JLabel("WEIGHT");
        jlabelb.setBounds(10, 50, 60, 20);
        frame.add(jlabelb);

        JTextField jtfid = new JTextField();
        jtfid.setBounds(80, 10, 200, 20);
        frame.add(jtfid);

        JTextField jtfbreed = new JTextField();
        jtfbreed.setBounds(80, 30, 100, 20);
        frame.add(jtfbreed);

        JTextField jtfweight = new JTextField();
        jtfweight.setBounds(80, 50, 150, 20);
        frame.add(jtfweight);

        JButton jbuttoninsert = new JButton("INSERT");
        jbuttoninsert.setBounds(80, 80, 90, 20);
        frame.add(jbuttoninsert);

        

        JButton jbuttonexit = new JButton("EXIT");
        jbuttonexit.setBounds(180, 390, 90, 20);
        frame.add(jbuttonexit);

        //table creation
        JTable jtable = new JTable();
        jtable.setBounds(20, 140, 250, 250);
        frame.add(jtable);
        jtable.setModel(dtm);
        JScrollPane scrollPane = new JScrollPane(jtable);
        scrollPane.setBounds(20, 140, 250, 250);
        frame.add(scrollPane);
        jtable.getColumnModel().getColumn(0).setPreferredWidth(70);
        jtable.getColumnModel().getColumn(1).setPreferredWidth(150);
        jtable.getColumnModel().getColumn(2).setPreferredWidth(100);

        jbuttoninsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               
                String id = jtfid.getText();
                String breedtype = jtfbreed.getText().toUpperCase();
                String weight = jtfweight.getText();
               
                if (!isInteger(weight)) {
                    JOptionPane.showMessageDialog(frame, "Please Insert Number Only");
                    return;
                }

                Farm farmers = new Farm(id, breedtype,weight);
                farmlist.add(farmers);//create object list array
                writeData();
            }
        });

       
        
      
        jbuttonexit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                frame.setVisible(false); //you can't see me!
                frame.dispose();
            }
        });

        readData();
        jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jtable.getSelectedRow();
                jtfid.setText(dtm.getValueAt(row, 0).toString());
                jtfbreed.setText(dtm.getValueAt(row, 1).toString());
                jtfweight.setText(dtm.getValueAt(row, 2).toString());
            }
        });

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FarmGui basicgui = new FarmGui();

    }

    void readData() { //read data from "data.txt" and display on table
        try {
            File file = new File("data.txt"); //create file
            file.createNewFile();//if not exit
            FileReader f = new FileReader("data.txt");
            StringBuffer sb = new StringBuffer();
            while (f.ready()) {
                char c = (char) f.read();
                if (c == '-') {
                    System.out.println(sb);
                    String farmarray[] = sb.toString().split(",");
                    Farm farmers = new Farm(farmarray[0], farmarray[1], farmarray[2]);
                    farmlist.add(farmers);
                    sb = new StringBuffer();
                } else {
                    sb.append(c);
                }
            }
            dtm.setRowCount(0); //update table
            for (int i = 0; i < farmlist.size(); i++) {//populate table using object list
                Object[] objs = {farmlist.get(i).getId(), farmlist.get(i).getBreed(), farmlist.get(i).getWeight()};
                dtm.addRow(objs);
            }
        } catch (IOException e) {
        }
    }

    private void writeData() { //write data to file "data.txt"
        try (FileWriter f = new FileWriter("data.txt")) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < farmlist.size(); i++) {
                sb.append(farmlist.get(i).getId()+ "," + farmlist.get(i).getBreed()+ "," + farmlist.get(i).getWeight()+ "-");
            }
            f.write(sb.toString());
            f.close();
        } catch (IOException e) {
            return;
        }
        dtm.setRowCount(0); //update table content
        for (int i = 0; i < farmlist.size(); i++) {//populate table using object list
            Object[] objs = {farmlist.get(i).getId(), farmlist.get(i).getBreed(), farmlist.get(i).getWeight()};
            dtm.addRow(objs);
        }
    }

    public boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
