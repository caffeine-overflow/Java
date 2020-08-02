
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Add_Actor extends JPanel {
	JButton addActorBtn, clearActorBtn;
	JTextField actorFirstNameFld, actorLastNameFld;

	// constructor
	public Add_Actor() {
		super();
		this.setBorder(new EmptyBorder(20, 10, 30, 10));

		/***********************************************************************************/
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 2, 30, 30));
		this.add(centerPanel);

		JLabel actorFirstNameLbl = new JLabel("First Name:");
		actorFirstNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel actorLastNameLbl = new JLabel("Last Name:");
		actorLastNameLbl.setHorizontalAlignment(SwingConstants.RIGHT);


		actorFirstNameFld = new JTextField();
		actorLastNameFld = new JTextField();

		centerPanel.add(actorFirstNameLbl);
		centerPanel.add(actorFirstNameFld);
		centerPanel.add(actorLastNameLbl);
		centerPanel.add(actorLastNameFld);

		/***********************************************************************************/
		JPanel btnPanel = new JPanel();

		btnPanel.setLayout(new FlowLayout());

		this.add(btnPanel);

		addActorBtn = new JButton("Add");
		clearActorBtn = new JButton("Clear");

		btnPanel.add(addActorBtn);
		btnPanel.add(clearActorBtn);
	}
}