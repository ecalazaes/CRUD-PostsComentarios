package com.ecalazaes.workshopmongo.services;

import com.ecalazaes.workshopmongo.dto.UserDTO;
import com.ecalazaes.workshopmongo.entities.User;
import com.ecalazaes.workshopmongo.repositories.UserRepository;
import com.ecalazaes.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id).
                orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void deleteById(String id){
        findById(id);
        userRepository.deleteById(id);
    }

    public User updateUser(String id, User user){
        return userRepository.findById(id)
                .map(userFound -> {
                    userFound.setName(user.getName());
                    userFound.setEmail(user.getEmail());
                    return userRepository.save(userFound);
                })
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado com o id: " + id));
    }

    public User fromDTO(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail());
    }
}
