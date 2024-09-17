package com.example.demo.controller;

import com.example.demo.model.Trivia;
import com.example.demo.repository.TriviaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TriviaController {

    @Autowired
    TriviaRepository triviaRepository;

    @GetMapping("trivia/all")
    List<Trivia> getAllTrivias(){
        return triviaRepository.findAll();
    }

    @GetMapping("trivia/{id}")
    Trivia getByQuestion(@PathVariable Long id){

        Optional<Trivia> optinalEntity =  triviaRepository.findById(id);
        Trivia trivia = optinalEntity.get();

        return trivia;
    }

    @PostMapping("trivia/post/{q}/{a}")
    Trivia postTriviaByPath(@PathVariable String q, @PathVariable String a){
        Trivia trivia = new Trivia(q, a);
        triviaRepository.save(trivia);
        return trivia;
    }

    @PostMapping("trivia/post")
    Trivia postTriviaByPath(@RequestBody Trivia newTrivia){
        triviaRepository.save(newTrivia);
        return newTrivia;
    }

    @DeleteMapping("trivia/all/delete")
    List<Trivia> deleteAllTrivia(){
        triviaRepository.deleteAll();
        return triviaRepository.findAll();
    }

    @DeleteMapping("trivia/{id}/delete")
    String deleteById(@PathVariable Long id){
        Optional<Trivia> optionalEntity =  triviaRepository.findById(id);
        Trivia trivia = optionalEntity.get();

        if (trivia == null){
            return "no such trivia";
        }
        else {
            triviaRepository.deleteById(id);
            return "trivia#" + trivia.getId() + " was deleted";
        }
    }


}
