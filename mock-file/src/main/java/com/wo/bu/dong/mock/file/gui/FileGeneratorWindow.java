package com.wo.bu.dong.mock.file.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.jdesktop.swingx.JXDatePicker;

import com.wo.bu.dong.mock.file.enums.FileNamePrefixEnum;
import com.wo.bu.dong.mock.file.generator.RSACSVZipFileUtils;

public class FileGeneratorWindow {

    private JFrame                        frmSunny;
    private JTextField                    tf_serialNum;
    private JTextField                    tf_count;
    private JComboBox<FileNamePrefixEnum> cb_fileType;
    private JLabel                        label;
    private JButton                       button;
    private JXDatePicker                  tf_dataDt;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FileGeneratorWindow window = new FileGeneratorWindow();
                    window.frmSunny.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public FileGeneratorWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmSunny = new JFrame();
        frmSunny.setTitle("文件生成-Sunny");
        frmSunny.setBounds(100, 100, 335, 307);
        frmSunny.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmSunny.getContentPane().setLayout(null);
        frmSunny.setLocationRelativeTo(null);//设置窗口居中

        cb_fileType = new JComboBox<>();
        cb_fileType.setModel(new DefaultComboBoxModel<FileNamePrefixEnum>(FileNamePrefixEnum.values()));
        cb_fileType.setToolTipText("");
        cb_fileType.setBounds(80, 10, 100, 21);
        frmSunny.getContentPane().add(cb_fileType);

        JLabel lblNewLabel = new JLabel("文件类型：");
        lblNewLabel.setBounds(10, 13, 100, 15);
        frmSunny.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("数据日期：");
        lblNewLabel_1.setBounds(10, 48, 100, 15);
        frmSunny.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("文件编号：");
        lblNewLabel_2.setBounds(10, 85, 100, 15);
        frmSunny.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("记录笔数：");
        lblNewLabel_3.setBounds(10, 123, 100, 15);
        frmSunny.getContentPane().add(lblNewLabel_3);

        tf_serialNum = new JTextField();
        tf_serialNum.setBounds(80, 82, 100, 21);
        frmSunny.getContentPane().add(tf_serialNum);
        tf_serialNum.setColumns(10);

        tf_dataDt = new JXDatePicker(new Date());
        tf_dataDt.setBounds(80, 45, 100, 21);
        tf_dataDt.setFormats("yyyyMMdd");
        tf_dataDt.getEditor().setEditable(false);
        frmSunny.getContentPane().add(tf_dataDt);

        tf_count = new JTextField();
        tf_count.setColumns(10);
        tf_count.setBounds(80, 120, 100, 21);
        frmSunny.getContentPane().add(tf_count);

        button = new JButton("开始生成");
        button.setToolTipText("");
        button.setForeground(new Color(0, 0, 0));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileNamePrefixEnum fileType = cb_fileType.getItemAt(cb_fileType.getSelectedIndex());
                String dataDt = DateFormatUtils.format(tf_dataDt.getDate(), "yyyyMMdd");
                String serialNum = tf_serialNum.getText();
                String count = tf_count.getText();
                //文本值校验
                if (!dataDt.matches("\\d{8}") || !serialNum.matches("00[1-9]{1}") || !count.matches("[1-9]\\d*")) {
                    JOptionPane.showMessageDialog(frmSunny, "参数错误", "提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //禁用按钮
                button.setEnabled(false);
                //生成文件
                try {
                    switch (fileType) {
                        case LOAN_USER:
                            RSACSVZipFileUtils.generateCustFile(Integer.valueOf(count), dataDt, serialNum);
                            break;
                        case LOAN:
                            RSACSVZipFileUtils.generateLnFile(Integer.valueOf(count), dataDt, serialNum);
                            break;
                        case LOAN_STATISTICS:
                            RSACSVZipFileUtils.generateStsFile(Integer.valueOf(count), dataDt, serialNum);
                            break;
                        case REPAY_PLAN:
                            RSACSVZipFileUtils.generateRptmSchdFile(Integer.valueOf(count), dataDt, serialNum);
                            break;
                        case INTEREST_INT_CT:
                            RSACSVZipFileUtils.generateIntCtFile(Integer.valueOf(count), dataDt, serialNum);
                            break;
                        case REPAY:
                            RSACSVZipFileUtils.generateRptmFile(Integer.valueOf(count), dataDt, serialNum);
                            break;
                        case ACCOUNT_BALANCE_DIR:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    System.out.println(e2.getMessage());
                }
                //启用按钮
                button.setEnabled(true);
            }
        });
        button.setBounds(80, 188, 93, 23);
        frmSunny.getContentPane().add(button);

        label = new JLabel("格式（001,002,.....）");
        label.setForeground(Color.RED);
        label.setBounds(190, 85, 132, 15);
        frmSunny.getContentPane().add(label);

    }
}
