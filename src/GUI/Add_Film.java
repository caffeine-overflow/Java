/**
 * Program Name	: Add_Film.java
 * Purpose			: A panel to add a new film and its actors
 * Author				: Prabin Gyawali (0877282) and Danish Davish (0691688)
 * Date					: Aug. 2, 2020
 */
package GUI;

import static Validator.SakilaValidator.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static Database.DbConnection.*;
import Exception.SakilaExpection;
import Model.Actor;
import Model.Film;

@SuppressWarnings("serial")
public class Add_Film extends JPanel {

	//Declare all the components
	JButton addFilmBtn, clearFilmAdd, addActorBtn;
	JTextField titleFld, releaseFld, lengthFld, replacementCostFld;
	JTextField featuresFld;
	JTextArea descriptionFld;
	JComboBox<String> durationFld,rateFld, categoryFld, languageFld, ratingFld;
	JLabel addActorLbl;
	JPanel centerPanel;
	ArrayList<Actor> actors;

	//Constants of string array representing duration, rate and rating
	private final static String[] DURATION_ARRAY = { "3","4","5","6","7"};
	private final static String[] RATE_ARRAY = { "0.99","2.99","4.99"};
	private final static String[] RATING_ARRAY = { "G","PG","PG-13","R","NC-17"};

	// constructor
	public Add_Film() {
		super();

		this.setBackground(new Color(255, 255, 255));
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout());

		/***********************************************************************************/
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(12, 2, 15, 5));
		this.add(centerPanel, BorderLayout.CENTER);

		JLabel titleLbl = new JLabel("Title:");
		titleLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel releaseLbl = new JLabel("Release year:");
		releaseLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel categoryLbl = new JLabel("Category:");
		categoryLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel languageLbl = new JLabel("Language:");
		languageLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel durationLbl = new JLabel("Rental duration (days):");
		durationLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel rateLbl = new JLabel("Rental rate ($):");
		rateLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lengthLbl = new JLabel("Length (min):");
		lengthLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel replacementCostLbl = new JLabel("Replacement cost($):");
		replacementCostLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel ratingLbl = new JLabel("Rating:");
		ratingLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel descriptionLbl = new JLabel("Desription:");
		descriptionLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel featuresLbl = new JLabel("Special features:");
		featuresLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		addActorLbl = new JLabel(" ");
		addActorLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		titleFld = new JTextField();
		releaseFld = new JTextField();
		//get all the film categories and languages
		Vector<String> categories = null;
		Vector<String> languages = null;
		try
		{
			categories = getAllCategory(); 
			languages = getAllLanguages();
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error while getting data from database. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		//Initilize all the component
		categoryFld = new JComboBox<String>(categories);
		categoryFld.setEditable(true);

		languageFld = new  JComboBox<String>(languages);
		languageFld.setEditable(true);
		durationFld = new JComboBox<String>(DURATION_ARRAY);
		rateFld = new  JComboBox<String>(RATE_ARRAY);
		lengthFld = new JTextField();
		replacementCostFld = new JTextField();
		ratingFld = new JComboBox<String>(RATING_ARRAY);
		featuresFld = new JTextField();
		descriptionFld = new JTextArea();
		addActorBtn = new JButton("Add Actors");

		//Add all the component to the panel
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
		//Add actor button
		actors=new ArrayList<>(); 
		//Add anonymous action listener to the add actor button
		addActorBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JComboBox<String> actorFirstName=null;
				try
				{
					actorFirstName = new JComboBox<String>(getAllActors()); // get all the actpr and add it to the the actor combobox
				} catch (SQLException e1)
				{
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				actorFirstName.setEditable(true);

				Object[] message = {
						"Actor Name: ", actorFirstName
				};

				Object[] options = {"Add Actor"};
				//Open a optainPane and add the actor combobox to the option pane
				int option = JOptionPane.showOptionDialog(centerPanel, message, "Add Actor",0,JOptionPane.PLAIN_MESSAGE,null,options,null);

				if (option == JOptionPane.OK_OPTION)
				{
					if(areComboBoxValid(actorFirstName)) { //if clicked okay, get the actor validate and add it
						String comma = "";
						Actor actor = new Actor();
						actor.setFirstName(actorFirstName.getSelectedItem().toString().split(" ")[0]);
						actor.setLastName(actorFirstName.getSelectedItem().toString().split(" ")[1]);

						try
						{
							validateActor(actor);
							if(actors.size()>0)  comma=", ";
							actors.add(actor);
							if(containsDuplicateActors(actors)) { //If the actor is already in the list, remove the duplicate
								JOptionPane.showMessageDialog(null, actor.getFirstName()+" already added for the film.", "Warning", JOptionPane.WARNING_MESSAGE);
								actors.remove(actors.size()-1);
							}
							else { //Add the actor to the actor list
								addActorLbl.setText(addActorLbl.getText()+comma+actor.getFirstName());
								JOptionPane.showMessageDialog(null, "Successfully added "+actor.getFirstName()+" for the film.", "Success", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (SakilaExpection ex)
						{
							JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Please select an Actor or enter a new actor Name", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}



	//create inner class Action listener object
	private class AddFilmPage implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand().equals("Add"))//IF clicked add add the film and actor
			{			  
				//Validate all the text fields and combo box and textAreas
				boolean validTextFields = areTextFieldValid(titleFld,featuresFld, releaseFld, lengthFld, replacementCostFld);
				boolean validComboBoxes = areComboBoxValid(durationFld,rateFld, categoryFld, languageFld, ratingFld);
				boolean validTextArea = areTextAreasValid(descriptionFld);
				//Add a new film if everything is valid
				if( validTextFields && validComboBoxes && validTextArea) {
					Film film = getFilm(); // call the getFilm function which returns a new film object created from the values entered
					try
					{
						//Validate and add film and actors
						validateFilm(film);
						if(actors.isEmpty())
							JOptionPane.showMessageDialog(null, "Please enter at least one actor for the film.", "Error", JOptionPane.ERROR_MESSAGE);
						else {
							insertFilmWithActors(film,actors);
							JOptionPane.showMessageDialog(null, "Successfully added "+titleFld.getText(), "Success", JOptionPane.INFORMATION_MESSAGE);
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
				//Reset the panel
				clearPanel(centerPanel);
				addActorLbl.setText("");
				actors.removeAll(actors);
			}


		}//end actionPerformed()


		/**
		 * 
		 * @return a film object created from the values enetred
		 */
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