package com.excilys.java.cdb.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.java.cdb.dto.ComputerDTO;
import com.excilys.java.cdb.dto.mappers.ComputerDtoMapper;
import com.excilys.java.cdb.model.Computer;
import com.excilys.java.cdb.service.ComputerService;

@RestController
@RequestMapping("ListComputer")
public class ComputerRestController {

    @Autowired
    private ComputerService computerService;

    @GetMapping
    public List<ComputerDTO> listComputers() {
        List<Computer> allComputers = computerService.getAll();
        return allComputers.stream().map(c -> ComputerDtoMapper.convertToComputerDTO(c)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ComputerDTO getComputer(@PathVariable Long id) {
        ComputerDTO dto = new ComputerDTO();
        if (computerService.getById(id)!=null) {
            dto = ComputerDtoMapper.convertToComputerDTO(computerService.getById(id));
        }
        return dto;
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteComputer(@PathVariable Long id) {
        if (computerService.getById(id)!=null) {
            computerService.deleteComputer(id);
            return HttpStatus.OK;
        }  
        return HttpStatus.NOT_FOUND;
    }

   @PostMapping(value = {"", "/"})
    public ComputerDTO createComputer(@RequestBody ComputerDTO dto) {
      computerService.addComputer(ComputerDtoMapper.toComputer(dto));
      return dto;
    }

    @PutMapping(value = {"", "/"})
    public ComputerDTO updateComputer(@RequestBody ComputerDTO dto) {
      computerService.updateComputer(ComputerDtoMapper.toComputer(dto));
      return dto;
    } 
}
