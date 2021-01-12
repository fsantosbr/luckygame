package com.fsantosinfo.lockygame.model.entities;

import java.io.Serializable;

public class LuckyNumber implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer myNumber;
    
    public LuckyNumber(){}

    public LuckyNumber(Long id, Integer myNumber) {
        this.id = id;
        this.myNumber = myNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMyNumber() {
        return myNumber;
    }

    public void setMyNumber(Integer myNumber) {
        this.myNumber = myNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((myNumber == null) ? 0 : myNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LuckyNumber other = (LuckyNumber) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (myNumber == null) {
            if (other.myNumber != null)
                return false;
        } else if (!myNumber.equals(other.myNumber))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return myNumber.toString();
    }

    
}
