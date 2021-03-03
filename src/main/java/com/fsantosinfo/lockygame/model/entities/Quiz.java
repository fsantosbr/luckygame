package com.fsantosinfo.lockygame.model.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Quiz implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 9, max = 75, message  = "A pergunta precisa ter entre 9 e 75 caracteres")
    private String question;

    @Size(min = 1, max = 30, message  = "Esta opção precisa ter entre 1 e 30 caracteres")
    private String option_1;
    @Size(min = 1, max = 30, message  = "Esta opção precisa ter entre 1 e 30 caracteres")
    private String option_2;
    @Size(min = 1, max = 30, message  = "Esta opção precisa ter entre 1 e 30 caracteres")
    private String option_3;

    @Max(3)
    @Min(1)
    @NotNull(message = "Precisa informar um valor")
    private Integer correctOption;

    @ManyToOne
    @JoinColumn(name = "lucky_game_id")  
    private LuckyGame luckyGame;

    public Quiz() {
    }

    
    public Quiz(Long id, String question, String option_1, String option_2, String option_3, Integer correctOption, LuckyGame luckyGame) {
        this.id = id;
        this.question = question;
        this.option_1 = option_1;
        this.option_2 = option_2;
        this.option_3 = option_3;
        this.correctOption = correctOption;
        this.luckyGame = luckyGame;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption_1() {
        return option_1;
    }

    public void setOption_1(String option_1) {
        this.option_1 = option_1;
    }

    public String getOption_2() {
        return option_2;
    }

    public void setOption_2(String option_2) {
        this.option_2 = option_2;
    }

    public String getOption_3() {
        return option_3;
    }

    public void setOption_3(String option_3) {
        this.option_3 = option_3;
    }

    public Integer getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(Integer correctOption) {
        this.correctOption = correctOption;
    }

    public LuckyGame getLuckyGame() {
        return luckyGame;
    }

    public void setLuckyGame(LuckyGame luckyGame) {
        this.luckyGame = luckyGame;
    }
   

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return Objects.equals(getId(), quiz.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
    
}
