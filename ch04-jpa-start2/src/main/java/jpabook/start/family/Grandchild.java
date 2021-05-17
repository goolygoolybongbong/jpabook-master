package jpabook.start.family;

import javax.persistence.*;

@Entity
public class Grandchild {
    @EmbeddedId
    private GrandchildId id;

    @MapsId("childId")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "CHILD_ID"),
            @JoinColumn(name = "PARENT_ID")
    })
    private Child child;

    private String name;

    // getter setter...

    public GrandchildId getId() {
        return id;
    }

    public void setId(GrandchildId id) {
        this.id = id;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
