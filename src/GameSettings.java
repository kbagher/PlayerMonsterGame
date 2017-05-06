import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;

/**
 * @author  Administrator
 * @created May 6, 2017
 */
public class GameSettings extends JPanel implements ActionListener
{
    JButton btBut0;
    JButton btBut1;
    JButton btBut2;
    JButton btBut3;

    /**
     *Constructor for the Panel0 object
     */
    public GameSettings()
    {
        super();

        GridBagLayout gbPanel0 = new GridBagLayout();
        GridBagConstraints gbcPanel0 = new GridBagConstraints();
        setLayout( gbPanel0 );

        btBut0 = new JButton( ""  );
        btBut0.addActionListener( this );
        gbcPanel0.gridx = 0;
        gbcPanel0.gridy = 0;
        gbcPanel0.gridwidth = 7;
        gbcPanel0.gridheight = 4;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( btBut0, gbcPanel0 );
        add( btBut0 );

        btBut1 = new JButton( ""  );
        btBut1.addActionListener( this );
        gbcPanel0.gridx = 2;
        gbcPanel0.gridy = 6;
        gbcPanel0.gridwidth = 8;
        gbcPanel0.gridheight = 4;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( btBut1, gbcPanel0 );
        add( btBut1 );

        btBut2 = new JButton( ""  );
        btBut2.addActionListener( this );
        gbcPanel0.gridx = 4;
        gbcPanel0.gridy = 12;
        gbcPanel0.gridwidth = 9;
        gbcPanel0.gridheight = 4;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( btBut2, gbcPanel0 );
        add( btBut2 );

        btBut3 = new JButton( ""  );
        btBut3.addActionListener( this );
        gbcPanel0.gridx = 9;
        gbcPanel0.gridy = 1;
        gbcPanel0.gridwidth = 8;
        gbcPanel0.gridheight = 4;
        gbcPanel0.fill = GridBagConstraints.BOTH;
        gbcPanel0.weightx = 1;
        gbcPanel0.weighty = 0;
        gbcPanel0.anchor = GridBagConstraints.NORTH;
        gbPanel0.setConstraints( btBut3, gbcPanel0 );
        add( btBut3 );
    }

    /**
     */
    public void actionPerformed( ActionEvent e )
    {
        if ( e.getSource() == btBut0 )
        {
            // Action for btBut0
        }
        if ( e.getSource() == btBut1 )
        {
            // Action for btBut1
        }
        if ( e.getSource() == btBut2 )
        {
            // Action for btBut2
        }
        if ( e.getSource() == btBut3 )
        {
            // Action for btBut3
        }
    }
}
