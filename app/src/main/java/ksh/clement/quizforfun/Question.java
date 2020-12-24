package ksh.clement.quizforfun;

public class Question {
    // QUESTIONS FOR DATABASE
    // Self Typed Questions
    public static Question[] DB_MATH_QUESTIONS =  {
            // Math Questions
            new Question("What is 10 + 8?", "18"),              // Question 1
            new Question("What is 150 - 64?", "86"),            // Question 2
            new Question("What is 14 * 5?", "70"),              // Question 3
            new Question("What is 100 divided by 25?", "4"),    // Question 4
            new Question("What is 58 + 93?", "151"),            // Question 5
            new Question("What is 78 - 32?", "46"),             // Question 6
            new Question("What is 70 * 0.5?", "35"),            // Question 7
            new Question("What is 10 / 2.5?", "4"),             // Question 8
            new Question("What is 10.4 + 31.3?", "41.7"),       // Question 9
            new Question("What is 19.6 - 50.0?", "-30.4"),      // Question 10
            new Question("What is -2.3 * 5?", "-11.5"),         // Question 11
            new Question("What is 0 / 14?", "0"),               // Question 12
            new Question("What is -65 + 32?", "-33"),           // Question 13
            new Question("What is 48 - 18?","30"),              // Question 14
            new Question("What is 321 * 0?", "0")               // Question 15
    };

    // Self typed questions
    public static Question[] DB_GEOGRAPHY_QUESTIONS = {
            new Question("How many countries are there in the world?", "195", "170", "82", "224"),                                                                  // Question 1
            new Question("What is the largest country in the world?","Russia", "America", "China", "Canada"),                                                       // Question 2
            new Question("What is the largest ocean?", "Pacific Ocean", "Atlantic Ocean", "Artic Ocean", "Indian Ocean"),                                                             // Question 3
            new Question("Which is the tallest mountain in the world?", "Everest", "K2", "Kangchenjunga", "Lhotse"),                                                // Question 4
            new Question("Which trench is the deepest in the world?", "Mariana Trench", "Tonga Trench", "Philippine Trench", "Kuril- Kamchatka Trench"),            // Question 5
            new Question("Which country has the largest population?", "China", "Singapore", "India", "United States"),                                              // Question 6
            new Question("Where is the tallest building located at?", "Burj Khalifa", "Shanghai Tower", "KXJB-TV Tower", "Makkah Royal Clock Tower"),               // Question 7
            new Question("How old is the Grand Canyon?", "5-6 million years old", "1-2 million years old", "3-4 million years old", "7-8 million years old"),       // Question 8
            new Question("Which is NOT part of the 7 wonders of the world?", "Mount Everest", "Temple of Artemis", "Colossus of Rhodes", "Great Pyramid of Giza"),  // Question 9
            new Question("Which is the largest continent?", "Asia", "Africa", "North America", "Europe"),                                                           // Question 10
            new Question("Which continent has the most countries?", "Africa", "North America", "Europe", "Asia"),                                                   // Question 11
            new Question("Which country has the highest population density?", "Macau", "Singapore", "Hong Kong", "Maldives"),                                       // Question 12
            new Question("Which is the smallest country in the world?", "Vatican City", "Monaco", "Nauru", "San Marino"),                                           // Question 13
            new Question("Which is the largest country in the world", "Russia", "Canada", "USA", "China"),                                                          // Question 14
            new Question("Which continent does Singapore belong to?", "Asia", "Australia", "Africa", "Europe")                                                      // Question 15
    };

    // Questions taken from https://topessaywriter.org/literature-general-knowledge-questions/
    // Possible Answers from Google
    public static Question[] DB_LITERATURE_QUESTIONS =  {
            // Literature Questions
            new Question("Tweedledum and Tweedledee are two characters of which Children’s book?", "Alice in Wonderland", "Charlotte's Web", "The Giving Tree", "The Cat in the Hat"),                                  // Question 1
            new Question("Who is the author of The Origin Of Species?", "Darwin", "William Shakespeare", "Emily Dickson", "Arthur Conan Doyle"),                                                                        // Question 2
            new Question("People of which religion consider ‘The Bhagavad Gita’ as their sacred text?", "Hinduism", "Buddhism", "Islam", "Judaism"),                                                                    // Question 3
            new Question("The abbreviated form of which book is NEB?", "New English Bible", "New English Book", "New Everyday Business", "Book of New England"),                                                        // Question 4
            new Question("Who invented the character, Robinson Crusoe?", "Daniel Defoe", "J.K Rowling", "Harold Robbins", "Dr. Seuss"),                                                                                 // Question 5
            new Question("Shakespeare’s four greatest tragedies are Macbeth, Othello, Hamlet and __________", "King Lear", "Romeo and Juliet", "Titus Andronicus", "Julius Caesar"),                                    // Question 6
            new Question("Name the mega-selling writer who has written “Deception Point”", "Dan Brown", "Oscar Wilde", "Sylvia Plath", "Anais Nin"),                                                                    // Question 7
            new Question("What is the meaning of the phrase ‘Mein Kampf’ in Hitler’s autobiography of the same name?", "My Struggle", "My Life", "My Story", "My Destination"),                                         // Question 8
            new Question("By what name was Shakespeare’s mother known before her marriage?", "Mary Arden", "Mary Magdalene", "Mary Shelley", "Mary Pickford"),                                                          // Question 9
            new Question("In which Charles Dickens’ novel we find the characters Bently Drummle, Joe Gargary, and Herbert Pocket?", "Great Expectations", "A Christmas Carol", "Oliver Twist", "David Copperfield"),    // Question 10
            new Question("Mention the exact number of sonnets written by William Shakespeare?", "154", "153", "159", "162"),                                                                                            // Question 11
            new Question("Name the only play written by Shakespeare in which there is no song.", "The Comedy Of Errors", "Hamlet", "Romeo and Juliet", "Macbeth"),                                                      // Question 12
            new Question("Mr. Freeze’ is the arch-enemy of a famous superhero of a comic book. What is the name of that superhero?", "Batman", "Spiderman", "Wolverine", "Catwoman"),                                   // Question 13
            new Question("In which novel of Roald Dahl, the character Willy Wonka can be found?", "Charlie And The Chocolate Factory.", "Matilda", "The Witches", "The BFG"),                                           // Question 14
            new Question("(The Mousetrap) is an interesting play. Who wrote this play?", "Agatha Christie", "William Shakespeare", "Tennessee Williams", "Oscar Wilde")                                                 // Question 15
    };


    // Instance Variables
    private String question;
    private String answer;
    private String[] option = new String[4];

    // Constructors
    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
        option[0] = answer;
        option[1] = null;
        option[2] = null;
        option[3] = null;
    }

    public Question(String question, String answer, String option1, String option2, String option3) {
        this.question = question;
        this.answer = answer;
        option[1] = option1;
        option[2] = option2;
        option[3] = option3;
        option[0] = answer;
    }

    // Getters
    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String[] getOption() {
        return option;
    }

    @Override
    public String toString() {
        return question;
    }
}
