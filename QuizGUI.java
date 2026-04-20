import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

/** 
 * QuizGUI - Question text FIXED to show properly 
 */ 
public class QuizGUI extends JFrame { 
    private Student student; 
    private CardLayout cardLayout; 
    private JPanel mainPanel; 

    // Quiz components 
    private JLabel lblQuestion, lblTimer, lblProgress; 
    private JRadioButton[] rbOptions = new JRadioButton[4]; 
    private ButtonGroup optionGroup; 
    private JButton btnNext; 
    private Timer quizTimer; 
    private int timeLeft = 30; 

    public QuizGUI() { 
        setTitle("Quiz Examination System"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(900, 700); 
        setLocationRelativeTo(null); 

        initUI(); 
        setVisible(true); 
    } 

    private void initUI() { 
        cardLayout = new CardLayout(); 
        mainPanel = new JPanel(cardLayout); 

        mainPanel.add(createLoginPanel(), "login"); 
        mainPanel.add(createQuizPanel(), "quiz"); 
        mainPanel.add(createResultPanel(), "result"); 

        add(mainPanel); 
        cardLayout.show(mainPanel, "login"); 
    } 

    private JPanel createLoginPanel() { 
        JPanel panel = new JPanel(new BorderLayout(20, 40)); 
        panel.setBackground(new Color(52, 152, 219)); 

        // Title 
        JPanel titlePanel = new JPanel(); 
        titlePanel.setBackground(new Color(52, 152, 219)); 
        JLabel title = new JLabel("Quiz Examination System", SwingConstants.CENTER); 
        title.setForeground(Color.WHITE); 
        title.setFont(new Font("Arial", Font.BOLD, 32)); 
        titlePanel.add(title); 
        panel.add(titlePanel, BorderLayout.NORTH); 

        // Form 
        JPanel formPanel = new JPanel(); 
        formPanel.setBackground(new Color(52, 152, 219)); 
        JLabel lblName = new JLabel("Enter Student Name:"); 
        lblName.setForeground(Color.WHITE); 
        lblName.setFont(new Font("Arial", Font.BOLD, 18)); 
        JTextField txtName = new JTextField(20); 
        txtName.setFont(new Font("Arial", Font.PLAIN, 16)); 
        JButton startBtn = new JButton("START QUIZ"); 
        startBtn.setFont(new Font("Arial", Font.BOLD, 16)); 
        startBtn.setBackground(Color.ORANGE); 
        startBtn.setForeground(Color.WHITE); 
        startBtn.setPreferredSize(new Dimension(150, 50)); 

        formPanel.add(lblName); 
        formPanel.add(txtName); 
        formPanel.add(startBtn); 
        panel.add(formPanel, BorderLayout.CENTER); 

        startBtn.addActionListener(e -> { 
            String name = txtName.getText().trim(); 
            if (!name.isEmpty()) { 
                student = new Student(name); 
                student.startQuiz(); 
                cardLayout.show(mainPanel, "quiz"); 
                showQuestion(); 
                startTimer(); 
            } else { 
                JOptionPane.showMessageDialog(this, "Please enter your name!"); 
            } 
        }); 

        return panel; 
    } 

    private JPanel createQuizPanel() { 
        JPanel mainQuizPanel = new JPanel(new BorderLayout()); 

        // TOP: Timer + Progress 
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20)); 
        topPanel.setBackground(Color.WHITE); 
        lblTimer = new JLabel("Time: 30s"); 
        lblTimer.setFont(new Font("Arial", Font.BOLD, 24)); 
        lblTimer.setForeground(Color.RED); 
        lblTimer.setPreferredSize(new Dimension(150, 40)); 
        lblProgress = new JLabel("Question 1/5"); 
        lblProgress.setFont(new Font("Arial", Font.BOLD, 20)); 
        lblProgress.setForeground(Color.GRAY); 
        topPanel.add(lblTimer); 
        topPanel.add(lblProgress); 
        mainQuizPanel.add(topPanel, BorderLayout.NORTH); 

        // QUESTION PANEL - FIXED VISIBILITY 
        JPanel questionPanel = new JPanel(); 
        questionPanel.setBackground(Color.WHITE); 
        questionPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); 
        lblQuestion = new JLabel("", SwingConstants.CENTER); 
        lblQuestion.setFont(new Font("Arial", Font.BOLD, 22)); 
        lblQuestion.setForeground(Color.DARK_GRAY); 
        lblQuestion.setPreferredSize(new Dimension(800, 120)); 
        lblQuestion.setVerticalAlignment(SwingConstants.TOP); 
        lblQuestion.setOpaque(true); 
        lblQuestion.setBackground(Color.LIGHT_GRAY); 
        lblQuestion.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); 
        questionPanel.add(lblQuestion); 
        mainQuizPanel.add(questionPanel, BorderLayout.NORTH); 

        // OPTIONS PANEL 
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 0, 15)); 
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60)); 
        optionsPanel.setBackground(Color.WHITE); 
        optionGroup = new ButtonGroup(); 
        for (int i = 0; i < 4; i++) { 
            rbOptions[i] = new JRadioButton(); 
            rbOptions[i].setFont(new Font("Arial", Font.PLAIN, 18)); 
            rbOptions[i].setBackground(Color.WHITE); 
            rbOptions[i].setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
            optionGroup.add(rbOptions[i]); 
            optionsPanel.add(rbOptions[i]); 
        } 
        mainQuizPanel.add(optionsPanel, BorderLayout.CENTER); 

        // NEXT BUTTON 
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        buttonPanel.setBackground(Color.WHITE); 
        btnNext = new JButton("Next →"); 
        btnNext.setFont(new Font("Arial", Font.BOLD, 18)); 
        btnNext.setBackground(new Color(46, 204, 113)); 
        btnNext.setForeground(Color.WHITE); 
        btnNext.setPreferredSize(new Dimension(150, 50)); 
        btnNext.setFocusPainted(false); 
        btnNext.addActionListener(e -> handleNext()); 
        buttonPanel.add(btnNext); 
        mainQuizPanel.add(buttonPanel, BorderLayout.SOUTH); 

        return mainQuizPanel; 
    } 

    private void showQuestion() { 
        // Clear selection 
        optionGroup.clearSelection(); 

        // Get current question 
        Question q = student.getCurrentQuestion(); 
        
        // 🔥 FIXED - Question now VISIBLE with large font + background 
        lblQuestion.setText((student.getCurrentIndex() + 1) + ". " + q.getQuestionText()); 
        lblQuestion.repaint(); 

        // Load options 
        for (int i = 0; i < 4; i++) { 
            rbOptions[i].setText((char)('A' + i) + ". " + q.getOptions()[i]); 
        } 

        // Update UI 
        lblProgress.setText("Question " + (student.getCurrentIndex() + 1) + "/5"); 
        btnNext.setText(student.hasNextQuestion() ? "Next →" : "Finish Quiz"); 

        // Reset timer 
        timeLeft = 30; 
        updateTimerDisplay(); 
    }

    private void handleNext() { 
        // Save answer 
        int selected = -1; 
        for (int i = 0; i < 4; i++) { 
            if (rbOptions[i].isSelected()) { 
                selected = i; 
                break; 
            } 
        } 
        
        int currentQNum = student.getCurrentIndex();
        student.submitAnswer(currentQNum, selected); 

        // Handle no selection (timeout or skip)
        if (selected == -1) {
            student.submitAnswer(currentQNum, -1); // Mark as unanswered
        }

        // Stop timer & advance 
        if (quizTimer != null) quizTimer.stop(); 

        if (student.nextQuestion()) { 
            showQuestion(); 
            startTimer(); 
        } else { 
            int score = student.getScore(); 
            showResult(score); 
        } 
    }

    private void startTimer() { 
        quizTimer = new Timer(1000, e -> { 
            timeLeft--; 
            updateTimerDisplay(); 
            if (timeLeft <= 0) { 
                quizTimer.stop(); 
                JOptionPane.showMessageDialog(this, "Time's Up! Auto Next"); 
                handleNext(); 
            } 
        }); 
        quizTimer.start(); 
    } 

    private void updateTimerDisplay() { 
        lblTimer.setText("Time: " + timeLeft + "s"); 
    } 

    private JPanel createResultPanel() { 
        JPanel panel = new JPanel(new BorderLayout()); 
        panel.setBackground(Color.LIGHT_GRAY); 

        JLabel congrats = new JLabel("Quiz Completed! ✓", SwingConstants.CENTER); 
        congrats.setFont(new Font("Arial", Font.BOLD, 28)); 
        congrats.setForeground(new Color(39, 174, 96)); 
        panel.add(congrats, BorderLayout.CENTER); 

        JButton restartBtn = new JButton("Take Quiz Again"); 
        restartBtn.setFont(new Font("Arial", Font.BOLD, 16)); 
        restartBtn.setBackground(Color.ORANGE); 
        restartBtn.setPreferredSize(new Dimension(200, 50)); 
        restartBtn.addActionListener(e -> cardLayout.show(mainPanel, "login")); 
        panel.add(restartBtn, BorderLayout.SOUTH); 

        return panel; 
    } 

    private void showResult(int score) { 
        int total = student.getTotalQuestions(); 
        double percentage = (double) score / total * 100; 

        String result = "Excellent Work " + student.getName() + "!\n\n" + 
                       "Score: " + score + "/" + total + "\n" + 
                       "Percentage: " + String.format("%.1f", percentage) + "%"; 

        JOptionPane.showMessageDialog(this, result, "Quiz Result", JOptionPane.INFORMATION_MESSAGE); 
        student.saveResult(score, total); 
        cardLayout.show(mainPanel, "result"); 
    } 
}

