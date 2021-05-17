package jpabook.start;

import java.io.Serializable;

public class MemberProductId implements Serializable {
    private String member;
    private String product;

    // hashCode and equals


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MemberProductId)
            return  member.equals(((MemberProductId)obj).member) &&
                    product.equals(((MemberProductId)obj).product);
        else
            return false;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
