package com.hanghae99.dog.comment.entity;

import com.hanghae99.dog.comment.dto.CmtRequestDto;
import com.hanghae99.dog.animal.entity.Animal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;


    @Column
    private String username;

    @ManyToOne //(fetch = FetchType.EAGER)
    @JoinColumn(name="Animal_id", nullable = false)
    private Animal animal;

    @Column
    private Long commentlike = 0L;



    public Comment(CmtRequestDto cmtRequestDto, Animal animal, String username) { //댓글 작성
        this.content = cmtRequestDto.getContent();
        this.username = username;
        this.animal = animal;
    }


    public void update(CmtRequestDto cmtRequestDto) {
        this.content = cmtRequestDto.getContent();
    }

    public void setLike(long like) {
        this.commentlike = like;
    }











}
