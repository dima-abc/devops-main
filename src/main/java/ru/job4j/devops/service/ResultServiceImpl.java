package ru.job4j.devops.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.devops.models.Result;
import ru.job4j.devops.repository.ResultRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService{
    private final ResultRepository resultRepository;
    @Override
    public void save(Result result) {
        resultRepository.save(result);
        log.debug("Saved result: {}", result);
    }

    @Override
    public List<Result> findAll() {
        return resultRepository.findAll();
    }
}
