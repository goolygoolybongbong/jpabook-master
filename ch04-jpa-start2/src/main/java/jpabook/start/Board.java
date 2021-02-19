package jpabook.start;

import javax.persistence.*;

@Entity
/*@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ",
        initialValue = 1,
        allocationSize = 10
)*/
public class Board {
    @Id
    @TableGenerator(
            name = "BOARD_SEQ_GENERATOR",
            table = "MY_SEQUENCES",
            pkColumnValue = "BOARD_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    private Long id;

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
