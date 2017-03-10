package application;
/*
  * Program name: BankRunner.java
  * By: Seohee Jung, Benn Xu and John Dong
  * Course: ICS4U1
  * School: A.Y. Jackson S.S.
  * Date: June 9th, 2015
  * Description: This is the full runner program for the bank. 
  * 	This will contain the GhettoBankRunner portion of the bank as well.
  */  
 
 // necessary imports
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;

// the GhettoBankRunner class
public class GhettoBankRunner extends JFrame
{
	// class constant
	final int NUMBER_LENGTH = 6;
	
	// class fields
	private int screenNum;
	private boolean accTypeShow;		// the account type, savings or chequings. savings - true
	private JFrame bank;					// and chequings is represented with false.
	private JLabel welcomeLabel = new JLabel();
	
	// adding in a few other variables for the runner portion
	int acc, trans, transfer, service;
   double amount=0, chequingBal, savingsBal;
   String first, last, gender;
   int pin1=0, pin2=1, date, month, year;
   String birthdate, eCard="";
   String [] list;
   Bank customer = new Bank();
   Account userAcc;
   Account other;
   Transactions action;
   boolean cont=false;
   String []message;
	
	/*
	 * the constructor
	 * no parameters necessary
	 */
	public GhettoBankRunner()
	{
		// starting the bank menu and account type
		bank = new JFrame();
		
		// sizing the frame
		bank.setSize(768,650);
		bank.setTitle("JXD BANK");
		accTypeShow = false;
		//bank.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// setting the menu to appear in the middle of the screen
		bank.setLocationRelativeTo(null);
		
		// adding an image
		try
		{
			BufferedImage welcomeImg = ImageIO.read(new FileInputStream("images/welcome_Screen.jpg"));
		
			welcomeLabel = new JLabel(new ImageIcon(welcomeImg));
			bank.add(welcomeLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot find/load welcome screen image file!");
		}
		
		// making the bank visible
		bank.setVisible(true);
	}
	
	// the buttons for the main menu
	public void showWelcome()
	{
		// quitting id
		final int welcomeID = 4;
		
		// creating the buttons
		bank.setLayout(null);
		JButton accCreate = new JButton("Create new account");
		JButton existingAcc = new JButton("Use existing account");
		JButton extOrNot = new JButton("Quit");
		
		// setting the co-ordinates of the buttons
		accCreate.setBounds(50,300,200,50);
		existingAcc.setBounds(50,450,200,50);
		extOrNot.setBounds(50, 550, 200, 50);
		
		// what to do in case each button is pressed
		accCreate.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				bank.dispatchEvent(new WindowEvent(bank, WindowEvent.WINDOW_CLOSING));
				newAccCreationShow();
			}
		});
		
		existingAcc.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				bank.dispatchEvent(new WindowEvent(bank, WindowEvent.WINDOW_CLOSING));
				showCardNumInput();
			}
		});
		
		extOrNot.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				bank.dispatchEvent(new WindowEvent(bank, WindowEvent.WINDOW_CLOSING));
				confirmationExit(welcomeID);
			}
		});
		
		// actually showing the buttons on the screen
		welcomeLabel.add(accCreate);
		welcomeLabel.add(existingAcc);
		welcomeLabel.add(extOrNot);
	}
	
	/*
	 * the main menu
	 * no parameters necessary
	 */
	public void showMainMenu()
	{
		// quitting id
		final int mmID = 5;
		
		final JFrame mm = new JFrame();
		mm.setSize(768,650);
		mm.setTitle("JXD BANK");
		//mm.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		JLabel mmLabel = new JLabel();
		userAcc = customer.user.getChequing();
		other = customer.user.getSavings();

		
		// setting the menu to appear in the middle of the screen
		mm.setLocationRelativeTo(null);
		
		// adding an image
		try
		{
			BufferedImage mmImg = ImageIO.read(new FileInputStream("images/mnMenu.jpg"));
		
			mmLabel = new JLabel(new ImageIcon(mmImg));
			mm.add(mmLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot find/load main menu image file!");
		}
		
		// showing the interface
		mm.setVisible(true);
		mm.setLayout(null);
		
		// the buttons in the main menu
		JButton derp = new JButton("Deposit");
		JButton trans = new JButton("Transfer");
		JButton wth = new JButton("Withdrawal");
		JButton trnsHis = new JButton("Transaction History");
		JButton othrSrv = new JButton("Other Services");
		JButton svAcc = new JButton("Switch to Savings account");
		JButton chqAcc = new JButton("Switch to Chequings account");
		JButton exet = new JButton("Exit");
		JLabel mulaAmont = new JLabel();
		JLabel wrtAcc = new JLabel("Chequings Account");
		JLabel prsnName = new JLabel();
		JLabel mulaInBank = new JLabel();
		
		// setting the location of the buttons
		derp.setBounds(41, 350, 200, 100);
 		trans.setBounds(41, 467, 200, 100);
  		wth.setBounds(279, 350, 200, 100);
  		trnsHis.setBounds(279, 467, 200, 100);
  		othrSrv.setBounds(517, 353, 200, 50);
		svAcc.setBounds(547, 27, 200, 40);
		chqAcc.setBounds(547, 95, 200, 40);
		exet.setBounds(517, 467, 200, 100);
		//mulaAmont.setBounds(279, );
		wrtAcc.setBounds(279, 215, 150, 20);
		prsnName.setBounds(279, 90, 200, 20);
		mulaInBank.setBounds(279, 268, 200, 20);
		
		// changing the font color
		wrtAcc.setForeground(Color.white);
		prsnName.setForeground(Color.white);
		mulaInBank.setForeground(Color.white);
		
		// setting the text of the label displayed
		prsnName.setText(customer.user.getFirstN() + " " + customer.user.getLastN());
		
		if(accTypeShow)
		{
			mulaInBank.setText("" + customer.user.getSavingsBal());
		}else
		{
			mulaInBank.setText("" + customer.user.getChequingBal());
		}
		
		if(accTypeShow)
		{
			wrtAcc.setText("Savings account");
		}
		
		// adding action listeners to tell the program what to do when the
		// button is pressed.
		derp.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				mm.dispatchEvent(new WindowEvent(mm, WindowEvent.WINDOW_CLOSING));
				showDeposit();
			}
		});
		
		trans.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				mm.dispatchEvent(new WindowEvent(mm, WindowEvent.WINDOW_CLOSING));
				showTransfer();
			}
		});
		
		wth.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				mm.dispatchEvent(new WindowEvent(mm, WindowEvent.WINDOW_CLOSING));
				showWithdrawal();
			}
		});
		
		trnsHis.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				mm.dispatchEvent(new WindowEvent(mm, WindowEvent.WINDOW_CLOSING));
				showTransactionHis();
			}
		});
		
		othrSrv.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				mm.dispatchEvent(new WindowEvent(mm, WindowEvent.WINDOW_CLOSING));
				showPersonalChange();
			}
		});
		
		svAcc.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				mm.dispatchEvent(new WindowEvent(mm, WindowEvent.WINDOW_CLOSING));
				accTypeShow = true;
				userAcc = customer.user.getSavings();
				other = customer.user.getChequing();
				showMainMenu();
			}
		});
		
		chqAcc.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				mm.dispatchEvent(new WindowEvent(mm, WindowEvent.WINDOW_CLOSING));
				accTypeShow = false;
				userAcc = customer.user.getChequing();
				other = customer.user.getSavings();
				showMainMenu();
			}
		});
		
		exet.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				mm.dispatchEvent(new WindowEvent(mm, WindowEvent.WINDOW_CLOSING));
				confirmationExit(mmID);
			}
		});
		
		// adding all of the buttons to the JFrame
		mmLabel.add(derp);
		mmLabel.add(trans);
		mmLabel.add(wth);
		mmLabel.add(trnsHis);
		mmLabel.add(othrSrv);
		mmLabel.add(svAcc);
		mmLabel.add(chqAcc);
		mmLabel.add(exet);
		mmLabel.add(wrtAcc);
		mmLabel.add(mulaAmont);
		mmLabel.add(prsnName);
		mmLabel.add(mulaInBank);
	}
	
	/*
	 *	The method will show the card number input screen
	 * no parameters necessary
	 */
	public void showCardNumInput()
	{
		// quitting id
		final int cniID = 6;
		
		// creating a new frame for card number input
		final JFrame cni = new JFrame();
		cni.setSize(768,650);
		cni.setTitle("JXD BANK");
		//cni.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		cni.setLocationRelativeTo(null);
		JLabel cniLabel = new JLabel();
		
		// adding the background
		try
		{
			BufferedImage cniImg = ImageIO.read(new FileInputStream("images/cniImage.jpg"));
			cniLabel = new JLabel(new ImageIcon(cniImg));
			
			cni.add(cniLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot find/load card number input screen image file!");
		}
		
		// displaying the JFrame
		cni.setVisible(true);
		cni.setLayout(null);
		
		// creating the text fields
		// to get the value inside textfield -- Double.parseDouble(ni.getText());
		final JTextField ni = new JTextField();
		final JPasswordField pi = new JPasswordField();
		//JPasswordField pif = new JPasswordField();

		ni.setBounds(400, 170, 100, 20);
		pi.setBounds(400, 256, 50, 20);
		//pif.setBounds(400, 308, 50, 20);
		
		// the exit, back and ok buttons
		JButton getOut = new JButton("Quit");
		JButton oops = new JButton("Back");
		JButton done = new JButton("OK");
		
		// placing the fields
		getOut.setBounds(100, 453, 100, 50);
		oops.setBounds(332, 453, 100, 50);
		done.setBounds(564, 453, 100, 50);
		
		// what to do in case each button is pressed
		getOut.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				cni.dispatchEvent(new WindowEvent(cni, WindowEvent.WINDOW_CLOSING));
				confirmationExit(cniID);
			}
		});
		
		oops.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				cni.dispatchEvent(new WindowEvent(cni, WindowEvent.WINDOW_CLOSING));
				GhettoBankRunner gui = new GhettoBankRunner();
				gui.showWelcome();
			}
		});
		
		done.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				eCard = ni.getText();
				pin1 = Integer.parseInt(pi.getText());
				
				//check if the account exists
				if(!customer.searchClient(eCard))
				{
	         	showInvalidCN();
					ni.setText("");
            }else
				{
             	customer.loadClient(eCard);
            }
				 
				if(!customer.checkPIN(pin1))
				{
					showInvalidCN();
					pi.setText("");
				}else
				{
					cni.dispatchEvent(new WindowEvent(cni, WindowEvent.WINDOW_CLOSING));
					showMainMenu();
				}
        	}
		});
		
		// adding the buttons to the frame
		cniLabel.add(getOut);
		cniLabel.add(oops);
		cniLabel.add(done);
		
		// adding in the textfield
		cniLabel.add(ni);
		cniLabel.add(pi);
		//cniLabel.add(pif);
	}
	
	/*
	 *	Displays the error message when an invalid card
	 * number is entered.
	 */
	public void showInvalidCN()
	{
		final JFrame icn = new JFrame();
		
		icn.setTitle("JXD BANK");
		icn.setSize(500, 200);
		//icn.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		icn.setLayout(null);
		icn.setLocationRelativeTo(null);
		icn.setVisible(true);
		
		// the label and button
		JLabel conf = new JLabel("Invalid card number or pin number!");
		JButton cs = new JButton("Close");
		
		// setting location for the label and button
		conf.setBounds(100, 100, 200, 50);
		cs.setBounds(350, 110, 75, 25);
		
		// what to do for the button
		cs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// closes this frame and goes back to the main menu
				icn.dispatchEvent(new WindowEvent(icn, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		// adding the label and button
		icn.add(conf);
		icn.add(cs);
	}
	/*
	 * displays the new account creation menu
	 * no parameters necessary
	 */
	public void newAccCreationShow()
	{
		// variable(s) (card number) & initialization
		int cn0 = 0;
		Random gen = new Random();
		final JFrame accShow = new JFrame();
		accShow.setSize(768,650);
		accShow.setTitle("JXD BANK");
		//accShow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		JLabel accCLabel = new JLabel();
		
		try
		{
			BufferedImage accCImg = ImageIO.read(new FileInputStream("images/accCreationImg.jpg"));
			accCLabel = new JLabel(new ImageIcon(accCImg));
			
			accShow.add(accCLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot load new account creation image!");
		}
		
		// setting the menu to appear in the middle of the screen
		accShow.setLocationRelativeTo(null);
		accShow.setVisible(true);
		accShow.setLayout(null);
		
		// randomly creating a new card number for the user
		for (int i = 0; i < NUMBER_LENGTH; i++)
		{
			cn0 += gen.nextInt(10) * Math.pow(10, i);
		}
		
		// the text on the screen
		final int cn = cn0;
 		JLabel cnDisplayGenerated = new JLabel("" + cn, JLabel.CENTER);
		final JTextField fnField = new JTextField();
		final JTextField bdMonth = new JTextField();
		final JTextField bdDay = new JTextField();
		final JTextField bdYear = new JTextField();
		final JTextField mnField = new JTextField();
		final JTextField lnName = new JTextField();
		final JPasswordField pnField = new JPasswordField();
		final JPasswordField pnConfField = new JPasswordField();
		JButton ext = new JButton("Exit");
		JButton iight = new JButton("OK");
		JRadioButton gm = new JRadioButton("Male");
		JRadioButton gf = new JRadioButton("Female");
		
		// button group to ensure that only one gender is selected at a time...
		ButtonGroup gendrr = new ButtonGroup();
		gendrr.add(gm);
		gendrr.add(gf);
		
		
		/*
		 * setting all of their positions & font size
		 */
 		cnDisplayGenerated.setVerticalAlignment(JLabel.CENTER);
		cnDisplayGenerated.setBounds(410, 400, 100, 20);
		cnDisplayGenerated.setForeground(Color.red);
		cnDisplayGenerated.setFont(new Font("" + cn, Font.PLAIN, 16));
		
		fnField.setBounds(185, 249, 150, 20);
		bdMonth.setBounds(528, 221, 40, 20);
		//bdMonth.setDocument(new JTextFieldLimit(2));
		bdDay.setBounds(583, 221, 40, 20);
		//bdDay.setDocument(new JTextFieldLimit(2));
		bdYear.setBounds(637, 221, 85, 20);
		//bdYear.setDocument(new JTextFieldLimit(4));
		mnField.setBounds(185, 325, 150, 20);
		lnName.setBounds(185, 400, 150, 20);
		pnField.setBounds(350, 480, 75, 20);
		pnConfField.setBounds(350, 530, 75, 20);
		ext.setBounds(650, 517, 72, 50);
		iight.setBounds(525, 517, 72, 50);
		gm.setBounds(528, 325, 75, 20);
		gf.setBounds(637, 325, 75, 20);
		
		// what to do in case each button is pressed
		ext.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				accShow.dispatchEvent(new WindowEvent(accShow, WindowEvent.WINDOW_CLOSING));
				GhettoBankRunner gui = new GhettoBankRunner();
				gui.showWelcome();
			}
		});
		
		// what to do in case button is pressed
		iight.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// runner portion
				first = fnField.getText();
				last = lnName.getText();
				month = Integer.parseInt(bdMonth.getText());
				year = Integer.parseInt(bdYear.getText());
				date = Integer.parseInt(bdDay.getText());
				eCard = "" + cn;
				pin1 = Integer.parseInt(pnField.getText());
				pin2 = Integer.parseInt(pnConfField.getText());
				
				// in case the passwords entered do not match
				if (pin1 != pin2)
				{
					showNotMatchingPin();
					pnField.setText("");
					pnConfField.setText("");
				}else
				{
					// saving to file
					customer.setupUser(first, last, gender, month, year, date, eCard, pin1, 25, 0, 0);
		   	   customer.savePersonalInfo();
				
					accShow.dispatchEvent(new WindowEvent(accShow, WindowEvent.WINDOW_CLOSING));
					showMainMenu();
				}
			}
		});
		
		// finding out the gender...used in runner portion
		gm.addActionListener(new ActionListener() 
		{
      	@Override
      	public void actionPerformed(ActionEvent e) 
			{
         	gender = "Male";
      	}
    	});

    	//add disallow listener
    	gf.addActionListener(new ActionListener() 
		{
      	@Override
        	public void actionPerformed(ActionEvent e)
			{
         	gender = "Female";
        	}
    	});
		
		/*
		 * actually showing the text
		 */
 		accCLabel.add(cnDisplayGenerated);
		accCLabel.add(fnField);
		accCLabel.add(bdMonth);
		accCLabel.add(bdDay);
		accCLabel.add(bdYear);
		accCLabel.add(mnField);
		accCLabel.add(lnName);
		accCLabel.add(pnField);
		accCLabel.add(pnConfField);
		accCLabel.add(ext);
		accCLabel.add(iight);
		accCLabel.add(gm);
		accCLabel.add(gf);
	}
	
	// displays a warning message signaling a pin mismatch
	public void showNotMatchingPin()
	{
		// creating the frame
		final JFrame nmp = new JFrame();
		
		nmp.setTitle("JXD BANK");
		nmp.setSize(500, 200);
		//nmp.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		nmp.setLayout(null);
		nmp.setLocationRelativeTo(null);
		nmp.setVisible(true);
		
		// the label and button
		JLabel conf = new JLabel("Pin mismatch!");
		JButton cs = new JButton("Close");
		
		// setting location for the label and button
		conf.setBounds(100, 100, 200, 50);
		cs.setBounds(350, 110, 75, 25);
		
		// what to do for the button
		cs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// closes this frame and goes back to the main menu
				nmp.dispatchEvent(new WindowEvent(nmp, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		// adding the label and button
		nmp.add(conf);
		nmp.add(cs);
	}
	
	/*
	 * Displays the deposit screen with the buttons for amount input
	 * no parameters necessary
	 */
	public void showDeposit()
	{
		// declaring the jframe and initializing the properties
		final JFrame dep = new JFrame();
		final int depCode = 1;						// code for telling the program where it is
		
		dep.setSize(768, 650);
		dep.setTitle("JXD BANK");
		//dep.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		dep.setLocationRelativeTo(null);
		JLabel depLabel = new JLabel();
		
		// adding the background
		try
		{
			BufferedImage depImage = ImageIO.read(new FileInputStream("images/depImg.jpg"));
			depLabel = new JLabel(new ImageIcon(depImage));
			
			dep.add(depLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot load new deposit image!");
		}
		
		// displaying the jframe and clearing the layout manager
		dep.setVisible(true);
		dep.setLayout(null);
		
		// the buttons
		final JButton[] mny = {new JButton("$100"), new JButton("$20"), new JButton("$200"), new JButton("$300"), new JButton("$40"), new JButton("$80"), new JButton("Main Menu")};
		
		// placing the buttons
		mny[0].setBounds(68, 148, 150, 100);
		mny[1].setBounds(338, 148, 150, 100);
 		mny[2].setBounds(68, 278, 150, 100);
 		mny[3].setBounds(68, 408, 150, 100);
  		mny[4].setBounds(336, 278, 150, 100);
  		mny[5].setBounds(336, 408, 150, 100);
		mny[6].setBounds(598, 275, 100, 100);
		
		// using the money buttons
		mny[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				dep.dispatchEvent(new WindowEvent(dep, WindowEvent.WINDOW_CLOSING));
				String amount = mny[0].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(depCode, realAmnt, accTypeShow);
			}
		});
		
		mny[1].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				dep.dispatchEvent(new WindowEvent(dep, WindowEvent.WINDOW_CLOSING));
				String amount = mny[1].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(depCode, realAmnt, accTypeShow);
			}
		});
		
		mny[2].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				dep.dispatchEvent(new WindowEvent(dep, WindowEvent.WINDOW_CLOSING));
				String amount = mny[2].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(depCode, realAmnt, accTypeShow);
			}
		});
		
		mny[3].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				dep.dispatchEvent(new WindowEvent(dep, WindowEvent.WINDOW_CLOSING));
				String amount = mny[3].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(depCode, realAmnt, accTypeShow);
			}
		});
		
		mny[4].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				dep.dispatchEvent(new WindowEvent(dep, WindowEvent.WINDOW_CLOSING));
				String amount = mny[4].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(depCode, realAmnt, accTypeShow);
			}
		});
		
		mny[5].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				dep.dispatchEvent(new WindowEvent(dep, WindowEvent.WINDOW_CLOSING));
				String amount = mny[5].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(depCode, realAmnt, accTypeShow);
			}
		});
		
		mny[6].addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				dep.dispatchEvent(new WindowEvent(dep, WindowEvent.WINDOW_CLOSING));
				showMainMenu();
			}
		});

		// adding the buttons to the frame and displaying the buttons properly
		for(int i = 0; i<mny.length; i++)
		{
			depLabel.add(mny[i]);
		}
	}
	
	/*
	 * The withdrawal screen
	 * no parameters necessary
	 */
	public void showWithdrawal()
	{
		// declaring the jframe and initializing the properties
		final JFrame wth = new JFrame();
		final int wthCode = 2;
		
		wth.setSize(768, 650);
		wth.setTitle("JXD BANK");
		//wth.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		wth.setLocationRelativeTo(null);
		JLabel wthLabel = new JLabel();
		
		// adding the background
		try
		{
			BufferedImage wthImage = ImageIO.read(new FileInputStream("images/wthImg.jpg"));
			wthLabel = new JLabel(new ImageIcon(wthImage));
			
			wth.add(wthLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot load new withdrawing image!");
		}
		
		// displaying the jframe and clearing the layout manager
		wth.setVisible(true);
		wth.setLayout(null);
		
		// the buttons
		final JButton[] mny = {new JButton("$100"), new JButton("$20"), new JButton("$200"), new JButton("$300"), new JButton("$40"), new JButton("$80"), new JButton("Main Menu")};
		
		// placing the buttons
		mny[0].setBounds(68, 148, 150, 100);
		mny[1].setBounds(338, 148, 150, 100);
 		mny[2].setBounds(68, 278, 150, 100);
 		mny[3].setBounds(68, 408, 150, 100);
  		mny[4].setBounds(336, 278, 150, 100);
  		mny[5].setBounds(336, 408, 150, 100);
		mny[6].setBounds(598, 275, 100, 100);
		
		// using the money buttons
		mny[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				wth.dispatchEvent(new WindowEvent(wth, WindowEvent.WINDOW_CLOSING));
				String amount = mny[0].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(wthCode, realAmnt, accTypeShow);
			}
		});
		
		mny[1].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				wth.dispatchEvent(new WindowEvent(wth, WindowEvent.WINDOW_CLOSING));
				String amount = mny[1].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(wthCode, realAmnt, accTypeShow);
			}
		});
		
		mny[2].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				wth.dispatchEvent(new WindowEvent(wth, WindowEvent.WINDOW_CLOSING));
				String amount = mny[2].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(wthCode, realAmnt, accTypeShow);
			}
		});
		
		mny[3].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				wth.dispatchEvent(new WindowEvent(wth, WindowEvent.WINDOW_CLOSING));
				String amount = mny[3].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(wthCode, realAmnt, accTypeShow);
			}
		});
		
		mny[4].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				wth.dispatchEvent(new WindowEvent(wth, WindowEvent.WINDOW_CLOSING));
				String amount = mny[4].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(wthCode, realAmnt, accTypeShow);
			}
		});
		
		mny[5].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				wth.dispatchEvent(new WindowEvent(wth, WindowEvent.WINDOW_CLOSING));
				String amount = mny[5].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(wthCode, realAmnt, accTypeShow);
			}
		});
		
		mny[6].addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				wth.dispatchEvent(new WindowEvent(wth, WindowEvent.WINDOW_CLOSING));
				showMainMenu();
			}
		});

		// adding the buttons to the frame and displaying the buttons properly
		for(int i = 0; i<mny.length; i++)
		{
			wthLabel.add(mny[i]);
		}
	}
	
	/*
	 * This method takes care of the transerring money between accounts GhettoBankRunner
	 * no parameters necessary
	 */
	public void showTransfer()
	{
		// declaring the jframe and initializing the properties
		final JFrame trans = new JFrame();
		final int transCode = 3;
		
		trans.setSize(768, 650);
		trans.setTitle("JXD BANK");
		//trans.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		trans.setLocationRelativeTo(null);
		JLabel transLabel = new JLabel();
		
		// adding the background
		try
		{
			BufferedImage transImage = ImageIO.read(new FileInputStream("images/transImg.jpg"));
			transLabel = new JLabel(new ImageIcon(transImage));
			
			trans.add(transLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot load new transfer image!");
		}
		
		// displaying the jframe and clearing the layout manager
		trans.setVisible(true);
		trans.setLayout(null);
		
		// the buttons
		final JButton[] mny = {new JButton("$100"), new JButton("$20"), new JButton("$200"), new JButton("$300"), new JButton("$40"), new JButton("$80"), new JButton("Main Menu")};
		JLabel where = new JLabel("***Chequings to Savings***");
		
		if (accTypeShow)
		{
			where.setText("***Savings to Chequings***");
		}
		
		// placing the buttons and the label
		mny[0].setBounds(68, 148, 150, 100);
		mny[1].setBounds(338, 148, 150, 100);
 		mny[2].setBounds(68, 278, 150, 100);
 		mny[3].setBounds(68, 408, 150, 100);
  		mny[4].setBounds(336, 278, 150, 100);
  		mny[5].setBounds(336, 408, 150, 100);
		mny[6].setBounds(598, 275, 100, 100);
		where.setBounds(68, 120, 200, 20);
		
		// setting the font color to white
		where.setForeground(Color.white);
		
		// using the money buttons
		mny[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				trans.dispatchEvent(new WindowEvent(trans, WindowEvent.WINDOW_CLOSING));
				String amount = mny[0].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(transCode, realAmnt, accTypeShow);
			}
		});
		
		mny[1].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				trans.dispatchEvent(new WindowEvent(trans, WindowEvent.WINDOW_CLOSING));
				String amount = mny[1].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(transCode, realAmnt, accTypeShow);
			}
		});
		
		mny[2].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				trans.dispatchEvent(new WindowEvent(trans, WindowEvent.WINDOW_CLOSING));
				String amount = mny[2].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(transCode, realAmnt, accTypeShow);
			}
		});
		
		mny[3].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				trans.dispatchEvent(new WindowEvent(trans, WindowEvent.WINDOW_CLOSING));
				String amount = mny[3].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(transCode, realAmnt, accTypeShow);
			}
		});
		
		mny[4].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				trans.dispatchEvent(new WindowEvent(trans, WindowEvent.WINDOW_CLOSING));
				String amount = mny[4].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(transCode, realAmnt, accTypeShow);
			}
		});
		
		mny[5].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				trans.dispatchEvent(new WindowEvent(trans, WindowEvent.WINDOW_CLOSING));
				String amount = mny[5].getText().substring(1);
				int realAmnt = Integer.parseInt(amount);
				showConfirmation(transCode, realAmnt, accTypeShow);
			}
		});
		
		mny[6].addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				trans.dispatchEvent(new WindowEvent(trans, WindowEvent.WINDOW_CLOSING));
				showMainMenu();
			}
		});

		// adding the buttons to the frame and displaying the buttons properly
		for(int i = 0; i<mny.length; i++)
		{
			transLabel.add(mny[i]);
		}
		
		// adding that line of text
		transLabel.add(where);
	}
	
	/*
	 * showing the transaction history
	 * no parameters necessary
	 */
	public void showTransactionHis()
	{
		// creating the frame
		final JFrame sth = new JFrame();
		
		// setting the elements of the frame
		sth.setSize(768, 650);
		sth.setLocationRelativeTo(null);
		sth.setLayout(null);
		sth.setTitle("JXD BANK");
		
		//sth.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		sth.setVisible(true);
		
		// adding the text area and button
		JTextArea heree = new JTextArea();
		JButton cs = new JButton("Close");
	
		// attempt to read from text file
		try
		{
			// "reader"
			BufferedReader bf = new BufferedReader(new FileReader("clients\\"+customer.user.getCardNum()+"0.txt"));
			
			// a string to store the line
			String str = bf.readLine();
			
			// checking if the file ended or not
			while(str != null)
			{
				// adding the string
				heree.append(str + "\r\n");
				str = bf.readLine();
			}
			
			bf.close();
		}catch(IOException ioe)
		{
			System.out.println("Error reading text file!");
		}
		
		// action to perform in case the button is pressed
		cs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				sth.dispatchEvent(new WindowEvent(sth, WindowEvent.WINDOW_CLOSING));
				showMainMenu();
			}
		});
		
		// the scroll pane in case text file is too large
		JScrollPane rllyHere = new JScrollPane(heree);
		
		// setting the location of the scroll pane, text area and button
		heree.setBounds(0, 0, 150, 600);
		rllyHere.setBounds(0, 0, 675, 610);
		cs.setBounds(680, 350, 70, 25);
		
		// adding the scroll pane
		sth.add(rllyHere);
		sth.add(cs);
	}
	
	/*
	 * changing user information screens
	 * no parameters necessary
	 */
	public void showPersonalChange()
	{
			// declaring the jframe and initializing the properties
		final JFrame pChange = new JFrame();
		
		pChange.setSize(768, 650);
		pChange.setTitle("JXD BANK");
		//trans.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pChange.setLocationRelativeTo(null);
		JLabel pChangeLabel = new JLabel();
		
		// adding the background
		try
		{
			BufferedImage pImage = ImageIO.read(new FileInputStream("images/pChangeImg.jpg"));
			pChangeLabel = new JLabel(new ImageIcon(pImage));
			
			pChange.add(pChangeLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot load other services image!");
		}
		
		// displaying the jframe and clearing the layout manager
		pChange.setVisible(true);
		pChange.setLayout(null);
		
		// adding the two buttons
		JButton pc = new JButton("PIN Change");
		JButton nc = new JButton("Name Change");
		
		// placing the buttons
		pc.setBounds(85, 217, 232, 270);
		nc.setBounds(433, 217, 232, 270);
		
		// what to do in case each button is pressed
		pc.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				pChange.dispatchEvent(new WindowEvent(pChange, WindowEvent.WINDOW_CLOSING));
				showPinC();
			}
		});
		
		nc.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				pChange.dispatchEvent(new WindowEvent(pChange, WindowEvent.WINDOW_CLOSING));
				showNameC();
			}
		});
		
		// displaying the buttons
		pChangeLabel.add(pc);
		pChangeLabel.add(nc);
	}
	
	/*
	 *	Displaying the pin change interface
	 * no parameters necessary
	 */
	public void showPinC()
	{
		// the frame
		final JFrame spc = new JFrame();
		
		// setting the elements of the frame
		spc.setSize(768, 650);
		spc.setTitle("JXD BANK");
		//spc.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		spc.setLocationRelativeTo(null);
		JLabel spcLabel = new JLabel();
		
		// adding the background
		try
		{
			BufferedImage spcImg = ImageIO.read(new FileInputStream("images/spc.jpg"));
			spcLabel = new JLabel(new ImageIcon(spcImg));
			
			spc.add(spcLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot load password change image!");
		}
		
		// displaying the jframe and clearing the layout manager
		spc.setVisible(true);
		spc.setLayout(null);
		
		// creating the password fields
		final JPasswordField oldP = new JPasswordField();
		final JPasswordField newP = new JPasswordField();
		final JPasswordField newPConf = new JPasswordField();
		JButton k = new JButton("OK");
		JButton naw = new JButton("Cancel");
		
		// what to do in case each button is pressed
		k.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// local variables to this method
				int oldPIN, newPIN1, newPIN2;
				
				oldPIN = Integer.parseInt(oldP.getText());
				newPIN1 = Integer.parseInt(newP.getText());
				newPIN2 = Integer.parseInt(newPConf.getText());
				
				if((!customer.checkPIN(oldPIN)) || (newPIN1 != newPIN2))
				{
					showInvalidPINConf();
					oldP.setText("");
					newP.setText("");
					newPConf.setText("");
				}else
				{
     				customer.changePIN(newPIN2);
					customer.savePersonalInfo();
					spc.dispatchEvent(new WindowEvent(spc, WindowEvent.WINDOW_CLOSING));
					pinChanged();
				}
			}
		});
		
		naw.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				spc.dispatchEvent(new WindowEvent(spc, WindowEvent.WINDOW_CLOSING));
				showMainMenu();
			}
		});
		
		// defining the location of the password fields
		oldP.setBounds(400, 235, 50, 25);
		newP.setBounds(360, 355, 50, 25);
		newPConf.setBounds(515, 415, 50, 25);
		k.setBounds(379, 492, 108, 70);
		naw.setBounds(560, 492, 108, 70);
		
		// adding the password fields to the label to be displayed
		spcLabel.add(oldP);
		spcLabel.add(newP);
		spcLabel.add(newPConf);
		spcLabel.add(k);
		spcLabel.add(naw);
	}
	
	
	/*
	 *	Displays the error message when an invalid pin
	 * number is entered.
	 */
	public void showInvalidPINConf()
	{
		final JFrame ipn = new JFrame();
		
		ipn.setTitle("JXD BANK");
		ipn.setSize(500, 200);
		//ipn.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		ipn.setLayout(null);
		ipn.setLocationRelativeTo(null);
		ipn.setVisible(true);
		
		// the label and button
		JLabel conf = new JLabel("Invalid previous PIN or pin mismatch!");
		JButton cs = new JButton("Close");
		
		// setting location for the label and button
		conf.setBounds(100, 100, 200, 50);
		cs.setBounds(350, 110, 75, 25);
		
		// what to do for the button
		cs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// closes this frame and goes back to the main menu
				ipn.dispatchEvent(new WindowEvent(ipn, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		// adding the label and button
		ipn.add(conf);
		ipn.add(cs);
	}
	
	/*
	 * Displaying the name change interface
	 * no parameters necessary
	 */
	public void showNameC()
	{
		// creating the frame
		final JFrame snc = new JFrame();
		
		// setting the elements of the frame
		snc.setSize(768, 650);
		snc.setTitle("JXD BANK");
		//snc.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		snc.setLocationRelativeTo(null);
		JLabel sncLabel = new JLabel();
		
		// adding the background
		try
		{
			BufferedImage sncImg = ImageIO.read(new FileInputStream("images/snc.jpg"));
			sncLabel = new JLabel(new ImageIcon(sncImg));
			
			snc.add(sncLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot load name confirmation image!");
		}
		
		// allowing the frame to be seen
		snc.setVisible(true);
		snc.setLayout(null);
		
		// creating the input fields
		final JTextField fn = new JTextField();
		final JTextField fnC = new JTextField();
		final JTextField ln = new JTextField();
		final JTextField lnC = new JTextField();
		
		// adding the two buttons
		JButton k = new JButton("OK");
		JButton naw = new JButton("Cancel");
		
		// what to do in case each button is pressed
		k.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// local name variables
				String newFirst1, newFirst2, newLast1, newLast2;
				
				// storing data inside the variables
				newFirst1 = fn.getText();
				newFirst2 = fnC.getText();
				newLast1 = ln.getText();
				newLast2 = lnC.getText();
				
				if((!(newFirst1.equals(newFirst2))) || (!(newLast1.equals(newLast2))))
				{
					showInvalidNConf();
					fn.setText("");
					fnC.setText("");
					ln.setText("");
					lnC.setText("");
				}else
				{
					customer.changeFirstName(newFirst2);
					customer.changeLastName(newLast2);
					snc.dispatchEvent(new WindowEvent(snc, WindowEvent.WINDOW_CLOSING));
					nameChanged();
				}
			}
		});
		
		naw.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				snc.dispatchEvent(new WindowEvent(snc, WindowEvent.WINDOW_CLOSING));
				showMainMenu();
			}
		});
		
		// setting the location for the textfields and buttons
		fn.setBounds(365, 237, 150, 25);
		fnC.setBounds(507, 294, 150, 25);
		ln.setBounds(355, 397, 150, 25);
		lnC.setBounds(502, 451, 150, 25);
		k.setBounds(422, 523, 108, 70);
		naw.setBounds(603, 523, 108, 70);
		
		// adding the textfields to the label
		sncLabel.add(fn);
		sncLabel.add(fnC);
		sncLabel.add(ln);
		sncLabel.add(lnC);
		sncLabel.add(k);
		sncLabel.add(naw);
	}
	
	/*
	 *	Displays the error message when an names mismatch.
	 */
	public void showInvalidNConf()
	{
		final JFrame inn = new JFrame();
		
		inn.setTitle("JXD BANK");
		inn.setSize(500, 200);
		//inn.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		inn.setLayout(null);
		inn.setLocationRelativeTo(null);
		inn.setVisible(true);
		
		// the label and button
		JLabel conf = new JLabel("First or Last names mismatch!");
		JButton cs = new JButton("Close");
		
		// setting location for the label and button
		conf.setBounds(100, 100, 200, 50);
		cs.setBounds(350, 110, 75, 25);
		
		// what to do for the button
		cs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// closes this frame and goes back to the main menu
				inn.dispatchEvent(new WindowEvent(inn, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		// adding the label and button
		inn.add(conf);
		inn.add(cs);
	}
	
	public void nameChanged()
	{
		// creating the frame and setting parameters
		final JFrame gj = new JFrame();
		
		gj.setTitle("JXD BANK");
		gj.setSize(500, 200);
		//gj.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		gj.setLayout(null);
		gj.setLocationRelativeTo(null);
		gj.setVisible(true);
		
		// the label and button
		JLabel conf = new JLabel("Name changed successfully!");
		JButton cs = new JButton("Close");
		
		// setting location for the label and button
		conf.setBounds(100, 100, 200, 50);
		cs.setBounds(350, 110, 75, 25);
		
		// what to do for the button
		cs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// closes this frame and goes back to the main menu
				gj.dispatchEvent(new WindowEvent(gj, WindowEvent.WINDOW_CLOSING));
				showMainMenu();
			}
		});
		
		// adding the label and button
		gj.add(conf);
		gj.add(cs);
	}
	
	/*
	 *	Confirms changed pin.
	 * no parameters necessary.
	 */
	public void pinChanged()
	{
		// creating the frame and setting parameters
		final JFrame gj = new JFrame();
		
		gj.setTitle("JXD BANK");
		gj.setSize(500, 200);
		//gj.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		gj.setLayout(null);
		gj.setLocationRelativeTo(null);
		gj.setVisible(true);
		
		// the label and button
		JLabel conf = new JLabel("Pin changed successfully!");
		JButton cs = new JButton("Close");
		
		// setting location for the label and button
		conf.setBounds(100, 100, 200, 50);
		cs.setBounds(350, 110, 75, 25);
		
		// what to do for the button
		cs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// closes this frame and goes back to the main menu
				gj.dispatchEvent(new WindowEvent(gj, WindowEvent.WINDOW_CLOSING));
				showMainMenu();
			}
		});
		
		// adding the label and button
		gj.add(conf);
		gj.add(cs);
	}
	 
	/*
	 * The confirmation screen, called up from the transaction pages
	 * parameters: int where - an "ID" as to determine where this method was called from in case the user wishes to quit
	 * 				int amnt - amount of money that is being moved around
	 *					boolean at - indicates the account type
	 */
	public void showConfirmation(int where, int amnt, boolean at)
	{
		// creating the frame
		final JFrame conf = new JFrame();
		final int code = where;
		final int mula = amnt;
		final boolean accountType = at;
		final String cn = customer.user.getCardNum();
		
		conf.setSize(768, 650);
		conf.setTitle("JXD BANK");
		conf.setLocationRelativeTo(null);
		// conf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JLabel confLabel = new JLabel();
		
		// adding the image
		try
		{
			BufferedImage confImage = ImageIO.read(new FileInputStream("images/confImg.jpg"));
			confLabel = new JLabel(new ImageIcon(confImage));
			
			conf.add(confLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot find/load confirmation screen image file!");
		}
		
		// showing the frame
		conf.setVisible(true);
		conf.setLayout(null);
		
		// creating the buttons
		JButton ok = new JButton("OK");
		JButton restrt = new JButton("Redo");
		JButton canc = new JButton("Cancel");
		JLabel money = new JLabel("$" + mula);
		JLabel acc = new JLabel("Chequings account");
		JLabel trnsctn = new JLabel("Deposit");
		JLabel crdnm = new JLabel(cn);
		
		// initializing the transaction
		if(accTypeShow)
		{
			userAcc = customer.user.getSavings();
			other = customer.user.getChequing();
		}else
		{
			userAcc = customer.user.getChequing();
			other = customer.user.getSavings();
		}
		
		// transaction declaration
		action = new Transactions(userAcc);
					
		// checking which account is currently being used
		if (accTypeShow)
		{
			acc.setText("Savings account");
		}
		
		// finding out where this method was called from and displaying
		if (code == 2)
		{
			trnsctn.setText("Withdrawal");
		}else if (code == 3)
		{
			if (accTypeShow)
			{
				trnsctn.setText("Transfer from Savings to Chequings");
			}else
			{
				trnsctn.setText("Transfer from Chequings to Savings");
			}
		}
		
		// what to do when each button is pressed
		ok.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// what to do in each case
				// withdrawal
				if (code == 2)
				{
					if(!accTypeShow)
					{	// checking money amount
						if(customer.user.getChequingBal() >= mula)
						{
							// setting the action
							action.setAction(1);
							
							// the withdrawal
							action.withdraw(mula);
							
							// saving to the transactions file
							customer.saveTransaction(action,mula,other);
							
							// close the current window
							conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
				
							// back to the main menu
							showMainMenu();
						}else
						{
							// close the current window
							conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
							
							// display error message
							showNoMula();
						}
					}else
					{	// checking money amount
						if(customer.user.getSavingsBal() >= mula)
						{
							// the action
							action.setAction(1);
							
							// the withdrawal
							action.withdraw(mula);
							
							// saving to the transactions file
							customer.saveTransaction(action,mula,other);
							
							// close the current window
							conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
				
							// back to the main menu
							showMainMenu();
						}else
						{
							// close the current window
							conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
							
							// display error message
							showNoMula();
						}
					}
				}else if (code == 3)
				{
					// transfer
					if(!accTypeShow)
					{	// checking money amount
						if(customer.user.getChequingBal() >= mula)
						{
							// setting the action
							action.setAction(4);
							
							// transfer
							action.transferOut(mula, other);
							
							// saving to the transactions file
							customer.saveTransaction(action,mula,other);
							
							// close the current window
							conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
				
							// back to the main menu
							showMainMenu();
						}else
						{
							// close the current window
							conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
							
							// show error message
							showNoMula();
						}

					}else
					{	// checking money amount
						if(customer.user.getSavingsBal() >= mula)
						{
							// setting the action
							action.setAction(4);
							
							// transfer
							action.transferOut(mula, other);
							
							// saving to the transactions file
							customer.saveTransaction(action,mula,other);
							
							// close the current window
							conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
				
							// back to the main menu
							showMainMenu();
						}else
						{
							// close the current window
							conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
							
							// display error message
							showNoMula();
						}
					}
				}else
				{
					// setting the action
					action.setAction(2);
					
					// deposit
					action.deposit(mula);
					
					// saving to the transactions file
					customer.saveTransaction(action,mula,other);
					
					// close the current window
					conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
				
					// back to the main menu
					showMainMenu();
				}
			}
		});
		
		// where to go when the restart transaction button is pressed
		restrt.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
				
				// where to go to depending on where the program was called
				switch (code)
				{
					case 1:
						showDeposit();
						break;
						
					case 2:
						showWithdrawal();
						break;
						
					case 3:
						showTransfer();
						break;
				
						
					default:
						System.out.println("Problem determining where the program is!");
						System.exit(0);
						break;
				}
			}
		});
		
		// what to do in case the action is cancelled
		canc.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				conf.dispatchEvent(new WindowEvent(conf, WindowEvent.WINDOW_CLOSING));
				showMainMenu();
			}
		});
		
		// placing the buttons and text
		ok.setBounds(560, 415, 100, 100);
		restrt.setBounds(328, 415, 100, 100);
		canc.setBounds(98, 415, 100, 100);
		money.setBounds(350, 290, 100, 20);
		acc.setBounds(350, 210, 200, 20);
		trnsctn.setBounds(350, 250, 250, 20);
		crdnm.setBounds(350, 170, 100, 20);
		money.setForeground(Color.white);
		acc.setForeground(Color.white);
		trnsctn.setForeground(Color.white);
		crdnm.setForeground(Color.white);
		
		// adding the text to the screen and buttons
		confLabel.add(ok);
		confLabel.add(restrt);
		confLabel.add(canc);
		confLabel.add(money);
		confLabel.add(acc);
		confLabel.add(trnsctn);
		confLabel.add(crdnm);
	}
	
	/*
	 * Displaying that there is not enough money
	 * in the bank to perform operations.
	 */
	public void showNoMula()
	{
		// creating the frame
		final JFrame snm = new JFrame();
		
		snm.setTitle("JXD BANK");
		snm.setSize(500, 200);
		//snm.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		snm.setLayout(null);
		snm.setLocationRelativeTo(null);
		snm.setVisible(true);
		
		// the label and button
		JLabel conf = new JLabel("Not enough money in specified account to withdraw/transfer!");
		JButton cs = new JButton("Close");
		
		// setting location for the label and button
		conf.setBounds(10, 100, 375, 50);
		cs.setBounds(400, 110, 75, 25);
		
		// what to do for the button
		cs.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				// closes this frame and goes back to the main menu
				snm.dispatchEvent(new WindowEvent(snm, WindowEvent.WINDOW_CLOSING));
				showMainMenu();
			}
		});
		
		// adding the label and button
		snm.add(conf);
		snm.add(cs);
	}
	/*
	 * method will ask for user confirmation whether or not to exit.
	 * no parameters necessary
	 */
	public void confirmationExit(int whereAmI)
	{
		// utilizing the code for where the program is
		final int code = whereAmI;
		
		// creating a new frame for the confirmation
		final JFrame cefi = new JFrame();
		cefi.setSize(768,650);
		cefi.setTitle("JXD BANK");
		//cefi.setDefaultCloseOperation(EXIT_ON_CLOSE);
		cefi.setLocationRelativeTo(null);
		JLabel cefiLabel = new JLabel();
		
		// adding the background
		try
		{
			BufferedImage cefiImg = ImageIO.read(new FileInputStream("images/cefiImage.jpg"));
			cefiLabel = new JLabel(new ImageIcon(cefiImg));
			
			cefi.add(cefiLabel);
		}catch(IOException ioe)
		{
			System.out.println("Cannot find/load confirmation exit from input screen image file!");
		}
		
		// displaying the JFrame
		cefi.setVisible(true);
		cefi.setLayout(null);
		
		// the jbuttons
		JButton reallyExit = new JButton("I'm out!");
		JButton troll = new JButton("I'm not done yet!");
		
		reallyExit.setBounds(435, 230, 300, 125);
		troll.setBounds(25, 230, 350, 125);
		
		// what to do for all of the buttons
		reallyExit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				System.exit(0);
			}
		});
		
		troll.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				cefi.dispatchEvent(new WindowEvent(cefi, WindowEvent.WINDOW_CLOSING));
				
				// switch structure used to determine where the algorithm was called
				switch (code)
				{
					case 4:
						GhettoBankRunner gui = new GhettoBankRunner();
					 	gui.showWelcome();
						break;
						
					case 5:
						showMainMenu();
						break;
						
					case 6:
						showCardNumInput();
						break;
						
					default:
						System.out.println("Problem determining where the program is!");
						System.exit(0);
						break;
				}
			}
		});
		
		// actually displaying the buttons
		cefiLabel.add(reallyExit);
		cefiLabel.add(troll);
	}
	
	// the main method
	public static void main(String[] args)
	{
		GhettoBankRunner gui = new GhettoBankRunner();
		
		// a slight delay before the main menu pops out
		try
		{
			Thread.sleep(2048);
		}catch (Exception e)
		{
			System.out.println("Can't sleep!");
		}
		
		// displaying the screen(s)
		gui.showWelcome();
		//gui.showMainMenu();
	}
}