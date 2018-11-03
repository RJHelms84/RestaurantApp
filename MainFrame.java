
package RestaurantReviewApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Clock;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class MainFrame extends JFrame implements ActionListener
{
    //main view vars
    private JPanel mainView;
    private JButton selectChinese;
    private JButton selectMexican;
    private JButton selectIndian;
    private JButton selectItalian;
    private JButton selectBack;
    private JButton selectAdd;
    private JButton selectReview;
    
    //top panel vars
    private JPanel topPanel;
    private JPanel topButtonsPanel;
    private JTextField titleText;
    
    private JPanel searchPanel;
    private JTextField searchField;
    private JButton searchButton;
    
    //list view vars
    private JScrollPane listScrollPane;
    private JPanel listView;
    private ArrayList<JPanel> restaurantPanels;
    private Border listBorder;
    
    //add restaurant view vars
    private JPanel addView;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel descLabel;
    private JTextField descField;
    private JLabel imgLabel;
    private JTextField imgField;
    private JLabel catLabel;
    private JTextField catField;
    private JLabel errorLabel;
    private JButton submitButton;
    
    //add review view vars
    private JPanel reviewView;
    private JLabel restaurantLabel;
    private JTextField restaurantField;
    private JLabel scoreLabel;
    private JTextField scoreField;
    private JLabel errorReviewLabel;
    private JButton submitReviewButton;
    
    //current restaurant categories
    private enum category
    {
        CHINESE,
        MEXICAN,
        INDIAN,
        ITALIAN,
        SEARCHTERM
    }
    
    //enum used to specify the selected category
    category selectedCategory;
    
    //var for the searchTerm entered by user
    private String searchTerm;
    
    //used to check current view
    private enum view
    {
        MAIN,
        LIST,
        ADD,
        REVIEW
    }
    
    //the currently displayed view
    view currentView;
    
    //container for restaurant objects
    ArrayList<Restaurant> existingRestaurants;
    //container for displayed list of restaurants
    ArrayList<Restaurant> displayedRestaurants;
    
    //constructor - sets up initial state of views
    public MainFrame()
    {
        //make the X button work, to close app
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //stop users resizing
        setResizable(false);
        
        //arrays to hold the restaurants that exist
        //and the currently displayed ones in the list view
        existingRestaurants = new ArrayList();
        displayedRestaurants = new ArrayList();
        
        //create jpanel for main view
        mainView = new JPanel(new GridLayout(2,2));
        //create buttons for the restaurant category
        selectChinese = new JButton("Chinese");
        selectMexican = new JButton("Mexican");
        selectIndian = new JButton("Indian");
        selectItalian = new JButton("Italian");
        //create back button
        selectBack = new JButton("BACK TO HOME");
        //create add new restaurant button
        selectAdd = new JButton("ADD RESTAURANT");
        //create add review button
        selectReview = new JButton("ADD REVIEW");
        //add event listeners to the buttons
        selectChinese.addActionListener(this);
        selectMexican.addActionListener(this);
        selectIndian.addActionListener(this);
        selectItalian.addActionListener(this);
        selectBack.addActionListener(this);
        selectAdd.addActionListener(this);
        selectReview.addActionListener(this);
        //set size of main view
        mainView.setPreferredSize(new Dimension(500,500));        
        //add buttons to the main panel
        mainView.add(selectChinese);
        mainView.add(selectMexican);
        mainView.add(selectIndian);
        mainView.add(selectItalian);
        //add main panel to the jframe
        add(mainView, BorderLayout.CENTER);
        //update current view
        currentView = currentView.MAIN;
        
        //create jpanel for list view
        listView = new JPanel(new GridLayout(1,1));
        listView.setPreferredSize(new Dimension(500,500)); 
	listScrollPane = new JScrollPane(listView);
        listScrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        restaurantPanels = new ArrayList();
        listBorder = BorderFactory.createLineBorder(Color.black);
        
        //create jpanel for add restaurant view
        addView = new JPanel(new GridLayout(9,2));
        addView.setPreferredSize(new Dimension(500,500));
        nameLabel = new JLabel("     NAME:");
        nameField = new JTextField("");
        descLabel = new JLabel("     DESCRIPTION:");
        descField = new JTextField("");
        imgLabel = new JLabel("     IMAGE PATH:");
        imgField = new JTextField("");
        catLabel = new JLabel("     CATEGORY:");
        catField = new JTextField("");
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.red);
        submitButton = new JButton("SUBMIT RESTAURANT");
        submitButton.addActionListener(this);
        
        JLabel blankLabel = new JLabel("");
        
        addView.add(nameLabel);
        addView.add(nameField);
        addView.add(descLabel);
        addView.add(descField);
        addView.add(imgLabel);
        addView.add(imgField);
        addView.add(catLabel);
        addView.add(catField);
        addView.add(errorLabel);
        addView.add(submitButton);
        addView.add(blankLabel);
        addView.add(blankLabel);
        addView.add(blankLabel);
        addView.add(blankLabel);
        addView.add(blankLabel);
        addView.add(blankLabel);
        
        //create jpanel for add review score view
        reviewView = new JPanel(new GridLayout(9,2));
        reviewView.setPreferredSize(new Dimension(500,500));
        restaurantLabel = new JLabel("     RESTAURANT:");
        restaurantField = new JTextField("");
        scoreLabel = new JLabel("     SCORE (0-100):");
        scoreField = new JTextField("");
        errorReviewLabel = new JLabel("");
        errorReviewLabel.setForeground(Color.red);
        submitReviewButton = new JButton("SUBMIT REVIEW");
        submitReviewButton.addActionListener(this);
        reviewView.add(restaurantLabel);
        reviewView.add(restaurantField);
        reviewView.add(scoreLabel);
        reviewView.add(scoreField);
        reviewView.add(errorReviewLabel);
        reviewView.add(submitReviewButton);
        reviewView.add(new JLabel(""));
        reviewView.add(new JLabel(""));
        reviewView.add(new JLabel(""));
        reviewView.add(new JLabel(""));
        reviewView.add(new JLabel(""));
        reviewView.add(new JLabel(""));
        reviewView.add(new JLabel(""));
        reviewView.add(new JLabel(""));
        reviewView.add(new JLabel(""));
        reviewView.add(new JLabel(""));
        reviewView.add(new JLabel(""));
        reviewView.add(new JLabel(""));

        //top panel and title
        topPanel = new JPanel(new GridLayout(3,1));
        titleText = new JTextField("RESTAURANT REVIEW GUIDE");
        titleText.setEditable(false);
        titleText.setHorizontalAlignment(SwingConstants.CENTER);
        titleText.setFont(titleText.getFont().deriveFont(Font.BOLD));
        topPanel.add(titleText);

        //search bar
        searchTerm = new String("");
        //align search element to left
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //create search field and set size
        searchField = new JTextField("");
        searchField.setPreferredSize(new Dimension(400,30));
        //creat search button and add event listener
        searchButton = new JButton("SEARCH");
        searchButton.addActionListener(this);
        //add field and button to search panel
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        //add search panel to top panel
        topPanel.add(searchPanel);
        
        //top buttons panel
        topButtonsPanel = new JPanel(new GridLayout(1,2));
        topButtonsPanel.add(selectAdd);
        topButtonsPanel.add(selectReview);
        topPanel.add(topButtonsPanel);
        //add top panel to main frame
        add(topPanel, BorderLayout.NORTH);
        
        //sets size automatically based on components
        pack();
        
        //this.setSize(500, 500);
    }
    
    public static void main(String[] args)
    {
        new MainFrame().setVisible(true);
    }
    
    //used to switch between the various views/interfaces displayed
    private void SwitchToView(String newView)
    {
        if (newView.equals("main"))
        {
            if(currentView != currentView.MAIN)
            {
                //switch to main view
                if(currentView == currentView.LIST)
                {
                    remove(selectBack);
                    remove(listScrollPane); 
                }
                else if(currentView == currentView.ADD)
                {
                    //reset fields
                    nameField.setText("");
                    descField.setText("");
                    imgField.setText("");
                    catField.setText("");
                    errorLabel.setText("");
                    topPanel.add(topButtonsPanel);
                    remove(addView);
                }
                else if(currentView == currentView.REVIEW)
                {
                    //reset field
                    restaurantField.setText("");
                    scoreField.setText("");
                    errorReviewLabel.setText("");
                    topPanel.add(topButtonsPanel);
                    remove(reviewView);
                }
                
                add(mainView, BorderLayout.CENTER);
                currentView = currentView.MAIN;
                validate();
                repaint();
            }
        }
        else if(newView.equals("list"))
        {
            if(currentView != currentView.LIST)
            {
                //switch to list view
                PopulateFromDatabase();
                    
                if(currentView == currentView.MAIN)
                {
                    //add back button to the jframe
                    add(selectBack, BorderLayout.SOUTH);
                    remove(mainView);
                }
                else if(currentView == currentView.ADD)
                {
                    //reset fields
                    nameField.setText("");
                    descField.setText("");
                    imgField.setText("");
                    catField.setText("");
                    errorLabel.setText("");
                    topPanel.add(topButtonsPanel);
                    remove(addView);
                }
                else if(currentView == currentView.REVIEW)
                {
                    //reset field
                    restaurantField.setText("");
                    scoreField.setText("");
                    errorReviewLabel.setText("");
                    topPanel.add(topButtonsPanel);
                    remove(reviewView);
                }
                
                add(listScrollPane, BorderLayout.CENTER);
                currentView = currentView.LIST;
                validate();
                repaint();
            }
            else if(selectedCategory == selectedCategory.SEARCHTERM)
            {
                remove(listScrollPane);

                PopulateFromDatabase();

                add(listScrollPane, BorderLayout.CENTER);
                validate();
                repaint();
            }
        }
        else if(newView.equals("add"))
        {
            topPanel.remove(topButtonsPanel);
            validate();
            repaint();
            
            if(currentView == currentView.MAIN)
            {
                //add back button to the jframe
                add(selectBack, BorderLayout.SOUTH);
                remove(mainView);
            }
            else if(currentView == currentView.LIST)
            {
                remove(listScrollPane);
            }
            
            currentView = currentView.ADD;
            add(addView, BorderLayout.CENTER);
            validate();
            repaint();
        }
        else if(newView.equals("review"))
        {
            topPanel.remove(topButtonsPanel);
            validate();
            repaint();
            
            if(currentView == currentView.MAIN)
            {
                //add back button to the jframe
                add(selectBack, BorderLayout.SOUTH);
                remove(mainView);
            }
            else if(currentView == currentView.LIST)
            {
                remove(listScrollPane);
            }
            
            currentView = currentView.REVIEW;
            add(reviewView, BorderLayout.CENTER);
            validate();
            repaint();
        }
    }
    
    //only used during development
    public void CreateTable1()
    {
        Connection conn = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:src/RestaurantReviewApp/database.db");
            Statement stmt = conn.createStatement();
            String query = "CREATE TABLE restaurants (id integer PRIMARY KEY,"
                    +"name text NOT NULL,description text NOT NULL,"
                    +"imageUrl text NOT NULL,category text NOT NULL);";
            
            stmt.execute(query);

        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }

    }
    
    //only used during development
    public void CreateTable2()
    {
        Connection conn = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:src/RestaurantReviewApp/database.db");
            Statement stmt = conn.createStatement();
            String query = "CREATE TABLE reviews (id integer PRIMARY KEY,"
                    +"score integer NOT NULL,comments text NOT NULL,"
                    +"userName text NOT NULL,foreignKey integer NOT NULL);";
            stmt.execute(query);

        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }

    }
    
    //only used during development
    public void CreateTable3()//scores
    {
        Connection conn = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:src/RestaurantReviewApp/database.db");
            Statement stmt = conn.createStatement();
            String query = "CREATE TABLE scores (id integer PRIMARY KEY,"
                    +"score integer NOT NULL,"
                    +"foreignKey integer NOT NULL);";
            stmt.execute(query);

        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }

    }
    
    //only used during development
    public void CreateTestEntries()
    {
        Connection conn = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:src/RestaurantReviewApp/database.db");
            Statement stmt = conn.createStatement();
            //String query = 
            //        "insert into restaurants (name, description, imageUrl, category)"
            //        + " values (\"Gennaro's Italian Kitchen\", \"The best pasta and pizzas in town!\","
            //        + " \"src/RestaurantReviewApp/gennaros.jpg\", \"italian\");";
            String query = "update restaurants set imageUrl=\"images/mexican.png\" where category=\"mexican\"";
            stmt.execute(query);

        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }

    }
    
    //adds a new restaurant to the restaurants table
    //in the database, based on user input
    public void AddRestaurantToDatabase()
    {
        Connection conn = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:src/RestaurantReviewApp/database.db");
            Statement stmt = conn.createStatement();
            String query = "insert into restaurants (name, description, imageUrl, category)"
                    + " values (\"" + nameField.getText()
                    + "\", \"" + descField.getText()
                    + "\", \"" + imgField.getText().toLowerCase() + "\", \"" 
                    + catField.getText().toLowerCase() + "\");";
            System.out.println(query);
            stmt.execute(query);

        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }

    }
    
    //query the database to get the restaurant id
    //of a given restaurant name
    public int GetRestaurantID(String name)
    {
       Connection conn = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:src/RestaurantReviewApp/database.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT id FROM restaurants WHERE name=\""
                    + name + "\"";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            
            return rs.getInt(1);

        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        } 
        
        return 99;//failed
    }
    
    //query the database for the average score of a restaurant
    //from the provided id
    public int GetScore(int restaurantID)
    {
        Connection conn = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:src/RestaurantReviewApp/database.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT score FROM scores WHERE foreignKey="
                    + restaurantID + "";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            boolean more = rs.next();
            
            ArrayList<Integer> scoreList = new ArrayList<Integer>();
            
            while(more)
            {
                scoreList.add(rs.getInt(1));
                
                more = rs.next();
            }
            
            int total = 0;
            int totalScore = 0;
            if(scoreList.size() > 0)
            {
                for(int i = 0; i < scoreList.size(); i++)
                {
                    total = total + scoreList.get(i);
                }
                
                totalScore = total / scoreList.size();
            }
            
            return totalScore;
        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        } 
        
        return 0;
    }
    
    //adds new review to the database, using the restaurant id
    //as the foreign key in the scores table
    public void AddReviewToDatabase(int restaurantID)
    {
        //return if id fetch failed
        if(restaurantID == 99)
            return;
        
        Connection conn = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:src/RestaurantReviewApp/database.db");
            Statement stmt = conn.createStatement();
            String query = "insert into scores (score, foreignKey)"
                    + " values (" + Integer.parseInt(scoreField.getText()) + ", "
                    + restaurantID + ");";
            System.out.println(query);
            stmt.execute(query);

        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }

    }
    
    //populate restaurant classes in the displayedRestaurants array
    //from the database entries
    public void PopulateFromDatabase()
    {
        //fill restaurant classes from database
        PopulateExistingRestaurants();
        
        //populate the display list
        displayedRestaurants.clear();
        for(Restaurant restaurant : existingRestaurants)
        {
            if(selectedCategory == selectedCategory.CHINESE)
            {
                if(restaurant.DisplayCategory().equals("Chinese"))
                {
                    displayedRestaurants.add(restaurant);
                }
            }
            else if(selectedCategory == selectedCategory.MEXICAN)
            {
                if(restaurant.DisplayCategory().equals("Mexican"))
                {
                    displayedRestaurants.add(restaurant);
                }
            }
            else if(selectedCategory == selectedCategory.INDIAN)
            {
                if(restaurant.DisplayCategory().equals("Indian"))
                {
                    displayedRestaurants.add(restaurant);
                }
            }
            else if(selectedCategory == selectedCategory.ITALIAN)
            {
                if(restaurant.DisplayCategory().equals("Italian"))
                {
                    displayedRestaurants.add(restaurant);
                }
            }
            else if(selectedCategory == selectedCategory.SEARCHTERM)
            {
                if(restaurant.DisplayName().toLowerCase()
                        .contains(searchTerm))
                {
                    displayedRestaurants.add(restaurant);
                }
                else if(restaurant.DisplayDescription().toLowerCase()
                        .contains(searchTerm.toLowerCase()))
                {
                    displayedRestaurants.add(restaurant);
                }
                else if(restaurant.DisplayCategory().toLowerCase()
                        .contains(searchTerm.toLowerCase()))
                {
                    displayedRestaurants.add(restaurant);
                }
            }
        }

        restaurantPanels.clear();

        for(Restaurant restaurant : displayedRestaurants)
        {
            JPanel tempPanel = new JPanel();
            tempPanel.setLayout(new GridBagLayout());
            tempPanel.setBorder(listBorder);

            //fill components with class functions
            JTextField category = 
                    new JTextField("CATEGORY: " + restaurant.DisplayCategory());
            category.setEditable(false);
            JTextField name = 
                    new JTextField("NAME: " + restaurant.getName());
            name.setEditable(false);
            JTextArea description = 
                    new JTextArea("DESCRIPTION:\n" + restaurant.getDescription());
            description.setEditable(false);

            try 
            {
                BufferedImage image = ImageIO.read(new File(restaurant.getImageUrl()));
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                
                JLabel calculatedScoreLabel = new JLabel("SCORE: " 
                    + GetScore(restaurant.getId()) 
                    + " out of possible 100 (Average of all ratings)");

                //set constraints for grid
                GridBagConstraints constraints = new GridBagConstraints();

                constraints.fill = GridBagConstraints.HORIZONTAL;
                constraints.gridx = 0;
                constraints.gridy = 0;
                tempPanel.add(category, constraints);

                constraints.fill = GridBagConstraints.HORIZONTAL;
                constraints.gridx = 0;
                constraints.gridy = 1;
                tempPanel.add(name, constraints);

                constraints.fill = GridBagConstraints.HORIZONTAL;
                constraints.gridx = 0;
                constraints.gridy = 2;
                tempPanel.add(description, constraints);

                constraints.fill = GridBagConstraints.HORIZONTAL;
                constraints.gridx = 0;
                constraints.gridy = 3;
                tempPanel.add(imageLabel, constraints);

                constraints.fill = GridBagConstraints.HORIZONTAL;
                constraints.gridx = 0;
                constraints.gridy = 4;
                tempPanel.add(calculatedScoreLabel, constraints);

                restaurantPanels.add(tempPanel);
            } catch (IOException ex) 
            {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        listScrollPane.getViewport().remove(listView);
        listView.removeAll();
        listView = new JPanel(new GridLayout(restaurantPanels.size(),1));
        listScrollPane.getViewport().add(listView);

        for(JPanel panel : restaurantPanels)
        {
           listView.add(panel); 
        }


        //if no results found, display appropriate message
        if(displayedRestaurants.isEmpty())
        {
            JLabel endLabel = new JLabel("NO RESULTS FOUND!");
            endLabel.setPreferredSize(new Dimension(400,160));

            JPanel tempPanel = new JPanel();
            tempPanel.setLayout(new BoxLayout(tempPanel,BoxLayout.Y_AXIS));
            tempPanel.setBorder(listBorder);
            tempPanel.add(endLabel);
            listView.add(tempPanel);
        }
    }
    
    //query the restaurants from the database table
    //so they can fill the classes
    public void PopulateExistingRestaurants()
    {
        Connection conn = null;

        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(
                    "jdbc:sqlite:src/RestaurantReviewApp/database.db");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM restaurants";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            boolean more = rs.next();
            existingRestaurants.clear();
            while(more)
            {
                if(rs.getString(5).equals("chinese"))
                {
                    Restaurant temp = new ChineseRestaurant(rs.getInt(1),
                        rs.getString(2),rs.getString(3),rs.getString(4));

                    existingRestaurants.add(temp);
                }
                else if(rs.getString(5).equals("mexican"))
                {
                    Restaurant temp = new MexicanRestaurant(rs.getInt(1),
                        rs.getString(2),rs.getString(3),rs.getString(4));

                    existingRestaurants.add(temp);
                }
                else if(rs.getString(5).equals("indian"))
                {
                    Restaurant temp = new IndianRestaurant(rs.getInt(1),
                        rs.getString(2),rs.getString(3),rs.getString(4));

                    existingRestaurants.add(temp);
                }
                else if(rs.getString(5).equals("italian"))
                {
                    Restaurant temp = new ItalianRestaurant(rs.getInt(1),
                        rs.getString(2),rs.getString(3),rs.getString(4));

                    existingRestaurants.add(temp);
                }
                
                more = rs.next();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace(System.err);
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
    }
    
    //handle button-click action events
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //set the selected category based on the button pressed
        if(e.getSource() == selectChinese)
        {
            selectedCategory = selectedCategory.CHINESE;
            SwitchToView("list");
        }
        else if(e.getSource() == selectMexican)
        {
            selectedCategory = selectedCategory.MEXICAN;
            SwitchToView("list");
        }
        else if(e.getSource() == selectIndian)
        {
            selectedCategory = selectedCategory.INDIAN;
            SwitchToView("list");
        }
        else if(e.getSource() == selectItalian)
        {
            selectedCategory = selectedCategory.ITALIAN;
            SwitchToView("list");
            //CreateTestEntries();
        }
        else if(e.getSource() == searchButton)
        {
            //store text from search field in searchterm
            if(!searchField.getText().trim().equals(""))
            {
                searchTerm = searchField.getText().trim();
                searchField.setText("");//clear search field
                selectedCategory = selectedCategory.SEARCHTERM;
                SwitchToView("list");
            }
        }
        else if(e.getSource() == selectBack)
        {
            SwitchToView("main");
        }
        else if(e.getSource() == selectAdd)
        {
            SwitchToView("add");
        }
        else if(e.getSource() == selectReview)
        {
            SwitchToView("review");
        }
        else if(e.getSource() == submitButton)
        {
            //INPUT VALIDATION
            
            //make sure fields aren't blank
            if(nameField.getText().isEmpty() || descField.getText().isEmpty()
                    || imgField.getText().isEmpty() || catField.getText().isEmpty())
            {
                errorLabel.setText("     A FIELD IS BLANK!");
                return;
            }
            
            PopulateExistingRestaurants();
            
            //check restaurant doesn't already exist
            for(Restaurant rest : existingRestaurants)
            {
                if(rest.DisplayName().equals(nameField.getText()))
                {
                    errorLabel.setText("     NAME ALREADY EXISTS!");
                    return;
                }
            }
            
            //check category exists
            if(!(catField.getText().toLowerCase().equals("chinese")
                    || catField.getText().toLowerCase().equals("mexican")
                    || catField.getText().toLowerCase().equals("indian")
                    || catField.getText().toLowerCase().equals("italian")))
            {
                errorLabel.setText("     INVALID CATEGORY!");
                return;
            }
            
            //insert into restaurants table based on fields data
            AddRestaurantToDatabase();
            
            //reset fields
            nameField.setText("");
            descField.setText("");
            imgField.setText("");
            catField.setText("");
            errorLabel.setText("");
            
        }
        else if(e.getSource() == submitReviewButton)
        {
            //INPUT VALIDATION
            
            PopulateExistingRestaurants();
            
            //check restaurant exists
            boolean restaurantExists = false;
            for(Restaurant rest : existingRestaurants)
            {
                if(rest.DisplayName().equals(restaurantField.getText()))
                {
                    restaurantExists = true;
                }
            }
            if(!restaurantExists)
            {
                errorReviewLabel.setText("     RESTAURANT NOT FOUND!");
                return;
            }
            
            //check rating number is valid
            try
            {
                Integer.parseInt(scoreField.getText());
            }
            catch(NumberFormatException ex)
            {
                errorReviewLabel.setText("     INVALID SCORE!");
                
                return;
            }
            
            //check score is within required range (0-100)
            if(Integer.valueOf(scoreField.getText()) < 0
                    || Integer.valueOf(scoreField.getText()) > 100)
            {
               errorReviewLabel.setText("     INVALID SCORE!"); 
               return;
            }
            
            //if validation successful, add review to database
            AddReviewToDatabase(GetRestaurantID(restaurantField.getText()));
            
            //reset field
            restaurantField.setText("");
            scoreField.setText("");
            errorReviewLabel.setText("");
            
        }
        
    }
     
}