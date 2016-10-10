import akka.actor.ActorRef;
import job.Job;
import job.TraceGeneratorJobPerf;
import messages.CreateWorker;
import messages.ExecuteJob;
import messages.KillWorker;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jaco on 10/5/16.
 *
 */
@SuppressWarnings("Since15")
public class MasterUI {

    private JFrame frame;
    private ActorRef master;
    private JTable table;
    private int numWorkers;


    public MasterUI(ActorRef master) {
        this.master = master;
        createComponents();

        this.frame.setVisible(true);
    }

    private void createComponents() {
        this.frame = new JFrame("Master");
        int width = 600;
        int height = 600;
        this.frame.setSize(width, height);
        this.frame.setResizable(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (UnsupportedLookAndFeelException e) {
        }

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//TODO: remove this.
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        this.frame.add(panel);

        JButton addWorkerButton = new JButton("Add perf Tracegenerator worker");
        GridBagConstraints gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 0, 0, new Insets(0, 10, 10, 10));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(addWorkerButton, gbc);

        addWorkerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAddWorkerFrame();
            }
        });

        NonEditableModel tableModel = new NonEditableModel();
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        tableModel.addColumn("ID");
        tableModel.addColumn("Command");

        this.table = new JTable(tableModel);
        this.table.setEnabled(true);
        this.table.getColumnModel().getColumn(0).setMaxWidth(100);
        this.table.getColumnModel().getColumn(0).setMinWidth(50);
        this.table.getColumnModel().getColumn(1).setMinWidth(width-100);

        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setBorder(new LineBorder(Color.BLACK));
        setGridBagConstants(gbc, 5, 0, 1, new Insets(0, 10, 0, 10));

        panel.add(scrollPane, gbc);
    }

    private void setGridBagConstants(GridBagConstraints gbc, int width, int gridx, int gridy, Insets insets) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = width;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = insets;
        gbc.weightx = 1;
    }

    private void createAddWorkerFrame() {
        JFrame addWorkerFrame = new JFrame("Add a worker");
        addWorkerFrame.setResizable(false);
        JPanel addWorkerPanel = new JPanel(new BorderLayout(5,5));

        JPanel fields = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 0, 0, new Insets(0, 10, 0, 0));
        fields.add(new JLabel("N:"), gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 0, 1, new Insets(0, 10, 0, 0));
        fields.add(new JLabel("Q:"), gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 0, 2, new Insets(0, 10, 0, 0));
        fields.add(new JLabel("P:"), gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 0, 3, new Insets(0, 10, 0, 0));
        fields.add(new JLabel("B:"), gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 0, 4, new Insets(0, 10, 0, 0));
        fields.add(new JLabel("Worker type:"), gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 0, 5, new Insets(0, 10, 0, 0));
        fields.add(new JLabel("Task type:"), gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 0, 6, new Insets(0, 10, 0, 0));
        fields.add(new JLabel("Lock type:"), gbc);
        addWorkerPanel.add(fields, BorderLayout.WEST);

        JTextField N = new JTextField();
        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 3, 1, 0, new Insets(5, 0, 0, 10));
        fields.add(N, gbc);

        JTextField Q = new JTextField();
        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 3, 1, 1, new Insets(5, 0, 0, 10));
        fields.add(Q, gbc);

        JTextField P = new JTextField();
        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 3, 1, 2, new Insets(5, 0, 0, 10));
        fields.add(P, gbc);

        JTextField B = new JTextField();
        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 3, 1, 3, new Insets(5, 0, 0, 10));
        fields.add(B, gbc);

        JRadioButton selfish = new JRadioButton("Selfish");
        JRadioButton friendly = new JRadioButton("Friendly");
        JRadioButton crazy = new JRadioButton("Crazy");

        ButtonGroup workerTypes = new ButtonGroup();
        workerTypes.add(selfish);
        workerTypes.add(friendly);
        workerTypes.add(crazy);
        selfish.setSelected(true);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 1, 4, new Insets(5, 0, 0, 10));
        fields.add(selfish, gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 2, 4, new Insets(5, 0, 0, 10));
        fields.add(friendly, gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 3, 4, new Insets(5, 0, 0, 10));
        fields.add(crazy, gbc);

        JRadioButton fib = new JRadioButton("Fibonacci");
        JRadioButton prime = new JRadioButton("Prime");
        JRadioButton sleep = new JRadioButton("Sleep");
        JRadioButton loops = new JRadioButton("Loops");
        JRadioButton mixed = new JRadioButton("Mixed");

        ButtonGroup taskTypes = new ButtonGroup();
        taskTypes.add(fib);
        taskTypes.add(prime);
        taskTypes.add(sleep);
        taskTypes.add(loops);
        taskTypes.add(mixed);
        fib.setSelected(true);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 1, 5, new Insets(5, 0, 0, 10));
        fields.add(fib, gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 2, 5, new Insets(5, 0, 0, 10));
        fields.add(prime, gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 3, 5, new Insets(5, 0, 0, 10));
        fields.add(sleep, gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 4, 5, new Insets(5, 0, 0, 10));
        fields.add(loops, gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 5, 5, new Insets(5, 0, 0, 10));
        fields.add(mixed, gbc);

        JRadioButton singleLock = new JRadioButton("Single");
        JRadioButton doubleLock = new JRadioButton("Double");

        ButtonGroup lockTypes = new ButtonGroup();
        lockTypes.add(singleLock);
        lockTypes.add(doubleLock);
        singleLock.setSelected(true);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 1, 6, new Insets(5, 0, 0, 10));
        fields.add(singleLock, gbc);

        gbc = new GridBagConstraints();
        setGridBagConstants(gbc, 1, 2, 6, new Insets(5, 0, 0, 10));
        fields.add(doubleLock, gbc);

        JOptionPane.showMessageDialog(addWorkerFrame, addWorkerPanel, "Add worker", JOptionPane.QUESTION_MESSAGE);

        if (valid(N) && valid(Q) && valid(P) && valid(B)) {

            int nVal = Integer.parseInt(N.getText());
            int qVal = Integer.parseInt(Q.getText());
            int pVal = Integer.parseInt(P.getText());
            int bVal = Integer.parseInt(B.getText());

            int workerType;
            int taskType;
            int lockType;

            if (selfish.isSelected()) {
                workerType = 0;
            } else if(friendly.isSelected()) {
                workerType = 1;
            } else {
                workerType = 2;
            }

            if (fib.isSelected()) {
                taskType = 0;
            } else if(prime.isSelected()) {
                taskType = 1;
            } else if(sleep.isSelected()) {
                taskType = 2;
            } else if(loops.isSelected()) {
                taskType = 3;
            } else {
                taskType = 4;
            }

            if (singleLock.isSelected()) {
                lockType = 0;
            } else {
                lockType = 1;
            }

            Job job = new TraceGeneratorJobPerf(nVal, qVal, pVal, bVal, workerType, taskType, lockType, numWorkers);

            master.tell(new CreateWorker(numWorkers), ActorRef.noSender());
            master.tell(new ExecuteJob(numWorkers, job), ActorRef.noSender());
            master.tell(new KillWorker(numWorkers), ActorRef.noSender());

            addToTable(job, numWorkers);

            numWorkers++;
        }
    }

    private boolean valid(JTextField input) {
        if (input.getText().trim().equals("") || input.getText() == null) {
            return false;
        }

        try {
            Integer.parseInt(input.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private class NonEditableModel extends DefaultTableModel {

        private static final long serialVersionUID = 1962392150367147079L;

        /**
         * Constructor for the class.
         */
        public NonEditableModel() {}

        /**
         * Makes a row not editable.
         */
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    private void addToTable(Job job, int id) {
        DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        model.addRow(new Object[]{id, job.getParams()});
    }

}
