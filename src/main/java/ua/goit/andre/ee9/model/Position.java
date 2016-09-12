package ua.goit.andre.ee9.model;

import javax.persistence.*;

/**
 * Created by Andre on 28.05.2016.
 */
@Entity
@Table(name = "position")
public class Position {

    @Id
    @Column(name = "position_name")
    private String positionName;

    public Position() {
    }

    public Position(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return positionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        return getPositionName() != null ? getPositionName().equals(position.getPositionName()) : position.getPositionName() == null;

    }

    @Override
    public int hashCode() {
        return getPositionName() != null ? getPositionName().hashCode() : 0;
    }
}
