/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DataViewer.java
 *
 * Created on Oct 24, 2009, 2:38:19 PM
 */
package wekinator;

import wekinator.LearningSystem;
import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author rebecca
 */
public class PlayalongScoreViewer extends javax.swing.JFrame {

    PlayalongScore score = null;
    MyRenderer renderer = null;

    public PlayalongScoreViewer(PlayalongScore score) {
        initComponents();
        renderer = new MyRenderer();
        populateTable(score);
        try {
            table.setDefaultRenderer(Class.forName("java.lang.Double"), renderer);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlayalongScoreViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.score = score;
        score.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                scorePropertyChange(evt);
            }
        });
      //  this.gui = gui;
    }

    private void scorePropertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PlayalongScore.PROP_ISPLAYING)) {
            System.out.println("received playing change event: " + score.getIsPlaying());
            //table.setEnabled(!score.getIsPlaying());
          /*  this.setEnabled(!score.getIsPlaying());
            table.setEnabled(!score.getIsPlaying());
            buttonAdd.setEnabled(!score.getIsPlaying()); */
            //  model.colorSpecial(-1);
            if (!score.isPlaying()) {
                renderer.setSpecialRow(-1);
                table.repaint();
            }

        } else if (evt.getPropertyName().equals(PlayalongScore.PROP_PLAYINGROW)) {
            System.out.println("Playing row " + score.getPlayingRow());
            renderer.setSpecialRow(score.getPlayingRow());
            table.repaint();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollTable = new javax.swing.JScrollPane();
        buttonDone = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        buttonAdd = new javax.swing.JButton();
        buttonListen = new javax.swing.JButton();
        buttonMoveUp = new javax.swing.JButton();
        buttonMoveDown = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        buttonDone.setText("Done");
        buttonDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDoneActionPerformed(evt);
            }
        });

        buttonDelete.setText("Delete selected");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        buttonAdd.setText("Add row");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        buttonListen.setText("Listen");
        buttonListen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonListenActionPerformed(evt);
            }
        });

        buttonMoveUp.setText("move up");
        buttonMoveUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMoveUpActionPerformed(evt);
            }
        });

        buttonMoveDown.setText("move down");
        buttonMoveDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMoveDownActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(buttonDelete)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(buttonAdd)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(buttonListen)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 325, Short.MAX_VALUE)
                .add(buttonDone))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(buttonMoveUp, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(buttonMoveDown, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scrollTable, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 595, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(156, 156, 156)
                        .add(buttonMoveUp)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(buttonMoveDown))
                    .add(scrollTable, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(buttonDone)
                    .add(buttonDelete)
                    .add(buttonAdd)
                    .add(buttonListen)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDoneActionPerformed
        this.dispose();

    }//GEN-LAST:event_buttonDoneActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
        model.deleteRows(table.getSelectedRows());
        table.repaint();
}//GEN-LAST:event_buttonDeleteActionPerformed

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        model.addRow();
}//GEN-LAST:event_buttonAddActionPerformed

    private void buttonListenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonListenActionPerformed
        if (table.getSelectedRow() == -1) {
            return;
        }

        int row = table.getSelectedRow();
        double d[] = model.getSelectedParams(row);
        for (int i = 0; i < d.length; i++) {
            Double dd = new Double(d[i]);
            if (dd.isNaN()) {
               // d[i] = gui.getCurrentParamValue(i);
                d[i] = WekinatorLearningManager.getInstance().getParams(i);
            }
        }
        //hack for now
     /*   float[] f = new float[d.length];
        for (int i = 0; i < d.length; i++) {
            f[i] = (float) d[i];
        } */
        //gui.listenToValues(f);
        WekinatorLearningManager.getInstance().setParams(d);
                //And play:
        OscHandler.getOscHandler().startSound();
        OscHandler.getOscHandler().sendParamsToSynth(d);

}//GEN-LAST:event_buttonListenActionPerformed

    private void buttonMoveUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMoveUpActionPerformed
        if (table.getSelectedRow() == -1 || table.getSelectedRow() == 0) {
            return;
        }
        int row = table.getSelectedRow();
        model.moveUp(row);
        table.getSelectionModel().setSelectionInterval(row - 1, row - 1);

}//GEN-LAST:event_buttonMoveUpActionPerformed

    private void buttonMoveDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMoveDownActionPerformed
        if (table.getSelectedRow() == -1 || table.getSelectedRow() == model.getRowCount() - 1) {
            return;
        }
        int row = table.getSelectedRow();
        model.moveDown(row);
        table.getSelectionModel().setSelectionInterval(row + 1, row + 1);
    }//GEN-LAST:event_buttonMoveDownActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                PlayalongScore ps = new PlayalongScore(3);
                double[] d = {1, 2, 3};
                double[] d2 = {2, 3, 4};
                ps.addParams(d, 2);
                ps.addParams(d2, 3);
                new PlayalongScoreViewer(ps).setVisible(true);
                /*   try {
                // Thread.sleep(1000);
                //       new DataViewer().setVisible(true);
                } catch (InterruptedException ex) {
                Logger.getLogger(PlayalongScoreViewer.class.getName()).log(Level.SEVERE, null, ex);
                } */

                ps.play();


            //       new DataViewer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonDelete;
    private javax.swing.JButton buttonDone;
    private javax.swing.JButton buttonListen;
    private javax.swing.JButton buttonMoveDown;
    private javax.swing.JButton buttonMoveUp;
    private javax.swing.JScrollPane scrollTable;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JTable table;
    private ScoreTableModel model;
  //  private MainGUI gui;

    private void populateTable(PlayalongScore score) {
        model = new ScoreTableModel(score);
        table = new JTable(model);
        scrollTable.setViewportView(table);
        table.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                tablePropertyChanged(evt);
            }
        });



    }

    private void tablePropertyChanged(PropertyChangeEvent evt) {
        System.out.println("Table prop change " + evt.getPropertyName());

    }
}

class MyRenderer extends DefaultTableCellRenderer {

    int specialRow = -1;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof Double) {
            if (row != specialRow) {
                if (isSelected) {
                    cell.setBackground(Color.BLUE);
                    cell.setForeground(Color.WHITE);
                } else {
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }
            } else {
                cell.setBackground(Color.RED);
                cell.setForeground(Color.WHITE);

            }

        /*           if (row == specialRow) {

        cell.setBackground(Color.red);


        } else {
        cell.setBackground(Color.white);
        if (isSelected) {
        cell.setForeground(Color.black);
        }
        }

        } */
        }
        return cell;
    }

    public void setSpecialRow(int r) {
        specialRow = r;
    }
}

class ScoreTableModel extends AbstractTableModel {

    private String[] columnNames;
    //  private Object[][] data;
    int numParams;
    PlayalongScore score;
    boolean[] isDiscrete = null;
    int[] maxValues = null;

    //TODO: this should be responsive to learningSystem changes!!
    public ScoreTableModel(PlayalongScore score) {
        this.score = score;
        score.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                fireTableDataChanged();
            }
        });
        this.numParams = score.getNumParams();
        LearningSystem ls = WekinatorInstance.getWekinatorInstance().getLearningSystem();
        SimpleDataset ds = null;
        if (ls != null) {
            ds = ls.getDataset();
        }

        columnNames = new String[numParams + 2];

        for (int i = 0; i < numParams; i++) {
            if (ds != null) {
                columnNames[i] = ds.getParameterName(i);
            } else {
                columnNames[i] = "Param" + i;
            }
        }
        columnNames[numParams] = "Seconds";
        columnNames[numParams + 1] = "Smooth";


        isDiscrete = new boolean[numParams];
        maxValues = new int[numParams];
        if (ds != null) {
            for (int i = 0; i < numParams; i++) {
                isDiscrete[i] = ds.isParameterDiscrete(i);
                maxValues[i] = ds.getMaxLegalDiscreteParamValues()[i];
            }
        }

    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return score.getScoreLength();
    }

    public void addRow() {


        double[] params = new double[numParams];
        double seconds = 1;

        score.addParams(params, seconds);
        int row = score.getScoreLength();

        fireTableRowsInserted(row, row);

    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        if (row < 0 || row >= score.getScoreLength()) {
            return null;
        }
        if (col < numParams) {
            return score.getParamsAt(row)[col];
        } else if (col == numParams) {
            return score.getSecondsAt(row);
        } else {
            return score.getSmoothAt(row);

        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        //Check it's legal:
        double val = 0.;
        if (value instanceof Double) {
            val = ((Double) value).doubleValue();
        } else if (value instanceof Integer) {
            val = ((Integer) value).intValue();
        }

        if (col < numParams) {
            if (isDiscrete[col]) {
                val = (int) val;
                if (val > maxValues[col]) {
                    return;
                }
            }
        } else if (col == numParams) {
            if (val < 0) {
                return;
            }
        }


        if (col < numParams) {
            score.setParamsAt(row, col, val);
        } else if (col == numParams) {
            score.setSecondsAt(row, val);
        } else {
            Boolean v = (Boolean) value;
            score.setSmoothAt(row, v);

        }
        fireTableCellUpdated(row, col);
    }

    void colorSpecial(int playingRow) {
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    void deleteRows(int[] selectedRows) {
        for (int j = selectedRows.length - 1; j >= 0; j--) {
            score.removeAt(selectedRows[j]);
            fireTableRowsDeleted(selectedRows[j], selectedRows[j]);
        }
    }

    double[] getSelectedParams(int row) {
        double d[] = score.getParamsAt(row);
        return d;
    }

    double getSelectedSeconds(int row) {
        return score.getSecondsAt(row);
    }

    void moveDown(int row) {
        if (row >= score.getScoreLength()) {
            return;
        }
        swap(row, row + 1);
    }

    void moveUp(int row) {
        if (row <= 0) {
            return;
        }
        swap(row, row - 1);
    }

    void swap(int row1, int row2) {
        double tmp1[] = score.getParamsAt(row1);
        double s = score.getSecondsAt(row1);
        boolean sm = score.getSmoothAt(row1);
        //  double d[] = new double[numParams];
        //  for (int i = 0; i < numParams; i++) {
        //      d[i] = tmp1[i];
        //  }

        double tmp2[] = score.getParamsAt(row2);
        double s2 = score.getSecondsAt(row2);
        boolean sm2 = score.getSmoothAt(row2);
        //     double d2[] = new double[numParams];
        //     for (int i = 0; i < numParams; i++) {
        //         d2[i] = tmp2[i];
        //     }

        score.setParamsAt(row1, tmp2);
        score.setSecondsAt(row1, s2);
        score.setSmoothAt(row1, sm2);
        fireTableRowsUpdated(row1, row1);

        score.setParamsAt(row2, tmp1);
        score.setSecondsAt(row2, s);
        score.setSmoothAt(row2, sm);

        fireTableRowsUpdated(row2, row2);

    }
}

