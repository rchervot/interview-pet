package it.rchervot.pet;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.springframework.lang.NonNull;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Pet {

    public static final String AGE_VIOLATION_MESSAGE = "The value must be positive";

    @Id()
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private Specie specie;

    @Column()
    @Min(value = 0, message = AGE_VIOLATION_MESSAGE)
    private int age;

    @Column()
    private String ownerName;

    public Pet() {
        name = "";
        specie = Specie.DOG;
    }

    public Pet(@NonNull String name, @NonNull Specie specie, int age) {
        this.name = name;
        this.specie = specie;
        this.age = age;
    }

    public Pet(@NonNull String name, @NonNull Specie specie, int age, String ownerName) {
        this(name, specie, age);
        this.ownerName = ownerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specie getSpecie() {
        return specie;
    }

    public void setSpecie(Specie specie) {
        this.specie = specie;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return age == pet.age && Objects.equals(id, pet.id) && Objects.equals(name, pet.name) && specie == pet.specie && Objects.equals(ownerName, pet.ownerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, specie, age, ownerName);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specie=" + specie +
                ", age=" + age +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}
