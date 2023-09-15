/*
 * Created by JFormDesigner on Thu Sep 14 15:34:37 CDT 2023
 */

package tools.cacheditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;

/**
 * @author alber
 */
public class CacheEditor extends JFrame {
    public CacheEditor() {
        initComponents();
    }

    private void addFolderItem(ActionEvent e) {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Albert Beuapre
        menuBar1 = new JMenuBar();
        fileMenu = new JMenu();
        openMenuItem = new JMenuItem();
        saveMenuItem = new JMenuItem();
        saveAsMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        editMenu = new JMenu();
        addFolderItem = new JMenuItem();
        removeFolderItem = new JMenuItem();
        addFileItem = new JMenuItem();
        removeFileItem = new JMenuItem();
        helpMenu = new JMenu();
        instructionsItem = new JMenuItem();
        splitPane1 = new JSplitPane();
        cacheFolderScrollPane = new JScrollPane();
        cacheFolderTree = new JTree();
        fileEditorPane = new JPanel();
        fileEditorTabPane = new JTabbedPane();
        bottomToolBar = new JToolBar();
        bottomToolBarPanel = new JPanel();
        progressBar = new JProgressBar();
        topToolBar = new JToolBar();

        //======== this ========
        var contentPane = getContentPane();

        //======== menuBar1 ========
        {

            //======== fileMenu ========
            {
                fileMenu.setText("File");

                //---- openMenuItem ----
                openMenuItem.setText("Open...");
                fileMenu.add(openMenuItem);

                //---- saveMenuItem ----
                saveMenuItem.setText("Save");
                fileMenu.add(saveMenuItem);

                //---- saveAsMenuItem ----
                saveAsMenuItem.setText("Save As...");
                fileMenu.add(saveAsMenuItem);

                //---- exitMenuItem ----
                exitMenuItem.setText("Exit");
                fileMenu.add(exitMenuItem);
            }
            menuBar1.add(fileMenu);

            //======== editMenu ========
            {
                editMenu.setText("Edit");

                //---- addFolderItem ----
                addFolderItem.setText("Add Folder");
                addFolderItem.addActionListener(e -> addFolderItem(e));
                editMenu.add(addFolderItem);

                //---- removeFolderItem ----
                removeFolderItem.setText("Remove Folder");
                editMenu.add(removeFolderItem);

                //---- addFileItem ----
                addFileItem.setText("Add File");
                editMenu.add(addFileItem);

                //---- removeFileItem ----
                removeFileItem.setText("Remove File");
                editMenu.add(removeFileItem);
            }
            menuBar1.add(editMenu);

            //======== helpMenu ========
            {
                helpMenu.setText("Help");

                //---- instructionsItem ----
                instructionsItem.setText("Instructions");
                helpMenu.add(instructionsItem);
            }
            menuBar1.add(helpMenu);
        }
        setJMenuBar(menuBar1);

        //======== splitPane1 ========
        {
            splitPane1.setResizeWeight(0.3);
            splitPane1.setFocusable(false);
            splitPane1.setOpaque(false);
            splitPane1.setDividerLocation(150);

            //======== cacheFolderScrollPane ========
            {
                cacheFolderScrollPane.setFocusable(false);
                cacheFolderScrollPane.setOpaque(false);

                //---- cacheFolderTree ----
                cacheFolderTree.setBorder(new TitledBorder(null, "Cache Folders", TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
                cacheFolderTree.setMinimumSize(new Dimension(100, 0));
                cacheFolderTree.setPreferredSize(new Dimension(100, 96));
                cacheFolderTree.setMaximumSize(null);
                cacheFolderTree.setOpaque(false);
                cacheFolderTree.setFocusable(false);
                cacheFolderScrollPane.setViewportView(cacheFolderTree);
            }
            splitPane1.setLeftComponent(cacheFolderScrollPane);

            //======== fileEditorPane ========
            {
                fileEditorPane.setBorder(null);
                fileEditorPane.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .EmptyBorder
                ( 0, 0 ,0 , 0) ,  "JF\u006frmDes\u0069gner \u0045valua\u0074ion" , javax. swing .border . TitledBorder. CENTER ,javax . swing. border
                .TitledBorder . BOTTOM, new java. awt .Font ( "D\u0069alog", java .awt . Font. BOLD ,12 ) ,java . awt
                . Color .red ) ,fileEditorPane. getBorder () ) ); fileEditorPane. addPropertyChangeListener( new java. beans .PropertyChangeListener ( ){ @Override public void
                propertyChange (java . beans. PropertyChangeEvent e) { if( "\u0062order" .equals ( e. getPropertyName () ) )throw new RuntimeException( )
                ;} } );

                GroupLayout fileEditorPaneLayout = new GroupLayout(fileEditorPane);
                fileEditorPane.setLayout(fileEditorPaneLayout);
                fileEditorPaneLayout.setHorizontalGroup(
                    fileEditorPaneLayout.createParallelGroup()
                        .addComponent(fileEditorTabPane, GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
                );
                fileEditorPaneLayout.setVerticalGroup(
                    fileEditorPaneLayout.createParallelGroup()
                        .addComponent(fileEditorTabPane, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
                );
            }
            splitPane1.setRightComponent(fileEditorPane);
        }

        //======== bottomToolBar ========
        {

            //======== bottomToolBarPanel ========
            {

                GroupLayout bottomToolBarPanelLayout = new GroupLayout(bottomToolBarPanel);
                bottomToolBarPanel.setLayout(bottomToolBarPanelLayout);
                bottomToolBarPanelLayout.setHorizontalGroup(
                    bottomToolBarPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, bottomToolBarPanelLayout.createSequentialGroup()
                            .addContainerGap(829, Short.MAX_VALUE)
                            .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                );
                bottomToolBarPanelLayout.setVerticalGroup(
                    bottomToolBarPanelLayout.createParallelGroup()
                        .addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                );
            }
            bottomToolBar.add(bottomToolBarPanel);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(topToolBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bottomToolBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(splitPane1, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(topToolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(splitPane1)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(bottomToolBar, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Albert Beuapre
    private JMenuBar menuBar1;
    private JMenu fileMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu editMenu;
    private JMenuItem addFolderItem;
    private JMenuItem removeFolderItem;
    private JMenuItem addFileItem;
    private JMenuItem removeFileItem;
    private JMenu helpMenu;
    private JMenuItem instructionsItem;
    private JSplitPane splitPane1;
    private JScrollPane cacheFolderScrollPane;
    private JTree cacheFolderTree;
    private JPanel fileEditorPane;
    private JTabbedPane fileEditorTabPane;
    private JToolBar bottomToolBar;
    private JPanel bottomToolBarPanel;
    private JProgressBar progressBar;
    private JToolBar topToolBar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
