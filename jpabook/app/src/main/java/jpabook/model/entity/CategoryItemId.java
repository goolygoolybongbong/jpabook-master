package jpabook.model.entity;

import java.io.Serializable;

public class CategoryItemId implements Serializable {
    private Long category;
    private Long item;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CategoryItemId) {
            return ((CategoryItemId) obj).getCategory().equals(this.category) &&
                    ((CategoryItemId) obj).getItem().equals(this.item);
        }
        return false;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getItem() {
        return item;
    }

    public void setItem(Long item) {
        this.item = item;
    }
}