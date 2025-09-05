package it.rchervot.pet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PetApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createPetNoName() {
        var pet = new Pet(null, Specie.CAT, 1);
        var r = restTemplate.postForEntity("http://localhost:" + port + "/pet", pet, Pet.class);
        assertNotNull(r);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, r.getStatusCode());
    }

    @Test
    void createPetNoSpecie() {
        var pet = new Pet("PetName", null, 1);
        var r = restTemplate.postForEntity("http://localhost:" + port + "/pet", pet, Pet.class);
        assertNotNull(r);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, r.getStatusCode());
    }

    @Test
    void createPetNegativeAge() {
        var pet = new Pet("PetName", Specie.CAT, -2);
        var r = restTemplate.postForEntity("http://localhost:" + port + "/pet", pet, Pet.class);
        assertNotNull(r);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, r.getStatusCode());
    }


    @Test
    void createPet() {
        var pet = new Pet("PetName", Specie.CAT, 2, "Jake");
        var savedPetResponse = restTemplate.postForEntity("http://localhost:" + port + "/pet", pet, Pet.class);
        assertEquals(HttpStatus.OK.value(), savedPetResponse.getStatusCode().value());

        var savedPet = savedPetResponse.getBody();
        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());

        pet.setId(savedPet.getId());
        assertEquals(pet, savedPet);
    }

    @Test
    void getPet() {
        var pet = restTemplate.getForObject("http://localhost:" + port + "/pet/1", Pet.class);
        assertNotNull(pet);
    }

    @Test
    void updatePet() {
        var petUrl = "http://localhost:" + port + "/pet/3";
        var pet = restTemplate.getForObject(petUrl, Pet.class);
        assertNotNull(pet);

        pet.setAge(pet.getAge() + 1);
        pet.setName(pet.getName() + " Updated");
        pet.setSpecie(pet.getSpecie() == Specie.CAT ? Specie.DOG : Specie.CAT);
        pet.setOwnerName("New Owner");

        restTemplate.put("http://localhost:" + port + "/pet/1", pet);

        var petUpdated = restTemplate.getForObject(petUrl, Pet.class);

        assertNotNull(petUpdated);
        assertEquals(pet, petUpdated);
    }

    @Test
    void deletePet() {
        var petUrl = "http://localhost:" + port + "/pet/2";
        var pet = restTemplate.getForObject(petUrl, Pet.class);
        assertNotNull(pet);
        restTemplate.delete(petUrl);
        var deletedPet = restTemplate.getForObject(petUrl, Pet.class);
        assertNull(deletedPet);
    }


}
