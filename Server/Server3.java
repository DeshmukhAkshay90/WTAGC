
package server;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.*;


import javax.microedition.io.*;
import javax.bluetooth.*;


public class Server extends JFrame implements ActionListener,Runnable
{
	int count=0;
	int tcount=0;
	double nowx,nowy;
	String sendmsg,readmsg,sernm,nm,passkey=null,key=null,str,str1,drv;
	InputStream is=null;
	OutputStream os=null;
	byte [] readarr=null;
	Robot robot;
	BufferedReader br;
	Container con;
	Object object;
	StreamConnectionNotifier scnoti;
	StreamConnection cnn;
	String inp;
	JFrame f=new JFrame("CHAT WINDOW...");
	JFrame f1=new JFrame("Drive Specification...");
	
	JLabel title=new JLabel("CHAT WINDOW");
	JLabel jbl =new JLabel("For Starting Server Please CLICK on");
	JLabel jbl1 =new JLabel("START and Wait For CONNECTION...");
	JLabel jblrec=new JLabel("Received Message :- ");
	JLabel jblsend=new JLabel("Type Message :- ");
	
	JTextArea tf=new JTextArea();
	
	JTextArea tf1=new JTextArea();

//	JTextField tf=new JTextField();
//	
//	JTextField tf1=new JTextField();
	
			
	JTextField txtdrive=new JTextField(10);	
		
	JButton sendbutton =new JButton("SEND");
	JButton exitbutton   =new JButton("EXIT");
	JButton startbutton  =new JButton("START");
	JButton okbutton =new JButton("OK");
	int prvx,prvy;
        int prvkx,prvky,newx,newy;
	Thread t;		
	Dimension sc;
	 Point point;
		public Server()
		{
                      try
                        {
                           robot=new Robot();
                           robot.setAutoDelay(40);
                        }
			catch(Exception e)
                        {
                            System.err.println("Exception "+e);
                        }
			sc=Toolkit.getDefaultToolkit().getScreenSize();
                   
                        System.out.println(sc.getWidth()+"  "+sc.getHeight());
			f.setContentPane(new JLabel(new ImageIcon("..\\a-032.png")));
			
			con = f.getContentPane();	
			con.setLayout(null);	
								
			f.setBounds(200,100,350,335);
			f.setResizable(false);
			f.setVisible(true);	
				
			title.setBounds(40,20,350,30);
			title.setForeground(Color.BLACK);
			title.setFont(new Font("Times new roman",Font.BOLD,35));
			con.add(title);
			
		
			jbl.setBounds(10,110,300,20);
		
			jbl.setFont(new Font("Times new roman",Font.BOLD,16));
			jbl.setForeground(Color.BLACK);
			con.add(jbl);
			
			jbl1.setFont(new Font("Times new roman",Font.BOLD,16));
			jbl1.setBounds(65,140,300,20);
			jbl1.setForeground(Color.BLACK);
			con.add(jbl1);
			
			jblrec.setFont(new Font("Times new roman",Font.BOLD,14));
			jblsend.setFont(new Font("Times new roman",Font.BOLD,14));
			
			startbutton.setBounds(40,250,80,30);
			con.add(startbutton);
				
			exitbutton.setBounds(200,250,80,30);
			con.add(exitbutton);
			
			startbutton.addActionListener(this);
			exitbutton.addActionListener(this);				
			sendbutton.addActionListener(this);
			okbutton.addActionListener(this);
				
			
		}
					
		public void actionPerformed(ActionEvent e) 		
		{
		
			String c=e.getActionCommand();
				
			if(c=="START")
			{	
				Thread t=new Thread(this);
				f.remove(startbutton);
				f.remove(jbl1);
				
				f.repaint();
				
				try
				{
											
					if(scnoti!=null)
						scnoti.close();
					
					if(cnn!=null)
						cnn.close();
					
					if(os!=null)
						os.close();
					
					if(is!=null)
						is.close();
						
				LocalDevice local=LocalDevice.getLocalDevice();
				local.setDiscoverable(DiscoveryAgent.GIAC);
							
				String sernm=local.getBluetoothAddress();
				
				String nm =local.getFriendlyName();
				
				System.out.println(sernm);
				System.out.println(nm);
				
				String url="btspp://localhost:" + 0x0009 + ";" + "name=" + nm + ";" + "authenticate=false";
						
				scnoti=(StreamConnectionNotifier) Connector.open(url);
			
				System.out.println(url);
					
				cnn=scnoti.acceptAndOpen();
				
				jbl.setBounds(10,90,300,20);		
				jbl.setText("Device Connected...");
				jbl.setFont(new Font("Times new roman",Font.BOLD,16));
				
				
				
				okbutton.setBounds(40,250,80,30);
				f.add(okbutton);
				f.repaint();
				
				t.start();
			 	}
				catch(IOException ex)
				{		
					jbl.setText("ERROR :- Please Check BLUETOOTH DEVICE...");	
					System.out.println(ex);
													
				}
				
			}
						
			if(c=="EXIT")	
			{
				try
				{
				
					if(scnoti!=null)
						scnoti.close();
					
					if(cnn!=null)
						cnn.close();
					
					if(os!=null)
						os.close();
					
					if(is!=null)
						is.close();
						
					System.exit(0);
									
				}
				catch(IOException ex){}

			}
			
			
			
		}
		
		
		public void run()
		{
		
		try
		{
			
				
			is=cnn.openInputStream();
			
			os=cnn.openOutputStream();
						
		//	System.out.println(nm);
					
			while (true) 
			{
				System.out.println("reading");
				
				StringBuffer sb = new StringBuffer();
				int c = 0;
					
				
					
				while ((c = is.read()) != -1)
				{
					
					if(c!='\n')
					{
						//System.out.println("XX");
						//System.out.println(sb.toString());
						
						sb.append((char)c);
                                                
					}
					else
					{	
					 	System.out.println("Data=  "+sb);
						inp=sb.toString();

						if(inp.startsWith("keypad"))
						{
						  keypadOperation(inp);
						}
						else
						{
						 if(inp.startsWith("click"))   
                                                 {
                                                     clickOperation(inp);
                                                 }
						else
                                                 {
                                                     checkOperation(inp);
                                                 }	
						}
                                                 break;
					}
				}
				
			}		
		}
		catch(IOException ex){}
	
	}	
		
       public void checkOperation(String msg)
        {   
             String arr[]=msg.split(":");
             prvx=(int)Float.parseFloat(arr[1]);
             prvy=(int)Float.parseFloat(arr[2]);
             movePointer(prvx, prvy);

            
        }
        
	public void keypadOperation(String msg)
	 {
		 String arr[]=msg.split(":");
		  switch(arr[1])
           	   {
               		 case "up":
                 		   robot.keyPress(KeyEvent.VK_UP);
                    	 break;
               		 case "down":
                                   robot.keyPress(KeyEvent.VK_DOWN);
                    	
                         break;
                   	case "left":
                                   robot.keyPress(KeyEvent.VK_LEFT);
                    	
                         break;
			case "right":
                                   robot.keyPress(KeyEvent.VK_RIGHT);
                    	
                         break;
                        case "move":
                              newx=Integer.parseInt(arr[2]);
                              
                              if(newx>0)
                              {
                                           robot.keyPress(KeyEvent.VK_RIGHT);
                    	          
                              }
                              else
                              {
                                             robot.keyPress(KeyEvent.VK_LEFT);
                               }
                              
                               break;

                    }

              
          
	 }	
	
	public void movePointer(int x,int y)
	{
		
				try 
				{ 
                                       
                                      point= MouseInfo.getPointerInfo().getLocation();
                                      nowx=point.getX();
                                      nowy=point.getY(); 
                                      System.out.println("changed "+(int)(x+nowx)+ "  "+ (int)(y+nowy)); 
                                       robot.mouseMove((int)(x+nowx),(int)(y+nowy)); 
                               
				} 
				catch(Exception e1) 
                                {
                                    
                                    System.out.println("mv Done"+e1); 
                                } 
			
		
				
	}	


	public void clickOperation(String button)
	{
		
                            int action;
                            if(button.contains("clickleft"))
                            {
                                action=InputEvent.BUTTON1_MASK;
                            }
                            else
                            {
                                action=InputEvent.BUTTON3_MASK;
                            }
                            
				try 
				{ 
                                         robot.mousePress(action);
                                         robot.mouseRelease(action);
				         
				} 
				catch(Exception e1)
                                {
                                     System.out.println(" bt prsd"+e1);
                                } 
				 
		
				System.out.println("Done"); 
	}	
			
		
	
	
		public static void main (String[] args) throws IOException 
		{
				JFrame f=new JFrame();
					new Server();
							
			f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}		
			
		
}
