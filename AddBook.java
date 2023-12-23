import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.*;

public class AddBook extends JFrame {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField isbnField;

    void prepareGUI() throws SQLException{
        
        setTitle("Add Book");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create labels
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(20, 20, 80, 20);
        add(titleLabel);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(20, 50, 80, 20);
        add(authorLabel);

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setBounds(20, 80, 80, 20);
        add(isbnLabel);

        // Create text fields
        titleField = new JTextField();
        titleField.setBounds(100, 20, 150, 20);
        add(titleField);

        authorField = new JTextField();
        authorField.setBounds(100, 50, 150, 20);
        add(authorField);

        isbnField = new JTextField();
        isbnField.setBounds(100, 80, 150, 20);
        add(isbnField);

        // Create add button
        JButton addButton = new JButton("Add");
        addButton.setBounds(100, 120, 80, 20);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();

                addBook(title, author, isbn);
            }
        });
        add(addButton);
        JButton close=new JButton("Back");
        close.setBounds(200,120,80,20);
        add(close);
		close.addActionListener(new ActionListener()
		{
		public void actionPerformed(ActionEvent e) 
			{
                setVisible(false);
				new MainPage();
			}
		});
    }

    public void addBook(String title, String author, String isbn) {
        try {
            // Establishing a connection to the database
            Connection connection = DatabaseUtil.getConnection();

            // Fetching the author_id from the author table
            String authorIdQuery = "SELECT author_id FROM authors WHERE name = ?";
            PreparedStatement authorIdStatement = connection.prepareStatement(authorIdQuery);
            authorIdStatement.setString(1, author);
            ResultSet authorIdResult = authorIdStatement.executeQuery();

            int authorId = 0;
            if (authorIdResult.next()) {
                authorId = authorIdResult.getInt("author_id");
            // ...

                        } else {
                            // Creating a new author
                            String createAuthorQuery = "INSERT INTO authors (name) VALUES (?)";
                            PreparedStatement createAuthorStatement = connection.prepareStatement(createAuthorQuery, Statement.RETURN_GENERATED_KEYS);
                            createAuthorStatement.setString(1, author);
                            createAuthorStatement.executeUpdate();

                            ResultSet generatedKeys = createAuthorStatement.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                authorId = generatedKeys.getInt(1);
                            }

                            createAuthorStatement.close();
                            generatedKeys.close();
                            String bookAuthorQuery = "INSERT INTO book_author (isbn, author_id) VALUES (?, ?)";
                            PreparedStatement bookAuthorStatement = connection.prepareStatement(bookAuthorQuery);
                            bookAuthorStatement.setString(1, isbn);
                            bookAuthorStatement.setInt(2, authorId);
                            bookAuthorStatement.executeUpdate();

                            bookAuthorStatement.close();
                        }

            // Creating a prepared statement to insert book details into the database
            String query = "INSERT INTO book (title, isbn, author_id) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, isbn);
            statement.setInt(3, authorId);

            // Executing the prepared statement to insert the book into the database
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Book added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add book.");
            }
            // Closing the database connection, statements, and result set
            statement.close();
            authorIdStatement.close();
            authorIdResult.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    AddBook() throws SQLException
	{
		prepareGUI();
	}
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
                    AddBook frame = new AddBook();
                    frame.setVisible(true);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
    }
}

