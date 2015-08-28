package au.edu.uq.smartass.web.iapp;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;

import au.edu.uq.smartass.app.PdfView;

public class PdfViewerApplet extends JApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 188841224607240169L;
	PagePanel panel;
	JPanel button_panel;
	JButton next, prev, plus, minus;
	
	JTextArea ta;

	int current_page;
	PDFFile pdffile;
	boolean first_show= true;

	@Override
	public void init() {
		try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
            		button_panel = new JPanel();
            		button_panel.setBackground(new Color(0xffffff));
            		button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.X_AXIS));
            		add(button_panel, BorderLayout.NORTH);
            		JButton bt;
            		button_panel.add(prev = new JButton("Previous"));
            		prev.setPreferredSize(new Dimension(90, 20));
            		prev.addActionListener(new ActionListener() {
            			public void actionPerformed(ActionEvent e) {
            				prevPage();
            			}
            		});
            		button_panel.add(Box.createRigidArea(new Dimension(0,10)));
            		button_panel.add(next = new JButton("Next"));
            		next.setPreferredSize(new Dimension(90, 20));
            		next.addActionListener(new ActionListener() {
            			public void actionPerformed(ActionEvent e) {
            				nextPage();
            			}
            		});
            		button_panel.add(Box.createRigidArea(new Dimension(0,10)));
            		button_panel.add(plus = new JButton("+"));
            		plus.setPreferredSize(new Dimension(90, 20));
            		plus.addActionListener(new ActionListener() {
            			public void actionPerformed(ActionEvent e) {
            				Dimension pd = panel.getCurSize();
            				pd = new Dimension(pd.width*4/3, pd.height*4/3);
            				panel.setPreferredSize(pd);
            				panel.setSize(pd);
            				panel.repaint();
            			}
            		});
            		button_panel.add(Box.createRigidArea(new Dimension(0,10)));
            		button_panel.add(minus = new JButton("-"));
            		minus.setMinimumSize(new Dimension(90, 20));
            		minus.addActionListener(new ActionListener() {
            			public void actionPerformed(ActionEvent e) {
            				Dimension pd = panel.getCurSize();
            				pd = new Dimension(pd.width/4*3, pd.height/4*3);
            				panel.setPreferredSize(pd);
            				panel.setSize(pd);
            				panel.validate();
            			}
            		});
            		
            		panel = new PagePanel();
            		//add(new JScrollPane(panel));
               		ta = new JTextArea();
            		//add(new JScrollPane(ta), BorderLayout.SOUTH);
            		//ta.setText("name="+getParameter("name") + "\nnode="+getParameter("node") + "\nsection=" + getParameter("section"));
            		
            		if(getParameter("name")!=null && getParameter("node")!=null && getParameter("section")!=null)
            			loadNode(getParameter("name"), getParameter("node"), getParameter("section"));
            		//refresh();

            		// show the first page
//            		panel.repaint();
            		panel.setPreferredSize(new Dimension(800, 300));
            		add(new JScrollPane(panel));
//            		Dimension pd = panel.getCurSize();
//            		pd = new Dimension(pd.width*3/2, pd.height*2);
  //          		panel.setPreferredSize(pd);
//            		panel.setSize(pd);

            		setVisible(true);
            		panel.repaint();
            		

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
	            		try {
	            			Thread.sleep(1200);
	            		} catch (InterruptedException e) {}
	            		//panel.repaint();
	            		showPage(0);
	            		//panel.useZoomTool(true);
                        }
                    });
                }
            });
        } catch (Exception e) {
    		ta.setText(e.getStackTrace().toString());
        }
	}
	
	public void loadNode(String fileName, String nodeName, String section) {
		try {
			//ta.append(readPdf(fileName, nodeName, section).toString());
			pdffile = new PDFFile(readPdf(fileName, nodeName, section));
		} catch(Exception e) {}


	}

	public ByteBuffer readPdf(String fileName, String nodeName, String section) {
		
	    URLConnection conn = null;
	    DataInputStream data = null;
	    URL cb = getCodeBase();
	    String[] path_arr = cb.toString().split("/");
	    String path = "";
	    for(int i=0;i<path_arr.length-1;i++)
	    	path = path + path_arr[i] + "/";
	    path = path + "interactive-pdf.htm?file=" + fileName + "&node="+nodeName+"&section="+section;
	    
//	    ta.append(path);
	    
	    try {
	      conn = (new URL(cb, path)).openConnection();
	      conn.setReadTimeout(100000);
	      conn.connect();
	      
	      int length = conn.getContentLength();
//	      ta.append("length: "+length);
	      if(length<0)
	    	  return null;
	      
	      data =  new DataInputStream(new BufferedInputStream(
	                     conn.getInputStream()));
	      byte[] bb = new byte[length];
	      ByteBuffer buf = ByteBuffer.wrap(bb);
	      byte[] aa = new byte[100];
	      int rl = 0;
	      while((rl=data.read(aa))>0) {
	    	  buf.put(aa, 0, rl);
	      }
	      return buf;
	    }
	    catch (IOException e) {
//	      ta.append("IO Error:" + e.getMessage());
	    }
	    
	    return null;
	  }

	
	public void showPage(int pageno) {
		if(pageno>pdffile.getNumPages())
			pageno = pdffile.getNumPages();
		if(pageno<1)
			pageno=1;
		PDFPage page = pdffile.getPage(pageno);
		panel.showPage(page);
		current_page = pageno;
		//prev.setEnabled(pageno!=1);
		//next.setEnabled(pageno!=pdffile.getNumPages());
		panel.repaint();
	}
	
	public void refresh() {
		showPage(current_page);
	}
	
	public int getPageNo() {
		return current_page;
	}
	
	public void nextPage() {
		showPage(current_page+1);
	}
	
	public void prevPage() {
		showPage(current_page-1);
	}
	
	
}
