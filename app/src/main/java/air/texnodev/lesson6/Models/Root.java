package air.texnodev.lesson6.Models;

import java.util.ArrayList;

public class Root{
    public int id;
    public String question;
    public String image_q;
    public String correct_ans_alls;
    public ArrayList<String> answers;
    public int correct_answer;
    public String question_category;

    public Root(int id, String question, String image_q, String correct_ans_alls, ArrayList<String> answers, int correct_answer, String question_category) {
        this.id = id;
        this.question = question;
        this.image_q = image_q;
        this.correct_ans_alls = correct_ans_alls;
        this.answers = answers;
        this.correct_answer = correct_answer;
        this.question_category = question_category;
    }
}

