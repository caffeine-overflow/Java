package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Add_Film extends JPanel {
	JButton addFilmBtn, clearFilmAdd, addActorBtn;
	JTextField titleFld, releaseFld, lengthFld, replacementCostFld, ratingFld;
	JTextArea featuresFld, descriptionFld;
	JComboBox durationFld,rateFld, categoryFld, languageFld;

	// constructor
	public Add_Film() {
		super();
		this.setBackground(new Color(255, 255, 255));
		this.setBorder(new EmptyBorder(20, 30, 30, 30));
		this.setLayout(new BorderLayout());

		/***********************************************************************************/
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(12, 2, 10, 10));
		this.add(centerPanel, BorderLayout.CENTER);

		JLabel titleLbl = new JLabel("Title:");
		titleLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel releaseLbl = new JLabel("Release year:");
		releaseLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel categoryLbl = new JLabel("Category:");
		categoryLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel languageLbl = new JLabel("Language:");
		languageLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel durationLbl = new JLabel("Rental duration:");
		durationLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel rateLbl = new JLabel("rental rate:");
		rateLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lengthLbl = new JLabel("Length:");
		lengthLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel replacementCostLbl = new JLabel("Replacement cost:");
		replacementCostLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel ratingLbl = new JLabel("Rating:");
		ratingLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel descriptionLbl = new JLabel("Special features:");
		descriptionLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel featuresLbl = new JLabel("Desription:");
		featuresLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel addActorLbl = new JLabel(" ");
		addActorLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		titleFld = new JTextField();
		releaseFld = new JTextField();
		String[] arr = { "array", "array2"};
		categoryFld = new JComboBox(arr);
		String[] arr2 = { "array", "array2"};
		languageFld = new  JComboBox(arr2);
		String[] durantionArray = { "3","4","5","6","7"};
		durationFld = new JComboBox(durantionArray);
		String[] rateArray = { "0.99","2.99","4.99"};
		rateFld = new  JComboBox(rateArray);
		lengthFld = new JTextField();
		replacementCostFld = new JTextField();
		ratingFld = new JTextField();
		featuresFld = new JTextArea();
		descriptionFld = new JTextArea();
		addActorBtn = new JButton("Add Actor");
		
		centerPanel.add(titleLbl);
		centerPanel.add(titleFld);
		centerPanel.add(categoryLbl);
		centerPanel.add(categoryFld);
		centerPanel.add(languageLbl);
		centerPanel.add(languageFld);
		centerPanel.add(releaseLbl);
		centerPanel.add(releaseFld);
		centerPanel.add(durationLbl);
		centerPanel.add(durationFld);
		centerPanel.add(rateLbl);
		centerPanel.add(rateFld);
		centerPanel.add(lengthLbl);
		centerPanel.add(lengthFld);
		centerPanel.add(replacementCostLbl);
		centerPanel.add(replacementCostFld);
		centerPanel.add(ratingLbl);
		centerPanel.add(ratingFld);
		centerPanel.add(featuresLbl);
		centerPanel.add(featuresFld);
		centerPanel.add(descriptionLbl);
		centerPanel.add(descriptionFld);
		centerPanel.add(addActorLbl);
		centerPanel.add(addActorBtn);

		/***********************************************************************************/
		JPanel btnPanel = new JPanel();
		btnPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnPanel.setLayout(new FlowLayout());

		this.add(btnPanel, BorderLayout.SOUTH);

		addFilmBtn = new JButton("Add");
		clearFilmAdd = new JButton("Clear");

		btnPanel.add(addFilmBtn);
		btnPanel.add(clearFilmAdd);
		
		addActorBtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
   
            	JTextField actorFirstName = new JTextField();
            	JTextField actorLastName = new JTextField();
            	
            	Object[] message = {
            	    "First Name:", actorFirstName,
            	    "Last Name:", actorLastName,
            	};
            	
            	Object[] options = {"Cancel","Save"};
                       
            	int option = JOptionPane.showConfirmDialog(centerPanel, message, "Add Actor", JOptionPane.OK_CANCEL_OPTION);
            	if (option == JOptionPane.OK_OPTION)
            	{
            	    String value1 = actorFirstName.getText();
            	    String value2 = actorLastName.getText();
            	    
            	    System.out.println(value1);
            	}
            }
        });
	}
}