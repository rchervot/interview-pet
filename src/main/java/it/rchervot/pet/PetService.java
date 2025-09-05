package it.rchervot.pet;


import java.util.List;
import java.util.Optional;

public interface PetService {
    Optional<Pet> findById(Long id);

    List<Pet> findAll();

    Pet save(Pet pet);

    void deleteById(Long id);
}
