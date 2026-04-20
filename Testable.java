/** 
 * Interface for quiz functionality. 
 */ 
public interface Testable { 
    void startQuiz(); 
    void submitAnswer(int questionIndex, int selectedOption); 
}
