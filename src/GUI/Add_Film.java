package GUI;

import static Validator.SakilaValidator.areComboBoxValid;
import static Validator.SakilaValidator.areTextFieldValid;
import static Validator.SakilaValidator.clearPanel;
import static Validator.SakilaValidator.validateActor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Database.DbConnection;
import Exception.SakilaExpection;
import Model.Actor;
import Model.Film;
import static Validator.SakilaValidator.*;

public class Add_Film extends JPanel {
	JButton addFilmBtn, clearFilmAdd, addActorBtn;
	JTextField titleFld, releaseFld, lengthFld, replacementCostFld;
	JTextArea featuresFld, descriptionFld;
	JComboBox<String> durationFld,rateFld, categoryFld, languageFld, ratingFld;
	JPanel centerPanel;
	ArrayList<Actor> actors;
	

	private final static String[] DURATION_ARRAY = { "3","4","5","6","7"};
	private final static String[] RATE_ARRAY = { "0.99","2.99","4.99"};
	private final static String[] RATING_ARRAY = { "G","PG","PG-13","R","NC-17"};
	
	// constructor
	public Add_Film() {
		super();
		
		this.setBackground(new Color(255, 255, 255));
		this.setBorder(new EmptyBorder(20, 30, 30, 30));
		this.setLayout(new BorderLayout());

		/***********************************************************************************/
		centerPanel = new JPanel();
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

		JLabel descriptionLbl = new JLabel("Desription:");
		descriptionLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel featuresLbl = new JLabel("Special features:");
		featuresLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel addActorLbl = new JLabel(" ");
		addActorLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		titleFld = new JTextField();
		releaseFld = new JTextField();
		Vector<String> categories = null;
		try
		{
			categories = new DbConnection().getAllCategory();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		categoryFld = new JComboBox<String>(categories);
		categoryFld.setEditable(true);
		Vector<String> languages = null;
		try
		{
			languages = new DbConnection().getAllLanguages();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		languageFld = new  JComboBox<String>(languages);
		languageFld.setEditable(true);
		durationFld = new JComboBox<String>(DURATION_ARRAY);
		rateFld = new  JComboBox<String>(RATE_ARRAY);
		lengthFld = new JTextField();
		replacementCostFld = new JTextField();
		ratingFld = new JComboBox<String>(RATING_ARRAY);
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
		addFilmBtn.addActionListener(new AddFilmPage());
		clearFilmAdd.addActionListener(new AddFilmPage());
		btnPanel.add(addFilmBtn);
		btnPanel.add(clearFilmAdd);
		actors=new ArrayList<>();
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
            		if(areTextFieldValid(actorFirstName,actorLastName)) {
            			Actor actor = new Actor();
            			actor.setFirstName(actorFirstName.getText());
            			actor.setLastName(actorLastName.getText());
            			try
									{
										validateActor(actor);
										actors.add(actor);
									} catch (SakilaExpection e)
									{
										JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
									}
            		}
            	}
            }
        });
	}
	
	
	
//create inner class listener object
	private class AddFilmPage implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Add"))
			{			  
				boolean validTextFields = areTextFieldValid(titleFld, releaseFld, lengthFld, replacementCostFld);
				boolean validComboBoxes = areComboBoxValid(durationFld,rateFld, categoryFld, languageFld, ratingFld);
				boolean validTextArea = areTextAreasValid(featuresFld, descriptionFld);
				
				if( validTextFields && validComboBoxes && validTextArea) {
					Film film = getFilm();
					try
					{
						validateFilm(film);
						if(actors.isEmpty())
							JOptionPane.showMessageDialog(null, "Please enter at least one actor for the film.", "Error", JOptionPane.ERROR_MESSAGE);
						else {
							new DbConnection().insertFilmWithActors(film,actors);
							clearPanel(centerPanel);
							actors.removeAll(actors);
						}
					
					}  catch (Exception e1)
					{
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please enter the value in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}else if(e.getActionCommand().equals("Clear")) {
				clearPanel(centerPanel);

				actors.removeAll(actors);
			}
			
			
		}//end actionPerformed()

		
		private Film getFilm()
		{
			Film film = new Film();
			film.setTitle(titleFld.getText().trim());
			film.setCategory(categoryFld.getSelectedItem().toString().trim());
			film.setLanguage(languageFld.getSelectedItem().toString().trim());
			film.setRating(ratingFld.getSelectedItem().toString().trim());
			film.setRental_duration(Integer.parseInt(durationFld.getSelectedItem().toString().trim()));
			film.setRental_rate(Float.parseFloat(rateFld.getSelectedItem().toString().trim()));
			film.setDescription(descriptionFld.getText().trim());
			film.setSpecial_features(featuresFld.getText().trim());
			
			film.setStringRelease_year(releaseFld.getText().trim());
			film.setStringLength(lengthFld.getText().trim());
			film.setStringReplacement_cost(replacementCostFld.getText().trim());
			return film;
		}
	
	}//end inner class
}