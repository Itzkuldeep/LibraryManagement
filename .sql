CREATE TABLE library.authors (
    Author_id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255)
);
CREATE TABLE library.book (
    Isbn VARCHAR(50) PRIMARY KEY,
    Title VARCHAR(255),
    Author_id INT,
    FOREIGN KEY (Author_id) REFERENCES library.authors(Author_id)

);
    
CREATE TABLE library.book_authors (
    Isbn VARCHAR(50),
    Author_id INT,
    FOREIGN KEY (Isbn) REFERENCES library.book(Isbn),
    FOREIGN KEY (Author_id) REFERENCES library.authors(Author_id)
);

CREATE TABLE library.borrower (
    Card_id INT PRIMARY KEY,
    Ssn VARCHAR(9) UNIQUE,
    Bname VARCHAR(255),
    Email VARCHAR(255),
    Address VARCHAR(255),
    City VARCHAR(255),
    State VARCHAR(255),
    Phone VARCHAR(10)

);

CREATE TABLE library.book_loans (
    Loan_id INT PRIMARY KEY,
    Isbn VARCHAR(13),
    Card_id INT,
    Date_out DATE,
    Due_date DATE,
    Date_in DATE,
    FOREIGN KEY (Isbn) REFERENCES library.book(Isbn),
    FOREIGN KEY (Card_id) REFERENCES library.borrower(Card_id)

);

CREATE TABLE library.fines (
    Loan_id INT PRIMARY KEY,
    Fine_amt DECIMAL(8, 2),
    Paid BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (Loan_id) REFERENCES library.book_loans(Loan_id)

);


INSERT INTO library.authors (Author_id, Name) VALUES
(1, 'Harper Lee'),
(2, 'George Orwell'),
(3, 'J.D. Salinger'),
(4, 'F. Scott Fitzgerald'),
(5, 'J.R.R. Tolkien'),
(6, 'Ray Bradbury'),
(7, 'Gabriel Garcia Marquez'),
(8, 'Aldous Huxley'),
(9, 'John Steinbeck');

INSERT INTO library.book (Isbn, Title, Author_id) VALUES
('978-0061120084', 'To Kill a Mockingbird', 1),
('978-0140187396', '1984', 2),
('978-0060935467', 'The Catcher in the Rye', 3),
('978-0062561029', 'The Great Gatsby', 4),
('978-0743273565', 'The Lord of the Rings', 5),
('978-0061122415', 'Fahrenheit 451', 6),
('978-0060916504', 'One Hundred Years of Solitude', 7),
('978-0679420253', 'Brave New World', 8),
('978-0060938413', 'The Grapes of Wrath', 9),
('978-0062315007', 'To Kill a Mockingbird', 1);


INSERT INTO library.book_authors (Isbn, Author_id) VALUES
('978-0061120084', 1),
('978-0140187396', 2),
('978-0060935467', 3),
('978-0062561029', 4),
('978-0743273565', 5),
('978-0061122415', 6),
('978-0060916504', 7),
('978-0679420253', 8),
('978-0060938413', 9),
('978-0062315007', 1);

