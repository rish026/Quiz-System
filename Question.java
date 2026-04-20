/** 
 * Question model class for MCQ quiz. 
 */ 
public class Question { 
    public String questionText; 
    public String[] options; 
    public int correctAnswerIndex; // 0-3 

    public Question(String questionText, String[] options, int correctAnswerIndex) { 
        this.questionText = questionText; 
        this.options = options; 
        this.correctAnswerIndex = correctAnswerIndex; 
    } 

    // Getters 
    public String getQuestionText() { return questionText; } 
    public String[] getOptions() { return options; } 
    public int getCorrectAnswerIndex() { return correctAnswerIndex; } 

    /** 
     * Check if given answer index is correct 
     */ 
    public boolean isCorrect(int userAnswer) { 
        return userAnswer == correctAnswerIndex; 
    } 
}
