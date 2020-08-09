/**
 * Program Name	: Reports.java
 * Purpose			: A panel to display income reports and best customer
 * Author				: Prabin Gyawali (0877282) and Danish Davish (0691688)
 * Date					: Aug. 2, 2020
 */
package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import static Database.DbConnection.*;
import java.util.Vector;

import javax.swing.*;

@SuppressWarnings("serial")
public class Reports extends JPanel
{

	// Variables declaration - do not modify                     
	private JCheckBox checkBoxStore1, checkBoxStore2;
	private JComboBox<String> comboBoxCategory, comboBoxFrom, comboTO;
	private JLabel lblTitleText,lblFilmCategory,lblReleaseYear,lblTo,lblStores;
	private JButton btnGetReport, btnGetBestCustomer;
	private Vector<String> yearList, categoryList;
	// End of variables declaration  

	//constructor
	public Reports()
	{
		super();//courtesy call

		//Initialize and add the components
		this.setBackground(new Color(250,250,250));
		lblTo = new JLabel("TO");
		lblStores = new JLabel("Store:");
		lblTitleText = new JLabel("Get Rental Income");
		lblTitleText.setFont(new Font("Tahoma", 0, 18)); // NOI18N
		lblFilmCategory = new JLabel("Film Category:");
		lblReleaseYear = new JLabel("Released Year:");
		lblTitleText.setHorizontalAlignment(SwingConstants.CENTER);

		checkBoxStore2 = new JCheckBox("#2");
		checkBoxStore1 = new JCheckBox("#1");

		yearList= new Vector<>();
		categoryList= new Vector<>();
		yearList.add("--Select--");
		categoryList.add("--All Films--");
		//get the category list from database
		try
		{
			categoryList.addAll(getAllCategory());
		} catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error while getting data from database. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		//populate the yearList from 1980 to 2020
		for(Integer i=1980;i<=2020;++i)
			yearList.add(i.toString());

		//Initialize all the components
		comboTO = new JComboBox<>();
		comboTO.setEnabled(false);

		comboBoxCategory = new JComboBox<>(categoryList);
		comboBoxFrom = new JComboBox<>(yearList);

		btnGetReport = new JButton("Get Income Report");
		btnGetReport.setFont(new Font("Tahoma", 0, 14)); // NOI18N
		btnGetReport.addActionListener(new GetReportListner()); // NOI18N


		btnGetBestCustomer = new JButton("Get Best Customer");
		btnGetBestCustomer.setFont(new Font("Tahoma", 0, 14)); // NOI18N
		btnGetBestCustomer.addActionListener(new GetBestCustomerListner()); // NOI18N

		//Create a group layout to add all the components
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		//place all the components
		placeItemsInGroup(layout);

		//Anonymous item listener for comboBox fromDate 
		comboBoxFrom.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				//If the from date is changed, add to date starting that from date and 2020
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int selected= comboBoxFrom.getSelectedIndex();						
					comboTO.removeAllItems();
					if(selected!=0) {
						for(Integer i=Integer.parseInt(yearList.get(selected));i<=2020;++i)
							comboTO.addItem(i.toString());
						comboTO.setEnabled(true);
					}
					else {
						comboTO.setSelectedIndex(-1);
						comboTO.setEnabled(false);
					}
				}
			}
		});

	}	//end constructor


	//create inner class Action listener for get report button
	private class GetReportListner implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int startDate=-1;
			int endDate=-1;
			String filmCategory = "all"; //default filmCategory is all
			String store = "both store"; //default store is both store
			String dateRange = ""; //default date range is empty
			String whereClause=" where 1=1 "; //set where clause to 1=1 because we will be adding the clause starting with and
			//To be valid at least one store should be selected
			if( checkBoxStore2.isSelected() || checkBoxStore1.isSelected()) {

				//If the from date is selected add the date range in where clause
				if (comboBoxFrom.getSelectedIndex() != 0) {
					startDate =Integer.parseInt(comboBoxFrom.getSelectedItem().toString()); 
					endDate =Integer.parseInt(comboTO.getSelectedItem().toString());
					whereClause += " and film.release_year>= '"+startDate+"'  and film.release_year<='"+endDate+"' ";
					dateRange+=" released between " + startDate +" and " +endDate;
				}
				//If selected get the category for the film
				if(comboBoxCategory.getSelectedIndex()!=0) {
					filmCategory = comboBoxCategory.getSelectedItem().toString();
					whereClause += " and category.name = '"+filmCategory+"'";
				}
				//At least one store should be selected
				if(checkBoxStore1.isSelected()) {
					if(!checkBoxStore2.isSelected()) {
						whereClause += " and inventory.store_id = 1";
						store = "store #1";
					}
				}
				else {
					whereClause += " and store.store_id = 2";
					store = "store #2";
				}

				try
				{
					float totalIncome = getIncomeReport(whereClause); //get the income report from the database
					String outputMessage = String.format("Total income for %s film%s at %s is $", filmCategory,dateRange,store);
					JOptionPane.showMessageDialog(null, outputMessage + totalIncome, "Income Report", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Please select at least one store", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}//end actionPerformed()



	//create inner class action listener object
	private class GetBestCustomerListner implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{			
			String store = "both store"; // default store is both
			String whereClause="";
			//check if at least one store is selected
			if( checkBoxStore2.isSelected() || checkBoxStore1.isSelected()) {
				if(checkBoxStore1.isSelected()) {
					if(!checkBoxStore2.isSelected()) {
						whereClause += " where inventory.store_id = 1";
						store = "store #1";
					}
				}
				else {
					whereClause += " where inventory.store_id = 2";
					store = "store #2";
				}

				try
				{
					//print the best customer for the selected store
					String outputMessage = String.format("\tBest customer for %s are\n\t\t%s", store,getBestCustomer(whereClause));
					JOptionPane.showMessageDialog(null, outputMessage , "Best Customer", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1)
				{
					JOptionPane.showMessageDialog(null, "Somethigng wrong in database: "+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Please select at least one store", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}//end actionPerformed()

	/**
	 * place all the component in group layout
	 * @param layout, a group layout where all the component are placed
	 */
	private void placeItemsInGroup(GroupLayout layout)
	{
		//The value for the group is set so that it look accurate in the screen.
		//set the component horizontally
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGap(36, 36, 36)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(lblFilmCategory, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(lblReleaseYear, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
												.addComponent(lblStores, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(checkBoxStore1)
														.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(checkBoxStore2))
												.addComponent(comboBoxCategory, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
												.addGroup(layout.createSequentialGroup()
														.addComponent(comboBoxFrom, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
														.addGap(18, 18, 18)
														.addComponent(lblTo, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(comboTO, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))
								.addGroup(layout.createSequentialGroup()
										.addComponent(btnGetReport, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
										.addGap(36, 36, 36)
										.addComponent(btnGetBestCustomer, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(55, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblTitleText, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
				);
		//set the component vertically
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblTitleText, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lblFilmCategory)
								.addComponent(comboBoxCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(24, 24, 24)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lblReleaseYear)
								.addComponent(comboBoxFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboTO, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTo))
						.addGap(22, 22, 22)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lblStores)
								.addComponent(checkBoxStore1)
								.addComponent(checkBoxStore2))
						.addGap(27, 27, 27)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(btnGetReport, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnGetBestCustomer, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(87, Short.MAX_VALUE))
				);
	}
}
//end class