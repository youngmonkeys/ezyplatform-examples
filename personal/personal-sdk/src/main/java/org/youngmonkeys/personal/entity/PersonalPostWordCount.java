package org.youngmonkeys.personal.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static org.youngmonkeys.personal.constant.PersonalConstants.TABLE_NAME_WORD_COUNT;

@Getter
@Setter
@ToString
@Entity
@Table(name = TABLE_NAME_WORD_COUNT)
@AllArgsConstructor
@NoArgsConstructor
public class PersonalPostWordCount {
    @Id
    @Column(name = "post_id")
    private long postId;

    @Column(name = "word_count")
    private long wordCount;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
