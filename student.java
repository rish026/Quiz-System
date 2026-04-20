import java.io.*; 
import java.util.*; 

/** 
 * Student class extends User, implements Testable and Resultable. 
 * Uses OOP inheritance and interfaces. 
 */ 
public class Student extends User implements Testable, Resultable { 

    private List<Question> questions = new ArrayList<>(); 
    private List<Integer> answers = new ArrayList<>(); // user selected indices 
    private int currentQuestionIndex = 0; 

    public Student(String name) { 
        super(name); 
        initializeQuestions(); 
    } 

    /** 
     * Initialize 5 sample MCQ questions on Java basics. 
     */ 
    private void initializeQuestions() { 
        questions.add(new Question("What does OOP stand for?", 
            new String[]{"Object Oriented Programming", "Online Object Processing", "Object Oriented Python", "Open Oriented Platform"}, 0)); 

        questions.add(new Question("Which keyword is used for inheritance in Java?", 
            new String[]{"extends", "inherits", "implements", "super"}, 0)); 

        questions.add(new Question("Which class does not support inheritance?", 
            new String[]{"final class", "abstract class", "public class", "static class"}, 0)); 

        questions.add(new Question("What is the default value of int in Java?", 
            new String[]{"0", "null", "undefined", "-1"}, 0)); 

        questions.add(new Question("Which interface is used for List?", 
            new String[]{"java.util.List", "java.util.Array", "java.util.Map", "java.util.Set"}, 0)); 
    } 

    @Override 
    public void startQuiz() { 
        currentQuestionIndex = 0; 
        answers.clear(); 
        for (int i = 0; i < questions.size(); i++) { 
            answers.add(-1); // -1 means not answered 
        } 
    } 

    @Override 
    public void submitAnswer(int questionIndex, int selectedOption) { 
        if (questionIndex >= 0 && questionIndex < answers.size()) { 
            answers.set(questionIndex, selectedOption); 
        } 
    } 

    /** 
     * Get current question 
     */ 
    public Question getCurrentQuestion() { 
        return questions.get(currentQuestionIndex); 
    } 

    /** 
     * Move to next question (increment & check if more) 
     */ 
    public boolean nextQuestion() { 
        currentQuestionIndex++; 
        return currentQuestionIndex < questions.size(); 
    } 

    /** 
     * Check if there is a next question (without incrementing)
     */ 
    public boolean hasNextQuestion() { 
        return currentQuestionIndex + 1 < questions.size(); 
    }


    /** 
     * Get score 
     */ 
    public int getScore() { 
        int score = 0; 
        for (int i = 0; i < questions.size(); i++) { 
            int userAns = answers.get(i); 
            if (userAns != -1 && questions.get(i).isCorrect(userAns)) { 
                score++; 
            } 
        } 
        return score; 
    } 

    public int getTotalQuestions() { 
        return questions.size(); 
    } 

    public List<Integer> getAnswers() { return answers; } 
    public List<Question> getQuestions() { return questions; } 
    public int getCurrentIndex() { return currentQuestionIndex; } 

    @Override 
    public void displayRole() {
        System.out.println("Role: Student");
    }

    @Override 
    public void saveResult(int score, int total) { 
        try (PrintWriter writer = new PrintWriter(new FileWriter("Result.txt", true))) { 
            double percentage = (double) score / total * 100; 
            writer.println("Student: " + this.getName() + ", Score: " + score + "/" + total + ", Percentage: " + String.format("%.2f", percentage) + "%"); 
        } catch (IOException e) { 
            System.err.println("Error saving result: " + e.getMessage()); 
        } 
    } 
}
