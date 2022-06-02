package com.devsuperior.ClientsCatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.devsuperior.ClientsCatalog.entities.Client;
import com.devsuperior.ClientsCatalog.entities.dto.ClientDTO;
import com.devsuperior.ClientsCatalog.repositories.ClientRepository;
import com.devsuperior.ClientsCatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	public Page<ClientDTO>findAllPaged(PageRequest pageRequest){
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(obj -> new ClientDTO(obj));
		
	}

	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
		return new ClientDTO(entity);
	}

	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyEntityToDto(entity, dto);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}
	
	private void copyEntityToDto(Client entity, ClientDTO dto) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
		entity.setIncome(dto.getIncome());
	}

}
